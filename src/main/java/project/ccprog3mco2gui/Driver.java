package project.ccprog3mco2gui;

import project.ccprog3mco2gui.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.ccprog3mco2gui.model.VendingMachineService;
import java.io.IOException;

public class Driver extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        VendingMachineService vendingMachineService = new VendingMachineService();

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("menu1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();

        MenuController menuController = fxmlLoader.getController();
        menuController.setDriverStage(stage);
        menuController.setVendingMachineService(vendingMachineService);
    }

    public static void main(String[] args) {
        launch();
    }
}