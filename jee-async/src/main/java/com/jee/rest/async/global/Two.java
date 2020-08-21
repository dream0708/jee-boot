package com.jee.rest.async.global;

public class Two<A, B> {

	private A a;
	private B b;

	public Two() {

	}

	public Two(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

	public void a(A a) {
		this.a = a;
	}

	public void b(B b) {
		this.b = b;
	}

	public A a() {
		return a;
	}

	public B b() {
		return b;
	}

}
