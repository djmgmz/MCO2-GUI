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
    private ObservableList<String> specialVendingMachineNames;

    private Stage driverStage;
    @FXML
    private ListView<String> regularVendingMachineListView;

    @FXML
    private ListView<String> specialVendingMachineListView;

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
    /**
     * Sets the vending machine service to be used by this controller.
     *
     * @param vendingMachineService the vending machine service to be used
     */
    public void setVendingMachineService(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    /**
     * Default constructor for the MenuController class.
     */
    public MenuController() {
    }
    /**
     * Overloaded constructor for the MenuController class.
     *
     * @param vendingMachineService the vending machine service to be used
     */
    public MenuController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    /**
     * Sets the driver stage to be used by this controller.
     *
     * @param driverStage the driver stage to be used
     */
    public void setDriverStage(Stage driverStage) {
        this.driverStage = driverStage;
    }

    /**
     * Initializes the controller.
     *
     * The initialization involves setting up listeners for the list views and
     * setting the action for the buttons. It also initializes the vending machine service.
     */
    @FXML
    public void initialize() {
        isInMaintenanceMode = false;

        vendingMachineService = VendingMachineService.getInstance();;
        regularVendingMachineListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                selectedRegularVendingMachineIndex = newValue.intValue();
                openRegular();
            }
        });

        specialVendingMachineListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                selectedSpecialVendingMachineIndex = newValue.intValue();
                openSpecial();
            }
        });
        specialVendingMachineNames = FXCollections.observableArrayList();
        specialVendingMachineListView.setItems(specialVendingMachineNames);
        regularVendingMachineNames = FXCollections.observableArrayList();
        regularVendingMachineListView.setItems(regularVendingMachineNames);
        btn1.setOnAction(e -> createVendingMachine());
        btn2.setOnAction(e -> testVendingMachine());
    }


    /**
     * Makes the buttons visible.
     */
    public void showButtons()
    {
        btn1.setVisible(true);
        btn2.setVisible(true);
        btn3.setVisible(true);
    }

    /**
     * Hides the buttons.
     */
    public void hideButtons()
    {
        btn1.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
    }

    /**
     * Enables the buttons.
     */
    public void enableButtons()
    {
        btn1.setDisable(false);
        btn2.setDisable(false);
        btn3.setDisable(false);
    }


    /**
     * Disables the buttons.
     */
    public void disableButtons()
    {
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
    }

    /**
     * Resets the main menu UI to its default state and sets up the main menu button actions.
     */
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

    /**
     * Sets up the UI and button actions for the vending machine creation screen.
     */
    @FXML
    public void createVendingMachine() {
        titleText.setText("Create a Vending Machine");
        btn1.setText("Regular Vending Machine");
        btn2.setText("Special Vending Machine");
        btn1.setOnAction(e -> regularVendingMachine());
        btn2.setOnAction(e -> specialVendingMachine());
        btn3.setOnAction(e -> goBackToMainMenu());
    }

    /**
     * Sets up the UI and button actions for the vending machine testing screen.
     */
    @FXML
    public void testVendingMachine() {
        titleText.setText("Test a Vending Machine");
        btn1.setText("Vending Features");
        btn2.setText("Maintenance Features");
        btn1.setOnAction(e -> vendingFeatures());
        btn2.setOnAction(e -> maintenanceFeatures());
    }

    /**
     * Sets up the UI and button actions for the vending features screen.
     */
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

    /**
     * Displays a list of regular vending machines that the user can select for testing.
     * If no regular vending machines are available, an error message is displayed.
     */
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
    /**
     * Displays a list of special vending machines that the user can select for testing.
     * If no special vending machines are available, an error message is displayed.
     */
    public void chooseSpecialVendingMachine()
    {
        if (vendingMachineService.getSpecialVendingMachines().isEmpty()) {
            errorText.setVisible(true);
        }
        else
        {
            titleText.setText("Choose a special vending machine");
            hideButtons();
            disableButtons();
            specialVendingMachineListView.setVisible(true);
            specialVendingMachineNames.clear();

            for (int i = 0; i < vendingMachineService.getSpecialVendingMachines().size(); i++) {
                SpecialVendingMachine vendingMachine = vendingMachineService.getSpecialVendingMachines().get(i);
                specialVendingMachineNames.add(vendingMachine.getName());
            }
        }
    }
    /**
     * Opens a new window with the UI for the selected regular vending machine.
     * The UI will either be the vending machine interface or the maintenance interface,
     * depending on the value of isInMaintenanceMode.
     * @throws IllegalStateException if driverStage has not been initialized.
     */
    public void openRegular()
    {
        if (this.driverStage == null) {
            throw new IllegalStateException("driverStage has not been initialized.");
        }

        RegularVendingMachine vendingMachine = vendingMachineService.getRegularVendingMachines().get(selectedRegularVendingMachineIndex);
        try {
            FXMLLoader fxmlLoader;
            if (isInMaintenanceMode) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/maintenance.fxml"));
                MaintenanceController controller = new MaintenanceController();
                controller.setVendingMachine(vendingMachine);
                fxmlLoader.setController(controller);

            } else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/regularvendingmachine.fxml"));
                RegularVendingMachineController controller = new RegularVendingMachineController();
                controller.setVendingMachine(vendingMachine);
                fxmlLoader.setController(controller);
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

    /**
     * Opens a new window with the UI for the selected special vending machine.
     * The UI will either be the vending machine interface or the maintenance interface,
     * depending on the value of isInMaintenanceMode.
     * @throws IllegalStateException if driverStage has not been initialized.
     */
    public void openSpecial()
    {
        if (this.driverStage == null) {
            throw new IllegalStateException("driverStage has not been initialized.");
        }

        SpecialVendingMachine vendingMachine = vendingMachineService.getSpecialVendingMachines().get(selectedSpecialVendingMachineIndex);
        try {
            FXMLLoader fxmlLoader;
            if (isInMaintenanceMode) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/maintenance.fxml"));
                MaintenanceController controller = new MaintenanceController();
                controller.setVendingMachineSpecial(vendingMachine);
                fxmlLoader.setController(controller);

            } else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/project/ccprog3mco2gui/specialvendingmachine.fxml"));
                SpecialVendingMachineController controller = new SpecialVendingMachineController();
                controller.setVendingMachine(vendingMachine);
                fxmlLoader.setController(controller);
            }

            Parent root = fxmlLoader.load();
            Stage vendingMachineStage = new Stage();
            vendingMachineStage.setTitle(isInMaintenanceMode ? "Maintenance" : "Vending Machine");
            vendingMachineStage.setScene(isInMaintenanceMode ? new Scene(root, 794, 728): new Scene(root, 1315, 728));
            vendingMachineStage.show();

            driverStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sets the UI to maintenance mode, where the user can select a vending machine type (regular or special) for maintenance.
     */
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

    /**
     * Prepares the UI for the creation of a new regular vending machine. The user is prompted to enter a name for the machine.
     */
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

    /**
     * Handles the Enter key event for the text field where the vending machine name is entered.
     * If the entered name is not empty, a new vending machine of the appropriate type is created based on the current titleText.
     * @param event the KeyEvent to handle.
     */
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

    /**
     * Prepares the UI for the creation of a new special vending machine. The user is prompted to enter a name for the machine.
     */
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

    /**
     * Exits the program.
     */
    @FXML
    public void exitProgram()
    {
        Platform.exit();
    }
}