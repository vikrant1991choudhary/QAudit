package com.sb.xlsql.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "bankSupportingDocs")
public class BankSupportingDocs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String bankBalance;
	
	private String fileDetails;
	
	private String comments;
	
	@CreationTimestamp
	private Date createdAt;
	
	private String createdBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBankBalance() {
		return bankBalance;
	}

	public void setBankBalance(String bankBalance) {
		this.bankBalance = bankBalance;
	}

	public String getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(String fileDetails) {
		this.fileDetails = fileDetails;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	


}
