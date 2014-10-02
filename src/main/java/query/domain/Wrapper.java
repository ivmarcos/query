package query.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import query.QueryCache;
import query.Reflection;
import query.builder.FetchBuilder;
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
	StatementBuilder getStatementBuilder();
	ParameterAppender getParameterAppender();
	Builder getBuilder();
	String[] getGroupByFields();
	Class<?> getEntityClass();
	Class<?>[] getFetchClasses();
	Class<?>[] getExceptionFetchClasses();
	String[] getFetchFields();
	String getSelectedField();
	StringBuilder getBuild();
	Type getType();
	Statement getStatement();
	Reflection getReflection();
	FetchBuilder getFetchBuilder();
	Sort getSort();
	String getQueryString();
	Query getQuery();
	Metrics getMetrics();
	QueryCache getCache();
	boolean enabled(Feature feature);
	boolean distinctSelect();
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
	void configure(Feature feature, boolean active);
	void setDistinctSelect(boolean distinctSelect);
	void setFetchClasses(Class<?>[] fetchClasses);
	void setExceptionFetchClasses(Class<?>[] exceptionFetchClasses);
	void setFetchFields(String[] fetchFields);
		
}
