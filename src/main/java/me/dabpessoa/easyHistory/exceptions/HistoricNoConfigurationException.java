package me.dabpessoa.easyHistory.exceptions;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class HistoricNoConfigurationException extends HistoricException {

    public HistoricNoConfigurationException() {}

    public HistoricNoConfigurationException(String message) {
        super(message);
    }

    public HistoricNoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricNoConfigurationException(Throwable cause) {
        super(cause);
    }

}

