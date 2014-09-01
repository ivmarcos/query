package query;

import java.util.List;

import query.domain.Wrapper;

public class Group<T> {
	
	private final Wrapper wrapper;
	
	public Group(Wrapper wrapper, String[] fields) {
		wrapper.setGroupByFields(fields);
		this.wrapper = wrapper;
	}

	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}

	public String getQueryString() {
		return new Executer<>(wrapper).getQueryString();
	}
	
	
}
