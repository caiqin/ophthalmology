package com.uniclans.ophthalmology.modules.diagnose.service.component;

import java.util.List;
import java.util.Map;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DiagnoseRecordVo;

/**
 * 诊断记录Service
 * @author Stanley
 *
 */
public interface IDiagnoseRecordService {
	/**
	 * @Title 分页查询诊断记录
	 * @param queryVo
	 * @return
	 * @throws Exception
	 */
	public PageFinder<DiagnoseRecordVo> pagedDiagnoseRecords(DiagnoseRecordVo queryVo,String userNo) throws Exception;

	public PageFinder<Map<String,Object>> pagedDiagnoseRecordReports(DiagnoseRecordVo queryVo) throws Exception;
	/**
	 * 新增医生诊断记录
	 */
	public String add(DiagnoseRecordVo diagnoseRecordVo)throws Exception;
	/**
	 * 修改医生诊断记录
	 */
	public void update(DiagnoseRecordVo diagnoseRecordVo)throws Exception;
	/**
	 * 查询详情
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getDetail(String testRecordId)throws Exception;
	/**
	 * 查询Ai诊断统计数据
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getAiStatisticData(String startTime,String endTime)throws Exception;
	/**
	 * 查询诊断统计数据
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getDiagnoseStatisticData(String startTime,String endTime)throws Exception;

	public PageFinder<DiagnoseRecordVo> pagedDiagnoseRecords2Export(DiagnoseRecordVo queryVo) throws Exception;
}
