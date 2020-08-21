package com.jee.rest.base.response.facade;

public interface IBusinessResponse {
	
	/**
	 * 返回错误码
	 * @return
	 */
	public abstract int getCode() ; //错误码
	/**
	 * 错误信息
	 * @return
	 */
	public abstract String getMsg() ; //错误信息 
	/**
	 * 错误名
	 * @return
	 */
	public abstract String getName() ; // 英文错误标示

}
