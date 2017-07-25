package me.dabpessoa.easyHistory.service;

import me.dabpessoa.easyHistory.exceptions.*;
import me.dabpessoa.easyHistory.model.AbstractHistory;
import me.dabpessoa.easyHistory.model.HistoricoComparator;
import me.dabpessoa.easyHistory.model.enums.History;
import me.dabpessoa.easyHistory.model.enums.HistoryField;
import me.dabpessoa.easyHistory.model.enums.HistoryKey;
import me.dabpessoa.easyHistory.model.enums.ListHistoryClass;
import me.dabpessoa.easyHistory.service.jpaTransaction.JPATransactionCallbackWithoutResult;
import me.dabpessoa.easyHistory.service.jpaTransaction.JPATransactionTemplate;
import me.dabpessoa.easyHistory.util.JpaEntityRepository;
import me.dabpessoa.easyHistory.util.ReflectionUtils;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by diego.pessoa on 06/07/2017.
 */
public class EasyHistory implements Historable {

    private JpaEntityRepository jpaEntityRepository;
    private JPATransactionTemplate jpaTransactionTemplate;

    public EasyHistory(EntityManager entityManager) {
        this.jpaEntityRepository = new JpaEntityRepository(entityManager);
        this.jpaTransactionTemplate = new JPATransactionTemplate(entityManager);
    }

    @Override
    public <T, H extends AbstractHistory> List<H> findAllHistory(T originalObject) throws HistoricNoConfigurationException, HistoricKeyFieldNotConfigured {
        Class<H> historicoClass = (Class<H>) findHistoryClass(originalObject.getClass());
        if (historicoClass == null) throw new HistoricNoConfigurationException("A entidade não possui configuração de histórico.");

        String historicoKeyFieldName = findHistoryKeyFieldName(originalObject.getClass());
        Object historicoKeyFieldValue = findHistoryKeyFieldValue(originalObject);

        Map<String, Object> params = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append(" select historico ");
        sb.append(" from "+historicoClass.getName()+" historico ");
        sb.append(" where 1=1 ");

        if (historicoKeyFieldName != null && !historicoKeyFieldName.isEmpty() && historicoKeyFieldValue != null) {
            sb.append(" and historico."+historicoKeyFieldName+"= :historicoKeyFieldValue ");
            params.put("historicoKeyFieldValue", historicoKeyFieldValue);
        }

        sb.append(" order by historico.dataRegistro desc ");

        List<H> historicos = jpaEntityRepository.queryForList(sb.toString(), params);
        return historicos;

    }

    @Override
    public  <T, H extends AbstractHistory> List<H> findAllListHistory(T originalObject) throws HistoricKeyValuesNotFound, HistoricKeyValuesNotEquals, HistoricKeyFieldNotConfigured {
        if (isList(originalObject)) throw new HistoricRuntimeException("Este método não aceita uma lista como parâmetro.");
        Class<?> originalObjectListClass = originalObject.getClass();
        return findAllListHistory(Arrays.asList(originalObject), originalObjectListClass);
    }

    @Override
    public  <T extends List, H extends AbstractHistory> List<H> findAllListHistory(T originalListObject, Class<?> originalObjectListClass) throws HistoricKeyFieldNotConfigured, HistoricKeyValuesNotEquals, HistoricKeyValuesNotFound {
        List<?> originalList = findListFromObjectList(originalListObject);
        if (originalList == null || originalList.isEmpty()) return null;

        checkListObject(originalListObject);
        if (originalObjectListClass == null) throw new HistoricRuntimeException("A classe do objeto passada por parâmetro não pode estar vazia.");

        Class<H> historicoListClass = (Class<H>) findListHistoryClass(originalObjectListClass);
        if (historicoListClass == null) throw new HistoricRuntimeException("A entidade não possui configuração de histórico.");

        String historicoKeyFieldName = findHistoryKeyFieldName(originalObjectListClass);
        Object historicoKeyFieldValue = findHistoryKeyFieldValueFromObjectList(originalListObject);

        Map<String, Object> params = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append(" select historico ");
        sb.append(" from "+historicoListClass.getName()+" historico ");
        sb.append(" where 1=1 ");

        if (historicoKeyFieldName != null && !historicoKeyFieldName.isEmpty() && historicoKeyFieldValue != null) {
            sb.append(" and historico."+historicoKeyFieldName+"= :historicoKeyFieldValue ");
            params.put("historicoKeyFieldValue", historicoKeyFieldValue);
        }

        sb.append(" order by historico.dataRegistro desc ");

        List<H> historicos = jpaEntityRepository.queryForList(sb.toString(), params);
        return historicos;

    }

