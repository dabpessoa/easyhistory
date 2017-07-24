package me.dabpessoa.easyHistory.exceptions;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class HistoricException extends Exception {

    public HistoricException() {}

    public HistoricException(String message) {
        super(message);
    }

    public HistoricException(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricException(Throwable cause) {
        super(cause);
    }

    public HistoricException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
