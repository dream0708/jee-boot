package com.jee.rest.base.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.serializer.ValueFilter;

import lombok.Data;

@Data
public class RequestValueFilter implements ValueFilter{
	
	private List<String> senstive ;
	
	private boolean zip = false ;

	@Override
	public Object process(Object object, String name, Object value) {
		if(StringUtils.isBlank(name)) {
			return "not*found*key" ;
		}
		if(senstive.contains(name)){
    		return "***" ;
    	}else{
    		if(zip){
    			return value ;
    		}else{
    			return value ;
    		}
    	}
	}

}
