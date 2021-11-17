module tscalise.cipherProject.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens tscalise.cryptographyProject to javafx.fxml;
    exports tscalise.cryptographyProject;
    exports tscalise.cryptographyProject.controllers;
    opens tscalise.cryptographyProject.controllers to javafx.fxml;
}