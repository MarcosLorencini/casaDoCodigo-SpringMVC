package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//indica que vamos usar o controle transacional do Spring
public class JPAConfiguration {
	@Bean// indica que os objetos serão gerenciados pelo Spring e podem ser injetados em qualquer ponto do código.
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();//abstracao do persistence.xml
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[]{"br.com.casadocodigo.loja.models"});
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();//escolher de implem. da JPA que no caso é o Hibernate
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		
		return em;
	}
	@Bean
	public DataSource dataSource(){//configura os parametros de conexao com o bd
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");
		dataSource.setUsername("root");
		dataSource.setPassword("171217");
		return dataSource;
	}
	
	private Properties additionalProperties(){
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){ //Faz com que a EntityManager seja capaz de realizar operações transacionais
		JpaTransactionManager transactionManager = new JpaTransactionManager();// A escolha da implementação que faz o controle transacional é para JPA, poderiamos escolher para Hibernate ou até para JDBC   
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
}
