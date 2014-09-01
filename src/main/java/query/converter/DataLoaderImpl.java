package query.converter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import query.annotation.AfterLoad;
import query.annotation.Mapped;
import query.domain.DataConverter;
import query.domain.FieldConverter;
import query.impl.QueryClassReflection;
import query.model.QueryException;

public class DataLoaderImpl implements DataConverter{
	
	private QueryClassReflection classReflection;
	private FieldConverter fieldConverter;
	private boolean hasAfterLoadMethods;
	

	private void init(Class<?> clazz, List<Object[]> list) {
		classReflection = new QueryClassReflection(clazz);
		fieldConverter = new FieldConverterImpl();
		hasAfterLoadMethods = classReflection.getMethodsAnnotatedWith(AfterLoad.class).size() > 0;
	}
	
	@Override
	public List populate(Class<?> clazz, List<Object[]> list)  {
		init(clazz, list);
		List result = new ArrayList();
		try {
			for (Object[] data : list) {
				Object newObject = clazz.newInstance();
				loadMappedFields(newObject, data);
				if (hasAfterLoadMethods) invokeAfterLoaded(newObject);
				result.add(newObject);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			return result;
		}
	}
	
	private void loadMappedFields(Object instance, Object[] data) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException, ConverterException{
		int i = 0;
		for (Field field : classReflection.getFieldsAnnotatedWith(Mapped.class)) {
			if (data[i] != null) {
				Object convertedData = fieldConverter.convert(field, data[i]);
				try{
					classReflection.getSetMethod(field).invoke(instance, convertedData);
				}catch(IllegalArgumentException e) {
					throw new QueryException("Field type is not matching. Field "+field.getName()+" class: "+field.getType().getName()+ " > Object class: "+convertedData.getClass().getName());
				}
			}
			i++;
		}
	}
	
	private void invokeAfterLoaded(Object instance) {
		try{
			for (Method method : classReflection.getMethodsAnnotatedWith(AfterLoad.class)) {
				method.invoke(instance, null);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
