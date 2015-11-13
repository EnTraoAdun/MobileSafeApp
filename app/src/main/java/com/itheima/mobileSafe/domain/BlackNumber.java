package com.itheima.mobileSafe.domain;

public class BlackNumber {
	private int id;
private String number;
private String method;

@Override
public String toString() {
	return "BlackNumber [id=" + id + ", number=" + number + ", method="
			+ method + "]";
}
public BlackNumber() {
	super();
	// TODO Auto-generated constructor stub
}
public BlackNumber(int id, String number, String method) {
	super();
	this.id = id;
	this.number = number;
	this.method = method;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = method;
}


}
