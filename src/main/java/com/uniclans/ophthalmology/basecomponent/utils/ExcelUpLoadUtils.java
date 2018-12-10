package com.uniclans.ophthalmology.basecomponent.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public class ExcelUpLoadUtils {

	private static Logger log = Logger.getLogger(ExcelUpLoadUtils.class);


	/**
	 * 读出excel内容(poi)
	 * 
	 * 还写了一个poi实现的方法，这里没有用到。poi与jxl的 区别是，在读入文件无法识别的时候，会报出文件头的编 码，方便查看。
	 * 
	 * @param file
	 * @param sheetNum  第几 个tab 页  0开始
 	 * @return
	 * @throws IOException
	 */
	public static List<String[]> getExcelDataWithPOI(File file,int sheetNum) throws IOException {
		InputStream is = new FileInputStream(file);// 与jxl不同的是，poi是根据InputStream来生成workbook的
		String type = file.getName().split("\\.")[1];// 取得文件的后缀名
		log.info("file type:" + type);
		List<String[]> resultList = new ArrayList<String[]>();// 返回的数据
		String[] data;// 同样每个String[]都是一行数据

		if (type.equals("xls")) {// 如果文件后缀名为xls，则如下处理文件处理 1997-2003版本
			HSSFWorkbook workboob = new HSSFWorkbook(is);// 生成HSSFWorkbook，这种类只用于xls文件
			HSSFSheet sheet = workboob.getSheetAt(sheetNum);// 获得第一张表单

			// 循环行,从第二行开始
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = sheet.getRow(rowNum);
				if (hssfRow == null) {// 行判空
					continue;
				}
				data = new String[hssfRow.getLastCellNum()];

				// 循环每一行的每个单元
				DecimalFormat df = new DecimalFormat("0");    
				for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
					HSSFCell hssfCell = hssfRow.getCell(cellNum);
					if (hssfCell == null) {// 单元判空
						continue;
					}
					if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						//先看是否是日期格式   
	                    if(HSSFDateUtil.isCellDateFormatted(hssfCell)){   
	                        //读取日期格式   
	                    	data[cellNum] = DateUtils.formatTime(hssfCell.getDateCellValue());   
	                    }else{   
	                        //读取数字   
	                    	data[cellNum] = df.format(hssfCell.getNumericCellValue());   
	                    }   
	                    continue; 
					} else {
						// 否则视为字符串
						data[cellNum] = hssfCell.getRichStringCellValue().getString();
					}
				}
				resultList.add(data);
			}
		} else {// 2003+版本
			/*
			 * xlsx文件处理，由于项目的版本过低，所有没有XSSFWorkbook这个类，
			 * XSSFWorkbook是用于xslx的类，除了使用的类不同，基本操作与xls 文件类似
			 */

			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(sheetNum);
			// 循环行,从第二行开始
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				XSSFRow row1 = sheet.getRow(rowNum);
				if (row1 == null) {// 行判空
					continue;
				}
				data = new String[row1.getLastCellNum()];

				// 循环每一行的每个单元
				for (int cellNum = 0; cellNum <= row1.getLastCellNum(); cellNum++) {
					XSSFCell xssfCell = row1.getCell(cellNum);
					if (xssfCell == null) {// 单元判空
						continue;
					}
					if (xssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						//先看是否是日期格式   
	                    if(HSSFDateUtil.isCellDateFormatted(xssfCell)){   
	                        //读取日期格式   
	                    	data[cellNum] = DateUtils.formatTime(xssfCell.getDateCellValue());   
	                    }else{   
	                        //读取数字   
	                    	data[cellNum] = xssfCell.getNumericCellValue()+"";   
	                    }   
	                    continue; 
					} else {
						// 否则视为字符串
						data[cellNum] = xssfCell.getRichStringCellValue().getString();
					}
				}
				resultList.add(data);
			}
		}
		return resultList;
	}



	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param paramName
	 *            reuqest里取param的KEY
	 * @param path
	 *            是存放文件的地址,为root下的子路径
	 * @return
	 */
	public static File getUploadFile(HttpServletRequest request, String paramName, String path) {

		if (request instanceof MultipartHttpServletRequest) {
			// 如果参数名未指定，则默认为importCommos
			if (StringUtils.isBlank(paramName)) {
				paramName = "importCommos";
			}
			// 如果保存路径未制动，则默认为/WEB-INF/upload/
			if (StringUtils.isBlank(path)) {
				path = "/WEB-INF/upload/";
			}

			// 由于使用的是ajaxFileUpLoad的js插件，所以强制转换request为MultipartHttpServletRequest
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;

			// MultipartFile是存放在内存中或以临时文件形式存放在硬盘上的，需要转换为File文件存储
			MultipartFile file = mRequest.getFile(mRequest.getFileNames().next());// 取得上传的MultipartFile文件

			File tempFile; // 即将转换的文件

			// 以下三行为取得真实路径，上传的文件将要存储于该路径下
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			ServletContext servletContext = webApplicationContext.getServletContext();
			String dirPath = servletContext.getRealPath(path);
			File dir = new File(dirPath);
			// 如果该路径不存在，则创建
			if (!dir.exists()) {
				dir.mkdir();
			}
			// 生成包含路径的完整文件名，文件名此处为原名加上当前时间为前缀生成
			String filePath = dirPath + "/" + (new Date()).getTime() + file.getOriginalFilename();
			tempFile = new File(filePath);// 生成文件

			try {

				// 取得目标文件的OutputSteam和源文件的InputStream
				OutputStream os = new FileOutputStream(tempFile);
				InputStream ins = file.getInputStream();
				int bytesRead = 0;
				byte[] buffer = new byte[8192];// 字节流，8192byte=1kb

				// 写入文件字节流
				while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				// 结束后关闭Stream
				os.close();
				ins.close();
				return tempFile;// 返回保存的文件
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	

	public static void main(String[] args) throws IOException {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("new   sheet");
			HSSFCellStyle style = wb.createCellStyle(); // 样式对象
			HSSFFont f = wb.createFont();
			f.setFontHeightInPoints((short) 14);// 字号
			f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			// 设置颜色
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// style.setFillForegroundColor(HSSFColor.DARK_TEAL.index);//前景颜色
			// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充方式，前色填充
			// 边框填充
			style.setFont(f);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			HSSFRow row = sheet.createRow((short) 0);
			row.setHeight((short) 1000);
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue("订单编号"); // 跨单元格显示的数据
			cell0.setCellStyle(style); // 样式
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("会员编号"); // 跨单元格显示的数据
			cell1.setCellStyle(style); // 样式
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("创建时间"); // 跨单元格显示的数据
			cell2.setCellStyle(style); // 样式
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue("订单状态"); // 跨单元格显示的数据
			cell3.setCellStyle(style); // 样式
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue("订单金额"); // 跨单元格显示的数据
			cell4.setCellStyle(style); // 样式
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue("支付方式"); // 跨单元格显示的数据
			cell5.setCellStyle(style); // 样式
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue("收货人电话"); // 跨单元格显示的数据
			cell6.setCellStyle(style); // 样式
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue("收货人昵称"); // 跨单元格显示的数据
			cell7.setCellStyle(style); // 样式
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue("订单税费"); // 跨单元格显示的数据
			cell8.setCellStyle(style); // 样式
			
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue("收货地址"); // 跨单元格显示的数据
			cell9.setCellStyle(style); // 样式
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue("货品编号"); // 跨单元格显示的数据
			cell10.setCellStyle(style); // 样式
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue("货品名称"); // 跨单元格显示的数据
			cell11.setCellStyle(style); // 样式
			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue("购买数量"); // 跨单元格显示的数据
			cell12.setCellStyle(style); // 样式
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue("货品单价"); // 跨单元格显示的数据
			cell13.setCellStyle(style); // 样式
			HSSFCell cell14 = row.createCell(14);
			cell14.setCellValue("货品税费"); // 跨单元格显示的数据
			cell14.setCellStyle(style); // 样式
			
			sheet.addMergedRegion(new   Region(1,(short)0,5,(short)13));
			HSSFRow row1 = sheet.createRow((short) 1);
			row1.setHeight((short) 1000);
			HSSFCell cell01 = row1.createCell(14);
			cell01.setCellValue("xxxx"); // 跨单元格显示的数据
			cell01.setCellStyle(style); // 样式
			sheet.autoSizeColumn((short) 0); // 调整第一列宽度
			sheet.autoSizeColumn((short) 1); // 调整第二列宽度
			sheet.autoSizeColumn((short) 2); // 调整第一列宽度
			sheet.autoSizeColumn((short) 3); // 调整第二列宽度
			sheet.autoSizeColumn((short) 4); // 调整第一列宽度
			sheet.autoSizeColumn((short) 5); // 调整第二列宽度
			sheet.autoSizeColumn((short) 6); // 调整第一列宽度
			sheet.autoSizeColumn((short) 7); // 调整第二列宽度
			sheet.autoSizeColumn((short) 8); // 调整第一列宽度
			sheet.autoSizeColumn((short) 9); // 调整第二列宽度
			sheet.autoSizeColumn((short) 10); // 调整第一列宽度
			sheet.autoSizeColumn((short) 11); // 调整第二列宽度
			sheet.autoSizeColumn((short) 12); // 调整第一列宽度
			sheet.autoSizeColumn((short) 13); // 调整第二列宽度
			sheet.autoSizeColumn((short) 14); // 调整第一列宽度
			/*
			 * HSSFRow row2 = sheet.createRow((short) 1);
			 * sheet.addMergedRegion(new Region(0,(short)0,1,(short)0));
			 * HSSFCell ce=row.createCell((short)0); ce.setCellValue("项目//日期");
			 * //表格的第一行第一列显示的数据 ce.setCellStyle(style); //样式，居中 int num=0;
			 * for(int i=0;i<9;i++){ //循环9次，每一次都要跨单元格显示 //计算从那个单元格跨到那一格 int
			 * celln=0; int celle=0; if(i==0){ celln=0; celle=1; }else{
			 * celln=(i*2); celle=(i*2+1); } //单元格合并 //四个参数分别是：起始行，起始列，结束行，结束列
			 * sheet.addMergedRegion(new
			 * Region(0,(short)(celln+1),0,(short)(celle+1))); HSSFCell cell =
			 * row.createCell((short) (celln+1) );
			 * cell.setCellValue("merging"+i); //跨单元格显示的数据
			 * cell.setCellStyle(style); //样式
			 * //不跨单元格显示的数据，如：分两行，上一行分别两格为一格，下一行就为两格，“数量”，“金额” HSSFCell cell1 =
			 * row2.createCell((short) celle ); HSSFCell cell2 =
			 * row2.createCell((short) (celle+1));
			 * //cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			 * cell1.setCellValue("数量"); cell1.setCellStyle(style); //
			 * cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			 * cell2.setCellValue("金额"); cell2.setCellStyle(style); num++; }
			 */
			File file = new File("/Users/zhoufufa/workbook.xls");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
			System.out.print("OK");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	/**
     * 导入
     * 
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List<String[]> importCsv(File file){
        List<String[]> dataList=new ArrayList<String[]>();
        
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
            String line = ""; 
            while ((line = br.readLine()) != null) { 
                dataList.add(line.replaceAll("\t", "").split(","));
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return dataList;
    }
}
