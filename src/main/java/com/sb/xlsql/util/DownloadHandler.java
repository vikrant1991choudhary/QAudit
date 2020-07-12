package com.sb.xlsql.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sb.xlsql.controller.ConstantsIF;


@WebServlet(
		  name = "download",
		  description = "Servlet",
		  urlPatterns = {"/download"}
		)
public class DownloadHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DownloadHandler() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Do Post Calling--------");
		String fileName = request.getParameter("file");
		String bhadroFileName = "";
		if(fileName!=null && fileName.length() > 0){
			bhadroFileName = fileName.substring(ConstantsIF.FILEPATH_TOSAVE.length());
		}
		//response.setContentType("text/html");
		response.setContentType("application/pdf");
		PrintWriter out = response.getWriter();   
		response.setContentType("APPLICATION/OCTET-STREAM");   
		response.setHeader("Content-Disposition","attachment; filename=\"" + bhadroFileName + "\"");   

		FileInputStream fileInputStream = new FileInputStream(fileName);  

		int i;   
		while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
		}   
		fileInputStream.close();   
		out.close();   
	}

}
