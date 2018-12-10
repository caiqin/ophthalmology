package com.uniclans.ophthalmology.modules.permission.service.component;

import java.util.List;

import com.uniclans.ophthalmology.modules.permission.service.model.SysRegionVo;


public interface ISysRegionService {

	 
	public SysRegionVo getRegionInfo(String id) throws Exception;
	
	public void saveRegionInfo(SysRegionVo sysRegion) throws Exception;
	
	public void updateRegionInfo(SysRegionVo sysRegion) throws Exception;
	
	public void delRegionInfo(String id) throws Exception;
	
	public List<SysRegionVo> getRegions(SysRegionVo sysRegionDo) throws Exception;
	
	/**
	 * 获取所有数据－按等级
	 * @param sysRegionDo
	 * @return
	 * @throws Exception
	 */
	public List<SysRegionVo> getAllRegions(SysRegionVo sysRegionDo) throws Exception;
	
	//public double getPostageByRegionNo(String provinceNo, String cityNo, String areaNo) throws Exception;
	
	public SysRegionVo getSysRegionBytNo(String tNo);
	
    public SysRegionVo getSysRegionByName(String name,String level);
	
	public SysRegionVo getSysRegionByName(String name,String level,String parentNo);
	
	public List<SysRegionVo> getSysRegionAlllist();
}
