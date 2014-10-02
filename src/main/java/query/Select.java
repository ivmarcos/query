package query;

import java.util.List;

import query.domain.Wrapper;

public class Select<T>{

	private final Wrapper wrapper;
	
	public Select(Wrapper wrapper, String field) {
		wrapper.setSelectedField(field);
		this.wrapper = wrapper;
	}
	
	public Select<T> distinct() {
		wrapper.setDistinctSelect(true);
		return this;
	}

	public From from(Class<?> entityClass) {
		return new From(wrapper, entityClass);
	}
	
	public Where<T> where(String field) {
		return new Where<T>(wrapper, field);
	}
	
	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}

	public Object findSingle() {
		return new Executer<T>(wrapper).findSingle();
	}

	public boolean exists() {
 		return new Executer<T>(wrapper).exists();
	}
	
	public Offset<T> offset(int offset){
		return new Offset<T>(wrapper, offset);
	}
	
	public Limit<T> limit(int limit){
		return new Limit<T>(wrapper, limit);
	}
}
