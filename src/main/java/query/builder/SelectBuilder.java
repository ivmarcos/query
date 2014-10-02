package query.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.Reflection;
import query.domain.StatementBuilder;
import query.domain.Wrapper;
import query.model.Type;

public class SelectBuilder implements StatementBuilder {
	
	final static Logger logger = LoggerFactory.getLogger(SelectBuilder.class);
	
	private final Wrapper wrapper;
	private final StringBuilder build;
	private final Reflection reflection;

	public SelectBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
		build = wrapper.getBuild();
		reflection = wrapper.getReflection();
	}

	@Override
	public void build() {
		if (wrapper.getQueryString() != null) {
			logger.debug("{} Building by query", wrapper);
			buildByQuery();
		}else {
			logger.debug("{} Building by statement", wrapper);
			buildByStatement();
		}
	}
	
	private void buildByQuery() {
		if (mustExtractFromNamedQuery()) {
			logger.info("{} Named query: {}", wrapper, wrapper.getQueryString());
			wrapper.getBuild().append(wrapper.getReflection().getQueryString(wrapper.getQueryString()));
		}else {
			wrapper.getBuild().append(wrapper.getQueryString());
		}
	}
	
	private boolean mustExtractFromNamedQuery() {
		return wrapper.getType() == Type.NAMED_QUERY && (wrapper.getParameters() != null || wrapper.getOrderParameters() != null);
	}
	
	private void buildByStatement() {
		build
			.append("Select ")
			.append(wrapper.distinctSelect() ? "distinct " : "")
			.append(reflection.getEntityClassInitials())
			.append(wrapper.getSelectedField() != null ? "." + wrapper.getSelectedField() : "")
			.append(" from ")
			.append(reflection.getEntityClassMapping());
		fetchJoins();
	}
	
	private void fetchJoins() {
		wrapper.getFetchBuilder().build();
	}
}
