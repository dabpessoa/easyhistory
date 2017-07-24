package me.dabpessoa.easyHistory.exceptions;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class HistoricKeyFieldNotConfigured extends HistoricException {

    public HistoricKeyFieldNotConfigured() {
    }

    public HistoricKeyFieldNotConfigured(String message) {
        super(message);
    }

    public HistoricKeyFieldNotConfigured(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricKeyFieldNotConfigured(Throwable cause) {
        super(cause);
    }

    public HistoricKeyFieldNotConfigured(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
