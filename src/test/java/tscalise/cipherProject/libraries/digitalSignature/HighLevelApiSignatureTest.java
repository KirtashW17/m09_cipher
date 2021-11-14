package tscalise.cipherProject.libraries.digitalSignature;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.Signature;

import static org.junit.jupiter.api.Assertions.*;

class HighLevelApiSignatureTest {

    @Test
    public void bufferedUpdateTesta() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
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
}