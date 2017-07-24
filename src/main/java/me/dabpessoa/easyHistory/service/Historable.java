package me.dabpessoa.easyHistory.service;

import me.dabpessoa.easyHistory.exceptions.*;
import me.dabpessoa.easyHistory.model.AbstractHistory;

import java.util.Date;
import java.util.List;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public interface Historable {

    public void saveHistory(Object originalObject) throws HistoricClassNotFound;

    public void saveHistory(Object originalObject, Long codigoUsuario) throws HistoricClassNotFound;

    public void saveHistory(Object originalObject, Date dataRegistro, Long codigoUsuario) throws HistoricClassNotFound;

    public void saveHistory(Object originalObject, String[] originalObjectFieldsName, String[] historicoFieldsName, Date dataRegistro, Long codigoUsuario) throws HistoricClassNotFound;

    public void saveHistoryIfNotEquals(Object originalObject) throws HistoricClassNotFound, HistoricKeyFieldNotConfigured;

    public void saveHistoryIfNotEquals(Object originalObject, Long codigoUsuario) throws HistoricClassNotFound, HistoricKeyFieldNotConfigured;

    public void saveHistoryIfNotEquals(Object originalObject, AbstractHistory ultimoHistorico) throws HistoricClassNotFound;

    public void saveHistoryIfNotEquals(Object originalObject, AbstractHistory ultimoHistorico, Long codigoUsuario) throws HistoricClassNotFound;

    public <T extends List> void saveListHistory(T originalList) throws HistoricClassNotFound;

    public <T extends List> void saveListHistory(T originalList, Date dataRegistro) throws HistoricClassNotFound;

    public <T extends List> void saveListHistory(T originalList, Date dataRegistro, Long codigoUsuario) throws HistoricClassNotFound;

    public <T extends List, K> void saveListHistoryIfNotEquals(T originalListObject, Class<K> originalObjectListClass) throws HistoricKeyValuesNotFound, HistoricClassNotFound, HistoricKeyFieldNotConfigured, HistoricKeyValuesNotEquals;

    public <T extends List, K> void saveListHistoryIfNotEquals(T originalListObject, Class<K> originalObjectListClass, Long codigoUsuario) throws HistoricClassNotFound, HistoricKeyFieldNotConfigured, HistoricKeyValuesNotFound, HistoricKeyValuesNotEquals;

    public <T extends List> void saveListHistoryIfNotEquals(T originalList, List<AbstractHistory> ultimoHistoricoList) throws HistoricClassNotFound;

    public <T extends List> void saveListHistoryIfNotEquals(T originalList, List<AbstractHistory> ultimoHistoricoList, Long codigoUsuario) throws HistoricClassNotFound;

    public <T, H extends AbstractHistory> List<H> findAllHistory(T originalObject) throws HistoricNoConfigurationException, HistoricKeyFieldNotConfigured;

    public <T, H extends AbstractHistory> List<H> findAllListHistory(T originalObject) throws HistoricKeyValuesNotFound, HistoricKeyValuesNotEquals, HistoricKeyFieldNotConfigured;

    public <T extends List, H extends AbstractHistory> List<H> findAllListHistory(T originalListObject, Class<?> originalObjectListClass) throws HistoricKeyFieldNotConfigured, HistoricKeyValuesNotEquals, HistoricKeyValuesNotFound;

}
