package query.model;

public class QueryException extends RuntimeException{

	private String message;

	public QueryException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
