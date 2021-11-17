package tscalise.cryptographyProject.libraries.digitalSignature;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.util.Arrays;

import static tscalise.cryptographyProject.libraries.utils.Utilities.getKeySize;

/**
 * Esta clase permite firmar vectores de bytes y archivos usando la API de alto nivel de Java.
 * @author Thomas Scalise
 * @version 1.0 (14/11/2021)
 */
public class HighLevelApiSignature {

    /**
     * Genera una firma digital a partir de un vector de bytes.
     * @param byteArray Vector de bytes de entrada
     * @param privateKey Clave privada para firmar los bytes
     * @return Vector de 128 bytes que componen la firma digital
     * @throws GeneralSecurityException En caso de que la clave o el input no sean válidos.
     */
    public static byte[] generateSignatureFromByteArrays(byte[] byteArray, PrivateKey privateKey) throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        signature.update(byteArray);
        return signature.sign();
    }

    /**
     * Genera la firma de un vector de bytes usando el método {@link #generateSignatureFromByteArrays(byte[], PrivateKey)}
     *  y despés la concatena al vector de bytes de entrada y lo retorna.
     * @param byteArray Vector de bytes de entrada
     * @param privateKey Llave privada con la cual queremos firmar el vector de bytes
     * @return input + signature (128 bytes)
     * @throws GeneralSecurityException
     */
    public static byte[] signBytes(byte[] byteArray, PrivateKey privateKey) throws GeneralSecurityException {
        int signatureSize = getKeySize((RSAKey) privateKey) / 8;
        byte[] signedBytes = Arrays.copyOf(byteArray, byteArray.length + signatureSize);
        byte[] signatureBytes = generateSignatureFromByteArrays(byteArray, privateKey);
        System.arraycopy(signatureBytes, 0, signedBytes, byteArray.length, signatureSize);
        return signedBytes;
    }

    /**
     * Genera los bytes de la firma digital de un archivo dado el archivo y la clave privada con la cual firmar.
     * @param file Archivo del cual queremos obtener la firma digital.
     * @param privateKey Llave privada con la cual queremos firmar
     * @return Vector de 128 bytes conteniente la firma digital.
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] generateSignatureFromFile(File file, PrivateKey privateKey) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buff = new byte[8192];

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        int readBytes;
        while ((readBytes = bis.read(buff)) > 0) {
            if (readBytes < 8192)
                buff = Arrays.copyOf(buff, readBytes);
            signature.update(buff);
        }

        return signature.sign();
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static void signFile(File inputFile, File destinationFile, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destinationFile));
        int buffSize = 8192;
        byte[] buff = new byte[buffSize];

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        int readBytes;
        while ((readBytes = in.read(buff)) > 0) {
            if (readBytes < buffSize)
                buff = Arrays.copyOf(buff, readBytes);
            signature.update(buff);
            out.write(buff);
        }
        in.close();

        buff = signature.sign();
        out.write(buff);
        out.flush();
        out.close();
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static boolean verifyFileSignature(File file, PublicKey publicKey) throws IOException, GeneralSecurityException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        int buffSize = 8192;
        byte[] buff1 = new byte[buffSize];
        byte[] buff2 = null;

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);

        int readBytes;
        while ((readBytes = in.read(buff1)) > 0) {
            if (readBytes < buffSize)
                buff1 = Arrays.copyOf(buff1, readBytes);

            if (buff2 != null)
                signature.update(buff2);
            buff2 = buff1;
        }
        in.close();

        if (buff2 == null) {
            return false;
        }

        byte[] lastMessageBytes = trimSignatureBytes(buff2, (RSAKey) publicKey);
        signature.update(lastMessageBytes);
        byte[] signatureBytes = getSignatureBytes(buff2, (RSAKey) publicKey);
        return signature.verify(signatureBytes);
    }

    /**
     * TODO DOCUMENTAR
     * @param signedFile
     * @param destinationFile
     * @param publicKey
     * @param alwaysTrim
     * @return
     */
    public static boolean verifyFileSignatureAndTrim(File signedFile, File destinationFile, PublicKey publicKey, boolean alwaysTrim)
            throws IOException, GeneralSecurityException {

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(signedFile));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destinationFile));
        int buffSize = 8192;
        byte[] buff1 = new byte[buffSize];
        byte[] buff2 = null;

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);

        int readBytes;
        while ((readBytes = in.read(buff1)) > 0) {
            if (readBytes < buffSize)
                buff1 = Arrays.copyOf(buff1, readBytes);
            if (buff2 != null) {
                signature.update(buff2);
                out.write(buff2);
            }
            buff2 = buff1;
        }
        in.close();

        if (buff2 == null)
            return false;

        byte[] lastMessageBytes = trimSignatureBytes(buff2, (RSAKey) publicKey);
        signature.update(lastMessageBytes);
        out.write(lastMessageBytes);
        out.flush();
        out.close();
        byte[] signatureBytes = getSignatureBytes(buff2, (RSAKey) publicKey);
        boolean validSignature = signature.verify(signatureBytes);

        if (!validSignature && !alwaysTrim) {
            destinationFile.delete();
        }

        return validSignature;
    }

    /**
     * Verifica que una firma sea correcta dados los bytes del mensaje, los de la firma y la clave pública de quién
     *  firmó el documento
     * @param messageBytes Bytes del mensaje sin la firma
     * @param signatureBytes Bytes de la firma
     * @param signerPublicKey Clave pública de quién firmó el mensaje
     * @throws NoSuchAlgorithmException Si el algoritmo indicado (SHA256withRSA) no está presente en el JRE (No deberia pasar)
     * @throws InvalidKeyException Si la clave no es válida (tamaño incorrecto, no inicializada, etc.)
     * @throws SignatureException Si no se pueden procesar los datos de entrada (o la instancia de Signature no está bien
     *  inicializada.
     */
    public static boolean verifyBytesSignature(byte[] messageBytes, byte[] signatureBytes, PublicKey signerPublicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(signerPublicKey);
        signature.update(messageBytes);
        return signature.verify(signatureBytes);
    }

    /**
     * Verifica que la firma concatenada en el mensaje sea correcta dados los bytes del mensaje con la firma y la
     *  clave pública de quién firmó el mensaje
     * @param bytesWithSignature Bytes del mensaje con la firma
     * @param signerPublicKey Clave pública de quién firmó el mensaje
     * @throws NoSuchAlgorithmException Si el algoritmo indicado (SHA256withRSA) no está presente en el JRE (No deberia pasar)
     * @throws InvalidKeyException Si la clave no es válida (tamaño incorrecto, no inicializada, etc.)
     * @throws SignatureException Si no se pueden procesar los datos de entrada (o la instancia de Signature no está bien
     *  inicializada.
     */
    public static boolean verifyBytesSignature(byte[] bytesWithSignature, PublicKey signerPublicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        byte[] messageBytes = trimSignatureBytes(bytesWithSignature, (RSAKey) signerPublicKey);
        byte[] signatureBytes = getSignatureBytes(bytesWithSignature, (RSAKey) signerPublicKey);
        return verifyBytesSignature(messageBytes, signatureBytes, signerPublicKey);
    }

    /**
     * Dado un mensaje con la firma concatenada al final devuelve los 128 bytes de la firma
     * @param bytesWithSignature Vector de bytes con la firma al final
     * @param key Clave publica/privada para obtener el tamaño de la firma.
     * @return Vector de 128 bytes que contiene la firma
     */
    public static byte[] getSignatureBytes(byte[] bytesWithSignature, RSAKey key) {
        int keySize = getKeySize(key) / 8;
        byte[] signatureBytes = new byte[keySize];
        System.arraycopy(bytesWithSignature, bytesWithSignature.length - keySize, signatureBytes, 0, keySize);
        return signatureBytes;
    }

    /**
     * Dado un mensaje con la firma concatenada al final devuelve el mensaje entero sin la firma
     * @param bytesWithSignature Vector de bytes con la firma al final
     * @param key Clave publica/privada para obtener el tamaño de la firma.
     * @return Vector de bytes que contiene el mensaje sin la firma
     */
    public static byte[] trimSignatureBytes(byte[] bytesWithSignature, RSAKey key) {
        int keySize = getKeySize(key) / 8;
        byte[] signatureBytes = new byte[bytesWithSignature.length - keySize];
        System.arraycopy(bytesWithSignature, 0, signatureBytes, 0, bytesWithSignature.length - keySize);
        return signatureBytes;
    }

}
