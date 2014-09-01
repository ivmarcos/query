package query.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Mapped {

	String sql() default "";
	String jpql() default "";
	String label() default "";
}
