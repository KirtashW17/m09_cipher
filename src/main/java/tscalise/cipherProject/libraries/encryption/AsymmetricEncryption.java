package tscalise.cipherProject.libraries.encryption;

import tscalise.cipherProject.libraries.hashing.ShaHashing;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

public class AsymmetricEncryption {

    // Heu de calcular la funció resum d’un document original sense encriptar i despès l’heu d’encriptar amb la vostra
    // clau privada. Un cop fet això, envieu els dos continguts al destinatari que haurà de comprovar la validesa de tot
    // plegat desencriptant primer (amb la vostra clau pública) i calculant la funció resum (hash) del contingut desencriptat.
    // Si el valor resum calculat és el mateix que el valor resum (hash) rebut, el contingut és correcte.

    // TODO: Funcio resum
    // TODO signatura
    // todo buffer 117 bytes
    // todo firma 128 en vez de 32
    // todo rsa para cifrar la clave simetrica
    // TODO firmar y cifrararchivo

    public static byte[] encrypt(byte[] input, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(input);
    }

    public static byte[] encrypt(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key, seed);
        return cipher.doFinal(input);
    }

    public static byte[] cryptAndSign(byte[] input, PublicKey bPublicKey, PrivateKey aPrivateKey, IvParameterSpec seed) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(aPrivateKey);

        signature.update(input);
        byte[] digitalSignature = signature.sign();
        // message should contain input + digitalSignature

        // TODO: Extract method
        byte[] output = new byte[input.length + digitalSignature.length];

        int count = 0;
        for (byte b : input) {
            output[count] = b;
            count++;
        }

        for (byte b : digitalSignature) {
            output[count] = b;
            count++;
        }

        Cipher cipher = Cipher.getInstance("RSA");
        if (seed != null)
            cipher.init(Cipher.ENCRYPT_MODE, bPublicKey, seed);
        else
            cipher.init(Cipher.ENCRYPT_MODE, bPublicKey);
        return cipher.doFinal(output);
    }

    public static byte[] getSignature(byte[] decryptedBytes) {
        byte[] signatureBytes = new byte[32];
        System.arraycopy(decryptedBytes, decryptedBytes.length - 33, signatureBytes, 0, 32);
        return signatureBytes;
    }

    public static byte[] trimSignature(byte[] decryptedBytes) {
        byte[] signatureBytes = new byte[decryptedBytes.length - 32];
        System.arraycopy(decryptedBytes, 0, signatureBytes, 0, decryptedBytes.length - 32);
        return signatureBytes;
    }

    public static boolean verifySignature(byte[] messageBytes, byte[] signatureBytes, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(messageBytes);
        return signature.verify(signatureBytes);
    }

    // TODO REVISar/borrar
    public static byte[] getEncryptedChecksum(byte[] input, Key privKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privKey);

        return cipher.doFinal(ShaHashing.getSHA256(input).getBytes());
    }

}
