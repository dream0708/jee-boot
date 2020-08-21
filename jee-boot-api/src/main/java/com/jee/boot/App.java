package com.jee.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.jee.rest.base.autoconfiguration.annotation.EnableBaseConfiguration;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
@EnableMethodCache(basePackages = { "com.jee" })
@EnableCreateCacheAnnotation
@EnableBaseConfiguration
@EnableScheduling
@Slf4j
public class App {
	public static void main(String[] args) {
		log.info(">>>>>>>>>>>>>>>>App 启动开始>>>>>>>>>>>>>>>>>>");
		try {
			ApplicationContext ctx = SpringApplication.run(App.class, args);
			ParserConfig.getGlobalInstance().addAccept("com.github.pagehelper.");
			ParserConfig.getGlobalInstance().addAccept("com.jee.");
			log.info(">>>>>>>>>>>>>>>>App 启动 成功>>>>>>>>>>>>>>>>");
		} catch (Exception e) {
			log.info(">>>>>>>>>>>>>>>>App 启动 失败>>>>>>>>>>>>>>>>");
			log.error(e.getMessage(), e);
			throw e;
		}
	}
}
