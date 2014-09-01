package query.model;

import java.util.List;

import query.Executer;
import query.Order;
import query.Where;
import query.domain.Wrapper;
import query.model.Syntax.Operator;

public class Condition<T>  {

	private final Wrapper wrapper;
	
	public Condition(Wrapper wrapper, Parameter parameter) {
		wrapper.addParameter(parameter);
		this.wrapper = wrapper;
	}
	
	public Where<T> and(String field) {
		return new Where<T>(wrapper, Operator.AND, field);
	}

	public  Where<T> or(String field) {
		return new Where<T>(wrapper, Operator.OR, field);
	}

	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}

	public T findSingle() {
		return new Executer<T>(wrapper).findSingle();
	}

	public boolean exists() {
		return new Executer<T>(wrapper).exists();
	}

	public Order<T> orderBy(String... fields) {
		return new Order<>(wrapper, fields);
	}
	
	public Number count() {
		return new Executer(wrapper).count();
	}
	
	public Number sum(String field) {
		return new Executer(wrapper, field).sum();
	}
	
	public int update() {
		return new Executer(wrapper).update();
	}
	
	public String getQueryString() {
		return new Executer(wrapper).getQueryString();
	}
	
}
