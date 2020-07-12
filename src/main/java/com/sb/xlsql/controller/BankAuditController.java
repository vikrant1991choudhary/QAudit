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
import com.sb.xlsql.model.BankAuditFindings;
import com.sb.xlsql.model.BankSupportingDocs;
import com.sb.xlsql.model.CompanyAuditStatus;
import com.sb.xlsql.model.FixedAssetSupportingDocs;

@Controller
public class BankAuditController {
	//public static final String SUPPORTING_DOCS_LOC ="C:/Confitech/QAudit/Report/";                    //"C:/QAudit/SupportingDocs/";
	
	@Autowired
	FileServices fileServices;
	
    @GetMapping("/bankAudit")
    public String index() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/bankAuditCheckpoint.html";
    } 
    
    @PostMapping("/uploadBankfile")
    public String uploadBankfile(@RequestParam("uploadBankfile") MultipartFile file,Model model,@RequestParam("bankCompany") Long bankCompany,@RequestParam("bankYear") String bankYear, HttpServletRequest request) {
    	System.out.println("uploadBankfile Upload Controller bankCompany="+bankCompany+" bankYear="+bankYear);
    	long userid = (long)request.getSession().getAttribute("id");
		try {
			fileServices.uploadBankfile(file,bankCompany,bankYear,userid);
			model.addAttribute("message", "Bank File uploaded successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
		}
        return "multipartfile/bankAuditCheckpoint.html";
    }
    
    @GetMapping("/getBankAuditFindings")
    public ResponseEntity<List<BankAuditFindings>> getBankAuditFindings(String companyId){
    	List<BankAuditFindings> res = fileServices.getBankAuditFindings(companyId);
    	
    	return new ResponseEntity<List<BankAuditFindings>>(res,HttpStatus.OK);
    }
    
    @PostMapping("/uploadBankSupportingfiles")
    public String uploadSupportingfiles(@RequestParam("supportingdocs") MultipartFile file, @RequestParam("comments") String comments, @RequestParam("bankBalance1")String bankBalance,HttpServletRequest req) {
    		System.out.println("Upload supporting file Bank controller");
          try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
              File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes); 

             BankSupportingDocs docs = new BankSupportingDocs();
             docs.setFileDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
             docs.setBankBalance(bankBalance);
             docs.setComments(comments);
             docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.saveBankSupportingDocs(docs);

          } catch (IOException e) {
              e.printStackTrace();
          }
          
          return "multipartfile/bankAuditFindings.html";
    }
    
    @GetMapping(value="/getSupportingBankFileDetails")
   	public ResponseEntity<List<BankSupportingDocs>> getSupportingBankFileDetails(@RequestParam("bankBalance") String bankBalance){
   		System.out.println("getSupportingFixedDetailsFileDetails");
   		List<BankSupportingDocs> addTicker=fileServices.getSupportingBankFileDetails(bankBalance);
   		
   		return new ResponseEntity<List<BankSupportingDocs>>(addTicker,HttpStatus.OK);	
   	}
    
    @PostMapping("/getDoneBankVerify")
    public String getDoneBankVerify(@RequestParam("bankpayfile") MultipartFile file, @RequestParam("bankComment") String fixedComment,HttpServletRequest req) {
    		System.out.println("Upload getDoneBankVerify file Fixed controller");
          try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
              File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes); 

             CompanyAuditStatus docs = new CompanyAuditStatus();
             docs.setFilesDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
             docs.setIdCompany(1);
             docs.setIdStatus(3);
             docs.setIdSubStatus(5);
             docs.setComment(fixedComment);
             docs.setYear("2020");
            // docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.getDoneBankVerify(docs);

          } catch (IOException e) {
              e.printStackTrace();
          }
          
          return "multipartfile/bankAuditFindings.html";
    }
    
}
