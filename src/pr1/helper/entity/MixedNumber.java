package pr1.helper.entity;

public class MixedNumber {
	private final double amount;
	private final int precision;
	public MixedNumber(double amount) {
		this.amount = amount;
		this.precision = 0;
	}

	public MixedNumber(double amount, int precision) {
		this.amount = amount;
		this.precision = precision;
	}

	public double getAmount() {
		return amount;
	}

	public int getPrecision() {
		return precision;
	}
}
