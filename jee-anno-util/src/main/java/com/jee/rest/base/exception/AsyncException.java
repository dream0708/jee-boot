package com.jee.rest.base.exception;

public class AsyncException extends RuntimeException{

	
	private int code ;
	
	private Object data  ;

	public AsyncException(){
		super();
	}
	public AsyncException(String message){
		super(message);
	}
	public AsyncException(int code , String message){
		super(message);
		this.code = code ;
	}
	
	public AsyncException(int code , String message , Object data){
		this(code , message) ;
		this.data = data ;
	}
	public AsyncException(int code , Object data){
		this(code , "DEFAULT ASYNC EXCEPTION") ;
		this.data = data ;
	}
	
	public AsyncException(Object data){
		this(0 , "DEFAULT ASYNC EXCEPTION") ;
		this.data = data ;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}
