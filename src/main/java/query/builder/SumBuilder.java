package query.builder;

import query.domain.StatementBuilder;
import query.domain.Wrapper;

public class SumBuilder implements StatementBuilder {
	
	private final Wrapper wrapper;
	
	public SumBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void build() {
		wrapper.getBuild()
			.append("Select sum(")
			.append(wrapper.getSelectedField())
			.append(") from ")
			.append(wrapper.getReflection().getEntityClassMapping());
	}

}
