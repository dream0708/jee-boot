package com.jee.rest.base.http.exception;


public class HttpFailedException extends RuntimeException{
	
	private Object response ;
	
	private Object request ;
	
	private int code ;
	
	
	public HttpFailedException(){
		super() ;
		this.code = -1 ;
	}
	
	public HttpFailedException(int code){
		super() ;
		this.code = code ;
	}
	
	public HttpFailedException(String msg){
		super(msg) ;
		this.code = -1 ;
	}
	
	public HttpFailedException(int code , String msg){
		super(msg) ;
		this.code = code ;
	}
	
	public HttpFailedException(int code , String msg , Object response){
		super(msg) ;
		this.code = code ;
		this.response = response ;
	}
	
	
	public HttpFailedException(int code , String msg , Object response , Object request){
		super(msg) ;
		this.code = code ;
		this.response = response ;
		this.request = request ;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}
	
	
	

}
