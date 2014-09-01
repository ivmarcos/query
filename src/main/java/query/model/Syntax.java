package query.model;

import query.domain.ParameterProcessor;


public abstract class Syntax {
	
	public enum Comparator implements ParameterProcessor{
		IS("="),
		IS_NOT("<>"), 
		IS_NULL("is null"), 
		IS_NOT_NULL("is not null"), 
		IN("in"), 
		LOWER_THAN("<"), 
		GREATER_THAN(">"), 
		CONTAINS("like"){
			@Override
			public void process(Parameter parameter) {
				parameter.setValue("%" + parameter.getValue() + "%");
			}
		}, 
		STARTS_WITH("like"){
			@Override
			public void process(Parameter parameter) {
				parameter.setValue("%" + parameter.getValue());
			}
		},
		ENDS_WITH("like"){
			@Override
			public void process(Parameter parameter) {
				parameter.setValue(parameter.getValue() + "%");
			}
		};
		private String syntax;
		Comparator(String syntax){
			this.syntax=  syntax;
		}
		public String getSyntax() {
			return syntax;
		}
		@Override
		public void process(Parameter parameter) {
			return;
		}
	}
	
	public enum Operator{
		AND("and"), 
		OR("or");
		private String syntax;
		Operator(String syntax){
			this.syntax = syntax;
		}
		public String getSyntax() {
			return syntax;
		}
	}
	
}
