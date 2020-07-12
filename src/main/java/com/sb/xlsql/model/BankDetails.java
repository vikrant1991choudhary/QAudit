package com.sb.xlsql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bankDetails")
public class BankDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String particulars;
	private double debit;	
	private double credit;
	private Long idCompany;
	private String financialyear;
	private String vchType;
	private String VchNo;
	private String mdate;
	private String blank1;
	private String blank2;
	private String txnType;
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
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public Long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	public String getFinancialyear() {
		return financialyear;
	}
	public void setFinancialyear(String financialyear) {
		this.financialyear = financialyear;
	}
	public String getVchType() {
		return vchType;
	}
	public void setVchType(String vchType) {
		this.vchType = vchType;
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
	
	
	public String getDate() {
		return mdate;
	}
	public void setDate(String date) {
		this.mdate = date;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getVchNo() {
		return VchNo;
	}
	public void setVchNo(String vchNo) {
		VchNo = vchNo;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
