package query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Cache {
	
	final static Logger logger = LoggerFactory.getLogger(Cache.class);
	
	private static com.google.common.cache.Cache<Object, Object> cache;
	private static Map<Class<?>, List<Object>> keyClassesMap;
	
	static {
		cache = com.google.common.cache.CacheBuilder
						.newBuilder()
						.maximumSize(1000)
						.expireAfterAccess(30, TimeUnit.MINUTES)
						.expireAfterWrite(60, TimeUnit.MINUTES)
						.build();
		keyClassesMap = new HashMap<Class<?>, List<Object>>();
	}
	
	public static CacheBuilder newBuilder() {
		return new CacheBuilder();
	}
	
	public static Object get(Object key) {
		logger.info("Getting from cache {}", key.toString());
		return cache.getIfPresent(key);
	}
	
	public static Object get(Object... keys) {
		logger.info("Getting cache for {}", convert(keys));
		return cache.getIfPresent(convert(keys));
	}
	
	public static void put(Object key, Object value) {
		cache.put(key, value);
	}
	
	private static String convert(Object...keys) {
		return Arrays.asList(keys).toString();
	}
	
	private static void put(Object key, Object value, Class<?>... keyClasses) {
		for (Class<?> keyClass : keyClasses) {
			List<Object> keys = keyClassesMap.get(keyClass);
			if (keys == null) keys = new ArrayList<Object>();
			keys.add(key);
			keyClassesMap.put(keyClass, keys);
		}
		cache.put(key, value);
	}
	
	
	public static void evict(Object key) {
		cache.invalidate(key);
	}
	
	public static void evict(Class<?> keyClass) {
		List<Object> keys = keyClassesMap.get(keyClass);
		if (keys != null) {
			for (Object key : keys) {
				evict(key);
			}
		}
		keyClassesMap.remove(keyClass);
	}
	
	public static void clear() {
		cache.invalidateAll();
		keyClassesMap.clear();
	}
	
	public static class CacheBuilder {
		
		private Class<?>[] classes;
		private Object[] keys;
		private Object key;
		private Object value;

		public CacheBuilder listen(Class<?>... classes) {
			this.classes = classes;
			return this;
		}
		
		public CacheBuilder keys(Object... keys) {
			this.keys = keys;
			return this;
		}

		public CacheBuilder key(Object key) {
			this.key = key;
			return this;
		}
		
		public CacheBuilder value(Object value) {
			this.value = value;
			return this;
		}
		
		public void build() {
			validate();
			if (key == null) {
				key = convert(keys);
			}
			if (classes == null) {
				put(key, value);
			}else {
				put(key, value, classes);
			}
		}
		
		private void validate() {
			if (value==null) throw new IllegalArgumentException("No value was informed.");
			if (key==null && keys == null) throw new IllegalArgumentException("No keys was informed.");
		}
		
	}
}
