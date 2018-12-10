package com.uniclans.ophthalmology.modules.diagnose.viewcomponent.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.component.IAiResultTranslateService;
import com.uniclans.ophthalmology.modules.basedata.service.model.AiResultTranslateVo;
import com.uniclans.ophthalmology.modules.diagnose.service.component.IDiagnoseRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.component.ITestRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DiagnoseRecordVo;
import com.uniclans.ophthalmology.modules.diagnose.viewcomponent.IReportComponent;

@Service
public class ReportCheckViewComponentImpl implements IReportComponent {
	@Resource
	private ITestRecordService testRecordService;
	@Resource
	private IAiResultTranslateService aiResultTranslateService;
	@Resource
	private IDiagnoseRecordService diagnoseRecordService;

	@Override
	public HSSFWorkbook generateWorkbookDiagnose(DiagnoseRecordVo queryVo) throws Exception {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("诊断报表");
		HSSFSheet sheetAi = workbook.createSheet("AI诊断统计");
		HSSFSheet sheetDiagnose = workbook.createSheet("专家诊断统计");
		// ProductVo productVo = productService.selects(productBatchNo);
		// createProductRow(productVo,workbook,sheet,productBatchNo);
		createAISheet(workbook, sheetAi, queryVo);
		createDiagnoseSheet(workbook, sheetDiagnose, queryVo);

		int i3 = 0;
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 7000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 7000);
		sheet.setColumnWidth(i3++, 20000);
		sheet.setColumnWidth(i3++, 20000);

		PageFinder<Map<String, Object>> priceIndexes = diagnoseRecordService.pagedDiagnoseRecordReports(queryVo);
		List<Map<String, Object>> list = priceIndexes.getData();
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;

