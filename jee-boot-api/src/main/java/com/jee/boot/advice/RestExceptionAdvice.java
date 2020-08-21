package com.jee.boot.advice;

import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.alibaba.fastjson.JSONException;
import com.jee.rest.base.exception.BusinessException;
import com.jee.rest.base.response.biz.BizResponse;
import com.jee.rest.base.response.code.ResponseCode;

@RestControllerAdvice
@Order
public class RestExceptionAdvice {

	private static final Logger logger = LogManager.getLogger(RestExceptionAdvice.class);

	public BizResponse handleException(Throwable ex, HttpServletRequest request) throws Throwable {
		if (ex instanceof BusinessException) {
			BusinessException be = (BusinessException) ex;
			logger.error("BusinessException , Url : {} , ErrorCode : {} , Message : {} ", request.getRequestURL(),
					be.getErrorCode().getCode(), be.getMessage());
			if (be.getReserved() != null) {
				return BizResponse.fail(be);
			}
			return BizResponse.fail(be);
		} else if (ex instanceof JSONException) {
			logger.error(ex.getMessage(), ex);
			logger.error("JSONException , Url : {} , ErrorCode : {} , Message : {} ", request.getRequestURL(),
					ResponseCode.JSON_FORMAT_ERROR_671.getCode(), "数据格式转换异常");
			return BizResponse.fail(ResponseCode.JSON_FORMAT_ERROR_671, "数据格式转换异常");
		} else if (ex instanceof RedisConnectionFailureException) {
			logger.error(ex.getMessage(), ex);
			logger.error("RedisConnectionFailureException , Url : {} , ErrorCode : {} , Message : {} ",
					request.getRequestURL(), ResponseCode.REQUEST_TIMEOUT_408.getCode(), "会话服务器连接失败");
			return BizResponse.fail(ResponseCode.REQUEST_TIMEOUT_408, "会话服务器连接失败");
		} else if (ex instanceof AsyncRequestTimeoutException) {
			logger.error(ex.getMessage(), ex);
			logger.error("AsyncRequestTimeoutException , Url : {} , ErrorCode : {} , Message : {} ",
					request.getRequestURL(), ResponseCode.DEFERREDRESULT_TIMEOUT_503.getCode(), "HTTP ASYNC 异步请求超时！");
			return BizResponse.fail(ResponseCode.SERVER_REJECT_512.getCode(), "请求超时！");
		} else if (ex instanceof HttpMessageNotReadableException) {
			logger.error(ex.getMessage(), ex);
			logger.error("HttpMessageNotReadableException , Url : {} , ErrorCode : {} , Message : {} ",
					request.getRequestURL(), ResponseCode.REQUEST_TIMEOUT_408.getCode(), "请求入参格式有误,请按照接口文档组织参数");
			return BizResponse.fail(ResponseCode.JSON_FORMAT_ERROR_671.getCode(), "请求入参格式有误,请按照接口文档组织参数");
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			logger.error(ex.getMessage(), ex);
			logger.error("HttpRequestMethodNotSupportedException , Url : {} , ErrorCode : {} , Message : {} ",
					request.getRequestURL(), ResponseCode.SERVER_REJECT_512.getCode(),
					"请求方式[GET,POST,PUT,DELETE]非法,请参考接口文档");
			return BizResponse.fail(ResponseCode.SERVER_REJECT_512.getCode(), "请求方式[GET,POST,PUT,DELETE]非法,请参考接口文档");
		} else if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException cex = (ConstraintViolationException) ex;
			Set<ConstraintViolation<?>> sets = cex.getConstraintViolations();
			ConstraintViolation<?> error = sets.stream().findFirst().get();
			Path path = error.getPropertyPath();
			String keyName = "";
			if (path != null && path instanceof PathImpl) {
				keyName = ((PathImpl) path).getLeafNode().getName();
			} else {
				keyName = path.toString();
			}
			String errMsg = StringUtils.defaultIfBlank(error.getMessage(), "入参错误") + "[" + keyName + "]";
			return BizResponse.fail(ResponseCode.ILLEGAL_PARAM_411, errMsg);
		} else if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
			FieldError error = c.getBindingResult().getFieldError();
			String errMsg = StringUtils.defaultIfBlank(error.getDefaultMessage(), "入参错误") + "[" + error.getField()
					+ "]";
			logger.error("MethodArgumentNotValidException , Url : {} , ErrorCode : {} , Message : {} ",
					request.getServletPath(), ResponseCode.ILLEGAL_PARAM_411.getCode(), errMsg);
			return BizResponse.fail(ResponseCode.ILLEGAL_PARAM_411, errMsg);
		} else if (ex instanceof BindException) {
			BindException c = (BindException) ex;
			FieldError error = c.getBindingResult().getFieldError();
			String errMsg = StringUtils.defaultIfBlank(error.getDefaultMessage(), "入参错误") + "[" + error.getField()
					+ "]";
			logger.error("BindException , Url : {} , ErrorCode : {} , Message : {} ", request.getServletPath(),
					ResponseCode.ILLEGAL_PARAM_411.getCode(), errMsg);
			return BizResponse.fail(ResponseCode.ILLEGAL_PARAM_411, errMsg);
		} else if (ex instanceof MissingServletRequestParameterException) {
			logger.error(ex.getMessage(), ex);
			MissingServletRequestParameterException me = (MissingServletRequestParameterException) ex;
			logger.error("{} , Url : {} , ErrorCode : {} , Message : {} ", ex.getClass().getSimpleName(),
					request.getServletPath(), ResponseCode.ILLEGAL_PARAM_411, "缺少参数[" + me.getParameterName() + "]");
			return BizResponse.fail(ResponseCode.ILLEGAL_PARAM_411, "缺少参数[" + me.getParameterName() + "]");
		} else {
			logger.error(ex.getMessage(), ex);
			logger.error("{} , Url : {} , ErrorCode : {} , Message : {} ", ex.getClass().getSimpleName(),
					request.getRequestURL(), ResponseCode.SYSTEM_ERROR_504.getCode(), ex.getMessage());
			return BizResponse.fail(ResponseCode.SYSTEM_ERROR_504, "服务器异常，请稍后重试");
		}

	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public BizResponse requestHandlingNoHandlerFound(HttpServletRequest request, NoHandlerFoundException ex) {
		logger.error("NoHandlerFoundException , Url : {} , ErrorCode : {} , Message : {} ", request.getRequestURL(),
				ResponseCode.NOT_FOUND_404.getCode(), ResponseCode.NOT_FOUND_404.getMsg());
		return BizResponse.fail(ResponseCode.NOT_FOUND_404);
	}

	// @ExceptionHandler(ClientAbortException.class)
	public void handleClientAbortException(HttpServletRequest request, Exception ex) {
		logger.error("管道破裂 异常 (Broken Pipe)  org.apache.catalina.connector.ClientAbortException: java.io.IOException ");
		logger.error("ClientAbortException:{}, URL:{}, Message:{}", ex.getClass().getSimpleName(),
				request.getRequestURI(), ex.getMessage());
	}

	@ExceptionHandler({ ExecutionException.class })
	public BizResponse hanldeExecutionException(ExecutionException ex, HttpServletRequest request) throws Throwable {
		logger.debug(ex.getMessage(), ex);
		return handleException(ex.getCause(), request);
	}

	@ExceptionHandler({ CompletionException.class })
	public BizResponse hanldeCompletionException(CompletionException ex, HttpServletRequest request) throws Throwable {
		logger.debug(ex.getMessage(), ex);
		return handleException(ex.getCause(), request);
	}

	@ExceptionHandler({ Throwable.class, RuntimeException.class, Exception.class })
	public BizResponse handleException(HttpServletRequest request, Exception ex) throws Throwable {
		return handleException(ex, request);
	}

}
