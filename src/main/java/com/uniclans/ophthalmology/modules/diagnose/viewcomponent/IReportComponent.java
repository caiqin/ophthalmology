package com.uniclans.ophthalmology.modules.diagnose.viewcomponent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.uniclans.ophthalmology.modules.diagnose.service.model.DiagnoseRecordVo;


public interface IReportComponent {
	/**
	 * 生成诊断报表excel
	 * @param priceIndexQueryVo
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook generateWorkbookDiagnose(DiagnoseRecordVo queryVo)
			throws Exception;
}
