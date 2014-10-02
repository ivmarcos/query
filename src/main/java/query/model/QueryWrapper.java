package query.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import query.DefaultParameterAppender;
import query.QueryCache;
import query.Reflection;
import query.builder.FetchBuilder;
import query.domain.Builder;
import query.domain.ParameterAppender;
import query.domain.StatementBuilder;
import query.domain.Wrapper;

public class QueryWrapper implements Wrapper { 

	private static int id;
	
	private final EntityManager entityManager;
	private List<Parameter> parameters;
	private List<Parameter> parametersToAppend;
	private List<OrderParameter> orderParameters;
	private StatementBuilder statementBuilder;
	private ParameterAppender parameterAppender;
	private Builder builder;
	private Class<?> entityClass;
	private Class<?>[] fetchClasses;
	private Class<?>[] exceptionFetchClasses;
	private String[] fetchFields;
	private String selectedField;
	private String[] groupByFields;
	private Type type;
	private Statement statement;
	private StringBuilder build;
	private Reflection reflection;
	private Sort sort;
	private FetchBuilder fetchBuilder;
	private Query query;
	private Metrics metrics;
	private String queryString;
	private Map<Feature, Boolean> featuresMap;
	private int offset;
	private int limit;
	private QueryCache cache;
	private boolean distinctSelect;
	
	public QueryWrapper (EntityManager entityManager) {
		this.entityManager = entityManager;
		init();
	}

	public QueryWrapper (EntityManager entityManager, Class<?> entityClass) {
		this.entityManager = entityManager;
		this.entityClass = entityClass;
		init();
	}

	private void init() {
		id++;
		build = new StringBuilder();
		type = Type.JPA_QUERY; 
		statement = Statement.SELECT;
		sort = Sort.ASCENDING;
		loadFeatures();
		metrics = new Metrics(this);
	}
	
	private void loadFeatures() {
		featuresMap = new HashMap<Feature, Boolean>();
		for (Feature feature : Feature.values()) {
			featuresMap.put(feature, feature.isActive());
		}
	}
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	@Override
	public Class<?> getEntityClass() {
		return entityClass;
	}

	@Override
	public String getSelectedField() {
		return selectedField;
	}

	@Override
	public void addParameter(Parameter parameter) {
		if (parameters == null) {
			parameters = new ArrayList<Parameter>();
		}
		parameters.add(parameter);
	}

	@Override
	public void setSelectedField(String selectedField) {
		this.selectedField = selectedField;
	}

	@Override
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public Statement getStatement() {
		return statement;
	}

	@Override
	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public StringBuilder getBuild() {
		return build;
	}

	@Override
	public Reflection getReflection() {
		if (reflection==null) {
			reflection = new Reflection(entityClass);
		}
		return reflection;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	@Override
	public Query getQuery() {
		return query;
	}

	@Override
	public void setQuery(Query query) {
		this.query = query;
	}

	@Override
	public Metrics getMetrics() {
		return metrics;
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	@Override
	public boolean enabled(Feature feature) {
		return featuresMap.get(feature);
	}

	@Override
	public List<Parameter> getParametersToAppend() {
		return parametersToAppend;
	}
	
	@Override
	public void setParametersToAppend(List<Parameter> parametersToAppend) {
		this.parametersToAppend = parametersToAppend;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public List<OrderParameter> getOrderParameters() {
		return orderParameters;
	}

	@Override
	public void addOrderParameter(OrderParameter orderParameter) {
		if (orderParameters == null) {
			orderParameters = new ArrayList<OrderParameter>();
		}
		orderParameters.add(orderParameter);
	}

	@Override
	public String[] getGroupByFields() {
		return groupByFields;
	}

	@Override
	public void setGroupByFields(String[] fields) {
		this.groupByFields = fields;
	}

	@Override
	public QueryCache getCache() {
		if (cache==null) {
			cache = new QueryCache(this);
		}
		return cache;
	}

	@Override
	public void configure(Feature feature, boolean active) {
		featuresMap.put(feature, active);
	}

	public boolean distinctSelect() {
		return distinctSelect;
	}

	@Override
	public void setDistinctSelect(boolean distinctSelect) {
		this.distinctSelect = distinctSelect;
	}

	@Override
	public FetchBuilder getFetchBuilder() {
		if (fetchBuilder == null) {
			fetchBuilder = new FetchBuilder(this);
		}
		return fetchBuilder;
	}

	@Override
	public Class<?>[] getFetchClasses() {
		return fetchClasses;
	}

	@Override
	public void setFetchClasses(Class<?>[] fetchClasses) {
		this.fetchClasses = fetchClasses;
	}

	@Override
	public String[] getFetchFields() {
		return fetchFields;
	}

	@Override
	public void setFetchFields(String[] fetchFields) {
		this.fetchFields = fetchFields;
	}

	@Override
	public Class<?>[] getExceptionFetchClasses() {
		return exceptionFetchClasses;
	}

	@Override
	public void setExceptionFetchClasses(Class<?>[] exceptionFetchClasses) {
		this.exceptionFetchClasses = exceptionFetchClasses;
	}
	
	@Override
	public Builder getBuilder() {
		if (builder == null) {
			builder = type.createBuilder(this);
		}
		return builder;
	}

	@Override
	public ParameterAppender getParameterAppender() {
		if (parameterAppender == null) {
			parameterAppender = new DefaultParameterAppender(this);
		}
		return parameterAppender;
	}

	@Override
	public StatementBuilder getStatementBuilder() {
		if (statementBuilder == null) {
			statementBuilder = statement.createStatementBuilder(this);
		}
		return statementBuilder;
	}

	public String toString() {
		return "QB" + id;
	}
}
