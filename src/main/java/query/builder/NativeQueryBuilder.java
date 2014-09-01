package query.builder;

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.Query;

import query.domain.Builder;
import query.domain.Wrapper;
import query.impl.Reflection;
import query.model.OrderParameter;
import query.model.Parameter;
import query.model.Statement;

public class NativeQueryBuilder implements Builder{
	
	private final Wrapper wrapper;
	private final Reflection reflection;
	
	public NativeQueryBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
		this.reflection = wrapper.getReflection();
	}

	@Override
	public void appendField(Parameter parameter) {
		final String key = parameter.getField();
		wrapper.getBuild()
			.append(reflection.getTableName(key))
			.append(".")
			.append(reflection.getColumnName(key))
			.append(reflection.getComparator(key))
			.append(":")
			.append(key.replace(".", ""));
	}
	
	
	@Override
	public Query createInEntityManager() {
		if (wrapper.getStatement() == Statement.COUNT) {
			return wrapper.getEntityManager().createNativeQuery(wrapper.getBuild().toString());
		}else{
			return wrapper.getEntityManager().createNativeQuery(wrapper.getBuild().toString(), wrapper.getEntityClass());
		}
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
					.append(reflection.getTableName(field))
					.append(".")
					.append(reflection.getColumnName(field))
					.append(" ")
					.append(orderParameter.getSort().getSyntax());
				if (itField.hasNext() || itGroup.hasNext()) wrapper.getBuild().append(", ");
			}
		}
		
	}
	
}
