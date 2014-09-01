package query.domain;

import java.util.List;

public interface Result<T> {

	public List<T> list();
	
	public List listObject();
	
	public T single();
	
	public boolean exists();

	public javax.persistence.Query getQuery();
}
