package query.model;

import java.util.Comparator;

public class ParameterComparator implements Comparator<Parameter> {

	@Override
	public int compare(Parameter p1, Parameter p2) {
		int result;
		result = p1.getComparator().compareTo(p2.getComparator());
		if (result == 0) {
			result = p1.getOperator().compareTo(p2.getOperator());
		}
		if (result == 0) {
			result = p1.getField().compareTo(p2.getField());
		}
		if (result == 0) {
			result = p1.getValue().toString().compareTo(p2.getValue().toString());
		}
		return result;
	}

}
