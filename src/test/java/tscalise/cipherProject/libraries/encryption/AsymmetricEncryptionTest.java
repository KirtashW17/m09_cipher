package tscalise.cipherProject.libraries.encryption;

import org.junit.jupiter.api.Test;
import tscalise.cipherProject.libraries.hashing.HashAlgorithm;
import tscalise.cipherProject.libraries.hashing.ShaHashing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;

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

        b = AsymmetricEncryption.encrypt(a, keyPair.getPublic());

        assertArrayEquals(a, AsymmetricEncryption.decrypt(b, keyPair.getPrivate()));
        assertEquals(str, new String(AsymmetricEncryption.decrypt(b, keyPair.getPrivate())));
    }

    @Test
    void asymmetricEncryptFile() throws GeneralSecurityException, IOException {
        File input = new File("cryptme");
        File output = new File("output");
        File output2 = new File("output2");

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");

        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();

        AsymmetricEncryption.encryptFile(input, output, keyPair.getPublic());
        AsymmetricEncryption.decryptFile(output, output2, keyPair.getPrivate());
    }

    @Test
    void asymmetricEncryptionCompareHash() throws GeneralSecurityException {
        String ahash, bhash, message = "test";
        byte[] original, encrypted, decrypted;

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();

        original = message.getBytes(StandardCharsets.UTF_8);
        encrypted = AsymmetricEncryption.encrypt(original, keyPair.getPublic());
        decrypted = AsymmetricEncryption.decrypt(encrypted, keyPair.getPrivate());

        ahash = ShaHashing.calculateHashHex(original, null, HashAlgorithm.SHA256);
        bhash = ShaHashing.calculateHashHex(decrypted, null, HashAlgorithm.SHA256);

        assertEquals(ahash, bhash);
        assertArrayEquals(original, decrypted);
        assertEquals(message, new String(decrypted));
    }

    /**
     * Este test lo he generado para probar el funcionamiento de las KeyStores y demostrar que mis actuales
     *  métodos pueden trabajar con claves almacenadas en un KeyStore
     */
    @Test
    void asymmetricEncryptionWithKeystore() throws GeneralSecurityException, IOException {
        // Cargamos la clave pública de B para cifrar desde A
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("a_keystore.jks"), "123456".toCharArray());
        Certificate certificate = keyStore.getCertificate("bCertificate");
        PublicKey publicKey = certificate.getPublicKey();

        // 128 chars input
        String str = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        byte[] a, b;

        a = str.getBytes(StandardCharsets.UTF_8);

        b = AsymmetricEncryption.encrypt(a, publicKey);

        // Cargamos la clave privada de B para descifrar desde B
        keyStore.load(new FileInputStream("b_keystore.jks"), "123456".toCharArray());
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("bKeyPair", "123456".toCharArray());


        assertArrayEquals(a, AsymmetricEncryption.decrypt(b, privateKey));
        assertEquals(str, new String(AsymmetricEncryption.decrypt(b, privateKey)));
    }

}