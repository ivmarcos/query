package query.model;

import java.util.Arrays;

public class OrderParameter {

	public OrderParameter(Sort sort, String...fields) {
		this.fields = fields;
		this.sort = sort;
	}
	private final String[] fields;
	private final Sort sort;
	
	public String[] getFields() {
		return fields;
	}
	public Sort getSort() {
		return sort;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fields);
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
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
		OrderParameter other = (OrderParameter) obj;
		Arrays.sort(fields);
		Arrays.sort(other.fields);
		if (!Arrays.equals(fields, other.fields))
			return false;
		if (sort != other.sort)
			return false;
		return true;
	}
	
	
	
}
