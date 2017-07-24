package me.dabpessoa.test.util;

import me.dabpessoa.test.model.Usuario;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class JPAUtil {

	public static void main(String[] args) {
		JPAUtil.teste();
	}

	public static void teste() {

//		Thread.currentThread().setContextClassLoader(new ClassLoader() {
//			@Override
//			public Enumeration<URL> getResources(String name) throws IOException {
//				if (name.equals("META-INF/persistence.xml")) {
//					return Collections.enumeration(Arrays.asList(new File("conf/persistence.xml")
//							.toURI().toURL()));
//				}
//				return super.getResources(name);
//			}
//		});
//		Persistence.createEntityManagerFactory("test");


//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dabpessoaPU");
		EntityManagerFactory factory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				archiverPersistenceUnitInfo(), Collections.unmodifiableMap(new HashMap<String, Object>() {
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

		EntityManager em = factory.createEntityManager();
		
		
		List<Usuario> usuarios = em.createQuery("select c from Usuario c").getResultList();
		System.out.println(usuarios);
		
		////////////////////////////////////
		
		
//		Contato contato = new Contato();
//		contato.setNome("teste JPA");
//		contato.setCpf("12341928340");
//
//		em.getTransaction().begin();
//		em.persist(contato);
//		em.getTransaction().commit();
//
//		em.close();
	}

	private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
		return new PersistenceUnitInfo() {

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
				} catch (IOException e) {}
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

		};
	}

}
