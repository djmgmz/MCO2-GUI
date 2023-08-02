package project.ccprog3mco2gui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * SpecialVendingMachine is a specific type of vending machine, it extends from the RegularVendingMachine.
 * This vending machine offers additional items such as milk, sweetener, and addons.
 */
public class SpecialVendingMachine extends RegularVendingMachine {

    //additional items

    private ItemSlots[] milkItems;
    private ItemSlots[] sweetenerItems;
    private ItemSlots[] addOnsItems;
    private ItemSlots[] startMilkItems;
    private ItemSlots[] startSweetenerItems;
    private ItemSlots[] startAddOnsItems;

    /**
     * The constructor for the SpecialVendingMachine. Calls the superclass constructor and then initializes
     * the additional items this machine offers.
     *
     * @param name The name of the vending machine.
     */
    public SpecialVendingMachine(String name) {
        super(name);
        initializeStandaloneItems();
    }


    /**
     * This method initializes the additional items offered by the vending machine, namely milk, sweetener, and addons.
     * Each category of item is initialized with three options and a set quantity in each slot.
     */
    private void initializeStandaloneItems() {
        Item[] milk = new Item[3];
        milk[0] = new Item("Almond Milk", 120, 86);
        milk[1] = new Item("Soy Milk", 90, 65);
        milk[2] = new Item("Cow's Milk", 100, 100);

        Item[] sweetener = new Item[3];
        sweetener[0] = new Item("Honey", 20, 10);
        sweetener[1] = new Item("Stevia", 15, 10);
        sweetener[2] = new Item("Agave Syrup", 25, 13);

        Item[] extras = new Item[3];
        extras[0] = new Item("Yogurt", 30, 25);
        extras[1] = new Item("Chia Seeds", 10, 10);
        extras[2] = new Item("Protein Powder", 50, 50);


        milkItems = new ItemSlots[3];
        sweetenerItems = new ItemSlots[3];
        addOnsItems = new ItemSlots[3];
        startMilkItems = new ItemSlots[3];
        startSweetenerItems = new ItemSlots[3];
        startAddOnsItems = new ItemSlots[3];
        for (int i = 0; i < 3; i++) {
            milkItems[i] = new ItemSlots(milk[i], 10); // Set quantity to 10 as an example, you can adjust as needed
            startMilkItems[i] = new ItemSlots(milk[i], 10); // Set quantity to 10 as an example, you can adjust as needed

        }
        for (int i = 0; i < 3; i++) {
            sweetenerItems[i] = new ItemSlots(sweetener[i], 10);
            startSweetenerItems[i] = new ItemSlots(sweetener[i], 10);

        }
        for (int i = 0; i < 3; i++) {
            addOnsItems[i] = new ItemSlots(extras[i], 10);
            startAddOnsItems[i] = new ItemSlots(extras[i], 10);

        }

    }
    /**
     * Sets the initial inventory for the standalone items in the vending machine.
     * This includes milk, sweetener, and addons.
     *
     * @param milk The array of ItemSlots containing the milk items.
     * @param sweetener The array of ItemSlots containing the sweetener items.
     * @param addons The array of ItemSlots containing the addons items.
     */
    public void setStandAloneItemsInv(ItemSlots[] milk, ItemSlots[] sweetener, ItemSlots[] addons)
    {
        milkItems = milk;
        sweetenerItems = sweetener;
        addOnsItems = addons;
        startMilkItems =  milk;
        startSweetenerItems = sweetener;
        startAddOnsItems = addons;

    }

    /**
     * Sets the starting inventory for the milk items in the vending machine.
     *
     * @param newInventory The array of ItemSlots containing the new inventory for milk items.
     */
    public void setStartingMilkInv(ItemSlots[] newInventory){
        this.milkItems = newInventory;
    }
    /**
     * Sets the starting inventory for the sweetener items in the vending machine.
     *
     * @param newInventory The array of ItemSlots containing the new inventory for sweetener items.
     */
    public void setStartingSweetenerInv(ItemSlots[] newInventory){
        this.sweetenerItems = newInventory;
    }

    /**
     * Sets the starting inventory for the addons items in the vending machine.
     *
     * @param newInventory The array of ItemSlots containing the new inventory for addons items.
     */
    public void setStartingAddonsInv(ItemSlots[] newInventory){
        this.addOnsItems = newInventory;

    }

    public String processPayment(ArrayList<Item> selectedIngredients, Double[] denominations, Item item) {
        return dispenseFinalItem(item, denominations);
    }
    public double calculatePrice(ArrayList<Item> selectedIngredients) {
        double totalPrice = 0.0;

        for (int i = 0; i < selectedIngredients.size(); i++) {
            totalPrice += selectedIngredients.get(i).getItemPrice();
        }

        return totalPrice;
    }
    public String dispenseFinalItem(Item smoothie, Double[] denominations) {
        String output = "";
        double totalPayment = Arrays.stream(denominations).mapToDouble(Double::doubleValue).sum();

        double itemPrice = smoothie.getItemPrice();
        String itemName = smoothie.getItemName();
        if (totalPayment < itemPrice) {
            return "Insufficient payment. Please insert the exact amount to purchase.";
        }

        double change = totalPayment - itemPrice;
        if (change < 0) {
            return "Insufficient payment. Please insert the exact amount to purchase.";
        }

        if (!hasSufficientChange(change)) {
            return "Not enough change in the vending machine. Transaction canceled.";
        }

        addPaymentDenominations(denominations);

        output += "Dispensing Item: " + itemName + " (Quantity: 1)\n";
        output += "Dispensing change with the following denominations:\n";

        List<Denomination> dispensedChange = calculateChange(change);
        for (Denomination denomination : dispensedChange) {
            int count = denomination.getCount();
            int value = denomination.getValue();
            output += value + " count: " + count + "\n";
        }

        updateDenominationsCount(dispensedChange);

        // Update total sales and transaction history
        setTotalSales(getTotalSales() + itemPrice);
        Transaction transaction = new Transaction(itemName, 1, itemPrice);
        getTransactions().add(transaction);

        return output;
    }

