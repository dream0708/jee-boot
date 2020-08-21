package com.jee.rest.base.annotation.session;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 会话管理控制可配置注解
 * @author yaomengke
 *
 */
@Target(ElementType.PARAMETER)  
@Retention(RetentionPolicy.RUNTIME) 
public @interface SessionUser {

	
	String value() default "" ; //用户身份认证字段
	
	boolean hash() default false ;  //随机码  防止重复提交和CSRF攻击
	
	String[] range() ;
}
