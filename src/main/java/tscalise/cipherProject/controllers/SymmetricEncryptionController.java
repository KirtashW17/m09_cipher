package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cipherProject.libraries.encryption.SymmetricEncryption;
import tscalise.cipherProject.libraries.utils.Utilities;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;


public class SymmetricEncryptionController extends EncryptionController {

    @FXML
    private RadioButton RBcifrar;
    @FXML
    private ToggleGroup TGAccion;
    @FXML
    private RadioButton RBdescifrar;
    @FXML
    private TextField TFsourceFile;
    @FXML
    private TextField TFdestinationFile;
    @FXML
    private CheckBox CBuseSeed;
    @FXML
    private RadioButton RBaSecretKey;
    @FXML
    private ToggleGroup TGkey;
    @FXML
    private RadioButton RBbSecretKey;
    @FXML
    private RadioButton RBmanualSeed;
    @FXML
    private ToggleGroup TGseedMode;
    @FXML
    private RadioButton RBautoSeed;
    @FXML
    private TextField TFmanualSeed;
    @FXML
    private TextField TFautoSeed;
    @FXML
    private Button BtnAction;

    @FXML
    public void switchAction() {
        switchUseSeed();
        if (RBcifrar.isSelected()) {
            BtnAction.setText("Cifrar");
        } else {
            RBmanualSeed.setSelected(true);
            BtnAction.setText("Descifrar");
        }
    }

    @FXML
    public void switchUseSeed() {
        boolean usingSeed = CBuseSeed.isSelected();
        boolean decrypting = !RBcifrar.isSelected();

        // Si estamos descifrando deshabilitimos las opciones de seed automático
        RBautoSeed.setDisable(!usingSeed && decrypting);
        RBmanualSeed.setDisable(!usingSeed);
        switchSeedMode();
    }

    @FXML
    public void switchSeedMode() {
        TFmanualSeed.setDisable(!RBmanualSeed.isSelected());
    }

    @FXML
    public void pressClearButton() {
        RBcifrar.setSelected(true);
        TFsourceFile.clear();
        TFdestinationFile.clear();
        RBaSecretKey.setSelected(true);
        CBuseSeed.setSelected(false);
        switchAction();
        TFautoSeed.setText("Aqui aparecerá la semilla generada");
    }

    @FXML
    public void pressActionButton() {

        if (!validateForm()) {
            Utilities.showAlertDialog("WARNING", "Los siguientes campos no pueden estar vacios: ");
        } else {
            doAction();
        }

    }

    @FXML
    public void pressExitButton() {
        Stage stage = (Stage) RBcifrar.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private boolean validateForm() {

        return true; //todo implementar
    }

    // TODO CONTROLAR EXCEPCIONES
    private void doAction() {
        String keystore = RBaSecretKey.isSelected() ? "a_keystore.jks" : "b_keystore.jks";
        SecretKey secretKey = getSecretKey(keystore);

        if (secretKey != null) {
            File sourceFile = new File(TFsourceFile.getText());
            File destinationFile = new File(TFdestinationFile.getText());

            try {
                if (RBcifrar.isSelected())
                    SymmetricEncryption.encryptFile(sourceFile, destinationFile, secretKey);
                else
                    SymmetricEncryption.decryptFile(sourceFile, destinationFile, secretKey);

                Utilities.showAlertDialog("INFORMATION", "El archivo se ha cifrado con éxito!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (BadPaddingException e) {
                e.printStackTrace();

            } catch (InvalidKeyException e) {
                e.printStackTrace();

            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private SecretKey getSecretKey(String keystore) {
        SecretKey secretKey = null;

        try {
            secretKey = SymmetricEncryption.getSecretKeyFromKeystore(keystore, "123456", "secretKey");
        } catch (IOException | GeneralSecurityException e) {
            Utilities.showAlertDialog("ERROR", "No se ha encontrado el almacén de llaves o este ha sido manipulado.");
            e.printStackTrace();
            // IOException se produce por ejemplo en caso de que la contraseña sea incorrecta o se produzca un error
            //  al leer el almacén de llaves, las excepciones hijas de GeneralSecurityException se producen varios caso,
            //  sin embargo con los keystores que hemos generado no pasará y no hace falta mostrar un diálogo diferente
            //  por cada cosa ya que no depende de las acciones del usuario, la contraseña, el alias y el keystores
            //  están definidos en el código, no en la entrada del usuario
        }
    return secretKey;
    }

}
