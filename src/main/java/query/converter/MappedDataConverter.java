package query.converter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import query.QueryClassReflection;
import query.annotations.AfterLoad;
import query.annotations.Mapped;
import query.domain.DataConverter;
import query.domain.FieldConverter;
import query.model.QueryException;

public class MappedDataConverter<T> implements DataConverter<T>{
	
	private final Class<T> entityClass;
	private final QueryClassReflection reflection;
	private final FieldConverter converter;
	private final boolean hasAfterLoadMethods;
	
	public MappedDataConverter(Class<T> entityClass) {
		this.entityClass = entityClass;
		reflection = new QueryClassReflection(entityClass);
		converter = new FieldConverterImpl();
		hasAfterLoadMethods = reflection.getMethodsAnnotatedWith(AfterLoad.class).size() > 0;
	}
	
	@Override
	public List<T> convert(List<Object[]> list)  {
		List<T> result = new ArrayList<T>();
		try {
			for (Object[] data : list) {
				T newObject = entityClass.newInstance();
				injectValues(newObject, data);
				if (hasAfterLoadMethods) invokeAfterLoad(newObject);
				result.add(newObject);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private void injectValues(Object instance, Object[] data) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException, ConverterException{
		int i = 0;
		for (Field field : reflection.getFieldsAnnotatedWith(Mapped.class)) {
			if (data[i] != null) {
				Object convertedData = converter.convert(field, data[i]);
				try{
					reflection.getSetMethod(field).invoke(instance, convertedData);
				}catch(IllegalArgumentException e) {
					throw new QueryException("Field type is not matching. Field "+field.getName()+" class: "+field.getType().getName()+ " > Object class: "+convertedData.getClass().getName());
				}
			}
			i++;
		}
	}
	
	private void invokeAfterLoad(Object instance) {
		try{
			for (Method method : reflection.getMethodsAnnotatedWith(AfterLoad.class)) {
				method.invoke(instance, null);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public T convert(Object[] data) {
		T result = null;
		try {
			result = entityClass.newInstance();
			injectValues(result, data);
			if (hasAfterLoadMethods) invokeAfterLoad(result);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
