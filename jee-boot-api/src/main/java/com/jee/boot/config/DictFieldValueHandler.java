package com.jee.boot.config;

import com.jee.boot.datasource.second.entity.Dict;
import com.jee.boot.datasource.second.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;
import com.jee.rest.base.serializer.annotation.CustomizedFieldFormat;
import com.jee.rest.base.serializer.handler.CustomizedFieldValueHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DictFieldValueHandler implements CustomizedFieldValueHandler {


	@Autowired
	private DictService dictService ;

	@Override
	public Object handlerExtendField(Object ret, Object propertyValue, CustomizedFieldFormat anno) {

		//log.info("{} , {}" , ret, anno.value());
		String type = anno.value() ;
		if(ret == null){
			Dict dict = new Dict() ;
			dict.setType(type);
			return dict ;
		}
		Dict dict = dictService.getDictDetails(type, String.valueOf(ret)) ;
		if(dict == null){
			dict = new Dict() ;
			dict.setType(type);
			dict.setValue(String.valueOf(ret));
		}
		return dict ;
	}

}
