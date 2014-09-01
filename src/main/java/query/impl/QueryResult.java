package query.impl;

import java.util.Arrays;
import java.util.List;

import query.converter.Converter;
import query.domain.Result;
import query.domain.Wrapper;
import query.model.Type;

public class QueryResult<T> implements Result<T>{

	private final Wrapper wrapper;
	private final javax.persistence.Query query;
	
	public QueryResult(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.query = wrapper.getQuery();
	}
	
	public List<T> list(){
		List result = getResultList();
		if (wrapper.getType() == Type.SQL_QUERY) {
			result = new Converter(wrapper.getEntityClass()).from(result);
		}
		return result;
	}
	
	public List listObject() {
		return getResultList();
	}
	
	public T single() {
		return (T) getSingleResult();
	}
	
	public boolean exists() {
		return getSingleResult() != null;
	}

	public javax.persistence.Query getQuery() {
		return query;
	}
	
	private List getResultList() {
		List result = Arrays.asList("OK", "ok");
		//List result = query.getResultList();
		wrapper.getCache().put(result);
		return result;
	}
	
	private Object getSingleResult() {
		///Object result = query.getSingleResult();
		List result = Arrays.asList("OK", "ok");
		wrapper.getCache().put(result);
		return result;
	}
	
	
}
