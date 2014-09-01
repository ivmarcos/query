package query.builder;

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.Parser;
import query.domain.Builder;
import query.domain.Wrapper;
import query.model.Parameter;
import query.model.Statement;
import query.util.Strings;

public class QueryBuilder<T> {

	final static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
	
	private final Wrapper wrapper;
	private Builder typeBuilder;
	private StringBuilder build;
	
	public QueryBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.build = wrapper.getBuild();
		this.typeBuilder = wrapper.getType().createBuilder(wrapper);
	}
	
	public void build() {
		parse();
		buildStatement();
		buildParameters();
		buildOrder();
		buildGroupBy();
		logger.info("Build {}", wrapper.getBuild().toString());
		/*buildInEntityManager();
		limitValues();
		putParameters();*/		
	}
	
	private void parse() {
		new Parser(wrapper).parse();
		logger.info("Query type: {}", wrapper.getType());
	}
	
	private void buildStatement() {
		wrapper.getStatement().createStatementBuilder(wrapper).build();
	}
	
	private void buildInEntityManager() {
		Query query = typeBuilder.createInEntityManager();
		wrapper.setQuery(query);
		
	}
	private void buildParameters() {
		if (wrapper.getParametersToAppend() == null) return;
		boolean containsWhere = containsWhere();
		for (Parameter parameter : wrapper.getParametersToAppend()) {
			if (!containsWhere) {
				build.append(" where ");
				containsWhere = true;
			}else {
				build
					.append(" ")
					.append(parameter.getOperator().getSyntax())
					.append(" ");
			}
			logger.info(parameter.getField());
			typeBuilder.appendField(parameter);
			appendComparator(parameter);
			appendParameter(parameter);
			parameter.getComparator().process(parameter);
		}
	}
	
	private void buildOrder() {
		if (containsOrderFields()) {
			build.append(" order by ");
			typeBuilder.appendOrderFields();
		}
	}

	private void buildGroupBy() {
		if (containsGroupByFields()) {
			build.append(" group by ");
			Iterator<String> it = Arrays.asList(wrapper.getGroupByFields()).iterator();
			while (it.hasNext()) {
				String field = it.next();
				build.append(field);
				if (it.hasNext()) build.append(", ");
			}
		}
		
	}
	
	private void limitValues() {
		if (wrapper.getLimit() > 0 ) {
			logger.info("Offset value: {} Limit value: {}", wrapper.getOffset(), wrapper.getLimit());
			wrapper.getQuery().setFirstResult(wrapper.getOffset());
			wrapper.getQuery().setMaxResults(wrapper.getLimit());
		}
	}
	
	private void putParameters() {
		if (wrapper.getParameters() == null) return;
		for (Parameter parameter : wrapper.getParameters()) {
			wrapper.getQuery().setParameter(removeSymbols(parameter.getField()), parameter.getValue());
		}
		logger.info("Parameters inserted: {}", wrapper.getParameters().toString());
	}
	
	private boolean containsWhere() {
		return build.toString().toLowerCase().contains("where");
	}
	
	private boolean containsOrderFields() {
		return wrapper.getOrderParameters() != null && wrapper.getStatement() == Statement.SELECT;
	}
	
	private boolean containsGroupByFields() {
		return wrapper.getGroupByFields() != null;
	}
	
	private void appendComparator(Parameter parameter) {
		build
			.append(" ")
			.append(parameter.getComparator().getSyntax())
			.append(" ");
	}
	
	private void appendParameter(Parameter parameter) {
		String key = parameter.getValue()==null? "" : ":" + parameter.getField().replace(".", "");
		build.append(key);
	}
	
	private String removeSymbols(String value) {
		return Strings.noSymbols(value);
	}
}
