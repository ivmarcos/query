package query.builder;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;
import query.model.Parameter;
import query.util.Strings;

public class ParameterParser {

	final static Logger logger = LoggerFactory.getLogger(ParameterParser.class);
	
	private final Wrapper wrapper;
	private final String queryBuild;
	
	public ParameterParser(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.queryBuild = wrapper.getBuild().toString();
	}

	public void parse() {
		if (hasParametersToCheck()) {
			if (queryBuild == null || queryBuild.isEmpty()) {
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
		return queryBuild.contains(":" + removeSymbols(parameter.getField()));
	}
	
	private String removeSymbols(String value) {
		return Strings.noSymbols(value);
	}
	
	private boolean hasParametersToCheck() {
		return wrapper.getParameters() != null;
	}
}
