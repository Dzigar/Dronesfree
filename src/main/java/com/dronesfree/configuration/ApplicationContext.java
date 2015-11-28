package com.dronesfree.configuration;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.dronesfree.*" })
@EnableTransactionManagement
@Import({ WebAppContext.class, SecurityContext.class, SocialContext.class,
		WebSocetConfig.class, AppWebSocketConfig.class })
@PropertySource("classpath:application.properties")
public class ApplicationContext {

	@Autowired
	private Environment env;

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_GENERATE_DDL = "hibernate.generateDdl";
	private static final String PROPERTY_NAME_DEFAULT_SCHEMA = "hibernate.default_schema";
	private static final String PROPERTY_NAME_HIBERNATE_JARIDA_USERTYPE = "jadira.usertype.autoRegisterUserTypes";
	private static final String PROPERTY_NAME_HIBERNATE_CHARSET = "hibernate.connection.Charset";
	private static final String PROPERTY_NAME_HIBERNATE_CHARACTER_ENCODING = "hibernate.connection.CharacterEncoding";
	private static final String PROPERTY_NAME_HIBERNATE_USE_UNICODE = "hibernate.connection.Useunicode";

	@Bean
	public SessionFactory sessionFactory() {

		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
				dataSource());
		builder.scanPackages("com.dronesfree.*").addProperties(
				getHibernateProperties());

		return builder.buildSessionFactory();
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, "true");
		prop.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "false");
		prop.put(PROPERTY_NAME_HIBERNATE_DIALECT,
				"org.hibernate.dialect.PostgreSQLDialect");
		prop.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");
		prop.put(PROPERTY_NAME_DEFAULT_SCHEMA, "dronesfree");
		prop.put(PROPERTY_NAME_HIBERNATE_GENERATE_DDL, "true");
		prop.put(PROPERTY_NAME_HIBERNATE_JARIDA_USERTYPE, "true");
		prop.put(PROPERTY_NAME_HIBERNATE_CHARSET, "UTF-8");
		prop.put(PROPERTY_NAME_HIBERNATE_CHARACTER_ENCODING, "UTF-8");
		prop.put(PROPERTY_NAME_HIBERNATE_USE_UNICODE, "true");
		return prop;
	}

	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getProperty(PROPERTY_NAME_DATABASE_DRIVER));
		ds.setUrl(env.getProperty(PROPERTY_NAME_DATABASE_URL));
		ds.setUsername(env.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		ds.setPassword(env.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
		return ds;
	}

	// Create a transaction manager
	@Bean
	public HibernateTransactionManager txManager() {
		return new HibernateTransactionManager(sessionFactory());
	}

}