package com.uniclans.ophthalmology.modules.basedata.service.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.AiResultTranslate;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DiseaseType;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IAiResultTranslateRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IAiResultTranslateService;
import com.uniclans.ophthalmology.modules.basedata.service.model.AiResultTranslateVo;
import com.uniclans.ophthalmology.modules.basedata.service.model.DiseaseTypeVo;

@Component
public class AiResultTranslateServiceImpl implements IAiResultTranslateService {

	@Resource
	private IAiResultTranslateRepository aiResultTranslateRepository;

	@Override
	@Transactional
	public void add(AiResultTranslateVo aiResultTranslateVo) throws Exception {
		AiResultTranslate entity = new AiResultTranslate();
		BeanUtils.deepCopy(aiResultTranslateVo, entity);
		aiResultTranslateRepository.save(entity);
	}

	@Override
	@Transactional
	public void update(AiResultTranslateVo aiResultTranslateVo) throws Exception {
		AiResultTranslate aiResultTranslate = aiResultTranslateRepository.findOne(aiResultTranslateVo.getId());
		String desc = aiResultTranslateVo.getDescription();
		if(LogicUtil.isNotNullAndEmpty(desc)&&!desc.equals(aiResultTranslate.getDescription())) {
			aiResultTranslate.setDescription(desc);
		}
	}

	@Override
	@Transactional
	public void delete(String id) throws Exception {
		aiResultTranslateRepository.delete(id);
	}

	@Override
	@Transactional(readOnly=true)
	public PageFinder<AiResultTranslateVo> pageData(AiResultTranslateVo queryVo) throws Exception {
		Specification<AiResultTranslate> sf = new Specification<AiResultTranslate>() {
			@Override
			public Predicate toPredicate(Root<AiResultTranslate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<AiResultTranslate> pages = aiResultTranslateRepository.findAll(sf, pageable);		
		PageFinder<AiResultTranslate> pageFinder = new PageFinder<AiResultTranslate>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<AiResultTranslateVo> data = new ArrayList<AiResultTranslateVo>();
			for (AiResultTranslate diseaseType : pageFinder.getData()) {
				AiResultTranslateVo diseaseTypeVo = new AiResultTranslateVo();
				BeanUtils.deepCopy(diseaseType, diseaseTypeVo);
				data.add(diseaseTypeVo);
			}
			return new PageFinder<AiResultTranslateVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<AiResultTranslateVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

}
