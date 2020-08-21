package com.jee.boot.utils;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/**
 @author yaomengke
 @create 2020- 04 - 14 - 16:50

 */
@Component
public class Constants   implements InitializingBean{


    public static final String HASH = "hash" ;
    public static final String USERID = "userid" ;
    public static final String LOGGER = "logger" ;
    public static final String LOGGER_FLAG = "ignore" ;
    public static final String MOBILE_NAME = "mobile" ;
    public static final String SESSION_NAME = "sessionid" ; //标识字段
    public static final String HEADER_NAME = "hsession" ; //标识字段
    
    public static final String USER_ATTR_STRING = "user" ;

    public static final String USERID_PREFIX = "jee:userid:";
    public static final String SESSION_PREFIX = "jee:session:";
 
    public static final String HIDDEN_STRING = "***" ;

    public static final String PROD = "prod" ;
    public static boolean APP_PROD_ENV  ;
    public static int  SESSION_EXPIRE_SECONDS = 1200 ;

    @Resource
    private Environment environment ;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_PROD_ENV = environment.getProperty("spring.profiles.active", "prod").contains(PROD) ;
        SESSION_EXPIRE_SECONDS = Integer.valueOf(environment.getProperty("session.expire.seconds" , "1200")) ;
    }

}