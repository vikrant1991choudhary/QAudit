package com.sb.xlsql.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cashbook")

public class CashBook {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "Date")
	private String date;
	
	@Column(name="txnType")
	private String txnType;
	
	@Column(name = "Particulars")
	private String particulars;
	
	@Column(name = "Descriptions")
	private String descriptions;
	
	@Column(name = "VchType")
	private String vchtype;
	
	@Column(name = "VchNo")
	private int vchno;
	
	@Column(name = "Debit")
	private double debit;
	
	@Column(name = "Credit")
	private double credit;
	
	private long companyId;
	
	private String financialyear;
	
	
	
	public String getFinancialyear() {
		return financialyear;
	}

	public void setFinancialyear(String financialyear) {
		this.financialyear = financialyear;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public CashBook() {
	}

	public CashBook(long id, String date, String txnType, String particulars, String descriptions, String vchtype,
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
