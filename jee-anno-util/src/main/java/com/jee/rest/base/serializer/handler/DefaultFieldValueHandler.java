package com.jee.rest.base.serializer.handler;

import com.jee.rest.base.serializer.annotation.CustomizedFieldFormat;

public class DefaultFieldValueHandler implements CustomizedFieldValueHandler{

	@Override
	public Object handlerExtendField(Object ret, Object propertyValue, CustomizedFieldFormat anno) {

        
		return ret ;
	}

}
