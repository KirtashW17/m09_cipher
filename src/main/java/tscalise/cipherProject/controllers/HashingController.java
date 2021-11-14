package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HashingController {

    @FXML
    private RadioButton RBsha1;
    @FXML
    private ToggleGroup TGhashAlgorythm;
    @FXML
    private RadioButton RBsha256;
    @FXML
    private RadioButton RBsha512;


    @FXML
    public void exit() {
        Stage stage = (Stage) RBsha1.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
