package com.uniclans.ophthalmology.modules.basedata.service.component;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.PatientVo;
/**
 * 患者Service
 * @author Stanley
 *
 */
public interface IPatientService {

	/**
	 * 分页查询患者信息
	 * 
	 * @Title: pagedPatients
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<PatientVo> pagedPatients(PatientVo queryVo,String userNo) throws Exception;
	/**
	 * 新增患者
	 * @param patientVo
	 * @throws Exception
	 */
	public void addPatient(PatientVo patientVo)throws Exception;
	/**
	 * 修改患者信息
	 * @param patientVo
	 * @throws Exception
	 */
	public void updatePatient(PatientVo patientVo)throws Exception;
}
