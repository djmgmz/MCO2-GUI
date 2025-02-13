package project.ccprog3mco2gui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a regular vending machine.
 */
public class RegularVendingMachine {

    protected  int MAX_SLOTS = 9;

    protected  String name;
    protected  ItemSlots[] slots;
    protected  ItemSlots[] startingInventory;
    protected  List<Denomination> denominations;
    protected  double totalSales;
    protected  List<Transaction> transactions;

    // Constructor
    /**
     * Creates a new RegularVendingMachine with the given name. The machine is initialized with a
     * specific number of slots and denominations, and the total sales and transactions are initialized
     * to zero.
     *
     * @param name the name of the RegularVendingMachine
     */
    public RegularVendingMachine(String name) {
        this.name = name;
        this.slots = new ItemSlots[MAX_SLOTS];
        this.startingInventory = new ItemSlots[MAX_SLOTS];
        this.denominations = new ArrayList<>();
        this.totalSales = 0;
        this.transactions = new ArrayList<>();
        initializeDenominations();
        predefinedSlots();
    }

    // Initialization methods
    /**
     * Initializes the denominations in the vending machine.
     */
    private void initializeDenominations() {
        denominations.add(new Denomination(1, 10));
        denominations.add(new Denomination(5, 10));
        denominations.add(new Denomination(10, 10));
        denominations.add(new Denomination(20, 10));
        denominations.add(new Denomination(50, 10));
        denominations.add(new Denomination(100, 10));
        denominations.add(new Denomination(200, 10));
        denominations.add(new Denomination(500, 10));
        denominations.add(new Denomination(1000, 10));
    }
    /**
     * Sets up predefined slots with items.
     */
    public void predefinedSlots() {
        String[] itemNames = {"Banana", "Strawberry", "Blueberry", "Mango", "Pineapple", "Kiwi", "Raspberry", "Peach", "Gomu-Gomu"};
        double[] itemPrices = {23, 45, 150, 60, 94, 49, 178, 167, 130};
        double[] itemCalories = {89, 6, 20, 68, 47, 61, 53, 56, 80};

        for (int i = 0; i < itemNames.length; i++) {
            String itemName = itemNames[i];
            double price = itemPrices[i];
            double calories = itemCalories[i];
            int quantity = 10; // Assuming the quantity is the same for all predefined slots
            Item item = new Item(itemName, price, calories);
            ItemSlots newSlot = new ItemSlots(item, quantity);
            getSlots()[i] = newSlot;
            getStartingInventory()[i] = new ItemSlots(item, quantity); // Update startingInventory
        }
    }

    // Getter methods
    /**
     * Gets the name of the vending machine.
     *
     * @return the name of the vending machine
     */
    public String getName() { return name; }
    /**
     * Gets the slots of the vending machine.
     *
     * @return the array of ItemSlots representing the slots in the vending machine
     */
    public ItemSlots[] getSlots() { return this.slots; }

    /**
     * Gets the starting inventory of the vending machine.
     *
     * @return the array of ItemSlots representing the starting inventory of the vending machine
     */
    public ItemSlots[] getStartingInventory() { return this.startingInventory; }

    /**
     * Gets the total sales of the vending machine.
     *
     * @return the total sales of the vending machine
     */
    public double getTotalSales() { return this.totalSales; }

    /**
     * Gets the list of denominations accepted by the vending machine.
     *
     * @return the list of Denomination objects accepted by the vending machine
     */
    public List<Denomination> getDenominations() { return denominations; }

    /**
     * Gets the list of transactions performed by the vending machine.
     *
     * @return the list of Transaction objects performed by the vending machine
     */
    public List<Transaction> getTransactions() { return this.transactions; }

    // Setter methods

    /**
     * Sets the total sales of the vending machine.
     *
     * @param totalSales the new total sales value
     */
    public void setTotalSales(double totalSales) { this.totalSales = totalSales; }

    /**
     * Sets the slots of the vending machine.
     *
     * @param slots the new array of ItemSlots for the vending machine
     */
    public void setSlots(ItemSlots[] slots) { this.slots = slots; }

    /**
     * Sets the starting inventory of the vending machine.
     *
     * @param newInventory the new array of ItemSlots for the starting inventory of the vending machine
     */
    public void setStartingInventory(ItemSlots[] newInventory) { this.startingInventory = newInventory; }

    /**
     * Checks if the vending machine has sufficient change for a given amount.
     *
     * @param amount the amount to check
     * @return true if the vending machine has sufficient change, false otherwise
     */
    public boolean hasSufficientChange(double amount) {   for (int i = denominations.size() - 1; i >= 0; i--) {
        Denomination denomination = denominations.get(i);
        int denominationValue = denomination.getValue();

        if (denomination.getCount() > 0 && amount >= denominationValue) {
            int requiredDenominationCount = (int) (amount / denominationValue);
            int availableDenominationCount = Math.min(requiredDenominationCount, denomination.getCount());
            amount -= availableDenominationCount * denominationValue;
        }
    }

        return amount == 0;
    }
    /**
     * Calculates the change to be given for a given amount.
     *
     * @param amount the amount to calculate the change for
     * @return the list of denominations for the change
     */
    public List<Denomination> calculateChange(double amount) {
        List<Denomination> change = new ArrayList<>();

        for (int i = denominations.size() - 1; i >= 0; i--) {
            Denomination denomination = denominations.get(i);
            int denominationValue = denomination.getValue();

            if (denomination.getCount() > 0 && amount >= denominationValue) {
                int availableDenominationCount = (int) (amount / denominationValue);
                availableDenominationCount = Math.min(availableDenominationCount, denomination.getCount());
                Denomination dispensedDenomination = new Denomination(denominationValue, availableDenominationCount);
                change.add(dispensedDenomination);
                amount -= availableDenominationCount * denominationValue;
            }
        }

        return change;
    }

