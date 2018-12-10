package com.uniclans.ophthalmology.modules.basedata.service.component;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.DoctorVo;
/**
 * 医生Service
 * @author Stanley
 *
 */
public interface IDoctorService {

	/**
	 * 分页查询医生信息
	 * 
	 * @Title: getUsers
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<DoctorVo> pagedDoctors(DoctorVo queryVo) throws Exception;
	/**
	 * 新增医生
	 * @param doctorVo
	 * @throws Exception
	 */
	public void addDoctor(DoctorVo doctorVo)throws Exception;
	/**
	 * 修改医生
	 * @param doctorVo
	 * @throws Exception
	 */
	public void updateDoctor(DoctorVo doctorVo)throws Exception;
	/**
	 * 查询医生
	 * @param doctorVo
	 * @throws Exception
	 */
	public DoctorVo getDoctor(String id)throws Exception;
}
