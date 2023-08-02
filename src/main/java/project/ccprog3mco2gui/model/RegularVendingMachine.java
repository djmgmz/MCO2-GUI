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
    public RegularVendingMachine(String name) {
        this.name = name;
        this.slots = new ItemSlots[MAX_SLOTS];
        this.startingInventory = new ItemSlots[MAX_SLOTS];
        this.denominations = new ArrayList<>();
        this.totalSales = 0;
        this.transactions = new ArrayList<>();
        initializeDenominations();
        predefinedSlots();
        slots[0].setQuantity(0);
        slots[0].setAvailability(false);
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
     * Creates slots in the vending machine based on user input.
     */
    public void createSlots() {
        Scanner scanner = new Scanner(System.in);
        ItemSlots[] slots = new ItemSlots[9];
        for (int i = 0; i < slots.length; i++) {
            int slotCount = getOccupiedSlotCount();
            if (slotCount >= slots.length) {
                System.out.println("Cannot create more slots. Maximum slot limit reached.");
                return;
            }

            System.out.print("Enter Item [" + (i + 1) + "] Name: ");
            String itemName = scanner.next();

            double price = -1;
            while (price < 0) {
                System.out.print("Enter Item Price: ");
                price = scanner.nextDouble();
                if (price < 0) {
                    System.out.println("The price you entered is invalid!");
                }
            }

            double calories = -1;
            while (calories < 0) {
                System.out.print("Enter Item Calories: ");
                calories = scanner.nextDouble();
                if (calories < 0) {
                    System.out.println("The calories you entered are invalid!");
                }
            }

            int quantity = 100;
            while (quantity > 10 || quantity < 0) {
                System.out.print("Enter Item Quantity: ");
                quantity = scanner.nextInt();
                if (quantity > 10) {
                    System.out.println("The capacity of the vending machine is only up to 10 items!");
                } else if (quantity < 0) {
                    System.out.println("You entered a negative amount of items!");
                }
            }

            Item item = new Item(itemName, price, calories);
            ItemSlots newSlot = new ItemSlots(item, quantity);
            slots[slotCount] = newSlot;
        }
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
    public String getName() { return name; }
    public ItemSlots[] getSlots() { return slots; }
    public ItemSlots[] getStartingInventory() { return this.startingInventory; }
    public double getTotalSales() { return this.totalSales; }
    public List<Denomination> getDenominations() { return denominations; }
    public List<Transaction> getTransactions() { return this.transactions; }

    // Setter methods
    public void setTotalSales(double totalSales) { this.totalSales = totalSales; }
    public void setSlots(ItemSlots[] slots) { this.slots = slots; }
    public void setStartingInventory(ItemSlots[] newInventory) { this.startingInventory = newInventory; }

    // Business methods
    /**
     * Returns the count of occupied slots in the vending machine.
     *
     * @return the count of occupied slots
     */
    public int getOccupiedSlotCount() {
        int count = 0;
        for (int i = 0; i < slots.length; i++) {
            ItemSlots slot = slots[i];
            if (slot != null) {
                count++;
            }
        }
        return count;
    }

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
     * Collects all the money from the vending machine.
     *
     * @return the total money within the vending machine
     */
    public double collectMoney() {
        double totalMoney = 0;
        for(int i = 0; i < denominations.size(); i++)
        {
            totalMoney += denominations.get(i).getValue()* denominations.get(i).getCount();
            this.denominations.get(i).setCount(0);
        }
        System.out.println("Collected: " + totalMoney);

        return totalMoney;
    }


    /**
     * Resets the transactions and total sales of the vending machine.
     */
    public void resetTransactions() {
        this.getTransactions().clear();
        this.setTotalSales(0);
    }

    /**
     * Restocks the specified slot with a new item and quantity, if the slot is not already stocked.
     *
     * @param slotIndex The index of the slot to be restocked.
     */
    public void restockWithNewItem(int slotIndex, Item newItem, int newQuantity) {
        ItemSlots[] slots = getSlots();
        ItemSlots slot = slots[slotIndex];

        slot.restockItem(newItem, newQuantity);
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
            updateStartingInventory();
            resetTransactions();
        }
    }
public void updateStartingInventory()
{
    this.startingInventory = slots;
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

        if (totalPayment >= slots[choice - 1].getItem().getItemPrice()) {
            addPaymentDenominations(denominations);
            result = dispenseItem(choice - 1, totalPayment, 1);
        }
        return result;
    }

}
