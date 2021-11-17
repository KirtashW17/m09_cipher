package tscalise.cryptographyProject.libraries.cipher;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase implementa el cifrado/descifrado usando Keyed Vigenere.
 * Se ha implementado de forma que los resultados obtenidos sean idénticos a la fuente:
 * http://rumkin.com/tools/cipher/vigenere-keyed.php
 * @author Thomas Scalise
 * @version 1.0 (14/11/2021)
 */
public class KeyedVigenere {

    // Abecedario modificado
    private final char[] customAlphabet;
    // Clave secreta
    private final String passphrase;

    // TODO explicar skippedChars para que el resultado sea igual que en cipherTools

    /**
     * Constructor
     * @param alphabetModificator Clave secreta para modificar el abecedario
     * @param passphrase Clave secreta
     */
    public KeyedVigenere(String alphabetModificator, String passphrase) {
        customAlphabet = generateAlphabet(alphabetModificator); // Inicializamos la Array 'alphabetModificator'
        this.passphrase = generatePassphrase(passphrase); // Inicializamos la String 'passphrase'
    }

    /**
     * Getter de alphabetModificator
     * @return alphabetModificator actual
     */
    public char[] getCustomAlphabet() {
        return customAlphabet;
    }

    /**
     * Getter de passphrase
     * @return passphrase actual
     */
    public String getPassphrase() {
        return passphrase;
    }

    /**
     * Descifra un mensaje con el modificador y la clave insertadas en el constructor
     * @param msg Mensaje a descifrar
     * @return Mensaje descifrado
     */
    public String decryptMessage(String msg) {
        char[] chars = msg.toCharArray(); // Convertimos el mensaje a un Array de caracteres
        int skippedChars = 0; // Contamos los caracteres que hemos saltado (no incluidos en [A-Za-z])

        // Iteramos sobre cada uno de los caracteres del mensaje
        for (int i = 0; i < chars.length; i++) {
            // Calculamos las posiciones que tenemos que desplazar el caracter en nuestro abecedario,
            //  para ello pasaremos la posición que usaremos del passphrase (i - skippedChars) % passphrase.length()
            //  a getAlphabetPosition() para obtener la cantida de caracteres a desplazar.
            int displacement = getAlphabetPosition(passphrase.charAt((i - skippedChars) % passphrase.length()));
            int position = getAlphabetPosition(chars[i]); // Posición del caracter actual sin desplazar
        if (!(chars[i] >= 65 && chars[i] <= 90 || chars[i] >= 97 && chars[i] <= 122))
            // En caso de que el caracter no esté comprendido en [A-Za-z] sumamos 1 a skippedChars.
            skippedChars ++;
        else {
            if (position - displacement < 0)
                // Si tras aplicar el desplazamiento la posición es inferior a 0 volvemos al final de la Array (-1 = 25)
                position = 26 - (displacement - position);
            else
                // En caso contrario, desplazamos las posiciones necesarias.
                position = position - displacement;

            if (chars[i] >= 65 && chars[i] <= 90)
                // [A-Z] UPPERCASE:  Añadimos la letra que esté en la posición 'position' del abecedario
                chars[i] = customAlphabet[position];
            else
                // [a-z] lowercase: Convertimos a minúscula la letra que esté en la posición 'position' del abecedario y añadimos
                chars[i] = Character.toLowerCase(customAlphabet[position]);
            }
        }
        return new String(chars); // Devolvemos una String formada por la array de caracters modificada
    }

