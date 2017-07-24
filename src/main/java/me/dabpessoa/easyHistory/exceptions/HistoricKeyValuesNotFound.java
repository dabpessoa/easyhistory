package me.dabpessoa.easyHistory.exceptions;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class HistoricKeyValuesNotFound extends HistoricException {

    public HistoricKeyValuesNotFound() {
    }

    public HistoricKeyValuesNotFound(String message) {
        super(message);
    }

    public HistoricKeyValuesNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricKeyValuesNotFound(Throwable cause) {
        super(cause);
    }

    public HistoricKeyValuesNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
