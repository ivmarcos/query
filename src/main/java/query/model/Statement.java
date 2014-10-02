package query.model;

import query.builder.CountBuilder;
import query.builder.DeleteBuilder;
import query.builder.SelectBuilder;
import query.builder.SumBuilder;
import query.builder.UpdateBuilder;
import query.domain.StatementBuilder;
import query.domain.StatementBuilderFactory;
import query.domain.Wrapper;

public enum Statement implements StatementBuilderFactory{

	SELECT{
		@Override
		public StatementBuilder createStatementBuilder(Wrapper wrapper) {
			return new SelectBuilder(wrapper);
		}
	},
	DELETE{
		@Override
		public StatementBuilder createStatementBuilder(Wrapper wrapper) {
			return new DeleteBuilder(wrapper);
		}
	},
	UPDATE{
		@Override
		public StatementBuilder createStatementBuilder(Wrapper wrapper) {
			return new UpdateBuilder(wrapper);
		}
	},
	SUM{
		@Override
		public StatementBuilder createStatementBuilder(Wrapper wrapper) {
			return new SumBuilder(wrapper);
		}
	},
	COUNT{
		@Override
		public StatementBuilder createStatementBuilder(Wrapper wrapper) {
			return new CountBuilder(wrapper);
		}
	}
	
}

