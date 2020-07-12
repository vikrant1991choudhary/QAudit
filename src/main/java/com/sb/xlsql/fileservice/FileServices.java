package com.sb.xlsql.fileservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sb.xlsql.model.BankAuditFindings;
import com.sb.xlsql.model.BankDetails;
import com.sb.xlsql.model.BankSupportingDocs;
import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.CashBookDTO;
import com.sb.xlsql.model.CompanyAuditStatus;
import com.sb.xlsql.model.DailyCashDTO;
import com.sb.xlsql.model.Debtors;
import com.sb.xlsql.model.DebtorsSupportingDocs;
import com.sb.xlsql.model.Files;
import com.sb.xlsql.model.FixedAsset;
import com.sb.xlsql.model.FixedAssetSupportingDocs;
import com.sb.xlsql.model.Ledger;
import com.sb.xlsql.model.MaterialityReport;
import com.sb.xlsql.model.SubStatusDTO;
import com.sb.xlsql.model.SupportingDocs;
import com.sb.xlsql.model.TrialBalance;
import com.sb.xlsql.repository.BankDetailsRepository;
import com.sb.xlsql.repository.BankSupportingDocsRepository;
import com.sb.xlsql.repository.CashBookRepository;
import com.sb.xlsql.repository.CompanyAuditStatusRepository;
import com.sb.xlsql.repository.DebtorsRepository;
import com.sb.xlsql.repository.DebtorsSupportingDocsRepository;
import com.sb.xlsql.repository.FileRepository;
import com.sb.xlsql.repository.FixedAssetRepository;
import com.sb.xlsql.repository.FixedAssetSupportingDocsRepository;
import com.sb.xlsql.repository.LedgerRepository;
import com.sb.xlsql.repository.SupportingDocsRepository;
import com.sb.xlsql.repository.TrialBalanceRepository;
import com.sb.xlsql.util.BankDetailsExcelUtils;
import com.sb.xlsql.util.CashBookReport;
import com.sb.xlsql.util.DebtExcelUtils;
import com.sb.xlsql.util.ExcelUtils;
import com.sb.xlsql.util.FixedAssetExcelUtils;
import com.sb.xlsql.util.TrialExcelUtils;

@Service
public class FileServices {
	@Autowired
	CashBookRepository cbRepository;

	
	@Autowired
	LedgerRepository lRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	SupportingDocsRepository supportingdocs;
	
	@Autowired
	CompanyAuditStatusRepository companyAuditStatusRepository;
	
	@Autowired
	TrialBalanceRepository trialBalanceRepository;
	
	@Autowired
	DebtorsRepository debtorsRepository;
	
	@Autowired
	FixedAssetRepository fixedAssetRepository;
	
	@Autowired
	BankDetailsRepository bankDetailsRepository;
	
	@Autowired
	FixedAssetSupportingDocsRepository fixedAssetSupportingDocsRepository;
	
	@Autowired
	BankSupportingDocsRepository bankSupportingDocsRepository;
	
	@Autowired
	DebtorsSupportingDocsRepository debtorsSupportingDocsRepository;
	
	@Autowired
	EntityManager em;
	
	

