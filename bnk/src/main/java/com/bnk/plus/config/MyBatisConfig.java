package com.bnk.plus.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.bnk.plus.config.support.Master;
import com.bnk.plus.config.support.Slave;

public abstract class MyBatisConfig {
	
	protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactory, DataSource dataSource) throws IOException {
		sessionFactory.setDataSource(dataSource);
		ResourceLoader ctx = new DefaultResourceLoader();
		sessionFactory.setConfigLocation(ctx.getResource(AppConstBean.MYBATIS_CONFIG_PATH));
		
		// 설정된 Package의 Class 파일명으로 Type Aliase를 명명한다.
		sessionFactory.setTypeAliasesPackage(AppConstBean.MYBATIS_TYPE_ALIASES_PACKAGE);
	}
}
	
	
@Configuration
@MapperScan(basePackages=AppConstBean.MYBATIS_MAPPER_SCAN_PACKAGE, annotationClass = Master.class, sqlSessionFactoryRef = "masterSqlSessionFactory")
class MasterMyBatisConfig extends MyBatisConfig {
	
	@Autowired
	@Qualifier("masterDataSource")
	DataSource masterDataSource;

	@Bean
	public SqlSessionTemplate sqlSession() throws Exception{
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(masterSqlSessionFactory());
		return sqlSessionTemplate;
	}
	
	@Bean
	public SqlSessionFactory masterSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		
		configureSqlSessionFactory(sessionFactory, masterDataSource);
		
		return sessionFactory.getObject();
	}
	
}

@Configuration
@MapperScan(basePackages=AppConstBean.MYBATIS_MAPPER_SCAN_PACKAGE, annotationClass = Slave.class, sqlSessionFactoryRef = "slaveSqlSessionFactory")
class SlaveMyBatisConfig extends MyBatisConfig {
	
	@Autowired
	@Qualifier("slaveDataSource")
	DataSource slaveDataSource;
	
	@Bean
	public SqlSessionFactory slaveSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		
		configureSqlSessionFactory(sessionFactory, slaveDataSource);
		
		return sessionFactory.getObject();
	}
	
}