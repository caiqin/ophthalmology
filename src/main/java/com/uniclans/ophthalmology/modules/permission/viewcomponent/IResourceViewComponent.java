package com.uniclans.ophthalmology.modules.permission.viewcomponent;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.ResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;

public interface IResourceViewComponent
{

	/**
	 * 新增系统资源
	 * 
	 * @Title: saveResource
	 * @param resourceVo
	 * @return boolean
	 */
	public boolean saveResource(SystemResourceVo resourceVo) throws Exception;

	/**
	 * 分页查询资源
	 * 
	 * @Title: getResourcesByPage
	 * @param viewCondition
	 * @param pageNo
	 * @param pageSize
	 * @return SystemResourceVo
	 */
	public PageFinder<SystemResourceVo> getResourcesByPage(
			ResourceQueryVo resourceQueryVo,Integer pageNo,Integer pageSize)
			throws Exception;

	/**
	 * 修改系统资源
	 * 
	 * @Title: updateResource
	 * @param resourceVo
	 * @return boolean
	 */
	public void updateResource(SystemResourceVo resourceVo) throws Exception;

	/**
	 * 通过id获取系统资源
	 * 
	 * @Title: getResourceById
	 * @param resourceId
	 * @return SystemResourceVo
	 */
	public SystemResourceVo getResourceById(String resourceId) throws Exception;
	
	public  String    getResourcesStr(SystemUserVo systemUserVo)throws Exception; 
	
	/**
	 * 删除系统资源
	 * 
	 * @Title: updateResource
	 * @param resourceVo
	 * @return boolean
	 */
	public void delResource(SystemResourceVo resourceVo) throws Exception;
}
