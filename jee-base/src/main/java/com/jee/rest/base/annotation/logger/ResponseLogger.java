package com.jee.rest.base.annotation.logger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jee.rest.base.response.biz.BizResponse;

@Target({ElementType.PARAMETER,ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME) 
@Documented
public @interface ResponseLogger {
	boolean value() default true ;
	
	String[] include() default {} ;
	
	String[] exclude() default {} ;
}
