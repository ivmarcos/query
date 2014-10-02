package query;

import java.util.List;

import query.domain.Result;
import query.domain.Wrapper;

public class CacheResult<T> implements Result<T>{

	private final Wrapper wrapper;
	
	public CacheResult(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	@Override
	public List<T> list(){
		return (List<T>) wrapper.getCache().getResult();
	}
	
	@Override
	public List listObject() {
		return (List) wrapper.getCache().getResult();
	}
	
	@Override
	public T single() {
		return (T) wrapper.getCache().getResult();
	}
	
	@Override
	public boolean exists() {
		return (List) wrapper.getCache().getResult() != null;
	}

	@Override
	public javax.persistence.Query getQuery() {
		return null;
	}

	@Override
	public Object singleObject() {
		return wrapper.getCache().getResult();
	}
}
