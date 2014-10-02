package query.builder;

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;
import query.model.Parameter;
import query.model.Statement;
import query.util.Strings;

public class QueryBuilder<T> {

	final static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
	
	private final Wrapper wrapper;
	
	public QueryBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public void build() {
		buildQueryString();
		logger.info("{} Build: {}", wrapper, wrapper.getBuild().toString());
		buildQueryEntityManager();
	}
	
	public void buildQueryString() {
		parseType();
		buildStatement();
		parseParameters();
		buildParameters();
		buildOrder();
		buildGroupBy();
	}
	
	private void buildQueryEntityManager() {
		createInEntityManager();
		limitValues();
		putParameters();		
	}
	
	private void parseType() {
		new TypeParser(wrapper).parse();
	}
	
	private void parseParameters() {
		new ParameterParser(wrapper).parse();
	}
	
	private void buildStatement() {
		wrapper.getStatementBuilder().build();
	}
	
	private void createInEntityManager() {
		Query query = wrapper.getBuilder().createInEntityManager();
		wrapper.setQuery(query);
	}
	
	private void buildParameters() {
		wrapper.getParameterAppender().append();
	}
	
	private void buildOrder() {
		if (containsOrderFields()) {
			wrapper.getBuild().append(" order by ");
			wrapper.getBuilder().appendOrderFields();
		}
	}

	private void buildGroupBy() {
		if (containsGroupByFields()) {
			wrapper.getBuild().append(" group by ");
			Iterator<String> it = Arrays.asList(wrapper.getGroupByFields()).iterator();
			while (it.hasNext()) {
				String field = it.next();
				wrapper.getBuild().append(field);
				if (it.hasNext()) wrapper.getBuild().append(", ");
			}
		}
	}
	
	private void limitValues() {
		if (wrapper.getLimit() > 0 ) {
			logger.debug("{} Offset value: {} Limit value: {}", wrapper, wrapper.getOffset(), wrapper.getLimit());
			wrapper.getQuery().setFirstResult(wrapper.getOffset());
			wrapper.getQuery().setMaxResults(wrapper.getLimit());
		}
	}
	
	private void putParameters() {
		if (wrapper.getParameters() == null) return;
		for (Parameter parameter : wrapper.getParameters()) {
			if (!parameter.valueAttachable()) continue;
			wrapper.getQuery().setParameter(removeSymbols(parameter.getField()), parameter.getValue());
		}
		logger.info("{} Parameters inserted: {}", wrapper, wrapper.getParameters().toString());
	}
	
	
	
	private boolean containsOrderFields() {
		return wrapper.getOrderParameters() != null && wrapper.getStatement() == Statement.SELECT;
	}
	
	private boolean containsGroupByFields() {
		return wrapper.getGroupByFields() != null;
	}
	
	
	private String removeSymbols(String value) {
		return Strings.noSymbols(value);
	}
}
