package com.jee.boot.datasource.first.config;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = FirstMyBatisConfiguration.PACKAGE,
sqlSessionFactoryRef = "firstSqlSessionFactory"

//,markerInterface = BaseMapper.class 
)
@EnableTransactionManagement
@Slf4j
public class FirstMyBatisConfiguration {

	static final String PACKAGE = "com.jee.boot.datasource.first.dao";
	static final String ENTITY_PACKAGE = "com.jee.boot.datasource.first";
	//static final String MAPPER_LOCATION = "com/jee/**/mapper/*.xml";
	static final String MAPPER_LOCATION = "mappers/first/**/*.xml"; // .换成id

	//com.github.pagehelper.PageInterceptor page = new PageInterceptor();

	@Bean(name = "firstDataSource")
	@Primary
	@ConfigurationProperties(prefix = "first.druid.datasource")
	public DataSource firstDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}



	//@Primary
	//@Bean(name = "firstSqlSessionFactory")
	public SqlSessionFactory firstSqlSessionFactory(@Qualifier("firstDataSource") DataSource firstDataSource)
			throws Exception {

		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(firstDataSource);
		sessionFactory.setVfs(SpringBootVFS.class);
		sessionFactory.setTypeAliasesPackage(ENTITY_PACKAGE);

		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(
						PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
						MAPPER_LOCATION));
		SqlSessionFactory factory =  sessionFactory.getObject() ;
		factory.getConfiguration().setMapUnderscoreToCamelCase(true);
		factory.getConfiguration().setUseColumnLabel(true);
		factory.getConfiguration().setUseGeneratedKeys(true);
		return factory ;
	}

	@Primary
	@Bean(name = "firstSqlSessionFactory")
	public SqlSessionFactory firstSqlSessionFactoryPlus(@Qualifier("firstDataSource") DataSource
																firstDataSource)
			throws Exception {
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		//bean.setVfs(SpringBootVFS.class);
		bean.setDataSource(firstDataSource);
		//设置mapper位置
		bean.setTypeAliasesPackage(ENTITY_PACKAGE);
		//设置mapper.xml文件的路径
	    bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(
					PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
								+ MAPPER_LOCATION));

		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setLogImpl(StdOutImpl.class);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setUseColumnLabel(true);
		configuration.setUseGeneratedKeys(true);

		bean.setConfiguration(configuration);
		bean.setPlugins(new Interceptor[]{paginationInterceptorMysql()} );
		return bean.getObject()  ;
	}

	@Bean(name = "firstSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate firstSqlSessionTemplate(
			@Qualifier("firstSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Primary
	@Bean(name = "firstJdbcTemplate")
	public JdbcTemplate firstJdbcTemplate(@Qualifier("firstDataSource")  DataSource firstDataSource) {
		JdbcTemplate jTemplate = new JdbcTemplate(firstDataSource) ;
		return jTemplate ;
	}


	public PaginationInterceptor paginationInterceptorMysql() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}

	//@Bean
	public PlatformTransactionManager  txManagerFirst(@Qualifier("firstDataSource")  DataSource firstDataSource) {
		return new DataSourceTransactionManager(firstDataSource) ;
	}

	@Bean(name = "firstTransactionManager")
	@Primary
	public DataSourceTransactionManager masterTransactionManager(@Qualifier("firstDataSource")  DataSource firstDataSource) {
		return new DataSourceTransactionManager(firstDataSource);
	}
	
}
