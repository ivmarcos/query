package query.domain;

import java.lang.reflect.Field;

public interface FieldConverter {

	Object convert(Field field, Object data);
	
}
