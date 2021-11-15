package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SymmetricEncryptionController {

    @FXML
    private RadioButton RBcifrar;
    @FXML
    private ToggleGroup TGAccion;
    @FXML
    private RadioButton RBdescifrar;
    @FXML
    private TextField TFsourceFile;
    @FXML
    private TextField TFdestinationFile;
    @FXML
    private CheckBox CBuseSeed;
    @FXML
    private RadioButton RBaSecretKey;
    @FXML
    private ToggleGroup TGkey;
    @FXML
    private RadioButton RBbSecretKey;
    @FXML
    private RadioButton RBmanualSeed;
    @FXML
    private ToggleGroup TGseedMode;
    @FXML
    private RadioButton RBautoSeed;
    @FXML
    private TextField TFmanualSeed;
    @FXML
    private TextField TFautoSeed;

    @FXML
    public void switchAction() {

    }

    @FXML
    public void pressSelectSourceButton() {

    }

    @FXML
    public void pressSelectDestinationButton() {

    }

    @FXML
    public void switchUseSeed() {

    }

    @FXML
    public void switchSeedMode() {

    }

    @FXML
    public void pressClearButton() {

    }

    @FXML
    public void pressActionButton() {

    }

    @FXML
    public void pressExitButton() {
        Stage stage = (Stage) RBcifrar.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
