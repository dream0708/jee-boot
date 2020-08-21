package com.jee.rest.base.exception;

import org.apache.commons.lang3.StringUtils;

import com.jee.rest.base.response.code.ResponseCode;
import com.jee.rest.base.response.facade.IBusinessResponse;
/**
 * 业务异常 
 * @author yaomengke
 *
 */
public class  BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = -1003683355305462875L;
    /** 错误码 */
	private IBusinessResponse errorCode ;
	
	/** 保留字段 其他信息 方便log处理等用途 **/
	private Object reserved ;
	
	public BusinessException(IBusinessResponse errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
	
    public BusinessException(IBusinessResponse errorCode, String message) {
    	super(StringUtils.isNotBlank(message) ? message : errorCode.getMsg());
        this.errorCode = errorCode;
    }
    
    public BusinessException(IBusinessResponse errorCode, String message , Object other) {
    	super(StringUtils.isNotBlank(message) ? message : errorCode.getMsg());
        this.errorCode = errorCode;
        this.reserved = other ;
    }
    /**
     * 一般化的错误 默认 code = 1
     * @param code
     * @param msg
     */
    public BusinessException(String msg){
    	super(msg) ;
    	this.errorCode = ResponseCode.FAILUER_1 ;
    }

   
	public Object getReserved() {
		return reserved;
	}

	public void setReserved(Object reserved) {
		this.reserved = reserved;
	}

	/**

     * 构造方法

     * @param message 错误消息

     * @param errorCode 错误码

     * @param ex {@link Throwable}

     */
    public BusinessException(IBusinessResponse errorCode, String message, Throwable ex) {
        super(message, ex);
        this.errorCode = errorCode;
    }

  
    public IBusinessResponse getErrorCode() {
        return errorCode;
    }

  
    public void setErrorCode(IBusinessResponse errorCode) {
        this.errorCode = errorCode;
    }

}
