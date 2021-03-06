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
import com.sb.xlsql.model.FixedAsset;
import com.sb.xlsql.model.FixedAssetSupportingDocs;
import com.sb.xlsql.model.SupportingDocs;

@Controller
public class InvestmentsAuditController {
	//public static final String SUPPORTING_DOCS_LOC ="C:/Confitech/QAudit/Report/";                    //"C:/QAudit/SupportingDocs/";
	
	@Autowired
	FileServices fileServices;
	
	
    @GetMapping("/investment")
    public String index() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/investmentsAuditCheckpoint.html";
    } 
    
//    @PostMapping("/uploadFixedAssetfile")
//    public String uploadFixedAssetfile(@RequestParam("uploadFixedfile") MultipartFile file,Model model,@RequestParam("fixedCompany") Long fixedCompany,@RequestParam("fixedYear") String fixedYear,HttpServletRequest request) {
//    	System.out.println("uploadFixedAssetfile Upload Controller fixedYear="+fixedYear+" fixedCompany="+fixedCompany);
//    	long userid = (long)request.getSession().getAttribute("id");
//
//		try {
//			fileServices.uploadFixedAssetfile(file,fixedCompany,fixedYear,userid);
//			model.addAttribute("message", "Fixed Asset File uploaded successfully!");
//		} catch (Exception e) {
//			e.printStackTrace();
//			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
//		}
//        return "multipartfile/fixedAssetAuditCheckpoint.html";
//    }
//    
//    @GetMapping("/getFixedAssetAudition")
//    public ResponseEntity<List<FixedAsset>> getFixedAssetAudition(@RequestParam("companyid") String companyId){
//    	List<FixedAsset> res = fileServices.getFixedAssetAudition(companyId);
//    	
//    	return new ResponseEntity<List<FixedAsset>>(res,HttpStatus.OK);
//    }
//    
//    @PostMapping("/uploadFixedSupportingfiles")
//    public String uploadSupportingfiles(@RequestParam("supportingdocs") MultipartFile file, @RequestParam("comments") String comments, @RequestParam("particulars1")String particulars,HttpServletRequest req) {
//    		System.out.println("Upload supporting file Fixed controller");
//          try {
//
//              // Get the file and save it somewhere
//              byte[] bytes = file.getBytes();
//              Path path = Paths.get(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
//             java.nio.file.Files.write(path, bytes);
//
//             FixedAssetSupportingDocs docs = new FixedAssetSupportingDocs();
//             docs.setFileDetails(path.toString());
//             docs.setParticulars(particulars);
//             docs.setComments(comments);
//             docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
//				
//             fileServices.saveFixedSupportingDocs(docs);
//
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
//          
//          return "multipartfile/fixedAssetAuditFindings.html";
//    }
//    
//    @GetMapping(value="/getSupportingFixedDetailsFileDetails")
//   	public ResponseEntity<List<FixedAssetSupportingDocs>> getSupportingFixedDetailsFileDetails(@RequestParam("particulars") String particulars){
//   		System.out.println("getSupportingFixedDetailsFileDetails");
//   		List<FixedAssetSupportingDocs> addTicker=fileServices.getSupportingFixedDetailsFileDetails(particulars);
//   		
//   		return new ResponseEntity<List<FixedAssetSupportingDocs>>(addTicker,HttpStatus.OK);	
//   	}
//    
//    
//    @PostMapping("/getDoneFixedSupportingBill")
//    public String getDoneFixedSupportingBill(@RequestParam("fixeddonefile") MultipartFile file, @RequestParam("fixedComment") String fixedComment,HttpServletRequest req) {
//    		System.out.println("Upload getDoneFixedSupportingBill file Fixed controller");
//    		try {
//
//    			// Get the file and save it somewhere
//    			byte[] bytes = file.getBytes();
//    			File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
//    			OutputStream os = new FileOutputStream(file1);
//    			os.write(bytes); 
//
//				/*
//				 * Path path = Paths.get(ConstantsIF.SUPPORTING_DOCS_LOC +
//				 * file.getOriginalFilename()); System.out.println(path);
//				 * java.nio.file.Files.write(path, bytes);
//				 */
//
//    			CompanyAuditStatus docs = new CompanyAuditStatus();
//    			docs.setFilesDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
//    			docs.setIdCompany(1);
//    			docs.setIdStatus(2);
//    			docs.setIdSubStatus(4);
//    			docs.setComment(fixedComment);
//    			docs.setYear("2020");
//    			// docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
//
//    			fileServices.getDoneFixedSupportingBill(docs);
//
//    		} catch (IOException e) {
//              e.printStackTrace();
//          }
//          
//          return "multipartfile/fixedAssetAuditFindings.html";
//    }
    
}
