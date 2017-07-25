package me.dabpessoa.easyHistory.service.jpaTransaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class JPATransactionTemplate {

    private EntityManager entityManager;

    public JPATransactionTemplate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityTransaction getTransaction () {
        return entityManager.getTransaction();
    }

    public void execute(JPATransactionCallbackWithoutResult callback) {
        execute((JPATransactionCallback)callback);
    }

    public <T> T execute(JPATransactionCallback<T> callback) {
        EntityTransaction transaction = getTransaction();
        T returnedObject = null;

        transaction.begin();
        try {
            returnedObject = callback.doInTransaction();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
        transaction.commit();

        return returnedObject;
    }

}
