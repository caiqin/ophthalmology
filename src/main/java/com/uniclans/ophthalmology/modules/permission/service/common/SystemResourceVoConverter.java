package com.uniclans.ophthalmology.modules.permission.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.basecomponent.utils.LogicUtil;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SystemResource;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;

@Component
public class SystemResourceVoConverter
{

	/**
	 * @methodName：resourcePo2Do
	 * @Description: 资源信息Po转Do
	 * @param systemResource
	 * @return
	 */
	public SystemResourceVo resourcePo2Vo(SystemResource systemResource)
	{
		SystemResourceVo systemResourceVo = null;
		if( LogicUtil.isNotNull(systemResource) )
		{
			systemResourceVo = new SystemResourceVo();
			BeanUtils.deepCopy(systemResource,systemResourceVo);
		}
		return systemResourceVo;
	}

	/**
	 * @methodName：resourceDo2Po
	 * @Description: 资源信息Do转Po
	 * @param systemResourceDo
	 * @return
	 */
	public SystemResource resourceVo2Po(SystemResourceVo systemResourceVo)
	{
		SystemResource systemResource = null;
		if( LogicUtil.isNotNull(systemResourceVo) )
		{
			systemResource = new SystemResource();
			BeanUtils.deepCopy(systemResourceVo,systemResource);
		}
		return systemResource;
	}

	public List<SystemResourceVo> resourcePo2VoList(
			List<SystemResource> resourceList)
	{
		List<SystemResourceVo> resourceVoList = new ArrayList<SystemResourceVo>();
		if( resourceList != null && resourceList.size() > 0 )
		{
			for(SystemResource resource : resourceList)
			{
				resourceVoList.add(resourcePo2Vo(resource));
			}
		}
		return resourceVoList;
	}
}
