/**
 * Driver class that acts as the entry point for the JavaFX application.
 * This class extends from javafx.application.Application.
 *
 * @author Dominic Joel M. Gomez, Peter B. Parker
 * @version 3.0
 * @since 2023-08-03
 */
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

    /**
     * The start method is the main entry point for all JavaFX applications.
     * It is called after the init method has returned and after the system is ready for 
     * the application to begin running.
     *
     * @param stage the primary stage for this application, onto which 
     * the application scene can be set. Applications may create other stages, 
     * if needed, but they will not be primary stages.
     * @throws IOException if an input or output error is detected when the servlet handles the request.
     */
    @Override
    public void start(Stage stage) throws IOException {
        VendingMachineService vendingMachineService = VendingMachineService.getInstance();

        // Add a default regular vending machine
        RegularVendingMachine defaultRegularVendingMachine = new RegularVendingMachine("Default Vending Machine");
        vendingMachineService.addRegularMachine(defaultRegularVendingMachine);
        // Add a default special vending machine
        SpecialVendingMachine defaultSpecialVendingMachine = new SpecialVendingMachine("Default Vending Machine");
        vendingMachineService.addSpecialMachine(defaultSpecialVendingMachine);

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("menu1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        MenuController menuController = fxmlLoader.getController();
        menuController.setVendingMachineService(vendingMachineService);
        menuController.setDriverStage(stage);

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method which launches the JavaFX application.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        launch();
    }
}
