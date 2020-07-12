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

import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.TrialBalance;

public class TrialExcelUtils {

	public static ByteArrayInputStream cashbookToExcel(List<TrialBalance> trialBalance) throws IOException {
		String[] COLUMNs = {"Particulars","Debit", "Credit"};
		try(
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			CreationHelper createHelper = workbook.getCreationHelper();
	 
			Sheet sheet = workbook.createSheet("Trial Balance");
	 
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
			for (TrialBalance trailBlnc : trialBalance) {
				Row row = sheet.createRow(rowIdx++);
	 
				
				row.createCell(0).setCellValue(trailBlnc.getParticulars());
				Cell debit=row.createCell(1);
				debit.setCellValue(trailBlnc.getDebit());
				debit.setCellStyle(numCellStyle);
				Cell credit=row.createCell(2);
				credit.setCellValue(trailBlnc.getCredit());
				credit.setCellStyle(numCellStyle);
			}
	 
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static List<TrialBalance> parseExcelFile(InputStream is,Long meterialityCompany,String meterialityYear) {
		try {
    		Workbook workbook = new XSSFWorkbook(is);
     
    		Sheet sheet = workbook.getSheet("Trial Balance");
    		Iterator<Row> rows = sheet.iterator();
    		
    		List<TrialBalance> lstCash = new ArrayList<TrialBalance>();
    		
    		int rowNumber = 0;
    	
    		while (rows.hasNext()) {
    			Row currentRow = rows.next();
    			
    			// skip header
    			if(rowNumber <7) {
    				rowNumber++;
    				continue;
    			}
    			
    			Iterator<Cell> cellsInRow = currentRow.iterator();

    			TrialBalance cb = new TrialBalance();
    			
    			//int cellIndex = 0;
    			//while (cellsInRow.hasNext()) {
    			int lastColumn = Math.max(currentRow.getLastCellNum(), 3);  
    			for (int cellIndex = 0; cellIndex < lastColumn; cellIndex++) {
    				if(currentRow.getRowNum()<70) {
    					Cell currentCell = currentRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        				if(currentCell.getCellType() == Cell.CELL_TYPE_BLANK)
        					continue;
        				 if(cellIndex==0) { // Particulars
        					cb.setParticulars(currentCell.getStringCellValue());
        				} else if(cellIndex==1) {//Credit
        					cb.setDebit(currentCell.getNumericCellValue());
        				} else if(cellIndex == 2) {
        					cb.setCredit(currentCell.getNumericCellValue());
        				}
        				cb.setIdCompany(meterialityCompany);
        				cb.setFinancialyear(meterialityYear);
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
