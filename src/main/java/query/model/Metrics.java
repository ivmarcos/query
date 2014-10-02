package query.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Wrapper;

public class Metrics {
	
	final static Logger logger = LoggerFactory.getLogger(Metrics.class);

	private final Wrapper wrapper;
	private final Long start;
	private Long end;

	public Metrics (Wrapper wrapper) {
		this.wrapper = wrapper;
		start = System.currentTimeMillis();
	}
	
	public void end() {
		end = System.currentTimeMillis();
		logger.info("{} performance: {} ms", wrapper, end-start);
	}
}
