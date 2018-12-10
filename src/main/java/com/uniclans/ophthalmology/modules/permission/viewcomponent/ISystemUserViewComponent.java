package com.uniclans.ophthalmology.modules.permission.viewcomponent;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserQueryVo;

public interface ISystemUserViewComponent
{

	/**
	 * 登陆时查询用户资源信息
	 * 
	 * @Title: getSystemUserByName
	 * @param loginName
	 *            登陆用户名
	 * @param loginPassword
	 *            登陆密码
	 * @return SystemUserVo
	 */
	public SystemUserVo getSystemUserByName(String loginName,
			String loginPassword) throws Exception;

	/**
	 * 通过Id查询用户资源信息
	 * 
	 * @Title: getSystemUserById
	 * @param userId
	 *            用户ID
	 * @return SystemUserVo
	 */
	public SystemUserVo getSystemUserById(String userId) throws Exception;

	/**
	 * 添加用户
	 * 
	 * @Title: addUser
	 * @param systemUserVo
	 * @return String
	 */
	public boolean addUser(SystemUserVo systemUserVo) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @Title: deleteUser
	 * @param systemUserVo
	 * @return SystemUserVo
	 */
	public SystemUserVo deleteUser(SystemUserVo systemUserVo) throws Exception;

	/**
	 * 更新用户
	 * 
	 * @Title: updateUser
	 * @param systemUserVo
	 * @return SystemUserVo
	 */
	public SystemUserVo updateUser(SystemUserVo systemUserVo) throws Exception;

	/**
	 * 分页查询用户
	 * 
	 * @Title: getUsers
	 * @param userViewCondition
	 * @param pageNo
	 * @param pageSize
	 * @return SystemUserVo
	 */
	public PageFinder<SystemUserVo> getUsers(UserQueryVo userQueryVo,
			Integer pageNo,Integer pageSize) throws Exception;

	/**
	 * 通过用户名称判断用户是否存在
	 * 
	 * @Title: getUsers
	 * @param systemUserVo
	 * @return boolean
	 */
	public boolean checkUserExist(SystemUserVo systemUserVo) throws Exception;
	
	/**
	 * 锁定用户
	 * 
	 * @Title: lockUser
	 * @param userId
	 * @return boolean
	 */
	public boolean lockUser(String userId) throws Exception;
	
	/**
	 * 解锁用户
	 * 
	 * @Title: unLockUser
	 * @param userId
	 * @return boolean
	 */
	public boolean unLockUser(String userId) throws Exception;
	
	
	/**
	 * 修改用户密码
	 * 
	 * @Title: remotePassword
	 * @param systemUserVo
	 * @return boolean
	 */
	public boolean remotePassword(SystemUserVo systemUserVo) throws Exception;
	/**
	 * 修改用户密码
	 * 
	 * @Title: remotePassword
	 * @param systemUserVo
	 * @return boolean
	 */
	public boolean updatePassword(SystemUserVo systemUserVo) throws Exception;
	
	/**
	 * 关联医生
	 * 
	 * @Title: remotePassword
	 * @param systemUserVo
	 * @return boolean
	 */
	public void relation(SystemUserVo systemUserVo) throws Exception;
}
