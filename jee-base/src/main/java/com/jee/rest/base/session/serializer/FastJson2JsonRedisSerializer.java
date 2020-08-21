package com.jee.rest.base.session.serializer;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	private static Logger logger = LoggerFactory.getLogger(FastJson2JsonRedisSerializer.class) ;

	private Class<T> clazz;
	
	public FastJson2JsonRedisSerializer(){
		
	}

	public FastJson2JsonRedisSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		if(logger.isDebugEnabled()){
			logger.debug("fast json serialize, class : {} , string:{} " , t.getClass() , 
					JSON.toJSONString(t, SerializerFeature.WriteClassName));
		}
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}

	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, DEFAULT_CHARSET);
		if(logger.isDebugEnabled()){
			logger.debug("fast json deserialize, class : {} , string:{} " , clazz.getName() ,
					str );
		}
		
		return (T) JSON.parseObject(str, clazz);
	}
}
