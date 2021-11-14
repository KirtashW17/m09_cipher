package tscalise.cipherProject.libraries.digitalSignature;

import java.io.*;
import java.security.*;
import java.util.Arrays;

/**
 * Esta clase permite firmar vectores de bytes y archivos usando la API de alto nivel de Java.
 * @author Thomas Scalise
 * @version 1.0 (14/11/2021)
 */
public class HighLevelApiSignature {

    // TODO SIGN FILE


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
        int signatureSize = 128;
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
    public static void signFile(File inputFile, File signedFile, PrivateKey privateKey) {
        // TODO: generate signature from file + write new file
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static boolean verifyFileSignature(File file, PublicKey publicKey) {
        // todo implement
        return false; // not implemented
    }


    /**
     * Verifica que una firma sea correcta dados los bytes del mensaje, los de la firma y la clave pública de quién
     *  firmó el documento
     * @param messageBytes Bytes del mensaje sin la firma
     * @param signatureBytes Bytes de la firma
     * @param signerPublicKey Clave pública de quién firmó el mensaje
     */
    public static boolean verifyBytesSignature(byte[] messageBytes, byte[] signatureBytes, PublicKey signerPublicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        java.security.Signature signature = java.security.Signature.getInstance("SHA256withRSA");
        signature.initVerify(signerPublicKey);
        signature.update(messageBytes);
        return signature.verify(signatureBytes);
    }

    /**
     * Verifica que la firma concatenada en el mensaje sea correcta dados los bytes del mensaje con la firma y la
     *  clave pública de quién firmó el mensaje
     * @param bytesWithSignature Bytes del mensaje con la firma
     * @param signerPublicKey Clave pública de quién firmó el mensaje
     */
    public static boolean verifyBytesSignature(byte[] bytesWithSignature, PublicKey signerPublicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        byte[] messageBytes = trimSignatureBytes(bytesWithSignature);
        byte[] signatureBytes = getSignatureBytes(bytesWithSignature);
        return verifyBytesSignature(messageBytes, signatureBytes, signerPublicKey);
    }

    /**
     * Dado un mensaje con la firma concatenada al final devuelve los 128 bytes de la firma
     * @return Vector de 128 bytes que contiene la firma
     */
    public static byte[] getSignatureBytes(byte[] bytesWithSignature) {
        byte[] signatureBytes = new byte[32];
        System.arraycopy(bytesWithSignature, bytesWithSignature.length - 33, signatureBytes, 0, 32);
        return signatureBytes;
    }

    /**
     * Dado un mensaje con la firma concatenada al final devuelve el mensaje entero sin la firma
     * @return Vector de bytes que contiene el mensaje sin la firma
     */
    public static byte[] trimSignatureBytes(byte[] bytesWithSignature) {
        byte[] signatureBytes = new byte[bytesWithSignature.length - 32];
        System.arraycopy(bytesWithSignature, 0, signatureBytes, 0, bytesWithSignature.length - 32);
        return signatureBytes;
    }

}
