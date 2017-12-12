package liyu.test.springboot.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableJpaRepositories("liyu.test.springboot.dao")
public class JpaConfigure {
	@Autowired
	@Qualifier("dataSourceForJpa")
	private DataSource dataSource;
	
	@Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);
        
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("liyu.test.springboot.model");
        factory.setDataSource(dataSource);

        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put("hibernate.ejb.naming_strategy","org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.jdbc.batch_size",50);
        
        jpaProperties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        factory.setJpaPropertyMap(jpaProperties);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
	
	@Bean
	public TransactionTemplate transactionTemplate(){
        TransactionTemplate transactionTemplate = new TransactionTemplate(
        	new JpaTransactionManager(entityManagerFactory()));
        return transactionTemplate;
	}
}
