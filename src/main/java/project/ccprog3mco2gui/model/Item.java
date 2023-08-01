package project.ccprog3mco2gui.model;

/**
 * Represents an item in the vending machine.
 */
public class Item {
    private String name;
    private double price;
    private double calories;

    /**
     * Constructs an Item object with the specified name, price, and calories.
     *
     * @param name     the name of the item
     * @param price    the price of the item
     * @param calories the calories of the item
     */
    public Item(String name, double price, double calories) {
        this.name = name;
        this.price = price;
        this.calories = calories;
    }

    /**
     * Sets the name of the item.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the item.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the calories of the item.
     *
     * @param calories the calories to set
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item
     */
    public String getItemName() {
        return name;
    }

    /**
     * Returns the price of the item.
     *
     * @return the price of the item
     */
    public double getItemPrice() {
        return price;
    }

    /**
     * Returns the calories of the item.
     *
     * @return the calories of the item
     */
    public double getItemCalories() {
        return calories;
    }
}