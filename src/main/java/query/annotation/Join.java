package query.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Join {

	JoinType value() default JoinType.INNER;
	
	public enum JoinType{
		LEFT, RIGHT, INNER
	}
}
