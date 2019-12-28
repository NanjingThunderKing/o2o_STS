package com.imooc.o2o.config.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * 对标spring-service里面的transactionManager
 * 实现TransactionManagementConfigurer是因为开启tx:annotation-driven
 * @author yangtengyu
 *
 */
@Configuration
// 首先使用注解@EnableTransactionManagement 开启事务支持后
// 在Service方法上添加注解@Transactional便可
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {
	
	@Autowired
	// 注入DataSourceConfiguration里的dataSource,通过createDataSource获取
	private DataSource dataSource;

	/**
	 * 关于事务管理，需要返回PlatformTransactionManager的实现
	 */
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
