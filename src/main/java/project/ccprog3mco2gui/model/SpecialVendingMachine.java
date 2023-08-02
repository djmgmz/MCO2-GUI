package project.ccprog3mco2gui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
            milkItems[i] = new ItemSlots(milk[i], 1); // Set quantity to 10 as an example, you can adjust as needed
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

    public String processPayment(ArrayList<Item> selectedIngredients, Double[] denominations, Item item) {
        return dispenseFinalItem(item, denominations);
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

    public void restockStandaloneItems() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.println("Which standalone items do you want to restock?");

        System.out.println("[1] Milk");
        System.out.println("[2] Sweetener");
        System.out.println("[3] Add-ons");

        choice = scanner.nextInt();

        ItemSlots[] milk = getMilk();
        ItemSlots[] sweetener = getSweetener();
        ItemSlots[] addons = getAddOns();

        switch(choice) {
            case 1 -> restockItems(scanner, milk);
            case 2 -> restockItems(scanner, sweetener);
            case 3 -> restockItems(scanner, addons);
            default -> System.out.println("Incorrect input");
        }

        resetTransactions();
        setStandAloneItemsInv(milk, sweetener, addons);
    }

    public void restockItems(Scanner scanner, ItemSlots[] items) {
        for (int i = 0; i < 3; i++) {
            System.out.println("[" + (i+1) + "] " + items[i].getItem().getItemName());
        }
        System.out.println("Select an item to restock: ");
        int itemChoice = scanner.nextInt();
        if(itemChoice >= 1 && itemChoice <= 3) {
            System.out.println("Enter the restock quantity: ");
            int quantity = scanner.nextInt();
            int currentQuantity = items[itemChoice-1].getQuantity();
            int maxCapacity = 10; // This could be a constant or a field in your class
            if(currentQuantity + quantity > maxCapacity) {
                System.out.println("Error: The restock quantity would exceed the slot's maximum capacity.");
            } else {
                items[itemChoice-1].increaseQuantity(quantity);
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

//    /**
//     * Prompts the user to select an item slot from an array.
//     *
//     * @param slots the array of item slots to choose from
//     * @return the index of the selected item slot in the slots array
//     */
//    public int selectItemSlot(ItemSlots[] slots) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Which item slot do you want to select?");
//        for (int i = 0; i < slots.length; i++) {
//            ItemSlots slot = slots[i];
//            Item item = slot.getItem();
//            String itemName = item != null ? item.getItemName() : "Empty";
//            int quantity = slot.getQuantity();
//            System.out.println("[" + (i + 1) + "] " + itemName + " (Quantity: " + quantity + ")");
//        }
//        System.out.print("Enter choice: ");
//        int itemSlotNumber = scanner.nextInt();
//        return itemSlotNumber - 1;
//    }

    public void restockFruits(int itemSlotIndex) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Restock item for " + getName());
        int restockSuccess = -1;
        ItemSlots[] slots = getSlots();

        if (itemSlotIndex < 0 || itemSlotIndex >= slots.length) {
            System.out.println("Invalid item slot number. Please enter a correct input.");
            return;
        }

        ItemSlots selectedSlot = slots[itemSlotIndex];
        System.out.println("Item Slot: " + selectedSlot.getItem().getItemName());

        if (!selectedSlot.isAvailable()) {
            System.out.println("There are no items left in the slot");
            System.out.println("[1] Stock with another item");
            System.out.println("[2] Restock Item");

            System.out.print("Enter choice: ");
            int n = scanner.nextInt();
            ItemSlots[] newInventory = getStartingInventory();
            switch (n) {
                case 1 -> {
                    System.out.print("Enter the name of the new item: ");
                    String itemName = scanner.next();
                    System.out.print("Enter the price of the new item: ");
                    double itemPrice = scanner.nextDouble();
                    System.out.print("Enter calories of the new item: ");
                    double itemCalories = scanner.nextDouble();
                    System.out.print("Enter quantity of the new item: ");
                    int newQuantity = scanner.nextInt();
                    if (newQuantity <= 10) {
                        Item newItem = new Item(itemName, itemPrice, itemCalories);
                        restockWithNewItem(itemSlotIndex, newItem, newQuantity);
                        newInventory[itemSlotIndex].setQuantity(newQuantity);
                        slots[itemSlotIndex] = newInventory[itemSlotIndex]; // Update slots array
                        restockSuccess = 1;
                    } else {
                        System.out.println("Each slot only has a capacity of 10 items.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter quantity to add: ");
                    int newQuantity2 = scanner.nextInt() + selectedSlot.getQuantity();
                    if (newQuantity2 <= 10) {
                        newInventory[itemSlotIndex].setQuantity(newQuantity2);
                        setStartingInventory(newInventory);
                        restockWithQuantity(itemSlotIndex, newQuantity2);
                        slots[itemSlotIndex] = newInventory[itemSlotIndex]; // Update slots array
                        restockSuccess = 1;
                    } else {
                        System.out.println("Each slot only has a capacity of 10 items.");
                    }
                }
                default -> System.out.println("Please enter a correct input");
            }
            if (restockSuccess == 1) {
                setSlots(newInventory);
                resetTransactions();
                System.out.println("Quantity for the selected item slot and transactions have been reset.");
            }
        } else {
            System.out.print("Enter quantity to add: ");
            int newQuantity = scanner.nextInt();

            if (newQuantity + selectedSlot.getQuantity() <= 10) {
                restockWithQuantity(itemSlotIndex, newQuantity);
                ItemSlots[] newInventory = getStartingInventory();
                newInventory[itemSlotIndex].setQuantity(newQuantity + selectedSlot.getQuantity());
                setStartingInventory(newInventory);
                slots[itemSlotIndex] = newInventory[itemSlotIndex]; // Update slots array

                resetTransactions();
                System.out.println("Stocking successful. Quantity for the selected item slot and transactions have been reset.");
                restockSuccess = 1;
            } else {
                System.out.println("Each slot only has a capacity of 10 items. Restocking not possible.");
            }
        }

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


    private void revertIngredientQuantities(ArrayList<Item> selectedIngredients) {
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

    private ItemSlots findCorrespondingSlot(Item item, ItemSlots[] slots) {
        for (ItemSlots slot : slots) {
            if (slot.getItem().equals(item)) {
                return slot;
            }
        }
        return null;
    }


    /**
     * Sets the item price for a specific item in a vending machine.
     */
    public void setItemPrice(ItemSlots[] itemSlots , double price, int itemSlotIndex) {

        ItemSlots selectedSlot = itemSlots[itemSlotIndex];
//        System.out.print("Enter new price for the item: ");
        if(price > 0 && price <= 1000)
        selectedSlot.getItem().setPrice(price);
//        System.out.println("Item price set successfully. The price for the selected item slot has been updated.");
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

    /* to do:
            ArrayList<Item> selectedIngredients in controller
            private void selectIngredient(ItemSlots[] itemSlots, int index, ArrayList<Item> selectedIngredients) {
            for fruits: selectIngredient(ItemSlots[] fruits, int index, ArrayList<Item> selectedIngredients)
            selectIngredient(ItemSlots[] itemSlots, int index, ArrayList<Item> selectedIngredients)
            ung pagcreate ng smoothie: createItem(ArrayList<Item> selectedIngredients)
            tas processPayment, returns boolean. if !successful -> revertIngredients.

            Peter:
            ung pagrestock -> settheinventories for each items.

        note:
        setitemprice -> greater than 0 but <= 1000

     */

}
