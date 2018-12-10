package com.uniclans.ophthalmology.modules.diagnose.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class ExcelView extends AbstractXlsView {

	@Override
	public void buildExcelDocument(Map<String, Object> arg0,
			Workbook workBook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = arg0.get("fileName")+"";
//		response.setContentType("application/vdn.ms-excel;charset=utf-8\"");
		response.setCharacterEncoding("utf-8");
//		response.setHeader("Content-disposition", "attachment;filename="+fileName);
		response.setContentType("application/octet-stream;charset=utf-8");  
		response.setHeader("Content-Disposition", "attachment;filename="  
		        + new String(fileName.getBytes(),"iso-8859-1") );  
		
		
		FileOutputStream fileOut = new FileOutputStream(fileName);
		workBook.write(fileOut);
		fileOut.close();

		
		OutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	
}