    @Override
    public <T extends List, K> void saveListHistoryIfNotEquals(T originalListObject, Class<K> originalObjectListClass) throws HistoricKeyValuesNotFound, HistoricClassNotFound, HistoricKeyFieldNotConfigured, HistoricKeyValuesNotEquals {
        saveListHistoryIfNotEquals(originalListObject, originalObjectListClass, null);
    }

    @Override
    public <T extends List, K> void saveListHistoryIfNotEquals(T originalListObject, Class<K> originalObjectListClass, Long codigoUsuario) throws HistoricClassNotFound, HistoricKeyFieldNotConfigured, HistoricKeyValuesNotFound, HistoricKeyValuesNotEquals {
        if (originalListObject == null || originalListObject.isEmpty()) return;

        boolean possuiHistoricoListAnnotationClass = hasListHistoryAnnotation(originalObjectListClass);
        if (possuiHistoricoListAnnotationClass) {

            List<AbstractHistory> ultimoHistoricoList = findLastListHistory(originalListObject, originalObjectListClass);
            saveListHistoryIfNotEquals(originalListObject, ultimoHistoricoList, codigoUsuario);

        } else {
            throw new HistoricRuntimeException("A classe \""+originalObjectListClass+"\" não está anotada com: "+ListHistoryClass.class);
        }
    }

    @Override
    public void saveHistoryIfNotEquals(Object originalObject) throws HistoricClassNotFound, HistoricKeyFieldNotConfigured {
        saveHistoryIfNotEquals(originalObject, (Long)null);
    }

    @Override
    public void saveHistoryIfNotEquals(Object originalObject, Long codigoUsuario) throws HistoricClassNotFound, HistoricKeyFieldNotConfigured {
        if (originalObject == null) return;

        boolean possuiHitoricoAnnotationClass = hasHistoryAnnotation(originalObject.getClass());
        if (possuiHitoricoAnnotationClass) {

            AbstractHistory ultimoHistorico = findLastHistory(originalObject);
            saveHistoryIfNotEquals(originalObject, ultimoHistorico, codigoUsuario);

        }
    }

    @Override
    public void saveHistoryIfNotEquals(Object originalObject, AbstractHistory ultimoHistorico) throws HistoricClassNotFound {
        saveHistoryIfNotEquals(originalObject, ultimoHistorico, null);
    }

    @Override
    public void saveHistoryIfNotEquals(Object originalObject, AbstractHistory ultimoHistorico, Long codigoUsuario) throws HistoricClassNotFound {
        if (ultimoHistorico == null) saveHistory(originalObject, new Date(), codigoUsuario);
        else {
            boolean isDiferente = !isHistoryFieldValuesEquals(originalObject, ultimoHistorico);
            if (isDiferente) {
                saveHistory(originalObject, new Date(), codigoUsuario);
            }
        }
    }

    @Override
    public <T extends List> void saveListHistoryIfNotEquals(T originalList, List<AbstractHistory> ultimoHistoricoList) throws HistoricClassNotFound {
        saveListHistoryIfNotEquals(originalList, ultimoHistoricoList, null);
    }

    @Override
    public <T extends List> void saveListHistoryIfNotEquals(T originalList, List<AbstractHistory> ultimoHistoricoList, Long codigoUsuario) throws HistoricClassNotFound {
        if (ultimoHistoricoList == null || ultimoHistoricoList.isEmpty()) saveListHistory(originalList, new Date(), codigoUsuario);
        else {
            boolean isDiferente = !isListHistoryFieldValuesEquals(originalList, ultimoHistoricoList);
            if (isDiferente) {
                saveListHistory(originalList, new Date(), codigoUsuario);
            }
        }
    }

    @Override
    public <T extends List> void saveListHistory(T originalList) throws HistoricClassNotFound {
        saveListHistory(originalList, null);
    }

