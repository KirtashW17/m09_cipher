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
    /**
     * TODO DOCUMENTAR
     * @param input
     * @param key
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] input, SecretKey key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(input);
    }

    /**
     * TODO DOCUMENTAR
     * Usamos CBC para cifrar con seed ya que ECB no nos permite insertar un IvParameterSpec
     * @param input
     * @param key
     * @param seed
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptWithSeed(byte[] input, SecretKey key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    /**
     * TODO DOCUMENTAR
     * Usamos CBC para cifrar con seed ya que ECB no nos permite insertar un IvParameterSpec
     * @param input
     * @param key
     * @param seed
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptWithSeed(byte[] input, SecretKey key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    /**
     * TODO DOCUMENTAR
     * @param input
     * @param key
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] input, SecretKey key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(input);
    }

    /**
     * TODO DOCUMENTAR
     * @param sourceFile
     * @param destinationFile
     * @param secretKey
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void encryptFile(File sourceFile, File destinationFile, SecretKey secretKey) throws IOException, GeneralSecurityException {
        int buffSize = 8192;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(sourceFile));
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

    /**
     *
     * @param sourceFile
     * @param destinationFile
     * @param secretKey
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void decryptFile(File sourceFile, File destinationFile, SecretKey secretKey) throws IOException, GeneralSecurityException {
        int buffSize = 8192;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(sourceFile));
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

    /**
     * TODO DOCUMENTAR
     * @param keystorePath
     * @param keystoreType
     * @param password
     * @param alias
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static SecretKey getSecretKeyFromKeystore(String keystorePath, String keystoreType, String password, String alias)
            throws IOException, GeneralSecurityException {

        char[] passwordChars = password.toCharArray();
        KeyStore keyStore = KeyStore.getInstance(keystoreType);
        keyStore.load(new FileInputStream(keystorePath), passwordChars);

        return (SecretKey) keyStore.getKey(alias, passwordChars);
    }

    /**
     * TODO DOCUMENTAR
     * @param keystorePath
     * @param password
     * @param alias
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     * */
    public static SecretKey getSecretKeyFromKeystore(String keystorePath, String password, String alias)
            throws IOException, GeneralSecurityException {

        return getSecretKeyFromKeystore(keystorePath, "JKS", password, alias);
    }

    }
