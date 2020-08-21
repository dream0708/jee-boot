package com.jee.rest.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.jee.rest.base.util.LogUtils;

import io.netty.handler.codec.Headers;
import lombok.Data;

@Data
public class AsyncLoggerApiInterceptor extends  HandlerInterceptorAdapter {

	private static final Logger logger = LogManager.getLogger("access");
	
	private ValueFilter valueFilter ;
	
	private String[] headers ; 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ThreadContext.clearAll();
	    LogUtils.logAsyncAccessEnterValueFilter(request , true , valueFilter );
	    LogUtils.logAsyncHeaderAccessEnter(request, headers);
        return super.preHandle(request, response, handler);
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LogUtils.logAsyncAccessCompletion(request );
		if(null != ex){
			logger.error(ex.getMessage()  , ex);
		}
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ThreadContext.clearAll();
	}

	
	

}
