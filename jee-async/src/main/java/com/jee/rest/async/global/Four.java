package com.jee.rest.async.global;

public class Four<A, B, C, D> {

	private A a;
	private B b;
	private C c;
	private D d;

	public Four() {

	}

	public Four(A a, B b, C c, D d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
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

	public C getC() {
		return c;
	}

	public void setC(C c) {
		this.c = c;
	}

	public D getD() {
		return d;
	}

	public void setD(D d) {
		this.d = d;
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

	public void d(D d) {
		this.d = d;
	}

	public D d() {
		return d;
	}

}
