package query;

import java.util.List;

import query.builder.ResultBuilder;
import query.domain.Wrapper;
import query.model.Metrics;
import query.model.Statement;
import query.model.Type;

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
		T result = new ResultBuilder<T>(wrapper).build().single();
		metrics.end();
		return result;
	}

	public boolean exists() {
		boolean result =  new ResultBuilder<T>(wrapper).build().exists();
		metrics.end();
		return result;
	}

	public Number count() {
		wrapper.setStatement(Statement.COUNT);
		Number result = (Number) new ResultBuilder(wrapper).build().singleObject();
		metrics.end();
		return result;
	}

	public Number sum() {
		wrapper.setStatement(Statement.SUM);
		Number result = (Number) new ResultBuilder(wrapper).build().singleObject();
		metrics.end();
		return result;
	}
	
	public int delete() {
		wrapper.setStatement(Statement.DELETE);
		javax.persistence.Query query = new ResultBuilder(wrapper).build().getQuery();
		int result = query.executeUpdate();
		metrics.end();
		return result;
	}
	
	public int update() {
		wrapper.setStatement(Statement.UPDATE);
		javax.persistence.Query query = new ResultBuilder(wrapper).build().getQuery();
		int result = query.executeUpdate();
		metrics.end();
		return result;
	}
	
	public javax.persistence.Query getQuery() {
		return new ResultBuilder(wrapper).build().getQuery();
	}
	
	public List listObject(){
		wrapper.setType(Type.SQL_QUERY);
		return new ResultBuilder(wrapper).build().listObject();
	}
	
	public Object singleObject() {
		return new ResultBuilder(wrapper).build().singleObject();
	}
	
	public String getQueryString() {
		new ResultBuilder(wrapper).buildQueryString();
		metrics.end();
		return wrapper.getBuild().toString();
	}
	
}
