package com.uniclans.ophthalmology.modules.permission.viewcomponent;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;


public interface ISystemRoleViewComponent
{

	/**
	 * 分页查找角色列表
	 * 
	 * @Title: getRoles
	 * @param viewCondition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageFinder<SystemRoleVo> getRoles(SystemRoleQueryVo queryVo,
			Integer pageNo,Integer pageSize) throws Exception;

	/**
	 * 查找角色列表
	 * 
	 * @Title: getSystemRoleVos
	 * @param systemRoleVo
	 * @return
	 */
	public List<SystemRoleVo> getSystemRoleVos(SystemRoleVo systemRoleVo)
			throws Exception;

	/**
	 * 新增系统角色
	 * 
	 * @Title: addSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	public boolean addSystemRole(SystemRoleVo systemRoleVo) throws Exception;

	/**
	 * 根据角色ID查找系统角色
	 * 
	 * @Title: getSystemRoleVo
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
	public void updateSystemRole(SystemRoleVo systemRoleVo) throws Exception;

	/**
	 * 删除系统角色
	 * 
	 * @Title: delSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	public boolean delSystemRole(SystemRoleVo systemRoleVo) throws Exception;
	
	public boolean isHoldUserOrPermi(String systemRoleId)throws Exception;
	
	public boolean remoteValid(String systemRoleId)throws Exception;
}
