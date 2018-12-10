package com.uniclans.ophthalmology.modules.permission.service.component;

import java.util.List;

import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;


public interface ISystemUserLoginService
{

	/**
	 * 登陆用户资源信息
	 * 
	 * @Title: getUserResources
	 * @Description: TODO
	 * @param systemUserDo
	 * @return
	 */
	public List<SystemResourceVo> getUserResources(SystemUserVo systemUserDo,String rescType)
			throws Exception;
}
