package tscalise.cryptographyProject.controllers.cipher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cryptographyProject.libraries.cipher.Cesar1;
import tscalise.cryptographyProject.libraries.cipher.Cesar2;

import java.util.Scanner;

public class Cesar2Controller {

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
        Cesar2 cesar = new Cesar2();

        if (RBcrypt.isSelected())
            output = cesar.xifraROT5(input);
        else
            output = cesar.desxifraROT5(input);

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
