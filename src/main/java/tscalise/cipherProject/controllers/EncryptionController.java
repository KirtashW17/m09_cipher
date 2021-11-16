package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.security.KeyStore;
import java.security.KeyStoreException;

import static tscalise.cipherProject.libraries.utils.Utilities.showFileChooser;

public class EncryptionController {

    @FXML
    TextField TFsourceFile;
    @FXML
    TextField TFdestinationFile;
    @FXML
    RadioButton RBcifrar;

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
    public void pressExitButton() {
        Stage stage = (Stage) RBcifrar.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
