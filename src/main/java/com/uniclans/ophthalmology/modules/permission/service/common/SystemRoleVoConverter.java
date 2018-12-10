package com.uniclans.ophthalmology.modules.permission.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemRole;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;

/**
 * @ClassName: SystemRoleVoConverter
 * @Description: 用户角色信息PO 与 DO 的相互转换
 */

@Component
public class SystemRoleVoConverter
{

	/**
	 * @methodName：systemRolePo2Vo
	 * @Description: 会员登录信息Po转Vo
	 * @param systemUser
	 * @return
	 */
	public SystemRoleVo systemRolePo2Vo(SystemRole systemRole)
	{
		SystemRoleVo systemRoleVo = null;
		if( LogicUtil.isNotNull(systemRole) )
		{
			systemRoleVo = new SystemRoleVo();
			BeanUtils.deepCopy(systemRole,systemRoleVo);
		}
		return systemRoleVo;
	}

	/**
	 * @methodName：systemRoleVo2Po
	 * @Description: 会员登录信息Vo转Po
	 * @param systemUser
	 * @return
	 */
	public SystemRole systemRoleVo2Po(SystemRoleVo systemRoleVo)
	{
		SystemRole systemRole = null;
		if( LogicUtil.isNotNull(systemRoleVo) )
		{
			systemRole = new SystemRole();
			BeanUtils.deepCopy(systemRoleVo,systemRole);
		}
		return systemRole;
	}

	public List<SystemRoleVo> rolePo2VoList(List<SystemRole> roleList)
	{
		List<SystemRoleVo> roleVoList = new ArrayList<SystemRoleVo>();
		if( roleList != null && roleList.size() > 0 )
		{
			for(SystemRole role : roleList)
			{
				roleVoList.add(systemRolePo2Vo(role));
			}
		}
		return roleVoList;
	}

}
