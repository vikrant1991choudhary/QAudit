package com.sb.xlsql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuditAreaController {
	//public static final String SUPPORTING_DOCS_LOC = "C:/QAudit/SupportingDocs/";
	
	
    @GetMapping("/auditArea")
    public String index() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/auditArea.html";
    }    
    
    
}
