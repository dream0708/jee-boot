package com.jee.rest.base.annotation.rbac;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Roles {


    String[] value();
    
    
    Logical logical() default Logical.AND; 
}
