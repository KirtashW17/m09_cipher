package tscalise.cipherProject.libraries.encryption;

import javax.crypto.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.util.Arrays;

import static tscalise.cipherProject.libraries.utils.Utilities.getKeySize;

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
        ByteBuffer byteBuffer = ByteBuffer.allocate(inputBufferSize);

        // Obtenemos nuestra instancia de Cipher usando el algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA");

        // Inicializamos el cipher en modo cifrado con nuestra respectiva clave y semilla.
        cipher.init(Cipher.ENCRYPT_MODE, key);

        while (inputPointer < input.length) {
            int length = input.length - inputPointer;
            if (length >= inputBufferSize) {
                byteBuffer.put(input, inputPointer, inputBufferSize);
                inputPointer += inputBufferSize;
                byte[] encryptedByteBuffer = cipher.doFinal(byteBuffer.array());
                byteBuffer.clear();
                for (byte b : encryptedByteBuffer) {
                    encryptedBytes[outputPointer] = b;
                    outputPointer++;
                }
            } else {
                byteBuffer.put(input, inputPointer, length);
                inputPointer += length;
                byte[] byteBufferArray = new byte[length];
                for (int i = 0; i < length; i++) {
                    byteBufferArray[i] = byteBuffer.get(i);
                }
                byteBuffer.clear();
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

    // TODO CONTROL DE EXCEPCIONES
    // todo return boolean
    public static void encryptFile(File fileSource, File fileDestination, Key key) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        int buffSize = getKeySize((RSAKey) key) / 8 - 11;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileSource));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileDestination));
        byte[] buff = new byte[buffSize];

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        int readBytes;
        while ((readBytes = in.read(buff)) > 0) {
            if (readBytes < buffSize)
                buff = Arrays.copyOf(buff, readBytes);
            out.write(cipher.doFinal(buff));
        }
        in.close();
        out.flush();
        out.close();
    }

    public static void decryptFile(File fileSource, File fileDestination, Key key) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        int buffSize = getKeySize((RSAKey) key) / 8;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileSource));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileDestination));
        byte[] buff = new byte[buffSize];

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);

        int readBytes;
        while ((readBytes = in.read(buff)) > 0) {
            if (readBytes < buffSize)
                buff = Arrays.copyOf(buff, readBytes);
            out.write(cipher.doFinal(buff));
        }
        in.close();
        out.flush();
        out.close();
    }}
