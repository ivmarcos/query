package query;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import query.domain.Wrapper;
import query.model.Parameter;
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
	
	public List<Object[]> listObject(){
		return new Executer<T>(wrapper).listObject();
	}
}
