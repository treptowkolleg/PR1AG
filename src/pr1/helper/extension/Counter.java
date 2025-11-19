package pr1.helper.extension;

public class Counter {
	private int counter;

	public Counter() {
		counter = 1;
	}

	public Counter(int startValue) {
		counter = startValue;
	}

	public int get() {
		return counter;
	}

	public void set(int startValue) {
		counter = (startValue > 0) ? startValue : 1;
	}

	public void reset() {
		this.counter = 1;
	}

	public void increment() {
		counter++;
	}

	public void decrement() {
		if (counter > 0) {
			counter--;
		}
	}

}
