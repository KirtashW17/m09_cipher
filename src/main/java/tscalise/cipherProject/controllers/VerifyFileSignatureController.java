package tscalise.cipherProject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tscalise.cipherProject.libraries.encryption.AsymmetricEncryption;
import tscalise.cipherProject.libraries.utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;

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
        // TODO
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
        TFsourceFile.clear();
        TFdestinationFile.clear();
//        RBaPrivate.setSelected(true);
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
//        Stage stage = (Stage) RBaPrivate.getScene().getWindow() ;
//        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private boolean validateForm() {
        // todo implementar
        // todo borde rojo fields no validos
        return true;
    }

    private void doAction() {
        File sourceFile = new File(TFsourceFile.getText());
        File destinationFile = new File(TFdestinationFile.getText());
        String keystore, alias;

//        if (RBaPrivate.isSelected()) {
//            keystore = "a_keystore.jks";
//            alias = "aKeyPair";
//        } else {
//            keystore = "b_keystore.jks";
//            alias = "bKeyPair";
//        }
//
//        PrivateKey key = getKey(keystore, alias);
//
//        try {
//            HighLevelApiSignature.signFile(sourceFile, destinationFile, key);
//            Utilities.showAlertDialog("INFORMATION", "El archivo se ha firmado con éxito!");
//        } catch (IOException e) {
//            Utilities.showAlertDialog("ERROR", "Se ha producido un error de E/S, revise que el archivo de" +
//                    "origen y el directorio de destino existan y que dispones de los permisos necesarios.");
//            e.printStackTrace();
//        } catch (GeneralSecurityException e) {
//            Utilities.showAlertDialog("ERROR", "Se ha producido un error al tratar de firmar el archivo.");
//        }

    }

    private PrivateKey getKey (String keystore, String alias) {
        Key key = null;

        try {
            key = AsymmetricEncryption.getRSAKey(keystore, "123456", alias, true);
        } catch (IOException | GeneralSecurityException e) {
            Utilities.showAlertDialog("ERROR", "No se ha encontrado el almacén de llaves o este ha sido manipulado.");
            e.printStackTrace();
            // IOException se produce por ejemplo en caso de que la contraseña sea incorrecta o se produzca un error
            //  al leer el almacén de llaves, las excepciones hijas de GeneralSecurityException se producen varios caso,
            //  sin embargo con los keystores que hemos generado no pasará y no hace falta mostrar un diálogo diferente
            //  por cada cosa ya que no depende de las acciones del usuario, la contraseña, el alias y el keystores
            //  están definidos en el código, no en la entrada del usuario
        }

        return (PrivateKey) key;
    }

}
