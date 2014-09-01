package query.domain;

import java.util.List;

public interface DataConverter {

	List populate(Class<?> clazz, List<Object[]> list);
	
}
