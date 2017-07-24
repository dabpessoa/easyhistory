package me.dabpessoa.easyHistory.service.jpaTransaction;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public abstract class JPATransactionCallbackWithoutResult implements JPATransactionCallback<Object> {
    public JPATransactionCallbackWithoutResult() {}

    public final Object doInTransaction() {
        this.doInTransactionWithoutResult();
        return null;
    }

    protected abstract void doInTransactionWithoutResult();

}
