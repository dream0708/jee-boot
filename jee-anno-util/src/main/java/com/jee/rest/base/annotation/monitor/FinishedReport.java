package com.jee.rest.base.annotation.monitor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.collections4.functors.FalsePredicate;
import org.apache.commons.collections4.functors.TruePredicate;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface FinishedReport {
	
	
	String       title() ;
	
	String[]     users() default {} ;
	
	
	String       group() default "" ;

}
