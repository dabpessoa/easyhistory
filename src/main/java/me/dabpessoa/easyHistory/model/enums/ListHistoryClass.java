package me.dabpessoa.easyHistory.model.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by diego.pessoa on 11/07/2017.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListHistoryClass {
    Class<?> historicoListClass();
}
