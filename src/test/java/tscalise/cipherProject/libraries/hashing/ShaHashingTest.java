package tscalise.cipherProject.libraries.hashing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ShaHashingTest {

    @Test
    void getSHA1() {
        String hash, saltedHash;
        hash = ShaHashing.getSHA1("test");
        saltedHash = ShaHashing.getSHA1("test", "seed");

        assertNotEquals(hash, saltedHash);
        assertEquals(hash, ShaHashing.getSHA1("test"));
        assertEquals(saltedHash, ShaHashing.getSHA1("test", "seed"));
    }

    @Test
    void getSHA256() {
        String hash, saltedHash;
        hash = ShaHashing.getSHA256("test");
        saltedHash = ShaHashing.getSHA256("test", "seed");

        assertNotEquals(hash, saltedHash);
        assertEquals(hash, ShaHashing.getSHA256("test"));
        assertEquals(saltedHash, ShaHashing.getSHA256("test", "seed"));
    }

    @Test
    void getSHA512() {
        String hash, saltedHash;
        hash = ShaHashing.getSHA512("test");
        saltedHash = ShaHashing.getSHA512("test", "seed");

        assertNotEquals(hash, saltedHash);
        assertEquals(hash, ShaHashing.getSHA512("test"));
        assertEquals(saltedHash, ShaHashing.getSHA512("test", "seed"));
    }
}