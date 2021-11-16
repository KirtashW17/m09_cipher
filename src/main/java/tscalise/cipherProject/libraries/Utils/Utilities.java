package tscalise.cipherProject.libraries.Utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import javax.swing.*;

public class Utilities {

    public static File showFileChooser(String title, boolean save, Stage stage, FileChooser.ExtensionFilter[] extensionFilters) {
        File selectedFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        if (extensionFilters != null) {
            for (FileChooser.ExtensionFilter ef : extensionFilters) {
                fileChooser.getExtensionFilters().add(ef);
            }
        }

        if (save)
            selectedFile = fileChooser.showSaveDialog(stage);
        else
            selectedFile = fileChooser.showOpenDialog(stage);

        return selectedFile;
    }

    /**
     * Tranforma un vector de bytes a una String Hexadecimal
     * @param hashBytes Vector de bytes de entrada
     * @return String en formato hexadecimal
     */
    public static String getHexString(byte[] hashBytes) {
        // Bytes a String Hexadecimal
        BigInteger bi = new BigInteger(1, hashBytes);
        StringBuilder hashtext = new StringBuilder(bi.toString(16));

        // Si no tiene los caracteres necesarios concatenamos ceros. Cada digito hexadecimal son 4 bits
        while (hashtext.length() < hashBytes.length * 2) {
            hashtext.insert(0, "0");
        }

        return hashtext.toString();
    }

    /**
     * Tranforma una String Hexadecimal a un vector de bytes
     * @param s String de entrada, tiene que tener un tamaño par (cada caracter, 4 bits)
     * @return Vector de arrays representado por la String hexadecimal
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


}
