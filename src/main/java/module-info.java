module tscalise.cipherProject.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens tscalise.cryptographyProject to javafx.fxml;
    exports tscalise.cryptographyProject;
    exports tscalise.cryptographyProject.controllers;
    opens tscalise.cryptographyProject.controllers to javafx.fxml;
    exports tscalise.cryptographyProject.controllers.encryption;
    opens tscalise.cryptographyProject.controllers.encryption to javafx.fxml;
    exports tscalise.cryptographyProject.controllers.hashing;
    opens tscalise.cryptographyProject.controllers.hashing to javafx.fxml;
    exports tscalise.cryptographyProject.controllers.cipher;
    opens tscalise.cryptographyProject.controllers.cipher to javafx.fxml;
    exports tscalise.cryptographyProject.controllers.digitalSignature;
    opens tscalise.cryptographyProject.controllers.digitalSignature to javafx.fxml;
}