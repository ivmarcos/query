package query;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import query.domain.Wrapper;
import query.model.Parameter;
import query.model.Sort;
import query.model.Syntax.Comparator;
import query.model.Syntax.Operator;

public class Parameters<T> {

	private final Wrapper wrapper;
	
	public Parameters(Wrapper wrapper, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			wrapper.addParameter(new Parameter(Operator.AND, Comparator.IS, entry.getKey(), entry.getValue()));
		}
		this.wrapper = wrapper;
	}
	
	public List<T> find() {
		return new Executer<T>(wrapper).find();
	}
	
	public Order<T> orderBy(String... fields){
		return new Order<T>(wrapper, fields);
	}
	
	public Order<T> orderBy(Sort sort, String... fields){
		return new Order<T>(wrapper, fields, sort);
	}
	
	public List<Object[]> listObject(){
		return new Executer<T>(wrapper).listObject();
	}
	
	public int update() {
		return new Executer<T>(wrapper).update();
	}
	
	public Number count() {
		return new Executer<T>(wrapper).count();
	}

	public Number sum(String field) {
		wrapper.setSelectedField(field);
		return new Executer<T>(wrapper).sum();
	}

	public Offset<T> offset(int offset){
		return new Offset<>(wrapper, offset);
	}
}
