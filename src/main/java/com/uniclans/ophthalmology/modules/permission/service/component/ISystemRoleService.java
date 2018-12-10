package com.uniclans.ophthalmology.modules.permission.service.component;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;


public interface ISystemRoleService {

	/**
	 * 分页查询角色信息
	 * 
	 * @Title: getUsers
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<SystemRoleVo> getRoles(SystemRoleQueryVo queryVo,int pageNo, int pageSize) throws Exception;

	/**
	 * 查找角色列表
	 * 
	 * @Title: getSystemRoleDos
	 * @param systemRoleDo
	 * @return
	 */
	public List<SystemRoleVo> getSystemRoleDos(SystemRoleVo systemRoleDo)
			throws Exception;

	/**
	 * 新增系统角色
	 * 
	 * @Title: addSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	public void addSystemRole(SystemRoleVo systemRoleDo) throws Exception;

	/**
	 * 根据角色ID查找系统角色
	 * 
	 * @Title: getSystemRoleDo
	 * @param systemRoleId
	 * @return
	 */
	public SystemRoleVo getSystemRoleVo(String systemRoleId) throws Exception;

	/**
	 * 更新系统角色
	 * 
	 * @Title: updateSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	public void updateSystemRole(SystemRoleVo systemRoleDo) throws Exception;

	/**
	 * 删除系统角色
	 * 
	 * @Title: delSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	public void delSystemRole(SystemRoleVo systemRoleDo) throws Exception;

	/**
	 * 查询角色信息
	 * 
	 * @Title: getSystemRoleByIds
	 * @Description: TODO
	 * @param roleIds
	 *            角色ID
	 * @return
	 */
	public List<SystemRoleVo> getSystemRoleByIds(List<String> roleIds)
			throws Exception;
	
	/**
	 * 通过角色名称判断角色是否存在
	 * 
	 * @Title: checkRoleExist
	 * @param systemRoleDo
	 * @return boolean
	 */
	public boolean checkRoleExist(SystemRoleVo systemRoleDo) throws Exception;
}
