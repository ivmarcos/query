package query.builder;

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.Builder;
import query.domain.Wrapper;
import query.model.OrderParameter;
import query.model.Parameter;
import query.model.Type;

public class JPAQueryBuilder implements Builder{

	final static Logger logger = LoggerFactory.getLogger(JPAQueryBuilder.class);
	
	private final Wrapper wrapper;
	
	public JPAQueryBuilder(Wrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	@Override
	public void appendField(Parameter parameter) {
		wrapper.getBuild()
			.append(wrapper.getReflection().getEntityClassInitials())
			.append(".")
			.append(parameter.getField());
	}
	
	
	@Override
	public Query createInEntityManager() {
		logger.info(wrapper.getBuild().toString());
		if (wrapper.getType() == Type.NAMED_QUERY && wrapper.getParametersToAppend() == null) {
			return wrapper.getEntityManager().createNamedQuery(wrapper.getQueryString());
		}
		return wrapper.getEntityManager().createQuery(wrapper.getBuild().toString());
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
					.append(wrapper.getReflection().getEntityClassInitials()) 
					.append(".")
					.append(field)
					.append(" ")
					.append(orderParameter.getSort().getSyntax());
				if (itField.hasNext() || itGroup.hasNext()) wrapper.getBuild().append(", ");
			}
		}
	}
	
}
