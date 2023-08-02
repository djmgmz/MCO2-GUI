package project.ccprog3mco2gui.controller;

        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.ListView;
        import javafx.scene.control.TextField;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.ResourceBundle;
        import java.util.Scanner;
        import java.util.concurrent.atomic.AtomicInteger;
        import java.util.concurrent.atomic.AtomicReference;

        import javafx.scene.control.Label;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;
        import project.ccprog3mco2gui.model.*;
/**
 * Controller class for the Maintenance operations in a Vending Machine application.
 * The class is responsible for updating the user interface and handling the user interactions.
 */
public class MaintenanceController implements Initializable {
    @FXML
    private Button restockItemBtn, setItemPriceBtn, collectMoneyBtn, replenishMoneyBtn, printTransSummaryBtn, backBtn, backBtn2, addBtn;
    @FXML
    private Text num1,num2,num3,num4,num5,num6,num7,num8,num9;
    @FXML
    private Text name1,name2,name3,name4,name5,name6,name7,name8,name9;
    @FXML
    private Text price1,price2,price3,price4,price5,price6,price7,price8,price9;
    @FXML
    private Text qty1,qty2,qty3,qty4,qty5,qty6,qty7,qty8,qty9;
    @FXML
    private TextField quantity_txtfield1, textfield11, textfield2,textfield3, textfield4, textfield21;
    @FXML
    private Text error1;

    @FXML
    private Text[] numLabels = new Text[9];
    @FXML
    private Text[] nameLabels = new Text[9];
    @FXML
    private Text[] priceLabels = new Text[9];
    @FXML
    private Text[] quantityLabels = new Text[9];
    @FXML
    private Button[] slotButtons = new Button[9];

