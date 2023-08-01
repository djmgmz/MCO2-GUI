package project.ccprog3mco2gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class RegularVendingMachineController implements Initializable {

    @FXML
    private ImageView redButton1;
    @FXML
    private ImageView redButton2;
    @FXML
    private ImageView redButton3;
    @FXML
    private ImageView redButton4;
    @FXML
    private ImageView redButton5;
    @FXML
    private ImageView redButton6;
    @FXML
    private ImageView redButton7;
    @FXML
    private ImageView redButton8;
    @FXML
    private ImageView redButton9;
    @FXML
    private ImageView redButton0;
    private ImageView[] redButtons = new ImageView[10];

    private int selectedRegularVendingMachineIndex;

    public RegularVendingMachineController(int selectedRegularVendingMachineIndex) {
        this.selectedRegularVendingMachineIndex = selectedRegularVendingMachineIndex;
        System.out.println(selectedRegularVendingMachineIndex);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        redButtons = new ImageView[]{redButton0, redButton1, redButton2, redButton3, redButton4, redButton5, redButton6, redButton7, redButton8, redButton9};

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