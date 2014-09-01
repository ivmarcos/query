package query.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;

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
	
	public Where<T> where(Map<String, Object> parameters){
		return getQuery().parameters(parameters);
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

	public Select<T> select(String field) {
		return getQuery().select(field);
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	private Query<T> getQuery() {
		return new Query<T>(entityManager, entityClass);
	}
}

