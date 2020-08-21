package com.jee.boot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider.ColumnNames;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "30s" , defaultLockAtLeastFor = "5s" )
public class ShedlockConfiguration {
	
    
    //@Bean
    public LockProvider lockProvider(@Qualifier("redisConnectionFactory1") RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockProvider(redisConnectionFactory) ;
    }

    
   
   
    @Bean
    public LockProvider lockProvider(@Qualifier("secondDataSource") DataSource jeeDataSource) {
    	return new JdbcTemplateLockProvider(
    			jeeDataSource , "tb_task_shedlock");
    }
    
    
    // @Bean
    public LockProvider lockProvider2(@Qualifier("secondDataSource") DataSource jeeDataSource) {
    	return new JdbcTemplateLockProvider(
    			 net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider
    			 .Configuration.builder()
    			.withTableName("tb_jee_task_shedlock")
        	    .withColumnNames(new ColumnNames("name", "lck_untl", "lckd_at", "lckd_by"))
        	    .withJdbcTemplate(new JdbcTemplate(jeeDataSource))
        	    .withLockedByValue("my-value")
        	    .build());
    }
    
}
