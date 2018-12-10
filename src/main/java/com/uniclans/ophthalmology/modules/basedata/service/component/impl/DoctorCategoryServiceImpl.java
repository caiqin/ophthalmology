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
import com.uniclans.ophthalmology.modules.basedata.dao.model.entity.DoctorCategory;
import com.uniclans.ophthalmology.modules.basedata.dao.repository.IDoctorCategoryRepository;
import com.uniclans.ophthalmology.modules.basedata.service.component.IDoctorCategoryService;
import com.uniclans.ophthalmology.modules.basedata.service.model.DoctorCategoryVo;

@Component
public class DoctorCategoryServiceImpl implements IDoctorCategoryService {

	@Resource
	private IDoctorCategoryRepository doctorCategoryRepository;
	
	@Override
	@Transactional(readOnly=true)
	public PageFinder<DoctorCategoryVo> pagedDoctorCategorys(DoctorCategoryVo queryVo) throws Exception {
		final String name = StringUtils.parseStrNull(queryVo.getCatName());
		Specification<DoctorCategory> sf = new Specification<DoctorCategory>() {
			@Override
			public Predicate toPredicate(Root<DoctorCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate catNameLike = null;
				if (!name.equals("")) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					catNameLike = cb.like(root.<String>get("catName"), "%" + name + "%");
				}
				if (null != catNameLike) {
					query.where(cb.and(catNameLike));
				}
				return null;
			}
		};
		Pageable pageable = new PageRequest(queryVo.getPageNo(), queryVo.getPageSize());
		Page<DoctorCategory> pages = doctorCategoryRepository.findAll(sf, pageable);		
		PageFinder<DoctorCategory> pageFinder = new PageFinder<DoctorCategory>(queryVo.getPageNo(), queryVo.getPageSize(), (int)pages.getTotalElements(), pages.getContent());
		if (pageFinder.getData() != null && !pageFinder.getData().isEmpty()) {
			List<DoctorCategoryVo> data = new ArrayList<>();
			for (DoctorCategory doctorCategory : pageFinder.getData()) {
				DoctorCategoryVo doctorCategoryVo = new DoctorCategoryVo();
				BeanUtils.deepCopy(doctorCategory, doctorCategoryVo);
				data.add(doctorCategoryVo);
			}
			return new PageFinder<DoctorCategoryVo>(queryVo.getPageNo(), queryVo.getPageSize(), pageFinder.getRowCount(), data);
		}
		return new PageFinder<DoctorCategoryVo>(queryVo.getPageNo(), queryVo.getPageSize(), 0);
	}

	@Override
	@Transactional
	public void addDoctorCategory(DoctorCategoryVo doctorCategoryVo) throws Exception {
		DoctorCategory doctorCategory = new DoctorCategory();
		BeanUtils.deepCopy(doctorCategoryVo, doctorCategory);
		doctorCategory.setCatId(CreatorNoUtil.getCode());
		doctorCategoryRepository.save(doctorCategory);
	}

	@Override
	@Transactional
	public void updateDoctorCategory(DoctorCategoryVo doctorCategoryVo) throws Exception {
		DoctorCategory doctorCategory = doctorCategoryRepository.findOne(doctorCategoryVo.getId());
		String catName = doctorCategoryVo.getCatName();
		String catDesc = doctorCategoryVo.getCatDesc();
		if(LogicUtil.isNotNullAndEmpty(catName)&&!catName.equals(doctorCategory.getCatName())) {
			doctorCategory.setCatName(catName);
		}
		if(LogicUtil.isNotNullAndEmpty(catDesc)&&!catDesc.equals(doctorCategory.getCatDesc())) {
			doctorCategory.setCatDesc(catDesc);
		}
	}

	@Override
	@Transactional
	public List<DoctorCategoryVo> getAll() throws Exception {
		List<DoctorCategory> poList = doctorCategoryRepository.findAll();
		List<DoctorCategoryVo> result = new ArrayList<>();
		if(poList!=null&&!poList.isEmpty()) {
			for (DoctorCategory doctorCategory : poList) {
				DoctorCategoryVo vo = new DoctorCategoryVo();
				BeanUtils.deepCopy(doctorCategory, vo);
				result.add(vo);
			}
		}
		return result;
	}

}
