package project.ccprog3mco2gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import project.ccprog3mco2gui.model.*;

public class RegularVendingMachineController implements Initializable {

    @FXML
    private ImageView redButton1,redButton2,redButton3,redButton4,redButton5,redButton6,redButton7,redButton8,redButton9,redButton0;
    @FXML
    private Label item1,item2,item3,item4,item5,item6,item7,item8,item9;
    @FXML
    private Label price1,price2,price3,price4,price5,price6,price7,price8,price9;
    @FXML
    private Label calories1,calories2,calories3,calories4,calories5,calories6,calories7,calories8,calories9;
    @FXML
    private Label quantity1,quantity2,quantity3,quantity4,quantity5,quantity6,quantity7,quantity8,quantity9;
    private ImageView[] redButtons = new ImageView[10];
    @FXML
    private Label[] itemLabels = new Label[9];
    @FXML
    private Label[] priceLabels = new Label[9];
    @FXML
    private Label[] caloriesLabels = new Label[9];
    @FXML
    private Label[] quantityLabels = new Label[9];

    private RegularVendingMachine vendingMachine;

    public RegularVendingMachineController(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        redButtons = new ImageView[]{redButton0, redButton1, redButton2, redButton3, redButton4, redButton5, redButton6, redButton7, redButton8, redButton9};
        itemLabels = new Label[]{item1, item2, item3, item4, item5, item6, item7, item8, item9};
        priceLabels = new Label[]{price1, price2, price3, price4, price5, price6, price7, price8, price9};
        caloriesLabels = new Label[]{calories1, calories2, calories3, calories4, calories5, calories6, calories7, calories8, calories9};
        quantityLabels = new Label[]{quantity1, quantity2, quantity3, quantity4, quantity5, quantity6, quantity7, quantity8, quantity9};

        ItemSlots[] slots = vendingMachine.getSlots();
        for (int i = 0; i < itemLabels.length; i++) {
            if (i < slots.length) {
                ItemSlots slot = slots[i];
                if (slot != null && slot.isAvailable()) {
                    Item item = slot.getItem();
                    itemLabels[i].setText(item.getItemName());
                } else {
                    // If the slot is not available or is null, set an empty label
                    itemLabels[i].setText("NULL");
                }
            } else {
                // If the vending machine has fewer slots, set empty labels for the remaining slots
                itemLabels[i].setText("MISSING");
            }
        }

        for (int i = 0; i < redButtons.length; i++) {
            final int buttonNumber = i; // Button numbers start from 0

            // Set event handler for mouse pressed
            redButtons[buttonNumber].setOnMousePressed(event -> {
                Image greenButtonImage = new Image(getClass().getResource("/assets/greenbutton" + buttonNumber + ".png").toExternalForm());
                redButtons[buttonNumber].setImage(greenButtonImage);
            });

            // Set event handler for mouse released
            redButtons[buttonNumber].setOnMouseReleased(event -> {
                Image redButtonImage = new Image(getClass().getResource("/assets/redbutton" + buttonNumber + ".png").toExternalForm());
                redButtons[buttonNumber].setImage(redButtonImage);
            });
        }
    }
}