		HSSFCellStyle cellStyle = workbook.createCellStyle();

		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 16); // 字体大小
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		cellStyle.setFont(font);
		// 写入各个字段的名称
		int i1 = 0;
		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("患者姓名");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("医院名称");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("医生");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("诊断时间");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue("AI诊断结果");

		cell = row.createCell(i1++);
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("专家检查结果");
		
		cell = row.createCell(i1++);
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("医院优惠码");

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Font fontRecord = workbook.createFont();
				fontRecord.setFontHeightInPoints((short) 14); // 字体大小
				cellStyle.setFont(fontRecord);
				HSSFRow dataRow = sheet.createRow(i + 1);
				Map<String, Object> rowData = list.get(i);

				int i2 = 0;
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("患者姓名");
				cell.setCellValue(String.valueOf(rowData.get("patientName")));
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("医院名称");
				cell.setCellValue(String.valueOf(rowData.get("hospitalName")));
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("医生");
				cell.setCellValue(String.valueOf(rowData.get("doctorName") == null ? "" : rowData.get("doctorName")));
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("诊断时间");
				cell.setCellValue(
						String.valueOf(rowData.get("diagnoseTime") == null ? "" : rowData.get("diagnoseTime")));
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				// cell.setCellValue("AI诊断结果");
				cell.setCellValue(getAiResult(rowData));

				cell = dataRow.createCell(i2++);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("专家检查结果");
				cell.setCellValue(getDiagnoseResult(rowData));
				
				
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("优惠码");
				cell.setCellValue(String.valueOf(rowData.get("code") == null ? "" : rowData.get("code")));
				cell.setCellStyle(cellStyle);
			}
		}
		return workbook;
	}

	private void createAISheet(HSSFWorkbook workbook, HSSFSheet sheet, DiagnoseRecordVo queryVo) throws Exception {

		int i3 = 0;
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 20000);

		List<Map<String, Object>> list = diagnoseRecordService.getAiStatisticData(queryVo.getStartTime(),
				queryVo.getEndTime());
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;

		HSSFCellStyle cellStyle = workbook.createCellStyle();

		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 16); // 字体大小
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		cellStyle.setFont(font);
		// 写入各个字段的名称
		int i1 = 0;
		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("未诊断");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("无效图像");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("阳性");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("阴性");
		cell.setCellStyle(cellStyle);

		cell = row.createCell(i1++);
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue("全部");

		cell = row.createCell(i1++);
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("医院");

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Font fontRecord = workbook.createFont();
				fontRecord.setFontHeightInPoints((short) 14); // 字体大小
				cellStyle.setFont(fontRecord);
				HSSFRow dataRow = sheet.createRow(i + 1);
				Map<String, Object> rowData = list.get(i);
				BigDecimal total = new BigDecimal(String.valueOf(rowData.get("totalCount")));
				int i2 = 0;
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("未诊断");
				BigDecimal curData = new BigDecimal(String.valueOf(rowData.get("notDiagnoseCount")));
				double percent = 0;
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				
				cell.setCellValue(String.valueOf(rowData.get("notDiagnoseCount"))+"("+percent+"%)");
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("无效图像");
				curData = new BigDecimal(String.valueOf(rowData.get("invalidCount")));
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				cell.setCellValue(String.valueOf(rowData.get("invalidCount"))+"("+percent+"%)");
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("阳性");
				curData = new BigDecimal(String.valueOf(rowData.get("positiveCount")));
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				cell.setCellValue(String.valueOf(rowData.get("positiveCount")+"("+percent+"%)"));
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("阴性");
				curData = new BigDecimal(String.valueOf(rowData.get("negativeCount")));
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				cell.setCellValue(String.valueOf(rowData.get("negativeCount"))+"("+percent+"%)");
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("所有");
				cell.setCellValue(String.valueOf(rowData.get("totalCount")));

				cell = dataRow.createCell(i2++);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("医院");
				cell.setCellValue(String.valueOf(rowData.get("hospitalName") == null ? "" : rowData.get("hospitalName")));

			}
		}

	}
	private void createDiagnoseSheet(HSSFWorkbook workbook, HSSFSheet sheet, DiagnoseRecordVo queryVo) throws Exception {
		
		int i3 = 0;
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 4000);
		sheet.setColumnWidth(i3++, 20000);
		
		List<Map<String, Object>> list = diagnoseRecordService.getDiagnoseStatisticData(queryVo.getStartTime(),
				queryVo.getEndTime());
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 16); // 字体大小
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		cellStyle.setFont(font);
		// 写入各个字段的名称
		int i1 = 0;
		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("未诊断");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("无效图像");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("阳性");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell(i1++);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("阴性");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell(i1++);
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue("全部");
		
		cell = row.createCell(i1++);
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("医院");
		
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Font fontRecord = workbook.createFont();
				fontRecord.setFontHeightInPoints((short) 14); // 字体大小
				cellStyle.setFont(fontRecord);
				HSSFRow dataRow = sheet.createRow(i + 1);
				Map<String, Object> rowData = list.get(i);
				BigDecimal total = new BigDecimal(String.valueOf(rowData.get("totalCount")));
				int i2 = 0;
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("未诊断");
				BigDecimal curData = new BigDecimal(String.valueOf(rowData.get("notDiagnoseCount")));
				double percent = 0;
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				
				cell.setCellValue(String.valueOf(rowData.get("notDiagnoseCount"))+"("+percent+"%)");
				cell.setCellStyle(cellStyle);
				
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("无效图像");
				curData = new BigDecimal(String.valueOf(rowData.get("invalidCount")));
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				cell.setCellValue(String.valueOf(rowData.get("invalidCount"))+"("+percent+"%)");
				cell.setCellStyle(cellStyle);
				
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("阳性");
				curData = new BigDecimal(String.valueOf(rowData.get("positiveCount")));
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				cell.setCellValue(String.valueOf(rowData.get("positiveCount")+"("+percent+"%)"));
				cell.setCellStyle(cellStyle);
				
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("阴性");
				curData = new BigDecimal(String.valueOf(rowData.get("negativeCount")));
				if(total.doubleValue()!=0) {
					percent = curData.divide(total,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
				}
				cell.setCellValue(String.valueOf(rowData.get("negativeCount"))+"("+percent+"%)");
				cell.setCellStyle(cellStyle);
				
				cell = dataRow.createCell(i2++);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("所有");
				cell.setCellValue(String.valueOf(rowData.get("totalCount")));
				
				cell = dataRow.createCell(i2++);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("医院");
				cell.setCellValue(String.valueOf(rowData.get("hospitalName") == null ? "" : rowData.get("hospitalName")));
				
			}
		}
		
	}

	private String getAiResult(Map<String, Object> map) throws Exception {
		String aiResult = String.valueOf(map.get("aiResult") == null ? "" : map.get("aiResult"));
		;
		if (aiResult == null || aiResult.equals("")) {
			return "未诊断";
		}
		if (aiResult.contains(",")) {
			String resultL = "";
			String resultR = "";
			AiResultTranslateVo queryVo = new AiResultTranslateVo();
			queryVo.setPageNo(0);
			queryVo.setPageSize(Integer.MAX_VALUE);
			List<AiResultTranslateVo> aiResultData = aiResultTranslateService.pageData(queryVo).getData();
			for (int i = 0; i < aiResultData.size(); i++) {
				AiResultTranslateVo dd = aiResultData.get(i);
				String right = aiResult.replace("右眼", "").split(",")[0].split(":")[0];
				String left = aiResult.replace("左眼", "").split(",")[1].split(":")[0];
				if (dd.getAiCode().equals(right)) {
					resultR = "右眼:" + dd.getDescription();
				}
				if (dd.getAiCode().equals(left)) {
					resultL = "左眼:" + dd.getDescription();
				}
			}
			return resultR + ',' + resultL;
		} else {
			return aiResult;
		}
	}

	private String getDiagnoseResult(Map<String, Object> map) {
		String diseaseLeft = String.valueOf(map.get("diseaseLeft") == null ? "" : map.get("diseaseLeft"));
		String diseaseRight = String.valueOf(map.get("diseaseRight") == null ? "" : map.get("diseaseRight"));
		if ((diseaseLeft == null || diseaseLeft.equals("")) && (diseaseRight == null || diseaseRight.equals(""))) {
			return "未诊断";
		} else {
			String l = "左眼:";
			String r = "右眼:";
			if (diseaseLeft == null || diseaseLeft.equals("")) {
				r += "";
			} else if (diseaseLeft.endsWith("#0")) {
				l += "无明显糖网病变";
			} else if (diseaseLeft.endsWith("#1")) {
				l += "轻度非增殖性糖网病变";
			} else if (diseaseLeft.endsWith("#2")) {
				l += "中度非增殖性糖网病变";
			} else if (diseaseLeft.endsWith("#3")) {
				l += "重度非增殖性糖网病变";
			} else if (diseaseLeft.endsWith("#4")) {
				l += "增殖性糖网病变";
			}
			if (diseaseRight == null || diseaseRight.equals("")) {
				r += "";
			} else if (diseaseRight.endsWith("#0")) {
				r += "无明显糖网病变";
			} else if (diseaseRight.endsWith("#1")) {
				r += "轻度非增殖性糖网病变";
			} else if (diseaseRight.endsWith("#2")) {
				r += "中度非增殖性糖网病变";
			} else if (diseaseRight.endsWith("#3")) {
				r += "重度非增殖性糖网病变";
			} else if (diseaseRight.endsWith("#4")) {
				r += "增殖性糖网病变";
			}
			return r + "," + l;
		}
	}

	/*
	 * public void createProductRow(ProductVo productVo,HSSFWorkbook
	 * workBook,HSSFSheet sheet,String productBatchNo) {
	 * 
	 * int i3 = 0; sheet.setColumnWidth(i3++, 4000); sheet.setColumnWidth(i3++,
	 * 4000); sheet.setColumnWidth(i3++, 7000); sheet.setColumnWidth(i3++, 10000);
	 * sheet.setColumnWidth(i3++, 4000); sheet.setColumnWidth(i3++, 4000);
	 * sheet.setColumnWidth(i3++, 4000); sheet.setColumnWidth(i3++, 4000);
	 * sheet.setColumnWidth(i3++, 4000); sheet.setColumnWidth(i3++, 4000);
	 * sheet.setColumnWidth(i3++, 6000); sheet.setColumnWidth(i3++, 4000);
	 * sheet.setColumnWidth(i3++, 6000); sheet.setColumnWidth(i3++, 6000);
	 * sheet.setColumnWidth(i3++, 4000); sheet.setColumnWidth(i3++, 4000);
	 * sheet.setColumnWidth(i3++, 6000); sheet.setColumnWidth(i3++, 4000);
	 * sheet.setColumnWidth(i3++, 8000);
	 * 
	 * HSSFRow row = sheet.createRow(0); HSSFCell cell = null;
	 * 
	 * HSSFCellStyle cellStyle = workBook.createCellStyle();
	 * 
	 * Font font = workBook.createFont(); font.setFontHeightInPoints((short)14);
	 * //字体大小 font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
	 * cellStyle.setFont(font); // 写入各个字段的名称 int i1=0; cell = row.createCell(i1++);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("产品批号");
	 * cell.setCellStyle(cellStyle);
	 * 
	 * cell = row.createCell(i1++); cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * cell.setCellValue("机台"); cell.setCellStyle(cellStyle);
	 * 
	 * cell = row.createCell(i1++); cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * cell.setCellValue("产品编号"); cell.setCellStyle(cellStyle);
	 * 
	 * cell = row.createCell(i1++); cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * cell.setCellValue("产品名称"); cell.setCellStyle(cellStyle);
	 * 
	 * cell = row.createCell(i1++); cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * cell.setCellValue("产品配方"); cell.setCellStyle(cellStyle);
	 * 
	 * cell = row.createCell(i1++); cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * cell.setCellValue("平均米重(KG)"); cell.setCellStyle(cellStyle);
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC); cell.setCellValue("最小米重(KG)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("最大米重(KG)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("平均直径(mm)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("最小直径(mm)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("最大直径(mm)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("平均壁厚(mm)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("最小壁厚(mm)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("最大壁厚(mm)");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("平均不圆度");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("最小不圆度");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("最大不圆度");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("印字颜色");
	 * 
	 * cell = row.createCell(i1++); cell.setCellStyle(cellStyle);
	 * cell.setCellType(HSSFCell.CELL_TYPE_STRING); cell.setCellValue("印(喷)内容");
	 * 
	 * Font fontRecord = workBook.createFont();
	 * fontRecord.setFontHeightInPoints((short)14); //字体大小
	 * cellStyle.setFont(fontRecord); HSSFRow dataRow = sheet.createRow(1); HSSFCell
	 * hcell = null; int i2=0; hcell = dataRow.createCell(i2++);//产品批号
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * hcell.setCellValue(productBatchNo); hcell.setCellStyle(cellStyle);
	 * 
	 * hcell = dataRow.createCell(i2++);//机台编号
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * hcell.setCellValue(productVo.getProductionLineNo());
	 * hcell.setCellStyle(cellStyle);
	 * 
	 * hcell = dataRow.createCell(i2++);//产品编号
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING);
	 * hcell.setCellValue(productVo.getProductNo()); hcell.setCellStyle(cellStyle);
	 * 
	 * hcell = dataRow.createCell(i2++);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); /// hcell.setCellValue("产品名称");
	 * hcell.setCellValue(productVo.getProductName());
	 * hcell.setCellStyle(cellStyle);
	 * 
	 * hcell = dataRow.createCell(i2++);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); // hcell.setCellValue("产品配方");
	 * hcell.setCellValue(productVo.getIngredientNo());
	 * hcell.setCellStyle(cellStyle);
	 * 
	 * hcell = dataRow.createCell(i2++);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_NUMERIC); //
	 * hcell.setCellValue("平均米重(KG)"); hcell.setCellValue(productVo.getWeight());
	 * hcell.setCellStyle(cellStyle);
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_NUMERIC); //
	 * hcell.setCellValue("最小米重(KG)"); hcell.setCellValue(productVo.getWeightMin());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("最大米重(KG)"); hcell.setCellValue(productVo.getWeightMax());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("平均直径(mm)"); hcell.setCellValue(productVo.getDiameter());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("最小直径(mm)");
	 * hcell.setCellValue(productVo.getMinOutDiameter());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("最大直径(mm)");
	 * hcell.setCellValue(productVo.getMaxOutDiameter());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("平均壁厚(mm)"); hcell.setCellValue(productVo.getThickness());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("最小壁厚(mm)");
	 * hcell.setCellValue(productVo.getThicknessMin());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("最大壁厚(mm)");
	 * hcell.setCellValue(productVo.getThicknessMax());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); // hcell.setCellValue("平均不圆度");
	 * hcell.setCellValue(productVo.getOutOfRoundness());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); // hcell.setCellValue("最小不圆度");
	 * hcell.setCellValue(productVo.getMinOutOfRoundness());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); // hcell.setCellValue("最大不圆度");
	 * hcell.setCellValue(productVo.getMaxOutOfRoundness());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); // hcell.setCellValue("印字颜色");
	 * hcell.setCellValue(productVo.getPrintWordColor());
	 * 
	 * hcell = dataRow.createCell(i2++); hcell.setCellStyle(cellStyle);
	 * hcell.setCellType(HSSFCell.CELL_TYPE_STRING); //
	 * hcell.setCellValue("印(喷)内容"); hcell.setCellValue(productVo.getPrintWord());
	 * 
	 * }
	 */
	public String getState(String basicState) {
		basicState = basicState == null ? "" : basicState;
		if (basicState.equals("0")) {
			return "检验合格";
		} else if (basicState.equals("1")) {
			return "检验不合格";
		}
		return "";
	}
	
	public static void main(String[] args) {
		BigDecimal curData = new BigDecimal(String.valueOf(40));
		double percent = 0;
		percent = curData.divide(new BigDecimal(80)).setScale(4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
		System.out.println(percent);
	}
}
