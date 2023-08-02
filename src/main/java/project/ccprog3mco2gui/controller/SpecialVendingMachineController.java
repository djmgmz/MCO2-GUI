package project.ccprog3mco2gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.*;
import javafx.scene.control.Label;
import project.ccprog3mco2gui.Driver;
import project.ccprog3mco2gui.model.*;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class SpecialVendingMachineController implements Initializable {

    @FXML
    private ImageView redButton1,redButton2,redButton3,redButton4,redButton5,redButton6,redButton7,redButton8,redButton9,redButton0;
    @FXML
    private Label item1,item2,item3,item4,item5,item6,item7,item8,item9;
    @FXML
    private Label price1,price2,price3,price4,price5,price6,price7,price8,price9,price10,price11,price12,price13,price14,price15,price16,price17,price18;
    @FXML
    private Label calories1,calories2,calories3,calories4,calories5,calories6,calories7,calories8,calories9,calories10,calories11,calories12,calories13,calories14,calories15,calories16,calories17,calories18;
    @FXML
    private Label quantity1,quantity2,quantity3,quantity4,quantity5,quantity6,quantity7,quantity8,quantity9, quantity10, quantity11, quantity12, quantity13, quantity14, quantity15, quantity16, quantity17, quantity18;
    private ImageView[] redButtons = new ImageView[10];
    @FXML
    private Label[] itemLabels = new Label[9];
    @FXML
    private Label[] priceLabels = new Label[18];
    @FXML
    private Label[] caloriesLabels = new Label[18];
    @FXML
    private Label[] quantityLabels = new Label[18];
    @FXML
    private Text screenText;

    private SpecialVendingMachine vendingMachine;
    private ArrayList<Item> selectedIngredients;
    @FXML
    private Label numberButtonText;

    @FXML
    private Button denom1, denom5, denom10, denom15, denom20, denom50, denom100, denom200, denom300, denom500, denom1000;

    private Button[] denomButtonsArray;
    @FXML
    private Label screenError;

    @FXML Button enterButton;

    @FXML Button backButton;

    private int selectedItemIndex = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        redButtons = new ImageView[]{redButton0, redButton1, redButton2, redButton3, redButton4, redButton5, redButton6, redButton7, redButton8, redButton9};
        itemLabels = new Label[]{item1, item2, item3, item4, item5, item6, item7, item8, item9};
        priceLabels = new Label[]{price1, price2, price3, price4, price5, price6, price7, price8, price9, price10, price11, price12, price13, price14, price15, price16, price17, price18};
        caloriesLabels = new Label[]{calories1, calories2, calories3, calories4, calories5, calories6, calories7, calories8, calories9, calories10,calories11,calories12,calories13,calories14,calories15,calories16,calories17,calories18};
        quantityLabels = new Label[]{quantity1, quantity2, quantity3, quantity4, quantity5, quantity6, quantity7, quantity8, quantity9, quantity10, quantity11, quantity12, quantity13, quantity14, quantity15, quantity16, quantity17, quantity18};
        denomButtonsArray = new Button[]{denom1, denom5, denom10, denom15, denom20, denom50, denom100, denom200, denom300, denom500,denom1000};

        ArrayList<Item> selectedIngredients = new ArrayList<>();

        Map<Button, Integer> denominationsMap = new HashMap<>();
        denominationsMap.put(denom1, 1);
        denominationsMap.put(denom5, 5);
        denominationsMap.put(denom10, 10);
        denominationsMap.put(denom15, 15);
        denominationsMap.put(denom20, 20);
        denominationsMap.put(denom50, 50);
        denominationsMap.put(denom100, 100);
        denominationsMap.put(denom200, 200);
        denominationsMap.put(denom300, 300);
        denominationsMap.put(denom500, 500);
        denominationsMap.put(denom1000, 1000);


        resetVendingMachine();

        for (int i = 0; i <= 9; i++) {
            int buttonNumber = i; // Button numbers 1 to 9

            redButtons[buttonNumber].setOnMouseClicked(event -> {
                // Add the respective number (1 to 9) to the existing text in numberButtonText
                numberButtonText.setText(numberButtonText.getText() + Integer.toString(buttonNumber));
            });
        }

        numberButtonText.textProperty().addListener((observable, oldValue, newValue) -> {
            numberButtonText.setVisible(!newValue.isEmpty());
        });

        screenText.setText("Select a fruit on the left (or enter 0 for change): ");

        enterButton.setOnAction(event -> {
            String textFromScreen = screenText.getText();
            String inputText = numberButtonText.getText().trim();

            if (inputText.isEmpty()) {
                screenError.setVisible(true);
                return;
            }

            if (textFromScreen.equals("Select a fruit on the left (or enter 0 for change): ")) {
                numberButtonText.setText("");
                try {
                    selectedItemIndex = Integer.parseInt(inputText);

                    if (selectedItemIndex >= 0 && selectedItemIndex <= vendingMachine.getSlots().length) {
                        screenError.setVisible(false);
                        if(selectedItemIndex == 0)
                        {
                            for (int i = 0; i < 10; i++) {
                                redButtons[i].setDisable(true);
                            }
                            screenText.setText("Enter the denominations of your payment");
                            for (Button denomButton : denomButtonsArray) {
                                denomButton.setDisable(false);
                            }
                        }
                        else {
                            vendingMachine.selectIngredient(vendingMachine.getSlots(), selectedItemIndex, selectedIngredients);
                            screenText.setText("Select your preferred milk on the right: ");
                        }
                    }
                    else
                    {
                        screenError.setVisible(true);
                    }
                }
                catch (NumberFormatException e)
                {
                    screenError.setVisible(true);
                }
            }
            else if(textFromScreen.equals("Select your preferred milk on the right: "))
            {
                selectedItemIndex = Integer.parseInt(inputText);
                numberButtonText.setText("");
                if (selectedItemIndex >= 1 && selectedItemIndex <= vendingMachine.getMilk().length) {
                    screenError.setVisible(false);
                    vendingMachine.selectIngredient(vendingMachine.getMilk(), selectedItemIndex, selectedIngredients);
                    screenText.setText("Select your preferred sweetener on the right: ");
                }
                else
                {
                    screenError.setVisible(true);
                }
            }
            else if(textFromScreen.equals("Select your preferred sweetener on the right: "))
            {
                selectedItemIndex = Integer.parseInt(inputText);
                numberButtonText.setText("");
                if (selectedItemIndex >= 1 && selectedItemIndex <= vendingMachine.getSweetener().length) {
                    screenError.setVisible(false);
                    vendingMachine.selectIngredient(vendingMachine.getSweetener(), selectedItemIndex, selectedIngredients);
                    screenText.setText("Select your preferred add-on on the right: ");
                }
                else
                {
                    screenError.setVisible(true);
                }
            }
            else if(textFromScreen.equals("Select your preferred add-on on the right: "))
            {
                selectedItemIndex = Integer.parseInt(inputText);
                numberButtonText.setText("");
                if (selectedItemIndex >= 1 && selectedItemIndex <= vendingMachine.getAddOns().length) {
                    screenError.setVisible(false);
                    vendingMachine.selectIngredient(vendingMachine.getAddOns(), selectedItemIndex, selectedIngredients);
                    screenText.setText("Enter the denominations of your payment");
                    for (int i = 0; i < 10; i++) {
                        redButtons[i].setDisable(true);
                    }
                    for (Button denomButton : denomButtonsArray) {
                        denomButton.setDisable(false);
                    }
                }
                else
                {
                    screenError.setVisible(true);
                }
            }
            else if(textFromScreen.equals("Enter the denominations of your payment"))
            {
                if (selectedItemIndex > 0)
                {
                    double totalPayment = 0;
                    String text = numberButtonText.getText();
                    String[] numbersArray = text.split(" ");
                    Double[] denominations = new Double[numbersArray.length];
                    for (int i = 0; i < numbersArray.length; i++) {
                        denominations[i] = Double.parseDouble(numbersArray[i]);
                        totalPayment += Double.parseDouble(numbersArray[i]);
                    }
                    Item item = vendingMachine.createItem(selectedIngredients);
                    double itemPrice = item.getItemPrice();
                    if (totalPayment < itemPrice)
                    {
                        numberButtonText.setText("");
                        screenError.setVisible(true);
                    }
                    else {
                        String output = "";
                        output = vendingMachine.processPayment(selectedIngredients, denominations, item);
                        numberButtonText.setText(output);
                        screenError.setVisible(false);
                        for (Button denomButton : denomButtonsArray) {
                            denomButton.setDisable(true);
                        }
                        screenText.setText("Please press Enter if you want to buy again");
                        updateVendingMachine();
                    }
                }
                else
                {
                    // for 0 input
                    String text = numberButtonText.getText();
                    String[] numbersArray = text.split(" ");
                    Double[] denominations = new Double[numbersArray.length];
                    for (int i = 0; i < numbersArray.length; i++) {
                        denominations[i] = Double.parseDouble(numbersArray[i]);
                    }

                    Map<Double, Integer> denominationCountMap = new HashMap<>();
                    for (double denomination : denominations) {
                        int count = denominationCountMap.getOrDefault(denomination, 0);
                        denominationCountMap.put(denomination, count + 1);
                    }

                    String changeOutput = "Dispensing change with the following denominations:\n";
                    for (Map.Entry<Double, Integer> entry : denominationCountMap.entrySet()) {
                        double denominationValue = entry.getKey();
                        int count = entry.getValue();
                        changeOutput += String.format("%.0f count: %d\n", denominationValue, count);
                    }

                    numberButtonText.setText(changeOutput);
                    screenText.setText("Please press Enter if you want to buy again");
                    for (Button denomButton : denomButtonsArray) {
                        denomButton.setDisable(true);
                    }
                }
            }
            else if(textFromScreen.equals("Please press Enter if you want to buy again"))
            {
                resetVendingMachine();
            }
        });

        Set<Button> denomButtons = denominationsMap.keySet();
        for (Iterator<Button> iterator = denomButtons.iterator(); iterator.hasNext(); ) {
            Button denomButton = iterator.next();
            denomButton.setOnAction(event -> {
                // Get the denomination value associated with the pressed button
                int denominationValue = denominationsMap.get(denomButton);

                // Append the denomination value to the existing text in numberButtonText
                String currentText = numberButtonText.getText();
                if (currentText.isEmpty()) {
                    numberButtonText.setText(Integer.toString(denominationValue));
                } else {
                    numberButtonText.setText(currentText + " " + denominationValue);
                }
            });
        }

        updateVendingMachine();
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

        backButton.setOnAction(this::goBackToMenu1);
    }

    private void updateVendingMachine()
    {
        ItemSlots[] slots = vendingMachine.getSlots();
        for (int i = 0; i < itemLabels.length; i++) {
            if (i < slots.length) {
                ItemSlots slot = slots[i];
                if (slot != null && slot.isAvailable()) {
                    Item item = slot.getItem();
                    itemLabels[i].setText(item.getItemName());
                    String itemprice = Double.toString(item.getItemPrice());
                    String itemcalories = Double.toString(item.getItemCalories());
                    String quantity = Integer.toString(slot.getQuantity());
                    priceLabels[i].setText(itemprice);
                    caloriesLabels[i].setText(itemcalories);
                    quantityLabels[i].setText(quantity);
                } else {
                    // If the slot is not available or is null, set an empty label
                    itemLabels[i].setText("N/A");
                    priceLabels[i].setText("N/A");
                    caloriesLabels[i].setText("N/A");
                    quantityLabels[i].setText("N/A");
                }
            } else {
                // If the vending machine has fewer slots, set empty labels for the remaining slots
                itemLabels[i].setText("MISSING");
                priceLabels[i].setText("MISSING");
                caloriesLabels[i].setText("MISSING");
                quantityLabels[i].setText("MISSING");
            }
        }

        ItemSlots milk[] = vendingMachine.getMilk();
        ItemSlots sweetener[] = vendingMachine.getSweetener();
        ItemSlots extras[] = vendingMachine.getAddOns();

        for (int i = 9; i <= 11; i++) {
            int index = i - 9;
            if (index < milk.length) {
                ItemSlots slot = milk[index];
                if (slot != null && slot.isAvailable()) {
                    Item item = slot.getItem();
                    priceLabels[i].setText(Double.toString(item.getItemPrice()));
                    caloriesLabels[i].setText(Double.toString(item.getItemCalories()));
                    quantityLabels[i].setText(Integer.toString(slot.getQuantity()));
                } else {
                    priceLabels[i].setText("N/A");
                    caloriesLabels[i].setText("N/A");
                    quantityLabels[i].setText("N/A");
                }
            } else {
                priceLabels[i].setText("MISSING");
                caloriesLabels[i].setText("MISSING");
                quantityLabels[i].setText("MISSING");
            }
        }

        for (int i = 12; i <= 14; i++) {
            int index = i - 12;
            if (index < sweetener.length) {
                ItemSlots slot = sweetener[index];
                if (slot != null && slot.isAvailable()) {
                    Item item = slot.getItem();
                    priceLabels[i].setText(Double.toString(item.getItemPrice()));
                    caloriesLabels[i].setText(Double.toString(item.getItemCalories()));
                    quantityLabels[i].setText(Integer.toString(slot.getQuantity()));
                } else {
                    priceLabels[i].setText("N/A");
                    caloriesLabels[i].setText("N/A");
                    quantityLabels[i].setText("N/A");
                }
            } else {
                priceLabels[i].setText("MISSING");
                caloriesLabels[i].setText("MISSING");
                quantityLabels[i].setText("MISSING");
            }
        }

        for (int i = 15; i <= 17; i++) {
            int index = i - 15;
            if (index < extras.length) {
                ItemSlots slot = extras[index];
                if (slot != null && slot.isAvailable()) {
                    Item item = slot.getItem();
                    priceLabels[i].setText(Double.toString(item.getItemPrice()));
                    caloriesLabels[i].setText(Double.toString(item.getItemCalories()));
                    quantityLabels[i].setText(Integer.toString(slot.getQuantity()));
                } else {
                    priceLabels[i].setText("N/A");
                    caloriesLabels[i].setText("N/A");
                    quantityLabels[i].setText("N/A");
                }
            } else {
                priceLabels[i].setText("MISSING");
                caloriesLabels[i].setText("MISSING");
                quantityLabels[i].setText("MISSING");
            }
        }
    }

    private void resetVendingMachine()
    {
        screenError.setVisible(false);
        numberButtonText.setText("");
        screenText.setText("Select a fruit on the left (or enter 0 for change): ");
        for (int i = 0; i < 10; i++) {
            redButtons[i].setDisable(false);
        }
    }

    private void goBackToMenu1(ActionEvent event) {
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

    public void setVendingMachine(SpecialVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

}