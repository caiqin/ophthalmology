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
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DiseaseType;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDiseaseTypeRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IDiseaseTypeService;
import com.uniclans.ophthalmology.modules.basedata.service.model.DiseaseTypeVo;

@Component
public class DiseaseTypeServiceImpl implements IDiseaseTypeService {
	
	@Resource
	private IDiseaseTypeRepository diseaseTypeRepository;

	@Override
	@Transactional(readOnly=true)
	public PageFinder<DiseaseTypeVo> pagedDiseaseTypes(DiseaseTypeVo queryVo) throws Exception {
		final String name = StringUtils.parseStrNull(queryVo.getDiseaseTypeName());
		Specification<DiseaseType> sf = new Specification<DiseaseType>() {
			@Override
			public Predicate toPredicate(Root<DiseaseType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate catNameLike = null;
				if (!name.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					catNameLike = cb.like(root.<String>get("diseaseTypeName"), "%" + name + "%");
				}
				if (null != catNameLike) {
					query.where(cb.and(catNameLike));
				}
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<DiseaseType> pages = diseaseTypeRepository.findAll(sf, pageable);		
		PageFinder<DiseaseType> pageFinder = new PageFinder<DiseaseType>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<DiseaseTypeVo> data = new ArrayList<DiseaseTypeVo>();
			for (DiseaseType diseaseType : pageFinder.getData()) {
				DiseaseTypeVo diseaseTypeVo = new DiseaseTypeVo();
				BeanUtils.deepCopy(diseaseType, diseaseTypeVo);
				data.add(diseaseTypeVo);
			}
			return new PageFinder<DiseaseTypeVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<DiseaseTypeVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public void addDiseaseType(DiseaseTypeVo diseaseTypeVo) throws Exception {
		DiseaseType doctorCategory = new DiseaseType();
		BeanUtils.deepCopy(diseaseTypeVo, doctorCategory);
		doctorCategory.setDiseaseTypeId(CreatorNoUtil.getCode());
		diseaseTypeRepository.save(doctorCategory);
	}

	@Override
	@Transactional
	public void updateDiseaseType(DiseaseTypeVo diseaseTypeVo) throws Exception {
		DiseaseType diseaseType = diseaseTypeRepository.findOne(diseaseTypeVo.getId());
		String diseaseTypeName = diseaseTypeVo.getDiseaseTypeName();
		String catDesc = diseaseTypeVo.getDiseaseTypeDesc();
		if(LogicUtil.isNotNullAndEmpty(diseaseTypeName)&&!diseaseTypeName.equals(diseaseType.getDiseaseTypeName())) {
			diseaseType.setDiseaseTypeName(diseaseTypeName);
		}
		if(LogicUtil.isNotNullAndEmpty(catDesc)&&!catDesc.equals(diseaseType.getDiseaseTypeDesc())) {
			diseaseType.setDiseaseTypeDesc(catDesc);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<DiseaseTypeVo> getAll() throws Exception {
		List<DiseaseType> poList = diseaseTypeRepository.findAll();
		List<DiseaseTypeVo> result = new ArrayList<>();
		if(poList!=null&&!poList.isEmpty()) {
			for (DiseaseType po : poList) {
				DiseaseTypeVo vo = new DiseaseTypeVo();
				BeanUtils.deepCopy(po, vo);
				result.add(vo);
			}
		}
		return result;
	}
	
	
}
