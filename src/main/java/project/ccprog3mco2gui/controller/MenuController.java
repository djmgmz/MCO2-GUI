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
import project.ccprog3mco2gui.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


public class MenuController {
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

    private VendingMachineService vendingMachineService;
    private int selectedRegularVendingMachineIndex;
    private int selectedSpecialVendingMachineIndex;
    private boolean isInMaintenanceMode;

    public void setDriverStage(Stage driverStage) {
        this.driverStage = driverStage;
    }

    @FXML
    public void initialize() {
        isInMaintenanceMode = false;

        vendingMachineService = new VendingMachineService();
        regularVendingMachineListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                selectedRegularVendingMachineIndex = newValue.intValue();
                openRegular();
            }
        });


//        specialVendingMachineListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.intValue() >= 0) {
//                selectedSpecialVendingMachineIndex = newValue.intValue(); // fixed: use different index for special vending machines
//                openSpecial();
//            }
//        });
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
        btn3.setOnAction(e -> exitProgram());
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
        btn3.setOnAction(e -> goBackToMainMenu());
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
        isInMaintenanceMode = false;
        titleText.setText("Choose a vending machine type");
        btn1.setText("Regular Vending Machine");
        btn2.setText("Special Vending Machine");
        btn1.setOnAction(e -> chooseRegularVendingMachine());
        btn2.setOnAction(e -> chooseSpecialVendingMachine());
        btn3.setOnAction(e -> goBackToMainMenu());
    }

    public void chooseRegularVendingMachine()
    {
        if (vendingMachineService.getRegularVendingMachines().isEmpty()) {
            errorText.setVisible(true);
        }
        else
        {
            titleText.setText("Choose a regular vending machine");
            hideButtons();
            disableButtons();
            regularVendingMachineListView.setVisible(true);
            regularVendingMachineNames.clear();

            for (int i = 0; i < vendingMachineService.getRegularVendingMachines().size(); i++) {
                RegularVendingMachine vendingMachine = vendingMachineService.getRegularVendingMachines().get(i);
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
        RegularVendingMachine vendingMachine = vendingMachineService.getRegularVendingMachines().get(selectedRegularVendingMachineIndex);
        try {
            FXMLLoader fxmlLoader;
            if (isInMaintenanceMode) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/maintenance.fxml")); // update the path to the maintenance FXML
                fxmlLoader.setController(new MaintenanceController(vendingMachine));
            } else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/regularvendingmachine.fxml"));
                fxmlLoader.setController(new RegularVendingMachineController(vendingMachine));
            }
            Parent root = fxmlLoader.load();
            Stage vendingMachineStage = new Stage();
            vendingMachineStage.setTitle(isInMaintenanceMode ? "Maintenance" : "Vending Machine");
            vendingMachineStage.setScene(new Scene(root, 794, 728));
            vendingMachineStage.show();

            driverStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public void openSpecial()
//    {
//        SpecialVendingMachine vendingMachine = vendingMachineService.getSpecialVendingMachines();
//        try {
//            FXMLLoader fxmlLoader;
//            if (isInMaintenanceMode) {
//                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/specialmaintenance.fxml")); // updated path
//                fxmlLoader.setController(new MaintenanceController(vendingMachine));
//            } else {
//                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/specialvendingmachine.fxml")); // updated path
////                fxmlLoader.setController(new SpecialVendingMachineController(vendingMachine)); // updated controller
//            }
//            Parent root = fxmlLoader.load();
//            Stage vendingMachineStage = new Stage();
//            vendingMachineStage.setTitle(isInMaintenanceMode ? "Maintenance" : "Vending Machine");
//            vendingMachineStage.setScene(new Scene(root, 794, 728));
//            vendingMachineStage.show();
//
//            driverStage.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    public void maintenanceFeatures()
    {
        this.isInMaintenanceMode = true;
        titleText.setText("Choose a vending machine type");
        btn1.setText("Regular Vending Machine");
        btn2.setText("Special Vending Machine");
        btn1.setOnAction(e -> chooseRegularVendingMachine());
        btn2.setOnAction(e -> chooseSpecialVendingMachine());
        btn3.setOnAction(e -> goBackToMainMenu());
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
                    vendingMachineService.addRegularMachine(regularVendingMachine);
                } else if ("Special Vending Machine".equals(vendingtype)) {
                    SpecialVendingMachine specialVendingMachine = new SpecialVendingMachine(vendingMachineName);
                    vendingMachineService.addSpecialMachine(specialVendingMachine);
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