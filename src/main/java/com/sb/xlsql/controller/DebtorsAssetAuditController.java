package com.sb.xlsql.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.sb.xlsql.model.CompanyAuditStatus;
import com.sb.xlsql.model.Debtors;
import com.sb.xlsql.model.DebtorsSupportingDocs;
import com.sb.xlsql.model.FixedAssetSupportingDocs;

@Controller
public class DebtorsAssetAuditController {
	//public static final String SUPPORTING_DOCS_LOC = "C:/QAudit/SupportingDocs/";
	@Autowired
	FileServices fileServices;
	
	
    @GetMapping("/debtorsAsset")
    public String index() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/debtorsAuditCheckpoint.html";
    }    
    
    @PostMapping("/uploadDebtorfile")
    public String uploadDebtorfile(@RequestParam("uploadDebtfile") MultipartFile file,Model model,@RequestParam("debtCompany") Long debtCompany,@RequestParam("debtYear") String debtYear,HttpServletRequest request) {
    	System.out.println("uploadTrialBalnfile Upload Controller debtYear="+debtYear+" debtCompany="+debtCompany);
    	long userid = (long)request.getSession().getAttribute("id");
		try {
			fileServices.uploadDebtorfile(file,debtCompany,debtYear,userid);
			model.addAttribute("message", "File uploaded successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
		}
        return "multipartfile/debtorsAuditCheckpoint.html";
    }
    
    @GetMapping("/getDebtorsFindings")
    public ResponseEntity<List<Debtors>> getDebtorsFindings(@RequestParam("companyid") String companyid){
    	List<Debtors> res = fileServices.getDebtorsFindings(companyid);
    	
    	return new ResponseEntity<List<Debtors>>(res,HttpStatus.OK);
    }
    
    @GetMapping("/getDebtorsFindingsCr")
    public ResponseEntity<List<Debtors>> getDebtorsFindingsCr(@RequestParam("companyid") String companyid){
    	List<Debtors> res = fileServices.getDebtorsFindingsCr(companyid);
    	
    	return new ResponseEntity<List<Debtors>>(res,HttpStatus.OK);
    }
    
    
    @PostMapping("/uploadDebtorSupportingfiles")
    public String uploadDebtorSupportingfiles(@RequestParam("supportingdocs") MultipartFile file, @RequestParam("comments") String comments, @RequestParam("particulars1")String particulars,HttpServletRequest req) {
    		System.out.println("Upload supporting file Debtors controller");
          try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
              File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes); 

           

             DebtorsSupportingDocs docs = new DebtorsSupportingDocs();
             docs.setFileDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
             docs.setParticulars(particulars);
             docs.setComments(comments);
             docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.saveDebtorsSupportingDocs(docs);

          } catch (IOException e) {
              e.printStackTrace();
          }
          
          return "multipartfile/debtorsAuditFindings.html";
    }
    
    @GetMapping(value="/getSupportingDebtorFileDetails")
   	public ResponseEntity<List<DebtorsSupportingDocs>> getSupportingFixedDetailsFileDetails(@RequestParam("particulars") String particulars){
   		System.out.println("getSupportingDebtorFileDetails");
   		List<DebtorsSupportingDocs> addTicker=fileServices.getSupportingDebtorFileDetails(particulars);
   		
   		return new ResponseEntity<List<DebtorsSupportingDocs>>(addTicker,HttpStatus.OK);	
   	}
    
    
    @PostMapping("/getDoneDebtorPaymentModal")
    public String getDoneDebtorPaymentModal(@RequestParam("debtorfile") MultipartFile file, @RequestParam("debtorComment") String debtorComment,HttpServletRequest req) {
    		System.out.println("Upload getDoneDebtorPaymentModal file Debtors controller");
          try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
             File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes); 

             CompanyAuditStatus docs = new CompanyAuditStatus();
             docs.setFilesDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
             docs.setIdCompany(1);
             docs.setIdStatus(5);
             docs.setIdSubStatus(6);
             docs.setComment(debtorComment);
             docs.setYear("2020");
            // docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.getDoneDebtorPaymentModal(docs);

          } catch (IOException e) {
              e.printStackTrace();
          }
          
          return "multipartfile/debtorsAuditFindings.html";
    }
    
}
