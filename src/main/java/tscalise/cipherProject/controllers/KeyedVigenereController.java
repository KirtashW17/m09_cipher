package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class KeyedVigenereController {

    @FXML
    private RadioButton RBcrypt;
    @FXML
    private ToggleGroup TGcipherAction;
    @FXML
    private RadioButton RBdecrypt;
    @FXML
    private TextField TFalphabetKey;
    @FXML
    private Button BtnCrypt;
    @FXML
    private Button BtnExit;
    @FXML
    private TextField TFpassphrase;
    @FXML
    private Label LabelAlphabetUsed;
    @FXML
    private TextArea TAinput;
    @FXML
    private TextArea TAoutput;


    @FXML
    public void exit() {
        Stage stage = (Stage) TAinput.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }


}
