package query.model;

import java.util.Arrays;
import java.util.Comparator;

public class OrderParameterComparator implements Comparator<OrderParameter> {

	@Override
	public int compare(OrderParameter o1, OrderParameter o2) {
		int result;
		result = o1.getSort().compareTo(o2.getSort());
		if (result == 0) {
			result = Arrays.asList(o1.getFields()).toString().compareTo(Arrays.asList(o2.getFields()).toString());
		}
		return result;
	}

}
