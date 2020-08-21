package com.jee.rest.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

public class LogUtils {

	private static String logAccessName = "access";
	private static String logErrorName = "error";
	
    public static final Logger ERROR_LOG = LogManager.getLogger(logErrorName);
    public static final Logger ACCESS_LOG = LogManager.getLogger(logAccessName);

   
    public static void logAsyncAccessEnter(HttpServletRequest request ){
    	logAsyncAccessEnter(request , Constants.sensitives , true ,  true) ;
    }
    
    public static void logAsyncAccessCompletion(HttpServletRequest request){
    	logAsyncAccessCompletion(request ,  Constants.sensitives ,  true ) ;
    }
    
    
    public static void logAsyncAccessEnter(HttpServletRequest request , List<String> senstive , boolean input , boolean zip) {
    	
    	
        String method = request.getMethod();
        String ip = IpUtils.getIpAddr(request);
        String url = request.getRequestURI();
      
        StringBuilder s = new StringBuilder();
	    //接口处理时间
		String uuid = (String) request.getAttribute(Constants.REQEUST_UUID_FLAG) ;
		
		if(StringUtils.isBlank(uuid)){ //第一次进入接口
			uuid = UUID.randomUUID().toString() ;
        	request.setAttribute(Constants.REQEUST_UUID_FLAG, uuid );
        	request.setAttribute(Constants.REQEUST_TIME_FLAG , System.currentTimeMillis());
        	//MDC.put(Constants.MDC_UUID_FLAG, uuid);
        	ThreadContext.put(Constants.MDC_UUID_FLAG, uuid);
        	s.append(getBlock("ENTER")) ;
        	s.append(getBlock(method));
            s.append(getBlock(ip));
            s.append(getBlock(url));
            if(input){
            	String params = getParams(request , senstive , zip);
                s.append(getBlock(params));
            }
            
        }else{ //异步有效 第二次进入接口
        	ThreadContext.put(Constants.MDC_UUID_FLAG, uuid);
        	String userid =  (String) request.getAttribute(Constants.REQUEST_USER_FLAG) ;
        	ThreadContext.put(Constants.REQUEST_USER_FLAG, userid);
        	s.append(getBlock("ASYNC")) ;
        	s.append(getBlock(method));
            s.append(getBlock(ip));
            s.append(getBlock(url));
        }
		
		
        getAccessLog().info(s.toString());
    }
    
    
    public static void logAsyncHeaderAccessEnter(HttpServletRequest request , String[] headers) {
    	
       
        //String url = request.getServletPath() ;
        StringBuilder s = new StringBuilder();
    	s.append(getBlock("HEADER")) ;
    	s.append(" [{ ") ;
        if(ArrayUtils.isNotEmpty(headers)) {
        	for(String name : headers) {
        		s.append("\"").append( name).append("\" : ") ;
        		s.append("\"").append( request.getHeader(name)).append("\"  ,  ") ;
        	}
        }
    	s.append(" }] ") ;
        getAccessLog().info(s.toString());
        
       
		
		
    }
    
    
    public static void logAsyncAccessEnterValueFilter(HttpServletRequest request , boolean input , ValueFilter vf) {
    	
    	
        String method = request.getMethod();
        String ip = IpUtils.getIpAddr(request);
        String url = request.getServletPath() ;
       
      
        StringBuilder s = new StringBuilder();
	    //接口处理时间
		String uuid = (String) request.getAttribute(Constants.REQEUST_UUID_FLAG) ;
		
		if(StringUtils.isBlank(uuid)){ //第一次进入接口
			uuid = UUID.randomUUID().toString() ;
        	request.setAttribute(Constants.REQEUST_UUID_FLAG, uuid );
        	request.setAttribute(Constants.REQEUST_TIME_FLAG , System.currentTimeMillis());
        	//MDC.put(Constants.MDC_UUID_FLAG, uuid);
        	ThreadContext.put(Constants.MDC_UUID_FLAG, uuid);
        	s.append(getBlock("ENTER")) ;
        	s.append(getBlock(method));
            s.append(getBlock(ip));
            s.append(getBlock(url));
            if(input){
            	String params = getParamsValueFilter(request, vf );
                s.append(getBlock(params));
            }
            
        }else{ //异步有效 第二次进入接口
        	
        	ThreadContext.put(Constants.MDC_UUID_FLAG, uuid);
        	String userid =  (String) request.getAttribute(Constants.REQUEST_USER_FLAG) ;
        	ThreadContext.put(Constants.REQUEST_USER_FLAG, userid);
        	s.append(getBlock("ASYNC")) ;
        	s.append(getBlock(method));
            s.append(getBlock(ip));
            s.append(getBlock(url));
        }
		
		
        getAccessLog().info(s.toString());
    }
    
   
  