    @FXML
    private Text inv1,inv2,inv3,inv4,inventoryname;
    @FXML
    private Button slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9;
    @FXML
    private Button stockBtn, enterBtn2;
    @FXML
    private Button startInvBtn, currInvBtn, transBtn;
    @FXML
    private Label  titleText;
    @FXML
    private AnchorPane anchorPaneItems, newquantity, slotspane, stockItem, setPrice, inventoryBtns, printScreen, moneyView,denomListBtns;
    @FXML
    private ListView transSummary, moneyListView;
    @FXML
    Button btn1, btn5, btn10, btn20, btn50, btn100, btn200, btn500, btn1000;
    private RegularVendingMachine vendingMachine;
    private SpecialVendingMachine specialVendingMachine;
    private int selectedItemIndex = -1;
    private boolean isSpecial;
    private Button backbtn2 = new Button();
    private AtomicInteger selectedSlot = new AtomicInteger();
    private AtomicReference<Integer> newQuantity = new AtomicReference<>(0);
    /**
     * Sets the RegularVendingMachine to be used by the controller for performing maintenance operations.
     *
     * @param vendingMachine the RegularVendingMachine to be set
     */
    public void setVendingMachine(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * Sets the SpecialVendingMachine to be used by the controller for performing maintenance operations.
     *
     * @param vendingMachine the SpecialVendingMachine to be set
     */
    public void setVendingMachineSpecial(SpecialVendingMachine vendingMachine) {
        this.specialVendingMachine = vendingMachine;
        this.isSpecial = true;
    }
    /**
     * The initialize method is called after all @FXML annotated members have been injected.
     * It's used to set up any additional data that the controller needs,
     * such as setting up event handlers, configuring properties, or populating controls.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numLabels = new Text[]{num1,num2,num3,num4,num5,num6,num7,num8,num9};
        nameLabels = new Text[]{name1,name2,name3,name4,name5,name6,name7,name8,name9};
        priceLabels = new Text[]{price1, price2, price3, price4, price5, price6, price7, price8, price9};
        quantityLabels = new Text[]{qty1, qty2, qty3, qty4, qty5, qty6, qty7, qty8, qty9};
        slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        moneyView.setVisible(false);
        denomListBtns.setVisible(false);
        setPrice.setVisible(false);
        printScreen.setVisible(false);
        inventoryBtns.setVisible(false);
        anchorPaneItems.setVisible(false);
        newquantity.setVisible(false);
        backBtn2.setVisible(false);
        slotspane.setVisible(false);
        stockItem.setVisible(false);
        backBtn2.setOnAction(e -> returnMaintenanceMenu());

            restockItemBtn.setOnAction(e -> restockItems());
            setItemPriceBtn.setOnAction(e -> setItemPrice()); // replace 'setItemPrice' with the actual method name.
            collectMoneyBtn.setOnAction(e -> collectMoney());
            replenishMoneyBtn.setOnAction(e -> replenishMoney());
            printTransSummaryBtn.setOnAction(e -> printTransSummary());

                backBtn.setOnAction(this::goBackToMenu2);
    }
    /**
     * Replenishes money for the vending machine.
     * The method will update the GUI to show the options for replenishing money.
     */
    private void replenishMoney() {
        hideButtons();
        showMoney();
        moneyView.setVisible(true);
        denomListBtns.setVisible(true);
        titleText.setText("Replenish Money");
        backBtn2.setVisible(true);

        btn1.setOnAction(e -> addDenomination(1));
        btn5.setOnAction(e -> addDenomination(5));
        btn10.setOnAction(e -> addDenomination(10));
        btn20.setOnAction(e -> addDenomination(20));
        btn50.setOnAction(e -> addDenomination(50));
        btn100.setOnAction(e -> addDenomination(100));
        btn200.setOnAction(e -> addDenomination(200));
        btn500.setOnAction(e -> addDenomination(500));
        btn1000.setOnAction(e -> addDenomination(1000));

        backBtn2.setOnAction(e -> returnMaintenanceMenu());
    }


    /**
     * Updates the GUI to show the money state of the vending machine.
     */
    private void showMoney()
    {
        denomListBtns.setVisible(true);
        moneyListView.setStyle("-fx-font-size: 15px;");
        moneyView.setVisible(true);

    }

    /**
     * Adds the specified denomination to the vending machine's available money.
     * Updates the list view to reflect the new state.
     *
     * @param value the denomination value to be added.
     */
    private void addDenomination(int value) {
        List<Denomination> denom = isSpecial ? specialVendingMachine.getDenominations() : vendingMachine.getDenominations();
        (specialVendingMachine != null ? specialVendingMachine : vendingMachine).addPaymentDenominations(new Double[] {(double) value});


        // Update the ListView
        moneyListView.getItems().clear();
        for (Denomination d : denom) {
            moneyListView.getItems().add(d.getValue() + ": " + d.getCount());
        }
    }
    /**
     * Collects all money from the vending machine.
     * The method will update the GUI to show the state of money collection.
     */
    private void collectMoney() {
        hideButtons();
        showMoney();
        titleText.setText("Collect Money");
        moneyView.setVisible(true);
        denomListBtns.setVisible(false);
        backBtn2.setVisible(true);
        moneyListView.getItems().clear();
        double totalCollected = 0.0;
        List<Denomination> d = isSpecial ? specialVendingMachine.getDenominations() : vendingMachine.getDenominations();
        for (Denomination denomination : d) {
            double valueOfDenomination = denomination.getValue() * denomination.getCount();
            totalCollected += valueOfDenomination; // Add the value of this denomination to the total
            moneyListView.getItems().add(denomination.getValue() + ": " + denomination.getCount());
            denomination.setCount(0);
        }
        moneyListView.getItems().add("Total Collected: " + totalCollected); // Add total collected to the ListView
        backBtn2.setOnAction(e -> returnMaintenanceMenu());
    }

    /**
     * Updates the GUI to print a transaction summary.
     * The summary includes starting inventory, current inventory, and transactions performed.
     */
    private void printTransSummary() {
        hideButtons();
        titleText.setText("Transaction Summary");
        backBtn2.setVisible(true);
        inventoryBtns.setVisible(true);
        transSummary.setVisible(true);
        printScreen.setVisible(true);

        // Create an ObservableList for the ListView
        ObservableList<String> items = FXCollections.observableArrayList();

        // Assuming transSummary is the ListView<String> you have in your GUI
        transSummary.setItems(items);
        transSummary.setStyle("-fx-font-size: 15px;");

        // Event handlers for the buttons
        startInvBtn.setOnAction(e ->{
            hideNames();
            items.clear();
            List<ItemSlots> startInventory = new ArrayList<>();
            if(vendingMachine != null)
                startInventory.addAll(List.of(vendingMachine.getStartingInventory()));

            if(isSpecial && specialVendingMachine != null)
            {
                startInventory.addAll(List.of(specialVendingMachine.getStartingInventory()));
                startInventory.addAll(List.of(specialVendingMachine.getStartingMilkInventory()));
                startInventory.addAll(List.of(specialVendingMachine.getStartingSweetenerInventory()));
                startInventory.addAll(List.of(specialVendingMachine.getStartingAddOnsInventory()));
            }
            for (ItemSlots itemSlot : startInventory) {
                items.add(itemSlot.getItem().getItemName() + "      " + itemSlot.getQuantity());
            }
        });

        currInvBtn.setOnAction(e -> {
            hideNames();
            items.clear();
            List<ItemSlots> currentInventory = new ArrayList<>();

            if(vendingMachine != null)
                currentInventory.addAll(List.of(vendingMachine.getSlots()));
            if(isSpecial && specialVendingMachine != null)
            {
                currentInventory.addAll(List.of(specialVendingMachine.getSlots()));
                currentInventory.addAll(List.of(specialVendingMachine.getMilk()));
                currentInventory.addAll(List.of(specialVendingMachine.getSweetener()));
                currentInventory.addAll(List.of(specialVendingMachine.getAddOns()));
            }
            for (ItemSlots itemSlot : currentInventory) {
                items.add(itemSlot.getItem().getItemName() + "      " + itemSlot.getQuantity());
            }
        });

        transBtn.setOnAction(e -> {
            showNames();
            items.clear();
            // Assuming you have a list of Transaction objects
            List<Transaction> transactions = isSpecial ? specialVendingMachine.getTransactions() : vendingMachine.getTransactions();

            // Loop counter for transaction numbering
            int count = 1;

            for (Transaction transaction : transactions) {
                String transString = " " + count + "           " +
                        transaction.getItemName() + "            " +
                        transaction.getQuantity() + "           " +
                        transaction.getPayment();
                items.add(transString);

                // Increment the counter
                count++;
            }
        });

        backBtn2.setOnAction(e -> returnMaintenanceMenu());
    }

    /**
     * Updates the GUI to hide texts.
     */
    private void showNames() {
        inventoryname.setVisible(false);
        inv1.setVisible(true);
        inv2.setVisible(true);
        inv3.setVisible(true);
        inv4.setVisible(true);
    }

    /**
     * Updates the GUI to hide texts.
     */
    private void hideNames() {
        inventoryname.setVisible(true);
        inv1.setVisible(false);
        inv2.setVisible(false);
        inv3.setVisible(false);
        inv4.setVisible(false);
    }
    /**
     * Handles setting the item price in the vending machine's GUI.
     * It provides options for setting the price for both slots and extras.
     */
    private void setItemPrice() {
        hideButtons();
        titleText.setText("Set Item Price");
        backBtn2.setVisible(true);
        populateItems();
        restockItemBtn.setVisible(true);
        if(isSpecial) {
            restockItemBtn.setText("Set Item Price for Slots");
            setItemPriceBtn.setText("Set Item Price for Extras");
            setItemPriceBtn.setVisible(true);
        }
        else {
            restockItemBtn.setText("Set Item Price for Slots");
            setItemPriceBtn.setVisible(false);
        }

        restockItemBtn.setOnAction(e -> setItemPriceSlots());//both special and regular (just fruits)
        setItemPriceBtn.setOnAction(e -> setItemPriceExtras());//just extras

        backBtn2.setOnAction(e -> returnMaintenanceMenu());
    }

    /**
     * Handles setting the price for standalone items (extras) in the vending machine's GUI.
     * Standalone items can be things like milk, sweeteners, etc.
     */
    private void setItemPriceExtras() {
        // Show list of standalone items here
        populateItemsStandAlone();
        anchorPaneItems.setVisible(true);
        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
        setPrice.setVisible(false);
        chooseItemSlotExtra();

        // After choosing item, enter the new price
        enterBtn2.setOnAction(e -> {
            try {
                double newPrice = Double.parseDouble(textfield21.getText());
                int selectedSlotIndex = selectedSlot.get();
                ItemSlots[] milkSlots = specialVendingMachine.getMilk();
                ItemSlots[] sweetenerSlots = specialVendingMachine.getSweetener();
                ItemSlots[] addonslots = specialVendingMachine.getAddOns();
                ItemSlots selectedSlot = null;

                if(selectedSlotIndex < milkSlots.length) {
                    selectedSlot = milkSlots[selectedSlotIndex];
                } else if (selectedSlotIndex < milkSlots.length + sweetenerSlots.length) {
                    selectedSlot = sweetenerSlots[selectedSlotIndex - milkSlots.length];
                } else {
                    selectedSlot = addonslots[selectedSlotIndex - milkSlots.length - sweetenerSlots.length];
                }

                if (selectedSlot != null && newPrice > 0) {
                    selectedSlot.getItem().setPrice(newPrice);
                    System.out.println("Slot " + selectedSlot.getItem().getItemName() + " newprice :" + newPrice);
                    populateItemsStandAlone();
                    resetMaintenance();
                }
                setPrice.setVisible(false);
                slotspane.setVisible(true);
                slotspane.setDisable(false);
                anchorPaneItems.setVisible(false);
                anchorPaneItems.setVisible(true);
                textfield21.clear();
            } catch (NumberFormatException ex) {
                // handle invalid number format exception, for example show an error message to the user.
            }
        });

        // Back button event handler
        backbtn2.setOnAction(e -> {returnMaintenanceMenu();});

        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
    }

    /**
     * Handles the selection of slots for standalone items (extras) in the vending machine's GUI.
     * This is used to select which slot's item price needs to be updated.
     */
    private void chooseItemSlotExtra() {
        slotspane.setVisible(true);
        slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        ItemSlots[] milkSlots = specialVendingMachine.getMilk();
        ItemSlots[] sweetenerSlots = specialVendingMachine.getSweetener();
        ItemSlots[] addonslots = specialVendingMachine.getAddOns();
        int length = milkSlots.length + sweetenerSlots.length + addonslots.length;

        for(int i = 0; i < length; i++) {
            int finalI = i;

            // Enable all buttons
            slotButtons[i].setDisable(false);

            slotButtons[i].setOnAction(e -> {
                setPrice.setVisible(true);
                slotspane.setVisible(false);
                selectedSlot.set(finalI);
            });
        }

        // Disable any remaining buttons if the vending machine has fewer slots
        for (int j = length; j < slotButtons.length; j++) {
            slotButtons[j].setDisable(true);
        }
    }

    /**
     * Handles setting the price for items in slots in the vending machine's GUI.
     * This method displays the slots, lets the user choose a slot,
     * and then prompts them to enter the new price for the item in the selected slot.
     */
    private void setItemPriceSlots() {
        populateItems();
        anchorPaneItems.setVisible(true);
        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
        setPrice.setVisible(false);
        chooseItemSlotPrice();

        enterBtn2.setOnAction(e -> {
            try {
                double newPrice = Double.parseDouble(textfield21.getText());
                ItemSlots[] slots = isSpecial ? specialVendingMachine.getSlots() : vendingMachine.getSlots();
                int selectedSlotIndex = selectedSlot.get();
                ItemSlots selectedSlot = null;

                slots[selectedSlotIndex].getItem().setPrice(newPrice);
                populateItems();

                System.out.println("Slot " + slots[selectedSlotIndex].getItem().getItemName() + selectedSlotIndex + " newprice :" + newPrice);

                setPrice.setVisible(false);
                slotspane.setVisible(true);
                resetMaintenance();

                anchorPaneItems.setVisible(false);
                anchorPaneItems.setVisible(true);

                anchorPaneItems.setVisible(false);
                anchorPaneItems.setVisible(true);
                textfield21.clear();
            } catch (NumberFormatException ex) {
                // handle invalid number format exception, for example show an error message to the user.
            }

        });


        // Back button event handler
        backbtn2.setOnAction(e -> returnMaintenanceMenu());
    }


    /**
     * Handles the restocking of items in the vending machine's GUI.
     * It provides options for restocking with a new item or simply increasing the quantity of an existing item.
     * Also, it gives the choice to restock regular slots or special ones, if available.
     */
    public void restockItems() {
        hideButtons();
        titleText.setText("Restock Items");
        populateItems();
        //choose item slot
        //if the item slot is empty there will be 2 options (Stock with new Item) + Restock with Quantity
        //else there will be only 1 (Restock with Quantity)
        restockItemBtn.setText("Stock With New Item");
        setItemPriceBtn.setText("Restock With Quantity");
        if(isSpecial)
        {
            restockItemBtn.setDisable(!specialVendingMachine.hasEmptySlot());
        }
        else{
            restockItemBtn.setDisable(!vendingMachine.hasEmptySlot());
        }

        restockItemBtn.setOnAction(e -> stockWithNewItem());//both special and regular (just fruits)
        if(!isSpecial)
        {
            setItemPriceBtn.setOnAction(e -> restockItemsNewQuantity());
        }
        else {
            setItemPriceBtn.setOnAction(e -> restockItemsNewQuantitySpecial());

        }
        backBtn2.setVisible(true);
        //stock with new item there will be user input (name, price, calories, quantity) -> then will call method in
        // the Vending machine

        //restock with quantity will be user input (only one)



        // Make the relevant buttons visible again

        restockItemBtn.setVisible(true);

        setItemPriceBtn.setVisible(true);
    }

    /**
     * Handles the restocking of special items in the vending machine's GUI.
     * It provides the user with the option to restock either slots or standalone items (extras).
     */
    private void restockItemsNewQuantitySpecial() {
        restockItemBtn.setDisable(false);
        restockItemBtn.setText("Restock Slots");
        setItemPriceBtn.setText("Restock Extras");

        //choose whether to restock fruits or standaloneitems
        restockItemBtn.setOnAction(e -> restockItemsNewQuantity());
        setItemPriceBtn.setOnAction(e -> restockItemsNewQuantityStandAlone());

    }
    /**
     * Handles the restocking of standalone items in the vending machine's GUI.
     * This method first displays the standalone items, then lets the user choose an item and input the quantity to be added.
     * Note: An item can be restocked only if the total quantity after restocking does not exceed 10.
     */
    private void restockItemsNewQuantityStandAlone() {
        // Show list of items here
        populateItemsStandAlone();
        // Hide buttons
        anchorPaneItems.setVisible(true);
        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
        newquantity.setVisible(true);
        slotspane.setVisible(true);
        newquantity.setDisable(true);
        newquantity.setVisible(chooseStandAloneItems());

        // After choosing item, enter the amount
        // When the Enter key is pressed, do something
        addBtn.setOnAction(e -> {
            try {
                int newQuantity = Integer.parseInt(quantity_txtfield1.getText());
                int selectedSlotIndex = selectedSlot.get();
                ItemSlots[] milkSlots = specialVendingMachine.getMilk();
                ItemSlots[] sweetenerSlots = specialVendingMachine.getSweetener();
                ItemSlots[] addonslots = specialVendingMachine.getAddOns();
                ItemSlots selectedSlot = null;
                if(selectedSlotIndex < milkSlots.length) {
                    selectedSlot = milkSlots[selectedSlotIndex];
                } else if (selectedSlotIndex < milkSlots.length + sweetenerSlots.length) {
                    selectedSlot = sweetenerSlots[selectedSlotIndex - milkSlots.length];
                } else {
                    selectedSlot = addonslots[selectedSlotIndex - milkSlots.length - sweetenerSlots.length];
                }

                if (selectedSlot != null && newQuantity > 0 && selectedSlot.getQuantity() + newQuantity <= 10) {
                    selectedSlot.increaseQuantity(newQuantity);
                    specialVendingMachine.resetTransactions();
                    specialVendingMachine.setStartingAddonsInv(specialVendingMachine.getSlots());
                    specialVendingMachine.setStandAloneItemsInv(milkSlots, sweetenerSlots, addonslots);
                    populateItemsStandAlone();
                    resetMaintenance();
                }
                slotspane.setDisable(false);
                newquantity.setDisable(true);
                anchorPaneItems.setVisible(false);
                anchorPaneItems.setVisible(true);
                quantity_txtfield1.clear();
                // Now you have newQuantity and chosenSlot
                // Do something with them here.
            } catch (NumberFormatException ex) {
                // handle invalid number format exception, for example show an error message to the user.
            }
        });

        // Back button event handler
        backbtn2.setOnAction(e -> {
            // Reset to initial UI state
            anchorPaneItems.setVisible(false);
            restockItemBtn.setVisible(true);
            setItemPriceBtn.setVisible(true);
            newquantity.setVisible(false);
        });

        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
    }


    /**
     * Handles the selection of standalone items in the vending machine's GUI.
     * This method displays the slots, enables the buttons for slots with items, and disables the buttons for empty slots.
     * When a slot button is clicked, it disables the slot pane and enables the quantity pane for the user to input the quantity to be added.
     * @return A boolean indicating whether at least one button was clicked (i.e., there is at least one standalone item).
     */
    public boolean chooseStandAloneItems() {
        slotspane.setVisible(true);
        Button[] slotButtons = {slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        ItemSlots[] milkSlots = specialVendingMachine.getMilk();
        ItemSlots[] sweetenerSlots = specialVendingMachine.getSweetener();
        ItemSlots[] addonslots = specialVendingMachine.getAddOns();
        int length = milkSlots.length + sweetenerSlots.length + addonslots.length;
        boolean clicked = false;

        for(int i = 0; i < length; i++)
        {
            int finalI = i;
            //Enable all buttons
            slotButtons[i].setDisable(false);
            clicked = true;

            slotButtons[i].setOnAction(e -> {
                newquantity.setDisable(false);
                newquantity.setVisible(true);
                slotspane.setDisable(true);
                selectedSlot.set(finalI);
            });
        }

        // Disable any remaining buttons if the vending machine has fewer slots
        for (int j = length; j < slotButtons.length; j++) {
            slotButtons[j].setDisable(true);
        }

        return clicked;
    }
    /**
     * Handles the creation and stocking of a new item in the vending machine's GUI.
     * This method displays the fields for entering the new item's details, which are:
     * the item's name, price, calories, and quantity. It also checks whether the entered values are valid.
     * If the values are valid, it creates the new item and restocks the chosen slot with the new item.
     * It then resets the UI to the initial state and clears the text fields.
     * If the values are not valid, it displays an error message without creating or stocking the new item.
     */
    private void stockWithNewItem() {
        // Display fields for new item details
        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
        anchorPaneItems.setVisible(true);
        slotspane.setVisible(true);
        stockItem.setVisible(false);
        stockItem.setDisable(true);
        // Initialize new item details
        chooseEmptyItemSlot();

        ChangeListener<String> textChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                // Do something with newValue here...
            }
        };

        // Attach the listener to the text fields
        textfield11.textProperty().addListener(textChangeListener);
        textfield2.textProperty().addListener(textChangeListener);
        textfield3.textProperty().addListener(textChangeListener);
        textfield4.textProperty().addListener(textChangeListener);
        stockBtn.setOnAction(e -> {

            String caloriesText = textfield3.getText();
            String priceText = textfield2.getText();
            String nameText = textfield11.getText();
            String quantityText = textfield4.getText();

            if (!isNumeric(caloriesText) || !isNumeric(priceText) || !isNumeric(quantityText)) {
                error1.setVisible(true); // Make error message visible
                return;
            }
            else {
                error1.setVisible(false);
            }

            double newItemCalories = Double.parseDouble(caloriesText);
            double newItemPrice = Double.parseDouble(priceText);
            String newItemName = nameText;
            int newItemQuantity = Integer.parseInt(quantityText);

            // Check if quantity is within the required range
            if (newItemQuantity < 1 || newItemQuantity > 10) {
                // Display an error message if the quantity is not in the required range
                // You might want to define a new error message for this purpose
                error1.setVisible(true);
                return;
            }

            Item newItem = new Item(newItemName, newItemPrice, newItemCalories);
            int selectedSlotIndex = selectedSlot.get();
            ItemSlots selectedSlot = null;

            if(isSpecial) {
                ItemSlots[] slots = specialVendingMachine.getSlots();
                slots[selectedSlotIndex].restockItem(newItem, newItemQuantity);
            } else {
                ItemSlots[] slots = vendingMachine.getSlots();
                slots[selectedSlotIndex].restockItem(newItem, newItemQuantity);
            }

            slotspane.setVisible(true);
            stockItem.setVisible(false);

                populateItems();
            resetMaintenance();
            anchorPaneItems.setVisible(false);
            anchorPaneItems.setVisible(true);
            returnMaintenanceMenu();
            textfield11.clear(); textfield2.clear(); textfield3.clear(); textfield4.clear();
        });


        backbtn2.setOnAction(e -> {
            // Reset to initial UI state
            stockItem.setVisible(false);
            anchorPaneItems.setVisible(false);
            restockItemBtn.setVisible(true);
            restockItemBtn.setDisable(false);
            setItemPriceBtn.setVisible(true);
            newquantity.setVisible(false);
        });
    }
    /**
     * Checks whether the provided string can be parsed into a Double, essentially determining if it's numeric.
     *
     * @param str the string to check
     * @return true if the string can be parsed into a Double, false otherwise
     */
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Enables the buttons corresponding to the empty slots in the vending machine's GUI and disables the others.
     * When a button (slot) is clicked, it opens a section to stock a new item and disables the slot section.
     *
     * @return true if at least one slot is empty, false otherwise
     */
    public boolean chooseEmptyItemSlot() {
        slotspane.setVisible(true);
        slotspane.setDisable(false);
        slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        ItemSlots[] slots;
        if(isSpecial)
        {
            slots = specialVendingMachine.getSlots();
        }
        else {
            slots = vendingMachine.getSlots();
        }
        int length = slots.length;
        boolean clicked = false;

        for(int i = 0; i < length; i++) {
            int finalI = i;

            // If the slot is not empty, disable the button
            if (slots[i].isAvailable()) {
                slotButtons[i].setDisable(true);
            } else {
                // If the slot is empty, enable the button and set clicked to true
                slotButtons[i].setDisable(false);
                clicked = true;
            }

            slotButtons[i].setOnAction(e -> {
                selectedSlot.set(finalI);
                stockItem.setVisible(true); // set the visibility of stockItem when a slot button is clicked
                stockItem.setDisable(false); // enable the stockItem when a slot button is clicked
                slotspane.setVisible(false);
            });
        }

        return clicked;
    }

