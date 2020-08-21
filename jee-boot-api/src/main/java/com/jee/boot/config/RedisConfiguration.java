package com.jee.boot.config;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class RedisConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "spring.redis.pool")
	public GenericObjectPoolConfig redisPoolConfig() {
		return new GenericObjectPoolConfig<>();
	}

	/**
	 * 配置第一个数据源
	 *
	 * @return
	 */

	@Bean
	@ConfigurationProperties(prefix = "spring.redis1")
	public RedisStandaloneConfiguration redisConfig1() {
		return new RedisStandaloneConfiguration();
	}

	/**
	 * 配置第二个数据源
	 *
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.redis2")
	public RedisStandaloneConfiguration redisConfig2() {
		return new RedisStandaloneConfiguration();
	}

	/**
	 * jedisPool方式
	 *
	 * @return
	 */

	@Bean( "redisConnectionFactory")
	@Primary
	public JedisConnectionFactory redisConnectionFactory(
			@Qualifier("redisPoolConfig") GenericObjectPoolConfig redisPoolConfig,
			@Qualifier("redisConfig1") RedisStandaloneConfiguration redisConfig1) {

		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
				.builder();
		jedisBuilder.poolConfig(redisPoolConfig);
		JedisClientConfiguration jedisClientConfiguration = jedisBuilder.build();
		return new JedisConnectionFactory(redisConfig1, jedisClientConfiguration);

	}
	
	@Bean( "redisConnectionFactory1" )
	public JedisConnectionFactory redisConnectionFactory1(
			@Qualifier("redisPoolConfig") GenericObjectPoolConfig redisPoolConfig,
			@Qualifier("redisConfig1") RedisStandaloneConfiguration redisConfig1) {

		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
				.builder();
		jedisBuilder.poolConfig(redisPoolConfig);
		JedisClientConfiguration jedisClientConfiguration = jedisBuilder.build();
		return new JedisConnectionFactory(redisConfig1, jedisClientConfiguration);

	}

	/**
	 * Lettuce方式
	 *
	 * @return
	 */

	@Bean("redisConnectionFactory2")
	public LettuceConnectionFactory redisConnectionFactory2(
			@Qualifier("redisPoolConfig") GenericObjectPoolConfig redisPoolConfig,
			@Qualifier("redisConfig2") RedisStandaloneConfiguration redisConfig2) {

		LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
				.poolConfig(redisPoolConfig).build();
		return new LettuceConnectionFactory(redisConfig2, clientConfiguration);
	}

	@Bean({ "redisTemplate", "redisTemplate1" })
	@Primary
	public RedisTemplate<String, String> redisTemplate(@Qualifier("redisConnectionFactory1")  RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean("redisTemplate2")
	public RedisTemplate<String, String> redisTemplate1(
			@Qualifier("redisConnectionFactory2") RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean(value = "redissonClient", destroyMethod = "shutdown")
	public RedissonClient redissonClient(
			@Qualifier("redisConnectionFactory1") JedisConnectionFactory jedisConnectionFactory) throws IOException {

		Config config = new Config();
		config.useSingleServer()
				.setAddress("redis://" + jedisConnectionFactory.getStandaloneConfiguration().getHostName() + ":"
						+ jedisConnectionFactory.getStandaloneConfiguration().getPort())
				.setPassword(new String(jedisConnectionFactory.getStandaloneConfiguration().getPassword().get()))
				.setConnectionMinimumIdleSize(10);

		return Redisson.create(config);

	}

}