    public static void logAsyncAccessCompletion(HttpServletRequest request , List<String> senstive , boolean zip ) {
        
        String method = request.getMethod();
        String ip = IpUtils.getIpAddr(request);
        String url = request.getServletPath() ;
        StringBuilder s = new StringBuilder();
    
        s.append(getBlock("RETURN")) ;
        Long end = System.currentTimeMillis() ;
        Long start = (Long) request.getAttribute(Constants.REQEUST_TIME_FLAG) ;
        Long consume = 0L ;
        if(null != start){
        	consume = end - start ;
        }
        s.append(getBlock(consume + ":ms")) ;
        
        s.append(getBlock(method));
        s.append(getBlock(ip));
        s.append(getBlock(url));
        getAccessLog().info(s.toString());
    }
    
   
    

    /**
     * 记录异常错误
     * 格式 [exception]
     *
     * @param message
     * @param e
     */
    public static void logError(String message, Throwable e) {
        StringBuffer s = new StringBuffer();
        s.append(getBlock("exception"));
        s.append(getBlock(message));
        ERROR_LOG.error(s.toString(), e);
    }

    /**
     * 记录页面错误
     * 错误日志记录 [page/eception][username][statusCode][errorMessage][servletName][uri][exceptionName][ip][exception]
     *
     * @param request
     */
    public static void logPageError(HttpServletRequest request) {
        
    }


    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }



    protected static String getParams(HttpServletRequest request , List<String> senstive , boolean zip) {
        final Map<String, String[]> params = request.getParameterMap();
        final Map<String, List<String>> temps = new HashMap<String , List<String>>();
        for(Entry<String, String[]> entry : params.entrySet()){
        	String key = entry.getKey() ;
        	if(senstive.contains(key.toLowerCase())){
        		temps.put(key, Arrays.asList("***")) ;
        	}else{
        		if(zip){
        			String [] values = entry.getValue() ;
        			List<String> ls = new LinkedList<String>() ;
        			for(String value : values){
        				if(value.length() > 8){
        					String vx = value.substring(0 , 4) + "***"  + value.substring(value.length() - 4 , value.length()) ;
        					ls.add(vx) ;
        				}else{
        					ls.add(value) ;
        				}
        			}
        			temps.put(key, ls) ;
        		}else{
        			temps.put(key, Arrays.asList(entry.getValue())) ;
        		}
        	}
        }
        return JSON.toJSONString(temps);
    }
    
    
    
    protected static String getParamsValueFilter(HttpServletRequest request , ValueFilter vf) {
        final Map<String, String[]> params = request.getParameterMap();
        String requestJson = JSON.toJSONString(params, vf ) ;
        return "##" +  StringUtils.defaultIfBlank(requestJson, "")  + "##" ;
    }
    
    
    

    @SuppressWarnings("unused")
	private static String getHeaders(HttpServletRequest request) {
    	Map<String, List<String>> headers = new HashMap<String , List<String>>() ;
        Enumeration<String> namesEnumeration = request.getHeaderNames();
        while(namesEnumeration.hasMoreElements()) {
            String name = namesEnumeration.nextElement();
            Enumeration<String> valueEnumeration = request.getHeaders(name);
            List<String> values = new ArrayList<String>();
            while(valueEnumeration.hasMoreElements()) {
                values.add(valueEnumeration.nextElement());
            }
            headers.put(name, values);
        }
        return JSON.toJSONString(headers);
    }

    
    public static Logger getAccessLog() {
        return ACCESS_LOG;
    }

    public static Logger getErrorLog() {
        return ERROR_LOG;
    }

}