    /**
     * Resets the transactions and total sales of the vending machine.
     */
    public void resetTransactions() {
        this.getTransactions().clear();
        this.setTotalSales(0);
    }
    /**
     * Restocks the specified slot with a new quantity.
     *
     * @param slotIndex   The index of the slot to be restocked.
     * @param newQuantity The new quantity to be restocked.
     */
    public void restockWithQuantity(int slotIndex, int newQuantity) {
        ItemSlots[] slots = getSlots();
        ItemSlots slot = slots[slotIndex];
        if(slot.getQuantity()+newQuantity <= 10)
        {
            slot.setAvailability(true);
            slot.setQuantity(slot.getQuantity()+newQuantity);
            setStartingInventory(slots);
            resetTransactions();
        }
    }
    // Internal helper methods
    /**
     * Updates the count of denominations in the vending machine after a transaction.
     *
     * @param dispensedChange the list of denominations dispensed as change
     */
    public void updateDenominationsCount(List<Denomination> dispensedChange) {
        for (Denomination dispensedDenomination : dispensedChange) {
            int denominationValue = dispensedDenomination.getValue();
            int dispensedCount = dispensedDenomination.getCount();

            for (Denomination denomination : denominations) {
                if (denomination.getValue() == denominationValue) {
                    denomination.setCount(denomination.getCount() - dispensedCount);
                    break;
                }
            }
        }
    }

    /**
     * Dispenses the selected item and processes the transaction.
     *
     * @param slotNumber         The slot number of the item to purchase.
     * @param payment            The payment amount made by the user.
     * @param quantityToDispense The quantity of the item to dispense.
     */
    public String dispenseItem(int slotNumber, double payment, int quantityToDispense) {
        ItemSlots selectedSlot = getSlots()[slotNumber];
        Item item = selectedSlot.getItem();
        double itemPrice = item.getItemPrice();
        String itemName = item.getItemName();

        if (payment < itemPrice) {
            return "Insufficient payment. Please insert the exact amount to purchase.";
        }

        if (selectedSlot.getQuantity() <= 0) {
            return "Not enough quantity of the item in the selected slot.";
        }

        double change = payment - itemPrice;
        if (change < 0) {
            return "Insufficient payment. Please insert the exact amount to purchase.";
        }

        if (!hasSufficientChange(change)) {
            return "Not enough change in the vending machine. Transaction canceled.";
        }

        String Output = "";
        Output += "Dispensing Item: " + itemName + " (Quantity: " + quantityToDispense + ")\n";
        Output += "Dispensing change with the following denominations:\n";

        List<Denomination> dispensedChange = calculateChange(change);
        for (Denomination denomination : dispensedChange) {
            int count = denomination.getCount();
            int value = denomination.getValue();
            Output += value + " count: " + count + "\n";
            System.out.println(value + " count: " + count +"\n");
        }

        selectedSlot.decreaseQuantity(quantityToDispense);
        if (selectedSlot.getQuantity() == 0) {
            selectedSlot.setAvailability(false);
        }

        updateDenominationsCount(dispensedChange);

        setTotalSales(getTotalSales() + itemPrice);
        Transaction transaction = new Transaction(itemName, quantityToDispense, itemPrice);
        getTransactions().add(transaction);
        return Output;
    }
    /**
     * Checks if there is any empty slot available in the vending machine.
     *
     * @return true if there is at least one empty slot, false otherwise.
     */
    public boolean hasEmptySlot() {
        for (ItemSlots slot : getSlots()) {
            if (slot == null || !slot.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds payment denominations to the vending machine.
     *
     * @param denominationValues the values of the denominations to add
     */
    public void addPaymentDenominations(Double[] denominationValues) {
        List<Denomination> vendingMachineDenominations = this.denominations;

        for (Double denominationValue : denominationValues) {
            for (Denomination denomination : vendingMachineDenominations) {
                if (denomination.getValue() == denominationValue) {
                    int count = denomination.getCount();
                    denomination.setCount(count + 1);
                    break;
                }
            }
        }
    }

    /**
     * Input of the user's denomination
     */
    public String payDenominations(Double[] denominations, int choice) {
        String result = "";
        double totalPayment = Arrays.stream(denominations).mapToDouble(Double::doubleValue).sum();

        if (totalPayment >= this.slots[choice - 1].getItem().getItemPrice()) {
            addPaymentDenominations(denominations);
            result = dispenseItem(choice - 1, totalPayment, 1);
        }
        return result;
    }

}
