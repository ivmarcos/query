package query.converter;

import java.util.Date;

import query.domain.FieldTypeConverter;

public enum FieldTypeConverterImpl implements FieldTypeConverter{
	SAME_CLASS{
		public Object convert(Object object) {
			return object;
		}
	},
	INTEGER_TO_BOOLEAN{
		public Object convert(Object object) {
			return ((Integer) object)==1;
		}
	},
	NUMBER_TO_LONG{
		public Object convert(Object object) {
			return ((Number) object).longValue();
		}
	},
	NUMBER_TO_INT{
		public Object convert(Object object) {
			return ((Number) object).intValue();
		}
	},
	BYTE_TO_BOOLEAN{
		public Object convert(Object object) {
			return ((Byte) object).intValue()==1;
		}
	},
	BIGINTEGER_TO_BOOLEAN{
		public Object convert(Object object) {
			return ((Number) object).intValue()==1;
		}
	},
	DATASQL_TO_DATE{
		public Object convert(Object object) {
			Date date = (Date) object;
			return date;
		}
	};
}

