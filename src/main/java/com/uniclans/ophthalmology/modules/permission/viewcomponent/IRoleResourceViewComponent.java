package com.uniclans.ophthalmology.modules.permission.viewcomponent;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.RoleResourceQueryVo;


public interface IRoleResourceViewComponent {

	/**
	 * 新增角色权限
	 * 
	 * @Title: saveRolesPermission
	 * @param roleId
	 *            角色ID
	 * @param perIds
	 *            权限ID
	 * @return
	 */
	public boolean saveRolesResource(String roleId, String perIds,String delIds)
			throws Exception;


	/**
	 * 根据角色ID查询所有权限信息
	 * 
	 * @Title: getPermissions
	 * @param queryVo
	 *            查询对象
	 * @return
	 */
	public PageFinder<RoleResourceQueryVo> getResources(
			RoleResourceQueryVo queryVo, Integer pageNo, Integer pageSize)
			throws Exception;

	/**
	 * 删除角色权限
	 * 
	 * @Title: delRolesPermission
	 * @param rolePerId
	 *            角色权限ID
	 * @return
	 */
	public boolean delRolesResource(String rolePerId) throws Exception;
	
	/**
	 * 批量删除角色权限
	 * 
	 * @Title: batchDelRolesPermission
	 * @param ids
	 *            角色权限ID集合
	 * @return
	 */
	public boolean batchDelRolesResource(String ids) throws Exception;
}
