package com.jee.boot.config;


import java.util.concurrent.ExecutorService;


import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.support.CacheMessage;
import com.alicp.jetcache.support.CacheMessagePublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtendsCacheMessagePublisher implements CacheMessagePublisher {

	//@Autowired
	private ExecutorService executorService ;
	
	@Override
	public void publish(String area, String cacheName, CacheMessage cacheMessage) {
		executorService.submit(() -> {
			
			//log.info(">>>{}>>>>{}>>>>{}>>>>>" , area , cacheName , JSON.toJSONString(cacheMessage));
			
			
		}) ;
		
	}

}
