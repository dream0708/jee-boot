package com.jee.rest.base.thread.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



public class SpringContextHolder implements ApplicationContextAware {
	
	
	   private static ApplicationContext applicationContext;


	    //实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	    @Override
	    public void setApplicationContext(ApplicationContext applicationContext) {
	        SpringContextHolder.applicationContext = applicationContext;
	    }


	    //取得存储在静态变量中的ApplicationContext.
	    public static ApplicationContext getApplicationContext() {
	        checkApplicationContext();
	        return applicationContext;
	    }

	    private static void checkApplicationContext() {
	        if (applicationContext == null) {
	            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
	        }
	    }

}
