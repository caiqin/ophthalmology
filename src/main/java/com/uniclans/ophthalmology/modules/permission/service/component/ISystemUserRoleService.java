package com.uniclans.ophthalmology.modules.permission.service.component;

import java.util.List;
import java.util.Map;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleVo;


public interface ISystemUserRoleService {
	/**
	 * 查询登录用户角色信息
	 * 
	 * @Title: getUserRoleList
	 * @Description: TODO
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<UserRoleVo> getUserRoleList(String userId) throws Exception;

	/**
	 * 查询登录用户角色信息
	 * 
	 * @Title: getUserRoleList
	 * @Description: TODO
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<UserRoleVo> getUserRoleList(String userId, String roleId)
			throws Exception;

	public PageFinder<UserRoleVo> getUserRoleforPage(String userId,
			Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 查询所有登录用户信息
	 * 
	 * @Title: getAllSystemUser
	 * @param loginType
	 * @return
	 */
	public List<SystemUserVo> getAllSystemUser(String loginType)
			throws Exception;

	/**
	 * 新增用户角色
	 * 
	 * @Title: saveUserRoles
	 * @param roleId
	 *            角色ID
	 * @param userIds
	 *            用户ID
	 * @return
	 */
	public boolean saveUserRoles(String roleId, String[] userIds)
			throws Exception;

	/**
	 * 新增用户角色
	 * 
	 * @Title: addUserRoles
	 * @param roleId
	 *            角色ID
	 * @param userIds
	 *            用户ID
	 * @return
	 */
	public boolean addUserRoles(String userId, String[] roleIds,String [] delIds)
			throws Exception;

	public PageFinder<UserRoleQueryVo> pageFindUserRoles(
			Map<String, String> paramMap, int pageNo, int pageSize)
			throws Exception;
	
	public boolean delUserRole(String userRoleId) throws Exception;
}
