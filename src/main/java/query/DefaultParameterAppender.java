package query;

import java.util.Collection;

import query.domain.ParameterAppender;
import query.domain.Wrapper;
import query.model.Parameter;

public class DefaultParameterAppender implements ParameterAppender {
	
	private final Wrapper wrapper;
	private boolean containsWhere;
	
	public DefaultParameterAppender(Wrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void append() {
		if (wrapper.getParametersToAppend() == null) return;
		checkContainsWhere();
		for (Parameter parameter : wrapper.getParametersToAppend()) {
			appendOperator(parameter);
			wrapper.getBuilder().appendField(parameter);
			appendComparator(parameter);
			appendParameter(parameter);
			parameter.getComparator().process(parameter);
		}
	}


	private void appendOperator(Parameter parameter) {
		if (!containsWhere) {
			wrapper.getBuild().append(" where ");
			containsWhere = true;
		}else {
			wrapper.getBuild()
				.append(" ")
				.append(parameter.getOperator().getSyntax())
				.append(" ");
		}
	}
	
	private void checkContainsWhere() {
		containsWhere = wrapper.getBuild().toString().toLowerCase().contains("where");
	}
	
	private void appendComparator(Parameter parameter) {
		final String syntax = getSyntax(parameter);
		wrapper.getBuild()
			.append(" ")
			.append(syntax)
			.append(" ");
	}
	
	private String getSyntax(Parameter parameter) {
		if (parameter.getValue() != null && parameter.getValue().getClass() == String.class) {
			return "like";
		}
		if (parameter.getValue() instanceof Collection) {
			return "in";
		}
		return parameter.getComparator().getSyntax();
	}
	
	private void appendParameter(Parameter parameter) {
		if (parameter.valueAttachable()) {
			wrapper.getBuild()
				.append(":")
				.append(parameter.getField().replace(".", ""));
		}
	}
	
	private boolean stringValue(Object value) {
		return value != null && value.getClass() == String.class;
	}
	
}
