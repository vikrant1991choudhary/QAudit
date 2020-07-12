package com.sb.xlsql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuditFindingsController {
	//public static final String SUPPORTING_DOCS_LOC = "C:/QAudit/SupportingDocs/";
	
	
    @GetMapping("/bankAuditFindings")
    public String bankAudit() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/bankAuditFindings.html";
    } 
    @GetMapping("/fixedAssetauditFindings")
    public String fixedAssetaudit() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/fixedAssetAuditFindings.html";
    } 
    
    @GetMapping("/debtorsAssetAuditFindings")
    public String debtorsAssetAudit() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/debtorsAuditFindings.html";
    }
    
}
