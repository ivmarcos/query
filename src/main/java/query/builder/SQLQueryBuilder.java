package query.builder;

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.Query;

import query.Reflection;
import query.domain.Builder;
import query.domain.Wrapper;
import query.model.OrderParameter;
import query.model.Parameter;

public class SQLQueryBuilder implements Builder {
	
	private final Wrapper wrapper;
	private final Reflection reflection;
	
	public SQLQueryBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.reflection = wrapper.getReflection();
	}

	@Override
	public void appendField(Parameter parameter) {
		final String key = parameter.getField();
		wrapper.getBuild()
			.append(reflection.getMappedSQLName(key));
	}
	
	
	@Override
	public Query createInEntityManager() {
		String sql = wrapper.getBuild().toString();
		return wrapper.getEntityManager().createNativeQuery(sql);
	}

	@Override
	public void appendOrderFields() {
		Iterator<OrderParameter> itGroup = wrapper.getOrderParameters().iterator();
		while (itGroup.hasNext()) {
			OrderParameter orderParameter = itGroup.next();
			Iterator<String> itField = Arrays.asList(orderParameter.getFields()).iterator();
			while (itField.hasNext()) {
				String field = itField.next();
				wrapper.getBuild()
					.append(reflection.getMappedSQLName(field))
					.append(" ")
					.append(orderParameter.getSort().getSyntax());
				if (itField.hasNext() || itGroup.hasNext()) wrapper.getBuild().append(", ");
			}
		}
	}
	

}
