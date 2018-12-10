package com.uniclans.ophthalmology.modules.permission.service.common;

import org.springframework.stereotype.Component;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.RoleResource;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleResourceVo;

@Component
public class RoleResourceVoConverter
{

	/**
	 * @methodName：rolePemissionPoConverVo
	 * @Description: 角色权限信息Po转Vo
	 * @param roleResource
	 * @return
	 */
	public SystemRoleResourceVo roleResourcePoConverVo(
			RoleResource roleResource)
	{
		SystemRoleResourceVo roleResourceVo = null;
		if( LogicUtil.isNotNull(roleResource) )
		{
			roleResourceVo = new SystemRoleResourceVo();
			BeanUtils.deepCopy(roleResource,roleResourceVo);
		}
		return roleResourceVo;

	}
}
