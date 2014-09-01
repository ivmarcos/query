package query.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import query.domain.Wrapper;

public class Key {

	private final Class<?> entityClass;
	private final String queryString;
	private final int offset;
	private final int limit;
	private final Sort sort;
	private final String[] groupByFields;
	private final List<OrderParameter> orderParameters;
	private final List<Parameter> parameters;
	private final String selectedField;
	private final Type type;
	
	public Key(Wrapper wrapper) {
		this.entityClass = wrapper.getEntityClass();
		this.queryString = wrapper.getQueryString();
		this.offset = wrapper.getOffset();
		this.limit = wrapper.getLimit();
		this.sort = wrapper.getSort();
		this.groupByFields = wrapper.getGroupByFields();
		this.orderParameters = wrapper.getOrderParameters();
		this.parameters = wrapper.getParameters();
		this.selectedField = wrapper.getSelectedField();
		this.type = wrapper.getType();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityClass == null) ? 0 : entityClass.hashCode());
		result = prime * result + Arrays.hashCode(groupByFields);
		result = prime * result + limit;
		result = prime * result + offset;
		result = prime * result
				+ ((orderParameters == null) ? 0 : orderParameters.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result
				+ ((queryString == null) ? 0 : queryString.hashCode());
		result = prime * result
				+ ((selectedField == null) ? 0 : selectedField.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Key other = (Key) obj;
		if (entityClass == null) {
			if (other.entityClass != null)
				return false;
		} else if (!entityClass.equals(other.entityClass))
			return false;
		if (limit != other.limit)
			return false;
		if (offset != other.offset)
			return false;
		if (groupByFields != null) {
			Arrays.sort(groupByFields);
			if (other.groupByFields != null) {
				Arrays.sort(other.groupByFields);
			}else {
				return false;
			}
		}
		if (!Arrays.equals(groupByFields, other.groupByFields))
			return false;
		if (orderParameters == null) {
			if (other.orderParameters != null)
				return false;
		} else {
			Collections.sort(orderParameters, new OrderParameterComparator());
			Collections.sort(other.orderParameters, new OrderParameterComparator());
			if (!orderParameters.equals(other.orderParameters)) return false;
		}
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else {
			Collections.sort(parameters, new ParameterComparator());
			Collections.sort(other.parameters, new ParameterComparator());
			if (!parameters.equals(other.parameters)) return false;
		}
		return true;
	}
	
	
	
	
}
