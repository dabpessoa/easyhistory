package me.dabpessoa.easyHistory.model.enums;

import me.dabpessoa.easyHistory.model.AbstractHistory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by diego.pessoa on 06/07/2017.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface History {

    /**
     * Representa a classe de histórico
     */
    Class<? extends AbstractHistory> value();
    /**
     * Representa a classe de histórico (outra opção)
     */
    Class<? extends AbstractHistory> historyClass() default AbstractHistory.class;

}
