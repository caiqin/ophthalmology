package com.uniclans.ophthalmology.modules.diagnose.service.component;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.diagnose.service.model.TestRecordVo;

/**
 * 检查记录Service
 * @author Stanley
 *
 */
public interface ITestRecordService {

	/**
	 *  * 分页查询检查记录
	 * @param queryVo
	 * @return
	 * @throws Exception
	 */
	public PageFinder<TestRecordVo> pagedTestRecords(TestRecordVo queryVo,String userNo) throws Exception;
	/**
	 * 
	 * @throws Exception
	 */
	public void addTestRecord(TestRecordVo vo)throws Exception;
	/**
	 * 修改状态
	 * @throws Exception
	 */
	public void updateState()throws Exception;

	public void sendImg2Ai(String testRecordId,String osUrl,String odUrl)throws Exception;
}
