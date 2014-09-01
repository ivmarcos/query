package query.model;

public enum Sort {

	ASCENDING("asc"),
	DESCENDING("desc");
	private String syntax;
	Sort(String syntax){
		this.syntax = syntax;
	}
	public String getSyntax() {
		return syntax;
	}
}
