package ua.kiev.dk.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by d.koshlyak on 23.07.2015.
 */

@Configuration
@EnableJpaRepositories(basePackages = {
        "ua.kiev.dk.repo"
})
@ComponentScan(value = "ua.kiev.dk")
@EnableTransactionManagement
public class PersistenceContext {

    private static final String[] ENTITY_PACKAGES = {
            "ua.kiev.dk.entities"
    };
    private final static String PROPERTY_DB_URL = "jdbc:mysql://localhost:3306/Adv";
    private final static String PROPERTY_DB_USER_NAME = "root";
    private final static String PROPERTY_DB_USER_PASSWORD = "r00t";
    private final static String PROPERTY_DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_MYSQL_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();


        dataSource.setUrl(PROPERTY_DB_URL);
        dataSource.setUsername(PROPERTY_DB_USER_NAME);
        dataSource.setPassword(PROPERTY_DB_USER_PASSWORD);
        dataSource.setDriverClassName(PROPERTY_DB_DRIVER_CLASS);

        dataSource.setMaxTotal(20);
        dataSource.setMaxConnLifetimeMillis(10000);
        dataSource.setMaxWaitMillis(15000);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception{
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("ua.kiev.dk.entities");

        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(additionalProperties());

        return entityManagerFactory;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");
        properties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, PROPERTY_NAME_MYSQL_DIALECT);
        return properties;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
