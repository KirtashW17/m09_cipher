package tscalise.cipherProject.libraries.encryption;

import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SymmetricEncryptionTest {

    @Test
    void symmetricEncryption() throws GeneralSecurityException {
        String str = "test";
        byte[] a, b;

        KeyGenerator generator = KeyGenerator.getInstance("AES");

        generator.init(192);
        SecretKey key = generator.generateKey();

        a = str.getBytes(StandardCharsets.UTF_8);
        b = SymmetricEncryption.encrypt(a, key);

        assertArrayEquals(a, SymmetricEncryption.decrypt(b, key));
        assertEquals(str, new String(SymmetricEncryption.decrypt(b, key)));
    }

    @Test
    void encryptFile() throws GeneralSecurityException, IOException {
        File input = new File("cryptme");
        File output = new File("output");
        File output2 = new File("output2");

        KeyGenerator generator = KeyGenerator.getInstance("AES");

        generator.init(192);
        SecretKey key = generator.generateKey();

        SymmetricEncryption.cryptFile(input, output, key);
        SymmetricEncryption.decryptFile(output, output2, key);
    }

    @Test
    void symmetricEncryptionWithSeed() throws GeneralSecurityException {
        String str = "test";
        byte[] a, b;

        KeyGenerator generator = KeyGenerator.getInstance("AES");

        generator.init(192);
        SecretKey key = generator.generateKey();

        IvParameterSpec seed = generateSeed();

        a = str.getBytes(StandardCharsets.UTF_8);
        b = SymmetricEncryption.encryptWithSeed(a, key, seed);

        assertFalse(Arrays.equals(b, SymmetricEncryption.encryptWithSeed(a, key, generateSeed())));
        assertArrayEquals(a, SymmetricEncryption.decryptWithSeed(b, key, seed));
        assertEquals(str, new String(SymmetricEncryption.decryptWithSeed(b, key, seed)));
    }

    /**
     * Este test lo he generado para probar el funcionamiento de las KeyStores y demostrar que mis actuales
     *  métodos pueden trabajar con claves almacenadas en un KeyStore
     */
    @Test
    void symmetricEncryptionWithKeystore() throws GeneralSecurityException, IOException {
        String str = "test";
        byte[] a, b;

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("a_keystore.jks"), "123456".toCharArray());
        SecretKey key = (SecretKey) keyStore.getKey("secretKey", "123456".toCharArray());

        a = str.getBytes(StandardCharsets.UTF_8);
        b = SymmetricEncryption.encrypt(a, key);

        keyStore.load(new FileInputStream("b_keystore.jks"), "123456".toCharArray());
        key = (SecretKey) keyStore.getKey("secretKey", "123456".toCharArray());

        assertArrayEquals(a, SymmetricEncryption.decrypt(b, key));
        assertEquals(str, new String(SymmetricEncryption.decrypt(b, key)));
    }

    private IvParameterSpec generateSeed() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] random = new byte[16];
        sr.nextBytes(random);
        return new IvParameterSpec(random);
    }

}