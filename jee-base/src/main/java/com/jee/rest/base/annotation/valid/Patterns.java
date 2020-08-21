package com.jee.rest.base.annotation.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jee.rest.base.anno.utils.RegularUtils;

@Target({ElementType.PARAMETER,ElementType.METHOD ,ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME) 
public @interface Patterns {

	String regex() default RegularUtils.allReg ;
	
	String message() default "" ;
}
