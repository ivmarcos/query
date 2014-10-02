package query.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import query.domain.DataConverter;

public class StringDataConverter<T> implements DataConverter<String[]> {
		
		final static Logger logger = LoggerFactory.getLogger(StringDataConverter.class);
		
		@Override
		public List<String[]> convert(List<Object[]> list){
			List<String[]> result = new ArrayList<String[]>();
			Long start = System.currentTimeMillis();
			int size = list.get(0).length;
			for (Object[] arrayObject : list) {
				String[] string = new String[size];
				int i = 0;
				for (Object object : arrayObject) {
					string[i++] = convertToString(object);
				};
				result.add(string);
			}
			Long end = System.currentTimeMillis();
			logger.info("Converted {} rows to a String[] list in {} miliseconds", list.size(), end-start);
			return result;
		}
		
		
		private String convertToString(Object object) {
			if (object==null) return "";
			if (object.getClass()==java.sql.Timestamp.class) {
				Date date = (Date) object;
				return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
			}
			if (object.getClass()==java.sql.Date.class){
				Date date = (Date) object;
				return new SimpleDateFormat("dd/MM/yyyy").format(date);
			}
			if (object.getClass()==BigDecimal.class) {
				Locale pt = new Locale("pt", "BR");
				NumberFormat format = NumberFormat.getInstance(pt);
				return format.format(object);
			}
			return object.toString();
		}


		@Override
		public String[] convert(Object[] data) {
			String[] result = new String[data.length];
			int i = 0;
			for (Object object : data) {
				result[i++] = convertToString(object);
			};
			return result;
		}


}
