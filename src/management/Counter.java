package management;

/**
 * A simple class that is used for counting things.
 */
public class Counter {

    private int count;

    /**
     * Creates a new counter.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Add number to current count.
     * @param number The number to add.
     */
    public void increase(int number) {
        if (count >= 0) {
            this.count += number;
        }
    }

    /**
     * subtract number from current count.
     * @param number The number to subtract.
     */
    public void decrease(int number) {
        if (count >= 0) {
            this.count -= number;
        }
    }

    /**
     * Get current count.
     * @return Current count.
     */
    public int getValue() {
        return this.count;
    }

    /**
     * Set value.
     * @param cnt New count.
     */
    public void setValue(int cnt) {
        this.count = cnt;
    }
}
