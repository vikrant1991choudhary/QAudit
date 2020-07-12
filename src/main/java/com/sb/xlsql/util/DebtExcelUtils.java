package com.sb.xlsql.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.format.CellNumberFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sb.xlsql.model.Debtors;
import com.sb.xlsql.model.TrialBalance;

public class DebtExcelUtils {


	public static ByteArrayInputStream cashbookToExcel(List<Debtors> debtor) throws IOException {
		String[] COLUMNs = {"Particulars","Opening Balance","Debit", "Credit","Closing Balance"};
		try(
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			CreationHelper createHelper = workbook.getCreationHelper();
	 
			Sheet sheet = workbook.createSheet("Sundry Debtors");
	 
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
			for (Debtors debt : debtor) {
				Row row = sheet.createRow(rowIdx++);
	 
				
				row.createCell(0).setCellValue(debt.getParticulars());
				row.createCell(1).setCellValue(debt.getOpeningBalance());
				Cell debit=row.createCell(2);
				debit.setCellValue(debt.getDebit());
				debit.setCellStyle(numCellStyle);
				Cell credit=row.createCell(3);
				credit.setCellValue(debt.getCredit());
				credit.setCellStyle(numCellStyle);
				row.createCell(4).setCellValue(debt.getClosingBalance());
			}
	 
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static List<Debtors> parseExcelFile(InputStream is,Long debtCompany,String debtYear) {
		try {
    		Workbook workbook = new XSSFWorkbook(is);
     
    		Sheet sheet = workbook.getSheet("Sundry Debtors");
    		Iterator<Row> rows = sheet.iterator();
    		
    		List<Debtors> lstCash = new ArrayList<Debtors>();
    		
    		int rowNumber = 0;
    	
    		while (rows.hasNext()) {
    			Row currentRow = rows.next();
    			
    			// skip header
    			if(rowNumber <9) {
    				rowNumber++;
    				continue;
    			}
    			
    			Iterator<Cell> cellsInRow = currentRow.iterator();

    			Debtors cb = new Debtors();
    			
    			//int cellIndex = 0;
    			//while (cellsInRow.hasNext()) {
    			int lastColumn = Math.max(currentRow.getLastCellNum(), 5);   // 5 define no of column
    			for (int cellIndex = 0; cellIndex < lastColumn; cellIndex++) {
    				if(currentRow.getRowNum()<136) {
    					Cell currentCell = currentRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        				if(currentCell.getCellType() == Cell.CELL_TYPE_BLANK)
        					continue;
        				 if(cellIndex==0) { // Particulars
        					cb.setParticulars(currentCell.getStringCellValue());
        				 }else if(cellIndex==1) {
        					 String formattedvalue = "";
        					 String formatstring = currentCell.getCellStyle().getDataFormatString();
        					 System.out.println(formatstring);
        					 formattedvalue = new CellNumberFormatter(formatstring).format(currentCell.getNumericCellValue());
        					 cb.setOpeningBalance(formattedvalue);
        				 }
        				 else if(cellIndex==2) {//Credit
        					cb.setDebit(currentCell.getNumericCellValue());
        				} else if(cellIndex == 3) {
        					cb.setCredit(currentCell.getNumericCellValue());
        				}else if(cellIndex==4) {
        					String formattedvalue = "";
       					 	String formatstring = currentCell.getCellStyle().getDataFormatString();
       					 	System.out.println(formatstring);
       					 formattedvalue = new CellNumberFormatter(formatstring).format(currentCell.getNumericCellValue());
        					cb.setClosingBalance(formattedvalue);
        				}
        				cb.setIdCompany(debtCompany);
        				cb.setFinancialyear(debtYear);
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
