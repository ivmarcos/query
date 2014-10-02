package query;

import java.util.List;
import java.util.Map;

import query.domain.Wrapper;

public class From<T> {
	
	private final Wrapper wrapper;
	
	public From (Wrapper wrapper, Class<T> entityClass) {
		wrapper.setEntityClass(entityClass);
		this.wrapper = wrapper;
	}
	
	public Where<T> where(String field) {
		return new Where<T>(wrapper, field);
	}
	
	public Fetch<T> fetch(Class<?>... fetchClasses){
		return new Fetch<T>(wrapper, fetchClasses);
	}

	public Fetch<T> fetch(String... fetchFields){
		return new Fetch<T>(wrapper, fetchFields);
	}

	public Fetch<T> fetch(){
		return new Fetch<T>(wrapper);
	}
	
	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}

	public Order<T> orderBy(String...fields){
		return new Order<>(wrapper, fields);
	}
	
	public Offset<T> offset(int offset){
		return new Offset<>(wrapper, offset);
	}
	
	public Limit<T> limit(int limit){
		return new Limit<>(wrapper, limit);
	}

	public Query<T> query(String query) {
		return new Query<T>(wrapper, query);
	}

	public Parameters<T> parameters(Map<String, Object> parameters) {
		return new Parameters<T>(wrapper, parameters);
	}
}
