package query.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import query.QueryCache;
import query.Reflection;
import query.domain.Wrapper;

public class QueryWrapper implements Wrapper { 

	private final EntityManager entityManager;
	private List<Parameter> parameters;
	private List<Parameter> parametersToAppend;
	private List<OrderParameter> orderParameters;
	private Class<?> entityClass;
	private String selectedField;
	private String[] groupByFields;
	private Type type;
	private Statement statement;
	private StringBuilder build;
	private Reflection reflection;
	private Sort sort;
	private Query query;
	private Metrics metrics;
	private String queryString;
	private Map<Feature, Boolean> featuresMap;
	private int offset;
	private int limit;
	private QueryCache cache;
	
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
		metrics = new Metrics();
		loadFeatures();
		type = Type.JPA_QUERY; 
		statement = Statement.SELECT;
		sort = Sort.ASCENDING;
		build = new StringBuilder();
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
	public boolean isActive(Feature feature) {
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
	public void setFeature(Feature feature, boolean active) {
		featuresMap.put(feature, active);
	}

	
}
