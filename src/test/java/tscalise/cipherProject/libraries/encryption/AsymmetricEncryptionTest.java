package tscalise.cipherProject.libraries.encryption;

import org.junit.jupiter.api.Test;
import tscalise.cipherProject.libraries.hashing.HashAlgorithm;
import tscalise.cipherProject.libraries.hashing.ShaHashing;

import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

class AsymmetricEncryptionTest {

    // todo test seed
    // todo test checksum

    @Test
    void asymmetricEncryption() throws GeneralSecurityException {
        // 128 chars input
        String str = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        byte[] a, b;

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();

        a = str.getBytes(StandardCharsets.UTF_8);

        b = AsymmetricEncryption.encrypt(a, keyPair.getPublic(), null);
        assertArrayEquals(a, AsymmetricEncryption.decrypt(b, keyPair.getPrivate(), null));
        assertEquals(str, new String(AsymmetricEncryption.decrypt(b, keyPair.getPrivate(), null)));
    }

    @Test
    void asymmetricEncryptionCompareHash() throws GeneralSecurityException {
        String ahash, bhash, message = "test";
        byte[] original, crypted, decrypted;

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();

        original = message.getBytes(StandardCharsets.UTF_8);
        crypted = AsymmetricEncryption.encrypt(original, keyPair.getPublic(), null);
        decrypted = AsymmetricEncryption.decrypt(crypted, keyPair.getPrivate(), null);

        ahash = ShaHashing.calculateHashHex(original, null, HashAlgorithm.SHA256);
        bhash = ShaHashing.calculateHashHex(decrypted, null, HashAlgorithm.SHA256);

        assertEquals(ahash, bhash);
        assertArrayEquals(original, decrypted);
        assertEquals(message, new String(decrypted));
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

    private IvParameterSpec generateSeed() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] random = new byte[16];
        sr.nextBytes(random);
        return new IvParameterSpec(random);
    }
}