    /**
     * Enables all the buttons corresponding to the slots in the vending machine's GUI.
     * When a button (slot) is clicked, it opens a section to set the price of the item in that slot and hides the slot section.
     */
    public void chooseItemSlotPrice() {
        slotspane.setVisible(true);
        slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        ItemSlots[] slots = isSpecial ? specialVendingMachine.getSlots() : vendingMachine.getSlots();

        for(int i = 0; i < slots.length; i++) {
            int finalI = i;

            // Enable all buttons
            slotButtons[i].setDisable(false);

            slotButtons[i].setOnAction(e -> {
                setPrice.setVisible(true);
                slotspane.setVisible(false);
                selectedSlot.set(finalI);
            });
        }
    }

    /**
     * Enables all the buttons corresponding to the slots in the vending machine's GUI.
     * When a button (slot) is clicked, it prepares for the user to enter a new quantity for the item in that slot.
     *
     * @return true if at least one slot is not empty, false otherwise
     */
    public boolean chooseAnyItemSlot() {
        slotspane.setVisible(true);
        slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        ItemSlots[] slots;
        if(isSpecial)
        {
            slots = specialVendingMachine.getSlots();
        }
        else {
            slots = vendingMachine.getSlots();
        }
        int length = slots.length;
        boolean clicked = false;

        for(int i = 0; i < length; i++) {
            int finalI = i;

            // Enable all buttons
            slotButtons[i].setDisable(false);
            clicked = true;

            slotButtons[i].setOnAction(e -> {
                newquantity.setDisable(false);
                selectedSlot.set(finalI);
            });
        }

        return clicked;
    }


