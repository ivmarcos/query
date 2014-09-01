package query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;

import query.domain.Wrapper;
import query.model.Feature;
import query.model.QueryWrapper;

public class DAO<T> implements Serializable {

	private final EntityManager entityManager;
	private final Class<T> entityClass;

	public DAO (Class<T> entityClass, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
	}
	
	public T add(T t) {
		entityManager.persist(t);
		return t;
	}
	
	public List<T> findAll(){
		return getQuery().find();
	}
	
	public T find(Object id) {
		return entityManager.getReference(entityClass, id);
	}
	
	public void remove(Object id) {
        entityManager.remove(find(id));
	}
	
	public T update(T t) {
		return (T) entityManager.merge(t);
	}
	
	public Number countAll() {
		return new Query<T>(entityManager, entityClass).count();
	}
	
	public DAO<T> clearCache() {
		Cache cache = entityManager.getEntityManagerFactory().getCache();
		cache.evict(entityClass);
		return this;
	}
	
	public DAO<T> clearAllCache() {
		Cache cache = entityManager.getEntityManagerFactory().getCache();
		cache.evictAll();
		return this;
	}
	
	public Parameters<T> parameters(Map<String, Object> parameters){
		return new Parameters<T>(getWrapper(), parameters);
	}

	public Where<T> where(String field) {
		return getQuery().where(field);
	}

	public Query<T> query(String query){ 
		return getQuery().query(query);
	}

	public Query<?> from(Class<?> entityClass){ 
		return new Query<>(entityManager, entityClass);
	}

	@Deprecated
	public Query<?> select(Class<?> entityClass){
		return new Query<>(entityManager, entityClass);
	}

	public Select<T> select(String field) {
		return getQuery().select(field);
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	private Query<T> getQuery() {
		return new Query<T>(entityManager, entityClass);
	}

	public Order<T> orderBy(String... fields){
		return new Order<T>(getWrapper(), fields);
	}
	
	private Wrapper getWrapper() {
		return new QueryWrapper(entityManager, entityClass);
	}
	
	public Query<T> feature(Feature feature, boolean active){
		Wrapper wrapper = getWrapper();
		wrapper.setFeature(feature, active);
		return new Query<T>(wrapper);
	}
}

