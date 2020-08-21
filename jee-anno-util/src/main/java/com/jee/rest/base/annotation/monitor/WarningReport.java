package com.jee.rest.base.annotation.monitor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WarningReport {
	
	
	String       title() ;
	
	String[]     users() default {} ;
	
	
	String       group() default "" ;
	
	boolean      exceptionStack() default false  ;

}
