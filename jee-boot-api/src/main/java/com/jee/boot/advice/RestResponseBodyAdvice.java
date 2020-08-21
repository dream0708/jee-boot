package com.jee.boot.advice ;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.jee.boot.session.annotation.CurrentSession;
import com.jee.rest.base.annotation.logger.IgnoreLogger;
import com.jee.rest.base.annotation.logger.ResponseLogger;
import com.jee.rest.base.response.biz.BizResponse;

@RestControllerAdvice
public class RestResponseBodyAdvice implements ResponseBodyAdvice<BizResponse> {

	private static final Logger log = LogManager.getLogger("access") ;


	public boolean supports(MethodParameter returnType,
							Class<? extends HttpMessageConverter<?>> converterType) {
		if (returnType.getMethodAnnotation(IgnoreLogger.class) == null) {
			CurrentSession session = returnType.getParameterAnnotation(CurrentSession.class) ;
			if(session != null && session.hash()) {
				return returnType.getParameterType().isAssignableFrom(BizResponse.class);
			}
			return returnType.getParameterType().isAssignableFrom(BizResponse.class);
		}
		return false;
	}

	public BizResponse beforeBodyWrite(BizResponse result, MethodParameter returnType, MediaType selectedContentType,
									   Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
									   ServerHttpResponse response) {
		HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
		String hash = (String) httpRequest.getAttribute(com.jee.boot.utils.Constants.HASH) ;
		if (result != null && StringUtils.isNotBlank(hash)) { // 统一hash处理
			result.hash(hash);
		}
		try {
			ResponseLogger logger = returnType.getMethodAnnotation(ResponseLogger.class) ;
			if(logger == null) {
				log.info("[RESPONSE-ALL] [{}] [@@@{}@@@] ", httpRequest.getServletPath(),
						JSON.toJSONString(result));
				return result ;
			}
			if(!logger.value() ) {
				log.info("[RESPONSE-COMMON] [{}] [@@@ code: {} , msg: {}@@@]  ", httpRequest.getServletPath(),
						result != null ? result.getCode() : "-9999" ,
						result != null ? result.getMsg() : "未配置错误提示");

			}else {
				String[] include = logger.include() ;
				if(ArrayUtils.isNotEmpty(include)) {
					log.info("[RESPONSE-COMMON] [{}] [@@@ code: {} , msg: {} , hash :{} @@@]  ", httpRequest.getServletPath(),
							result != null ? result.getCode() : "-9999" ,
							result != null ? result.getMsg() : "未配置错误提示" ,
							result != null ? result.getHash() : "找不到HASH" );
					if(result != null) {
						for(String key : include) {
							Object object = result.get(key) ;
							log.info(" [RESPONSE-BIZ-{}] => {} " , key , JSON.toJSONString(object));
						}
					}
				}else {
					log.info("[RESPONSE-ALL] [{}] [@@@{}@@@] ", httpRequest.getServletPath(),
							JSON.toJSONString(result));
				}

			}
		}catch (Exception e) {
			log.error("reponsebody advice error :{} " , e.getMessage());
		}
		return result;
	}

}
