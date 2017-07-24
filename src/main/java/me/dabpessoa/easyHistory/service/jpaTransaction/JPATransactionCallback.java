package me.dabpessoa.easyHistory.service.jpaTransaction;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
@FunctionalInterface
public interface JPATransactionCallback<T> {

    public T doInTransaction();

}
