package query;

import java.util.Collection;

import query.domain.Parametizer;
import query.domain.Wrapper;
import query.model.Condition;
import query.model.Parameter;
import query.model.Syntax.Comparator;
import query.model.Syntax.Operator;

public class Where<T> implements Parametizer<T> {

	private final Wrapper wrapper;
	private String field;
	private Operator operator;
	
	public Where(Wrapper wrapper, Operator operator, String field) {
		this.wrapper = wrapper;
		this.operator = operator;
		this.field = field;
	}
	
	public Where(Wrapper wrapper, String field) {
		this.wrapper = wrapper;
		this.operator = Operator.AND;
		this.field = field;
	}
	
	

	@Override
	public Condition<T> is(Object value) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.IS, field, value));
	}

	@Override
	public Condition<T> in(Object... values) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.IN, field, values));
	}

	@Override
	public Condition<T> contains(String value) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.CONTAINS, field, value));
	}

	@Override
	public Condition<T> startsWith(String value) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.STARTS_WITH, field, value));
	}

	@Override
	public Condition<T> endsWith(String value) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.ENDS_WITH, field, value));
	}

	@Override
	public Condition<T> notNull() {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.IS_NOT_NULL, field, null));
	}

	@Override
	public Condition<T> isNull() {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.IS_NULL, field, null));
	}

	@Override
	public Condition<T> lowerThan(Number value) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.LOWER_THAN, field, value));
	}

	@Override
	public Condition<T> greaterThan(Number value) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.GREATER_THAN, field, value));
	}

	@Override
	public Condition<T> notEquals(Object value) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.IS_NOT, field, value));
	}

	@Override
	public Condition<T> in(Collection values) {
		return new Condition<T>(wrapper, new Parameter(operator, Comparator.IN, field, values));
	}
	

	
}