    @Override
    public <T extends List> void saveListHistory(T originalList, Date dataRegistro) throws HistoricClassNotFound {
        saveListHistory(originalList, dataRegistro, null);
    }

    @Override
    public <T extends List> void saveListHistory(T originalList, Date dataRegistro, Long codigoUsuario) throws HistoricClassNotFound {
        if (originalList == null) return;
        if (dataRegistro == null) dataRegistro = new Date();
        for (Object originalObject : originalList) {
            saveHistory(originalObject, dataRegistro, codigoUsuario);
        }
    }

    @Override
    public void saveHistory(Object originalObject) throws HistoricClassNotFound {
        saveHistory(originalObject, null, null);
    }

    @Override
    public void saveHistory(Object originalObject, Long codigoUsuario) throws HistoricClassNotFound {
        saveHistory(originalObject, null, codigoUsuario);
    }

    @Override
    public void saveHistory(Object originalObject, Date dataRegistro, Long codigoUsuario) throws HistoricClassNotFound {

        List<Field> historicoFields = ReflectionUtils.findFieldsByAnnotation(originalObject.getClass(), HistoryField.class);
        String[] originalObjectFieldsName = new String[historicoFields.size()];
        String[] historicoFieldsName = new String[historicoFields.size()];
        for (int i = 0 ; i < historicoFields.size() ; i++) {
            originalObjectFieldsName[i] = historicoFields.get(i).getName();
            Object annotationAtributeValue = ReflectionUtils.findAnnotationAttributeValue(originalObject.getClass(), HistoryField.class, historicoFields.get(i), "name");
            String historicoFieldName = annotationAtributeValue != null ? annotationAtributeValue.toString() : null;
            historicoFieldsName[i] = historicoFieldName;
        }

        saveHistory(originalObject, originalObjectFieldsName, historicoFieldsName, dataRegistro, codigoUsuario);

    }

