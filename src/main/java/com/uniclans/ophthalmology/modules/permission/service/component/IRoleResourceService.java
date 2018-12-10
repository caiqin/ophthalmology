package com.uniclans.ophthalmology.modules.permission.service.component;

import java.util.List;
import java.util.Map;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.RoleResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleResourceVo;


public interface IRoleResourceService {

	/**
	 * 查询角色ID角色权限信息
	 * 
	 * @Title: getRolePemissionList
	 * @Description: TODO
	 * @param roleIds
	 *            角色ID
	 * @return
	 */
	public List<SystemRoleResourceVo> getRoleResourcesList(String[] roleIds)
			throws Exception;


	/**
	 * 新增角色权限
	 * 
	 * @Title: saveRolePrmission
	 * @param roleId
	 *            角色ID
	 * @param perIds
	 *            权限ID
	 * @return
	 */
	public boolean saveRoleResources(String roleId, String[] perIds,String[] delId)
			throws Exception;

	public PageFinder<SystemRoleResourceVo> getRoleResourcesByRole(
			String roleId, Integer pageNo, Integer pageSize) throws Exception;

	public PageFinder<RoleResourceQueryVo> pageRoleResources(
			Map<String, String> paramMap, int pageNo, int pageSize)
			throws Exception;

	/**
	 * 删除角色权限
	 * 
	 * @Title: delRolePrmission
	 * @param rolePerId
	 *            角色权限ID
	 * @return
	 */
	public boolean delRoleResource(String rolePerId) throws Exception;
}
