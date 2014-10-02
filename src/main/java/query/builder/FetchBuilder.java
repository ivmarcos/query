package query.builder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.Reflection;
import query.domain.Wrapper;
import query.model.Feature;

public class FetchBuilder {
	
	final static Logger logger = LoggerFactory.getLogger(FetchBuilder.class);

	private final Wrapper wrapper;
	private final Reflection reflection;
	private final List<Class<?>> fetchClasses;
	private final List<Class<?>> exceptionFetchClasses;
	private final List<String> fetchFields;
	
	public FetchBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.reflection = wrapper.getReflection();
		this.fetchClasses = wrapper.getFetchClasses() != null ? Arrays.asList(wrapper.getFetchClasses()) : null;
		this.fetchFields = wrapper.getFetchFields() != null ? Arrays.asList(wrapper.getFetchFields()) : null;
		this.exceptionFetchClasses = wrapper.getExceptionFetchClasses() != null ? Arrays.asList(wrapper.getExceptionFetchClasses()) : new ArrayList<Class<?>>();
	}
	
	public void build() {
		List<Field> fields = getFieldsToFetch();
		logger.debug("{} Joins to fetch: {}", wrapper, fields.size());
		for (Field field  : fields) {
			wrapper.getBuild()
				.append(" ")
				.append(getJoinSyntax(field))
				.append(" fetch ")
				.append(reflection.getEntityClassInitials())
				.append(".")
				.append(field.getName())
				.append(" ")
				.append(field.getName().toLowerCase());
		}
	}
	
	private List<Field> getFieldsToFetch(){
		List<Field> fields = new ArrayList<Field>();
		for (Field field : wrapper.getEntityClass().getDeclaredFields()) {
			if (matchStrategy(field)) {
				fields.add(field);
			}
		}
		return fields;
	}
	
	private boolean matchStrategy(Field field) {
		if (fetchClasses != null) {
			return fetchClasses.contains(field.getType()) && !exceptionFetchClasses.contains(field.getType());
		}
		if (fetchFields != null) {
			return fetchFields.contains(field.getName());
		}
		if (wrapper.enabled(Feature.FETCH_MANDATORY_JOINS)) {
			return manyToOne(field) && !optional(field);
		}
		if (wrapper.enabled(Feature.FETCH_ALL)) {
			return manyToOne(field);
		}
		if (wrapper.enabled(Feature.FETCH_JOINS)) {
			return manyToOne(field) && !lazy(field) && !cached(field);
		}
		return false;
	}
	
	
	private String getJoinSyntax(Field field) {
		if (optional(field)) {
			return "left join";
		}
		return "join";
	}
	
	private boolean lazy(Field field) {
		return field.getAnnotation(ManyToOne.class).fetch() == FetchType.LAZY;
	}
	
	private boolean manyToOne(Field field) {
		return field.isAnnotationPresent(ManyToOne.class);
	}
	
	private boolean optional(Field field) {
		return field.getAnnotation(ManyToOne.class).optional();
	}
	
	private boolean cached(Field field) {
		return field.getType().isAnnotationPresent(org.hibernate.annotations.Cache.class);
	}
	

}
