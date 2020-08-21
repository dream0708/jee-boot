package com.jee.rest.base.serializer.handler;

import com.jee.rest.base.serializer.annotation.CustomizedFieldFormat;

public interface CustomizedFieldValueHandler {
	
	
	public Object  handlerExtendField(Object ret, Object  propertyValue , CustomizedFieldFormat anno) ;
}
