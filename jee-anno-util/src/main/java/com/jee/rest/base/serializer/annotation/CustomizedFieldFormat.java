package com.jee.rest.base.serializer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.jee.rest.base.serializer.handler.CustomizedFieldValueHandler;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomizedFieldFormat{

	String value() ;
	
    String error() default "" ;
	
	String nullable() default "" ;
	
	String condition() default "" ;
	
	Class<? extends CustomizedFieldValueHandler> handlerClass()  ;
	
}

