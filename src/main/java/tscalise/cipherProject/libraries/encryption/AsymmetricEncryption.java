package tscalise.cipherProject.libraries.encryption;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.util.Arrays;

/**
 * En esta clase encontraremos métodos para poder cifrar/descifrar usando Cifrado Asimétrico con el
 * algoritmo de cifrado RSA.
 * @author Thomas Scalise
 * @version 1.0 (14/11/2021)
 */
public class AsymmetricEncryption {
    // Heu de calcular la funció resum d’un document original sense encriptar i despès l’heu d’encriptar amb la vostra
    // clau privada. Un cop fet això, envieu els dos continguts al destinatari que haurà de comprovar la validesa de tot
    // plegat desencriptant primer (amb la vostra clau pública) i calculant la funció resum (hash) del contingut desencriptat.
    // Si el valor resum calculat és el mateix que el valor resum (hash) rebut, el contingut és correcte.

    // TODO CIFRAR/DESCIFRAR ARCHIVOS

    /**
     * Cifra un mensaje mediante encriptación asimétrica RSA.
     * Requiere bastante RAM para mensajes grandes, en esos casos es recomendable cifrar un archivo, ya que se hará
     *  mediante un bufferedReader / bufferedWriter
     * @param input Mensaje sin descifrar
     * @param key Clave para cifrar, solitamente nuestra clave pública, aunque no siempre.
     * @return Mensaje cifrado, array de 0 posiciones en caso de que el input sea nulo o un array de 0 bytes.
     * @throws GeneralSecurityException En caso de que se produzca algún error al cifrar, como que el input o
     *  la clave insertados no sean válidos.
     */
    public static byte[] encrypt(byte[] input, Key key) throws GeneralSecurityException {
        if (input == null || input.length == 0) {
            return null;
        }

        // Inicializamos los punteros para saber en que posición del vector estamos leyendo/escribiendo
        int inputPointer = 0, outputPointer = 0;

        // Obtenemos el tamaño de la clave, el cual usaremos en el siguiente paso, y lo pasamos a bytes.
        int keySize = getKeySize((RSAKey) key) / 8;
        int inputBufferSize = keySize - 11;

        // El tamaño de los bloques depende del tamaño de la clave:
        // Calculamos el tamaño del vector de salida, para ellos obtenemos el número entero resultado de redondear
        //  hacia abajo el resultado de input.length / (keySize - 11), y lo múltiplicamos por keySize
        //  (cada keySize - 11 bytes de entrada, keySize de salida, función escalonada)
        //  0 - 245 -> 256 | 245 - 490 -> 512 | 591 - 735 -> 768 ...
        // TODO ARREGLAR ESTE COMENTARIO
        // TODO DOCUMENTAR EL MOTIVO POR EL CUAL ELEGIMOS KEYS DE 256 (128 están deprecadas)

        int outputLength = (input.length + inputBufferSize - 1) / inputBufferSize * keySize;

        // Y finalmente creamos un vector de bytes cifrados del tamaño que hemos calculado anteriormente
        byte[] encryptedBytes = new byte[outputLength];

        // Creamos un ByteBuffer de 117 bytes para ir leyendo los bytes del vector de 117 en 117
        ByteBuffer bb = ByteBuffer.allocate(inputBufferSize);

        // Obtenemos nuestra instancia de Cipher usando el algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA");

        // Inicializamos el cipher en modo cifrado con nuestra respectiva clave y semilla.
        cipher.init(Cipher.ENCRYPT_MODE, key);

        while (inputPointer < input.length) {
            int length = input.length - inputPointer;
            if (length >= inputBufferSize) {
                bb.put(input, inputPointer, inputBufferSize);
                inputPointer += inputBufferSize;
                byte[] cryptedByteBuffer = cipher.doFinal(bb.array());
                bb.clear();
                for (byte b : cryptedByteBuffer) {
                    encryptedBytes[outputPointer] = b;
                    outputPointer++;
                }
            } else {
                bb.put(input, inputPointer, length);
                inputPointer += length;
                byte[] byteBufferArray = new byte[length];
                for (int i = 0; i < length; i++) {
                    byteBufferArray[i] = bb.get(i);
                }
                bb.clear();
                byteBufferArray = cipher.doFinal(byteBufferArray);
                for (byte b: byteBufferArray) {
                    encryptedBytes[outputPointer] = b;
                    outputPointer++;
                }
            }
        }

        return encryptedBytes;
    }

    /**
     * Descifra un mensaje cifrado mediante encriptación asimétrica RSA.
     * Requiere bastante RAM para mensajes grandes, en esos casos es recomendable descifrar un archivo, ya que se hará
     *  mediante un bufferedReader / bufferedWriter
     * @param input Mensaje cifrado
     * @param key Clave para descifrar, solitamente nuestra clave privada, aunque no siempre.
     * @return Mensaje descifrado, null en caso de que el input sea nulo o un array de 0 bytes.
     * @throws GeneralSecurityException En caso de que se produzca algún error al desencriptar, como que el input o
     *  la clave insertados no sean válidos.
     */
    public static byte[] decrypt(byte[] input, Key key) throws GeneralSecurityException {
        if (input == null || input.length == 0) {
            return null;
        }

        int inputPointer = 0, outputPointer = 0;

        // Obtenemos el tamaño de la clave, el cual usaremos en el siguiente paso, y lo pasamos a bytes.
        int keySize = getKeySize((RSAKey) key) / 8;
        int outputBufferSize = keySize - 11;

        int outputLength = input.length / keySize * outputBufferSize;
        byte[] decryptedBytes = new byte[outputLength];

        ByteBuffer bb = ByteBuffer.allocate(keySize);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);

        while (inputPointer < input.length) {
            bb.put(input, inputPointer, keySize);
            inputPointer += keySize;
            byte[] byteBufferArray = cipher.doFinal(bb.array());
            bb.clear();
            for (byte b : byteBufferArray) {
                decryptedBytes[outputPointer] = b;
                outputPointer++;
            }
            outputLength -= outputBufferSize - byteBufferArray.length;
            decryptedBytes = Arrays.copyOf(decryptedBytes, outputLength);
        }

        return decryptedBytes;
    }

    /**
     * Devuelve el tamaño de una clave en bits dada la clave misma
     * No podemos retornar directamente bitLength ya que en ciertos casos tiene 0's delante, dando como resultado un
     *  número que no es una potencia de 2 y no es un tamaño de clave válido.
     * @param key RSAKey de la cual deseamos conocer el tamaño
     * @return Tamaño de la clave en bits
     */
    private static int getKeySize(RSAKey key) {
        int keySize;
        int bitLength = key.getModulus().bitLength();
        if (bitLength <= 512) {
            throw new IllegalArgumentException("Invalid Key size.");
        }
        else if (bitLength <= 1024) {
            keySize = 1024;
        }
        else if (bitLength <= 2048) {
            keySize = 2048;
        }
        else if (bitLength <= 4096) {
            keySize = 4096;
        }
        else {
            throw new IllegalArgumentException("Invalid Key size.");
        }
        return keySize;
    }

}
