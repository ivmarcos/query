package query.domain;

import java.util.List;

public interface DataConverter<T> {

	List<T> convert(List<Object[]> list);
	
	T convert(Object[] data);
	
}
