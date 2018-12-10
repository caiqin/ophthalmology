package com.uniclans.ophthalmology.modules.permission.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemUser;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;

/**
 * @ClassName: SystemUserDoConverter
 * @Description: 用户登录信息PO 与 DO 的相互转换
 */

@Component
public class SystemUserVoConverter
{

	/**
	 * @methodName：systemUserPo2Do
	 * @Description: 会员登录信息Po转Vo
	 * @param systemUser
	 * @return
	 */
	public SystemUserVo systemUserPo2Vo(SystemUser systemUser)
	{
		SystemUserVo systemUserVo = null;
		if( LogicUtil.isNotNull(systemUser) )
		{
			systemUserVo = new SystemUserVo();
			systemUserVo.setLoginPassword(systemUserVo.getLoginPassword());
			BeanUtils.deepCopy(systemUser,systemUserVo);
		}
		return systemUserVo;
	}

	/**
	 * @methodName：systemUserPo2Vo
	 * @Description: 会员登录信息Vo转Po
	 * @param systemUser
	 * @return
	 */
	public SystemUser systemUserVo2Po(SystemUserVo systemUserVo)
	{
		SystemUser systemUser = null;
		if( LogicUtil.isNotNull(systemUserVo) )
		{
			systemUser = new SystemUser();
			BeanUtils.deepCopy(systemUserVo,systemUser);
		}
		return systemUser;
	}

	public List<SystemUserVo> userPo2VoList(List<SystemUser> userList)
	{
		List<SystemUserVo> userVoList = new ArrayList<SystemUserVo>();
		if( userList != null && userList.size() > 0 )
		{
			for(SystemUser user : userList)
			{
				userVoList.add(systemUserPo2Vo(user));
			}
		}
		return userVoList;
	}

}
