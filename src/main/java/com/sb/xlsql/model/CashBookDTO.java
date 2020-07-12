package com.sb.xlsql.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class CashBookDTO {
	
	private long id;
	
	
	private String date;
	
	
	private String txnType;
	
	
	private String particulars;
	
	
	private String descriptions;
	
	
	private String vchtype;
	
	
	private int vchno;
	
	
	private double debit;
	
	
	private double credit;
	
	private long count;
	
	public CashBookDTO() {
	}

	public CashBookDTO(long id, String date, String txnType, String particulars, String descriptions, String vchtype,
			int vchno, double debit, double credit) {
		super();
		this.id = id;
		this.date = date;
		this.txnType = txnType;
		this.particulars = particulars;
		this.descriptions = descriptions;
		this.vchtype = vchtype;
		this.vchno = vchno;
		this.debit = debit;
		this.credit = credit;
	}

	public String getTxnType() {
		return txnType;
	}

	
	

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

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

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getVchtype() {
		return vchtype;
	}

	public void setVchtype(String vchtype) {
		this.vchtype = vchtype;
	}

	public int getVchno() {
		return vchno;
	}

	public void setVchno(int vchno) {
		this.vchno = vchno;
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

	@Override
	public String toString() {
		return "CashBook [id=" + id + ", date=" + date + ", particulars=" + particulars + ", descriptions="
				+ descriptions + ", vchtype=" + vchtype + ", vchno=" + vchno + ", debit=" + debit + ", credit=" + credit
				+ "]";
	}
	
}
