package tscalise.cryptographyProject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tscalise.cryptographyProject.Main;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public void showKeyedVigenereView() {
        changeScene("views/cipher/keyed_vigenere.fxml", "Cifrado Keyed Vigenere");
    }

    @FXML
    public void showHashingFromStringView() {
        changeScene("views/hashing/hash_from_string.fxml", "Calcular Función Resumen");
    }

    @FXML
    public void showHashingFromFileView() {
        changeScene("views/hashing/hash_from_file.fxml", "Calcular Función Resumen");
    }

    @FXML
    public void showSymmetricEncryptionView() {
        changeScene("views/encryption/symmetric_encryption.fxml", "Cifrado Simétrico");
    }

    @FXML
    public void showAsymmetricEncryptionView() {
        changeScene("views/encryption/asymmetric_encryption.fxml", "Cifrado Asimétrico");
    }

    @FXML
    public void showSignFileView() {
        changeScene("views/digitalSignature/sign_file.fxml", "Firmar un archivo");
    }

    @FXML
    public void showVerifyFileSignatureView() {
        changeScene("views/digitalSignature/verify_file_signature.fxml", "Comprobar firma digital");
    }

    @FXML
    public void showCesar1View() {
        changeScene("views/cipher/cesar1.fxml", "Cesar 1");
    }

    @FXML
    public void showCesar2View() {
        changeScene("views/cipher/cesar2.fxml", "Cesar 2");
    }

    /**
     * Obrim el formulari que passem per paràmetre. Amb el titol també passat per paràmetre
     * @param view Formulari a obrir
     * @param title Títol del formulari
     */
    private void showView(String view, String title, boolean resizeable) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view));

            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            // stage.initStyle((StageStyle.TRANSPARENT));
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(resizeable);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeScene(String fxml, String title) {
        try {
            Parent pane = FXMLLoader.load(
                    Main.class.getResource(fxml));
            Scene originalScene = mainAnchorPane.getScene();
            Stage primaryStage = (Stage) originalScene.getWindow();
            String originalTitle = primaryStage.getTitle();
            primaryStage.setTitle(title);
            primaryStage.setOnCloseRequest(event -> {
                primaryStage.setOnCloseRequest(null);
                primaryStage.setTitle(originalTitle);
                primaryStage.setScene(originalScene);
                event.consume();
            });
            primaryStage.setScene(new Scene(new Pane(pane)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
