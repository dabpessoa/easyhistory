package me.dabpessoa.easyHistory.util;

import me.dabpessoa.easyHistory.exceptions.HistoricRuntimeException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JpaEntityRepository {

    private EntityManager entityManager;

    public JpaEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> List<T> findAll(Class<T> clazz){
        return entityManager.createQuery("from ".concat(clazz.getSimpleName())).getResultList();
    }

    public <T> T findById(Class<T> clazz, Serializable id){
        return entityManager.find(clazz, id);
    }

    public <T> void save(T entity){
        entityManager.persist(entity);
    }

    public <T> T update(T entity) {
        return entityManager.merge(entity);
    }

    public <T> void delete(T entity){
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
    }

    public <T> void saveAll(Collection<T> entities){
        if(entities == null || entities.isEmpty()){
            return;
        }
        entities.stream().forEach( e -> save(e) );
    }

    public <T> void deleteAll(Collection<T> entities){
        if(entities == null || entities.isEmpty()){
            return;
        }
        entities.stream().forEach( e -> delete(e) );
    }

    public int executeUpdate(String queryS, Map<String, Object> params){
        Query query = entityManager.createQuery(queryS);
        addQueryParams(query,params);
        return query.executeUpdate();
    }

    public <T> List<T> queryForList(String queryS){
        return queryForList(queryS, (Object) null);
    }

    public <T> List<T> queryForList(String queryS, Map<String, Object> params){
        Query query = entityManager.createQuery(queryS);
        addQueryParams(query, params);
        return query.getResultList();
    }

    public <T> List<T> queryForList(String queryS, Object... params){
        Query query = entityManager.createQuery(queryS);
        addQueryParams(query, params);
        return query.getResultList();
    }

    public <T> T queryForOne(String queryS, Object... params){
        Query query = entityManager.createQuery(queryS);
        addQueryParams(query, params);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }catch (Exception e){
            throw new HistoricRuntimeException(e);
        }
    }

    public <T> T queryForOne(String queryS){
        return queryForOne(queryS, (Object) null);
    }

    public <T> T queryForOne(String queryS, Map<String, Object> params){
        Query query = entityManager.createQuery(queryS);
        addQueryParams(query,params);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }catch (Exception e){
            throw new HistoricRuntimeException(e);
        }
    }

    public <T> List<T> sqlQueryForList(String queryS){
        return sqlQueryForList(queryS, null);
    }

    public <T> List<T> sqlQueryForList(String queryS, Map<String, Object> params){
        Query query = entityManager.createNativeQuery(queryS);
        addQueryParams(query, params);
        return query.getResultList();
    }

    public <T> T sqlQueryForOne(String queryS){
        return sqlQueryForOne(queryS, null);
    }

    public <T> T sqlQueryForOne(String queryS, Map<String, Object> params){
        Query query = entityManager.createNativeQuery(queryS);
        addQueryParams(query,params);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }catch (Exception e){
            throw new HistoricRuntimeException(e);
        }
    }

    public static void addQueryParams(Query query, Map<String, Object> params) {
        if(query != null && params != null){
            for (String param : params.keySet()) {
                Object value = params.get(param);
                query.setParameter(param, value);
            }
        }
    }

    public static void addQueryParams(Query query, Object... params) {
        if(query != null && params != null){
            int position = 1;
            for (Object param : params) {
                query.setParameter(position++, param);
            }
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}