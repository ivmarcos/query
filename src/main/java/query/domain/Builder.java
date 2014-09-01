package query.domain;

import javax.persistence.Query;

import query.model.Parameter;

public interface Builder {

	void appendField(Parameter parameter);
	Query createInEntityManager();
	void appendOrderFields();
}
