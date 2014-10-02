package query.model;

public enum Feature {

	FETCH_JOINS(false),
	FETCH_ALL(false),
	FETCH_MANDATORY_JOINS(false),
	CACHE_MODE(false);
	Feature(boolean active){
		this.active = active;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	private boolean active;
	
	
}
