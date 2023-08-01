package project.ccprog3mco2gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import project.ccprog3mco2gui.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


public class MenuController {
    private ArrayList<RegularVendingMachine> regularVendingMachines;
    private ArrayList<SpecialVendingMachine> specialVendingMachines;
    private ObservableList<String> regularVendingMachineNames;

    private Stage driverStage;
    @FXML
    private ListView<String> regularVendingMachineListView;

    @FXML
    private Label titleText;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private TextField textField;

    @FXML
    private Label nameText;

    @FXML
    private Label errorText;

    private int selectedRegularVendingMachineIndex;


    public int getRegularVendingMachineIndex()
    {
        return selectedRegularVendingMachineIndex;
    }
    public void setDriverStage(Stage driverStage) {
        this.driverStage = driverStage;
    }



    @FXML
    public void initialize() {
        regularVendingMachineListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                selectedRegularVendingMachineIndex = newValue.intValue();
                openRegular();
            }
        });
        regularVendingMachines = new ArrayList<>();
        specialVendingMachines = new ArrayList<>();
        regularVendingMachineNames = FXCollections.observableArrayList();
        regularVendingMachineListView.setItems(regularVendingMachineNames);
        btn1.setOnAction(e -> createVendingMachine());
        btn2.setOnAction(e -> testVendingMachine());
    }

    public void showButtons()
    {
        btn1.setVisible(true);
        btn2.setVisible(true);
        btn3.setVisible(true);
    }
    public void hideButtons()
    {
        btn1.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
    }
    public void enableButtons()
    {
        btn1.setDisable(false);
        btn2.setDisable(false);
        btn3.setDisable(false);
    }
    public void disableButtons()
    {
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
    }


    @FXML
    public void goBackToMainMenu()
    {
        btn1.setOnAction(e -> createVendingMachine());
        btn2.setOnAction(e -> testVendingMachine());
        titleText.setText("Vending Machine Simulator");
        btn1.setText("Create a Vending Machine");
        btn2.setText("Test a Vending Machine");
        textField.setVisible(false);
        nameText.setVisible(false);
        textField.setDisable(true);
        showButtons();
        enableButtons();
    }

    @FXML
    public void createVendingMachine() {
        titleText.setText("Create a Vending Machine");
        btn1.setText("Regular Vending Machine");
        btn2.setText("Special Vending Machine");
        btn1.setOnAction(e -> regularVendingMachine());
        btn2.setOnAction(e -> specialVendingMachine());
    }

    @FXML
    public void testVendingMachine() {
        titleText.setText("Test a Vending Machine");
        btn1.setText("Vending Features");
        btn2.setText("Maintenance Features");
        btn1.setOnAction(e -> vendingFeatures());
        btn2.setOnAction(e -> maintenanceFeatures());
    }

    @FXML
    public void vendingFeatures()
    {
        titleText.setText("Choose a vending machine type");
        btn1.setText("Regular Vending Machine");
        btn2.setText("Special Vending Machine");
        btn1.setOnAction(e -> chooseRegularVendingMachine());
        btn2.setOnAction(e -> chooseSpecialVendingMachine());
    }

    public void chooseRegularVendingMachine()
    {
        if (regularVendingMachines.isEmpty()) {
            errorText.setVisible(true);
        }
        else
        {
            titleText.setText("Choose a regular vending machine");
            hideButtons();
            disableButtons();
            regularVendingMachineListView.setVisible(true);
            regularVendingMachineNames.clear();

            for (int i = 0; i < regularVendingMachines.size(); i++) {
                RegularVendingMachine vendingMachine = regularVendingMachines.get(i);
                regularVendingMachineNames.add(vendingMachine.getName());
            }
        }
    }

    public void chooseSpecialVendingMachine()
    {
        titleText.setText("Choose a special vending machine");
        hideButtons();
        disableButtons();
    }

    public void openRegular()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/regularvendingmachine.fxml"));
            fxmlLoader.setController(new RegularVendingMachineController(selectedRegularVendingMachineIndex));
            Parent root = fxmlLoader.load();
            Stage vendingMachineStage = new Stage();
            vendingMachineStage.setTitle("Vending Machine");
            vendingMachineStage.setScene(new Scene(root, 794, 728));
            vendingMachineStage.show();

            driverStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSpecial()
    {

    }
    @FXML
    public void maintenanceFeatures()
    {

    }

    @FXML
    public void regularVendingMachine()
    {
        titleText.setText("Regular Vending Machine");
        hideButtons();
        disableButtons();
        textField.setVisible(true);
        textField.setDisable(false);
        nameText.setVisible(true);
    }

    @FXML
    public void onEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String vendingtype = titleText.getText();
            String vendingMachineName = textField.getText().trim();
            if (vendingMachineName.isEmpty()) {
                errorText.setVisible(true);
            } else {
                errorText.setVisible(false);
                goBackToMainMenu();
                if ("Regular Vending Machine".equals(vendingtype)) {
                    RegularVendingMachine regularVendingMachine = new RegularVendingMachine(vendingMachineName);
                    regularVendingMachines.add(regularVendingMachine);
                } else if ("Special Vending Machine".equals(vendingtype)) {
                    SpecialVendingMachine specialVendingMachine = new SpecialVendingMachine(vendingMachineName);
                    specialVendingMachines.add(specialVendingMachine);
                }
            }
        }
    }

    @FXML
    public void specialVendingMachine()
    {
        titleText.setText("Special Vending Machine");
        hideButtons();
        disableButtons();
        textField.setVisible(true);
        textField.setDisable(false);
        nameText.setVisible(true);
    }

    @FXML
    public void exitProgram()
    {
        Platform.exit();
    }
}