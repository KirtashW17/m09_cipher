package tscalise.cipherProject.libraries.hashing;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *  TODO DOCUMENTATION
 */
public class ShaHashing {
    // TODO: Documentar
    // TODO: Aplicacion practica
    // TODO: Return bytes array
    // TODO: Get hash from file (use update method)

    // Nota: Se podria devolver el Hash en un vector de Bytes, pero nos es más util obtenerlo directamente en una
    //  String en nuestro caso de uso.

    /**
     * Calcula el "resumen" de una entrada dada la entrada, la semilla (opcional) y el algoritmo a usar.
     * @param input Bytes de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen. Puede ser null
     * @param algorithm Algoritmo con el cual queremos calcular la función resumen
     * @return Función resumen en un vector de bytes
     */
    public static byte[] calculateHashBytes(byte[] input, byte[] seed, HashAlgorithm algorithm) throws NoSuchAlgorithmException {
        input = attachSeed(input, seed);
        MessageDigest md = MessageDigest.getInstance(algorithm.algorithmName);
        return md.digest(input);
    }

    /** TODO ELIMINAR
     * Calcula el "resumen" de una entrada dada la entrada, la semilla (opcional) y el algoritmo a usar.
     * @param input Bytes de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen. Puede ser null
     * @param algorithm Algoritmo con el cual queremos calcular la función resumen
     * @return Función resumen en un vector de bytes
     * @deprecated SE ELIMINARÁ EN EL PRÓXIMO COMMIT
     */
    public static byte[] calculateHashBytes(byte[] input, byte[] seed, String algorithm) throws NoSuchAlgorithmException {
        return calculateHashBytes(input, seed, HashAlgorithm.valueOf(algorithm));
    }

    /**
     * Calcula el "resumen" de una entrada dada la entrada, la semilla (opcional) y el algoritmo a usar y la devuelve en una String hexadecimal.
     * @param input Bytes de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen. Puede ser null
     * @param algorithm Algoritmo con el cual queremos calcular la función resumen
     * @return Función resumen en formato String Hexadecimal
     */
    public static String calculateHashHex(byte[] input, byte[] seed, HashAlgorithm algorithm) throws NoSuchAlgorithmException {
        return getHexString(calculateHashBytes(input, seed, algorithm));
    }

    /**
     * Calcula el "resumen" de una entrada dada la entrada, la semilla (opcional) y el algoritmo a usar y la devuelve en
     *  una String hexadecimal.
     * @param input String de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen. Puede ser null
     * @param algorithm Algoritmo con el cual queremos calcular la función resumen
     * @return Función resumen en formato String Hexadecimal
     */
    public static String calculateHashHex(String input, String seed, HashAlgorithm algorithm)
            throws NoSuchAlgorithmException {

        byte[] seedBytes = null;
        if (seed != null)
            seedBytes = seed.getBytes(StandardCharsets.UTF_8);

        return calculateHashHex(input.getBytes(StandardCharsets.UTF_8), seedBytes, algorithm);
    }

    /** TODO ELIMINAR
     * Calcula el "resumen" de una entrada dada la entrada, la semilla (opcional) y el algoritmo a usar y la devuelve
     * en una String hexadecimal.
     * @param input String de entrada a partir de los cuales se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen. Puede ser null
     * @param algorithm Algoritmo con el cual queremos calcular la función resumen
     * @return Función resumen en formato String Hexadecimal
     * @deprecated SE ELIMINARÁ EN EL PRÓXIMO COMMIT
     */
    public static String calculateHashHex(String input, String seed, String algorithm)
            throws IllegalArgumentException, NoSuchAlgorithmException {

        return calculateHashHex(input, seed, HashAlgorithm.valueOf(algorithm));
    }

    /**
     * Calcula el "resumen" del contenido de un archivo dada el archivo, la semilla (opcional) y el algoritmo a usar.
     * @param file Archivo a partir del cual se calculará el resumen
     * @param seed Semilla para modificar la salida de la función resumen. Puede ser null
     * @param algorithm Algoritmo con el cual queremos calcular la función resumen
     * @return Función resumen en un vector de bytes
     */
    public static byte[] calculateHashBytes(File file, byte[] seed, HashAlgorithm algorithm)
            throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance(algorithm.algorithmName);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buff = new byte[8192];
        int readBytes;
        while ((readBytes = bis.read(buff)) > 0 ) {
            if (readBytes < 8192)
                buff = Arrays.copyOf(buff, readBytes);
            md.update(buff);
        }
        return md.digest(seed);
    }

    public static String calculateHashHex(File file, byte[] seed, HashAlgorithm algorithm) throws NoSuchAlgorithmException, IOException {
        return getHexString(calculateHashBytes(file, seed, algorithm));
    }

    /**
     * Tranforma un vector de bytes a una String Hexadecimal
     * @param hashBytes Vector de bytes de entrada
     * @return String en formato hexadecimal
     */
    private static String getHexString(byte[] hashBytes) {
        // Bytes a String Hexadecimal
        BigInteger bi = new BigInteger(1, hashBytes);
        StringBuilder hashtext = new StringBuilder(bi.toString(16));

        // Si no tiene los caracteres necesarios concatenamos ceros. Cada digito hexadecimal son 4 bits
        while (hashtext.length() < hashBytes.length * 2) {
            hashtext.insert(0, "0");
        }

        return hashtext.toString();
    }

    /**
     * @param input Añade un seed a un vector de bytes
     * @param seed Vector de bytes al cual queremos añadirle el seed
     * @return Nuevo vector de bytes con el seed concatenado
     */
    private static byte[] attachSeed(byte[] input, byte[] seed) {
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
        return input;
    }
}
