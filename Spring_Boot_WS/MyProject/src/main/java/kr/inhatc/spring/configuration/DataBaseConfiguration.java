package kr.inhatc.spring.configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DataBaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	//hikari(DB)관련 설정
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig()
	{
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception
	{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println("==========>" + dataSource.toString());
		return dataSource;
	}
	
	//sql 관련 세션 설정
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception
	{		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		//Mybatis 설정 추가
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		return sqlSessionFactoryBean.getObject();
	}
	
	//표기법 설정
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)
	{
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	// JPA 설정
	// JPA 설정 빈 등록
	@Bean
	@ConfigurationProperties(prefix = "spring.jpa")
	public Properties hibernateConfig() {
		return new Properties();
	}
}
