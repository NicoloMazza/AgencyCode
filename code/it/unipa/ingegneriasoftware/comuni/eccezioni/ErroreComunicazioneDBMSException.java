package it.unipa.ingegneriasoftware.comuni.eccezioni;

public class ErroreComunicazioneDBMSException extends Exception {

    private static final long serialVersionUID = 1L;

    public ErroreComunicazioneDBMSException(String messaggio) {
        super(messaggio);
    }
}