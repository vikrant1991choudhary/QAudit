package com.sb.xlsql.controller;


import java.io.FileOutputStream;

import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.sb.xlsql.fileservice.FileServices;
import com.sb.xlsql.model.CompanyAuditStatus;
import com.sb.xlsql.model.Files;
import com.sb.xlsql.model.ResponsePDfDTO;
import com.sb.xlsql.model.SupportingDocs;

@Controller
public class UploadFileController {
	public static final String SUPPORTING_DOCS_LOC = "C:/QAudit/SupportingDocs/";
	@Autowired
	FileServices fileServices;
	
	
	
    @GetMapping("/uploadfile")
    public String index() {
        //return "multipartfile/uploadform.html";
    	return "multipartfile/fileupload.html";
    }
    
    @PostMapping("/uploadfile")
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, @RequestParam("clientCompany") Long clientCompany,@RequestParam("cashYear") String cashYear, Model model, HttpServletRequest request) {
    	System.out.println("clientCompany="+clientCompany);
    	long userid = (long)request.getSession().getAttribute("id");
    	request.getSession().setAttribute("clientid",clientCompany);
		try {
			fileServices.store(file,userid,clientCompany,cashYear);
			model.addAttribute("message", "File uploaded successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
		}
        return "multipartfile/auditArea.html";
    }
    
    @PostMapping(value="/getDoneCashPayment")
   	public String getDoneCashPayment(@RequestParam("cashpayabovefile") MultipartFile file,@RequestParam("comment") String comment, HttpServletRequest request){
   		System.out.println("getFileDetails");
   		
   		try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
             File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes); 

             CompanyAuditStatus docs = new CompanyAuditStatus();
             docs.setFilesDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
              docs.setIdCompany(1);
              docs.setIdStatus(1);
              docs.setIdSubStatus(1);
              docs.setComment(comment);
              docs.setYear("2020");
            // docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.getDoneDebtorPaymentModal(docs);

          } catch (IOException e) {
              e.printStackTrace();
			  
          }
				return "multipartfile/auditReport.html";
   	}
    @PostMapping(value="/getDoneMultiplePayment")
   	public String getDoneMultiplePayment(@RequestParam("cashMultiFile") MultipartFile file, @RequestParam("comment") String comment, HttpServletRequest request){
   		System.out.println("getDoneMultiplePayment");
   		//Long idcompany= (Long)request.getSession().getAttribute("clientid");
   		//System.out.println("idcompany="+idcompany);

		try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
             File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes); 

             CompanyAuditStatus docs = new CompanyAuditStatus();
             docs.setFilesDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
              docs.setIdCompany(1);
              docs.setIdStatus(1);
              docs.setIdSubStatus(2);
              docs.setComment(comment);
              docs.setYear("2020");
            // docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.getDoneDebtorPaymentModal(docs);

          } catch (IOException e) {
              e.printStackTrace();
			  
          }
				return "multipartfile/auditReport.html";

   		//CompanyAuditStatus addTicker=fileServices.getDoneMultiplePayment(idStatus,idSubStatus,year,idcompany,comment);
   		
   		//return new ResponseEntity<CompanyAuditStatus>(addTicker,HttpStatus.OK);	
   	}
    @PostMapping(value="/getDoneCashHand")
   	public String getDoneCashHand(@RequestParam("cashinhandfile") MultipartFile file, 
		@RequestParam("comment") String comment, HttpServletRequest request){
   		System.out.println("getDoneCashHand");
   		try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
             File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes); 

             CompanyAuditStatus docs = new CompanyAuditStatus();
             docs.setFilesDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
              docs.setIdCompany(1);
              docs.setIdStatus(1);
              docs.setIdSubStatus(3);
              docs.setComment(comment);
              docs.setYear("2020");
            // docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.getDoneDebtorPaymentModal(docs);

          } catch (IOException e) {
              e.printStackTrace();
			  
          }
				return "multipartfile/auditReport.html";
   	}
    
    @GetMapping(value="/getFileDetails")
   	public ResponseEntity<List<Files>> getFileDetails(Long idCompany){
   		System.out.println("getFileDetails");
   		List<Files> addTicker=fileServices.getFileDetails(idCompany);
   		
   		return new ResponseEntity<List<Files>>(addTicker,HttpStatus.OK);	
   	}
    
    @GetMapping(value="/generatePDF")
    public ResponseEntity<ResponsePDfDTO> generatePDF(HttpServletRequest request){
    	System.out.println("generatePDF-----");

    	long idCompany = (long)request.getSession().getAttribute("clientid");
    	String fileName = fileServices.generatePDF(idCompany);
    	ResponsePDfDTO dto = new ResponsePDfDTO();
    	//dto.setResponseStr("http://jharvis.com:9093/downloadPDF?fileName="+fileName);
    	String downloadPDF = ConstantsIF.downloadPDF + fileName;
    	dto.setResponseStr(downloadPDF);
    	//dto.setResponseStr("http://localhost:9093/downloadPDF?fileName="+fileName);
    	return new ResponseEntity<ResponsePDfDTO>(dto,HttpStatus.OK);	 
    }

	@GetMapping(value="/generatePDF1")
    public ResponseEntity<ResponsePDfDTO> generatePDF1(HttpServletRequest request){
    	System.out.println("generatePDF1-----");
    	long idCompany = (long)request.getSession().getAttribute("clientid");

    	String fileName = fileServices.generatePDF1(idCompany);
    	ResponsePDfDTO dto = new ResponsePDfDTO();
    //	dto.setResponseStr("http://jharvis.com:9093/downloadPDF?fileName="+fileName);
    	String downloadPDF = ConstantsIF.downloadPDF + fileName;
    	dto.setResponseStr(downloadPDF);
    	return new ResponseEntity<ResponsePDfDTO>(dto,HttpStatus.OK);	 
    }
    
    @GetMapping(value="/downloadPDF")
   	public void downloadPDF(HttpServletRequest request, HttpServletResponse response){
   		System.out.println("downloadPDF-----");
   		
   		try {
   			String fileName = request.getParameter("fileName");
   			System.out.println("Download="+fileName);
   			File file = new File(fileName);
   			
   			String mimeType = "application/octet-stream";
			
			response.setContentType(mimeType);			
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));			 
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.getOutputStream().flush();
			
			/*
			 * response.setContentType("application/pdf"); PrintWriter out =
			 * response.getWriter(); response.setContentType("APPLICATION/OCTET-STREAM");
			 * response.setHeader("Content-Disposition","attachment; filename=\"" + fileName
			 * + "\"");
			 * 
			 * FileInputStream fileInputStream = new FileInputStream(fileName);
			 * 
			 * int i; while ((i=fileInputStream.read()) != -1) { out.write(i); }
			 * fileInputStream.close(); out.close();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} 
   	}
    
    @GetMapping(value="/download")
   	public void download(HttpServletRequest request, HttpServletResponse response){
   		System.out.println("download-----");
   		
   		try {
   			String filepath = request.getParameter("filepath");
   			System.out.println(filepath);
   			// create object of Path 
   	        Path path = Paths.get(filepath); 
   	  
   	        // call getFileName() and get FileName path object 
   	        Path fileName = path.getFileName(); 
   			System.out.println("Download="+fileName.toString());
   			File file = new File(fileName.toString());
   			
   			String mimeType = "application/octet-stream";
			
			response.setContentType(mimeType);			
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));			 
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.getOutputStream().flush();
			
			/*
			 * response.setContentType("application/pdf"); PrintWriter out =
			 * response.getWriter(); response.setContentType("APPLICATION/OCTET-STREAM");
			 * response.setHeader("Content-Disposition","attachment; filename=\"" + fileName
			 * + "\"");
			 * 
			 * FileInputStream fileInputStream = new FileInputStream(fileName);
			 * 
			 * int i; while ((i=fileInputStream.read()) != -1) { out.write(i); }
			 * fileInputStream.close(); out.close();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} 
   	}
    

    
    @PostMapping("/uploadSupportingfiles")
    public String uploadSupportingfiles(@RequestParam("supportingdocs") MultipartFile file, @RequestParam("comments") String comments, @RequestParam("vccno")String vccno,HttpServletRequest req) {
    		System.out.println("Upload supporting file controller");
          try {

              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
               File file1 = new File(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
    			OutputStream os = new FileOutputStream(file1);
    			os.write(bytes);


             SupportingDocs docs = new SupportingDocs();
             docs.setFileDetails(ConstantsIF.SUPPORTING_DOCS_LOC + file.getOriginalFilename());
             docs.setVoucherNo(vccno);
             docs.setComments(comments);
             docs.setCreatedBy(req.getSession(false).getAttribute("id").toString());
				
             fileServices.saveSupportingDocs(docs);

          } catch (IOException e) {
              e.printStackTrace();
          }
          
          return "multipartfile/auditReport.html";
    }
    
    @GetMapping(value="/getSupportingFileDetails")
   	public ResponseEntity<List<SupportingDocs>> getSupportingFileDetails(@RequestParam("vchno") String vchno){
   		System.out.println("getFileDetails");
   		List<SupportingDocs> addTicker=fileServices.getSupportingFileDetails(vchno);
   		
   		return new ResponseEntity<List<SupportingDocs>>(addTicker,HttpStatus.OK);	
   	}
    
    @GetMapping("/getAssetWithoutBilCount")
    public ResponseEntity<String> getAssetWithoutBilCount(@RequestParam("companyid") String companyid){
    	String val = fileServices.getAssetWithoutBilCount(companyid);
    	
    	return new ResponseEntity<String>(val,HttpStatus.OK);
    }
    
    
}