    /**
     * This method allows the user to restock existing items in the vending machine.
     * Upon activation, this method displays the list of items and hides other UI elements
     * like restock and set price buttons.
     * It then allows the user to choose an item slot and specify a quantity for restock.
     * The new quantity is only accepted if it is greater than zero and doesn't exceed the slot capacity (which is checked in the vending machine).
     *
     * The method also includes event handlers for an "Add" button and a "Back" button.
     * The "Add" button triggers the restocking process for a selected item slot,
     * while the "Back" button resets the UI to its initial state.
     */
    public void restockItemsNewQuantity() {
        //visible pa ung list of items here
        //hide buttons
        anchorPaneItems.setVisible(true);
        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
        newquantity.setVisible(true);
        slotspane.setVisible(true);
        newquantity.setDisable(true);
        newquantity.setVisible(chooseAnyItemSlot());

        //after choosing item, enter the amount
        // When the Enter key is pressed, do something

        addBtn.setOnAction(e -> {
                try {
                    newQuantity.set(Integer.parseInt(quantity_txtfield1.getText()));
                    if(newQuantity.get() != null && newQuantity.get() > 0) {
                        //will only work if newquantity is <= 10 (checking is in the vendingM
                        if(isSpecial)
                        {
                            specialVendingMachine.restockWithQuantity(selectedSlot.get(), newQuantity.get());
                            specialVendingMachine.resetTransactions();
                            specialVendingMachine.setStartingAddonsInv(specialVendingMachine.getSlots());
                            ItemSlots[] milkSlots = specialVendingMachine.getMilk();
                            ItemSlots[] sweetenerSlots = specialVendingMachine.getSweetener();
                            ItemSlots[] addonslots = specialVendingMachine.getAddOns();
                            specialVendingMachine.setStandAloneItemsInv(milkSlots, sweetenerSlots, addonslots);
                        }
                        else {
                            vendingMachine.restockWithQuantity(selectedSlot.get(), newQuantity.get());
                        }
                        System.out.println("Slot " + selectedSlot.get() + " Q " +newQuantity.get());
                        newquantity.setDisable(true);
                        populateItems();
                        resetMaintenance();
                    }
                    anchorPaneItems.setVisible(false);
                    anchorPaneItems.setVisible(true);

                    quantity_txtfield1.clear();
                    // Now you have newQuantity.get() and chosenSlot
                    // Do something with them here.
                } catch (NumberFormatException ex) {
                    // handle invalid number format exception, for example show an error message to the user.
                }

        });

        // Back button event handler
        backbtn2.setOnAction(e -> {
            // Reset to initial UI state
            anchorPaneItems.setVisible(false);
            restockItemBtn.setVisible(true);
            setItemPriceBtn.setVisible(true);
            newquantity.setVisible(false);
        });

        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
    }
    /**
     * This method populates the vending machine UI with items currently in the machine.
     * It checks the status of each slot in the machine and updates corresponding UI elements
     * (number, name, price, and quantity labels) accordingly. If the slot is empty or unavailable,
     * the labels are set to display "Empty" or "*".
     */
    public void populateItems() {
        ItemSlots[] slots = isSpecial ? specialVendingMachine.getSlots() : vendingMachine.getSlots();

        for (int i = 0; i < slots.length; i++) {
            String number = Integer.toString(i+1);
            numLabels[i].setText(number);
            if (slots[i].isAvailable()) {
                ItemSlots slot = slots[i];

                if (slot != null && slot.isAvailable()) {
                    String itemprice = Double.toString(slots[i].getItem().getItemPrice());
                    String quantity = Integer.toString(slots[i].getQuantity());
                    nameLabels[i].setText(slots[i].getItem().getItemName());
                    priceLabels[i].setText(itemprice);
                    quantityLabels[i].setText(quantity);
                } else {
                    // If the slot is not available or is null, set an empty label
                    nameLabels[i].setText("Empty");
                    priceLabels[i].setText("Empty");
                    quantityLabels[i].setText("Empty");
                }
            } else {
                // If the vending machine has fewer slots, set empty labels for the remaining slots
                nameLabels[i].setText("Empty");
                priceLabels[i].setText("*");
                quantityLabels[i].setText("*");
            }
        }
    }
    /**
     * This method populates the vending machine UI with standalone items in the machine,
     * specifically items in the categories: Milk, Sweetener, and Addons.
     * It iterates over each category, checks the status of each slot,
     * and updates corresponding UI elements (number, name, price, and quantity labels) accordingly.
     * If a slot is empty, the labels will not be populated.
     */
    public void populateItemsStandAlone() {
        ItemSlots[] milkSlots = specialVendingMachine.getMilk();
        ItemSlots[] sweetenerSlots = specialVendingMachine.getSweetener();
        ItemSlots[] addonslots = specialVendingMachine.getAddOns();

        ItemSlots[][] slotsArr = new ItemSlots[][]{milkSlots, sweetenerSlots, addonslots};
        int currentIndex = 0;
        for (ItemSlots[] slots : slotsArr) {
            for (int i = 0; i < slots.length; i++) {
                if (slots[i] != null) {
                    String number = Integer.toString(currentIndex+1);
                    numLabels[currentIndex].setText(number);
                    String itemprice = Double.toString(slots[i].getItem().getItemPrice());
                    String quantity = Integer.toString(slots[i].getQuantity());
                    nameLabels[currentIndex].setText(slots[i].getItem().getItemName());
                    priceLabels[currentIndex].setText(itemprice);
                    quantityLabels[currentIndex].setText(quantity);
                }
                currentIndex++;
            }
        }
    }
    /**
     * Resets the Maintenance Features screen to its default state.
     * This includes setting visibility of various UI elements and resetting
     * certain UI elements to their default text. The action handlers for
     * 'Restock Items' and 'Set Item Price' buttons are also reset.
     */
    public void returnMaintenanceMenu()
    {
        printTransSummaryBtn.setVisible(true);
        denomListBtns.setVisible(false);
        moneyView.setVisible(false);
        transSummary.setVisible(false);
        inventoryBtns.setVisible(false);
        printScreen.setVisible(false);
        setPrice.setVisible(false);
        stockItem.setVisible(false);
        restockItemBtn.setText("Restock Items");
        setItemPriceBtn.setText("Set Item Price");
        titleText.setText("Maintenance Features");
        setItemPriceBtn.setDisable(false);
        restockItemBtn.setDisable(false);
        restockItemBtn.setOnAction(e -> restockItems());
        setItemPriceBtn.setOnAction(e -> setItemPrice());
        anchorPaneItems.setVisible(false);
        newquantity.setVisible(false);
        backBtn2.setVisible(false);
        slotspane.setVisible(false);
        restockItemBtn.setVisible(true);
        setItemPriceBtn.setVisible(true);
        collectMoneyBtn.setVisible(true);
        replenishMoneyBtn.setVisible(true);
        backBtn.setVisible(true);
    }
    /**
     * Hides all the action buttons in the vending machine UI.
     * The buttons that are hidden include: restockItemBtn, setItemPriceBtn,
     * collectMoneyBtn, replenishMoneyBtn, printTransSummaryBtn, and backBtn.
     */
    public void hideButtons()
    {
        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
        collectMoneyBtn.setVisible(false);
        replenishMoneyBtn.setVisible(false);
        printTransSummaryBtn.setVisible(false);
        backBtn.setVisible(false);
    }

