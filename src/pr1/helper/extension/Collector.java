package pr1.helper.extension;

public class Collector<T extends Number> {
	private Double sum = 0.0;

	public void add(T value) {
		if (null == value) {
			return;
		}
		sum += value instanceof Double ? value.doubleValue() : value.intValue();
	}

	public void set(T value) {
		if (null == value) {
			return;
		}
		sum = value instanceof Double ? value.doubleValue() : value.intValue();
	}

	public double getSum() {
		return sum.doubleValue();
	}

	public int getSumAsInteger() {
		return sum.intValue();
	}
	
}
