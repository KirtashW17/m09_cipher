module tscalise.cipherProject.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens tscalise.cipherProject to javafx.fxml;
    exports tscalise.cipherProject;
    exports tscalise.cipherProject.controllers;
    opens tscalise.cipherProject.controllers to javafx.fxml;
}