    @Override
    public void saveHistory(Object originalObject, String[] originalObjectFieldsName, String[] historicoFieldsName, Date dataRegistro, Long codigoUsuario) throws HistoricClassNotFound {

        Object historico = createHistoryObjectFromOriginalObject(originalObject, originalObjectFieldsName, historicoFieldsName);

        if (!(historico instanceof AbstractHistory)) {
            throw new HistoricRuntimeException("A classe de histórico deve extender a classe \"AbstractHistory\".");
        } else {
            AbstractHistory historicoEntity = (AbstractHistory) historico;
            if (dataRegistro == null) dataRegistro = new Date();
            historicoEntity.setDataRegistro(dataRegistro);
            historicoEntity.setCodigoUsuario(codigoUsuario);
        }

        jpaTransactionTemplate.execute(new JPATransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult() {
                jpaEntityRepository.save(historico);
            }
        });

    }

    private boolean isHistoryFieldValuesEquals(Object originalObject, AbstractHistory ultimoHistorico) {

        List<Field> originalObjectFieldsForHistorico = ReflectionUtils.findFieldsByAnnotation(originalObject.getClass(), HistoryField.class);

        for (int i = 0 ; i < originalObjectFieldsForHistorico.size() ; i++) {
            Field originalObjectField = originalObjectFieldsForHistorico.get(i);

            Object annotationAtributeValue = ReflectionUtils.findAnnotationAttributeValue(originalObject.getClass(), HistoryField.class, originalObjectField, "name");
            String historicoFieldName = annotationAtributeValue != null ? annotationAtributeValue.toString() : null;

            Field ultimoHistoricoField = ReflectionUtils.findFieldByName(ultimoHistorico.getClass(), historicoFieldName);

            Object originalObjectFieldValue = ReflectionUtils.findFieldValue(originalObject, originalObjectField);
            Object ultimoHistoricoFieldValue = ReflectionUtils.findFieldValue(ultimoHistorico, ultimoHistoricoField);

            if (originalObjectFieldValue == null && ultimoHistoricoFieldValue == null) continue;
            if (originalObjectFieldValue == null && ultimoHistoricoFieldValue != null) return false;
            if (originalObjectFieldValue != null && ultimoHistoricoFieldValue == null) return false;

            if (originalObject instanceof HistoricoComparator) {
                if (!(((HistoricoComparator) originalObject).historicoFieldEquals(originalObjectFieldValue, ultimoHistoricoFieldValue))) {
                    return false;
                }
            } else {
                if (!originalObjectFieldValue.equals(ultimoHistoricoFieldValue)) {
                    return false;
                }
            }

        }

        return true;

    }

    public boolean isListHistoryFieldValuesEquals(Object originalObject, List<AbstractHistory> ultimoHistoricoList) {
        List<?> originalList = findListFromObjectList(originalObject);
        if (originalList == null && ultimoHistoricoList != null) return false;
        if (originalList!= null && ultimoHistoricoList == null) return false;
        if (originalList.size() != ultimoHistoricoList.size()) return false;

        // Comparando objetos da lista
        ArrayList<AbstractHistory> ultimoHistoricoListCopy = new ArrayList<>(ultimoHistoricoList);
        if (ultimoHistoricoList != null && !originalList.isEmpty()) {
            for (int i = 0 ; i < originalList.size() ; i++) {
                Object oo = originalList.get(i);
                int indexToRemove = -1;
                for (int j = 0 ; j < ultimoHistoricoListCopy.size() ; j++) {
                    AbstractHistory ultimoHistorico = ultimoHistoricoListCopy.get(j);
                    if (isHistoryFieldValuesEquals(oo, ultimoHistorico)) {
                        indexToRemove = j;
                        break;
                    }
                }
                if (indexToRemove != -1) {
                    ultimoHistoricoListCopy.remove(indexToRemove);
                } else return false;
            }
        } return true;
    }

    public <T extends List> List<AbstractHistory> findLastListHistory(T originalListObject, Class<?> originalObjectListClass) throws HistoricKeyValuesNotFound, HistoricKeyFieldNotConfigured, HistoricKeyValuesNotEquals {
        List<?> list = findListFromObjectList(originalListObject);
        if (list == null) return null;

        Class<AbstractHistory> historicoListClass = (Class<AbstractHistory>) findListHistoryClass(originalObjectListClass);

        String historicoKeyFieldName = findHistoryKeyFieldName(originalObjectListClass);

        checkKeyFieldsFill(originalListObject);
        Object historicoKeyFieldValue = findHistoryKeyFieldValueFromObjectList(originalListObject);

        Map<String, Object> params = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append(" select historico ");
        sb.append(" from "+historicoListClass.getName()+" historico ");
        sb.append(" where 1=1 ");
        sb.append("   and historico.dataRegistro = (select max(h1.dataRegistro) from "+historicoListClass.getName()+" h1 where 1=1 ");

        if (historicoKeyFieldName != null && !historicoKeyFieldName.isEmpty() && historicoKeyFieldValue != null) {
            sb.append(" and h1." + historicoKeyFieldName + "= :historicoKeyFieldValue ");
            params.put("historicoKeyFieldValue", historicoKeyFieldValue);
        }
        sb.append(" ) ");

        if (historicoKeyFieldName != null && !historicoKeyFieldName.isEmpty() && historicoKeyFieldValue != null) {
            sb.append(" and historico."+historicoKeyFieldName+"= :historicoKeyFieldValue ");
            params.put("historicoKeyFieldValue", historicoKeyFieldValue);
        }

        List<AbstractHistory> ultimoHistoricoList = jpaEntityRepository.queryForList(sb.toString(), params);

        return ultimoHistoricoList;

    }

    public AbstractHistory findLastHistory(Object originalObject) throws HistoricKeyFieldNotConfigured {

        Class<AbstractHistory> historicoClass = (Class<AbstractHistory>) findHistoryClass(originalObject.getClass());

        String historicoKeyFieldName = findHistoryKeyFieldName(originalObject.getClass());
        Object historicoKeyFieldValue = findHistoryKeyFieldValue(originalObject);

        Map<String, Object> params = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append(" select historico ");
        sb.append(" from "+historicoClass.getName()+" historico ");
        sb.append(" where 1=1 ");
        sb.append("   and historico.dataRegistro = (select max(h1.dataRegistro) from "+historicoClass.getName()+" h1 where 1=1 ");

        if (historicoKeyFieldName != null && !historicoKeyFieldName.isEmpty() && historicoKeyFieldValue != null) {
            sb.append(" and h1." + historicoKeyFieldName + "= :historicoKeyFieldValue ");
            params.put("historicoKeyFieldValue", historicoKeyFieldValue);
        }
        sb.append(" ) ");

        if (historicoKeyFieldName != null && !historicoKeyFieldName.isEmpty() && historicoKeyFieldValue != null) {
            sb.append(" and historico."+historicoKeyFieldName+"= :historicoKeyFieldValue ");
            params.put("historicoKeyFieldValue", historicoKeyFieldValue);
        }

        AbstractHistory ultimoHistorico = jpaEntityRepository.queryForOne(sb.toString(), params);
        return ultimoHistorico;

    }

    private Object findHistoryKeyFieldValue(Object originalObject) throws HistoricKeyFieldNotConfigured {
        Field historicoKeyField = findHistoryKeyField(originalObject.getClass());
        if (historicoKeyField == null) return null;
        Object historicoKeyFieldValue = ReflectionUtils.findFieldValue(originalObject, historicoKeyField);
        return historicoKeyFieldValue;
    }

    private <T extends Collection> List<Object> findListHistoryKeyFieldValues(Object originalObject) throws HistoricKeyFieldNotConfigured {
        List<?> list = findListFromObjectList(originalObject);
        if (list == null || list.isEmpty()) return null;

        List<Object> values = new ArrayList<Object>();

        for (Object o : list) {
            Field historicoKeyField = findHistoryKeyField(o.getClass());
            if (historicoKeyField != null) {
                Object historicoKeyFieldValue = ReflectionUtils.findFieldValue(o, historicoKeyField);
                values.add(historicoKeyFieldValue);
            }
        }

        return values;
    }

    private <T extends List> Object findFirstListHistoryKeyFieldValue(T originalListObject) throws HistoricKeyValuesNotEquals, HistoricKeyFieldNotConfigured {
        if (originalListObject == null || originalListObject.isEmpty()) return null;

        checkKeyFieldsValuesEquals(originalListObject);

        Object firstListObject = originalListObject.get(0);
        if (firstListObject == null) return null;

        Field firstHistoricoKeyField = findHistoryKeyField(firstListObject.getClass());
        if (firstHistoricoKeyField == null) return null;

        Object historicoKeyFieldValue = ReflectionUtils.findFieldValue(firstListObject, firstHistoricoKeyField);

        return historicoKeyFieldValue;

    }

    private boolean isList(Object originalObject) {
        if (originalObject == null) return false;
        if (originalObject instanceof Collection) return true;
        else return false;
    }

    private List<?> findListFromObjectList(Object originalObject) {
        boolean isList = isList(originalObject);
        if (isList) {
            return (List<?>) originalObject;
        } else {
            return null;
        }
    }

    public String findHistoryKeyFieldName(Class<?> originalObjectClass) throws HistoricKeyFieldNotConfigured {
        Field historicoKeyField = findHistoryKeyField(originalObjectClass);
        if (historicoKeyField == null) return null;
        return historicoKeyField.getName();
    }

    public Field findHistoryKeyField(Class<?> originalObjectClass) throws HistoricKeyFieldNotConfigured {
        return ReflectionUtils.findFirstFieldByAnnotation(originalObjectClass, HistoryKey.class);
    }

    public <T> boolean hasHistoryAnnotation(Class<T> clazz) {
        return ReflectionUtils.hasClassAnnotation(clazz, History.class);
    }

    public <T> boolean hasListHistoryAnnotation(Class<T> clazz) {
        return ReflectionUtils.hasClassAnnotation(clazz, ListHistoryClass.class);
    }

    public <T> Class<?> findListHistoryClass(Class<T> clazz) {
        ListHistoryClass historicoListAnnotation = (ListHistoryClass) ReflectionUtils.findClassAnnotation(clazz, ListHistoryClass.class);
        if (historicoListAnnotation != null) return historicoListAnnotation.historicoListClass();
        else return null;
    }

    public <T> Class<?> findHistoryClass(Class<T> clazz) {
        History historicoAnnotation = (History) ReflectionUtils.findClassAnnotation(clazz, History.class);
        if (historicoAnnotation != null) {
            Class<? extends AbstractHistory> historyClass = historicoAnnotation.value();
            if (historyClass == null) historyClass = historicoAnnotation.historyClass();
            return historyClass;
        }
        else return null;
    }

    public <T> T createEmptyHistoryObject(Class<?> clazz) {
        Class<?> historicoClass = findHistoryClass(clazz);
        if (historicoClass == null) historicoClass = findListHistoryClass(clazz);
        if (historicoClass != null) {
            try {
                return (T) historicoClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    private Object createHistoryObjectFromOriginalObject(Object originalObject, String[] originalObjectFieldsName, String[] historicoFieldsName) throws HistoricClassNotFound {
        if (originalObjectFieldsName == null || historicoFieldsName == null) return null;
        if (originalObjectFieldsName.length != historicoFieldsName.length) {
            throw new HistoricRuntimeException("Não é possível criar o histório. Tamanhos dos arrays diferentes.");
        }

        Object[] historicoFielsValue = new Object[originalObjectFieldsName.length];

        for (int i = 0 ; i < originalObjectFieldsName.length ; i++) {
            Object originalFieldValue = ReflectionUtils.findFieldValue(originalObject, originalObjectFieldsName[i]);
            historicoFielsValue[i] = originalFieldValue;
        }

        Object historicoObject = createEmptyHistoryObject(originalObject.getClass());
        if (historicoObject == null) {
            throw new HistoricClassNotFound("Não foi possível encontrar classe de histórico para a entidade: "+originalObject.getClass());
        }

        fillHistoryObject(historicoObject, historicoFieldsName, historicoFielsValue);

        return historicoObject;
    }

    private void fillHistoryObject(Object emptyHistoricoObject, String[] historicoFieldsName, Object[] historicoFieldsValue) {
        for (int i = 0 ; i < historicoFieldsName.length ; i++) {
            Field historicoField = ReflectionUtils.findFieldByName(emptyHistoricoObject.getClass(), historicoFieldsName[i]);
            if (historicoField != null) {
                try {
                    ReflectionUtils.setFieldValue(emptyHistoricoObject, historicoField, historicoFieldsValue[i]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T extends List> void checkListObject(T originalListObject) throws HistoricKeyValuesNotFound, HistoricKeyValuesNotEquals, HistoricKeyFieldNotConfigured {
        if (originalListObject == null || originalListObject.isEmpty()) return;

        /*#1*/ checkKeyFieldsFill(originalListObject);
        /*#2*/ checkKeyFieldsValuesEquals(originalListObject);
    }

    private <T extends List> Object findHistoryKeyFieldValueFromObjectList(T originalListObject) throws HistoricKeyValuesNotEquals, HistoricKeyFieldNotConfigured {
        if (originalListObject == null || originalListObject.isEmpty()) return null;
        return findFirstListHistoryKeyFieldValue(originalListObject);
    }

    /**
     * Verifica se os campos chaves estão preenchidos.
     * OBS: Todos os campos chaves devem estar preenchidos
      */
    public <T extends List> void checkKeyFieldsFill(T originalListObject) throws HistoricKeyValuesNotFound, HistoricKeyFieldNotConfigured {
        List<Object> historicoKeyFieldValues = findListHistoryKeyFieldValues(originalListObject);
        if (historicoKeyFieldValues == null || historicoKeyFieldValues.isEmpty() || historicoKeyFieldValues.stream().anyMatch(o -> o == null)) throw new HistoricKeyValuesNotFound("Campos chave da lista nulos ou não encontrados.");
    }

    /**
     * Verifica se o campo chave de todos os elementos da lista são iguais.
     * OBS: Todos os campos chaves deve ter valores iguais
      */
    public <T extends List> void checkKeyFieldsValuesEquals(T originalListObject) throws HistoricKeyValuesNotEquals, HistoricKeyFieldNotConfigured {
        List<Object> historicoKeyFieldValues = findListHistoryKeyFieldValues(originalListObject);
        boolean allHistoricoKeyFieldValuesEquals = historicoKeyFieldValues.stream().distinct().count() <= 1;
        if (!allHistoricoKeyFieldValuesEquals) throw new HistoricKeyValuesNotEquals("Os elementos da lista não possuem o mesmo valor de chave de histórico.");
    }

}
