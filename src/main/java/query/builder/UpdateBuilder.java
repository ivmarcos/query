package query.builder;

import org.hibernate.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.StatementBuilder;
import query.domain.Wrapper;
import query.model.Type;

public class UpdateBuilder implements StatementBuilder {
	
	final static Logger logger = LoggerFactory.getLogger(UpdateBuilder.class);
	
	private final Wrapper wrapper;

	public UpdateBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void build() {
		if (wrapper.getQueryString() != null) {
			logger.info("Building by query..");
			buildByQuery();
		}else {
			throw new QueryException("No query was informed");
		}
	}
	
	private void buildByQuery() {
		if (mustExtractFromNamedQuery()) {
			logger.info("Getting named query");
			wrapper.getBuild().append(wrapper.getReflection().getQueryString(wrapper.getQueryString()));
		}else {
			wrapper.getBuild().append(wrapper.getQueryString());
		}
	}
	
	private boolean mustExtractFromNamedQuery() {
		return wrapper.getType() == Type.NAMED_QUERY && (wrapper.getParameters() != null || wrapper.getOrderParameters() != null);
	}
	
}
