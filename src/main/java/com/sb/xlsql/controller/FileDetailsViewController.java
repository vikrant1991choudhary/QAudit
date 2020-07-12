package com.sb.xlsql.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sb.xlsql.fileservice.FileServices;
import com.sb.xlsql.model.AuditCompany;
import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.CashBookDTO;
import com.sb.xlsql.model.DailyCashDTO;
import com.sb.xlsql.model.SubStatusDTO;

@Controller
public class FileDetailsViewController {

	@Autowired
	FileServices fileServices;

	@GetMapping("/auditDetailsView")
	public String fileDetailsView() {
		return "multipartfile/details.html";
	}
	
	@GetMapping("/auditReport")
	public String auditReport() {
		return "multipartfile/auditReport.html";
	}
	
	@GetMapping("/auditDataHistory")
	public String auditDataHistory() {
		return "multipartfile/auditDataHistory.html";
	}
	@GetMapping("/bankAuditDataHistory")
	public String bankAuditDataHistory() {
		return "multipartfile/bankAuditDataHistory.html";
	}
	@GetMapping("/debtorsAuditDataHistory")
	public String debtorsAuditDataHistory() {
		return "multipartfile/debtorsAuditDataHistory.html";
	}
	@GetMapping("/fixedAssetAuditDataHistory")
	public String fixedAssetAuditDataHistory() {
		return "multipartfile/fixedAssetAuditDataHistory.html";
	}

	@GetMapping(value="/getCashBookDetails")
	public ResponseEntity<List<CashBook>> getCashBookDetails(Long idCompany){

		//System.out.println("getCashBookDetails");
		List<CashBook> addTicker=fileServices.getCashBookDetails(idCompany);

		return new ResponseEntity<List<CashBook>>(addTicker,HttpStatus.OK);

	}
	@GetMapping(value="/getCashBookReport")
	public ResponseEntity<List<CashBook>> getCashBookReport(Long idCompany){

		//System.out.println("getCashBookDetails");
		List<CashBook> addTicker=fileServices.getCashBookReport(idCompany);

		return new ResponseEntity<List<CashBook>>(addTicker,HttpStatus.OK);

	}
	
	@GetMapping(value="/getMultipleCashBookPayment")
	public ResponseEntity<List<CashBookDTO>> getMultipleCashBookPayment(Long idCompany){
		
		System.out.println("getMultipleCashBookPayment");
		List<CashBookDTO> addTicker=fileServices.getMultipleCashBookPayment(idCompany);
		
		return new ResponseEntity<List<CashBookDTO>>(addTicker,HttpStatus.OK);
		
	}
	
	@GetMapping(value="/getEverydayCashPayment")
	public ResponseEntity<List<DailyCashDTO>> getEverydayCashPayment(Long idCompany){
		
		System.out.println("getEverydayCashPayment");
		List<DailyCashDTO> addTicker=fileServices.getEverydayCashPayment(idCompany);
		
		return new ResponseEntity<List<DailyCashDTO>>(addTicker,HttpStatus.OK);
		
	}
	
	@GetMapping("/auditStatus")
	public String auditStatus() {
		return "multipartfile/auditStatus.html";
	}

	@PostMapping("/getAuditStatus")
	public ResponseEntity<List<SubStatusDTO>> getAuditStatus(@RequestParam("auditYear") String auditYear, @RequestParam("auditCompany") String auditCompany,HttpSession session){
		
		  session.setAttribute("companyid", auditCompany);
		  session.setAttribute("audityear", auditYear);
		 
		return new ResponseEntity<List<SubStatusDTO>>(fileServices.getAuditStatus(auditYear,auditCompany),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/getAuditCompany")
	public ResponseEntity<AuditCompany> getAuditCompany(HttpSession session){
		AuditCompany audit = new AuditCompany();
		if(session.getAttribute("companyid")!=null) {
			audit.setAuditCompany(session.getAttribute("companyid").toString());
			audit.setAudityear(session.getAttribute("audityear").toString());
		}	
		
		return new ResponseEntity<AuditCompany>(audit,HttpStatus.OK);
	}
	
	@GetMapping("/getAbovePaycount")
	public ResponseEntity<String> getAbovePaycount(@RequestParam("companyId")String companyid){
		String res = fileServices.getCountMorethenPayment(companyid);
		
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
}
