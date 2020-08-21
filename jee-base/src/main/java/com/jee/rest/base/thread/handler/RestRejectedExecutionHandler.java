package com.jee.rest.base.thread.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jee.rest.base.exception.BusinessException;
import com.jee.rest.base.response.code.ResponseCode;
/**
 * 线程被拒绝策略
 * @author yaomengke
 *
 */
public class RestRejectedExecutionHandler implements RejectedExecutionHandler  {

	private static Logger logger = LoggerFactory.getLogger(RestRejectedExecutionHandler.class);
	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor pool) {
		logger.error( runnable + " : has been rejected !");
		logger.error("the pool is shutting .... : " + pool.isTerminating()) ;
		logger.error("the pool is shutted .... : " + pool.isTerminated());
		throw new BusinessException(ResponseCode.SERVER_REJECT_512, "服务器暂时拒绝访问!") ;
	}

}
