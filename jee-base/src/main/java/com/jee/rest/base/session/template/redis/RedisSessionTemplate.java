package com.jee.rest.base.session.template.redis;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jee.rest.base.session.template.SessionTemplate;

public class RedisSessionTemplate implements SessionTemplate {
	
	
	private RedisTemplate<String , String> redisTemplate ;
	
	@Override
	public boolean exists(String key) {
		return redisTemplate.hasKey(key) ;
	}

	@Override
	public void set(String key, String value) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public <T> void setObject(String key, T value, SerializerFeature... sf) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForValue().
		        set(key, JSON.toJSONString(value , sf));
	}

	@Override
	public void setex(String key, String value, int expires) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForValue().set(key,
				value, expires, TimeUnit.SECONDS);
	}

	@Override
	public String get(String key) {
		return redisTemplate.opsForValue().get(key) ;
	}

	@Override
	public <T> T getObject(String key, Class<T> clazz) {
		String value = redisTemplate.opsForValue().get(key) ; 
		return JSON.parseObject(value , clazz) ;
	}

	@Override
	public <T> T getObject(String key, TypeReference<T> type) {
		String value = redisTemplate.opsForValue().get(key) ; 
		return JSON.parseObject(value , type) ;
	}

	@Override
	public <T> T getObject(String key, Type type) {
		String value = redisTemplate.opsForValue().get(key) ; 
		return JSON.parseObject(value , type) ;
	}

	@Override
	public void expire(String key, int seconds) {
		redisTemplate.expire(key, seconds, TimeUnit.SECONDS) ;
	}

	@Override
	public void expireAt(String key, Date date) {
		redisTemplate.expireAt(key, date) ;
	}
	
	@Override
	public void set(String key, Object value) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForValue().set(key, JSON.toJSONString(value)); 
	}

	@Override
	public void set(String key, Object value, int expire) {
		if(null == value){
			return ;
		}
		
		redisTemplate.opsForValue().set(key, 
				JSON.toJSONString(value), expire, TimeUnit.SECONDS);
	}

	@Override
	public void set(String key, String value, int expire) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForValue().set(key, 
				value  , expire, TimeUnit.SECONDS);
	}
	
	public  long increment(String key , int value) {
		return redisTemplate.opsForValue().increment(key, value) ;
	}

	@Override
	public void setAttribute(String sessionid, String key, Object value) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForHash().put(sessionid, key, 
				JSON.toJSONString(value));
	}

	@Override
	public void setAttribute(String sessionid, String key, Object value , SerializerFeature ... sf) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForHash().put(sessionid, key, 
				JSON.toJSONString(value , sf));
	}
	
	@Override
	public void setAttribute(String sessionid, String key, String value) {
		if(null == value){
			return ;
		}
		redisTemplate.opsForHash().put(sessionid, key, value);
	}


	@Override
	public void setAttribute(String sessionid, Map<String, String> map) {
		redisTemplate.opsForHash().putAll(sessionid , map) ;
	}
	@Override
	public  boolean setAttribute(String sessionid , String[] keys , Object[] objs ) {
		Map<String , String> values = new HashMap<String , String>() ;
		if(keys.length != objs.length){
			return false ;
		}
		for(int i = 0 ; i < keys.length  ; i ++){
			if(objs[i] == null){
				continue ;
			}
			if(objs[i] instanceof String){
				values.put(keys[i], (String)objs[i] ) ;
			}else{
				values.put(keys[i], JSON.toJSONString(objs[i] , SerializerFeature.WriteClassName) ) ;
			}
			
		}
		redisTemplate.opsForHash().putAll(sessionid, values);
		return true ;
	}

	@Override
	public String getAttribute(String sessionid, String key) {
		return (String)redisTemplate.opsForHash().get(sessionid, key) ;
	}

	@Override
	public <T> T getAttribute(String sessionid, String key, Class<T> clazz) {
		String value = (String)redisTemplate.opsForHash().get(sessionid, key) ;
		return JSON.parseObject(value , clazz) ;
	}

	@Override
	public <T> T getAttribute(String sessionid, String key, TypeReference<T> type) {
		String value = (String)redisTemplate.opsForHash().get(sessionid, key) ;
		return JSON.parseObject(value , type) ;
	}
	
	public  long increment(String sessionid , String key , long value) {
		return redisTemplate.opsForHash().increment(sessionid, key, value) ;
	}
	

	@Override
	public Map<Object, Object> getAll(String sessionid) {
		return redisTemplate.opsForHash().entries(sessionid) ;
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	@Override
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	@Override
	public void removeAttribute(String sessionid, String... key) {
		redisTemplate.boundHashOps(sessionid).delete((Object[]) key ) ;
	}

	//@Override
	public void removeAttribute(String sessionid, String  key) {
		redisTemplate.boundHashOps(sessionid).delete((String)key) ;
	}


	@Override
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern) ;
	}

	@Override
	public Long ttl(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS) ;
	}

	@Override
	public String lpop(String key) {
		return redisTemplate.boundListOps(key).leftPop() ;
	}

	@Override
	public Long lpush(String key, String value) {
		return redisTemplate.boundListOps(key).leftPush(value) ;
	}

	@Override
	public String rpop(String key) {
		return redisTemplate.boundListOps(key).rightPop() ;
	}

	@Override
	public Long rpush(String key, String value) {
		return redisTemplate.boundListOps(key).rightPush(value) ;
	}

	@Override
	public Long sadd(String key, String... members) {
		return redisTemplate.boundSetOps(key).add(members) ;
	}

	@Override
	public String spop(String key) {
		return redisTemplate.boundSetOps(key).pop() ;
	}

	@Override
	public Set<String> spop(String key, Long count) {
		return redisTemplate.boundSetOps(key).distinctRandomMembers(count) ;
	}

	@Override
	public Set<String> smembers(String key) {
		return redisTemplate.boundSetOps(key).members() ;
	}


	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	
}
