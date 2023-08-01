module project.ccprog3mco2gui {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens project.ccprog3mco2gui to javafx.fxml;
    exports project.ccprog3mco2gui;
    exports project.ccprog3mco2gui.controller;
    opens project.ccprog3mco2gui.controller to javafx.fxml;
}