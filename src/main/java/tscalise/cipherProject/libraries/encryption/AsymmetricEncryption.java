package tscalise.cipherProject.libraries.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Arrays;

/**
 *  TODO DOCUMENTATION
 */
public class AsymmetricEncryption {

    // Heu de calcular la funció resum d’un document original sense encriptar i despès l’heu d’encriptar amb la vostra
    // clau privada. Un cop fet això, envieu els dos continguts al destinatari que haurà de comprovar la validesa de tot
    // plegat desencriptant primer (amb la vostra clau pública) i calculant la funció resum (hash) del contingut desencriptat.
    // Si el valor resum calculat és el mateix que el valor resum (hash) rebut, el contingut és correcte.

    // TODO: Funcio resum signature (?) - no estoy muy convencido

    // TODO: String input method overload
    // TODO: Xifrar fitxer
    // TODO: Check input not null

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
    public static byte[] encrypt(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        // Inicializamos los punteros para saber en que posición del vector estamos leyendo/escribiendo
        int inputPointer = 0, outputPointer = 0;

        // Calculamos el tamaño del vector de salida, para ellos obtenemos el número entero resultado de redondear
        //  hacia abajo el resultado de input.length / 117, y lo múltiplicamos por 128 (cada 117 bytes de entrada,
        //  128 de salida, función escalonada)
        //  0 - 117 -> 128 | 118 - 234 -> 258 | 235 - 352 -> 384
        int outputLength = (input.length + 117 - 1) / 117 * 128;

        // Y finalmente creamos un vector de bytes cifrados del tamaño que hemos calculado anteriormente
        byte[] encryptedBytes = new byte[outputLength];

        // Creamos un ByteBuffer de 117 bytes para ir leyendo los bytes del vector de 117 en 117
        ByteBuffer bb = ByteBuffer.allocate(117);

        // Obtenemos nuestra instancia de Cipher usando el algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA");

        // Inicializamos el cipher en modo cifrado con nuestra respectiva clave y semilla.
        cipher.init(Cipher.ENCRYPT_MODE, key, seed);

        while (inputPointer < input.length) {
            int length = input.length - inputPointer;
            if (length >= 117) {
                bb.put(input, inputPointer, 117);
                inputPointer += 117;
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
    public static byte[] decrypt(byte[] input, Key key, IvParameterSpec seed) throws GeneralSecurityException {
        int inputPointer = 0, outputPointer = 0;
        int outputLength = input.length / 128 * 117;
        byte[] decryptedBytes = new byte[outputLength];

        ByteBuffer bb = ByteBuffer.allocate(128);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key, seed);

        while (inputPointer < input.length) {
            bb.put(input, inputPointer, 128);
            inputPointer += 128;
            byte[] byteBufferArray = cipher.doFinal(bb.array());
            bb.clear();
            for (byte b : byteBufferArray) {
                decryptedBytes[outputPointer] = b;
                outputPointer++;
            }
            outputLength -= 117 - byteBufferArray.length;
            decryptedBytes = Arrays.copyOf(decryptedBytes, outputLength);
        }

        return decryptedBytes;
    }

}
