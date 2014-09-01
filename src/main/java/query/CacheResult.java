package query;

import java.util.List;

import query.domain.Result;
import query.domain.Wrapper;

public class CacheResult<T> implements Result<T>{

	private final Wrapper wrapper;
	
	public CacheResult(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public List<T> list(){
		return (List<T>) wrapper.getCache().getResult();
	}
	
	public List listObject() {
		return (List) wrapper.getCache().getResult();
	}
	
	public T single() {
		return (T) (List) wrapper.getCache().getResult();
	}
	
	public boolean exists() {
		return (List) wrapper.getCache().getResult() != null;
	}

	public javax.persistence.Query getQuery() {
		return null;
	}
}
