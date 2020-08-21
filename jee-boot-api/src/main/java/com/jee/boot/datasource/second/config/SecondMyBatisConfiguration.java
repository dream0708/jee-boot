package com.jee.boot.datasource.second.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by hackerdom on 2018.6.27.<br>
 */
@Configuration
@MapperScan(basePackages = SecondMyBatisConfiguration.PACKAGE, 
sqlSessionFactoryRef = "secondSqlSessionFactory"
)
@EnableTransactionManagement
@Slf4j
public class SecondMyBatisConfiguration {

	static final String ENTITY_PACKAGE = "com.jee.boot.datasource.second";
	static final String PACKAGE = "com.jee.boot.datasource.second.dao";
	//static final String MAPPER_LOCATION = "com/jee/**/mapper/*.xml";
	static final String MAPPER_LOCATION = "mappers/second/**/*.xml"; // .换成id


	@Bean(name = "secondDataSource")
	@ConfigurationProperties(prefix = "second.druid.datasource")
	public DataSource secondDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}



	//@Primary
	//@Bean(name = "secondSqlSessionFactory")
	public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource secondDataSource)
			throws Exception {
		
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(secondDataSource);
		//sessionFactory.setVfs(SpringBootVFS.class);
		sessionFactory.setTypeAliasesPackage(ENTITY_PACKAGE);
		
		sessionFactory.setMapperLocations(
			new PathMatchingResourcePatternResolver().getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + SecondMyBatisConfiguration.MAPPER_LOCATION));
		SqlSessionFactory factory =  sessionFactory.getObject() ;
		factory.getConfiguration().setMapUnderscoreToCamelCase(true);
		factory.getConfiguration().setUseColumnLabel(true);
		factory.getConfiguration().setUseGeneratedKeys(true);
		return factory ;
	}

	@Bean(name = "secondSqlSessionFactory")
	public SqlSessionFactory secondSqlSessionFactoryPlus(@Qualifier("secondDataSource") DataSource
																secondDataSource)
			throws Exception {
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		//bean.setVfs(SpringBootVFS.class);
		bean.setDataSource(secondDataSource);
		//设置mapper位置
		bean.setTypeAliasesPackage(ENTITY_PACKAGE);
		//设置mapper.xml文件的路径
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(
						PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_LOCATION));

		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setLogImpl(StdOutImpl.class);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setUseColumnLabel(true);
		configuration.setUseGeneratedKeys(true);

		bean.setConfiguration(configuration);
		bean.setPlugins(new Interceptor[]{paginationInterceptorMysql()} );
		return bean.getObject()  ;
	}

	@Bean(name = "secondSqlSessionTemplate")
	public SqlSessionTemplate secondSqlSessionTemplate(
			@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	

	@Bean(name = "secondJdbcTemplate")
	public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDataSource")  DataSource secondDataSource) {
		JdbcTemplate jTemplate = new JdbcTemplate(secondDataSource) ;
		return jTemplate ;
	}


	public PaginationInterceptor paginationInterceptorMysql() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}
	
	@Bean
	public PlatformTransactionManager  txManagerSecond(@Qualifier("secondDataSource")  DataSource secondDataSource) {
		return new DataSourceTransactionManager(secondDataSource) ;
	}
	//@Bean(name = "secondTransactionManager")
	public DataSourceTransactionManager masterTransactionManager(@Qualifier("secondDataSource")  DataSource secondDataSource) {
		return new DataSourceTransactionManager(secondDataSource);
	}
}
