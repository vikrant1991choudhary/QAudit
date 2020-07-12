package com.sb.xlsql.util;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.sb.xlsql.controller.ConstantsIF;
import com.sb.xlsql.model.CashBook;
import com.sb.xlsql.model.CashBookDTO;
import com.sb.xlsql.model.DailyCashDTO;
import com.itextpdf.text.Image;

public class CashBookReport {

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
	private static Font normal=new Font(Font.FontFamily.HELVETICA,9f,Font.NORMAL);
	private static Font normal1=new Font(Font.FontFamily.HELVETICA,9f,Font.NORMAL);
	private static Font italic=new Font(Font.FontFamily.HELVETICA,8f,Font.ITALIC);
	private static Font grey=new Font(Font.FontFamily.HELVETICA,9f,Font.NORMAL);
	private static Font red= new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
	private static Font catFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
	private static Font catFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);

	private static final Font SUBFONT = new Font(Font.getFamily("TIMES_ROMAN"), 10,    Font.BOLD|Font.UNDERLINE);
	
	
	// public static final String DEST = "CashBookReport.pdf";
	

	/* public static void main(String[] args) { */
	public static String createPDF(List<CashBook> getCashBookReport,List<CashBookDTO> getMultipleCashBookPayment,List<DailyCashDTO> getEverydayCashPayment) {
		double random = Math.random() * 49 + 1;
		String fileName = ConstantsIF.FILEPATH_TOSAVE + ConstantsIF.PDF_QAudit_FILE_NAME+random +ConstantsIF.PDF_FILE_EXT;

		try {

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			// MyFooter event =new MyFooter();
			//  writer.setPageEvent(event);
			header(document);
			cashPaymentDetails(document,getCashBookReport);
			multiplePaymentDetails(document,getMultipleCashBookPayment);
			everyDayPaymentDetails(document,getEverydayCashPayment);
			System.out.println("Created Success CashBook Report");
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fileName= "+fileName);
		return fileName;
	}
	
	private static void header(Document document) throws DocumentException {
		 catFont1.setColor(new BaseColor(255,255,255));
		 catFont2.setColor(new BaseColor(255,255,255));	
		  PdfPTable table1 = new PdfPTable(3);
		  table1.setWidthPercentage(100); 
		  float[]  columnWidths = {4f,4f,4f};
		  
		  table1.setWidths(columnWidths);
		  
		  PdfPCell cell2 = new PdfPCell(new Phrase("Q-Audit",catFont2));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		  cell2.setBorder(PdfPCell.NO_BORDER); 
		 // table1.setSpacingAfter(5);
		  table1.addCell(cell2); 
		  cell2 = new PdfPCell(new Phrase("Audit Findings",catFont2));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		  cell2.setBorder(PdfPCell.NO_BORDER); 
		  table1.setSpacingAfter(5f);
		  table1.addCell(cell2); 
		  String pattern = "dd-MM-yyyy";
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		  String date = simpleDateFormat.format(new Date());
		  cell2 = new PdfPCell(new Phrase("Date: "+date,catFont2));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		  cell2.setBorder(PdfPCell.NO_BORDER); 
		 // table1.setSpacingAfter(5f);
		  table1.addCell(cell2); 
		  document.add(table1);
		  
		  
		  PdfPTable table = new PdfPTable(2);
			

			table.setWidthPercentage(100);
			float[] columnWidths1 = {6f,6f};
			
			table.setWidths(columnWidths1);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Company Name: "+" Confitech Solutions Pvt Ltd", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			c1.setBorder(PdfPCell.NO_BORDER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("For the year ended at: "+" 31st March 2020", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setFixedHeight(20f);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("CIN: "+" U72300WB2014PTC202201", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setFixedHeight(20f);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GSTIN: "+" 19AAFCC6857M1ZY", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setFixedHeight(20f);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			table.setSpacingAfter(5f);
			document.add(table);
		 
	 }

	 private static void header1(Document document) throws DocumentException {
		 catFont1.setColor(new BaseColor(255,255,255));
		 catFont2.setColor(new BaseColor(255,255,255));	
		  PdfPTable table1 = new PdfPTable(3);
		  table1.setWidthPercentage(100); 
		  float[]  columnWidths = {4f,4f,4f};
		  
		  table1.setWidths(columnWidths);
		  
		  PdfPCell cell2 = new PdfPCell(new Phrase("Q-Audit",catFont2));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		  cell2.setBorder(PdfPCell.NO_BORDER); 
		 // table1.setSpacingAfter(5);
		  table1.addCell(cell2); 
		  cell2 = new PdfPCell(new Phrase("Audit Audit Summary",catFont2));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		  cell2.setBorder(PdfPCell.NO_BORDER); 
		  table1.setSpacingAfter(5f);
		  table1.addCell(cell2); 
		  String pattern = "dd-MM-yyyy";
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		  String date = simpleDateFormat.format(new Date());
		  cell2 = new PdfPCell(new Phrase("Date: "+date,catFont2));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		  cell2.setBorder(PdfPCell.NO_BORDER); 
		 // table1.setSpacingAfter(5f);
		  table1.addCell(cell2); 
		  document.add(table1);
		  
		  
		  PdfPTable table = new PdfPTable(2);
			

			table.setWidthPercentage(100);
			float[] columnWidths1 = {6f,6f};
			
			table.setWidths(columnWidths1);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Company Name: "+" Confitech Solutions Pvt Ltd", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			c1.setBorder(PdfPCell.NO_BORDER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("For the year ended at: "+" 31st March 2020", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setFixedHeight(20f);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("CIN: "+" U72300WB2014PTC202201", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setFixedHeight(20f);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GSTIN: "+" 19AAFCC6857M1ZY", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setFixedHeight(20f);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			table.setSpacingAfter(5f);
			document.add(table);
		 
	 }
	
	
	 private static void cashPaymentDetails(Document document,List<CashBook> getCashBookReport) throws DocumentException {
		
		 catFont1.setColor(new BaseColor(255,255,255));
		 PdfPTable table2 = new PdfPTable(1); 
		  table2.setWidthPercentage(100); 
		  float[]  columnWidths2 = {10f};
		  
		  table2.setWidths(columnWidths2);
		  
		  PdfPCell cell3 = new PdfPCell(new Phrase("Cash Audit",catFont1));
		  cell3.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell3.setFixedHeight(20f);
		  cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		  cell3.setBorder(PdfPCell.NO_BORDER); 
		  table2.setSpacingAfter(5);
		  table2.addCell(cell3); 
		  table2.setSpacingAfter(5f);
		  table2.setSpacingBefore(5f);
		  document.add(table2);
		
		  PdfPTable table1 = new PdfPTable(1); table1.setWidthPercentage(100); float[]
		  columnWidths = {10f};
		  
		  table1.setWidths(columnWidths);
		  
		  PdfPCell cell2 = new PdfPCell(new Phrase("CashPayment Above INR 20000",catFont1));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setBorder(PdfPCell.NO_BORDER); table1.setSpacingAfter(5);
		  table1.addCell(cell2); 
		  document.add(table1);
		 
		 
			PdfPTable table = new PdfPTable(7);
			

			table.setWidthPercentage(100);
			float[] columnWidths1 = {2f, 3f,3f,3f,3f,2f,2f};
			
			table.setWidths(columnWidths1);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Date", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Txn Type", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Particulars", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Voucher Type", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Voucher No", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Debit", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Credit", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			
			//set the value
			for (CashBook cashbook : getCashBookReport) {
			c1 = new PdfPCell(new Phrase(cashbook.getDate(), normal1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase(cashbook.getTxnType(), normal1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase(cashbook.getParticulars(), normal1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase(cashbook.getVchtype(), normal1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
			String VChnumber=String.valueOf(cashbook.getVchno());
			c1 = new PdfPCell(new Phrase(VChnumber, normal1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
			String debit1=String.valueOf(cashbook.getDebit());
			c1 = new PdfPCell(new Phrase(debit1, normal1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
			String credit1=String.valueOf(cashbook.getCredit());

			c1 = new PdfPCell(new Phrase(credit1, normal1));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
			/*
			 * c1 = new PdfPCell(new Phrase("", normal1));
			 * c1.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(c1); c1 = new
			 * PdfPCell(new Phrase("", normal1));
			 * c1.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(c1); c1 = new
			 * PdfPCell(new Phrase("", normal1));
			 * c1.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(c1); c1 = new
			 * PdfPCell(new Phrase("", normal1));
			 * c1.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(c1);
			 */
			}
			table1.setSpacingAfter(20f);
	        document.add(table);
			
	 }
	 private static void multiplePaymentDetails(Document document,List<CashBookDTO> getMultipleCashBookPayment) throws DocumentException {
			
		 catFont1.setColor(new BaseColor(255,255,255));
		
		  PdfPTable table1 = new PdfPTable(1); table1.setWidthPercentage(100); float[]
		  columnWidths = {10f};
		  
		  table1.setWidths(columnWidths);
		  
		  PdfPCell cell2 = new PdfPCell(new Phrase("Multiple Payments in cash to a Single Vendor",catFont1));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setBorder(PdfPCell.NO_BORDER);
		  table1.setSpacingBefore(20f);
		  table1.setSpacingAfter(5);
		  table1.addCell(cell2); 
		  document.add(table1);
		 
		 
			PdfPTable table = new PdfPTable(3);
			

			table.setWidthPercentage(100);
			float[] columnWidths1 = {4f,4f,4f};
			
			table.setWidths(columnWidths1);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Particulars", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Total Cash Payment (INR)", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("No of Times", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			
			for (CashBookDTO cashbook : getMultipleCashBookPayment) {
				c1 = new PdfPCell(new Phrase(cashbook.getParticulars(), normal1));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(c1);
				String credit1=String.valueOf(cashbook.getCredit());
				c1 = new PdfPCell(new Phrase(credit1, normal1));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase(String.valueOf(cashbook.getCount()), normal1));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(c1);
				
				}
			
			
			
			
			table1.setSpacingAfter(20f);
	        document.add(table);
			
	 }
	 
	 private static void everyDayPaymentDetails(Document document,List<DailyCashDTO> getEverydayCashPayment) throws DocumentException {
			
		 catFont1.setColor(new BaseColor(255,255,255));
		
		  PdfPTable table1 = new PdfPTable(1); table1.setWidthPercentage(100); float[]
		  columnWidths = {10f};
		  
		  table1.setWidths(columnWidths);
		  
		  PdfPCell cell2 = new PdfPCell(new Phrase("Cash in Hand everyday",catFont1));
		  cell2.setBackgroundColor(new BaseColor(153, 51, 51)); 
		  cell2.setFixedHeight(20f);
		  cell2.setBorder(PdfPCell.NO_BORDER); 
		  table1.setSpacingBefore(20f);
		  table1.setSpacingAfter(5);
		  table1.addCell(cell2); 
		  document.add(table1);
		 
		 
			PdfPTable table = new PdfPTable(2);
			

			table.setWidthPercentage(100);
			float[] columnWidths1 = {6f, 6f};
			
			table.setWidths(columnWidths1);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Date", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Cash Balance", catFont1));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setFixedHeight(20f);
			c1.setBackgroundColor(new BaseColor(153, 51, 51));
			table.addCell(c1);
			
			
			for (DailyCashDTO cashbook : getEverydayCashPayment) {
				c1 = new PdfPCell(new Phrase(cashbook.getDate(), normal1));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(c1);	
				Double roundoff=Double.parseDouble(cashbook.getDailyCash());
				double dailyCash = (double) Math.round(roundoff * 100) / 100;
				
				c1 = new PdfPCell(new Phrase(String.valueOf(String.format("%.3f", dailyCash)), normal1));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(c1);
				
				}
			
			
			
			table1.setSpacingAfter(20f);
	        document.add(table);
			
	 }

	 public static String createPDF1(List<CashBook> getCashBookReport,List<CashBookDTO> getMultipleCashBookPayment,List<DailyCashDTO> getEverydayCashPayment) {
		double random = Math.random() * 49 + 1;
		String fileName = ConstantsIF.FILEPATH_TOSAVE + ConstantsIF.PDF_QAudit_FILE_NAME+random +ConstantsIF.PDF_FILE_EXT;

		try {

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			// MyFooter event =new MyFooter();
			//  writer.setPageEvent(event);
			header1(document);
			
			Paragraph paragraph1 = new Paragraph("Cash payments above INR 20,000", catFont1);
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph1);

			Image img1 = Image.getInstance("C:/QAudit/src/main/resources/static/images/chart.png");
			document.add(img1);


			Paragraph paragraph2 = new Paragraph("Multiple payments in cash to a single vendor", catFont1);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph2);
			

			Image img2 = Image.getInstance("C:/QAudit/src/main/resources/static/images/chart2.png");
			document.add(img2);
			Paragraph paragraph3 = new Paragraph("Cash in hand every day", catFont1);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph3);
			
			Image img3 = Image.getInstance("C:/QAudit/src/main/resources/static/images/chart3.png");
			
			/*img.setAlignment(Element.ALIGN_CENTER);
			img.setAbsolutePosition(((document.right() - document.left()) / 2) - 30 ,
					document.top() + 30);*/
			document.add(img3);
			
			
			//cashPaymentDetails(document,getCashBookReport);
			//multiplePaymentDetails(document,getMultipleCashBookPayment);
			//everyDayPaymentDetails(document,getEverydayCashPayment);
			System.out.println("Created Success CashBook Report");
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fileName= "+fileName);
		return fileName;
	}
}
