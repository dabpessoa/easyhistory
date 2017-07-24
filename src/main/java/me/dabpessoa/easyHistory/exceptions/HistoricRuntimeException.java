package me.dabpessoa.easyHistory.exceptions;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class HistoricRuntimeException extends RuntimeException {

    public HistoricRuntimeException() {}

    public HistoricRuntimeException(String message) {
        super(message);
    }

    public HistoricRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricRuntimeException(Throwable cause) {
        super(cause);
    }

    public HistoricRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
