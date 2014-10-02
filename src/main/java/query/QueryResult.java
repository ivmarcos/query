package query;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.converter.Converter;
import query.domain.Result;
import query.domain.Wrapper;
import query.model.Statement;
import query.model.Type;

public class QueryResult<T> implements Result<T>{
	
	final Logger logger = LoggerFactory.getLogger(QueryResult.class);

	private final Wrapper wrapper;
	private final javax.persistence.Query query;
	
	public QueryResult(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.query = wrapper.getQuery();
	}
	
	@Override
	public List<T> list(){
		return getResultList();
	}
	
	@Override
	public List listObject() {
		return getResultList();
	}
	
	@Override
	public T single() {
		return (T) getSingleResult();
	}
	
	@Override
	public Object singleObject() {
		return getSingleResult();
	}
	
	@Override
	public boolean exists() {
		return getSingleResult() != null;
	}

	@Override
	public javax.persistence.Query getQuery() {
		return query;
	}
	
	private List getResultList() {
		logger.debug("{} Getting list result", wrapper);
		List result;
		try{
			result = query.getResultList();
			if (needsConvertion()) result = convert(result);
		}catch(NoResultException ne) {
			logger.info("{} No result.", wrapper);
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		wrapper.getCache().put(result);
		return result;
	}
	
	private Object getSingleResult() {
		logger.info("{} Getting single result", wrapper);
		Object result;
		try{
			query.setMaxResults(1);
			result = query.getSingleResult();
			if (needsConvertion()) result = convert(result);
		}catch(NoResultException ne) {
			logger.info("{} No result.", wrapper);
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		wrapper.getCache().put(result);
		return result;
	}
	
	private boolean needsConvertion() {
		return wrapper.getType() == Type.SQL_QUERY && !wrapper.getReflection().hasEntityAnnotation() && wrapper.getEntityClass() != Class.class && wrapper.getStatement() == Statement.SELECT;
	}
	
	private List convert(List result) {
		return new Converter(wrapper.getEntityClass()).convert(result);
	}
	
	private Object convert(Object result) {
		return new Converter(wrapper.getEntityClass()).convert((Object[]) result);
	}
	
}
