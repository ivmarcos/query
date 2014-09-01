package query.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import query.domain.Data;


public class QueryClassReflection {

	private static final Class<?> GENERIC_CLASS = Data.class;
	
	private final Class<?> clazz;	
	private Map<Field, Method> setMethodsMapped; 
	private Map<Class<? extends Annotation>, List<Field>> fieldsCached;
	private Map<Class<? extends Annotation>, List<Method>> methodsCached;
	
	private enum MethodType{
		SET("set"), GET("get");
		private String declaration;
		MethodType(String declaration){
			this.declaration = declaration;
		}
		public String getDeclaration() {
			return declaration;
		}
	}
	
	public QueryClassReflection(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public boolean isGeneric() {
		return GENERIC_CLASS.isAssignableFrom(clazz) || clazz==GENERIC_CLASS; 
	}
		
	public Method getSetMethod(Field field)  {
		if (setMethodsMapped==null) setMethodsMapped = new HashMap<Field, Method>();
		Method method = setMethodsMapped.get(field);
		if (method==null) {
			try{
				method = findMethod(field, MethodType.SET);
			}catch(Exception e) {
				e.printStackTrace();
			}
			setMethodsMapped.put(field, method);
		}	
		return method;
	}	
	
	private Method findMethod(Field field, MethodType type) throws SecurityException, NoSuchMethodException {
		return clazz.getMethod(type.getDeclaration()+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1, field.getName().length()), field.getType());
	}
	
	public List<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation){
		if (fieldsCached==null) fieldsCached = new HashMap<Class<? extends Annotation>, List<Field>>();
		List<Field> fields = fieldsCached.get(annotation);
		if (fields==null) {
			fields = new ArrayList<Field>();
			for (Field f : clazz.getDeclaredFields()) {
				if (f.isAnnotationPresent(annotation)) {
					fields.add(f);
				}
			}
		}
		fieldsCached.put(annotation, fields);
		return fields;
	}
	
	public List<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation){
		if (methodsCached==null) methodsCached = new HashMap<Class<? extends Annotation>, List<Method>>();
		List<Method> methods = methodsCached.get(annotation);
		if (methods==null) {
			methods = new ArrayList<Method>();
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.isAnnotationPresent(annotation)) {
					methods.add(m);
				}
			}
		}
		methodsCached.put(annotation, methods);
		return methods;
	}
}
