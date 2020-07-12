package com.sb.xlsql.model;

public class MaterialityReport {

	private long id;
	private String particulars;
	private double value;
	private String perofmat;
	private double newvalue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getPerofmat() {
		return perofmat;
	}
	public void setPerofmat(String perofmat) {
		this.perofmat = perofmat;
	}
	public double getNewvalue() {
		return newvalue;
	}
	public void setNewvalue(double newvalue) {
		this.newvalue = newvalue;
	}
	
	
	
}
