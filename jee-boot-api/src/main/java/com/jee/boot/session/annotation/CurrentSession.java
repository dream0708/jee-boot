package com.jee.boot.session.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yaomengke
 * @create 2020- 04 - 15 - 9:02
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentSession {

    /**
     * 重复提交
     * @return
     */
    boolean hash() default false ;
    
    /**
     * 重复提交时间
     */
    int lock() default  -1 ;
    /**
     * 打印日志
     * @return
     */
    boolean logger() default true  ;

    /**
     * 可见范围
     * @return
     */
    String  range() default "" ;


    /**渠道
     */
    String[] channel() default { } ;


}