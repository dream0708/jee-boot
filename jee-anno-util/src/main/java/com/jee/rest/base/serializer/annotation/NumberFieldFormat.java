package com.jee.rest.base.serializer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 用于解决BigDecimal序列化精度问题
 * 将BigDecimal转成String
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NumberFieldFormat {
   
    String format() default ""  ;
    
    RoundingMode mode() default RoundingMode.HALF_UP ;
    
    int   groupingSize() default 0 ;
    
    int   scale() default -1 ;
    
    String error() default "0.0" ;
    
    String nullable() default "0.0"  ;
}
