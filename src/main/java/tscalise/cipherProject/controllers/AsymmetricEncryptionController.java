package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;

import static tscalise.cipherProject.libraries.Utils.Utilities.showFileChooser;

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

    }

    // TODO CODIGO DUPLICADO (search common field and use inheritance?)
    @FXML
    public void pressSelectSourceButton() {
        String title = "Seleccionar archivo de origen";
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow();
        File selectedFile = showFileChooser(title, false, stage, null);

        // TODO: Extract method
        // TODO: IF DESCIFRANDO REMOVER .ENC Y FILTRAR SOLO .ENC
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            TFsourceFile.setText(filePath);
            TFdestinationFile.setText(filePath + ".enc");
        }
    }

    // TODO CODIGO DUPLICADO
    @FXML
    public void pressSelectDestinationButton() {
        String title = "Seleccionar ruta de destinación";
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow();

        // TODO: Extract method and put to parent class
        // TODO: IF DESCIFRANDO NO FILTER Y NO FORZAR A .ENC
        FileChooser.ExtensionFilter[] extensionFilters =
                { new FileChooser.ExtensionFilter("ENC files (*.enc)", "*.enc") };

        File selectedFile = showFileChooser(title, true, stage, extensionFilters);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            if(!selectedFile.getName().contains(".")) {
                filePath = filePath + ".enc";
            }
            TFdestinationFile.setText(filePath);
        }
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
