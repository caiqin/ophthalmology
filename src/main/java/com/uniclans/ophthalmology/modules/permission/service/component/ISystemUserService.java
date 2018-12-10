package com.uniclans.ophthalmology.modules.permission.service.component;

import java.util.List;
import java.util.Map;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserQueryVo;


public interface ISystemUserService
{

	/**
	 * 根据登陆名、密码查询用户信息
	 * 
	 * @Title: getSystemUserByName
	 * @Description: TODO
	 * @param loginName
	 *            登录名
	 * @param loginPassword
	 *            密码
	 * @return
	 */
	public SystemUserVo getSystemUserByName(String loginName,
			String loginPassword) throws Exception;

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @Title: getSystemUserById
	 * @Description: TODO
	 * @param loginName
	 *            登录名
	 * @param loginPassword
	 *            密码
	 * @return
	 */
	public SystemUserVo getSystemUserById(String userId) throws Exception;

	/**
	 * 查询所有用户信息
	 * 
	 * @Title: getAllUsers
	 * @Description: TODO
	 * @param loginName
	 *            登录名
	 * @return
	 */
	public List<SystemUserVo> getAllUsers() throws Exception;

	/**
	 * 根据用户类型查询所有用户信息
	 * 
	 * @Title: getAUsersForType
	 * @param loginType
	 *            用户类型
	 * @return
	 */
	public List<SystemUserVo> getAUsersForType(String loginType)
			throws Exception;
	
	
	

	/**
	 * 保存(修改)用户信息
	 * 
	 * @Title: saveOrUpdateUser
	 * @param systemUserDo
	 * @return SystemUserDo
	 */
	public SystemUserVo saveOrUpdateUser(SystemUserVo systemUserDo)
			throws Exception;

	/**
	 * 删除用户信息
	 * 
	 * @Title: deleteUser
	 * @param systemUserDo
	 * @return SystemUserDo
	 */
	public SystemUserVo deleteUser(SystemUserVo systemUserDo) throws Exception;

	/**
	 * 分页查询用户信息
	 * 
	 * @Title: getUsers
	 * @param pageNo
	 * @return pageSize
	 */
	public PageFinder<SystemUserVo> getUsers(UserQueryVo userQueryVo,Integer pageNo,Integer pageSize) throws Exception;

	/**
	 * 通过用户名称判断用户是否存在
	 * 
	 * @Title: getUsers
	 * @param systemUserDo
	 * @return boolean
	 */
	public boolean checkUserExist(SystemUserVo systemUserDo) throws Exception;
	
	/**
	 * 锁定/解锁用户
	 * 
	 * @Title: unLockOrlockUser
	 * @param userId
	 * @return boolean
	 */
	public boolean unLockOrlockUser(String userId) throws Exception;
	
	/**
	 * 修改用户密码
	 * 
	 * @Title: remotePassword
	 * @param systemUserVo
	 * @return boolean
	 */
	public boolean remotePassword(SystemUserVo systemUserDo) throws Exception;
	/**
	 * 修改用户密码
	 * 
	 * @Title: remotePassword
	 * @param systemUserVo
	 * @return boolean
	 */
	public boolean updatePassword(SystemUserVo systemUserDo) throws Exception;
	
	public boolean editPassword(String id,String password) throws Exception ;
	
	/**
	 * 根据用户角色查询该角色所有用户信息
	 * 
	 * @param roleId 角色id
	 *           
	 * @return
	 */
	public Map<String,SystemUserVo> getUsersByRoleId(String roleId)
			throws Exception;

	boolean checkMobileExist(SystemUserVo systemUserDo) throws Exception;
	/**
	 * 管理医生
	 * @param systemUserVo
	 * @throws Exception
	 */
	public void relation(SystemUserVo systemUserVo) throws Exception;
}
