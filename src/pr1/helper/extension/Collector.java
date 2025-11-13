package pr1.helper.extension;

public class Collector<T extends Number> {

    private double sum = 0.0;

    public void add(T value) {
        if (value != null) {
            sum += value.doubleValue();
        }
    }

    public double getSum() {
        return sum;
    }

}
