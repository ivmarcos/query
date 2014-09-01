package query.builder;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.StatementBuilder;
import query.domain.Wrapper;
import query.impl.Reflection;
import query.model.Feature;
import query.model.Type;

public class SelectBuilder implements StatementBuilder {
	
	final static Logger logger = LoggerFactory.getLogger(SelectBuilder.class);
	
	private final Wrapper wrapper;
	private final StringBuilder build;
	private final Reflection reflection;

	public SelectBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
		build = wrapper.getBuild();
		reflection = wrapper.getReflection();
	}

	@Override
	public void build() {
		if (wrapper.getQueryString() != null) {
			buildByQuery();
		}else {
			buildByStatement();
		}
	}
	
	private void buildByQuery() {
		if (wrapper.getType() == Type.NAMED_QUERY && wrapper.getParametersToAppend() != null) {
			wrapper.getBuild().append(wrapper.getReflection().getQueryString(wrapper.getQueryString()));
		}else {
			wrapper.getBuild().append(wrapper.getQueryString());
		}
	}
	
	private void buildByStatement() {
		build
			.append("Select ")
			.append(reflection.getEntityClassInitials());
		if (wrapper.getSelectedField() != null) {
			build
				.append(".")
				.append(wrapper.getSelectedField());
		}
		build
			.append(" from ")
			.append(reflection.getEntityClassMapping());
		fetchJoins();
	}
	
	private void fetchJoins() {
		if (!wrapper.isActive(Feature.FETCH_JOINS)) return; 
		List<Field> fields = reflection.getFieldsToFetch();
		logger.info("Joins to fetch: {}", fields.size());
		for (Field field  : fields) {
			build.append(" ")
				.append(reflection.getJoinSyntax(field))
				.append(" fetch ")
				.append(reflection.getEntityClassInitials())
				.append(".")
				.append(field.getName())
				.append(" ")
				.append(field.getName().toLowerCase());
		}
	}
}
