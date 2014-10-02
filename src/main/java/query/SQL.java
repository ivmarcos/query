package query;

import java.util.List;
import java.util.Map;

import query.domain.Wrapper;
import query.model.Type;

public class SQL<T> {

	private final Wrapper wrapper;
	
	public SQL(Wrapper wrapper, String sql) {
		wrapper.setQueryString(sql);
		wrapper.setType(Type.SQL_QUERY);
		this.wrapper = wrapper;
	}
	
	public Cache<T> cache(){
		return new Cache<T>(wrapper);
	}
	
	public Cache<T> cache(Object... keys){
		return new Cache<T>(wrapper, keys);
	}

	public <X> From<X> from(Class<X> entityClass) {
		return new From<X>(wrapper, entityClass);
	}
	
	public Where<T> where(String field) {
		return new Where<T>(wrapper, field);
	}
	
	public Parameters<T> parameters(Map<String, Object> parameters){
		return new Parameters<T>(wrapper, parameters);
	}
	
	public Select<T> select(String field) {
		return new Select<T>(wrapper, field);
	}

	public Number count() {
		return new Executer<>(wrapper).count();
	}
	
	public Number sum(String field) {
		return new Executer<>(wrapper, field).sum();
	}

	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}

	public Order<T> orderBy(String... fields) {
		return new Order<>(wrapper, fields);
	}
	
	public String getQueryString() {
		return new Executer<>(wrapper).getQueryString();
	}
	
	public Offset<T> offset(int offset){
		return new Offset<>(wrapper, offset);
	}
	
	public Limit<T> limit(int limit){
		return new Limit<>(wrapper, limit);
	}
	
	public List<Object[]> listObject() {
		return new Executer<T>(wrapper).listObject();
	}
}
