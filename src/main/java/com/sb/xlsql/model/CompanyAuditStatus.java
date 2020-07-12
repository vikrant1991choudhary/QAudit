package com.sb.xlsql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companyAuditStatus")
public class CompanyAuditStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String year;
	private long idStatus;
	private long idSubStatus;
	private long idCompany;
	private String comment;
	private String filesDetails;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public long getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(long idStatus) {
		this.idStatus = idStatus;
	}
	public long getIdSubStatus() {
		return idSubStatus;
	}
	public void setIdSubStatus(long idSubStatus) {
		this.idSubStatus = idSubStatus;
	}
	public long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(long idCompany) {
		this.idCompany = idCompany;
	}
	public String getFilesDetails() {
		return filesDetails;
	}
	public void setFilesDetails(String filesDetails) {
		this.filesDetails = filesDetails;
	}
	
	
	

}
