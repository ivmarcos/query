package query;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import query.domain.Wrapper;
import query.model.QueryWrapper;

public class Query<T> {

	private final Wrapper wrapper;
	
	public Query(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public Query(EntityManager entityManager) {
		wrapper = new QueryWrapper(entityManager);
	}
	
	public Query(EntityManager entityManager, Class<T> entityClass) {
		wrapper = new QueryWrapper(entityManager);
		wrapper.setEntityClass(entityClass);
	}
	
	public Query<T> query(String query){
		wrapper.setQueryString(query);
		return this;
	}
	
	public WithCache<T> cache(){
		return new WithCache<T>(wrapper);
	}
	
	public WithCache<T> cache(Object... keys){
		return new WithCache<T>(wrapper, keys);
	}

	public From from(Class<?> entityClass) {
		return new From<>(wrapper, entityClass);
	}
	
	@Deprecated
	public Select<T> field(String field) {
		return new Select<T>(wrapper, field);
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
