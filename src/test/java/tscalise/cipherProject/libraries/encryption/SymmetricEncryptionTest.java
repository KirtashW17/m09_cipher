package tscalise.cipherProject.libraries.encryption;

import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SymmetricEncryptionTest {

    @Test
    void symmetricEncryption() throws GeneralSecurityException {
        String str = "test";
        byte[] a, b;

        KeyGenerator generator = KeyGenerator.getInstance("AES");

        generator.init(192);
        Key key = generator.generateKey();

        a = str.getBytes(StandardCharsets.UTF_8);
        b = SymmetricEncryption.encrypt(a, key);

        assertArrayEquals(a, SymmetricEncryption.decrypt(b, key));
        assertEquals(str, new String(SymmetricEncryption.decrypt(b, key)));
    }

    @Test
    void symmetricEncryptionWithSeed() throws GeneralSecurityException {
        String str = "test";
        byte[] a, b;

        KeyGenerator generator = KeyGenerator.getInstance("AES");

        generator.init(192);
        Key key = generator.generateKey();

        IvParameterSpec seed = generateSeed();

        a = str.getBytes(StandardCharsets.UTF_8);
        b = SymmetricEncryption.encryptWithSeed(a, key, seed);

        assertFalse(Arrays.equals(b, SymmetricEncryption.encryptWithSeed(a, key, generateSeed())));
        assertArrayEquals(a, SymmetricEncryption.decryptWithSeed(b, key, seed));
        assertEquals(str, new String(SymmetricEncryption.decryptWithSeed(b, key, seed)));
    }

    private IvParameterSpec generateSeed() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] random = new byte[16];
        sr.nextBytes(random);
        return new IvParameterSpec(random);
    }

}