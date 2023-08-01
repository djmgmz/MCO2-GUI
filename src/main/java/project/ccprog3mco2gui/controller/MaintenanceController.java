package project.ccprog3mco2gui.controller;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Button;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import java.net.URL;
        import java.util.ResourceBundle;
        import java.util.Scanner;


        import javafx.scene.control.Label;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.text.TextFlow;
        import javafx.scene.text.Text;
        import project.ccprog3mco2gui.model.*;

public class MaintenanceController implements Initializable {
    @FXML
    private Button restockItemBtn, setItemPriceBtn, collectMoneyBtn, replenishMoneyBtn, printTransSummaryBtn, backBtn;

    @FXML
    private Text num1,num2,num3,num4,num5,num6,num7,num8,num9;
    @FXML
    private Text name1,name2,name3,name4,name5,name6,name7,name8,name9;
    @FXML
    private Text price1,price2,price3,price4,price5,price6,price7,price8,price9;
    @FXML
    private Text qty1,qty2,qty3,qty4,qty5,qty6,qty7,qty8,qty9;

    @FXML
    private Text[] numLabels = new Text[9];
    @FXML
    private Text[] nameLabels = new Text[9];
    @FXML
    private Text[] priceLabels = new Text[9];
    @FXML
    private Text[] quantityLabels = new Text[9];
    @FXML
    private Label  titleText;
    @FXML
    private AnchorPane anchorPaneItems;
    private RegularVendingMachine vendingMachine;

    public MaintenanceController (RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPaneItems.setVisible(false);

        if (!(vendingMachine instanceof SpecialVendingMachine)) {
            numLabels = new Text[]{num1,num2,num3,num4,num5,num6,num7,num8,num9};
            nameLabels = new Text[]{name1,name2,name3,name4,name5,name6,name7,name8,name9};
            priceLabels = new Text[]{price1, price2, price3, price4, price5, price6, price7, price8, price9};
            quantityLabels = new Text[]{qty1, qty2, qty3, qty4, qty5, qty6, qty7, qty8, qty9};


            restockItemBtn.setOnAction(e -> restockItems());
//            backBtn.setOnAction(e -> anchorPaneItems.setVisible(false));
//            setItemPriceBtn.setOnAction(e -> setItemPrice()); // replace 'setItemPrice' with the actual method name.
            //... repeat for other buttons
        } else {
            restockItemBtn.setOnAction(e -> ((SpecialVendingMachine) vendingMachine).restockFruits());
//            setItemPriceBtn.setOnAction(e -> setItemPrice()); // replace 'setItemPrice' with the actual method name.
            //... repeat for other buttons
        }
    }

    public void restockItems() {
        hideButtons();
        titleText.setText("Restock Items");
        anchorPaneItems.setVisible(true);
        populateItems();





        restockItemBtn.setText("Stock With New Item");
        setItemPriceBtn.setText("Restock With Quantity");

        // If the first item's slot is empty, adjust button text accordingly
        // This is just an example, adjust according to your actual needs
        // Commented out due to lack of context in the provided code
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
        restockItemBtn.setVisible(true);
        setItemPriceBtn.setVisible(true);
    }

    public void populateItems() {
        ItemSlots[] slots = vendingMachine.getSlots();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isAvailable()) {
                ItemSlots slot = slots[i];
                String number = Integer.toString(i);
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

    public void chooseItem()
    {

    }
    public void restockItemsNewQuantity()
    {
        restockItemBtn.setVisible(false);

    }

    public void showButtons()
    {
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
}
