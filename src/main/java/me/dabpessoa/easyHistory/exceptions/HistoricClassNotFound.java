package me.dabpessoa.easyHistory.exceptions;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class HistoricClassNotFound extends HistoricException {

    public HistoricClassNotFound() {
    }

    public HistoricClassNotFound(String message) {
        super(message);
    }

    public HistoricClassNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricClassNotFound(Throwable cause) {
        super(cause);
    }

    public HistoricClassNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
