package query.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Metrics {
	
	final static Logger logger = LoggerFactory.getLogger(Metrics.class);

	private final Long start;
	private Long end;

	public Metrics () {
		start = System.currentTimeMillis();
	}
	
	public void end() {
		end = System.currentTimeMillis();
		logger.info("Query performance: {} ms", end-start);
	}
}
