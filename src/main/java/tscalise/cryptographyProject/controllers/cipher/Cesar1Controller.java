package tscalise.cryptographyProject.controllers.cipher;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cryptographyProject.libraries.cipher.Cesar1;
import tscalise.cryptographyProject.libraries.cipher.KeyedVigenere;

import java.util.Scanner;

public class Cesar1Controller {

    @FXML
    private RadioButton RBcrypt;
    @FXML
    private Button btnAction;
    @FXML
    private TextArea TAinput;
    @FXML
    private TextArea TAoutput;

    @FXML
    public void pressExitButton() {
        Stage stage = (Stage) TAinput.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void pressActionButton() {
        String input, output;
        input = TAinput.getText();

        if (RBcrypt.isSelected())
            output = Cesar1.xifraROT5(input);
        else
            output = Cesar1.desxifraROT5(input);

        TAoutput.setText(output);
    }

    @FXML
    public void pressClearButton() {
        TAinput.clear();
        TAoutput.clear();
        RBcrypt.setSelected(true);
        switchRadioButtons();
    }

    @FXML
    public void switchRadioButtons() {
        if (RBcrypt.isSelected())
            btnAction.setText("Cifrar");
        else
            btnAction.setText("Descifrar");
    }


}
