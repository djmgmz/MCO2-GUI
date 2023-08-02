package project.ccprog3mco2gui.model;

/**
 * Represents an item slot in the vending machine.
 */
public class ItemSlots {
    private Item item;
    private int quantity;
    private boolean availability;

    /**
     * Constructs an ItemSlots object with the specified item and quantity.
     *
     * @param item     the item in the slot
     * @param quantity the quantity of the item in the slot
     */
    public ItemSlots(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.availability = (quantity > 0);
    }

    /**
     * Sets the item in the slot.
     *
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Returns the item in the slot.
     *
     * @return the item in the slot
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the quantity of the item in the slot.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the quantity of the item in the slot.
     *
     * @return the quantity of the item in the slot
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Checks if the item in the slot is available.
     *
     * @return true if the item is available, false otherwise
     */
    public boolean isAvailable() {
        return availability;
    }
    /**
     * Sets the availability status of the item slot.
     *
     * @param availability true if the item slot is available, false otherwise
     */
    public void setAvailability(boolean availability)
    {
        this.availability = availability;
    }

    /**
     * Decreases the quantity of the item in the slot by the specified amount.
     *
     * @param amount the amount to decrease the quantity by
     */
    public void decreaseQuantity(int amount) {
        if (amount <= quantity) {
            quantity -= amount;
        } else {

        }
    }

    /**
     * Increase the quantity of the item in the slot.
     *
     * @param amount the amount to increase the quantity by
     */
    public void increaseQuantity(int amount) {
        quantity += amount;
    }
    /**
     * Restocks the item slot with a new item and quantity.
     *
     * @param newItem     The new item to be restocked.
     * @param newQuantity The new quantity to be restocked.
     */
    public void restockItem(Item newItem, int newQuantity) {
        setItem(newItem);
        setQuantity(newQuantity);
        setAvailability(true);
    }
}