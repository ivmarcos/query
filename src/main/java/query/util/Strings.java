package query.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Strings {

	private static Map<String, String> accents;
	private static List<String> symbols;

	static {
		accents = new HashMap<String, String>();
		accents.put("á", "a");
		accents.put("à", "a");
		accents.put("â", "a");
		accents.put("ã", "a");
		accents.put("ç", "c");
		accents.put("é", "e");
		accents.put("è", "e");
		accents.put("ê", "e");
		accents.put("í", "i");
		accents.put("ì", "i");
		accents.put("î", "i");
		accents.put("ó", "o");
		accents.put("ò", "o");
		accents.put("ô", "o");
		accents.put("õ", "o");
		accents.put("ú", "u");
		accents.put("ù", "u");
		accents.put("û", "u");
		accents.put("Á", "A");
		accents.put("À", "A");
		accents.put("Â", "A");
		accents.put("Ã", "A");
		accents.put("É", "E");
		accents.put("È", "E");
		accents.put("Ê", "E");
		accents.put("Í", "I");
		accents.put("Ì", "I");
		accents.put("Î", "I");
		accents.put("Î", "I");
		accents.put("Ó", "O");
		accents.put("Ò", "O");
		accents.put("Ô", "O");
		accents.put("Õ", "O");
		accents.put("Ú", "U");
		accents.put("Ù", "U");
		accents.put("Û", "U");
		accents.put("Ç", "C");
		symbols = Arrays.asList("\\.", "º", "ª", "!", "[()]", "\\,", "\\;", "\\@", "\\#", "\\$", "\\¨", "\\&", "\\*", "\\?", "\\+", "\\-");
	}

	public static String collectionToString(Collection<?> collection) {
		StringBuilder result = new StringBuilder();
		for (Object o : collection) {
			if (result.length()>0) result.append(", ");
			result.append(o.toString());
		}
		return result.toString();
	}

	public static String simpleName(String name) {
		String[] names = name.split(" ");
		StringBuilder result = new StringBuilder();
		int i = 0;
		for(String n : names) {
			if (i>2) break;
			result.append(n + " ");
			if (i==1&&!isClause(n)) break;
			i++;
		}
		return result.toString();
	}

	public static String noSymbols(String word) {
		if (word==null) return "";
		String result = word;
		for (String symbol : symbols) {
			result = result.replaceAll(symbol, "");
		}
		return result;
	}

	public static String noAccents(String word) {
		if (word==null) return "";
		String result = word;
		for (Entry<String, String> p : accents.entrySet()){
			result = result.replace(p.getKey(), p.getValue());
		}
		return result.toString();
	}

	public static String toUrl(String value) {
		String result = value.toLowerCase().trim();
		result = noAccents(result);
		result = noSymbols(result);
		result = result.replace(" - ", "-");
		result = result.replace(" ", "-");
		return result;
	}

	public static String capitalizeFirst(String value) {
		return value.substring(0, 1).toUpperCase()+value.substring(1, value.length()).toLowerCase();
	}

	public static String capitalize(String value) {
		StringBuilder result = new StringBuilder();  
		String[] names = value.toLowerCase().split("\\s+|\\(|\\)");  
		for(String n : names){  
			if (!isClause(n)) {
				result.append(" "+ n.replaceFirst(n.substring(0, 1), n.substring(0, 1).toUpperCase()));  
			}else {
				result.append(" "+ n);
			}
		}  
		return result.toString().trim();  
	}

	public static String capitalizeSimpleName(String value) {
		StringBuilder result = new StringBuilder();  
		String[] names = value.toLowerCase().split("\\s+|\\(|\\)");  
		int i = 0;
		for(String n : names){  
			if (i>2) break;
			if (!isClause(n)) {
				result.append(" "+ n.replaceFirst(n.substring(0, 1), n.substring(0, 1).toUpperCase()));  
				if (i==1) break;
			}else {
				result.append(" "+ n);
			}
			i++;
		}  
		return result.toString().trim();  
	}

	public static boolean isClause(String value) {
		if (value.toUpperCase().equals("E")) return true; 
		if (value.length()<=3) {
			return value.substring(0, 1).toUpperCase().equals("D");
		}
		return false;
	}

	public static String dotString(String value, int maxLength) {
		StringBuilder result = new StringBuilder();
		if (value.length()>maxLength) {
			result.append(value.substring(0, maxLength));
			result.append("...");		
		}else {
			result.append(value);
		}
		return result.toString();
	}

	public static String formatSize(Long bytes) { 
		final boolean si = true;
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "KMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	public static String wrap(int max, String value) {
		String result = new String(value);
		if (value.length()>max) {
			int index = value.indexOf(" ", max);
			if (index > -1) {
				result = value.substring(0, index) + "\r" + value.substring(index, value.length());
			}
		}
		return result;
	}

}
