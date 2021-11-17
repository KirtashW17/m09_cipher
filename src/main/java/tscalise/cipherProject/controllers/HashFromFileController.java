package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cipherProject.libraries.hashing.HashAlgorithm;
import tscalise.cipherProject.libraries.hashing.ShaHashing;
import tscalise.cipherProject.libraries.utils.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashFromFileController {
    @FXML
    private RadioButton RBsha1;
    @FXML
    private RadioButton RBsha256;
    @FXML
    private RadioButton RBsha512;
    @FXML
    private ToggleGroup TGhashAlgorythm;
    @FXML
    private CheckBox CBseed;
    @FXML
    private ToggleGroup TGseedMode;
    @FXML
    private RadioButton RBrandom;
    @FXML
    private RadioButton RBmanual;
    @FXML
    private TextField TFseed;
    @FXML
    private TextField TFinputFile;
    @FXML
    private TextArea TAoutput;

    // TODO: COPY BUTTON
    // TODO: Mostrar seed aleatorio

    @FXML
    public void pressExitButton() {
        Stage stage = (Stage) RBsha1.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void pressActionButton() {
        if (TFinputFile.getText().equals("")) {
            Utilities.showAlertDialog("WARNING", "Por favor, rellene el campo 'archivo'");
        } else {
            doAction();
        }
    }

    @FXML
    public void seedCBAction() {
        if (CBseed.isSelected()) {
            RBrandom.setDisable(false);
            RBmanual.setDisable(false);
            if (RBmanual.isSelected())
                TFseed.setDisable(false);
        } else {
            RBrandom.setDisable(true);
            RBmanual.setDisable(true);
            TFseed.setDisable(true);
        }
    }

    @FXML
    public void swtchSeedModeRB() {
        TFseed.setDisable(!RBmanual.isSelected());
    }

    @FXML
    public void pressClearButton() {
        TFseed.clear();
        TFinputFile.clear();
        TAoutput.clear();
        RBsha1.setSelected(true);
        RBmanual.setSelected(false);
        CBseed.setSelected(false);
        seedCBAction();
    }

    @FXML
    public void pressSelectSourceButton() {
        String title = "Seleccionar archivo de origen";
        Stage stage = (Stage) TFseed.getScene().getWindow();
        File selectedFile = Utilities.showFileChooser(title, false, stage, null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            TFinputFile.setText(filePath);
        }
    }

    private void doAction() {
        String inputFilePath, output, algorithm;
        byte[] seed = new byte[0];

        inputFilePath = TFinputFile.getText();

        RadioButton selectedRB = (RadioButton) TGhashAlgorythm.getSelectedToggle();
        algorithm = selectedRB.getText();

        if (CBseed.isSelected()) {
            if (RBmanual.isSelected()) {
                seed = TFseed.getText().getBytes(StandardCharsets.UTF_8);
            } else {
                byte[] randomBytes = new byte[16];
                Random random = new Random();
                random.nextBytes(randomBytes);
                seed = randomBytes;
            }
        }

        try {
            output = ShaHashing.calculateHashHex(new File(inputFilePath), seed, HashAlgorithm.valueOf(algorithm));
            TAoutput.setText(output);
        } catch (FileNotFoundException e) {
            Utilities.showAlertDialog("ERROR", "El archivo de origen no existe. Seleccione otro archivo.");
        }
        catch (IOException e) {
            Utilities.showAlertDialog("ERROR", "Se ha producido un error de E/S inesperado al leer el " +
                    "archivo de origen. Por favor, revise sus permisos");
        } catch (NoSuchAlgorithmException e) {
            // Esto no debería pasar...
            e.printStackTrace();
        }
    }

}
