package com.uniclans.ophthalmology.modules.permission.service.common;

import org.springframework.stereotype.Component;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.UserRole;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleVo;

@Component
public class UserRoleVoConverter
{

	/**
	 * @methodName：userRolePoConverDo
	 * @Description: 用户角色信息Po转Do
	 * @param userRole
	 * @return
	 */
	public UserRoleVo userRolePoConvertVo(UserRole userRole)
	{
		UserRoleVo userRoleVo = null;
		if( LogicUtil.isNotNull(userRole) )
		{
			userRoleVo = new UserRoleVo();
			BeanUtils.deepCopy(userRole,userRoleVo);
		}
		return userRoleVo;

	}

}
