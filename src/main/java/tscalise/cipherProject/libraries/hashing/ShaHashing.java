package tscalise.cipherProject.libraries.hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  TODO DOCUMENTATION
 */
public class ShaHashing {

    // TODO: Documentar
    // TODO: Aplicacion practica
    //  Us heu d’inventar una aplicació pràctica del Hash i implementar-la mitjançant Java.
    //  A l’aplicatiu heu d’explicar quina utilitat heu trobat per el tema del Hash.

    // TODO: Get hash from file (use update method)

    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA1(String input) {
        return getHash(input, null, HashAlgorithm.SHA1);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA1(String input, String seed) {
        return getHash(input, seed, HashAlgorithm.SHA1);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA1(byte[] input) {
        return getHash(input, null, HashAlgorithm.SHA1);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA1(byte[] input, byte[] seed) {
        return getHash(input, seed, HashAlgorithm.SHA1);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256(String input) {
        return getHash(input, null, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256(String input, String seed) {
        return getHash(input, seed, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256(byte[] input) {
        return getHash(input, null, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256(byte[] input, byte[] seed) {
        return getHash(input, seed, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512(String input) {
        return getHash(input, null, HashAlgorithm.SHA512);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512(String input, String seed) {
        return getHash(input, seed, HashAlgorithm.SHA512);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512(byte[] input) {
        return getHash(input, null, HashAlgorithm.SHA512);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512(byte[] input, byte[] seed) {
        return getHash(input, seed, HashAlgorithm.SHA512);
    }


    /**
     * Genera el "resumen" de una entrada dada la entrada, la semilla (opcional)
     * @param input Bytes de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen
     * @param algorithm Algoritmo con el cual queremos generar la función resumen
     * @return Función resumen en formato String
     */
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

    /**
     * Genera el "resumen" de una entrada dada la entrada, la semilla (opcional)
     * @param input String de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen
     * @param algorithm Algoritmo con el cual queremos generar la función resumen
     * @return Función resumen en formato String
     */
    private static String getHash(String input, String seed, HashAlgorithm algorithm) {
        byte[] seedBytes = null;
        if (seed != null)
            seedBytes = seed.getBytes(StandardCharsets.UTF_8);

        return getHash(input.getBytes(StandardCharsets.UTF_8), seedBytes, algorithm);
    }

    /**
     * Genera el "resumen" de una entrada dada la entrada, la semilla (opcional)
     * @param input String de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen
     * @param algorithm Algoritmo con el cual queremos generar la función resumen
     * @return Función resumen en formato String
     */
    private static String getHash(String input, String seed, String algorithm) throws IllegalArgumentException {
        return getHash(input, seed, HashAlgorithm.valueOf(algorithm));
    }

}
