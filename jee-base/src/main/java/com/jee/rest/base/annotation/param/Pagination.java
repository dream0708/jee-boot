package com.jee.rest.base.annotation.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  
@Retention(RetentionPolicy.RUNTIME) 
public @interface Pagination {
	
	String noName() default "pageNo" ;
	
	String sizeName() default "pageSize" ;
	
	int defaultNo() default 1 ;
	
	int defaultSize() default 15 ;

}
