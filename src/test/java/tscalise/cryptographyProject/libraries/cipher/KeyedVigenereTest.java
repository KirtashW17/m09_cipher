package tscalise.cryptographyProject.libraries.cipher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyedVigenereTest {

    private final KeyedVigenere keyedVigenere = new KeyedVigenere("my alphabet modificator",
            "&Çñmáy%$ p_a\\/sSP-hra4\n\t\b\r3s*e3.-,");

    @Test
    void objectInitialization() {
        char[] expectedAlphabet = {'M', 'Y', 'A', 'L', 'P', 'H', 'B', 'E', 'T', 'O', 'D', 'I', 'F', 'C', 'R',
                'G', 'J', 'K', 'N', 'Q', 'S', 'U', 'V', 'W', 'X', 'Z'};

        assertArrayEquals(expectedAlphabet, keyedVigenere.getCustomAlphabet());
        assertEquals("MYPASSPHRASE", keyedVigenere.getPassphrase());
    }

    @Test
    void encryptMessage() {
        assertEquals("Ma porrbsu ofsratdyp yjve Irytr Xhoiwujy",
                keyedVigenere.cryptMessage("My message encrypted with Keyed Vigenere"));
    }

    @Test
    void decryptMessage() {
        assertEquals("My message encrypted with Keyed Vigenere",
                keyedVigenere.decryptMessage("Ma porrbsu ofsratdyp yjve Irytr Xhoiwujy"));
    }

}