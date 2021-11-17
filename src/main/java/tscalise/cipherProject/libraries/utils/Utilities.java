package tscalise.cipherProject.libraries.utils;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.util.Base64;

public class Utilities {

    public static void showAlertDialog(String type, String message) {
        Alert alert = new Alert(Alert.AlertType.valueOf(type), message);
        alert.showAndWait();
    }

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

    /**
     * Devuelve el tamaño de una clave en bits dada la clave misma
     * No podemos retornar directamente bitLength ya que en ciertos casos tiene 0's delante, dando como resultado un
     *  número que no es una potencia de 2 y no es un tamaño de clave válido.
     * @param key RSAKey de la cual deseamos conocer el tamaño
     * @return Tamaño de la clave en bits
     */
    public static int getKeySize(RSAKey key) {
        int keySize;
        int bitLength = key.getModulus().bitLength();
        if (bitLength <= 512) {
            throw new IllegalArgumentException("Invalid Key size.");
        }
        else if (bitLength <= 1024) {
            keySize = 1024;
        }
        else if (bitLength <= 2048) {
            keySize = 2048;
        }
        else if (bitLength <= 4096) {
            keySize = 4096;
        }
        else {
            throw new IllegalArgumentException("Invalid Key size.");
        }
        return keySize;
    }

    public String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] Base64StringToBytes(String b64String) {
        return Base64.getDecoder().decode(b64String);
    }



}
