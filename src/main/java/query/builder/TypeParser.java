package query.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;
import query.model.Feature;
import query.model.Type;

public class TypeParser {

	final static Logger logger = LoggerFactory.getLogger(TypeParser.class);
	
	private final Wrapper wrapper;
	private final Type type;
	
	public TypeParser(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.type = wrapper.getType();
	}

	public void parse() {
		checkTypes();
		checkFeatures();
		validate();
		logger.info("{} Type: {} ", wrapper, wrapper.getType());
	}
	
	private void checkTypes() {
		if (hasNamedQuerySyntax() && type == Type.JPA_QUERY) {
			wrapper.setType(Type.NAMED_QUERY);
		}else if (hasEntityAnnotation() && type == Type.SQL_QUERY) {
			wrapper.setType(Type.NATIVE_QUERY);
		}else if (!hasEntityAnnotation()) {
			wrapper.setType(Type.SQL_QUERY);
		}
	}
	
	private void checkFeatures() {
		if (wrapper.getSelectedField() != null) {
			wrapper.configure(Feature.FETCH_JOINS, false);
		}
	}
	
	private void validate() {
		/*if (wrapper.getType() != Type.SQL_QUERY && !wrapper.getReflection().hasEntityAnnotation()) {
			throw new QueryException("Class " + wrapper.getEntityClass() + " is not a entity!");
		}*/
	}
	
	private boolean hasNamedQuerySyntax() {
		return wrapper.getQueryString() != null && !wrapper.getQueryString().contains(" ");
	}
	
	private boolean hasEntityAnnotation() {
		return wrapper.getReflection().hasEntityAnnotation();
	}
	
	
	
}
