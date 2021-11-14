package tscalise.cipherProject.libraries.hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  TODO DOCUMENTATION
 */
public class ShaHashingOld {
    // TODO: Documentar
    // TODO: Aplicacion practica
    // TODO: Return bytes array
    // TODO: Get hash from file (use update method)

    // Nota: Se podria devolver el Hash en un vector de Bytes, pero nos es más util obtenerlo directamente en una
    //  String en nuestro caso de uso.


    /**
     * Genera el Hash SHA1 en formato hexadecimal de una String pasada por parámetro
     * @param input Entrada a partir de la cual se generará el Hash
     * @return Hash SHA1
     */
    public static String getSHA1Hex(String input) {
        return getHashHex(input, null, HashAlgorithm.SHA1);
    }

    /**
     * Genera el Hash SHA1 en formato hexadecimal de una String pasada por parámetro y un seed para alterar la salida
     * @param input Entrada a partir de la cual se generará el Hash
     * @param seed Semilla utilizada para alterar la salida
     * @return Hash SHA1
     */
    public static String getSHA1Hex(String input, String seed) {
        return getHashHex(input, seed, HashAlgorithm.SHA1);
    }

    /**
     * Genera el Hash SHA1 en formato hexadecimal de un vector de bytes pasado por parámetro
     * @param input Entrada a partir de la cual se generará el Hash
     * @return Hash SHA1
     */
    public static String getSHA1Hex(byte[] input) {
        return getHashHex(input, null, HashAlgorithm.SHA1);
    }

    /**
     * Genera el Hash SHA1 en formato hexadecimal de una String pasada por parámetro y un otro vector de bytes usado como
     *  semilla para alterar la salida
     * @param input Entrada a partir de la cual se generará el Hash
     * @param seed Semilla utilizada para alterar la salida
     * @return Hash SHA1
     */
    public static String getSHA1Hex(byte[] input, byte[] seed) {
        return getHashHex(input, seed, HashAlgorithm.SHA1);
    }

    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256Hex(String input) {
        return getHashHex(input, null, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256Hex(String input, String seed) {
        return getHashHex(input, seed, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256Hex(byte[] input) {
        return getHashHex(input, null, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA256Hex(byte[] input, byte[] seed) {
        return getHashHex(input, seed, HashAlgorithm.SHA256);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512Hex(String input) {
        return getHashHex(input, null, HashAlgorithm.SHA512);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512Hex(String input, String seed) {
        return getHashHex(input, seed, HashAlgorithm.SHA512);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512Hex(byte[] input) {
        return getHashHex(input, null, HashAlgorithm.SHA512);
    }
    /**
     *  TODO DOCUMENTATION
     */
    public static String getSHA512Hex(byte[] input, byte[] seed) {
        return getHashHex(input, seed, HashAlgorithm.SHA512);
    }

    /**
     * Genera el "resumen" de una entrada dada la entrada, la semilla (opcional) todo en formato hex?
     * @param input Bytes de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen
     * @param algorithm Algoritmo con el cual queremos generar la función resumen
     * @return Función resumen en formato String
     */
    private static String getHashHex(byte[] input, byte[] seed, HashAlgorithm algorithm) {
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

        // TODO EXTRACT METHOD
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm.algorithmName);
            byte[] messageDigest = md.digest(input);

            // Bytes a String Hexadecimal
            BigInteger bi = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(bi.toString(16));

            // Si no tiene los caracteres necesarios concatenamos ceros. Cada digito hexadecimal son 4 bits
            // NOTA: algorithm.bytes = messageDigest.length
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
    private static String getHashHex(String input, String seed, HashAlgorithm algorithm) {
        byte[] seedBytes = null;
        if (seed != null)
            seedBytes = seed.getBytes(StandardCharsets.UTF_8);

        return getHashHex(input.getBytes(StandardCharsets.UTF_8), seedBytes, algorithm);
    }

    /**
     * Genera el "resumen" de una entrada dada la entrada, la semilla (opcional)
     * @param input String de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen
     * @param algorithm Algoritmo con el cual queremos generar la función resumen
     * @return Función resumen en formato String
     */
    private static String getHashHex(String input, String seed, String algorithm) throws IllegalArgumentException {
        return getHashHex(input, seed, HashAlgorithm.valueOf(algorithm));
    }

}
