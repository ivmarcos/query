package query.domain;

public interface StatementBuilderFactory {

	StatementBuilder createStatementBuilder(Wrapper wrapper);
}
