package tscalise.cipherProject.libraries.digitalSignature;

import java.io.*;
import java.security.*;
import java.util.Arrays;

/**
 *  TODO DOCUMENTATION
 */
public class HighLevelApiSignature {

    // TODO signatura
    // TODO LOW LEVEL API
    // TODO HIGH LEVEL API
    // TODO KEYSTORE
    // TODO SIGN FILE
    // TODO SIGN BYTE ARRAY
    // todo firma 128 en vez de 32


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
        // TODO: Comprobación errores, ver initializeBufferedReader y poner en Utils
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buff = new byte[8192];

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);


        while (bis.read(buff) > 0) {
            signature.update(buff);
        }

        return signature.sign();
    }

    /**
     * Genera los bytes de la firma digital de un archivo dado el archivo y la clave privada con la cual firmar.
     * @param filePath Ruta del archivo del cual queremos obtener la firma digital.
     * @param privateKey Llave privada con la cual queremos firmar
     * @return Vector de 128 bytes conteniente la firma digital.
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] generateSignatureFromFile(String filePath, PrivateKey privateKey) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        File file = new File(filePath);
        return generateSignatureFromFile(file, privateKey);
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
    public static void signFile(String inputFilePath, String signedFilePath, PrivateKey privateKey) {
        // TODO: generate signature from file + write new file
    }

//    /**
//     *  TODO DOCUMENTATION
//     */
//    public static boolean verifyFileSignature(File file, PublicKey publicKey) {
//
//    }
//
//    /**
//     *  TODO DOCUMENTATION
//     */
//    public static boolean verifyFileSignature(String filePath, PublicKey publicKey) {
//
//    }

    /**
     *  TODO DOCUMENTATION
     */
    public static boolean verifyBytesSignature(byte[] messageBytes, byte[] signatureBytes, PublicKey signerPublicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        java.security.Signature signature = java.security.Signature.getInstance("SHA256withRSA");
        signature.initVerify(signerPublicKey);
        signature.update(messageBytes);
        return signature.verify(signatureBytes);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static boolean verifyBytesSignature(byte[] bytesWithSignature, PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        byte[] messageBytes = trimSignatureBytes(bytesWithSignature);
        byte[] signatureBytes = getSignatureBytes(bytesWithSignature);
        return verifyBytesSignature(messageBytes, signatureBytes, publicKey);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] getSignatureBytes(byte[] bytesWithSignature) {
        byte[] signatureBytes = new byte[32];
        System.arraycopy(bytesWithSignature, bytesWithSignature.length - 33, signatureBytes, 0, 32);
        return signatureBytes;
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static byte[] trimSignatureBytes(byte[] bytesWithSignature) {
        byte[] signatureBytes = new byte[bytesWithSignature.length - 32];
        System.arraycopy(bytesWithSignature, 0, signatureBytes, 0, bytesWithSignature.length - 32);
        return signatureBytes;
    }

}
