package project.ccprog3mco2gui;

import project.ccprog3mco2gui.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.ccprog3mco2gui.model.RegularVendingMachine;
import project.ccprog3mco2gui.model.SpecialVendingMachine;
import project.ccprog3mco2gui.model.VendingMachineService;
import java.io.IOException;

public class Driver extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        VendingMachineService vendingMachineService = VendingMachineService.getInstance();

        // Add a default regular vending machine (you can customize this as needed)
        RegularVendingMachine defaultRegularVendingMachine = new RegularVendingMachine("Default Vending Machine");
        vendingMachineService.addRegularMachine(defaultRegularVendingMachine);
        SpecialVendingMachine defaultSpecialVendingMachine = new SpecialVendingMachine("Default Vending Machine");
        vendingMachineService.addSpecialMachine(defaultSpecialVendingMachine);

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("menu1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        MenuController menuController = fxmlLoader.getController(); // get the instance of the MenuController from FXMLLoader
        menuController.setVendingMachineService(vendingMachineService); // assuming you have this method in your MenuController class
        menuController.setDriverStage(stage);

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}