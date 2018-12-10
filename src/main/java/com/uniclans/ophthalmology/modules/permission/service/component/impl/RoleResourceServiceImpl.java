package com.uniclans.ophthalmology.modules.permission.service.component.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.basecomponent.utils.StringUtils;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.RoleResource;
import com.uniclans.ophthalmology.modules.permission.dao.repository.IRoleResourceRepository;
import com.uniclans.ophthalmology.modules.permission.service.common.RoleResourceVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.common.SystemResourceVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.IRoleResourceService;
import com.uniclans.ophthalmology.modules.permission.service.model.RoleResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleResourceVo;

@Service
public class RoleResourceServiceImpl implements
		IRoleResourceService {
	@Resource
	private EntityManager entityManager;
	@Resource
	private IRoleResourceRepository roleResourceRepository;
	@Resource
	private RoleResourceVoConverter roleResourceDoConverter;
	@Resource
	private SystemResourceVoConverter systemResourceVoConverter;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true,rollbackFor={Exception.class})
	public List<SystemRoleResourceVo> getRoleResourcesList(String[] roleId)throws Exception {
		List<SystemRoleResourceVo> pemissionDoList = new ArrayList<SystemRoleResourceVo>();
		List<String> roleIds = new ArrayList<>();
		if(roleId!=null&&roleId.length!=0) {
			for (String id : roleId) {
				roleIds.add(id);
			}
		}
        //创建实例
		 List<RoleResource> roleResources= roleResourceRepository.findByRoleIdIn(roleIds);
		if (roleResources != null && roleResources.size() > 0) {
			for (RoleResource rolePemission : roleResources) {
				pemissionDoList.add(roleResourceDoConverter
						.roleResourcePoConverVo(rolePemission));
			}
		}
		return pemissionDoList;
	}


	@Override
	@Transactional
	public boolean saveRoleResources(String roleId, String[] perIds,String[] delId)
			throws Exception {
		if (perIds != null && perIds.length > 0) {
			for (String perId : perIds) {
					RoleResource roleResource = new RoleResource();
					roleResource.setResourceId(perId);
					roleResource.setRoleId(roleId);
					roleResource.setEnabled(1);
					roleResourceRepository.save(roleResource);
			}
		}
		if(delId!=null&&delId.length>0) {
			for (String id : delId) {
				roleResourceRepository.delete(roleId, id);
			}
		}
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true,rollbackFor={Exception.class})
	public PageFinder<SystemRoleResourceVo> getRoleResourcesByRole(
			String roleId, Integer pageNo, Integer pageSize) throws Exception {
		List<SystemRoleResourceVo> rolePerDoList = new ArrayList<SystemRoleResourceVo>();
		Pageable pageable = new PageRequest(pageNo, pageSize);
		Page<RoleResource> pages = roleResourceRepository.findByRoleId(roleId, pageable);
		PageFinder<RoleResource> rolePerPageFinger = new PageFinder<RoleResource>(pages.getNumber(),pages.getSize(),(int)pages.getTotalElements(), pages.getContent());
		if (rolePerPageFinger != null && rolePerPageFinger.getData() != null) {
			for (RoleResource roleResource : rolePerPageFinger.getData()) {
				rolePerDoList.add(roleResourceDoConverter
						.roleResourcePoConverVo(roleResource));
			}
			return new PageFinder<SystemRoleResourceVo>(pageNo, pageSize,
					rolePerPageFinger.getRowCount(), rolePerDoList);
		}

		return new PageFinder<SystemRoleResourceVo>(pageNo, pageSize, 0,
				rolePerDoList);
	}

	@Override
	@Transactional
	public PageFinder<RoleResourceQueryVo> pageRoleResources(
			Map<String, String> paramMap, int pageNo, int pageSize)
			throws Exception {
		String roleName = paramMap.get("roleName");
		String resourceName = paramMap.get("resourceName");
		String roleId = paramMap.get("roleId");
		String resourceId = paramMap.get("resourceId");
		Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");

		StringBuffer countString = new StringBuffer("select count(*) ");
		StringBuffer from = new StringBuffer(
				"FROM tbl_system_role_resource p, tbl_system_resource u,tbl_system_role r ");
		StringBuffer where = new StringBuffer(
				"WHERE p.role_id=r.id AND p.resource_id=u.id  ");
		if (StringUtils.isNotBlank(resourceName)) {
			where.append("AND u.res_name LIKE :resourceName ");
		}
		if (StringUtils.isNotBlank(roleName)) {
			where.append("AND r.role_name LIKE :roleName ");
		}
		if (StringUtils.isNotBlank(roleId)) {
			where.append("AND r.id = :roleId ");
		}
		if (StringUtils.isNotBlank(resourceId)) {
			where.append("AND u.id = :resourceId ");
		}
		
		Query query = entityManager.createNativeQuery(countString.append(from).append(where).toString());
		if (StringUtils.isNotBlank(resourceName)) {
			query.setParameter("resourceName", "%" + resourceName + "%");
		}
		if (StringUtils.isNotBlank(roleName)) {
			query.setParameter("roleName", "%" + roleName + "%");
		}
		if (StringUtils.isNotBlank(roleId)) {
			query.setParameter("roleId", roleId);
		}
		if (StringUtils.isNotBlank(resourceId)) {
			query.setParameter("resourceId", resourceId);
		}
		Object count = query.getSingleResult();
		int totalRows = Integer.parseInt(String.valueOf(count));
		PageFinder<RoleResourceQueryVo> pageFinder = new PageFinder<RoleResourceQueryVo>(
				pageNo, pageSize, totalRows);
		if(totalRows>0) {
			StringBuffer dataString = new StringBuffer(
					"SELECT 	p.id id,r.id roleId,r.role_name roleName,u.id resourceId,u.res_name resourceName,r.is_valid enabled,u.create_time createTime,u.res_type resType ");
			dataString.append(from);
			dataString.append(where);
			dataString.append(" ORDER BY r.id desc ");
			
			Query querySql = entityManager.createNativeQuery(dataString.toString());
			if (StringUtils.isNotBlank(resourceName)) {
				querySql.setParameter("resourceName", "%" + resourceName
						+ "%");
			}
			if (StringUtils.isNotBlank(roleName)) {
				querySql.setParameter("roleName", "%" + roleName + "%");
			}
			if (StringUtils.isNotBlank(roleId)) {
				querySql.setParameter("roleId", roleId);
			}
			if (StringUtils.isNotBlank(resourceId)) {
				query.setParameter("resourceId", resourceId);
			}
			querySql.setMaxResults(pageFinder.getPageNo());
			querySql.setFirstResult(pageFinder.getStartOfPage());
			List<Object[]> orderQueryResults = querySql.getResultList();
			List<RoleResourceQueryVo> data = new ArrayList<>();
			if(orderQueryResults!=null&&!orderQueryResults.isEmpty()) {
				for (Object[] list : orderQueryResults) {
					RoleResourceQueryVo vo = new RoleResourceQueryVo();
					vo.setId(String.valueOf(list[0]));
					vo.setRoleId(String.valueOf(list[1]));
					vo.setRoleName(String.valueOf(list[2]));
					vo.setResourceId(String.valueOf(list[3]));
					vo.setResourceName(String.valueOf(list[4]));
					vo.setEnabled(String.valueOf(list[5]));
					vo.setCreateTime(DateUtils.parseTime(String.valueOf(list[6])));
					vo.setResType(String.valueOf(list[7]));
					data.add(vo);
				}
			}
			
			pageFinder.setData(data);
		}
		
		
		return pageFinder;
	}

	@Override
	@Transactional
	public boolean delRoleResource(String rolePerId) {
		try {
			roleResourceRepository.delete(rolePerId);
		} catch (Exception e) {
			return false;
		}
		 return true;
	}
}