    /**
     * Resets the state of all the item slots in the vending machine.
     * Each slot button is first disabled and then re-enabled. This can be used to ensure
     * the vending machine's user interface reflects the current state of the machine.
     */
    private void resetMaintenance()
    {
        ItemSlots[] slots;
        if(isSpecial)
        {
            slots = specialVendingMachine.getSlots();
        }
        else {
            slots = vendingMachine.getSlots();
        }
        int length = slots.length;

        for (int i = 0; i < length; i++) {
            slotButtons[i].setDisable(true);
            slotButtons[i].setDisable(false);
        }
    }

    /**
     * Switches the application scene to the main menu.
     * It accomplishes this by creating a new FXMLLoader for the main menu fxml file,
     * setting the vending machine service on the newly created menu controller,
     * and finally setting the scene on the current stage.
     *
     * @param event An ActionEvent that triggers this method. Typically this is the user clicking a button.
     */
    private void goBackToMenu2(ActionEvent event) {
        try {
            // Get the singleton instance of VendingMachineService
            VendingMachineService vendingMachineService = VendingMachineService.getInstance();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/menu1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            MenuController menuController = fxmlLoader.getController();
            menuController.setVendingMachineService(vendingMachineService);

            // Get the stage from the event source
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            menuController.setDriverStage(stage);

            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
