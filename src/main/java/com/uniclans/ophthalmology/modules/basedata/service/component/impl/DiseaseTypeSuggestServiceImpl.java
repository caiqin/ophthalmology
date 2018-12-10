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
import com.uniclans.ophthalmology.basecomponent.utils.CreatorNoUtil;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DiseaseTypeSuggest;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDiseaseTypeSuggestRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IDiseaseTypeSuggestService;
import com.uniclans.ophthalmology.modules.basedata.service.model.DiseaseTypeSuggestVo;

@Component
public class DiseaseTypeSuggestServiceImpl implements IDiseaseTypeSuggestService {

	@Resource
	private IDiseaseTypeSuggestRepository diseaseTypeSuggestRepository;
	@Override
	@Transactional(readOnly=true)
	public PageFinder<DiseaseTypeSuggestVo> pagedDiseaseTypeSuggests(DiseaseTypeSuggestVo queryVo) throws Exception {
		final String name = StringUtils.parseStrNull(queryVo.getSuggestContent());
		Specification<DiseaseTypeSuggest> sf = new Specification<DiseaseTypeSuggest>() {
			@Override
			public Predicate toPredicate(Root<DiseaseTypeSuggest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate catNameLike = null;
				if (!name.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					catNameLike = cb.like(root.<String>get("suggestContent"), "%" + name + "%");
				}
				if (null != catNameLike) {
					query.where(cb.and(catNameLike));
				}
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<DiseaseTypeSuggest> pages = diseaseTypeSuggestRepository.findAll(sf, pageable);		
		PageFinder<DiseaseTypeSuggest> pageFinder = new PageFinder<DiseaseTypeSuggest>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<DiseaseTypeSuggestVo> data = new ArrayList<DiseaseTypeSuggestVo>();
			for (DiseaseTypeSuggest diseasesuggestType : pageFinder.getData()) {
				DiseaseTypeSuggestVo diseaseTypeSuggestVo = new DiseaseTypeSuggestVo();
				BeanUtils.deepCopy(diseasesuggestType, diseaseTypeSuggestVo);
				data.add(diseaseTypeSuggestVo);
			}
			return new PageFinder<DiseaseTypeSuggestVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<DiseaseTypeSuggestVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public void addDiseaseTypeSuggest(DiseaseTypeSuggestVo diseaseTypeSuggestVo) throws Exception {
		DiseaseTypeSuggest diseaseTypeSuggest = new DiseaseTypeSuggest();
		BeanUtils.deepCopy(diseaseTypeSuggestVo, diseaseTypeSuggest);
		diseaseTypeSuggest.setSuggestId(CreatorNoUtil.getCode());
		diseaseTypeSuggestRepository.save(diseaseTypeSuggest);}

	@Override
	@Transactional
	public void updateDiseaseTypeSuggest(DiseaseTypeSuggestVo diseaseTypeVo) throws Exception {
		DiseaseTypeSuggest diseaseType = diseaseTypeSuggestRepository.findOne(diseaseTypeVo.getId());
		String suggestContent = diseaseTypeVo.getSuggestContent();
		if(LogicUtil.isNotNullAndEmpty(suggestContent)&&!suggestContent.equals(diseaseType.getSuggestContent())) {
			diseaseType.setSuggestContent(suggestContent);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<DiseaseTypeSuggestVo> getByDiseaseTypeId(String diseaseTypeId) throws Exception {
		List<DiseaseTypeSuggest> poList = new ArrayList<>();
		if(!diseaseTypeId.contains(",")) {
			List<DiseaseTypeSuggest> list = diseaseTypeSuggestRepository.findByDiseaseTypeId(diseaseTypeId);
			if(list!=null&&!list.isEmpty()) {
				poList.addAll(list);
			}
		}else {
			String [] params = diseaseTypeId.split(",");
			for (String string : params) {
				List<DiseaseTypeSuggest> list = diseaseTypeSuggestRepository.findByDiseaseTypeId(string);
				if(list!=null&&!list.isEmpty()) {
					poList.addAll(list);
				}
			}
		}
		List<DiseaseTypeSuggestVo> result = new ArrayList<>();
		if(poList!=null&&!poList.isEmpty()) {
			for (DiseaseTypeSuggest po : poList) {
				DiseaseTypeSuggestVo vo = new DiseaseTypeSuggestVo();
				BeanUtils.deepCopy(po, vo);
				result.add(vo);
			}
		}
		return result;
	}

}
