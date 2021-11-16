package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;

import static tscalise.cipherProject.libraries.utils.Utilities.showFileChooser;

// TODO VERIFY HASH -> Option generate checksum, supocion sign the checksum

public class AsymmetricEncryptionController extends EncryptionController {
    @FXML
    private RadioButton RBcifrar;
    @FXML
    private ToggleGroup TGaccion;
    @FXML
    private RadioButton RBdescifrar;
    @FXML
    private TextField TFsourceFile;
    @FXML
    private TextField TFdestinationFile;
    @FXML
    private RadioButton aKeystoreaPrivate;
    @FXML
    private ToggleGroup TGkey;
    @FXML
    private RadioButton RBaKeystore_aPublic;
    @FXML
    private RadioButton RBaKeystore_bPublic;
    @FXML
    private RadioButton RBbKeystore_bPrivate;
    @FXML
    private RadioButton RBbKeystore_bPublic;
    @FXML
    private RadioButton RBbKeystore_aPublic;

    @FXML
    public void switchAction() {
        // TODO
    }

    @FXML
    public void switchKey() {
        // TODO
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
