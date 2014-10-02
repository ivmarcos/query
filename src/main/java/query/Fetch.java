package query;

import java.util.List;
import java.util.Map;

import query.domain.Wrapper;
import query.model.Feature;

public class Fetch<T> {

	private final Wrapper wrapper;
	
	public Fetch(Wrapper wrapper, Class<?>[] fetchClasses) {
		wrapper.setFetchClasses(fetchClasses);
		this.wrapper = wrapper;
	}
	
	public Fetch(Wrapper wrapper, String[] fetchFields) {
		wrapper.setFetchFields(fetchFields);
		this.wrapper = wrapper;
	}
	
	public Fetch(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public Where<T> where(String field) {
		return new Where<T>(wrapper, field);
	}
	
	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}

	public Fetch<T> all() {
		wrapper.configure(Feature.FETCH_ALL, true);
		return this;
	}
	
	public Fetch<T> except(Class<?>[] exceptionFetchClasses){
		wrapper.setExceptionFetchClasses(exceptionFetchClasses);
		return this;
	}
	
	public Fetch<T> mandatoryJoins() {
		wrapper.configure(Feature.FETCH_MANDATORY_JOINS, true);
		return this;
	}
	
	public Order<T> orderBy(String...fields){
		return new Order<T>(wrapper, fields);
	}
	
	public Offset<T> offset(int offset){
		return new Offset<T>(wrapper, offset);
	}
	
	public Limit<T> limit(int limit){
		return new Limit<T>(wrapper, limit);
	}

	public Query<T> query(String query) {
		return new Query<T>(wrapper, query);
	}

	public Parameters<T> parameters(Map<String, Object> parameters) {
		return new Parameters<T>(wrapper, parameters);
	}
	
}
