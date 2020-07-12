package com.sb.xlsql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sb.xlsql.fileservice.FileServices;
import com.sb.xlsql.model.MaterialityReport;

@Controller
public class MaterialityController {
	//public static final String SUPPORTING_DOCS_LOC = "C:/QAudit/SupportingDocs/";
	@Autowired
	FileServices fileServices;
	
    @GetMapping("/materiality")
    public String index() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/materiality.html";
    }  
    
    @PostMapping("/uploadTrialBalnfile")
    public String uploadTrialBalnfile(@RequestParam("uploadTrialBlnfile") MultipartFile file,Model model,@RequestParam("meterialityCompany") Long meterialityCompany,@RequestParam("meterialityYear") String meterialityYear) {
    	System.out.println("uploadTrialBalnfile Upload Controller meterialityYear="+meterialityYear+" meterialityCompany="+meterialityCompany);
		try {
			fileServices.uploadTrialBalnfile(file,meterialityCompany,meterialityYear);
			model.addAttribute("message", "File uploaded successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
		}
        return "multipartfile/materiality.html";
    }
    
    @GetMapping("/getMaterialityReport")
    public ResponseEntity<List<MaterialityReport>> getMaterialityReport(@RequestParam("companyId") String companyId){
    	List<MaterialityReport> res = fileServices.getMaterialityAudit(companyId);
    	
    	return new ResponseEntity<List<MaterialityReport>>(res,HttpStatus.OK);
    }
    
    
}
