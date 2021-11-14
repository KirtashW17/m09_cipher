package tscalise.cipherProject.libraries.hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaHashing {

    // TODO: Documentar
    // TODO: Aplicacion practica
    //  Us heu d’inventar una aplicació pràctica del Hash i implementar-la mitjançant Java.
    //  A l’aplicatiu heu d’explicar quina utilitat heu trobat per el tema del Hash.

    public static String getSHA1(String input) {
        return getHash(input, null, HashAlgorithm.SHA1);
    }

    public static String getSHA1(String input, String seed) {
        return getHash(input, seed, HashAlgorithm.SHA1);
    }

    public static String getSHA256(String input) {
        return getHash(input, null, HashAlgorithm.SHA256);
    }

    public static String getSHA256(String input, String seed) {
        return getHash(input, seed, HashAlgorithm.SHA256);
    }

    public static String getSHA512(String input) {
        return getHash(input, null, HashAlgorithm.SHA512);
    }

    public static String getSHA512(String input, String seed) {
        return getHash(input, seed, HashAlgorithm.SHA512);
    }

    public static String getSHA1(byte[] input) {
        return getHash(input, null, HashAlgorithm.SHA1);
    }

    public static String getSHA1(byte[] input, byte[] seed) {
        return getHash(input, seed, HashAlgorithm.SHA1);
    }

    public static String getSHA256(byte[] input) {
        return getHash(input, null, HashAlgorithm.SHA256);
    }

    public static String getSHA256(byte[] input, byte[] seed) {
        return getHash(input, seed, HashAlgorithm.SHA256);
    }

    public static String getSHA512(byte[] input) {
        return getHash(input, null, HashAlgorithm.SHA512);
    }

    public static String getSHA512(byte[] input, byte[] seed) {
        return getHash(input, seed, HashAlgorithm.SHA512);
    }

    private static String getHash(byte[] input, byte[] seed, HashAlgorithm algorithm) {
        if (seed != null) {
            byte[] tmp = new byte[input.length + seed.length];
            int cont = 0;

            for (byte b : input) {
                tmp[cont] = b;
                cont++;
            }

            for (byte b : seed) {
                tmp[cont] = b;
                cont ++;
            }

            input = tmp;
        }

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm.label);
            byte[] messageDigest = md.digest(input);

            // Bytes a String Hexadecimal
            BigInteger bi = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(bi.toString(16));

            // Si no tiene los caracteres necesarios concatenamos ceros. Cada digito hexadecimal son 4 bits
            while (hashtext.length() < algorithm.bytes * 2) {
                hashtext.insert(0, "0");
            }

            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getHash(String input, String seed, HashAlgorithm algorithm) {

        // Podem posar el seed on vulguem: al començament, al final, als dos o fins i tot al centre.
        if (seed != null)
            input = input.substring(0, input.length() / 2) + seed + input.substring(input.length() / 2);


        return getHash(input.getBytes(StandardCharsets.UTF_8), null, algorithm);
    }

    private static String getHash(String input, String seed, String algorithm) throws IllegalArgumentException {
        return getHash(input, seed, HashAlgorithm.valueOf(algorithm));
    }

}
