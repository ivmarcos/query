package query.impl;

import java.util.List;

import query.domain.Wrapper;
import query.model.OrderParameter;
import query.model.Sort;

public class Order<T> {

	private final Wrapper wrapper;
	private final OrderParameter orderParameter;
	private final String[] fields;
	
	public Order(Wrapper wrapper, String...fields) {
		this.fields = fields;
		this.wrapper = wrapper;
		orderParameter = new OrderParameter(Sort.ASCENDING, fields);
		wrapper.addOrderParameter(orderParameter);
	}

	public Order<T> descending() {
		int index = wrapper.getOrderParameters().indexOf(orderParameter);
		wrapper.getOrderParameters().set(index, new OrderParameter(Sort.DESCENDING, fields));
		return this;
	}
	
	public Order<T> and(String... fields){
		return new Order<T>(wrapper, fields);
	}
	
	public Group<T> groupBy(String... fields){
		return new Group<>(wrapper, fields);
	}
	
	public Offset<T> offset(int offset){
		return new Offset<>(wrapper, offset);
	}
	
	public Limit<T> limit(int limit){
		return new Limit<>(wrapper, limit);
	}
	
	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}

	public T findSingle() {
		return new Executer<T>(wrapper).findSingle();
	}

	public boolean exists() {
		return new Executer<>(wrapper).exists();
	}
	
	public String getQueryString() {
		return new Executer<>(wrapper).getQueryString();
	}
	
}
