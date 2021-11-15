package tscalise.cipherProject.libraries.digitalSignature;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.Signature;

import static org.junit.jupiter.api.Assertions.*;

class HighLevelApiSignatureTest {

    // TODO MORE TESTS

    /**
     * Este test comprueba que el resultado de la firma es el mismo al hacer un único update con todos los bytes,
     *  o varios parte de los bytes. Evidentemente el orden no se debe alterar y cada byte debe pasar por el método
     *  update una y solo una vez.
     * */
    @Test
    public void bufferedUpdateTest() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        byte[] a, b, c, s1, s2;
        a = "12".getBytes(StandardCharsets.UTF_8);
        b = "34".getBytes(StandardCharsets.UTF_8);
        c = "1234".getBytes(StandardCharsets.UTF_8);

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(c);
        s1 = signature.sign();

        signature.initSign(privateKey);
        signature.update(a);
        signature.update(b);
        s2 = signature.sign();

        assertArrayEquals(s1, s2);
    }

    //    @Test
//    void verifySignature() throws  GeneralSecurityException{
//        String str = "1";
//        byte[] a, b;
//
//        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
//        kpGen.initialize(1024);
//        KeyPair keyPairA = kpGen.generateKeyPair();
//        KeyPair keyPairB = kpGen.generateKeyPair();
//
//        a = str.getBytes(StandardCharsets.UTF_8);
//
////        IvParameterSpec seed = generateSeed();
//
//        b = AsymmetricEncryption.cryptAndSign(a, keyPairB.getPublic(), keyPairA.getPrivate(), null);
//        byte[] decryptedBytes = AsymmetricEncryption.decrypt(b, keyPairB.getPrivate());
//        byte[] messageBytes = AsymmetricEncryption.trimSignature(decryptedBytes);
//        byte[] signatureBytes = AsymmetricEncryption.getSignature(decryptedBytes);
//        assertTrue(AsymmetricEncryption.verifySignature(messageBytes, signatureBytes, keyPairA.getPublic()));
////        assertArrayEquals(a, AsymmetricEncryption.decrypt(b, keyPair.getPrivate()));
////        assertEquals(str, new String(AsymmetricEncryption.decrypt(b, keyPair.getPrivate())));
//    }
}