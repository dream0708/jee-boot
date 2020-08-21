package com.jee.rest.base.autoconfiguration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.jee.rest.base.autoconfiguration.start.CommonMvcConfigurationSupport;
import com.jee.rest.base.autoconfiguration.start.CommonRestConfigurationSupport;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ CommonRestConfigurationSupport.class})
public @interface EnableBaseConfiguration {

}
