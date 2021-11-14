package tscalise.cipherProject.libraries.cipher;

public class Vigenere {

    // todo: acabar

    //     private int[][] table = new int[26][52];

    private String passphrase;

    public Vigenere(String passphrase) {
        this.passphrase = passphrase;
    }

    public String vigenereCryptMessage(String msg) {
        char[] chars = msg.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            int displacement = passphrase.charAt(i % passphrase.length()) - 65;
            int position = chars[i];
            if (position >= 65 && position <= 90) {
                if (position - displacement < 65)
                    position = 90 - (66 - position - displacement);
                else
                    position = position - displacement;
            } else if (position >= 97 && position <= 122) {
                if (position - displacement < 97)
                    position = 122 - (98 - position - displacement);
                else
                    position = position - displacement;
            } else {
                // todo skip char
            }
            chars[i] = (char) position;
        }

        return new String(chars);
    }






//    public void initializeMatrix() {
//        // TODO: trovar manera de modificar l'alfabet amb el paràmetre alphabet si el volem fer servir
//        int i, j;
//
//        for (i = 0; i < 26; i++) {
//            for (j = 0; j < 26 - i; j++) {
//                table[i][j] = j + i;
//            }
//
//            for (j = 26 - i; j < 26; j++) {
//                table[i][j] = (j + i) - 26;
//            }
//
//            for (j = 26; j < 52 - i; j++) {
//                table[i][j] = j + i - 26;
//            }
//
//            for (j = 52 - i; j < 52; j++) {
//                table[i][j] = (j + i) - 52;
//            }
//        }
//
//        // TODO REMOVE: JUST FOR DEBUG PURPOSES
//        for (int[] x: table) {
//            for (int y : x) {
//                System.out.print(y + " ");
//            }
//            System.out.println();
//        }
//    }


}
