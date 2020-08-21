package com.jee.rest.async.global;

public class One<A> {
	private A a ;
	public One(){
		
	}
	
    public One(A a ){
		this.a = a;
	} 
	
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}
	public void a(A a) {
	    this.a = a;
	}
    public A a() {
	     return a;
	}

	  


}
