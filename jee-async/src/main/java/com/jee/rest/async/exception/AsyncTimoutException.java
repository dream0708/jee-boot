package com.jee.rest.async.exception;

public class AsyncTimoutException extends RuntimeException {

	
	public AsyncTimoutException(){
		super() ;
	}
	
	public AsyncTimoutException(String message) {
        super( message );
    }
	
    
	
}
