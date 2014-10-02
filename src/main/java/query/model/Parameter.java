package query.model;

import java.util.Arrays;
import java.util.Collection;

import query.model.Syntax.Comparator;
import query.model.Syntax.Operator;

public class Parameter {
	
	private final Operator operator;
	private final Comparator comparator;
	private final String field;
	private Object value;
	
	public Parameter(Operator operator, Comparator comparator, String field, Object value) {
		this.operator = operator;
		this.comparator = comparator;
		this.field = field;
		this.value = value;
	}

	public Operator getOperator() {
		return operator;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public String getField() {
		return field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public boolean valueAttachable() {
		return comparator != Comparator.IS_NOT_NULL && comparator != Comparator.IS_NULL;
	}
	
	@Override
	public String toString() {
		if (value instanceof Collection) {
			return field + "=" + Arrays.toString(((Collection)value).toArray());
		}
		return field + "=" + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comparator == null) ? 0 : comparator.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (comparator != other.comparator)
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (operator != other.operator)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	

}
