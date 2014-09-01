package query.domain;

import query.model.Parameter;

public interface FieldProcessor {

	String process(Wrapper wrapper, Parameter parameter);
}
