package com.uniclans.ophthalmology.modules.permission.viewcomponent;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleQueryVo;


public interface IUserRoleViewComponent {

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
	public boolean saveUserRoles(String roleId, String userIds)
			throws Exception;

	/**
	 * 新增用户角色
	 * 
	 * @Title: addUserRoles
	 * @param roleIds
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public boolean addUserRoles(String userId, String roleIds,String deleteIds) throws Exception;

	/**
	 * 根据用户类型获取用户列表
	 * 
	 * @Title: getUsers
	 * @param loginType
	 * @return
	 */
	public List<SystemUserVo> getUsers(String loginType) throws Exception;

	/**
	 * 根据用户id获取角色列表
	 * 
	 * @Title: getUsers
	 * @param loginType
	 * @return
	 */
	public PageFinder<SystemRoleVo> getUserRoleList(String userId,
			Integer pageNo, Integer pageSize) throws Exception;

	public PageFinder<UserRoleQueryVo> pageUserRoleList(
			UserRoleQueryVo userRoleQueryVo, int pageNo, int pageSize)
			throws Exception;

	/**
	 * 删除用户角色
	 * 
	 * @Title: delUserRole
	 * @param id
	 * @return
	 */
	public boolean delUserRole(String id) throws Exception;
	
	/**
	 * 批量删除用户角色
	 * 
	 * @Title: batchDelUserRole
	 * @param id
	 * @return
	 */
	public boolean batchDelUserRole(String ids) throws Exception;
}
