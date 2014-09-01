package query.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.annotation.Join;
import query.annotation.Mapped;
import query.model.QueryException;


public class Reflection {

	final static Logger logger = LoggerFactory.getLogger(Reflection.class);
	
	private final Class<?> entityClass;
	private final String entityClassMapping;
	private final String entityClassInitials;
	
	public Reflection(Class<?> entityClass) {
		this.entityClass = entityClass;
		entityClassInitials = entityClass.getSimpleName().substring(0, 1).toLowerCase();
		entityClassMapping = entityClass.getSimpleName() + " " + entityClassInitials;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public String getEntityClassMapping() {
		return entityClassMapping;
	}

	public String getEntityClassInitials() {
		return entityClassInitials;
	}
	
	public List<Field> getFieldsToFetch(){
		List<Field> fields = new ArrayList<Field>();
		for (Field field : entityClass.getDeclaredFields()) {
			if (manyToOne(field) && !lazyFetch(field) && !cached(field)) {
				fields.add(field);
			}
		}
		return fields;
	}
	
	
	public String getJoinSyntax(Field field) {
		if (joined(field)){
			return field.getAnnotation(Join.class).value().name().toLowerCase() + " join";
		}else if (optional(field)) {
			return "left join";
		}
		return "join";
	}
	
	public String getQueryString(String namedQueryName) {
		NamedQueries namedQueries = (NamedQueries) entityClass.getAnnotation(NamedQueries.class);
		for (NamedQuery namedQuery : namedQueries.value()) {
			if (namedQuery.name().equals(namedQueryName)) return namedQuery.query();
		}
		throw new QueryException("Named query " + namedQueryName + " not found in class " + entityClass.getName());
	}
	
	public String getTableName(String columnField) {
		Field field = null;
		try {
			field = getField(columnField);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field.getDeclaringClass().getAnnotation(Table.class).name();
	}
	
	public String getColumnName(String columnField) {
		Field field = null;
		try {
			field = getField(columnField);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field.getAnnotation(Column.class).name();
	}
	
	public String getMappedSQLName(String columnField) {
		try {
			Field field = entityClass.getDeclaredField(columnField);
			if (field.isAnnotationPresent(Mapped.class)) {
				String sql = field.getAnnotation(Mapped.class).sql();
				if (sql.isEmpty()) throw new IllegalArgumentException("No sql mapped in the field "+columnField+" for class "+entityClass.getName());
				return sql;
			}else {
				throw new IllegalArgumentException("Field "+columnField+" for class "+entityClass.getName()+" is not mapped.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			return null;
		}
	}

	private Class getFieldClass(String columnField) throws NoSuchFieldException, SecurityException {
		return getField(columnField).getType();
	}
	
	public String getComparator(String columnField) {
		final String equal = " = ";
		try {
			Class fieldClass = getFieldClass(columnField);
			if (fieldClass==String.class) return " like ";
		} catch (Exception e) {
			return equal;
		}
		return equal;
	}
	
	private Field getField(String columnField) throws NoSuchFieldException, SecurityException {
		Class fieldClass = entityClass;
		String[] fields = columnField.replace(".","_").split("_");
		Field field = null;
		for(String f : fields) {
			field = fieldClass.getDeclaredField(f);
			fieldClass = field.getType();
		}
		return field;
	}
	
	private boolean lazyFetch(Field field) {
		return field.getAnnotation(ManyToOne.class).fetch() == FetchType.LAZY;
	}
	
	private boolean manyToOne(Field field) {
		return field.isAnnotationPresent(ManyToOne.class);
	}
	
	private boolean optional(Field field) {
		return field.getAnnotation(ManyToOne.class).optional();
	}
	
	private boolean joined(Field field) {
		return field.isAnnotationPresent(Join.class);
	}
	
	private boolean cached(Field field) {
		return field.getType().isAnnotationPresent(null);
	}
}
