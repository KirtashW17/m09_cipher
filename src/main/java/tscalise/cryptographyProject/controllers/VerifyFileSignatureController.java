package tscalise.cryptographyProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tscalise.cryptographyProject.libraries.digitalSignature.HighLevelApiSignature;
import tscalise.cryptographyProject.libraries.encryption.AsymmetricEncryption;
import tscalise.cryptographyProject.libraries.utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public class VerifyFileSignatureController {

    @FXML
    private TextField TFsourceFile;
    @FXML
    private TextField TFdestinationFile;
    @FXML
    private Button BtnSelectDestination;
    @FXML
    private RadioButton RBb_public;
    @FXML
    private ToggleGroup TGkey;
    @FXML
    private RadioButton RBa_public;
    @FXML
    private RadioButton RBverify;
    @FXML
    private ToggleGroup TGaction;
    @FXML
    private RadioButton RBverifyAndTrim;
    @FXML
    private CheckBox CBpermissive;


    @FXML
    public void switchAction() {
        if (RBverify.isSelected()) {
            TFdestinationFile.setDisable(true);
            BtnSelectDestination.setDisable(true);
            CBpermissive.setSelected(false);
            CBpermissive.setDisable(true);
        } else {
            TFdestinationFile.setDisable(false);
            BtnSelectDestination.setDisable(false);
            CBpermissive.setDisable(false);
        }
    }

    @FXML
    public void pressSelectSourceButton() {
        String title = "Seleccionar archivo de origen";
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow();
        FileChooser.ExtensionFilter[] extensionFilters = new FileChooser.ExtensionFilter[]
                {new FileChooser.ExtensionFilter("Signed files (*.sign)", "*.sign")};

        File selectedFile = Utilities.showFileChooser(title, false, stage, extensionFilters);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            TFsourceFile.setText(filePath);
            String destinationFilePath = filePath.replace(".sign", "");
            if (filePath.equals(destinationFilePath))
                TFdestinationFile.setText(filePath + "_unsigned");
            else
                TFdestinationFile.setText(destinationFilePath);
        }
    }

    @FXML
    public void pressSelectDestinationButton() {
        String title = "Seleccionar ruta de destinación";
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow();

        FileChooser.ExtensionFilter[] extensionFilters = new FileChooser.ExtensionFilter[]
                {new FileChooser.ExtensionFilter("Signed files (*.sign)", "*.sign")};

        File selectedFile = Utilities.showFileChooser(title, true, stage, extensionFilters);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            TFdestinationFile.setText(filePath);
        }
    }


    @FXML
    public void pressClearButton() {
        RBverify.setSelected(true);
        TFsourceFile.clear();
        TFdestinationFile.clear();
        RBb_public.setSelected(true);
        switchAction();
    }

    @FXML
    public void pressActionButton() {

        if (!validateForm()) {
            Utilities.showAlertDialog("WARNING", "Por favor, rellene los campos obligatorios (archivo y destinación)");
        } else {
            doAction();
        }

    }

    @FXML
    public void pressExitButton() {
        Stage stage = (Stage) TFdestinationFile.getScene().getWindow() ;
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private boolean validateForm() {
        // todo implementar (O no)
        // todo borde rojo fields no validos
        return true;
    }

    private void doAction() {
        File sourceFile = new File(TFsourceFile.getText());
        File destinationFile = new File(TFdestinationFile.getText());
        String keystore, alias;

        if (RBa_public.isSelected()) {
            keystore = "a_keystore.jks";
            alias = "aKeyPair";
        } else {
            keystore = "b_keystore.jks";
            alias = "bKeyPair";
        }

        PublicKey key = getKey(keystore, alias);

        try {
            boolean verified;
            if (RBverifyAndTrim.isSelected())
                verified = HighLevelApiSignature.verifyFileSignatureAndTrim(sourceFile, destinationFile, key, CBpermissive.isSelected());
            else
                verified = HighLevelApiSignature.verifyFileSignature(sourceFile, key);

            if (verified)
                Utilities.showAlertDialog("INFORMATION", "La firma se ha verificado con éxito. El mensaje " +
                        "ha sido firmado con la clave privada emparejada con esta clave pública.");
            else
                Utilities.showAlertDialog("WARNING", "La verificación de la firma ha fallado. El mensaje " +
                        "no ha sido firmado con la clave privada emparejada con esta clave pública.");

        } catch (IOException e) {
            Utilities.showAlertDialog("ERROR", "Se ha producido un error de E/S, revise que el archivo de " +
                    "origen y el directorio de destino existan y que dispones de los permisos necesarios.");
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            Utilities.showAlertDialog("ERROR", "Se ha producido un error al tratar de firmar el archivo.");
        }

    }

    private PublicKey getKey(String keystore, String alias) {
        Key key = null;

        try {
            key = AsymmetricEncryption.getRSAKey(keystore, "123456", alias, false);
        } catch (IOException | GeneralSecurityException e) {
            Utilities.showAlertDialog("ERROR", "No se ha encontrado el almacén de llaves o este ha sido manipulado.");
            e.printStackTrace();
            // IOException se produce por ejemplo en caso de que la contraseña sea incorrecta o se produzca un error
            //  al leer el almacén de llaves, las excepciones hijas de GeneralSecurityException se producen varios caso,
            //  sin embargo con los keystores que hemos generado no pasará y no hace falta mostrar un diálogo diferente
            //  por cada cosa ya que no depende de las acciones del usuario, la contraseña, el alias y el keystores
            //  están definidos en el código, no en la entrada del usuario
        }

        return (PublicKey) key;
    }

}
