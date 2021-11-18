package tscalise.cryptographyProject.libraries.repasoJava;

import javax.swing.*;

public class ComprovaMailOriginal {

    public static void main(String[] args) {

        String el_mail = JOptionPane.showInputDialog("Introduce mail");

        try {
            examina_mail(el_mail);
        } catch (MailErroniException e) {
            System.err.println(e.getMessage());
            // No ens interessa la causa pero amb e.getCause() podriem obtindre el NullPointerException que hem 'ocultat'.
        } finally {
            System.out.println("Finalitzant execució.");
            System.out.println("El bloc 'finally' s'acomiada.");
        }
    }

    static void examina_mail(String mail) throws MailErroniException {

        int arroba = 0;

        boolean punt = false;

        // Posem el bucle for en un try/catch per si és produeix una NullPointerException al cridar el mètode length() de mail.
        //  Això passaria si es tanca la finestra del JOptionPane
        // Sería molt millor comprovar si mail és null, i en cas de ser-ho, assignar-li una string buida,
        //  però això és un exercici d'Excepcions.
        try {
            for (int i = 0; i < mail.length(); i++) {

                if (mail.charAt(i) == '@') {
                    arroba++;
                }

                if (mail.charAt(i) == '.') {
                    punt = true;
                }
            }
        } catch (NullPointerException e) {
            // En cas de que es produeixi una NullPointerException, llançarem la nostra MailErroniException
            //  i a més li pasarem la causa (e)
            throw new MailErroniException("No s'ha introduït cap correu electrònic", e);
        }

        if (arroba == 1 && punt == true) {
            System.out.println("És correcte");
        } else if (mail.length() < 3) {
            // En cas de que la mail sigui de menys de 3 caràcters, llançarem la nostra MailErroniException
            //  amb un missatge explicant que ha passat.
            // En realitat una mail haurìa de tenir com a mínim 6 lletres, ja que no hi ha TLD d'una sola lletra,
            //  i la mail més corta possible actualment tindria aquest format - x@x.es.
            // De totes formes, la millor forma de validar una mail seria amb una expressió regular.

            throw new MailErroniException("S'ha introduït una mail de menys de 3 caràcters.");
        } else {
            System.out.println("No és correcte");
        }


    }

}
