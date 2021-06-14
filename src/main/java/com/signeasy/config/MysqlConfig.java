package com.signeasy.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableJpaRepositories("com.signeasy.repository")
public class MysqlConfig {

	
	@Value("${hibernate.connection.url}")
	private String url;
	@Value("${username}")
	private String username;
	@Value("${password}")
	private String password;
	@Value("${hibernate.maxpool.size}")
	private int maxPoolSize;
	@Value("${hibernate.minimum.idle}")
	private int minIdle;
	@Value("${hibernate.dialect}")
	private String dialect;
	@Value("${hibernate.connection.driver_class}")
	private String driver;
	@Value("${hibernate.hbm2ddl.auto}")
	private String auto;
	@Value("${spring.jpa.show-sql}")
	private String showSql;
	@Value("${hibernate.connection.pool_size}")
	private int connectionPoolSize;
	//@Value("${}")
	//private int batchSize;
	
	
	@Bean
	public HibernateTemplate getHibernateTemplate() {
		
		return new HibernateTemplate(sessionFactory().getObject());
	}
	
	@Bean(name = "entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory() {
		
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("com.signeasy.entity");
		return sessionFactoryBean;
	}
	
	@Bean
	public DataSource dataSource() {
		
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(username);
		config.setPassword(password);
		config.setMaximumPoolSize(maxPoolSize);
		config.setMinimumIdle(minIdle);
		config.setConnectionTestQuery("SELECT 1");
		return new HikariDataSource(config);
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}
	
	private Properties hibernateProperties() {
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", dialect);
		properties.setProperty("hibernate.connection.driver_class", driver);
		properties.setProperty("hibernate.hbm2ddl.auto", auto);
		properties.setProperty("hibernate.globally_quoted_identifiers", "true");
		properties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
		//properties.setProperty("hibernate.jdbc.batch_size", String.valueOf(batchSize));
		properties.setProperty("show_sql", showSql);
		properties.setProperty("hibernate.connection.pool_size", String.valueOf(connectionPoolSize));
		return properties;
	}
}
