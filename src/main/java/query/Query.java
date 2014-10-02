package query;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import query.domain.Wrapper;
import query.model.Feature;
import query.model.QueryWrapper;

@RequestScoped
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
	
	public Query(Wrapper wrapper, String query) {
		wrapper.setQueryString(query);
		this.wrapper = wrapper;
	}
	
	public Query<T> query(String query){
		wrapper.setQueryString(query);
		return this;
	}
	
	public Fetch<T> fetch(Class<?>... fetchClasses){
		return new Fetch<T>(wrapper, fetchClasses);
	}

	public Fetch<T> fetch(String... fetchFields){
		return new Fetch<T>(wrapper, fetchFields);
	}

	public Fetch<T> fetch(){
		wrapper.configure(Feature.FETCH_JOINS, true);
		return new Fetch<T>(wrapper);
	}
	
	public SQL<T> sql(String sql){
		return new SQL<>(wrapper, sql);
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
	
	public Select select(String field) {
		return new Select(wrapper, field);
	}
	
	public <X> Select<X> select(String field, Class<X> returnClass) {
		return new Select<X>(wrapper, field);
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
	
	public T find(Object id) {
		return (T) wrapper.getEntityManager().find(wrapper.getEntityClass(), id);
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
