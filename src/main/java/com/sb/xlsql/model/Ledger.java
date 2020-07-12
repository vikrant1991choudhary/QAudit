package com.sb.xlsql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ledger")
public class Ledger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String date;
	private String particulars;
	private String vchtype;
	private String vchno;
	private String debit;
	private String credit;
	private String txnType;
	private String blank1;
	private String blank2;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getVchtype() {
		return vchtype;
	}
	public void setVchtype(String vchtype) {
		this.vchtype = vchtype;
	}
	
	
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getBlank1() {
		return blank1;
	}
	public void setBlank1(String blank1) {
		this.blank1 = blank1;
	}
	public String getBlank2() {
		return blank2;
	}
	public void setBlank2(String blank2) {
		this.blank2 = blank2;
	}
	public String getVchno() {
		return vchno;
	}
	public void setVchno(String vchno) {
		this.vchno = vchno;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	
	
	
	
	
	



}
