package tscalise.cipherProject.libraries.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;

public class SymmetricEncryption {

    //  A l’hora d’implementar un exemple d’encriptació simètrica en Java, no tot és tan senzill com fer les crides
    //  corresponents a l’API JCA/JCE de Java. Aquí teniu el repte de crear un exemple d’encriptació simètrica utilitzant
    //  Java JCE/JCA que utilitzi un “seed” aleatori addicional al contingut que voleu encriptar.
    //  Això es fa per tal que el resultat de l’encriptació no segueixi el mateix patró i per tant es pugui arribar a desencriptar.

    // TODO Symmetric with iv / seed
    // TODO JCE/JCA
    // TODO CIFRAR ARCHIVOS

    public static byte[] encrypt(byte[] input, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(input);
    }

    public static byte[] encryptWithSeed(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    public static byte[] decryptWithSeed(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(input);
    }
}
