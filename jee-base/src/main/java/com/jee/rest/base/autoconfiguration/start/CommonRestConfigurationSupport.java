package com.jee.rest.base.autoconfiguration.start;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jee.rest.base.serializer.extend.ExtendFastJsonHttpMessageConverter;
import com.jee.rest.base.serializer.extend.ExtendSerializeConfigForDataFormat;
import com.jee.rest.base.session.template.SessionTemplate;
import com.jee.rest.base.session.template.redis.RedisSessionTemplate;
import com.jee.rest.base.thread.spring.SpringContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonRestConfigurationSupport {
	
	@Bean
	public SessionTemplate sessionTemplate(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
		log.info("common inject [SessionTemplate] support ");
        RedisSessionTemplate sessionTemplate  = new RedisSessionTemplate() ;
		sessionTemplate.setRedisTemplate(redisTemplate);
		return sessionTemplate ;
	}
	
	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder() ;
	}
	
	
	
	@Bean(name = "stringConverter")
	public HttpMessageConverter<String> stringConverter() {
	    StringHttpMessageConverter converter = new StringHttpMessageConverter( Charset.forName("UTF-8"));
	       
	    return converter;
	}
	
	//@Bean(name = "fastConverter")
    public HttpMessageConverter<?> fastConverter() {
	    //1、定义一个convert转换消息的对象
    	FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
	    //2、添加fastjson的配置信息
	    FastJsonConfig fastJsonConfig = new FastJsonConfig();
	    //fastJsonConfig.setSerializeFilters(DataDecimalContextValueFilter.instance);
	    SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance() ;
	    //serializeConfig.put(Double.class, new DoubleSerializer("0.00"));
	    //serializeConfig.put(Double.class, new BigDecimalFormatSerializer("0.00")) ;
	    fastJsonConfig.setSerializeConfig(serializeConfig);
	    
	    fastJsonConfig.setSerializerFeatures(new SerializerFeature[] {
	    	  //  输出key是包含双引号
              //  SerializerFeature.QuoteFieldNames,
	          //  是否输出为null的字段,若为null 则显示该字段
              //  SerializerFeature.WriteMapNullValue,
	          //  数值字段如果为null，则输出为0
	          SerializerFeature.WriteNullNumberAsZero,
	          //   List字段如果为null,输出为[],而非null
	          SerializerFeature.WriteNullListAsEmpty,
	          //  字符类型字段如果为null,输出为"",而非null
	          SerializerFeature.WriteNullStringAsEmpty,
	          //  Boolean字段如果为null,输出为false,而非null
	          SerializerFeature.WriteNullBooleanAsFalse,
	          //  Date的日期转换器
	          SerializerFeature.WriteDateUseDateFormat,
	          //  循环引用
	          SerializerFeature.DisableCircularReferenceDetect,
	    });
	    fastJsonConfig.setCharset(Charset.forName("UTF-8"));
	    //2-1 处理中文乱码问题
	    List<MediaType> fastMediaTypes = new ArrayList<>();
	    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
	    fastConverter.setSupportedMediaTypes(fastMediaTypes);
	    //3、在convert中添加配置信息
	    fastConverter.setFastJsonConfig(fastJsonConfig);
	    return fastConverter;
	}

	//@Bean(name = "extendFastConverter")
    public HttpMessageConverter<?> extendFastConverter() {
	    //1、定义一个convert转换消息的对象
    	ExtendFastJsonHttpMessageConverter fastConverter = new ExtendFastJsonHttpMessageConverter();
	    //2、添加fastjson的配置信息
	    FastJsonConfig fastJsonConfig = new FastJsonConfig();
	    SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance() ;
	    //serializeConfig.put(Double.class, new BigDecimalFormatSerializer("0.00")) ;
	    //fastJsonConfig.setSerializeConfig(serializeConfig);
	    ExtendSerializeConfigForDataFormat config = new ExtendSerializeConfigForDataFormat() ;
	    fastJsonConfig.setSerializeConfig(config);
	    
	    fastJsonConfig.setSerializerFeatures(new SerializerFeature[] {
	    	  //  输出key是包含双引号
              //  SerializerFeature.QuoteFieldNames,
	          //  是否输出为null的字段,若为null 则显示该字段
              //  SerializerFeature.WriteMapNullValue,
	          //  数值字段如果为null，则输出为0
	          SerializerFeature.WriteNullNumberAsZero,
	          //   List字段如果为null,输出为[],而非null
	          SerializerFeature.WriteNullListAsEmpty,
	          //  字符类型字段如果为null,输出为"",而非null
	          SerializerFeature.WriteNullStringAsEmpty,
	          //  Boolean字段如果为null,输出为false,而非null
	          SerializerFeature.WriteNullBooleanAsFalse,
	          //  Date的日期转换器
	          SerializerFeature.WriteDateUseDateFormat,
	          //  循环引用
	          SerializerFeature.DisableCircularReferenceDetect,
	    });
	    fastJsonConfig.setCharset(Charset.forName("UTF-8"));
	    //2-1 处理中文乱码问题
	    List<MediaType> fastMediaTypes = new ArrayList<>();
	    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
	    fastConverter.setSupportedMediaTypes(fastMediaTypes);
	    //3、在convert中添加配置信息
	    fastConverter.setFastJsonConfig(fastJsonConfig);
	    return fastConverter;
	}


}
