package tscalise.cipherProject.libraries.hashing;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class ShaHashingTest {

    /**
     * Este test lo he generado para comprobar si tenia que hacer un trim del ByteBuffer que uso al generar un hash
     *  a partir de un archivo, y evidentemente si, ya que si no el hash resultante no sería correcto.
     */
    @Test
    void digestOfBuffTest() throws NoSuchAlgorithmException {
        byte[] a, b;
        a = new byte[]{ 12, 43, 54};
        b = new byte[]{ 12, 43, 54, 0, 0};
        assertNotEquals(ShaHashing.calculateHashHex(a, null, HashAlgorithm.SHA1), ShaHashing.calculateHashHex(b, null, HashAlgorithm.SHA1));
    }

    @Test
    void getSHA1() throws NoSuchAlgorithmException {
        String hash, saltedHash;
        hash = ShaHashing.calculateHashHex("test", null, HashAlgorithm.SHA1);
        saltedHash = ShaHashing.calculateHashHex("test", "seed", HashAlgorithm.SHA1);

        assertNotEquals(hash, saltedHash);
        assertEquals(hash, ShaHashing.calculateHashHex("test", null, HashAlgorithm.SHA1));
        assertEquals(saltedHash, ShaHashing.calculateHashHex("test", "seed", HashAlgorithm.SHA1));
    }

    @Test
    void getSHA256() throws NoSuchAlgorithmException {
        String hash, saltedHash;
        hash = ShaHashing.calculateHashHex("test", null, HashAlgorithm.SHA256);
        saltedHash = ShaHashing.calculateHashHex("test", "seed", HashAlgorithm.SHA256);

        assertNotEquals(hash, saltedHash);
        assertEquals(hash, ShaHashing.calculateHashHex("test", null, HashAlgorithm.SHA256));
        assertEquals(saltedHash, ShaHashing.calculateHashHex("test", "seed", HashAlgorithm.SHA256));
    }

    @Test
    void getSHA512() throws NoSuchAlgorithmException {
        String hash, saltedHash;
        hash = ShaHashing.calculateHashHex("test", null, HashAlgorithm.SHA512);
        saltedHash = ShaHashing.calculateHashHex("test", "seed", HashAlgorithm.SHA512);

        assertNotEquals(hash, saltedHash);
        assertEquals(hash, ShaHashing.calculateHashHex("test", null, HashAlgorithm.SHA512));
        assertEquals(saltedHash, ShaHashing.calculateHashHex("test", "seed", HashAlgorithm.SHA512));
    }
}