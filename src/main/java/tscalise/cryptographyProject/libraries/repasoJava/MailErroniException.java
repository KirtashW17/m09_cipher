package tscalise.cryptographyProject.libraries.repasoJava;

public class MailErroniException extends IllegalArgumentException {

    /**
     * Constructor sense paràmetres
     */
    public MailErroniException() {
        super();
    }

    /**
     * Constructor a missatge
     * @param message Missatge de la excepció, es pot llegit amb getMessage()
     */
    public MailErroniException(String message) {
        super(message);
    }

    /**
     * Constructor a missatge i causa
     * @param message Missatge de la excepció, es pot llegit amb getMessage()
     */
    public MailErroniException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor amb causa
     * @param cause Causa de la excepció (Qualsevol altre Throwable capturat que l'hagi causada en un block try/catch)
     */
    public MailErroniException(Throwable cause) {
        super(cause);
    }
}
