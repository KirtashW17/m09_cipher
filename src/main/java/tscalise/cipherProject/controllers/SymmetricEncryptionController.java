package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;

import static tscalise.cipherProject.libraries.Utils.Utilities.showFileChooser;

public class SymmetricEncryptionController extends EncryptionController {

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
        String title = "Seleccionar archivo de origen";
        Stage stage = (Stage) TFautoSeed.getScene().getWindow();
        File selectedFile = showFileChooser(title, false, stage, null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            TFsourceFile.setText(filePath);
            TFdestinationFile.setText(filePath + ".enc");
        }
    }

    @FXML
    public void pressSelectDestinationButton() {
        String title = "Seleccionar ruta de destinación";
        Stage stage = (Stage) TFautoSeed.getScene().getWindow();
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
