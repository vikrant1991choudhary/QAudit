package com.sb.xlsql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sb.xlsql.fileservice.FileServices;
import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.Ledger;

@Controller
public class LedgerDetailsViewController {

	@Autowired
	FileServices fileServices;

	@GetMapping("/ledgerDetailsView")
	public String fileDetailsView() {
		return "multipartfile/ledgerAuditReport.html";
	}
	
	

	@GetMapping(value="/getDocumentChecked")
	public ResponseEntity<List<Ledger>> getDocumentChecked(){

		
		List<Ledger> datas=fileServices.findDoucmenttobeChecked();

		return new ResponseEntity<List<Ledger>>(datas,HttpStatus.OK);

	}
	
	
	
}
