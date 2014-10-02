package query;

import java.util.List;

import query.domain.Wrapper;

public class Offset<T> {

	private final Wrapper wrapper;
	
	public Offset(Wrapper wrapper, int offset) {
		wrapper.setOffset(offset);
		this.wrapper = wrapper;
	}
	
	public Limit<T> limit(int limit){
		return new Limit<>(wrapper, limit);
	}
	
	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}
	
	public String getQueryString() {
		return new Executer<>(wrapper).getQueryString();
	}

}