	//Store File Data to Database
	@Transactional(rollbackOn = {Exception.class})
	public void store(MultipartFile file,long userid,Long clientCompany,String cashYear){
		/*
		 * try { List<CashBook> lstCB =
		 * ExcelUtils.parseExcelFile(file.getInputStream(),clientCompany); // Save
		 * Customers to DataBase cbRepository.saveAll(lstCB);
		 * 
		 * Files files = new Files();
		 * 
		 * files.setFileName(file.getOriginalFilename());
		 * files.setFileType("Cash Book"); files.setUploadedBy( userid);
		 * files.setIdClient(clientCompany); files.setFinancialYear(cashYear);
		 * files.setDate(Calendar.getInstance().getTime());
		 * 
		 * fileRepository.save(files); } catch (Exception e) { // throw new
		 * RuntimeException("FAIL! -> message = " + e.getMessage());
		 * e.printStackTrace(); }
		 */
		
		try {

			String qryStr ="select count(*) datacount from [dbo].[cashbook] where financialyear ="+cashYear+"and company_id ="+clientCompany +";";
			Query query = em.createNativeQuery(qryStr);
			System.out.println("uploadCash ="+query);
			List<Object[]> audits = (List<Object[]>)query.getResultList();
			System.out.println("Size="+audits.size());
			if(audits.size()>0) {
				cbRepository.deleteAll();
				List<CashBook> lstCB = ExcelUtils.parseExcelFile(file.getInputStream(),clientCompany);
				// Save Customers to DataBase
				cbRepository.saveAll(lstCB);
			}else {
				List<CashBook> lstCB = ExcelUtils.parseExcelFile(file.getInputStream(),clientCompany);
				// Save Customers to DataBase
				cbRepository.saveAll(lstCB);
			}		
			

			Files files = new Files();

			files.setFileName(file.getOriginalFilename());
			files.setFileType("Cash Book");
			files.setUploadedBy( userid);
			files.setIdClient(clientCompany);
			files.setFinancialYear(cashYear);
			files.setDate(Calendar.getInstance().getTime());

			fileRepository.save(files);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Load Data to Excel File
	public ByteArrayInputStream loadFile() {
		List<CashBook> cashbook = (List<CashBook>) cbRepository.findAll();

		try {
			ByteArrayInputStream in = ExcelUtils.cashbookToExcel(cashbook);
			return in;
		} catch (IOException e) {}

		return null;
	}

	public List<Files> getFileDetails(Long idCompany) {

		return fileRepository.findAllFilesData(idCompany); 

	}

	public List<CashBook> getCashBookDetails(Long idCompany) {

		return cbRepository.findAllCashBookData(idCompany); 

	}
	public List<CashBook> getCashBookReport(Long idCompany) {

		return cbRepository.findAllCashBookReport(idCompany); 

	}

	
	/**
	 * @return
	 */
	public List<CashBookDTO> getMultipleCashBookPayment(Long idCompany) {

		/**
		 * Query working only MySQL
		 * */
		//Query query = em.createNativeQuery("select id,particulars,count(id),sum(credit) from cashbook where credit>0 group by particulars having count(id) >1");
		
		/**
		 * Query working only SQLServer
		 * */
		
		Query query = em.createNativeQuery("select C.particulars,count(id) as nooftimes,sum(credit) as totalcredit from cashbook AS C where credit>0 and company_id="+idCompany+"  group by C.particulars having count(id) >1");
		@SuppressWarnings("unchecked")
		List<Object[]> audits = query.getResultList();
		List<CashBookDTO> dtos = new ArrayList<>();
		for(Object[] audit:audits) {
			CashBookDTO dto = new CashBookDTO();

			/*
			 * dto.setId(((BigInteger)audit[0]).longValue());
			 * dto.setParticulars((String)audit[1]);
			 * dto.setCount(((BigInteger)audit[2]).longValue());
			 * dto.setCredit((Double)audit[3]);
			 */
			
			
			  
			  dto.setParticulars((String)audit[0]);
			  dto.setCount((Integer)audit[1]);
			  dto.setCredit((Double)audit[2]);
			 
			
			
			

			dtos.add(dto);
		}
		return dtos;
		
		//return cbRepository.getMultipleCashBookPayment(); 

	}
	
	public List<DailyCashDTO> getEverydayCashPayment(Long idCompany) {
		String openingBal = "15767.00"; 
				
		/**
		 * Query working only MySQL
		 * */
		/*
		 * String
		 * qryStr="select date_format(str_to_date(date,'%a %b %d 00:00:00 IST %Y'),'%d/%m/%Y') date, "
		 * + "(SUM(debit) - SUM(credit)) as cashbalance from cashbook group by date  " +
		 * "order by id ;";
		 */
		
		/**
		 * Query working only SQLServer
		 * */
		String qryStr = "select CONVERT(varchar,date,101) as mdate, (SUM(debit) - SUM(credit)) as cashbalance from cashbook where company_id="+idCompany+ "group by date order by min(id)";
		
		System.out.println("qryStr==== "+qryStr);

		Query query = em.createNativeQuery(qryStr);

		
		  @SuppressWarnings("unchecked") 
		  List<Object[]>  audits = query.getResultList();
		  List<DailyCashDTO> dtos = new ArrayList<>();  
		  int i = 0;
		  
		  for(Object[] audit:audits) {
			  DailyCashDTO dto = new DailyCashDTO();
			
			  
			  dto.setDate((String)audit[0]);
			  Double dbcash=(Double)audit[1];
			  Double actualOpeningBal=Double.parseDouble(openingBal) +dbcash;
			  dto.setDailyCash(String.valueOf(actualOpeningBal));
			  openingBal=String.valueOf(actualOpeningBal);
		  
		  
		  dtos.add(dto); 
		  
		  
		  }
		//Collections.re
		 
		return dtos;
		
		//return cbRepository.getMultipleCashBookPayment(); 

	}
	
	public CompanyAuditStatus getDoneCashPayment(Long idStatus,Long idSubStatus,String year,Long idcompany, String comment) {

		CompanyAuditStatus companyAuditStatus=new CompanyAuditStatus();
		companyAuditStatus.setYear(year);
		companyAuditStatus.setIdStatus(idStatus);
		companyAuditStatus.setIdSubStatus(idSubStatus);
		companyAuditStatus.setIdCompany(idcompany);
		companyAuditStatus.setComment(comment);
		//companyAuditStatus.setFilesDetails(file);
		return companyAuditStatusRepository.save(companyAuditStatus); 

	}
	public CompanyAuditStatus getDoneCashHand(Long idStatus,Long idSubStatus,String year,Long idcompany,String comment) {

		CompanyAuditStatus companyAuditStatus=new CompanyAuditStatus();
		companyAuditStatus.setYear(year);
		companyAuditStatus.setIdStatus(idStatus);
		companyAuditStatus.setIdSubStatus(idSubStatus);
		companyAuditStatus.setIdCompany(idcompany);
		companyAuditStatus.setComment(comment);
		//companyAuditStatus.setFilesDetails(file);
		return companyAuditStatusRepository.save(companyAuditStatus); 

	}
	public CompanyAuditStatus getDoneMultiplePayment(Long idStatus,Long idSubStatus,String year,Long idcompany, String comment) {

		CompanyAuditStatus companyAuditStatus=new CompanyAuditStatus();
		companyAuditStatus.setYear(year);
		companyAuditStatus.setIdStatus(idStatus);
		companyAuditStatus.setIdSubStatus(idSubStatus);
		companyAuditStatus.setIdCompany(idcompany);
		companyAuditStatus.setComment(comment);
		//companyAuditStatus.setFilesDetails(file);
		return companyAuditStatusRepository.save(companyAuditStatus); 

	}
	
	public List<SubStatusDTO> getAuditStatus(String year, String cmpny){
		
	//	Query query = em.createNativeQuery("select ss.sub_status_name,s.comment from company_audit_status s,sub_status ss WHERE s.id_company = 1 AND s.year='2020' AND s.id_sub_status=ss.id;");

		Query query = em.createNativeQuery("select ss.sub_status_name,s.comment,s.files_details from company_audit_status s,sub_status ss WHERE s.id_company = "+cmpny+" AND s.year='"+year+"' AND s.id_sub_status=ss.id;");
		System.out.println("select ss.sub_status_name,s.comment from company_audit_status s,sub_status ss WHERE s.id_company = "+cmpny+" AND s.year='"+year+"' AND s.id_sub_status=ss.id;");
		@SuppressWarnings("unchecked")
		List<Object[]> audits = (List<Object[]>)query.getResultList();
		List<SubStatusDTO> dtos = new ArrayList<>();
		for(Object[] audit:audits) {
			SubStatusDTO dto = new SubStatusDTO();
			dto.setSub_status_name((String)audit[0]);
			dto.setComment((String)audit[1]);
			dto.setFileDetails((String)audit[2]);
			dtos.add(dto);
		}
		return dtos;
	}
	
	
	public String generatePDF(Long idCompany) {
		List<CashBookDTO> multi =	getMultipleCashBookPayment(idCompany); 
		List<CashBook> cash=getCashBookReport(idCompany);
		List<DailyCashDTO> everyday=getEverydayCashPayment(idCompany); 
		return	CashBookReport.createPDF(cash, multi,everyday);

	}

	public String generatePDF1(Long idCompany) {
		List<CashBookDTO> multi = getMultipleCashBookPayment(idCompany); 
		List<CashBook> cash=getCashBookReport(idCompany);
			List<DailyCashDTO> everyday=getEverydayCashPayment(idCompany); 
			return	CashBookReport.createPDF1(cash, multi,everyday);

	}
	 
	
	public List<Ledger> findDoucmenttobeChecked(){
		return lRepository.findDoucmenttobeChecked();
	}
	
	
	@Transactional(rollbackOn = {Exception.class})
	public void saveSupportingDocs(SupportingDocs docs){
		System.out.println("Upload supporting file Service");
		try {
			/*
			 * List<CashBook> lstCB = ExcelUtils.parseExcelFile(file.getInputStream()); //
			 * Save Customers to DataBase cbRepository.saveAll(lstCB);
			 * 
			 * Files files = new Files();
			 * 
			 * files.setFileName(file.getOriginalFilename());
			 * files.setFileType("Cash Book"); files.setUploadedBy( userid);
			 * files.setIdClient(clientCompany);
			 * files.setDate(Calendar.getInstance().getTime());
			 */
			supportingdocs.save(docs);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<SupportingDocs> getSupportingFileDetails(String vchno) {

		return supportingdocs.findAllSupportingFilesData(vchno);

	}
	
	@Transactional(rollbackOn = {Exception.class})
	public void uploadTrialBalnfile(MultipartFile file,Long meterialityCompany,String meterialityYear ){
		/*
		 * try {
		 * 
		 * List<TrialBalance> lstCB =
		 * TrialExcelUtils.parseExcelFile(file.getInputStream(),meterialityCompany,
		 * meterialityYear); // Save Customers to DataBase
		 * trialBalanceRepository.saveAll(lstCB);
		 * 
		 * 
		 * } catch (Exception e) { // throw new RuntimeException("FAIL! -> message = " +
		 * e.getMessage()); e.printStackTrace(); }
		 */
		try {
			String qryStr ="select count(*) datacount from [dbo].[trial_balance] where financialyear ="+meterialityYear+"and id_company ="+meterialityCompany +";";
			Query query = em.createNativeQuery(qryStr);
			System.out.println("uploadTrialBalnfile ="+query);
			List<Object[]> audits = (List<Object[]>)query.getResultList();
			//System.out.println("Size="+audits.size());
			int dtos = 0;
			for(Object audit:audits) {
				dtos = (Integer)audit;
			}
			System.out.println("Size="+dtos);
			if(dtos>0) {
				trialBalanceRepository.deleteAll();
				List<TrialBalance> lstCB = TrialExcelUtils.parseExcelFile(file.getInputStream(),meterialityCompany,meterialityYear);
				// Save Customers to DataBase
				trialBalanceRepository.saveAll(lstCB);
			}else {
				List<TrialBalance> lstCB = TrialExcelUtils.parseExcelFile(file.getInputStream(),meterialityCompany,meterialityYear);
				// Save Customers to DataBase
				trialBalanceRepository.saveAll(lstCB);
			}

			
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<MaterialityReport> getMaterialityAudit(String compId){
		String matialityCal = "select id,CASE WHEN particulars = 'Profit & Loss A/c' THEN 'Net Profit' WHEN particulars = 'Fixed Assets' "
				+ "THEN 'Total assets' WHEN particulars = 'Indirect Expenses' "
				+ "THEN 'Total Expenses'	WHEN particulars = 'Direct Incomes' "
				+ "THEN 'Revenues' END 'particulars',CASE WHEN credit = 0 "
				+ "then debit WHEN debit = 0 then credit END value, CASE WHEN particulars = 'Profit & Loss A/c' THEN '10%' WHEN particulars = 'Fixed Assets'"
				+ " THEN '5%' WHEN particulars = 'Indirect Expenses' THEN '3%'	WHEN particulars = 'Direct Incomes' THEN '2%' END 'per_of_mat' "
				+ "from trial_balance where particulars in ('Fixed Assets','Direct Incomes','Indirect Expenses','Profit & Loss A/c') and id_company = "+compId;
		
		Query query = em.createNativeQuery(matialityCal);
		System.out.println(matialityCal);
		@SuppressWarnings("unchecked")
		List<Object[]> audits = (List<Object[]>)query.getResultList();
		List<MaterialityReport> dtos = new ArrayList<>();
		for(Object[] audit:audits) {
			MaterialityReport dto = new MaterialityReport();
			dto.setId(((BigInteger)audit[0]).longValue());
			dto.setParticulars((String)audit[1]);
			dto.setValue((Double)audit[2]);
			dto.setPerofmat((String)audit[3]);
			
			double newval = ((Double)audit[2])* (Double.parseDouble(String.valueOf(((String)audit[3]).charAt(0)))/100);
			dto.setNewvalue(newval);
			System.out.println(newval);
			dtos.add(dto);
		}
		return dtos;
	}
	
	
	@Transactional(rollbackOn = {Exception.class})
	public void uploadDebtorfile(MultipartFile file,Long debtCompany,String debtYear,Long userid ){
		/*
		 * try { List<Debtors> lstCB =
		 * DebtExcelUtils.parseExcelFile(file.getInputStream(),debtCompany,debtYear); //
		 * Save Customers to DataBase debtorsRepository.saveAll(lstCB);
		 * 
		 * Files files = new Files();
		 * 
		 * files.setFileName(file.getOriginalFilename()); files.setFileType("Debtors");
		 * files.setUploadedBy( userid); files.setIdClient(debtCompany);
		 * files.setFinancialYear(debtYear);
		 * files.setDate(Calendar.getInstance().getTime());
		 * 
		 * fileRepository.save(files);
		 * 
		 * } catch (Exception e) { // throw new RuntimeException("FAIL! -> message = " +
		 * e.getMessage()); e.printStackTrace(); }
		 */
		
		try {
			

			String qryStr ="select count(*) datacount from [dbo].[debtors] where financialyear ="+debtYear+"and id_company ="+debtCompany +";";
			Query query = em.createNativeQuery(qryStr);
			System.out.println("uploadCash ="+query);
			List<Object[]> audits = (List<Object[]>)query.getResultList();
			System.out.println("Size="+audits.size());
			if(audits.size()>0) {
				debtorsRepository.deleteAll();
				List<Debtors> lstCB = DebtExcelUtils.parseExcelFile(file.getInputStream(),debtCompany,debtYear);
				// Save Customers to DataBase
				debtorsRepository.saveAll(lstCB);
			}else {
				List<Debtors> lstCB = DebtExcelUtils.parseExcelFile(file.getInputStream(),debtCompany,debtYear);
				// Save Customers to DataBase
				debtorsRepository.saveAll(lstCB);
			}	
			
			
			
			
			Files files = new Files();

			files.setFileName(file.getOriginalFilename());
			files.setFileType("Debtors");
			files.setUploadedBy( userid);
			files.setIdClient(debtCompany);
			files.setFinancialYear(debtYear);
			files.setDate(Calendar.getInstance().getTime());

			fileRepository.save(files);
			
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Debtors> getDebtorsFindings(String companyId) {
		
		String matialityCal = "select particulars,opening_balance,closing_balance,debit,credit from debtors where id_company= "+companyId+" group by particulars,opening_balance,closing_balance,debit,credit,financialyear  having count(particulars)>1  order by financialyear desc";
		
		Query query = em.createNativeQuery(matialityCal);
		System.out.println(matialityCal);
		@SuppressWarnings("unchecked")
		List<Object[]> audits = (List<Object[]>)query.getResultList();
		List<Debtors> dtos = new ArrayList<>();
		for(Object[] audit:audits) {
			Debtors dto = new Debtors();
			dto.setParticulars((String)audit[0]);
			dto.setOpeningBalance((String)audit[1]);
			dto.setClosingBalance((String)audit[2]);
			dto.setDebit((Double)audit[3]);
			dto.setCredit((Double)audit[4]);
			dtos.add(dto);
		}
		return dtos;
		
	}
	
	public List<Debtors> getDebtorsFindingsCr(String companyId) {
		
		String matialityCal = "Select particulars,closing_balance from debtors where closing_balance LIKE '%Cr' and id_company="+companyId;
		
		Query query = em.createNativeQuery(matialityCal);
		System.out.println(matialityCal);
		@SuppressWarnings("unchecked")
		List<Object[]> audits = (List<Object[]>)query.getResultList();
		List<Debtors> dtos = new ArrayList<>();
		for(Object[] audit:audits) {
			Debtors dto = new Debtors();
			dto.setParticulars((String)audit[0]);
			
			dto.setClosingBalance((String)audit[1]);
			
			dtos.add(dto);
		}
		return dtos;
		
	}
	
	
	@Transactional(rollbackOn = {Exception.class}) 
	public void	uploadFixedAssetfile(MultipartFile file,Long fixedCompany,String fixedYear,Long userid ){ 
		/*
		 * try { List<FixedAsset> lstCB =
		 * FixedAssetExcelUtils.parseExcelFile(file.getInputStream(),fixedCompany,
		 * fixedYear); // Save Customers to DataBase
		 * fixedAssetRepository.saveAll(lstCB);
		 * 
		 * Files files = new Files();
		 * 
		 * files.setFileName(file.getOriginalFilename());
		 * files.setFileType("Fixed Asset"); files.setUploadedBy( userid);
		 * files.setIdClient(fixedCompany); files.setFinancialYear(fixedYear);
		 * files.setDate(Calendar.getInstance().getTime());
		 * 
		 * fileRepository.save(files);
		 * 
		 * 
		 * } catch (Exception e) { // throw new RuntimeException("FAIL! -> message = " +
		 * //e.getMessage()); e.printStackTrace(); }
		 */
		
		try {

			String qryStr ="select count(*) datacount from [dbo].[fixed_asset] where financialyear ="+fixedYear+"and id_company ="+fixedCompany +";";
			Query query = em.createNativeQuery(qryStr);
			System.out.println("uploadCash ="+query);
			List<Object[]> audits = (List<Object[]>)query.getResultList();
			System.out.println("Size="+audits.size());
			if(audits.size()>0) {
				fixedAssetRepository.deleteAll();
				List<FixedAsset> lstCB = FixedAssetExcelUtils.parseExcelFile(file.getInputStream(),fixedCompany,fixedYear); // Save Customers to DataBase
				fixedAssetRepository.saveAll(lstCB);
			}else {
				List<FixedAsset> lstCB = FixedAssetExcelUtils.parseExcelFile(file.getInputStream(),fixedCompany,fixedYear); // Save Customers to DataBase
				fixedAssetRepository.saveAll(lstCB);
			}	
			
		
		
		Files files = new Files();

		files.setFileName(file.getOriginalFilename());
		files.setFileType("Fixed Asset");
		files.setUploadedBy( userid);
		files.setIdClient(fixedCompany);
		files.setFinancialYear(fixedYear);
		files.setDate(Calendar.getInstance().getTime());

		fileRepository.save(files);


	} catch (Exception e) { // throw new RuntimeException("FAIL! -> message = " +
		//e.getMessage());
		e.printStackTrace(); 
		} 
	}
	
	public List<FixedAsset> getFixedAssetAudition(String companyId){
		return fixedAssetRepository.getFixedAssetAudition(companyId);
	}
	
	@Transactional(rollbackOn = {Exception.class}) 
	public void	uploadBankfile(MultipartFile file,Long bankCompany,String bankYear,Long userid ){ 
		/*
		 * try { List<BankDetails> lstCB
		 * =BankDetailsExcelUtils.parseExcelFile(file.getInputStream(),bankCompany,
		 * bankYear); // Save Customers to DataBase
		 * bankDetailsRepository.saveAll(lstCB); Files files = new Files();
		 * 
		 * files.setFileName(file.getOriginalFilename());
		 * files.setFileType("Bank Audit"); files.setUploadedBy( userid);
		 * files.setIdClient(bankCompany); files.setFinancialYear(bankYear);
		 * files.setDate(Calendar.getInstance().getTime());
		 * 
		 * fileRepository.save(files);
		 * 
		 * 
		 * } catch (Exception e) { // throw new RuntimeException("FAIL! -> message = " +
		 * //e.getMessage()); e.printStackTrace(); }
		 */
		
		try {
			String qryStr ="select count(*) datacount from [dbo].[bank_details] where financialyear ="+bankYear+"and id_company ="+bankCompany +";";
			Query query = em.createNativeQuery(qryStr);
			System.out.println("uploadCash ="+query);
			List<Object[]> audits = (List<Object[]>)query.getResultList();
			System.out.println("Size="+audits.size());
			if(audits.size()>0) {
				bankDetailsRepository.deleteAll();
				List<BankDetails> lstCB =BankDetailsExcelUtils.parseExcelFile(file.getInputStream(),bankCompany,
						bankYear); // Save Customers to DataBase
				bankDetailsRepository.saveAll(lstCB);
			}else {
				List<BankDetails> lstCB =BankDetailsExcelUtils.parseExcelFile(file.getInputStream(),bankCompany,
						bankYear); // Save Customers to DataBase
				bankDetailsRepository.saveAll(lstCB);
			}		
			
		
		
		
		Files files = new Files();

		files.setFileName(file.getOriginalFilename());
		files.setFileType("Bank Audit");
		files.setUploadedBy( userid);
		files.setIdClient(bankCompany);
		files.setFinancialYear(bankYear);
		files.setDate(Calendar.getInstance().getTime());

		fileRepository.save(files);


	} catch (Exception e) { // throw new RuntimeException("FAIL! -> message = " +
		//e.getMessage());
		e.printStackTrace(); 
		} 
	}
	
	public List<BankAuditFindings> getBankAuditFindings(String companyId) {

		String bankCal = "select CONVERT(varchar,mdate,101) as mdate, (SUM(debit) - SUM(credit)) as bankbalance from [dbo].[bank_details] where id_company = "+companyId+" group by mdate order by min(id);";

		Query query = em.createNativeQuery(bankCal);
		System.out.println(bankCal);
		@SuppressWarnings("unchecked")
		List<Object[]> audits = (List<Object[]>)query.getResultList();
		List<BankAuditFindings> dtos = new ArrayList<>();
		for(Object[] audit:audits) {
			BankAuditFindings dto = new BankAuditFindings();
			dto.setDate((String)audit[0]);
			dto.setBankBalance(String.valueOf((Double)audit[1]));
			
			dtos.add(dto);
		}
		return dtos;

	}
	
	public String getCountMorethenPayment(String companyid) {
		String counts = "select count(id) from cashbook where credit>20000 and company_id="+companyid;

		Query query = em.createNativeQuery(counts);
		System.out.println(counts);
		@SuppressWarnings("unchecked")
		List<Object[]> audits = (List<Object[]>)query.getResultList();
		String dtos = "";
		for(Object audit:audits) {
			//BankAuditFindings dto = new BankAuditFindings();
			dtos = String.valueOf((Integer)audit);
			//dto.setBankBalance(String.valueOf((Double)audit[1]));
			
			//dtos.add(dto);
		}
		return dtos;
	}
	
	@Transactional(rollbackOn = {Exception.class})
	public void saveFixedSupportingDocs(FixedAssetSupportingDocs docs){
		System.out.println("Upload supporting file Fixed Service");
		try {
			
			fixedAssetSupportingDocsRepository.save(docs);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	public List<FixedAssetSupportingDocs> getSupportingFixedDetailsFileDetails(String particulars) {

		return fixedAssetSupportingDocsRepository.findAllSupportingFixedDetailsFileDetails(particulars);

	}
	
	

	@Transactional(rollbackOn = {Exception.class})
	public void saveBankSupportingDocs(BankSupportingDocs docs){
		System.out.println("Upload supporting file Fixed Service");
		try {
			/*
			 * List<CashBook> lstCB = ExcelUtils.parseExcelFile(file.getInputStream()); //
			 * Save Customers to DataBase cbRepository.saveAll(lstCB);
			 * 
			 * Files files = new Files();
			 * 
			 * files.setFileName(file.getOriginalFilename());
			 * files.setFileType("Cash Book"); files.setUploadedBy( userid);
			 * files.setIdClient(clientCompany);
			 * files.setDate(Calendar.getInstance().getTime());
			 */
			bankSupportingDocsRepository.save(docs);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	public List<BankSupportingDocs> getSupportingBankFileDetails(String bankBalance) {

		return bankSupportingDocsRepository.findAllSupportingBankFileDetails(bankBalance);

	}
	
	@Transactional(rollbackOn = {Exception.class})
	public void getDoneFixedSupportingBill(CompanyAuditStatus docs){
		System.out.println("Upload getDoneFixedSupportingBill file Fixed Service");
		try {			
			companyAuditStatusRepository.save(docs);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Transactional(rollbackOn = {Exception.class})
	public void getDoneBankVerify(CompanyAuditStatus docs){
		System.out.println("Upload getDoneBankVerify file Fixed Service");
		try {			
			companyAuditStatusRepository.save(docs);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@Transactional(rollbackOn = {Exception.class})
	public void saveDebtorsSupportingDocs(DebtorsSupportingDocs docs){
		System.out.println("Upload supporting file Debtors Service");
		try {
			
			debtorsSupportingDocsRepository.save(docs);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<DebtorsSupportingDocs> getSupportingDebtorFileDetails(String particulars) {

		return debtorsSupportingDocsRepository.findAllSupportingDebtorFileDetails(particulars);

	}
	
	@Transactional(rollbackOn = {Exception.class})
	public void getDoneDebtorPaymentModal(CompanyAuditStatus docs){
		System.out.println("Upload getDoneDebtorPaymentModal file Debtor Service");
		try {			
			companyAuditStatusRepository.save(docs);
		} catch (Exception e) {
			//       	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String getAssetWithoutBilCount(String cmpny){
		
		//	Query query = em.createNativeQuery("select ss.sub_status_name,s.comment from company_audit_status s,sub_status ss WHERE s.id_company = 1 AND s.year='2020' AND s.id_sub_status=ss.id;");

			Query query = em.createNativeQuery("select COUNT(*) assetwithoutbill from [dbo].[fixed_asset] F LEFT JOIN [dbo].[fixed_asset_supporting_docs] FS ON F.particulars = FS.particulars WHERE FS.file_details IS NULL AND F.id_company = "+cmpny+";");
			System.out.println("select COUNT(*) assetwithoutbill from [dbo].[fixed_asset] F LEFT JOIN [dbo].[fixed_asset_supporting_docs] FS ON F.particulars = FS.particulars WHERE FS.file_details IS NULL AND F.id_company = "+cmpny+";");
			@SuppressWarnings("unchecked")
			List<Object[]> audits = (List<Object[]>)query.getResultList();
			String dtos = "";
			for(Object audit:audits) {
				dtos = String.valueOf((Integer)audit);
			}
			return dtos;
		}
}
