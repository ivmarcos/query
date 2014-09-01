package query.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.StatementBuilder;
import query.domain.Wrapper;

public class CountBuilder implements StatementBuilder {
	
	final static Logger logger = LoggerFactory.getLogger(CountBuilder.class);

	private final Wrapper wrapper;
	private StringBuilder build;
	
	public CountBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.build = wrapper.getBuild();
	}

	@Override
	public void build() {
		if (wrapper.getQueryString() != null) {
			convert();
		}else {
			build
				.append("Select count(*) from ")
				.append(wrapper.getReflection().getEntityClassMapping());
		}
	}
	
	private void convert() {
		final String buildString = build.toString();
		final int indexCount = buildString.toLowerCase().indexOf("count");
		final int indexFrom = buildString.toLowerCase().indexOf("from");
		if (indexCount<0 || indexCount>indexFrom) {
			build = new StringBuilder("Select count(*) ").append(buildString.substring(indexFrom, buildString.length()));
		}
		logger.info("Count query converted: {}",wrapper.getBuild().toString());
	}
	
}
