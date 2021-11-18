package tscalise.cryptographyProject.libraries.cipher;

import java.security.InvalidParameterException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * M09_UF1_Act4: Xifrat César BIS: Aquesta classe permet 'xifrar' missatges desplaçant els caràcters 5 posicions,
 *  a diferència de l'activitat base, aqui fem servir un conjunt personalitzat de caràcters.
 * @author Thomas Scalise
 */
public class Cesar2 {

    /** Caràcters acceptats **/
    private static char[] alphanumericChars = new char[62];

    public Cesar2() {
        fillAlphanumericChars();
    }

    /**
     * Xifra un missatge rotant 5 posicions el caràcters, si supera la última posició del Array torna a 0.
     * @param missatge Missatge a xifrar
     * @return Missatge xifrat
     */
    public String xifraROT5(String missatge) {
        char[] chars = missatge.toCharArray();
        int length = chars.length;
        char[] newChars = new char[length];

        for (int i = 0; i < length; i++) {
            if (!isAcceptedChar(chars[i]))
                newChars[i] = chars[i];
            else {
                int pos = getArrayPosition(chars[i]);
                if (pos > 56) { // Llençaria IndexOutOfBoundException
                    pos = pos - 57;
                } else {
                    pos += 5;
                }
                newChars[i] = alphanumericChars[pos];
            }
        }

        return new String(newChars);
    }

    /**
     * Xifra un missatge rotant 5 posicions el caràcters, si la posició baixa de 0 torna a 62.
     * @param missatge Missatge a xifrar
     * @return Missatge xifrat
     */
    public String desxifraROT5(String missatge){
        char[] chars = missatge.toCharArray();
        int length = chars.length;
        char[] newChars = new char[length];


        for (int i = 0; i < length; i++) {
            if (!isAcceptedChar(chars[i]))
                newChars[i] = chars[i];
            else {
                int pos = getArrayPosition(chars[i]);
                if (pos < 5) { // Aniria a un índex negatiu
                    pos = 61 - (4 - pos);
                } else {
                    pos -= 5;
                }
                newChars[i] = alphanumericChars[pos];
            }
        }

        return new String(newChars);
    }

    /**
     * Omple l'Array de caràcters acceptats
     */
    private void fillAlphanumericChars(){
        int cont = 0;
        for (int i = 48; i < 58; i++) {
            // char - 48 = arrayPosition
            alphanumericChars[cont] = (char) i;
            cont++;
        }

        for (int i = 65; i < 91; i++) {
            // char - 55 = arrayPosition
            alphanumericChars[cont] = (char) i;
            cont++;
        }

        for (int i = 97; i < 123; i++) {
            // char - 61 = arrayPosition
            alphanumericChars[cont] = (char) i;
            cont++;
        }
    }

    private boolean isAcceptedChar(char ch) {
        return ch >= 48 && ch < 58 || ch >= 65 && ch < 91 || ch >= 97 && ch < 123;
    }

    /**
     * Retorna la posició d'un caràcter en l'array de caracters admesos
     * @param c Caràcter a cercar
     * @return posició del caràcter en alphanumericChars
     */
    private int getArrayPosition(char c) {
        int position;

        if (c == 32)
            position = 62;
        else {
            position = c - 48;
            if (c > 64)
                position -= 7; // - 55
            if (c > 96)
                position -= 6; // -61
        }

        return position;
    }

}
