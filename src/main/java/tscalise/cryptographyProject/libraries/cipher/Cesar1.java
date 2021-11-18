package tscalise.cryptographyProject.libraries.cipher;

import java.util.Scanner;

/**
 * M09_UF1_Act4: Xifrat César: Aquesta classe permet 'xifrar' missatges desplaçant els caràcters 5 posicions
 * @author Thomas Scalise
 */
public class Cesar1 {

    /**
     * Xifra un missatge rotant 5 posicions el caràcters.
     * @param missatge Missatge a xifrar
     * @return Missatge xifrat
     */
    public static String xifraROT5(String missatge) {
        char[] chars = missatge.toCharArray();
        int length = chars.length;
        char[] newChars = new char[length];

        for (int i = 0; i < length; i++) {
          newChars[i] = (char) (chars[i] + 5);
        }

        return new String(newChars);
    }

    /**
     * Xifra un missatge rotant 5 posicions el caràcters.
     * @param missatge Missatge a desxifrar
     * @return Missatge desxifrat
     */
    public static String desxifraROT5(String missatge){
        char[] chars = missatge.toCharArray();
        int length = chars.length;
        char[] newChars = new char[length];

        for (int i = 0; i < length; i++) {
            newChars[i] = (char) (chars[i] - 5);
        }

        return new String(newChars);
    }
}
