package query;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;
import query.model.Feature;
import query.model.Key;

public class QueryCache {
	
	final static Logger logger = LoggerFactory.getLogger(QueryCache.class);
	
	private Object result;
	private final Wrapper wrapper;
	private Object key;
	private Class<?>[] listenerClasses;
	
	public QueryCache(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	private boolean isActive() {
		return wrapper.isActive(Feature.CACHE_MODE);
	} 

	public void put(Object result) {
		if (!isActive()) return;
		Cache
			.newBuilder()
			.key(getKey())
			.listen(getListenerClasses())
			.value(result)
			.build();
		this.result = result;
	}
	
	private void lookCache() {
		logger.info("Looking at cache");
		result = Cache.get(getKey());
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public boolean inCache() {
		if (isActive()){
			lookCache();
		}
		return result != null;
	}
	
	private Class<?>[] getListenerClasses(){
		if (listenerClasses == null) {
			listenerClasses = new Class<?>[]{wrapper.getEntityClass()};
		}
		return listenerClasses;
	}
	
	private Object getKey() {
		if (key == null) {
			key = new Key(wrapper);
		}
		return key;
	}
	
	public void setKey(Object... keys) {
		if (keys.length == 1) {
			key = keys[0];
		}else {
			key = Arrays.asList(keys).toString();
		}
	}
	
	public void setListenerClasses(Class<?>[] listenerClasses) {
		this.listenerClasses = listenerClasses;
	}
}
