package query;

import java.util.List;

import query.domain.Wrapper;

public class Limit<T> {

	private final Wrapper wrapper;
	
	public Limit(Wrapper wrapper, int limit) {
		wrapper.setLimit(limit);
		this.wrapper = wrapper;
	}
	
	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}
	
	public String getQueryString() {
		return new Executer<>(wrapper).getQueryString();
	}

	
}
