package com.uniclans.ophthalmology.modules.permission.service.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniclans.ophthalmology.modules.permission.service.component.IRoleResourceService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemResourceService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserLoginService;
import com.uniclans.ophthalmology.modules.permission.service.component.ISystemUserRoleService;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleVo;

@Service
public class SystemUserLoginServiceImpl implements
		ISystemUserLoginService {

	@Resource
	private ISystemUserRoleService	userRoleServComonent;
	@Resource
	private IRoleResourceService	roleResourceServComonent;
	@Resource
	private ISystemResourceService	resourceServComponent;

	@Override
	@Transactional( readOnly = true )
	public List<SystemResourceVo> getUserResources(SystemUserVo systemUserDo,String rescType)
			throws Exception
	{
		List<SystemResourceVo> resourceList = new ArrayList<SystemResourceVo>();
		String[] roleIds = null;
		// 得到用户的角色列表
		List<UserRoleVo> roleList = userRoleServComonent.getUserRoleList(systemUserDo.getId());
		if( roleList != null && roleList.size() > 0 )
		{
			roleIds = new String[ roleList.size() ];
			for(int i = 0 ;i < roleList.size() ;i++)
			{
				roleIds[ i ] = roleList.get(i).getRoleId();
			}
			// 得到用户的角色权限列表
			List<SystemRoleResourceVo> roleResourceVos = getRolePemissionList(roleIds);
			// 获取权限资源列表
			resourceList = getResources(roleResourceVos,rescType);
		}
		return resourceList;
	}

	private List<SystemRoleResourceVo> getRolePemissionList(String[] roleIds)
			throws Exception
	{
//		List<SysytemRolePermissionVo> pemissionDoList = rolePemissionServComonent
//				.getRolePermissionList(roleIds);
		List<SystemRoleResourceVo> roleResourcesList = roleResourceServComonent.getRoleResourcesList(roleIds);
		return roleResourcesList;
	}

	private List<SystemResourceVo> getResources(
			List<SystemRoleResourceVo> rolePemissionList,String rescType) throws Exception
	{
		List<String> resIds = new ArrayList<String>();
		List<SystemResourceVo> resourceList = new ArrayList<SystemResourceVo>();
		if(rolePemissionList!=null&&!rolePemissionList.isEmpty()){
			for (SystemRoleResourceVo systemRoleResourceVo : rolePemissionList) {
				resIds.add(systemRoleResourceVo.getResourceId());
			}
		}
		
		// 根据资源ID查找资源列表
		if( resIds != null && resIds.size() > 0 )
		{
			resourceList = resourceServComponent.getResourceByIds(resIds,rescType);
		}
		return resourceList;
	}

}
