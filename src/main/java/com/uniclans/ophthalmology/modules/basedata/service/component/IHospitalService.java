package com.uniclans.ophthalmology.modules.basedata.service.component;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.HospitalVo;
/**
 * 医院Service
 * @author Stanley
 *
 */
public interface IHospitalService {

	/**
	 * 分页查询 医院信息
	 * 
	 * @Title: getUsers
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<HospitalVo> pagedHospitals(HospitalVo queryVo) throws Exception;
	/**
	 * 新增 医院
	 * @param doctorVo
	 * @throws Exception
	 */
	public void addHospital(HospitalVo hospitalVo)throws Exception;
	/**
	 * 修改 医院
	 * @param doctorVo
	 * @throws Exception
	 */
	public void updateHospital(HospitalVo hospitalVo)throws Exception;
	/**
	 * 查询所有医院
	 * @param hospitalVo
	 * @return
	 * @throws Exception
	 */
	public List<HospitalVo> getAll()throws Exception;
	/**
	 * 查询医院
	 * @param hospitalVo
	 * @return
	 * @throws Exception
	 */
	public HospitalVo getHospital(String hospitalId)throws Exception;
	/**
	 * 生成二维码
	 * @param hospitalVo
	 * @throws Exception
	 */
	public void createQrCode(HospitalVo hospitalVo)throws Exception;
	/**
	 * 启用禁用二维码
	 * @param hospitalVo
	 * @throws Exception
	 */
	public void enableQr(HospitalVo hospitalVo)throws Exception;

}
