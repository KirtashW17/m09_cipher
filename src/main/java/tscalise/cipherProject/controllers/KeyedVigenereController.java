package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cipherProject.libraries.cipher.KeyedVigenere;

public class KeyedVigenereController {

    @FXML
    private RadioButton RBcrypt;
    @FXML
    private TextField TFalphabetModificator;
    @FXML
    private Button btnAction;
    @FXML
    private TextField TFpassphrase;
    @FXML
    private Label LabelAlphabetUsed;
    @FXML
    private TextArea TAinput;
    @FXML
    private TextArea TAoutput;

    // TODO: COPY BUTTON



    @FXML
    public void pressExitButton() {
        Stage stage = (Stage) TAinput.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void pressActionButton() {
        String alphabetModificator, passphrase, input, output;
        
        alphabetModificator = TFalphabetModificator.getText();
        passphrase = TFpassphrase.getText();
        input = TAinput.getText();

        KeyedVigenere keyedVigenere = new KeyedVigenere(alphabetModificator, passphrase);

        if (RBcrypt.isSelected())
            output = keyedVigenere.cryptMessage(input);
        else
            output = keyedVigenere.decryptMessage(input);

        TAoutput.setText(output);
    }

    @FXML
    public void pressClearButton() {
        TAinput.clear();
        TAoutput.clear();
        TFalphabetModificator.clear();
        TFpassphrase.clear();
        RBcrypt.setSelected(true);
        typeOnAlphabetModificator();
        switchRadioButtons();
    }

    @FXML
    public void typeOnAlphabetModificator() {
        String alphabetUsed = new String(KeyedVigenere.generateAlphabet(TFalphabetModificator.getText()));
        LabelAlphabetUsed.setText(alphabetUsed);
    }

    @FXML
    public void switchRadioButtons() {
        if (RBcrypt.isSelected())
            btnAction.setText("Cifrar");
        else
            btnAction.setText("Descifrar");
    }


}
