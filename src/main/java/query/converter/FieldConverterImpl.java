package query.converter;

import static query.converter.FieldTypeConverterImpl.BIGINTEGER_TO_BOOLEAN;
import static query.converter.FieldTypeConverterImpl.BYTE_TO_BOOLEAN;
import static query.converter.FieldTypeConverterImpl.DATASQL_TO_DATE;
import static query.converter.FieldTypeConverterImpl.INTEGER_TO_BOOLEAN;
import static query.converter.FieldTypeConverterImpl.NUMBER_TO_INT;
import static query.converter.FieldTypeConverterImpl.NUMBER_TO_LONG;
import static query.converter.FieldTypeConverterImpl.SAME_CLASS;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.FieldConverter;
import query.domain.FieldTypeConverter;

public class FieldConverterImpl implements FieldConverter {
	
	final static Logger logger = LoggerFactory.getLogger(FieldConverterImpl.class);
	
	private Map<Field, FieldTypeConverter> convertersCached = new HashMap<Field, FieldTypeConverter>();

	@Override
	public Object convert(Field field, Object object) {
		if (object==null) return null;
		FieldTypeConverter converter = getConverter(field, object);
		return converter != null ? converter.convert(object) : object;
	}
	
	private FieldTypeConverter getConverter(Field field, Object object) {
		FieldTypeConverter converter = convertersCached.get(field);
		if (converter != null) {
			return converter;
		}
		converter = parse(field, object);
		if (converter != null) {
			convertersCached.put(field, converter);
		}
		return converter;
	}
	
	private FieldTypeConverter parse(Field field, Object object) {
		final Class<?> source = object.getClass();
		final Class<?> destination = field.getType();
		if (source==destination) return SAME_CLASS;
		if (object instanceof Number) {
			if (destination==Long.class || destination==long.class) return NUMBER_TO_LONG;
			if (destination==Integer.class || destination==int.class) return NUMBER_TO_INT;
		}
		if (destination==Boolean.class || destination==boolean.class) {
			if (source==Integer.class) return INTEGER_TO_BOOLEAN;
			if (source==Byte.class) return BYTE_TO_BOOLEAN;
			if (source==BigInteger.class) return BIGINTEGER_TO_BOOLEAN;
		}
		if (destination==Date.class) {
			if (source==java.sql.Date.class) return DATASQL_TO_DATE;
		}
		logger.error("Classes not mapped. Source class -> {} Destination class -> {}", source.getName(), destination.getName());
		return null;
	}
}

