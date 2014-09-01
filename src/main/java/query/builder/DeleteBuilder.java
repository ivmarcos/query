package query.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.StatementBuilder;
import query.domain.Wrapper;

public class DeleteBuilder implements StatementBuilder {
	
	final static Logger logger = LoggerFactory.getLogger(DeleteBuilder.class);
	
	private final Wrapper wrapper;

	public DeleteBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void build() {
		wrapper.getBuild().append("Delete from ").append(wrapper.getReflection().getEntityClassMapping());
	}
	
}
