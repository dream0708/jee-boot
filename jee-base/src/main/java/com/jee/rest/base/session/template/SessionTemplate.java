package com.jee.rest.base.session.template;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

public interface SessionTemplate {
    
	
	public  boolean exists(String key) ;
	
	public  void set(String key , String value) ;
	
	//public <T>  void  setObject(String key , T value) ;
	public <T>  void  setObject(String key , T value , SerializerFeature... sf ) ;
	public  void setex(String key , String value , int expires) ;
	
	public  String get(String key) ;
	public  <T> T getObject(String key , Class<T> clazz) ;
	public  <T> T getObject(String key , TypeReference<T> type) ;
	public  <T> T getObject(String key , Type type) ;
	public  long increment(String key , int value) ;
	public  void expire(String key , int seconds) ;
	public  void expireAt(String key, Date date) ;
	
	public  void set(String key , Object value) ;
	public  void set(String key , Object value , int expire) ;
	public  void set(String key , String value , int expire) ;
	public  void setAttribute(String sessionid , String key , Object value) ;
	public  void setAttribute(String sessionid , String key , Object value , SerializerFeature... sf) ;
	public  void setAttribute(String sessionid , String key , String value) ;
	public  void setAttribute(String sessionid , Map<String , String> map) ;
	public  boolean setAttribute(String sessionid , String[] keys , Object[] objs ) ;
	
	public String getAttribute(String sessionid , String key )  ;
	public  long increment(String sessionid , String key , long value) ;
	
	public <T> T getAttribute(String sessionid , String key , Class<T> clazz) ;
	public <T> T getAttribute(String sessionid , String key , TypeReference<T> type)  ;
	
	public Map<Object , Object> getAll(String sessionid) ;
	
	public void delete(String key) ;
	public void delete(List<String> keys) ;
	
	public void removeAttribute(String sessionid , String... key) ;
		
   
    public Set<String> keys(String pattern) ;
    public Long ttl(String key);
    
    public String lpop(String key) ;
    public Long lpush(String key , String value) ;
    
    public String rpop(String key) ;
    public Long rpush(String key , String value) ;
    
    
    public Long sadd(String key , String ... members ) ;
    public String spop(String key) ;
  
    
    public Set<String> spop(String key , Long count) ;
    public Set<String> smembers(String key ) ;
    
}