    public ItemSlots[] getMilk() {
        return milkItems;
    }

    public ItemSlots[] getSweetener() {
        return sweetenerItems;
    }

    public ItemSlots[] getAddOns() {
        return addOnsItems;
    }
    /**
     * Retrieves the initial inventory of milk items.
     *
     * @return An array of ItemSlots representing the starting milk inventory.
     */
    public ItemSlots[] getStartingMilkInventory() {
        return this.startMilkItems;
    }

    /**
     * Retrieves the initial inventory of sweetener items.
     *
     * @return An array of ItemSlots representing the starting sweetener inventory.
     */
    public ItemSlots[] getStartingSweetenerInventory() {
        return this.startSweetenerItems;
    }

    /**
     * Retrieves the initial inventory of addon items.
     *
     * @return An array of ItemSlots representing the starting addons inventory.
     */
    public ItemSlots[] getStartingAddOnsInventory() {
        return this.startAddOnsItems;
    }
    /**
     * Creates a new Item based on a list of selected ingredients. The cost and calories of the new
     * item are determined by the sum of the cost and calories of each ingredient.
     *
     * @param selectedIngredients A list of Items representing the selected ingredients.
     * @return A new Item representing the final product.
     */
    public Item createItem(ArrayList<Item> selectedIngredients) {
        double totalCost = 0;
        double totalCalories = 0;
        for(int i = 0; i < selectedIngredients.toArray().length; i++)
        {
            totalCost += selectedIngredients.get(i).getItemPrice();
            totalCalories += selectedIngredients.get(i).getItemCalories();
        }
        return new Item("Fruit Smoothie", totalCost, totalCalories);
    }
    /**
     * Selects an ingredient from an array of item slots based on the provided index and adds it to the list of selected ingredients.
     * If the selected ingredient is unavailable or the index is out of bounds, an appropriate message is printed and the method returns false.
     *
     * @param itemSlots An array of ItemSlots representing the available items.
     * @param index The index of the desired item.
     * @param selectedIngredients A list of Items representing the selected ingredients.
     * @return true if the ingredient was successfully added, false otherwise.
     */
    public boolean selectIngredient(ItemSlots[] itemSlots, int index, ArrayList<Item> selectedIngredients) {
        if(index < 1 || index > itemSlots.length) {
            System.out.println("Invalid option. Please try again.");
            return false;
        }

        ItemSlots slot = itemSlots[index-1];
        if(slot.getQuantity() <= 0) {
            System.out.println("Sorry, this item is currently unavailable.");
            return false;
        }
        Item chosenItem = slot.getItem();
        selectedIngredients.add(chosenItem);
        slot.decreaseQuantity(1);
        return true;
    }

    /**
     * Reverts the quantity of each selected ingredient.
     * It locates the corresponding slot of each ingredient in the vending machine and increments the quantity by 1.
     * It iterates through the standard slots and the special slots for milk items, sweetener items, and addon items.
     *
     * @param selectedIngredients A list of Items representing the selected ingredients to be reverted.
     */
    public void revertIngredientQuantities(ArrayList<Item> selectedIngredients) {
        for (Item ingredient : selectedIngredients) {
            ItemSlots correspondingSlot = findCorrespondingSlot(ingredient, this.slots);
            if (correspondingSlot == null) {
                correspondingSlot = findCorrespondingSlot(ingredient, milkItems);
            }
            if (correspondingSlot == null) {
                correspondingSlot = findCorrespondingSlot(ingredient, sweetenerItems);
            }
            if (correspondingSlot == null) {
                correspondingSlot = findCorrespondingSlot(ingredient, addOnsItems);
            }
            if (correspondingSlot != null) {
                correspondingSlot.increaseQuantity(1);
            }
        }
    }
    /**
     * Overrides the hasEmptySlot method from the superclass to include the additional item categories offered by the SpecialVendingMachine.
     * Returns true if there is an empty or unavailable slot in any of the item categories.
     *
     * @return true if an empty or unavailable slot is found, false otherwise.
     */
    @Override
    public boolean hasEmptySlot() {
        List<ItemSlots[]> allSlotArrays = Arrays.asList(getSlots(), milkItems, sweetenerItems, addOnsItems);

        for (ItemSlots[] slotsArray : allSlotArrays) {
            for (ItemSlots slot : slotsArray) {
                if (slot == null || !slot.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Finds the ItemSlots object that corresponds to the provided Item in the given array of ItemSlots.
     *
     * @param item The Item to find.
     * @param slots An array of ItemSlots in which to search for the item.
     * @return The ItemSlots object containing the item, or null if no such object is found.
     */
    private ItemSlots findCorrespondingSlot(Item item, ItemSlots[] slots) {
        for (ItemSlots slot : slots) {
            if (slot.getItem().equals(item)) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Validates the given denomination for use in vending machine payment.
     *
     * @param denomination The denomination to validate.
     * @return True if the denomination is valid, false otherwise.
     */
    private boolean validateDenominations(int denomination) {
        int[] validDenominations = {1, 5, 10, 15, 20, 50, 100, 200, 300, 500, 1000};

        for (int i = 0; i < validDenominations.length; i++) {
            if (denomination == validDenominations[i]) {
                return true;
            }
        }

        System.out.println("Error: Invalid denomination (" + denomination + "). We do not accept this type of denomination.");
        return false;
    }
}
