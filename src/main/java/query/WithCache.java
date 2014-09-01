package query;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;
import query.model.Feature;

public class WithCache<T> {
	
	final static Logger logger = LoggerFactory.getLogger(WithCache.class);

	private final Wrapper wrapper;
	
	public WithCache(Wrapper wrapper, Object... keys) {
		wrapper.setFeature(Feature.CACHE_MODE, true);
		wrapper.getCache().setKey(keys);
		this.wrapper = wrapper;
	}
	
	public WithCache(Wrapper wrapper) {
		wrapper.setFeature(Feature.CACHE_MODE, true);
		this.wrapper = wrapper;
	}
	
	public WithCache<T> query(String query){
		wrapper.setQueryString(query);
		return this;
	}
	
	public WithCache<T> listen(Class<?>... classes){
		wrapper.getCache().setListenerClasses(classes);
		return this;
	}
	
	public From<?> from(Class<?> entityClass) {
		return new From<>(wrapper, entityClass);
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
	
}