    /**
     * Cifra un mensaje con el modificador y la clave insertadas en el constructor
     * Este método funciona exactamente que el anterior pero sumando el desplazamiento en vez de restarlo,
     *  y por ellos comprobando que la posición no suba de 25 - (26 = 0)
     * @param msg Mensaje a cifrar
     * @return Mensaje cifrado
     */
    public String cryptMessage(String msg) {
        char[] chars = msg.toCharArray(); // Convertimos el mensaje a un Array de caracteres
        int skippedChars = 0; // Contamos los caracteres que hemos saltado (no incluidos en [A-Za-z])

        // Iteramos sobre cada uno de los caracteres del mensaje
        for (int i = 0; i < chars.length; i++) {
            // Calculamos las posiciones que tenemos que desplazar el caracter en nuestro abecedario,
            //  para ello pasaremos la posición que usaremos del passphrase (i - skippedChars) % passphrase.length()
            //  a getAlphabetPosition() para obtener la cantida de caracteres a desplazar.
            int displacement = getAlphabetPosition(passphrase.charAt((i - skippedChars) % passphrase.length()));
            int position = getAlphabetPosition(chars[i]); // Posición del caracter actual sin desplazar
            if (!(chars[i] >= 65 && chars[i] <= 90 || chars[i] >= 97 && chars[i] <= 122))
                // En caso de que el caracter no esté comprendido en [A-Za-z] sumamos 1 a skippedChars.
                skippedChars ++;
            else {
                if (position + displacement > 25)
                    // Si tras aplicar el desplazamiento la posición es seperior a 25 volvemos al principio de la Array (26 = 0)
                    position = position + displacement - 26;
                else
                    // En caso contrario, desplazamos las posiciones necesarias.
                    position = position + displacement;

                if (chars[i] >= 65 && chars[i] <= 90)
                    // [A-Z] UPPERCASE:  Añadimos la letra que esté en la posición 'position' del abecedario
                    chars[i] = customAlphabet[position];
                else
                    // [a-z] lowercase: Convertimos a minúscula la letra que esté en la posición 'position' del abecedario y añadimos
                    chars[i] = Character.toLowerCase(customAlphabet[position]);
            }
        }
        return new String(chars); // Devolvemos una String formada por la array de caracters modificada
    }

    /**
     * Genera el vector del abecedario modificado (customAlphabet)
     * Es público i estático para poder mostrar el abecedario que se creará en tiempo real
     * @param alphabetModificator Clave modificadora del abecedario, el abecedario se modificará en base a esta.
     */
    public static char[] generateAlphabet(String alphabetModificator) {
        char[] alphabet = new char[26];
        alphabetModificator = alphabetModificator.toUpperCase(); // Pasa la String a mayúscula
        int alphabetPointer = 0; // Inicializamos un puntero enla posición 0
        List<Character> alphabetChars = new ArrayList<>(); // Creamos una lista que contendrá todas lasletras del abecedario

        // Añadimos los caracteres de la A a la Z a la lista alphabetChars.
        for (int i = 65; i<=90; i++) {
            alphabetChars.add((char) i);
        }

        // Iteramos sobre los caracteres del alphabetModificator pasado por parámetro
        for (int i = 0; i < alphabetModificator.length(); i++) {
            char ch = alphabetModificator.charAt(i); // Guardamos en una variable el caracter actual
            if (alphabetChars.remove((Object) ch)) {
                // Si el caracter aún no ha sido removido de alphabetChars, lo insertamos en alphabetModificator en la posición del puntero.
                alphabet[alphabetPointer] = ch;
                // Y sumamos 1 al puntero
                alphabetPointer++;
            }
        }

        while (alphabetPointer < 26) {
            // Si aún no hemos insertado 26 caracteres en nuestro vector, insertamos todos los que quedan en la lista.
            alphabet[alphabetPointer] = alphabetChars.remove(0);
            alphabetPointer++;
        }

        return alphabet;
    }

    /**
     * Genera la passphrase amb format correcte a partir d'una passphrase bruta
     * @param passphrase Passphrase bruta
     */
    private String generatePassphrase(String passphrase) {
        passphrase = passphrase.toUpperCase(); // Pasamos la passphrase a mayúscula
        StringBuilder sb = new StringBuilder(); // Inicializamos un StringBuilder en el cual insertaremos la passphrase final

        // Iteramos sobre todos los caracteres de la passphrase, filtrando todos aquellos que no estén comprendidos entre A y Z.
        for (int i = 0; i < passphrase.length(); i++) {
           char pointer = passphrase.charAt(i);
           if (pointer >= 65 && pointer <= 90)
               sb.append(pointer);
        }

        // Asignamos la passphrase filtrada a la variable de instancai passphrase.
        return sb.toString();
    }

    /**
     * Retorna la posición de un caracter en el Array del abecedario
     * @param c Caracter a buscar
     * @return posición del caracter en el abecedario modificado (alphabetModificator). -1 si no se ha encontrado.
     */
    private int getAlphabetPosition(char c) {
        c = Character.toUpperCase(c); // Pasamos el caracter a mayúscula
        int position = -1; // Inicializamos la posición en -1

        for (int i = 0; i < 26; i++) {
            // Iteramos sobre todos los caracteres hasta que encontremos el que buscamos
            if (customAlphabet[i] == c) {
                // Si encontramos el caracter, asignamos el valor de i a position y salimos del bucle.
                position = i;
                break;
            }
        }

        // Retornamos position
        return position;
    }


}
