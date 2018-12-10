package com.uniclans.ophthalmology.modules.basedata.viewcomponent.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;

import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.component.IAiResultTranslateService;
import com.uniclans.ophthalmology.modules.basedata.viewcomponent.IExportComponent;
import com.uniclans.ophthalmology.modules.diagnose.service.component.IDiagnoseRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.component.ITestRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DiagnoseRecordVo;

@Service
public class ExportCheckViewComponentImpl implements IExportComponent {
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

		int i3 = 0;
		sheet.setColumnWidth(i3++, 7000);
		sheet.setColumnWidth(i3++, 7000);
		sheet.setColumnWidth(i3++, 7000);
		sheet.setColumnWidth(i3++, 7000);

		PageFinder<DiagnoseRecordVo> priceIndexes = diagnoseRecordService.pagedDiagnoseRecords2Export(queryVo);
		List<DiagnoseRecordVo> list = priceIndexes.getData();
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

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Font fontRecord = workbook.createFont();
				fontRecord.setFontHeightInPoints((short) 14); // 字体大小
				cellStyle.setFont(fontRecord);
				HSSFRow dataRow = sheet.createRow(i + 1);
				DiagnoseRecordVo rowData = list.get(i);

				int i2 = 0;
				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("患者姓名");
				cell.setCellValue(rowData.getPatientName());
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("医院名称");
				cell.setCellValue(rowData.getHospitalName());
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("医生");
				cell.setCellValue(rowData.getDoctorName());
				cell.setCellStyle(cellStyle);

				cell = dataRow.createCell(i2++);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// cell.setCellValue("诊断时间");
				cell.setCellValue(DateUtils.formatTime(rowData.getDiagnoseTime()));
				cell.setCellStyle(cellStyle);
				
			}
		}
		return workbook;
	}




	
	public static void main(String[] args) {
		BigDecimal curData = new BigDecimal(String.valueOf(40));
		double percent = 0;
		percent = curData.divide(new BigDecimal(80)).setScale(4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
		System.out.println(percent);
	}
}
