package query.builder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Result;
import query.domain.Wrapper;
import query.impl.CacheResult;
import query.impl.QueryResult;

public class ResultBuilder<T> {

	final static Logger logger = LoggerFactory.getLogger(ResultBuilder.class);
	
	private final Wrapper wrapper;
	
	public ResultBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public Result<T> build() {
		if (wrapper.getCache().inCache()) {
			return new CacheResult<T>(wrapper);
		}
		buildQuery();
		return new QueryResult<T>(wrapper);
	}
	
	private void buildQuery() {
		QueryBuilder<T> builder = new QueryBuilder<T>(wrapper);
		builder.build();
	}
	
}
