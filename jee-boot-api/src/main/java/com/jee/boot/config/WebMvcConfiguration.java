package com.jee.boot.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jee.boot.interceptor.SessionUserInterceptor;
import com.jee.boot.utils.Constants;
import com.jee.rest.base.interceptor.AsyncLoggerApiInterceptor;
import com.jee.rest.base.serializer.extend.ExtendFastJsonHttpMessageConverter;
import com.jee.rest.base.serializer.extend.ExtendSerializeConfigForDataFormat;
import com.jee.rest.base.util.RequestValueFilter;

@ConditionalOnWebApplication
@Order(100)
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport{
	


	@Resource
	private SessionUserInterceptor sessionUserInterceptor ;
	
	@Value("${mvc.logger.sensitives:pwd,password}")
	private String mvcSensitives = "pwd,password" ;
	
	@Value("${mvc.logger.exclude.paths:/valid/code/**,/resource/**,/apidoc/**}")
	private String mvcExcludePaths = "/valid/code/**,/resource/**,/apidoc/**" ;
	
	@Value("${mvc.apidoc.paths:file:D:/workspace10/jee-boot/jee-boot-api/apidoc/}")
	private String apiDocPath = "file:D:/workspace10/jee-boot/jee-boot-api/apidoc/" ;
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/**")
		        .addResourceLocations("classpath:/static/","classpath:/public/");
		if(true) {
			registry.addResourceHandler("/apidoc/**")
			        .addResourceLocations(apiDocPath) ;
		}
		super.addResourceHandlers(registry);
	}
	
	

	@Override
	protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		super.configureContentNegotiation(configurer);
	}

	@Override
	protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

		super.addReturnValueHandlers(returnValueHandlers);
	}

	
	public HttpMessageConverter<String> stringConverter() {
	    StringHttpMessageConverter converter = new StringHttpMessageConverter( Charset.forName("UTF-8"));
	       
	    return converter;
	}
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

    //@Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter(){
        ByteArrayHttpMessageConverter bam = new ByteArrayHttpMessageConverter();
        List<org.springframework.http.MediaType> mediaTypes = new LinkedList<org.springframework.http.MediaType>();
        mediaTypes.add(org.springframework.http.MediaType.APPLICATION_JSON);
        mediaTypes.add(org.springframework.http.MediaType.IMAGE_JPEG);
        mediaTypes.add(org.springframework.http.MediaType.IMAGE_PNG);
        mediaTypes.add(org.springframework.http.MediaType.IMAGE_GIF);
        mediaTypes.add(org.springframework.http.MediaType.TEXT_PLAIN);
        mediaTypes.add(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM) ;
        bam.setSupportedMediaTypes(mediaTypes);
        return bam;
    }
    
	
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

	
	
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.clear();
	    converters.add(stringConverter());
	    converters.add(extendFastConverter());
	    converters.add(byteArrayHttpMessageConverter()) ;
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		argumentResolvers.add(sessionUserInterceptor) ;
		super.addArgumentResolvers(argumentResolvers);
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		RequestValueFilter valueFilter = new RequestValueFilter() ;
		valueFilter.setSenstive(Arrays.asList(mvcSensitives.split(",")));
		valueFilter.setZip(false);
		AsyncLoggerApiInterceptor iater = new AsyncLoggerApiInterceptor() ;
		iater.setValueFilter(valueFilter);
		iater.setHeaders(new String[] {Constants.HEADER_NAME , Constants.HASH});
		registry.addInterceptor(iater).excludePathPatterns(mvcExcludePaths.split(",")) ;
		super.addInterceptors(registry);
		
	}
	

}
