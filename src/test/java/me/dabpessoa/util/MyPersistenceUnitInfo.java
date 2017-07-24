package me.dabpessoa.util;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by diego.pessoa on 24/07/2017.
 */
public class MyPersistenceUnitInfo implements PersistenceUnitInfo {

    public String getPersistenceUnitName() {
        return "ApplicationPersistenceUnit";
    }


    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    public DataSource getJtaDataSource() {
        return null;
    }

    public DataSource getNonJtaDataSource() {
        return null;
    }

    public List<String> getMappingFileNames() {
        return Collections.emptyList();
    }

    public List<URL> getJarFileUrls() {
        try {
            return Collections.list(this.getClass()
                    .getClassLoader()
                    .getResources(""));
        } catch (IOException e) {
        }
        return null;
    }

    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    public List<String> getManagedClassNames() {
        return Collections.emptyList();
    }

    public boolean excludeUnlistedClasses() {
        return false;
    }

    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    public ValidationMode getValidationMode() {
        return null;
    }

    public Properties getProperties() {
        return new Properties();
    }

    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    public ClassLoader getClassLoader() {
        return null;
    }

    public void addTransformer(ClassTransformer transformer) {

    }

    public ClassLoader getNewTempClassLoader() {
        return null;
    }

}
