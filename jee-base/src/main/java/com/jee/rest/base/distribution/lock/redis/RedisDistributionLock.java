package com.jee.rest.base.distribution.lock.redis;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.jee.rest.base.distribution.lock.DistributionLock;

public class RedisDistributionLock implements DistributionLock {

	// 加锁超时时间，单位毫秒， 即：加锁时间内执行完操作，如果未完成会有并发现象
	private static final long LOCK_TIMEOUT = 5 * 1000;

	private static final Logger logger = LoggerFactory.getLogger(RedisDistributionLock.class);

	private StringRedisTemplate redisTemplate;

	public RedisDistributionLock() {

	}

	public RedisDistributionLock(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public synchronized long lock(String lockKey, String threadName) {
		logger.info("...{} , {} 开始执行加锁...", lockKey, threadName);
		while (true) { // 循环获取锁

			// 锁时间
			Long timeout = currtDistributionTime() + LOCK_TIMEOUT + 1;
			if (redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
					// 定义序列化方式
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					byte[] value = serializer.serialize(timeout.toString());
					boolean flag = redisConnection.setNX(lockKey.getBytes(), value);
					return flag;
				}
			})) {
				// 如果加锁成功
				logger.info("...{} , {} 加锁成功, SETNX SUCCESS...", lockKey, threadName);
				// 设置超时时间，释放内存
				redisTemplate.expire(lockKey, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
				return timeout;
			} else {
				
				// 未获取锁，尝试重新获取
                // 此处使用double check 的思想，防止多线程同时竞争到锁
                // 1) 先获取上一个锁的过期时间，校验当前是否过期。
                // 2) 如果过期了，尝试使用getset方式获取锁。此处可能存在多个线程同时执行到的情况。
                // 3) getset更新过期时间，并且获取上一个锁的过期时间。
                // 4) 如果getset获取到的oldExpireAt 已过期，说明获取锁成功。
                //    如果和当前比未过期，说明已经有另一个线程提前获取到了锁
                //    这样也没问题，只是短暂的将上一个锁稍微延后一点时间（只有在A和B线程同时执行到getset时，才会出现，延长的时间很短）
				
				
				// 获取redis里面的时间
				String result = redisTemplate.opsForValue().get(lockKey);
				Long currentLockTimeout = (result == null ? null : Long.parseLong(result));
				// 锁已经失效
				if (currentLockTimeout != null && currentLockTimeout.longValue() < System.currentTimeMillis()) {
					// 判断是否为空，不为空时，说明已经失效，如果被其他线程设置了值，则第二个条件判断无法执行
					// 获取上一个锁到期时间，并设置现在的锁到期时间
					Long oldLockTimeout = Long
							.valueOf(redisTemplate.opsForValue().getAndSet(lockKey, timeout.toString()));
					if (oldLockTimeout != null && oldLockTimeout.longValue() == currentLockTimeout.longValue()) {
						// 多线程运行时，多个线程签好都到了这里，但只有一个线程的设置值和当前值相同，它才有权利获取锁
						logger.info("...{} , {} 加锁成功, OLD LOCKTIME equals CURRENT LOCKTIME...", lockKey, threadName);
						// 设置超时间，释放内存
						redisTemplate.expire(lockKey, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
						// 返回加锁时间
						return timeout;
					}
				}
			}

			try {
				logger.info("...{} ,  {}等待加锁， 睡眠100毫秒...", lockKey, threadName);
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				logger.error("...SLEEP 200 EXCEPTION...");
			}
		}
	}

	@Override
	public synchronized void unlock(String lockKey, long lockValue, String threadName) {
		logger.info("...{} , {}执行解锁...", lockKey, threadName); // 正常直接删除
																// 如果异常关闭判断加锁会判断过期时间
		String result = redisTemplate.opsForValue().get(lockKey); // 获取redis中设置的时间
		Long currLockTimeout = (result == null) ? null : Long.valueOf(result);
		// 如果是加锁者，则删除锁， 如果不是，则等待自动过期，重新竞争加锁
		if (currLockTimeout != null && currLockTimeout.longValue() == lockValue) {
			redisTemplate.delete(lockKey);
			logger.info("...{} ,  {}解锁成功...", lockKey, threadName);
		}

	}

	@Override
	public long currtDistributionTime() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection.time();
			}
		});
	}

	public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}