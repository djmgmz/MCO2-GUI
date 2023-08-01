package project.ccprog3mco2gui.model;

/**
 * Represents a denomination of currency.
 */
public class Denomination {
    private int value;
    private int count;

    /**
     * Constructs a Denomination object with the specified value and count.
     *
     * @param value the value of the denomination
     * @param count the count of the denomination
     */
    public Denomination(int value, int count) {
        this.value = value;
        this.count = count;
    }

    /**
     * Returns the value of the denomination.
     *
     * @return the value of the denomination
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the count of the denomination.
     *
     * @return the count of the denomination
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count of the denomination.
     *
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}
