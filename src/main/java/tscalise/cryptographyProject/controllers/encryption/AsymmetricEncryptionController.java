package tscalise.cryptographyProject.controllers.encryption;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cryptographyProject.libraries.encryption.AsymmetricEncryption;
import tscalise.cryptographyProject.libraries.utils.Utilities;
import javax.crypto.BadPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;

// TODO VERIFY HASH -> Option generate checksum, supocion sign the checksum

public class AsymmetricEncryptionController extends EncryptionController {
    @FXML
    private ToggleGroup TGaccion;
    @FXML
    private RadioButton RBaKeystoreaPrivate;
    @FXML
    private ToggleGroup TGkey;
    @FXML
    private RadioButton RBaKeystore_aPublic;
    @FXML
    private RadioButton RBaKeystore_bPublic;
    @FXML
    private RadioButton RBbKeystore_bPrivate;
    @FXML
    private RadioButton RBbKeystore_bPublic;
    @FXML
    private RadioButton RBbKeystore_aPublic;
    @FXML
    private Button BtnAction;

    @FXML
    public void switchAction() {
        if (RBcifrar.isSelected())
            BtnAction.setText("Cifrar");
        else
            BtnAction.setText("Descifrar");
    }

    @FXML
    public void switchKey() {
        // Por ahora nada, si añadimos la opcion de subir una custom key implementar, para habilitar/desactivar opciones
    }

    @FXML
    public void pressClearButton() {
        RBcifrar.setSelected(true);
        TFsourceFile.clear();
        TFdestinationFile.clear();
        RBbKeystore_bPublic.setSelected(true);
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

    private void doAction() {
        Key key = null;
        String keystore = null, alias = null;
        boolean isPrivateKey = false;

        switch (((RadioButton) TGkey.getSelectedToggle()).getId()) {
            case "RBaKeystore_aPrivate":
                keystore = "a_keystore.jks";
                alias = "aKeyPair";
                isPrivateKey = true;
                break;
            case "RBaKeystore_bPublic":
                keystore = "a_keystore.jks";
                alias = "bCertificate";
                break;
            case "RBbKeystore_bPrivate":
                keystore = "b_keystore.jks";
                alias = "bKeyPair";
                isPrivateKey = true;
                break;
            case "RBbKeystore_aPublic":
                keystore = "b_keystore.jks";
                alias = "aCertificate";
                break;
        }

        try {
            key = AsymmetricEncryption.getRSAKey(keystore, "123456", alias, isPrivateKey);
        } catch (IOException | GeneralSecurityException e) {
            Utilities.showAlertDialog("ERROR", "No se ha encontrado el almacén de llaves o este ha sido manipulado.");
            e.printStackTrace();
            // IOException se produce por ejemplo en caso de que la contraseña sea incorrecta o se produzca un error
            //  al leer el almacén de llaves, las excepciones hijas de GeneralSecurityException se producen varios caso,
            //  sin embargo con los keystores que hemos generado no pasará y no hace falta mostrar un diálogo diferente
            //  por cada cosa ya que no depende de las acciones del usuario, la contraseña, el alias y el keystores
            //  están definidos en el código, no en la entrada del usuario
        }

        if (key != null) {
            File sourceFile = new File(TFsourceFile.getText());
            File destinationFile = new File(TFdestinationFile.getText());

            try {
                if (RBcifrar.isSelected())
                    AsymmetricEncryption.encryptFile(sourceFile, destinationFile, key);
                else
                    AsymmetricEncryption.decryptFile(sourceFile, destinationFile, key);

                String partialStr = RBcifrar.isSelected() ? "cifrado" : "descifrado";
                Utilities.showAlertDialog("INFORMATION", "El archivo se ha " + partialStr + " con éxito!");
            } catch (FileNotFoundException e) {
                Utilities.showAlertDialog("ERROR", "No se ha encontrado el archivo de origen o el " +
                        "directorio de destinación.");
            } catch (BadPaddingException e) {
                Utilities.showAlertDialog("ERROR", "La clave usada es incorrecta o el la entrada no tiene " +
                        "el formato esperado. Revisar clave y archivo de entrada.");
            } catch (IOException e) {
                Utilities.showAlertDialog("ERROR", "Se ha producido un error de E/S inesperado.");
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                Utilities.showAlertDialog("ERROR", "Se ha producido un error de seguridad inesperado.");
                e.printStackTrace();
            }
//            catch (InvalidKeyException e) {
//                USAR SI AÑADIMOS LA OPCION DE SUBIR TU PROPIA CLAVE
//                e.printStackTrace();
//            }

        }

    }

    private boolean validateForm() {
        // todo implementar
        // todo borde rojo fields no validos
        return true;
    }


}
