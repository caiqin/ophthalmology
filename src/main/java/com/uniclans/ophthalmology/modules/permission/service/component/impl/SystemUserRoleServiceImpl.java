package com.uniclans.ophthalmology.modules.permission.service.component.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.UserRole;
import com.uniclans.ophthalmology.modules.permission.dao.repository.IUserRoleRepository;
import com.uniclans.ophthalmology.modules.permission.service.common.UserRoleVoConverter;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserRoleService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleVo;

@Service
public class SystemUserRoleServiceImpl implements
		ISystemUserRoleService {
	@Resource
	private EntityManager entityManager;
	@Resource
	private IUserRoleRepository userRoleDao;
	@Resource
	private UserRoleVoConverter userRoleDoConverter;
	@Resource
	private ISystemUserService systemUserServComponent;
	
	@Override
	@Transactional( readOnly = true )
	public List<UserRoleVo> getUserRoleList(String userId) throws Exception
	{
		// 得到用户的角色列表
		List<UserRole> roleList = userRoleDao.findByUserId(userId);
		List<UserRoleVo> roleVoList = new ArrayList<>();
		if( roleList != null && roleList.size() > 0 )
		{
			for(UserRole userRole : roleList)
			{
				roleVoList
						.add(userRoleDoConverter.userRolePoConvertVo(userRole));
			}
		}
		return roleVoList;
	}
	@Override
	@Transactional(readOnly = true)
	public List<UserRoleVo> getUserRoleList(String userId,String roleId) throws Exception {
		// 得到用户的角色列表
		List<UserRoleVo> roleDoList = new ArrayList<UserRoleVo>();
		return roleDoList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SystemUserVo> getAllSystemUser(String loginType)
			throws Exception {
		return systemUserServComponent.getAUsersForType(loginType);
	}

	@Override
	@Transactional
	public boolean saveUserRoles(String roleId, String[] userIds)
			throws Exception {
		if (userIds != null && userIds.length > 0) {
			for (String userId : userIds) {
				// 先删除、后新增
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				userRoleDao.delete(userRole);
				
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				userRoleDao.save(userRole);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public boolean addUserRoles(String userId, String[] roleIds,String [] delIds)
			throws Exception {
		if (delIds != null && delIds.length > 0) {
			for (String roleId : delIds) {
				String hql = "delete from UserRole where userId=:userId and roleId=:roleId";
				Query query = entityManager.createQuery(hql);
				query.setParameter("userId", userId);
				query.setParameter("roleId", roleId);
				query.executeUpdate();
			}
		}
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				userRoleDao.delete(userRole);
				userRole.setEnabled("1");
				userRoleDao.save(userRole);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public PageFinder<UserRoleVo> getUserRoleforPage(String userId,
			Integer pageNo, Integer pageSize) throws Exception {
		List<UserRoleVo> userRoleDoList = new ArrayList<UserRoleVo>();
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		Example<UserRole> example = Example.of(userRole);
		Pageable pageable = new PageRequest(pageNo, pageSize);
		Page<UserRole> pages = userRoleDao.findAll(example, pageable);
		
		PageFinder<UserRole> pageUserRole = new PageFinder<UserRole>(pageNo, pageSize, (int)pages.getTotalElements(), pages.getContent());
		if(pageUserRole!=null&&pageUserRole.getData()!=null){
			for(UserRole userRole1:pageUserRole.getData()){
				userRoleDoList.add(userRoleDoConverter.userRolePoConvertVo(userRole1));
			}
			return new PageFinder<UserRoleVo>(pageNo, pageSize,
					pageUserRole.getRowCount(), userRoleDoList);
		}
		return new PageFinder<UserRoleVo>(pageNo, pageSize, 0,
				userRoleDoList);
	}

	@Override
	@Transactional
	public PageFinder<UserRoleQueryVo> pageFindUserRoles(
			Map<String, String> paramMap, int pageNo, int pageSize)
			throws Exception {
//		return userRoleDao.pageFindUserRoles(paramMap, pageNo, pageSize);
		return null;
	}

	@Override
	@Transactional
	public boolean delUserRole(String userRoleId) throws Exception {
		userRoleDao.delete(userRoleId);
		return true;
	}

	
}
