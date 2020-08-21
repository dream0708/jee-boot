package com.jee.rest.async.global;

public class Three<A, B, C> {

	private A a;
	private B b;
	private C c;

	public Three() {

	}

	public Three(A a, B b, C c) {

		this.a = a;
		this.b = b;
		this.c = c;
	}

	public C getC() {
		return c;
	}

	public void setC(C c) {
		this.c = c;
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

	public void c(C c) {
		this.c = c;
	}

	public A a() {
		return a;
	}

	public B b() {
		return b;
	}

	public C c() {
		return c;
	}

}
