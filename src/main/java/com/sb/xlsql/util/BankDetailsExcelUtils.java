package com.sb.xlsql.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sb.xlsql.model.BankDetails;
import com.sb.xlsql.model.TrialBalance;

public class BankDetailsExcelUtils {


	public static ByteArrayInputStream cashbookToExcel(List<BankDetails> bankDetails) throws IOException {
		String[] COLUMNs = {"Date","Particulars"," "," ","Vch Type","Vch No.","Debit", "Credit"};
		try(
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			CreationHelper createHelper = workbook.getCreationHelper();
	 
			Sheet sheet = workbook.createSheet("Bank Statement");
	 
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
	 
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
	 
			// Row for Header
			Row headerRow = sheet.createRow(0);
	 
			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}
	 
			// CellStyle for Date
			CellStyle numCellStyle = workbook.createCellStyle();
			numCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
	 
			int rowIdx = 1;
			for (BankDetails bankBlnc : bankDetails) {
				Row row = sheet.createRow(rowIdx++);
	 
				row.createCell(0).setCellValue(bankBlnc.getDate());
				row.createCell(1).setCellValue(bankBlnc.getParticulars());
				row.createCell(2).setCellValue(bankBlnc.getBlank1());
				row.createCell(3).setCellValue(bankBlnc.getBlank2());
				row.createCell(4).setCellValue(bankBlnc.getVchType());
				Cell vchNo=row.createCell(5);
				vchNo.setCellValue(bankBlnc.getVchNo());
				vchNo.setCellStyle(numCellStyle);
				Cell debit=row.createCell(6);
				debit.setCellValue(bankBlnc.getDebit());
				debit.setCellStyle(numCellStyle);
				Cell credit=row.createCell(7);
				credit.setCellValue(bankBlnc.getCredit());
				credit.setCellStyle(numCellStyle);
			}
	 
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static List<BankDetails> parseExcelFile(InputStream is,Long bankCompany,String bankYear) {
		try {
    		Workbook workbook = new XSSFWorkbook(is);
     
    		Sheet sheet = workbook.getSheet("Bank Statement");
    		Iterator<Row> rows = sheet.iterator();
    		
    		List<BankDetails> lstCash = new ArrayList<BankDetails>();
    		
    		int rowNumber = 0;
    	
    		while (rows.hasNext()) {
    			Row currentRow = rows.next();
    			
    			// skip header
    			if(rowNumber <4) {
    				rowNumber++;
    				continue;
    			}
    			
    			Iterator<Cell> cellsInRow = currentRow.iterator();

    			BankDetails cb = new BankDetails();
    			
    			//int cellIndex = 0;
    			//while (cellsInRow.hasNext()) {
    			int lastColumn = Math.max(currentRow.getLastCellNum(), 9);  
    			for (int cellIndex = 0; cellIndex < lastColumn; cellIndex++) {
    				if(currentRow.getRowNum()<3023) {
    					//System.out.println("Current Row===="+currentRow.getRowNum());
    					Cell currentCell = currentRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        				if(currentCell.getCellType() == Cell.CELL_TYPE_BLANK)
        					continue;
        				
        				if(cellIndex==0) {
        					if(currentCell.getCellType() == Cell.CELL_TYPE_BLANK)
        						cb.setDate("");
        					else if(currentCell.getCellType() == Cell.CELL_TYPE_STRING)
        						cb.setDate(currentCell.getStringCellValue());
        					else
        						cb.setDate((currentCell.getDateCellValue()).toString());
        				}else if(cellIndex==1) { // Particulars
        					cb.setTxnType(currentCell.getStringCellValue());
        				}else if(cellIndex==2) { // Particulars
        					cb.setParticulars(currentCell.getStringCellValue());
        				}else if(cellIndex==3) { // Particulars
        					cb.setBlank1(currentCell.getStringCellValue());
        				}else if(cellIndex==4) { // Particulars
        					cb.setBlank2(currentCell.getStringCellValue());
        				}else if(cellIndex==5) { // Particulars
        					cb.setVchType(currentCell.getStringCellValue());
        				}else if(cellIndex==6) {//Credit
        					cb.setVchNo(currentCell.getStringCellValue());
        				}  else if(cellIndex==7) {//Credit
        					cb.setDebit(currentCell.getNumericCellValue());
        				} else if(cellIndex == 8) {
        					cb.setCredit(currentCell.getNumericCellValue());
        				}
        				cb.setIdCompany(bankCompany);
        				cb.setFinancialyear(bankYear);
        				lstCash.add(cb);
    				}
    				else {
    					break;
    				} 				
    				
    				//cellIndex++;
    				
    			}
    			
    			
    		}
    		
    		// Close WorkBook
    		workbook.close();
    		
    		return lstCash;
        } catch (IOException e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
	}
	
	




}
