package tscalise.cipherProject.libraries.digitalSignature;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.Signature;
import java.security.interfaces.RSAKey;

import static org.junit.jupiter.api.Assertions.*;

class HighLevelApiSignatureTest {

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

    @Test
    void verifySignature() throws  GeneralSecurityException{
        String str = "test";
        byte[] input, inputSigned, signatureBytes, messageBytes;

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();

        input = str.getBytes(StandardCharsets.UTF_8);
        inputSigned = HighLevelApiSignature.signBytes(input, keyPair.getPrivate());
        signatureBytes = HighLevelApiSignature.getSignatureBytes(inputSigned, (RSAKey) keyPair.getPublic());
        messageBytes = HighLevelApiSignature.trimSignatureBytes(inputSigned, (RSAKey) keyPair.getPublic());

        assertTrue(HighLevelApiSignature.verifyBytesSignature(messageBytes, signatureBytes, keyPair.getPublic()));
        assertTrue(HighLevelApiSignature.verifyBytesSignature(inputSigned, keyPair.getPublic()));
    }

    @Test
    void verifyFileSignature() throws IOException, GeneralSecurityException {
        File inputFile = new File("signme");
        File outputFile = new File("signme.sign");
        File outputFile2 = new File("signme_unsigned");

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();

        HighLevelApiSignature.signFile(inputFile, outputFile, keyPair.getPrivate());
        assertTrue(HighLevelApiSignature.verifyFileSignature(outputFile, keyPair.getPublic()));
        assertTrue(HighLevelApiSignature.verifyFileSignatureAndTrim(outputFile, outputFile2, keyPair.getPublic()));
    }
}