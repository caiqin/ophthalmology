package com.uniclans.ophthalmology.modules.main.viewcomponent;

import java.util.List;

import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;


public interface IUserLoginViewComponent
{

	/**
	 * 登陆用户资源信息
	 * 
	 * @Title: getUserResources
	 * @Description: TODO
	 * @param systemUserVo
	 * @return
	 */
	public List<SystemResourceVo> getUserResources(SystemUserVo systemUserVo, String systemName,String rescType)
			throws Exception;
	
	
	public String toConverString(List<SystemResourceVo> list) throws Exception;
}
