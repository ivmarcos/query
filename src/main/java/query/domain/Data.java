package query.domain;


public class Data {
	
	public Data(int size) {
		data = new Object[size];
	}

	private Object[] data;
	private int index = 0; 
		
	public Object getValue(int posicao) {
		return data[posicao];
	}

	public void setValue(Object value) {
		index++;
		data[index] = value;
	}
	
	public int size() {
		return data.length;
	}

	public Object[] getData() {
		return data;
	}


}
