package query.domain;

import java.util.Collection;

import query.model.Condition;

public interface Parametizer<T> {
	
	Condition<T> is(Object value);
	Condition<T> in(Collection<?> values);
	Condition<T> in(Object... values);
	Condition<T> contains(String value);
	Condition<T> startsWith(String value);
	Condition<T> endsWith(String value);
	Condition<T> notEquals(Object value);
	Condition<T> notNull();
	Condition<T> isNull();
	Condition<T> lowerThan(Number value);
	Condition<T> greaterThan(Number value);
}
