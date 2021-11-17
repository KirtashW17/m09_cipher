package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import tscalise.cipherProject.libraries.utils.Utilities;

import java.io.File;


public class EncryptionController {

    @FXML
    TextField TFsourceFile;
    @FXML
    TextField TFdestinationFile;
    @FXML
    RadioButton RBcifrar;
    @FXML
    RadioButton RBdescifrar;

    @FXML
    public void pressSelectSourceButton() {
        String title = "Seleccionar archivo de origen";
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow();
        FileChooser.ExtensionFilter[] extensionFilters = null;

        if (RBdescifrar.isSelected()) {
            extensionFilters = new FileChooser.ExtensionFilter[]
                    {new FileChooser.ExtensionFilter("ENC files (*.enc)", "*.enc")};
        }

        File selectedFile = Utilities.showFileChooser(title, false, stage, extensionFilters);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            TFsourceFile.setText(filePath);
            if (RBcifrar.isSelected())
                TFdestinationFile.setText(filePath + ".enc");
            else {
                String destinationFilePath = filePath.replace(".enc", "");
                if (filePath.equals(destinationFilePath))
                    TFdestinationFile.setText(filePath + "_decrypted");
                else
                    TFdestinationFile.setText(filePath.replace(".enc", ""));
            }
        }
    }

    @FXML
    public void pressSelectDestinationButton() {
        String title = "Seleccionar ruta de destinación";
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow();

        FileChooser.ExtensionFilter[] extensionFilters = null;

        if (RBcifrar.isSelected())
            extensionFilters = new FileChooser.ExtensionFilter[]
                    {new FileChooser.ExtensionFilter("ENC files (*.enc)", "*.enc")};

        File selectedFile = Utilities.showFileChooser(title, true, stage, extensionFilters);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
            if(RBcifrar.isSelected() && fileName.endsWith(".enc")) {
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
