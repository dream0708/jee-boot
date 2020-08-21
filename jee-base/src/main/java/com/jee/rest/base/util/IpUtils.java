package com.jee.rest.base.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

public class IpUtils {
    
	

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null ;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        return ip;
    }
    
    public static String getIpAddr(NativeWebRequest request) {
        if (request == null) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getNativeRequest(HttpServletRequest.class).getRemoteAddr();
        }
        return ip;
    }
    
    
    public static String getRealIp(HttpServletRequest request , String defaultIp){
    	try {
    		String ip = getIpAddr(request) ;
        	if(StringUtils.isNotBlank(ip) && ip.contains(",")){
        		ip = ip.split(",")[0] ;
        	}
        	return ip ;
    	}catch (Exception e) {
			return defaultIp ;
		}
    	
    }
    public static String getRealIp(HttpServletRequest request) {
    	return  getRealIp( request , "unkown-ip");
    }
    
    
    public static String getRealIp(NativeWebRequest request , String defaultIp){
    	try {
    		String ip = getIpAddr(request) ;
        	if(StringUtils.isNotBlank(ip) && ip.contains(",")){
        		ip = ip.split(",")[0] ;
        	}
        	return ip ;
    	}catch (Exception e) {
    		return defaultIp ;
		}
    	
    }
    
    public static String getRealIp(NativeWebRequest request) {
    	return  getRealIp( request , "unkown-ip");
    }
}
