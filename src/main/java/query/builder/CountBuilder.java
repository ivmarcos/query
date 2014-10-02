package query.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.StatementBuilder;
import query.domain.Wrapper;
import query.model.Type;

public class CountBuilder implements StatementBuilder {
	
	final static Logger logger = LoggerFactory.getLogger(CountBuilder.class);

	private final Wrapper wrapper;
	
	public CountBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void build() {
		if (wrapper.getQueryString() != null) {
			convert();
		}else {
			wrapper.getBuild()
				.append("Select count(*) from ")
				.append(wrapper.getReflection().getEntityClassMapping());
		}
	}
	
	private void convert() {
		final String query = getQuery();
		logger.debug("{} convert to count: {] ", wrapper, query);
		final int count = query.toLowerCase().indexOf("count");
		final int from = query.toLowerCase().indexOf("from");
		if (count < 0 || count > from) {
			wrapper.getBuild().setLength(0);
			wrapper.getBuild()
				.append("Select count(*) ")
				.append(query.substring(from, query.length()));
		}
	}
	
	private String getQuery() {
		if (mustExtractFromNamedQuery()) {
			return wrapper.getReflection().getQueryString(wrapper.getQueryString());
		}else {
			return wrapper.getQueryString();
		}
	}
	
	private boolean mustExtractFromNamedQuery() {
		return wrapper.getType() == Type.NAMED_QUERY;
	}
	
	
}
