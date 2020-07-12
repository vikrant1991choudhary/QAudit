package com.sb.xlsql.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sb.xlsql.model.CashBook;

public class ExcelUtils {
	public static ByteArrayInputStream cashbookToExcel(List<CashBook> cashbooks) throws IOException {
		String[] COLUMNs = {"ID", "Date", "Particulars", "Descriptions", "VchType", "VchNo", "Debit", "Credit"};
		try(
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			CreationHelper createHelper = workbook.getCreationHelper();
	 
			Sheet sheet = workbook.createSheet("CashBook");
	 
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
			for (CashBook cashbook : cashbooks) {
				Row row = sheet.createRow(rowIdx++);
	 
				row.createCell(0).setCellValue(cashbook.getId());
				row.createCell(1).setCellValue(cashbook.getDate());
				row.createCell(2).setCellValue(cashbook.getParticulars());
				row.createCell(3).setCellValue(cashbook.getDescriptions());
				row.createCell(4).setCellValue(cashbook.getVchtype());
				Cell vchno = row.createCell(5);
				vchno.setCellValue(cashbook.getVchno());
				vchno.setCellStyle(numCellStyle);
				Cell debit=row.createCell(6);
				debit.setCellValue(cashbook.getDebit());
				debit.setCellStyle(numCellStyle);
				Cell credit=row.createCell(7);
				credit.setCellValue(cashbook.getCredit());
				credit.setCellStyle(numCellStyle);
			}
	 
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static List<CashBook> parseExcelFile(InputStream is,long companyid) {
		try {
    		Workbook workbook = new XSSFWorkbook(is);
     
    		Sheet sheet = workbook.getSheet("CashBook");
    		Iterator<Row> rows = sheet.iterator();
    		
    		List<CashBook> lstCash = new ArrayList<CashBook>();
    		
    		int rowNumber = 0;
    	
    		while (rows.hasNext()) {
    			Row currentRow = rows.next();
    			
    			// skip header
    			if(rowNumber <6) {
    				rowNumber++;
    				continue;
    			}
    			
    			Iterator<Cell> cellsInRow = currentRow.iterator();

    			CashBook cb = new CashBook();
    			
    			//int cellIndex = 0;
    			//while (cellsInRow.hasNext()) {
    			int lastColumn = Math.max(currentRow.getLastCellNum(), 8);
    			for (int cellIndex = 0; cellIndex < lastColumn; cellIndex++) {
    				if(currentRow.getRowNum()<2563) {
    					Cell currentCell = currentRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        				if(currentCell.getCellType() == Cell.CELL_TYPE_BLANK)
        					continue;
        				if(cellIndex==0) { // Date
        					cb.setDate((currentCell.getDateCellValue()).toString());
        					//System.out.println(currentCell.getDateCellValue());
        				} else if(cellIndex==1) { // txnType
        					cb.setTxnType(currentCell.getStringCellValue());
        				} else if(cellIndex==2) { // Particulars
        					cb.setParticulars(currentCell.getStringCellValue());
        				} else if(cellIndex==3) { 
        					
        				} else if(cellIndex==4) {
        					
        					
        				} else if(cellIndex==5) {// Voucher type
        					cb.setVchtype(currentCell.getStringCellValue());
        				} else if(cellIndex==6) {//Voucher no
        					cb.setVchno(Integer.parseInt(currentCell.getStringCellValue()));
        				} else if(cellIndex==7) {//Credit
        					cb.setDebit(currentCell.getNumericCellValue());
        				} else if(cellIndex == 8) {
        					cb.setCredit(currentCell.getNumericCellValue());
        				}
        				cb.setCompanyId(companyid);
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
