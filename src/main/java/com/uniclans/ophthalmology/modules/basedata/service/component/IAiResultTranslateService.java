package com.uniclans.ophthalmology.modules.basedata.service.component;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.model.AiResultTranslateVo;

/**
 * ai结果描述
 * 
 * @author Stanley
 *
 */
public interface IAiResultTranslateService {
	public void add(AiResultTranslateVo aiResultTranslateVo) throws Exception;

	public void update(AiResultTranslateVo aiResultTranslateVo) throws Exception;

	public void delete(String id) throws Exception;

	public PageFinder<AiResultTranslateVo> pageData(AiResultTranslateVo aiResultTranslateVo) throws Exception;

}
