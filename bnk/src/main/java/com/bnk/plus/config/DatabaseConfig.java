package com.bnk.plus.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bnk.plus.commons.CoConstDef;
import com.bnk.plus.config.properties.DatabaseProperties;
import com.bnk.plus.config.properties.MasterDatabaseProperties;
import com.bnk.plus.config.properties.SlaveDatabaseProperties;
import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@PropertySources(value = {@PropertySource(value=AppConstBean.APP_CONFIG_PROPERTIES_PATH)})
public abstract class DatabaseConfig {
	
	@Resource
	private Environment env;
	
	protected void configureDataSource(BoneCPDataSource dataSource, DatabaseProperties databaseProperties) {
		
		if(CoConstDef.FLAG_YES.equals(env.getRequiredProperty(databaseProperties.getDatabaseMod()))) {
			dataSource.setDriverClass(env.getRequiredProperty(databaseProperties.getDatabaseDriverLog4()));
			dataSource.setJdbcUrl(env.getRequiredProperty(databaseProperties.getDatabaseUrlLog4()));
		} else {
			dataSource.setDriverClass(env.getRequiredProperty(databaseProperties.getDatabaseDriver()));
			dataSource.setJdbcUrl(env.getRequiredProperty(databaseProperties.getDatabaseUrl()));
		}
		
		dataSource.setUsername(env.getRequiredProperty(databaseProperties.getDatabaseUsername()));
		dataSource.setPassword(env.getRequiredProperty(databaseProperties.getDatabasePassword()));
		
		
		/*
		 * BoneCP Config
		 */
		// Pool에서 Connection을 가져올 때 Lock 경쟁을 최소화 하기 위해 pool을 여러 개의 파티션으로 나눌 수 있음 (기본:1, 높은 성능 필요할때 3-4 추천)
		dataSource.setPartitionCount(1);
		if("LIVE".equalsIgnoreCase(env.getProperty("server.mode"))){
			// 파티션별 Connection 수. Connection = partitionCount * maxConnectionsPerPartion
			dataSource.setMinConnectionsPerPartition(5);
			dataSource.setMaxConnectionsPerPartition(10);
		}else{
			dataSource.setMinConnectionsPerPartition(1);
			dataSource.setMaxConnectionsPerPartition(2);
		}
    }
}

@Configuration
@EnableTransactionManagement
class MasterDatabaseConfig extends DatabaseConfig {
      
    @Primary
    @Bean(name = "masterDataSource", destroyMethod = "close")
    public DataSource masterDataSource() {
    	BoneCPDataSource masterDataSource = new BoneCPDataSource();
        configureDataSource(masterDataSource, new MasterDatabaseProperties());
        return masterDataSource;
    }
      
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(masterDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}

@Configuration
@EnableTransactionManagement
class SlaveDatabaseConfig extends DatabaseConfig {
	
    @Bean(name = "slaveDataSource", destroyMethod = "close")
    public DataSource slaveDataSource() {
    	BoneCPDataSource slaveDataSource = new BoneCPDataSource();
        configureDataSource(slaveDataSource, new SlaveDatabaseProperties());
        return slaveDataSource;
    }
      
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(slaveDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}
