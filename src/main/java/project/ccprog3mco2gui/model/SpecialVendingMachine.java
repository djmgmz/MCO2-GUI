package project.ccprog3mco2gui.model;

import java.util.List;

public class SpecialVendingMachine extends RegularVendingMachine {

    //additional items
    private ItemSlots[] standAloneItems;

    public SpecialVendingMachine(String name) {
        super(name);
        initializeStandaloneItems();
        predefinedSlots();
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

        // Create separate slots for each type of ingredient
        standAloneItems = new ItemSlots[9];
        for (int i = 0; i < 3; i++) {
            standAloneItems[i] = new ItemSlots(milk[i], 10); // Set quantity to 10 as an example, you can adjust as needed
        }
        for (int i = 0; i < 3; i++) {
            standAloneItems[i + 3] = new ItemSlots(sweetener[i], 10);
        }
        for (int i = 0; i < 3; i++) {
            standAloneItems[i + 6] = new ItemSlots(extras[i], 10);
        }
    }

    public ItemSlots[] getStandAloneItems() {
        return standAloneItems;
    }

    public Item getMilk(int index)
    {
        return standAloneItems[index-1].getItem();
    }
    public Item getSweetener(int index)
    {
        return standAloneItems[index*2-1].getItem();
    }
    public Item getAddOns(int index)
    {
        return standAloneItems[index*3-1].getItem();
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

    // New method: Predefined slots with items.
    @Override
    public void predefinedSlots() {
        super.predefinedSlots();

        String[] itemNames = {"Almond Milk", "Soy Milk", "Cow's Milk", "Honey", "Stevia", "Agave Syrup", "Yogurt", "Chia Seeds", "Protein Powder"};
        double[] itemPrices = {120, 90, 100, 20, 15, 25, 30, 10, 50};
        double[] itemCalories = {86, 65, 100, 10, 10, 13, 25, 10, 50};

        for (int i = 0; i < itemNames.length; i++) {
            String itemName = itemNames[i];
            double price = itemPrices[i];
            double calories = itemCalories[i];
            int quantity = 10; // Assuming the quantity is the same for all predefined slots
            Item item = new Item(itemName, price, calories);
            ItemSlots newSlot = new ItemSlots(item, quantity);
            standAloneItems[i] = newSlot;
        }
    }

}
