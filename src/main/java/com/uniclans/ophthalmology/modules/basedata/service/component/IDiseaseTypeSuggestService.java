package com.uniclans.ophthalmology.modules.basedata.service.component;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.DiseaseTypeSuggestVo;
/**
 * 疾病种类建议Service
 * @author Stanley
 *
 */
public interface IDiseaseTypeSuggestService {

	/**
	 * 分页查询 疾病种类信息
	 * 
	 * @Title: pagedDiseaseTypes
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<DiseaseTypeSuggestVo> pagedDiseaseTypeSuggests(DiseaseTypeSuggestVo queryVo) throws Exception;
	/**
	 * 新增 疾病种类
	 * @param doctorVo
	 * @throws Exception
	 */
	public void addDiseaseTypeSuggest(DiseaseTypeSuggestVo diseaseTypeVo)throws Exception;
	/**
	 * 修改 疾病种类
	 * @param doctorVo
	 * @throws Exception
	 */
	public void updateDiseaseTypeSuggest(DiseaseTypeSuggestVo diseaseTypeVo)throws Exception;
	/**
	 * 查询所有疾病种类
	 * @param hospitalVo
	 * @return
	 * @throws Exception
	 */
	public List<DiseaseTypeSuggestVo> getByDiseaseTypeId(String diseaseTypeId)throws Exception;
}
