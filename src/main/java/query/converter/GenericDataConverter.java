package query.converter;


public class GenericDataConverter{
	/*
	private Class<?> clazz;
	private List<Object[]> list;
	
	private void init(Class<?> clazz, List<Object[]> list) {
		this.clazz = clazz;
		this.list = list;
	}

	@Override
	public List convert(List<Object[]> list)  {
		init(clazz, list);
		List result = new ArrayList();
		final int size = list.get(0).length + 1;
		try {
			for (Object[] data : list) {
				Data queryData = new Data(size);
				for (Object o : data) {
					queryData.setValue(convert(o));
				}
				result.add(queryData);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			return result;
		}
	}

	
	private Object convert(Object object) {
		if (object==null) return null;
		if (object.getClass()==BigInteger.class) {
			return ((BigInteger) object).longValue();
		}else if (object.getClass()==Short.class) {
			return ((Short) object).intValue();
		}else if (object.getClass()==java.sql.Date.class){
			Date date = (Date) object;
			return date;
		}
		return object;
	}*/
	
}
