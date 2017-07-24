package me.dabpessoa.test;

import me.dabpessoa.easyHistory.exceptions.HistoricClassNotFound;
import me.dabpessoa.easyHistory.exceptions.HistoricKeyFieldNotConfigured;
import me.dabpessoa.easyHistory.service.EasyHistory;
import me.dabpessoa.test.model.Pessoa;
import me.dabpessoa.util.MyPersistenceUnitInfo;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class EasyHistoryTest {

    private static EntityManager entityManager;

    @Test
    public void deveSalvarHistorico() {

        EasyHistory hs = new EasyHistory(entityManager);

        Pessoa p = new Pessoa();
        p.setNome("diego");
        p.setCpf("54654564");

        try {
            hs.saveHistoryIfNotEquals(p);
        } catch (HistoricClassNotFound historicClassNotFound) {
            historicClassNotFound.printStackTrace();
        } catch (HistoricKeyFieldNotConfigured historicKeyFieldNotConfigured) {
            historicKeyFieldNotConfigured.printStackTrace();
        }


    }

    @BeforeClass
    public static void before() {
        EntityManagerFactory factory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                new MyPersistenceUnitInfo(), Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put(AvailableSettings.JPA_JDBC_DRIVER, "org.postgresql.Driver");
                        put(AvailableSettings.JPA_JDBC_URL, "jdbc:postgresql://localhost:5432/dabpessoa");
                        put(AvailableSettings.JPA_JDBC_USER, "postgres");
                        put(AvailableSettings.JPA_JDBC_PASSWORD, "postgres");
                        put(AvailableSettings.DIALECT, Oracle12cDialect.class);
//						put(AvailableSettings.HBM2DDL_AUTO, AvailableSettings.CREATE);
                        put(AvailableSettings.SHOW_SQL, false);
                        put(AvailableSettings.QUERY_STARTUP_CHECKING, false);
                        put(AvailableSettings.GENERATE_STATISTICS, false);
                        put(AvailableSettings.USE_REFLECTION_OPTIMIZER, false);
                        put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);
                        put(AvailableSettings.USE_QUERY_CACHE, false);
                        put(AvailableSettings.USE_STRUCTURED_CACHE, false);
                        put(AvailableSettings.STATEMENT_BATCH_SIZE, 20);
                    }
                })
        );

        entityManager = factory.createEntityManager();
    }

}
