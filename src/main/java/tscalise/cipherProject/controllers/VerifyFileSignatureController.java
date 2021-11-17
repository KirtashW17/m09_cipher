package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VerifyFileSignatureController {

    @FXML
    private TextField TFsourceFile;
    @FXML
    private TextField TFdestinationFile;
    @FXML
    private Button BtnSelectDestination;
    @FXML
    private RadioButton RBbPublic;
    @FXML
    private ToggleGroup TGkey;
    @FXML
    private RadioButton RBaPublic;
    @FXML
    private CheckBox CBpermissive;

    @FXML
    public void switchAction() {

    }

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
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }



}
