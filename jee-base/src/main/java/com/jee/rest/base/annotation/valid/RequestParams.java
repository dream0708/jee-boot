package com.jee.rest.base.annotation.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.METHOD ,ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME) 
public @interface RequestParams {
	
	String name() default "" ;
	
	String desc() default "" ;
	
	String defaultValue() default "";

}
