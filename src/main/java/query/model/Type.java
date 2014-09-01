package query.model;

import query.builder.JPAQueryBuilder;
import query.builder.NativeQueryBuilder;
import query.builder.SQLQueryBuilder;
import query.domain.Builder;
import query.domain.BuilderFactory;
import query.domain.Wrapper;


public enum Type implements BuilderFactory{
	JPA_QUERY,
	NAMED_QUERY, 
	NATIVE_QUERY{
		@Override
		public Builder createBuilder(Wrapper wrapper) {
			return new NativeQueryBuilder(wrapper);
		}
	}, 
	SQL_QUERY{
		@Override
		public Builder createBuilder(Wrapper wrapper) {
			return new SQLQueryBuilder(wrapper);
		}
	};

	@Override
	public Builder createBuilder(Wrapper wrapper) {
		return new JPAQueryBuilder(wrapper);
	}

	
}
