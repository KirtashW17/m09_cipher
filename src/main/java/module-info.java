module tscalise.cipherProject.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens tscalise.cipherProject to javafx.fxml;
    exports tscalise.cipherProject;
    exports tscalise.cipherProject.controllers;
    opens tscalise.cipherProject.controllers to javafx.fxml;
}