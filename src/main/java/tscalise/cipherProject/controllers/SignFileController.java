package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SignFileController {

    @FXML
    private TextField TFsourceFile;
    @FXML
    private TextField TFdestinationFile;
    @FXML
    private RadioButton RBaPrivate;
    @FXML
    private ToggleGroup TGkey;
    @FXML
    private RadioButton RBbPrivate;


    @FXML
    public void pressSelectSourceButton() {

    }

    @FXML
    public void pressSelectDestinationButton() {

    }

    @FXML
    public void pressClearButton() {

    }

    @FXML
    public void pressActionButton() {

    }

    @FXML
    public void pressExitButton() {
        Stage stage = (Stage) RBaPrivate.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
