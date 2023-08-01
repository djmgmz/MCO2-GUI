package project.ccprog3mco2gui.model;

import java.util.ArrayList;
import java.util.List;

public class SpecialVendingMachine extends RegularVendingMachine {

    //additional items

    private ItemSlots[] milkItems;
    private ItemSlots[] sweetenerItems;
    private ItemSlots[] addOnsItems;
    private ItemSlots[] startMilkItems;
    private ItemSlots[] startSweetenerItems;
    private ItemSlots[] startAddOnsItems;

    public SpecialVendingMachine(String name) {
        super(name);
        initializeStandaloneItems();
    }

    // Initialization method to create standalone items
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
    public void setStandAloneItemsInv(ItemSlots[] milk, ItemSlots[] sweetener, ItemSlots[] addons)
    {
        milkItems = milk;
        sweetenerItems = sweetener;
        addOnsItems = addons;
        startMilkItems =  milk;
        startSweetenerItems = sweetener;
        startAddOnsItems = addons;

    }
    public void setStartingMilkInv(ItemSlots[] newInventory){
        this.milkItems = newInventory;
    }

    public void setStartingSweetenerInv(ItemSlots[] newInventory){
        this.sweetenerItems = newInventory;
    }

    public void setStartingAddonsInv(ItemSlots[] newInventory){
        this.addOnsItems = newInventory;

    }

    public boolean dispenseFinalItem(double payment, Item item)
    {

        double itemPrice = item.getItemPrice();
        String itemName = item.getItemName();
        if (payment < itemPrice) {
            System.out.println("Insufficient payment. Please insert the exact amount to purchase.");
            return false;
        }
        double change = payment - itemPrice;
        if (change < 0) {
            System.out.println("Insufficient payment. Please insert the exact amount to purchase.");
            return false;
        }

        if (!hasSufficientChange(change)) {
            System.out.println("Not enough change in the vending machine. Transaction canceled.");
            return false;
        }

        // Perform the actual dispensing of the item and change
        System.out.println("Dispensing Item: " + itemName + " (Quantity: " + 1 + ")");
        System.out.println("Dispensing change with the following denominations:");

        // Calculate and dispense the change denominations
        List<Denomination> dispensedChange = calculateChange(change);
        for (Denomination denomination : dispensedChange) {
            int count = denomination.getCount();
            int value = denomination.getValue();
            System.out.println(value + " count: " + count);
        }

        updateDenominationsCount(dispensedChange);

        // Update total sales and transaction history
        setTotalSales(getTotalSales() + itemPrice);
        Transaction transaction = new Transaction(itemName, 1, itemPrice);
        getTransactions().add(transaction);

        return true;
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
    public void prepareBasicFruitSmoothieWithProcess(List<Item> selectedIngredients) {
        System.out.println("\nPreparing Basic Fruit Smoothie...");
        for (Item ingredient : selectedIngredients) {
            System.out.println("Adding " + ingredient.getItemName() + "...");
            try {
                Thread.sleep(1000); // Simulate the process of adding each ingredient
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Smoothie Done!");
        System.out.println("-------------------");
    }


    public ItemSlots[] getStartingMilkInventory() {
        return this.startMilkItems;
    }

    public ItemSlots[] getStartingSweetenerInventory() {
        return this.startSweetenerItems;
    }

    public ItemSlots[] getStartingAddOnsInventory() {
        return this.startAddOnsItems;
    }
}
