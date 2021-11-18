package tscalise.cryptographyProject.libraries.repasoJava;

import javax.swing.*;
import java.util.regex.Pattern;

public class ComprovaMail {

    /**
     * Comprova que una dirección de correo electrónico sea válida, en caso contrario, lanza una MailErroniException
     * @param mail Dirección de correo electrónico en formato String
     * @throws MailErroniException En caso de que el correo electrónico no sea válido
     *  (!matches ^[^@]+@[^@]+\.[a-zA-Z]{2,}$)
     */
    public static void examina_mail(String mail) throws MailErroniException {
        // Posem el bucle for en un try/catch per si és produeix una NullPointerException al cridar el mètode length() de mail.
        //  Això passaria si es tanca la finestra del JOptionPane
        // Sería molt millor comprovar si mail és null, i en cas de ser-ho, assignar-li una string buida,
        //  però això és un exercici d'Excepcions.
        String pattern = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$";

        boolean matches = Pattern.matches(pattern, mail);

        if (!matches)
            // En cas de que la mail no compleixi el patro, llançarem la nostra MailErroniException
            //  amb un missatge explicant que ha passat.
            throw new MailErroniException("S'ha introduït una mail de menys de 3 caràcters.");
    }

}
