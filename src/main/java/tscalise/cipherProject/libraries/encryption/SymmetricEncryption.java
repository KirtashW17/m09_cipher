package tscalise.cipherProject.libraries.encryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

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

    // TODO CIFRAR/DESCIFRAR ARCHIVOS
    // TODO: Ojo: CBC != ECB. Documentar

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] encrypt(byte[] input, SecretKey key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(input);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] encryptWithSeed(byte[] input, SecretKey key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] decryptWithSeed(byte[] input, SecretKey key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] decrypt(byte[] input, SecretKey key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(input);
    }

    // TODO CONTROL DE EXCEPCIONES
    // todo return boolean
    // TODO normalize name (encryptFile)
    public static void encryptFile(File fileSource, File destinationFile, SecretKey secretKey) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        int buffSize = 8192;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileSource));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destinationFile));
        byte[] buff = new byte[buffSize];

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        int readBytes;
        while ((readBytes = in.read(buff)) > 0) {
            if (readBytes < buffSize)
                buff = Arrays.copyOf(buff, readBytes);
            out.write(cipher.doFinal(buff));
        }
        in.close();
        out.flush();
        out.close();
    }

    public static void decryptFile(File fileSource, File destinationFile, SecretKey secretKey) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        int buffSize = 8192;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileSource));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destinationFile));
        byte[] buff = new byte[buffSize];

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        int readBytes;
        while ((readBytes = in.read(buff)) > 0) {
            if (readBytes < buffSize)
                buff = Arrays.copyOf(buff, readBytes);
            out.write(cipher.doFinal(buff));
        }
        in.close();
        out.flush();
        out.close();
    }

    public static SecretKey getSecretKeyFromKeystore(String keystorePath, String keystoreType, String password, String alias)
            throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {

        char[] passwordChars = password.toCharArray();
        KeyStore keyStore = KeyStore.getInstance(keystoreType);
        keyStore.load(new FileInputStream(keystorePath), passwordChars);

        return (SecretKey) keyStore.getKey(alias, passwordChars);
    }

    public static SecretKey getSecretKeyFromKeystore(String keystorePath, String password, String alias)
            throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {

        return getSecretKeyFromKeystore(keystorePath, "JKS", password, alias);
    }

    }
