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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
/**
 * Controller for the SpecialVendingMachine application.
 *
 * This class is responsible for managing the user interface and business logic for a
 * special type of vending machine. It implements JavaFX's Initializable interface to
 * provide initialization logic for the GUI components.
 *
 * This controller interacts with the GUI components defined in an associated FXML file,
 * handling user input and updating the display as needed.
 * It also communicates with the model classes to carry out the core functionality of
 * the application.
 */
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
    private Text screenText, finalPrice;

    private SpecialVendingMachine vendingMachine;
    private ArrayList<Item> selectedIngredients;
    @FXML
    private Label numberButtonText;

    @FXML
    private Button denom1, denom5, denom10, denom20, denom50, denom100, denom200, denom500, denom1000;

    private Button[] denomButtonsArray;
    @FXML
    private Label screenError;

    @FXML Button enterButton;

    @FXML Button backButton;

    private int selectedItemIndex = -1;
    private boolean itemCheck;
    private Timeline timeline;
    private boolean isFinished;
    /**
     * Initializes the GUI components for the Vending Machine and sets up the appropriate event listeners.
     *
     * It initializes all ImageView, Label, and Button elements used in the application, and sets up
     * event listeners for the various user interactions like button clicks and text field changes.
     * It also maps denomination buttons to their respective denomination values.
     *
     * This method is called automatically by JavaFX when the FXML file associated with this controller is loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        redButtons = new ImageView[]{redButton0, redButton1, redButton2, redButton3, redButton4, redButton5, redButton6, redButton7, redButton8, redButton9};
        itemLabels = new Label[]{item1, item2, item3, item4, item5, item6, item7, item8, item9};
        priceLabels = new Label[]{price1, price2, price3, price4, price5, price6, price7, price8, price9, price10, price11, price12, price13, price14, price15, price16, price17, price18};
        caloriesLabels = new Label[]{calories1, calories2, calories3, calories4, calories5, calories6, calories7, calories8, calories9, calories10,calories11,calories12,calories13,calories14,calories15,calories16,calories17,calories18};
        quantityLabels = new Label[]{quantity1, quantity2, quantity3, quantity4, quantity5, quantity6, quantity7, quantity8, quantity9, quantity10, quantity11, quantity12, quantity13, quantity14, quantity15, quantity16, quantity17, quantity18};
        denomButtonsArray = new Button[]{denom1, denom5, denom10, denom20, denom50, denom100, denom200, denom500,denom1000};

        ArrayList<Item> selectedIngredients = new ArrayList<>();
        selectedIngredients.clear();

        Map<Button, Integer> denominationsMap = new HashMap<>();
        denominationsMap.put(denom1, 1);
        denominationsMap.put(denom5, 5);
        denominationsMap.put(denom10, 10);
        denominationsMap.put(denom20, 20);
        denominationsMap.put(denom50, 50);
        denominationsMap.put(denom100, 100);
        denominationsMap.put(denom200, 200);
        denominationsMap.put(denom500, 500);
        denominationsMap.put(denom1000, 1000);

        resetVendingMachine();
        isFinished = false;
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
                            itemCheck = vendingMachine.selectIngredient(vendingMachine.getSlots(), selectedItemIndex, selectedIngredients);
                            if(itemCheck == true)
                            {
                                screenText.setText("Select your preferred milk on the right: ");
                            }
                            else {
                                screenError.setVisible(true);
                            }
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
                    itemCheck = vendingMachine.selectIngredient(vendingMachine.getMilk(), selectedItemIndex, selectedIngredients);
                    if(itemCheck == true)
                    {
                        screenText.setText("Select your preferred sweetener on the right: ");
                    }
                    else {
                        screenError.setVisible(true);
                    }
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
                    itemCheck = vendingMachine.selectIngredient(vendingMachine.getAddOns(), selectedItemIndex, selectedIngredients);
                    if(itemCheck == true)
                    {
                        double finalPriceValue = vendingMachine.calculatePrice(selectedIngredients);
                        finalPrice.setText(String.format("%.2f", finalPriceValue));
                        finalPrice.setVisible(true);
                        screenText.setText("Enter the denominations of your payment");
                        for (int i = 0; i < denomButtonsArray.length; i++) {
                            redButtons[i].setDisable(true);
                        }
                        for (Button denomButton : denomButtonsArray) {
                            denomButton.setDisable(false);
                        }
                    }
                    else {
                        screenError.setVisible(true);
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
                        if (numbersArray[i] != null && !numbersArray[i].isEmpty()) {
                            try {
                                denominations[i] = Double.parseDouble(numbersArray[i]);
                                totalPayment += Double.parseDouble(numbersArray[i]);
                            } catch (NumberFormatException e) {
                                screenError.setVisible(true);
                            }
                        }
                    }
                    Item item = vendingMachine.createItem(selectedIngredients);
                    double itemPrice = item.getItemPrice();
                    if (totalPayment < itemPrice)
                    {
                        finalPrice.setVisible(false);
                        numberButtonText.setText("");
                        screenError.setVisible(true);
                    }
                    else {
                        finalPrice.setVisible(false);
                        String output = "";
                        output = vendingMachine.processPayment(selectedIngredients, denominations, item);
                        numberButtonText.setText(output);
                        screenError.setVisible(false);
                        enterButton.setDisable(true);
                        setupAnimation(selectedIngredients, numberButtonText, () -> {
                                    enterButton.setDisable(false);
                                    for (Button denomButton : denomButtonsArray) {
                                        denomButton.setDisable(true);
                                    }
                                    screenText.setText("Please press Enter if you want to buy again");
                                    updateVendingMachine();
                                    selectedIngredients.clear();
                                    isFinished = true;
                                });
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
                    finalPrice.setVisible(false);
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
                System.out.println("this works");
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
        if(!isFinished)
        {
            vendingMachine.revertIngredientQuantities(selectedIngredients);

        }
        backButton.setOnAction(event -> {
            if (!isFinished) {
                vendingMachine.revertIngredientQuantities(selectedIngredients);
            }
            goBackToMenu1(event);
        });

    }
    /**
     * Updates the display of the vending machine.
     *
     * This method retrieves all the item slots from the vending machine, and updates the
     * corresponding labels in the GUI to match the current state of the items in the machine.
     *
     * The information displayed for each slot includes the item name, price, calories and quantity.
     * If a slot is unavailable or null, the labels for that slot are set to "N/A". If the vending machine
     * has fewer slots than expected, the labels for the remaining slots are set to "MISSING".
     *
     * Besides the main item slots, the vending machine also includes slots for milk, sweetener, and
     * extra add-ons. These are treated separately, and the display of these slots is updated in a similar
     * manner to the main item slots.
     */
    private void updateVendingMachine()
    {
        ItemSlots[] slots = vendingMachine.getSlots();
        for (int i = 0; i < itemLabels.length; i++) {
            if (i < slots.length) {
                ItemSlots slot = slots[i];
                if (slot.getQuantity() == 0)
                {
                    slot.setAvailability(false);
                }
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
    /**
     * Resets the vending machine interface.
     *
     * This method sets the error screen to be invisible, clears the number button text,
     * sets the screen text to a prompt for the user to select a fruit or enter 0 for change,
     * and enables all red buttons.
     */
    private void resetVendingMachine()
    {
        screenError.setVisible(false);
        numberButtonText.setText("");
        screenText.setText("Select a fruit on the left (or enter 0 for change): ");
        for (int i = 0; i < 10; i++) {
            redButtons[i].setDisable(false);
        }
    }

    /**
     * Returns the user to the main menu.
     *
     * This method loads the main menu and sets the vending machine service to the singleton instance.
     * It then retrieves the current stage from the event source and sets the scene to the main menu.
     *
     * @param event the event that triggers this method
     */
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

    /**
     * Sets the vending machine to the specified instance.
     *
     * @param vendingMachine the vending machine instance to set
     */
    public void setVendingMachine(SpecialVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * Creates a list of animation words for the selected ingredients.
     *
     * This method iterates through the list of selected ingredients, and for each ingredient,
     * it adds a corresponding action (e.g., "Blending Banana..." for a banana) to a list of steps.
     * After all the ingredients have been processed, it adds "Smoothie Done!" to the list of steps.
     *
     * @param selectedIngredients the list of selected ingredients
     * @return the list of animation words
     */
    public ArrayList<String> setAnimationWords(ArrayList<Item> selectedIngredients)
    {
        ArrayList<String> steps =new ArrayList<>();

        for (int i = 0; i < selectedIngredients.size(); i++) {
            Item ingredient = selectedIngredients.get(i);
            String itemName = ingredient.getItemName();
            switch(itemName)
            {
                case "Banana":
                    steps.add("Blending Banana...");
                    break;
                case "Strawberry":
                    steps.add("Cutting up strawberries...");
                    break;
                case "Blueberry":
                    steps.add("Adding Blueberries...");
                    break;
                case "Mango":
                    steps.add("Slicing Mango...");
                    break;
                case "Pineapple":
                    steps.add("Dicing Pineapple...");
                    break;
                case "Kiwi":
                    steps.add("Peeling and Slicing Kiwi...");
                    break;
                case "Raspberry":
                    steps.add("Mashing Raspberries...");
                    break;
                case "Peach":
                    steps.add("Slicing Peaches...");
                    break;
                case "Gomu-Gomu":
                    steps.add("Stretching Gomu-Gomu...");
                    break;
                case "Almond Milk":
                    steps.add("Pouring Almond Milk...");
                    break;
                case "Soy Milk":
                    steps.add("Adding Soy Milk...");
                    break;
                case "Cow's Milk":
                    steps.add("Milking Cow...");
                    break;
                case "Honey":
                    steps.add("Drizzling Honey...");
                    break;
                case "Stevia":
                    steps.add("Sprinkling Stevia...");
                    break;
                case "Agave Syrup":
                    steps.add("Mixing in Agave Syrup...");
                    break;
                case "Yogurt":
                    steps.add("Scooping Yogurt...");
                    break;
                case "Chia Seeds":
                    steps.add("Throwing in Chia Seeds...");
                    break;
                case "Protein Powder":
                    steps.add("Mixing in some Protein Powder...");
                    break;
                default:
                    steps.add("Adding unknown ingredient...");
                    break;
            }
        }
        steps.add("Smoothie Done!");

        return steps;
    }
    /**
     * Sets up an animation for displaying the process of creating a smoothie.
     *
     * This method creates a list of steps based on the selected ingredients,
     * then sets up a Timeline animation in JavaFX where each step is displayed with a delay.
     * It begins with an initial delay of 3 seconds, then each subsequent step is displayed
     * after an additional delay of 3 seconds. After all the steps, the specified callback
     * function is called.
     *
     * @param selectedIngredients the list of selected ingredients
     * @param messageLabel the label in which the step messages are displayed
     * @param onFinishedCallback the function to be called after the last step message is displayed
     */
    private void setupAnimation(ArrayList<Item> selectedIngredients, Label messageLabel, Runnable onFinishedCallback) {
        // Get the list of steps for the animation
        ArrayList<String> steps = setAnimationWords(selectedIngredients);

        // Create a new Timeline
        timeline = new Timeline();

        // Set an initial delay of 3 seconds before the animation starts
        KeyFrame initialDelayFrame = new KeyFrame(Duration.seconds(3), event -> {
            // This code will execute after the initial delay
            messageLabel.setText(steps.get(0)); // Set the first step message
        });
        timeline.getKeyFrames().add(initialDelayFrame);

        // Add keyframes for each step
        for (int i = 1; i < steps.size() - 1; i++) {
            Duration delay = Duration.seconds(i + 3); // Add 3 seconds to each subsequent step
            String stepMessage = steps.get(i);
            KeyFrame keyFrame = new KeyFrame(delay, event -> {
                // This code will execute after the specified delay
                messageLabel.setText(stepMessage);
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        KeyFrame finalStepFrame = new KeyFrame(Duration.seconds(steps.size() + 3), event -> {
            // This code will execute after the last step
            messageLabel.setText(steps.get(steps.size() - 1)); // Show the last step message
            onFinishedCallback.run();
        });
        timeline.getKeyFrames().add(finalStepFrame);

        // Start the Timeline
        timeline.play();
    }

}