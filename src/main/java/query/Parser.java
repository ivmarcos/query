package query;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;
import query.model.Parameter;
import query.model.Type;
import query.util.Strings;

public class Parser {

	final static Logger logger = LoggerFactory.getLogger(Parser.class);
	
	private final Wrapper wrapper;
	private final Type type;
	
	public Parser(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.type = wrapper.getType();
	}

	public void parse() {
		if (hasNamedQuerySyntax() && type == Type.JPA_QUERY) {
			wrapper.setType(Type.NAMED_QUERY);
		}
		if (hasParametersToCheck()) {
			if (wrapper.getQueryString() == null) {
				wrapper.setParametersToAppend(wrapper.getParameters());
				return;
			}
			wrapper.setParametersToAppend(new ArrayList<Parameter>());
			for (Parameter parameter : wrapper.getParameters()) {
				if (!alreadyContains(parameter)) wrapper.getParametersToAppend().add(parameter);
			}
		}
	}
	
	private boolean alreadyContains(Parameter parameter) {
		return wrapper.getQueryString().contains(":" + removeSymbols(parameter.getField()));
	}
	
	private String removeSymbols(String value) {
		return Strings.noSymbols(value);
	}
	
	private boolean hasNamedQuerySyntax() {
		return wrapper.getQueryString()!= null && !wrapper.getQueryString().contains(" ");
	}
	
	private boolean hasParametersToCheck() {
		return wrapper.getParameters() != null;
	}
}
