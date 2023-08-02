package project.ccprog3mco2gui.controller;

        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextField;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;
        import java.util.Scanner;
        import java.util.concurrent.atomic.AtomicInteger;
        import java.util.concurrent.atomic.AtomicReference;

        import javafx.scene.control.Label;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;
        import project.ccprog3mco2gui.model.*;

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
    private Button slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9;
    @FXML
    private Button stockBtn, enterBtn2;

    @FXML
    private Label  titleText;
    @FXML
    private AnchorPane anchorPaneItems, newquantity, slotspane, stockItem, setPrice;
    private RegularVendingMachine vendingMachine;
    private SpecialVendingMachine specialVendingMachine;
    private int selectedItemIndex = -1;
    private boolean isSpecial;
    private Button backbtn2 = new Button();
    private AtomicInteger selectedSlot = new AtomicInteger();
    private AtomicReference<Integer> newQuantity = new AtomicReference<>(0);

    public void setVendingMachine(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
    public void setVendingMachineSpecial(SpecialVendingMachine vendingMachine) {
        this.specialVendingMachine = vendingMachine;
        this.isSpecial = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPrice.setVisible(false);
        anchorPaneItems.setVisible(false);
        newquantity.setVisible(false);
        backBtn2.setVisible(false);
        slotspane.setVisible(false);
        stockItem.setVisible(false);
        backBtn2.setOnAction(e -> returnMaintenanceMenu());

            numLabels = new Text[]{num1,num2,num3,num4,num5,num6,num7,num8,num9};
            nameLabels = new Text[]{name1,name2,name3,name4,name5,name6,name7,name8,name9};
            priceLabels = new Text[]{price1, price2, price3, price4, price5, price6, price7, price8, price9};
            quantityLabels = new Text[]{qty1, qty2, qty3, qty4, qty5, qty6, qty7, qty8, qty9};
            slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
            restockItemBtn.setOnAction(e -> restockItems());
            setItemPriceBtn.setOnAction(e -> setItemPrice()); // replace 'setItemPrice' with the actual method name.

            backBtn.setOnAction(this::goBackToMenu2);

//            restockItemBtn.setOnAction(e -> );
//            setItemPriceBtn.setOnAction(e -> setItemPrice()); // replace 'setItemPrice' with the actual method name.
            //... repeat for other buttons
    }

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
            setItemPriceBtn.setVisible(false);
        }

        restockItemBtn.setOnAction(e -> setItemPriceSlots());//both special and regular (just fruits)
        setItemPriceBtn.setOnAction(e -> setItemPriceExtras());//just extras

        backBtn2.setOnAction(e -> returnMaintenanceMenu());
    }

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
        backbtn2.setOnAction(e -> {
            // Reset to initial UI state
            anchorPaneItems.setVisible(false);
            restockItemBtn.setVisible(true);
            setItemPriceBtn.setVisible(true);
            setPrice.setVisible(false);
        });

        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
    }

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

    private void restockItemsNewQuantitySpecial() {
        restockItemBtn.setDisable(false);
        restockItemBtn.setText("Restock Slots");
        setItemPriceBtn.setText("Restock Extras");

        //choose whether to restock fruits or standaloneitems
        restockItemBtn.setOnAction(e -> restockItemsNewQuantity());
        setItemPriceBtn.setOnAction(e -> restockItemsNewQuantityStandAlone());

    }
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

            if(isSpecial) {
                ItemSlots[] slots = specialVendingMachine.getSlots();
                slots[selectedItemIndex].restockItem(newItem, newItemQuantity);
            } else {
                ItemSlots[] slots = vendingMachine.getSlots();
                slots[selectedItemIndex].restockItem(newItem, newItemQuantity);
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

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


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


    public void returnMaintenanceMenu()
    {

        setPrice.setVisible(false);
        stockItem.setVisible(false);
        restockItemBtn.setText("Restock Items");
        setItemPriceBtn.setText("Set Item Price");
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
        printTransSummaryBtn.setVisible(true);
        backBtn.setVisible(true);
    }
    public void hideButtons()
    {
        restockItemBtn.setVisible(false);
        setItemPriceBtn.setVisible(false);
        collectMoneyBtn.setVisible(false);
        replenishMoneyBtn.setVisible(false);
        printTransSummaryBtn.setVisible(false);
        backBtn.setVisible(false);
    }
    public void specialRestockItems() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to restock Standalone items or Fruits? (1: Standalone, 2: Fruits): ");
        int choice = scanner.nextInt();

    }
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
