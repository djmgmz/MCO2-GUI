package project.ccprog3mco2gui.controller;

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

        import javafx.scene.input.KeyCode;
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
    private TextField quantity_txtfield1;

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
    private Label  titleText;
    @FXML
    private AnchorPane anchorPaneItems, newquantity, slotspane;
    private RegularVendingMachine vendingMachine;
    private int selectedItemIndex = -1;

    private Button backbtn2 = new Button();
    private AtomicInteger selectedSlot = new AtomicInteger();
    private AtomicReference<Integer> newQuantity = new AtomicReference<>(0);

    public void setVendingMachine(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        anchorPaneItems.setVisible(false);
        newquantity.setVisible(false);
        backBtn2.setVisible(false);
        slotspane.setVisible(false);
        backBtn2.setOnAction(e -> returnMaintenanceMenu());

        if (!(vendingMachine instanceof SpecialVendingMachine)) {
            numLabels = new Text[]{num1,num2,num3,num4,num5,num6,num7,num8,num9};
            nameLabels = new Text[]{name1,name2,name3,name4,name5,name6,name7,name8,name9};
            priceLabels = new Text[]{price1, price2, price3, price4, price5, price6, price7, price8, price9};
            quantityLabels = new Text[]{qty1, qty2, qty3, qty4, qty5, qty6, qty7, qty8, qty9};
            slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};

            restockItemBtn.setOnAction(e -> restockItems());
//            setItemPriceBtn.setOnAction(e -> setItemPrice()); // replace 'setItemPrice' with the actual method name.
//            replenishMoneyBtn.setOnAction(e -> );
            //... repeat for other buttons

            backBtn.setOnAction(this::goBackToMenu2);

        } else {
//            restockItemBtn.setOnAction(e -> );
//            setItemPriceBtn.setOnAction(e -> setItemPrice()); // replace 'setItemPrice' with the actual method name.
            //... repeat for other buttons
        }
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

//        restockItemBtn.setOnAction(e -> stockWithNewItem());
        setItemPriceBtn.setOnAction(e -> restockItemsNewQuantity());
        backBtn2.setVisible(true);
        //stock with new item there will be user input (name, price, calories, quantity) -> then will call method in
        // the Vending machine

        //restock with quantity will be user input (only one)


    /*
    if (vendingMachine.isSlotEmpty(0)) {
        // Here you may also want to change the actions the buttons perform
        restockItemBtn.setOnAction(e -> stockNewItem());
        setItemPriceBtn.setOnAction(e -> restockWithQuantity());
    } else {
        restockItemBtn.setOnAction(e -> stockNewItem());
    }
    */

        // Make the relevant buttons visible again
        {
            restockItemBtn.setVisible(true);
        }
        setItemPriceBtn.setVisible(true);
    }


//    private void stockWithNewItem() {
//        // Display fields for new item details
//        newItemPane.setVisible(true);
//        restockItemBtn.setVisible(false);
//        setItemPriceBtn.setVisible(false);
//
//        // Initialize new item details
//        AtomicReference<String> newItemName = new AtomicReference<>("");
//        AtomicReference<Double> newItemPrice = new AtomicReference<>(0.0);
//        AtomicReference<Integer> newItemCalories = new AtomicReference<>(0);
//        AtomicReference<Integer> newItemQuantity = new AtomicReference<>(0);
//        int chosenSlot = chooseItem().get();
//
//        // When the Enter key is pressed, get new item details
//        nameTextField.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                newItemName.set(nameTextField.getText());
//            }
//        });
//
//        priceTextField.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                try {
//                    newItemPrice.set(Double.parseDouble(priceTextField.getText()));
//                } catch (NumberFormatException ex) {
//                    // handle invalid number format exception, for example show an error message to the user.
//                }
//            }
//        });
//
//        caloriesTextField.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                try {
//                    newItemCalories.set(Integer.parseInt(caloriesTextField.getText()));
//                } catch (NumberFormatException ex) {
//                    // handle invalid number format exception, for example show an error message to the user.
//                }
//            }
//        });
//
//        quantityTextField.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                try {
//                    newItemQuantity.set(Integer.parseInt(quantityTextField.getText()));
//                    // Create new Item with user-inputted details
//                    Item newItem = new Item(newItemName.get(), newItemPrice.get(), newItemCalories.get());
//                    // Stock new item in chosen slot
//                    vendingMachine.stockWithNewItem(chosenSlot, newItem, newItemQuantity.get());
//                } catch (NumberFormatException ex) {
//                    // handle invalid number format exception, for example show an error message to the user.
//                }
//            }
//        });
//    }



    public void chooseItemSlot() {
        slotButtons = new Button[]{slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};
        int length = vendingMachine.getSlots().length;
        for(int i = 0; i < length; i++) {
            int finalI = i;
            slotButtons[i].setOnAction(e -> {
                selectedSlot.set(finalI);
                newquantity.setDisable(false);
            });
        }
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
        chooseItemSlot();
        //after choosing item, enter the amount
        // When the Enter key is pressed, do something

        addBtn.setOnAction(e -> {
                try {
                    newQuantity.set(Integer.parseInt(quantity_txtfield1.getText()));
                    if(newQuantity.get() != null && newQuantity.get() > 0) {
                        //will only work if newquantity is <= 10 (checking is in the vendingM
                        vendingMachine.restockWithQuantity(selectedSlot.get(), newQuantity.get());
                        System.out.println("Slot " + selectedSlot.get() + " Q " +newQuantity.get());
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
        ItemSlots[] slots = vendingMachine.getSlots();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isAvailable()) {
                ItemSlots slot = slots[i];
                String number = Integer.toString(i+1);
                numLabels[i].setText(number);
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
                numLabels[i].setText("*");
                nameLabels[i].setText("MISSING");
                priceLabels[i].setText("MISSING");
                quantityLabels[i].setText("MISSING");
            }
        }
    }



    public void returnMaintenanceMenu()
    {
        restockItemBtn.setText("Restock Items");
        setItemPriceBtn.setText("Set Item Price");
        restockItemBtn.setOnAction(e -> restockItems());
        setItemPriceBtn.setOnAction(null);
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
        int length = vendingMachine.getSlots().length;

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
