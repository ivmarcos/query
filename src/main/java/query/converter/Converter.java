package query.converter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.DataConverter;

public class Converter<T> {
	
	final static Logger logger = LoggerFactory.getLogger(Converter.class.getName());
	
	private final Class<T> returnClass;
	private final DataConverter<T> converter;
	
	public Converter(Class<T> returnClass) {
		this.returnClass = returnClass;
		this.converter = getDataConverter();
	}
	
	public List<T> convert(List<Object[]> list) {
		logger.debug("Converting list to {}", returnClass.getName());
		return converter.convert(list);
	}
	
	public T convert(Object[] object) {
		logger.debug("Converting array to {}", returnClass.getName());
		return (T) converter.convert(object);
	}
	
	
	private DataConverter<T> getDataConverter() {
		if (returnClass == String[].class) {
			return new StringDataConverter();
		}
		return new MappedDataConverter<T>(returnClass);
	}
	
	
}
