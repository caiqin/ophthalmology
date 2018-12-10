package com.uniclans.ophthalmology.modules.permission.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.uniclans.ophthalmology.basecomponent.utils.BeanUtils;
import com.uniclans.ophthalmology.modules.permission.dao.model.entity.SysRegion;
import com.uniclans.ophthalmology.modules.permission.service.model.SysRegionVo;


@Component
public class SysRegionVoConverter
{

	public SysRegion sysRegionDoConverPo(SysRegionVo sysRegionDo)
	{
		SysRegion iregion = null;
		if( sysRegionDo != null )
		{
			iregion = new SysRegion();
			BeanUtils.deepCopy(sysRegionDo,iregion);
		}
		return iregion;
	}

	public SysRegionVo sysRegionPoConverDo(SysRegion iSysRegion)
	{
		SysRegionVo regionDo = null;
		if( iSysRegion != null )
		{
			regionDo = new SysRegionVo();
			BeanUtils.deepCopy(iSysRegion,regionDo);
		}
		return regionDo;
	}

	public List<SysRegionVo> sysRegionPoConverDoList(List<SysRegion> iRegionList)
	{
		List<SysRegionVo> regionDoList = new ArrayList<SysRegionVo>();
		if( iRegionList != null && iRegionList.size() > 0 )
		{
			for(SysRegion iRegion : iRegionList)
			{
				regionDoList.add(this.sysRegionPoConverDo(iRegion));
			}
		}
		return regionDoList;
	}
}
