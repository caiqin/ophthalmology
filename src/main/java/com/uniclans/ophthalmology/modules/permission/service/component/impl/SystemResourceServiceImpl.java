package com.uniclans.ophthalmology.modules.permission.service.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.constans.SystemManagerConstants;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemResource;
import com.uniclans.ophthalmology.modules.permission.dao.repository.ISystemResourceRepository;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemResourceVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemResourceService;
import com.uniclans.ophthalmology.modules.permission.service.model.ResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;

@Service
public class SystemResourceServiceImpl implements ISystemResourceService {
	@Resource
	private EntityManager entityManager;
	@Resource
	private ISystemResourceRepository resourceDao;
	@Resource
	private SystemResourceVoConverter resourceDoConverter;

	@Override
	@Transactional(readOnly = true)
	public List<SystemResourceVo> getResourceByIds(List<String> resIds, String rescType) throws Exception {
		List<SystemResourceVo> doList = new ArrayList<SystemResourceVo>();
		String hql = "";
		StringBuffer str = new StringBuffer();
		if (resIds == null || resIds.size() <= 0) {
			return null;
		}
		for (int i = 0; i < resIds.size(); i++) {
			if (resIds.get(i) != null && !resIds.get(i).equals("")) {
				str.append("'" + resIds.get(i) + "'").append(",");
			}
		}
		str = str.length() > 1 ? str.deleteCharAt(str.length() - 1) : str;
		hql = "from SystemResource where id in (" + str + ") and deleteFlag=0 ";
		if (rescType != null && !rescType.equals("")) {
			hql += "and resType='" + rescType + "'";
		}
		hql = hql + " order by seqNum asc";
		Query query = entityManager.createQuery(hql);
		List<SystemResource> resourceList = query.getResultList();
		if (resourceList != null && resourceList.size() > 0) {
			for (SystemResource resource : resourceList) {
				doList.add(resourceDoConverter.resourcePo2Vo(resource));
			}
		}

		return doList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemResourceVo> getResourceByIds(List<String> resIds) throws Exception {
		List<SystemResourceVo> doList = new ArrayList<SystemResourceVo>();
		String[] ids = new String[resIds.size()];
		for (int i = 0; i < resIds.size(); i++) {
			ids[i] = resIds.get(i);
		}
		List<SystemResource> resourceList = resourceDao.findByIdIn(resIds);
		if (resourceList != null && resourceList.size() > 0) {
			for (SystemResource resource : resourceList) {
				doList.add(resourceDoConverter.resourcePo2Vo(resource));
			}
		}

		return doList;
	}

	@Override
	@Transactional
	public void addSystemResource(SystemResourceVo resourceDo) throws Exception {
		SystemResource systemResource = resourceDoConverter.resourceVo2Po(resourceDo);
		resourceDao.save(systemResource);
	}

	@Override
	@Transactional(readOnly = true)
	public PageFinder<SystemResourceVo> getResourcesByPage(ResourceQueryVo resourceQueryVo, int pageNo, int pageSize)
			throws Exception {
		final String startDate = StringUtils.parseStrNull(resourceQueryVo.getStartDate());
		final String endDate = StringUtils.parseStrNull(resourceQueryVo.getEndDate());
		final String resourceName = StringUtils.parseStrNull(resourceQueryVo.getResName());
		Specification<SystemResource> sf = new Specification<SystemResource>() {
			@Override
			public Predicate toPredicate(Root<SystemResource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate p1 = cb.equal(root.<String>get("deleteFlag"), SystemManagerConstants.DELETE_FLAG_0);
				Predicate stuNameLike = null;
				if (StringUtils.isNotEmpty(resourceName)) {
					// 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
					stuNameLike = cb.like(root.<String>get("resName"), "%" + resourceName + "%");
				}

				Predicate clazzNameLike = null;
				if (!startDate.equals("") && !endDate.equals("")) {
					clazzNameLike = cb.between(root.<String>get("createTime"), startDate, endDate);
				} else if (!startDate.equals("") && endDate.equals("")) {
					clazzNameLike = cb.ge(root.<Long>get("createTime"), DateUtils.parseDate(startDate).getTime());
				} else if (startDate.equals("") && !endDate.equals("")) {
					clazzNameLike = cb.le(root.<Long>get("createTime"), DateUtils.parseDate(endDate).getTime());
				}
				
				query.where(cb.and(p1));
				if (null != stuNameLike)
					query.where(cb.and(p1, stuNameLike));
				if (null != clazzNameLike)
					query.where(cb.and(p1, stuNameLike, clazzNameLike));
				
				return null;
			}
		};
		Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.DESC, "createTime");
		Page<SystemResource> pages = resourceDao.findAll(sf, pageable);
		PageFinder<SystemResource> pageFinder = new PageFinder<SystemResource>(pageNo, pageSize,
				(int) pages.getTotalElements(), pages.getContent());
		List<SystemResource> resourceList = pageFinder.getData();
		if (resourceList != null && !resourceList.isEmpty()) {
			PageFinder<SystemResourceVo> page = new PageFinder<SystemResourceVo>(pageNo, pageSize,
					pageFinder.getRowCount());
			List<SystemResourceVo> list = resourceDoConverter.resourcePo2VoList(resourceList);
			page.setData(list);
			return page;
		}
		return new PageFinder<SystemResourceVo>(pageNo, pageSize, pageFinder.getRowCount());
	}

	@Override
	@Transactional
	public void updateResource(SystemResourceVo resourceDo) throws Exception {
		SystemResource systemResource = resourceDao.findOne(resourceDo.getId());
		systemResource.setRemark(resourceDo.getRemark());
		systemResource.setResName(resourceDo.getResName());
		systemResource.setResUrl(resourceDo.getResUrl());
		systemResource.setResCode(resourceDo.getResCode());
		systemResource.setSeqNum(resourceDo.getSeqNum());
		systemResource.setIsShow(resourceDo.getIsShow());
		systemResource.setLevel(resourceDo.getLevel());
		systemResource.setResType(resourceDo.getResType());
		if (LogicUtil.isNotNullAndEmpty(resourceDo.getDeleteFlag())) {
			systemResource.setDeleteFlag(resourceDo.getDeleteFlag());
		}
		systemResource.setSupperResId(resourceDo.getSupperResId());
	}

	@Override
	@Transactional(readOnly = true)
	public SystemResourceVo getResourceById(String resourceId) throws Exception {
		SystemResource resource = resourceDao.findOne(resourceId);
		SystemResourceVo resourceVo = resourceDoConverter.resourcePo2Vo(resource);
		if (resource.getSupperResId() != null && !resource.getSupperResId().equals("")) {
			SystemResource superResource = resourceDao.findOne(resource.getSupperResId());
			if (superResource != null) {
				resourceVo.setSupperResName(superResource.getResName());
			} else {
				resourceVo.setSupperResName("#");
			}
		}

		return resourceVo;
	}

	@Override
	@Transactional
	public boolean checkSourceExist(SystemResourceVo resourceDo) throws Exception {
		String resName = resourceDo.getResName();
		if (resName != null && !"".equals(resName)) {
			SystemResource customer = new SystemResource();
			customer.setResName(resName);

			// 创建实例
			Example<SystemResource> example = Example.of(customer);
			return resourceDao.exists(example);
		}
		return false;
	}

}
