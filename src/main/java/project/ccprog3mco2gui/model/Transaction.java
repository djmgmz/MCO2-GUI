package project.ccprog3mco2gui.model;

/**
 * Represents a transaction made in a vending machine.
 */
public class Transaction {
    private String itemName;
    private int quantity;
    private double payment;

    /**
     * Constructs a Transaction object with the specified item name, quantity, and payment.
     * Item name must not be null or empty. Quantity and payment must be greater than zero.
     *
     * @param itemName  the name of the item
     * @param quantity  the quantity of the item
     * @param payment   the payment amount for the item
     */
    public Transaction(String itemName, int quantity, double payment) {
        if (itemName == null || itemName.isEmpty()) {
            throw new IllegalArgumentException("Item name must not be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (payment <= 0) {
            throw new IllegalArgumentException("Payment must be greater than zero.");
        }
        this.itemName = itemName;
        this.quantity = quantity;
        this.payment = payment;
    }

    /**
     * Returns the name of the item in the transaction.
     *
     * @return the name of the item
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * Sets the name of the item in the transaction.
     *
     * @param itemName the name of the item
     */
    public void setItemName(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            throw new IllegalArgumentException("Item name must not be null or empty.");
        }
        this.itemName = itemName;
    }

    /**
     * Returns the quantity of the item in the transaction.
     *
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item in the transaction.
     *
     * @param quantity the quantity of the item
     */
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    /**
     * Returns the payment amount for the item in the transaction.
     *
     * @return the payment amount
     */
    public double getPayment() {
        return payment;
    }

    /**
     * Sets the payment amount for the item in the transaction.
     *
     * @param payment the payment amount for the item
     */
    public void setPayment(double payment) {
        if (payment <= 0) {
            throw new IllegalArgumentException("Payment must be greater than zero.");
        }
        this.payment = payment;
    }

    /**
     * Returns a string representation of the Transaction object.
     *
     * @return a string representation of the Transaction object
     */
    public String toString() {
        return "Transaction{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", payment=" + payment +
                '}';
    }
}
