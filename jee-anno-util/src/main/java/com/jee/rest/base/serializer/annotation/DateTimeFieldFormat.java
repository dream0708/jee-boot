package com.jee.rest.base.serializer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.time.DateFormatUtils;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateTimeFieldFormat{

	String format() default "yyyyMMdd" ;
	
	String error() default "" ;
	
	String nullable() default "" ;
	
}

