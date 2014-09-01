package query.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import query.QueryCache;
import query.Reflection;
import query.model.Feature;
import query.model.Metrics;
import query.model.OrderParameter;
import query.model.Parameter;
import query.model.Sort;
import query.model.Statement;
import query.model.Type;

public interface Wrapper {

	EntityManager getEntityManager();
	List<Parameter> getParameters();
	List<Parameter> getParametersToAppend();
	List<OrderParameter> getOrderParameters();
	String[] getGroupByFields();
	Class<?> getEntityClass();
	String getSelectedField();
	StringBuilder getBuild();
	Type getType();
	Statement getStatement();
	Reflection getReflection();
	Sort getSort();
	String getQueryString();
	Query getQuery();
	Metrics getMetrics();
	QueryCache getCache();
	boolean isActive(Feature feature);
	int getLimit();
	int getOffset();
	void setEntityClass(Class<?> entityClass);
	void addParameter(Parameter parameter);
	void setSelectedField(String selectedField);
	void setSort(Sort sort);
	void setQuery(Query query);
	void setQueryString(String queryString);
	void setType(Type type); 
	void setParametersToAppend(List<Parameter> parametersToAppend);
	void setLimit(int limit);
	void setOffset(int offset);
	void setStatement(Statement statement);
	void addOrderParameter(OrderParameter orderParameter);
	void setGroupByFields(String[] fields);
	void setFeature(Feature feature, boolean active);
	
		
}
