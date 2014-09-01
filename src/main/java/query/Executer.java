package query;

import java.util.List;

import query.builder.ResultBuilder;
import query.domain.Wrapper;
import query.model.Metrics;
import query.model.Statement;

public class Executer<T> {

	private final Wrapper wrapper;
	private final Metrics metrics;
	
	public Executer (Wrapper wrapper) {
		this.wrapper = wrapper;
		this.metrics = wrapper.getMetrics();
	}
	
	public Executer (Wrapper wrapper, String field) {
		wrapper.setSelectedField(field);
		this.wrapper = wrapper;
		this.metrics = wrapper.getMetrics();
	}
	
	public List<T> find() {
		List<T> result = new ResultBuilder<T>(wrapper).build().list();
		metrics.end();
		return result;
	}

	public T findSingle() {
		T t = new ResultBuilder<T>(wrapper).build().single();
		metrics.end();
		return t;
	}

	public boolean exists() {
		boolean result =  new ResultBuilder<T>(wrapper).build().exists();
		metrics.end();
		return result;
	}

	public Number count() {
		wrapper.setStatement(Statement.COUNT);
		Number result = (Number) new ResultBuilder(wrapper).build().single();
		metrics.end();
		return result;
	}

	public Number sum() {
		wrapper.setStatement(Statement.SUM);
		Number result = (Number) new ResultBuilder(wrapper).build().single();
		metrics.end();
		return result;
	}
	
	public int delete() {
		wrapper.setStatement(Statement.DELETE);
		return new ResultBuilder(wrapper).build().getQuery().executeUpdate();
	}
	
	public int update() {
		wrapper.setStatement(Statement.UPDATE);
		return new ResultBuilder(wrapper).build().getQuery().executeUpdate();
	}
	
	public javax.persistence.Query getQuery() {
		return new ResultBuilder(wrapper).build().getQuery();
	}
	
	public List<Object[]> listObject(){
		return new ResultBuilder(wrapper).build().listObject();
	}
	
	public String getQueryString() {
		new ResultBuilder(wrapper).build();
		metrics.end();
		return wrapper.getBuild().toString();
	}
	
}
