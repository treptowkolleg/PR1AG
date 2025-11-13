package pr1.helper.extension;

public class Counter {

    private int counter = 1;

    public int get() {
        return counter;
    }

    public void set(int counter) {
        if (counter > 0) {
            this.counter = counter;
        } else {
            this.counter = 1;
        }
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
