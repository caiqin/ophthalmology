package com.uniclans.ophthalmology.modules.basedata.service.component;

import java.io.File;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.HospitalIdVo;

/**
 * ai结果描述
 * 
 * @author Stanley
 *
 */
public interface IHospitalIdService {
	public void add(HospitalIdVo hospitalIdVo) throws Exception;

	public void update(HospitalIdVo hospitalIdVo) throws Exception;

	public void delete(String id) throws Exception;

	public PageFinder<HospitalIdVo> pageData(HospitalIdVo hospitalIdVo) throws Exception;

	public void importData(File file)throws Exception;
}
