package query;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;
import query.model.Feature;

public class Cache<T> {
	
	final static Logger logger = LoggerFactory.getLogger(Cache.class);

	private final Wrapper wrapper;
	
	public Cache(Wrapper wrapper, Object... keys) {
		wrapper.configure(Feature.CACHE_MODE, true);
		wrapper.getCache().setKey(keys);
		this.wrapper = wrapper;
	}
	
	
	public Cache(Wrapper wrapper) {
		wrapper.configure(Feature.CACHE_MODE, true);
		this.wrapper = wrapper;
	}
	
	public Cache(Wrapper wrapper, boolean cacheable) {
		if (cacheable) wrapper.configure(Feature.CACHE_MODE, true);
		this.wrapper = wrapper;
	}
	
	public Cache<T> query(String query){
		wrapper.setQueryString(query);
		return this;
	}

	public Cache<T> listen(Class<?>... classes){
		wrapper.getCache().setListenerClasses(classes);
		return this;
	}
	
	public SQL<T> sql(String sql) {
		return new SQL<T>(wrapper, sql);
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
	
}
