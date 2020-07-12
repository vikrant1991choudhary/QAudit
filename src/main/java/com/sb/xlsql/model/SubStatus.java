package com.sb.xlsql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subStatus")
public class SubStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String subStatusName;
	private long idStatus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubStatusName() {
		return subStatusName;
	}
	public void setSubStatusName(String subStatusName) {
		this.subStatusName = subStatusName;
	}
	public long getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(long idStatus) {
		this.idStatus = idStatus;
	}
	
	

}
