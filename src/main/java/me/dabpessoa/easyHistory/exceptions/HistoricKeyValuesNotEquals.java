package me.dabpessoa.easyHistory.exceptions;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class HistoricKeyValuesNotEquals extends HistoricException {

    public HistoricKeyValuesNotEquals() {
    }

    public HistoricKeyValuesNotEquals(String message) {
        super(message);
    }

    public HistoricKeyValuesNotEquals(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricKeyValuesNotEquals(Throwable cause) {
        super(cause);
    }

    public HistoricKeyValuesNotEquals(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
