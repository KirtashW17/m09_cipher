package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cipherProject.libraries.hashing.HashAlgorithm;
import tscalise.cipherProject.libraries.hashing.ShaHashing;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashFromStringController {
    @FXML
    private RadioButton RBsha1;
    @FXML
    private ToggleGroup TGhashAlgorythm;
    @FXML
    private RadioButton RBsha256;
    @FXML
    private RadioButton RBsha512;
    @FXML
    private CheckBox CBseed;
    @FXML
    private RadioButton RBrandom;
    @FXML
    private ToggleGroup TGseedMode;
    @FXML
    private RadioButton RBmanual;
    @FXML
    private TextField TFseed;
    @FXML
    private TextArea TAinput;
    @FXML
    private TextArea TAoutput;

    // TODO: COPY BUTTON
    // TODO: Mostrar seed aleatorio

    @FXML
    public void exit() {
        Stage stage = (Stage) RBsha1.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void pressActionButton() throws NoSuchAlgorithmException {
        String input, output, algorithm;
        byte[] seed = null;

        input = TAinput.getText();

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

        output = ShaHashing.calculateHashHex(input.getBytes(StandardCharsets.UTF_8), seed, HashAlgorithm.valueOf(algorithm));
        TAoutput.setText(output);
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
        TAoutput.clear();
        TAoutput.clear();
        RBsha1.setSelected(true);
        RBmanual.setSelected(false);
        CBseed.setSelected(false);
        seedCBAction();
    }

}
