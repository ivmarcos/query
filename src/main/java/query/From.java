package query;

import java.util.List;

import query.domain.Wrapper;

public class From<T> {
	
	private final Wrapper wrapper;
	
	public From (Wrapper wrapper, Class<T> entityClass) {
		wrapper.setEntityClass(entityClass);
		this.wrapper = wrapper;
	}
	
	public Where<T> where(String field) {
		return new Where(wrapper, field);
	}

	public List find() {
		return new Executer(wrapper).find();
	}

	public Order<T> orderBy(String...fields){
		return new Order<>(wrapper, fields);
	}
	
	public Offset<T> offset(int offset){
		return new Offset<>(wrapper, offset);
	}
	
	public Limit<T> limit(int limit){
		return new Limit<>(wrapper, limit);
	}
}
