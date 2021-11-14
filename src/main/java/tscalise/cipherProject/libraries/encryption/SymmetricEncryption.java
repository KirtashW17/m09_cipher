package tscalise.cipherProject.libraries.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;

/**
 * En esta clase encontraremos métodos para poder cifrar/descifrar usando Cifrado Simétrico con el
 * algoritmo de cifrado AES.
 * @author Thomas Scalise
 * @version 1.0 (14/11/2021)
 */
public class SymmetricEncryption {

    //  A l’hora d’implementar un exemple d’encriptació simètrica en Java, no tot és tan senzill com fer les crides
    //  corresponents a l’API JCA/JCE de Java. Aquí teniu el repte de crear un exemple d’encriptació simètrica utilitzant
    //  Java JCE/JCA que utilitzi un “seed” aleatori addicional al contingut que voleu encriptar.
    //  Això es fa per tal que el resultat de l’encriptació no segueixi el mateix patró i per tant es pugui arribar a desencriptar.

    // TODO CIFRAR ARCHIVOS
    // TODO: Ojo: CBC != ECB. Documentar

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] encrypt(byte[] input, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(input);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] encryptWithSeed(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] decryptWithSeed(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] decrypt(byte[] input, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(input);
    }
}
