package com.jee.rest.base.annotation.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.METHOD ,ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME) 
public @interface Maxs {
	
	double value() default Double.MAX_VALUE ;
	
    String message() default "" ;
}
