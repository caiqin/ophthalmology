package com.uniclans.ophthalmology.modules.basedata.service.component;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.DoctorCategoryVo;
/**
 * 医生专长Service
 * @author Stanley
 *
 */
public interface IDoctorCategoryService {

	/**
	 * 分页查询 医生专长信息
	 * 
	 * @Title: getUsers
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<DoctorCategoryVo> pagedDoctorCategorys(DoctorCategoryVo queryVo) throws Exception;
	/**
	 * 新增 医生专长
	 * @param doctorVo
	 * @throws Exception
	 */
	public void addDoctorCategory(DoctorCategoryVo doctorCategoryVo)throws Exception;
	/**
	 * 修改 医生专长
	 * @param doctorVo
	 * @throws Exception
	 */
	public void updateDoctorCategory(DoctorCategoryVo doctorCategoryVo)throws Exception;
	/**
	 * 查询所有医生专长
	 * @param hospitalVo
	 * @return
	 * @throws Exception
	 */
	public List<DoctorCategoryVo> getAll()throws Exception;
}
