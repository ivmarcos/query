package query.converter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.DataConverter;

public class Converter {
	
	final static Logger logger = LoggerFactory.getLogger(Converter.class.getName());
	
	private final Class<?> clazz;
	
	public Converter(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public List from(List<Object[]> resultList) {
		return populateList(resultList);
	}
	
	private List populateList(List<Object[]> list){
		DataConverter loader = new DataLoaderImpl();
		List result = loader.populate(clazz, list);
		return result;
	}
	
	
}
