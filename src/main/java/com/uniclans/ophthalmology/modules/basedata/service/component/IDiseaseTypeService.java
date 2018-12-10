package com.uniclans.ophthalmology.modules.basedata.service.component;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.DiseaseTypeVo;
/**
 * 疾病种类Service
 * @author Stanley
 *
 */
public interface IDiseaseTypeService {

	/**
	 * 分页查询 疾病种类信息
	 * 
	 * @Title: pagedDiseaseTypes
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<DiseaseTypeVo> pagedDiseaseTypes(DiseaseTypeVo queryVo) throws Exception;
	/**
	 * 新增 疾病种类
	 * @param doctorVo
	 * @throws Exception
	 */
	public void addDiseaseType(DiseaseTypeVo diseaseTypeVo)throws Exception;
	/**
	 * 修改 疾病种类
	 * @param doctorVo
	 * @throws Exception
	 */
	public void updateDiseaseType(DiseaseTypeVo diseaseTypeVo)throws Exception;
	/**
	 * 查询所有疾病种类
	 * @param hospitalVo
	 * @return
	 * @throws Exception
	 */
	public List<DiseaseTypeVo> getAll()throws Exception;
}
