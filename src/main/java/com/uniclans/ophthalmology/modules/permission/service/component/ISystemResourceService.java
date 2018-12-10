package com.uniclans.ophthalmology.modules.permission.service.component;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.ResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;


public interface ISystemResourceService
{
	/**
	 * 查询资源信息
	 * 
	 * @Title: getResourceByIds
	 * @Description: TODO
	 * @param resIds
	 *            资源ID
	 * @return
	 */
	public List<SystemResourceVo> getResourceByIds(List<String> resIds,String rescType)
			throws Exception;
	/**
	 * 查询资源信息
	 * 
	 * @Title: getResourceByIds
	 * @Description: TODO
	 * @param resIds
	 *            资源ID
	 * @return
	 */
	public List<SystemResourceVo> getResourceByIds(List<String> resIds)
			throws Exception;

	/**
	 * 新增资源信息
	 * 
	 * @Title: addSystemResource
	 * @Description: TODO
	 * @param resourceDo
	 * @return
	 */
	public void addSystemResource(SystemResourceVo resourceDo)
			throws Exception;

	/**
	 * 分页查询资源
	 * 
	 * @Title: getResourcesByPage
	 * @param viewCondition
	 * @param pageNo
	 * @param pageSize
	 * @return SystemResourceVo
	 */
	public PageFinder<SystemResourceVo> getResourcesByPage(ResourceQueryVo resourceQueryVo,int pageNo,int pageSize)
			throws Exception;

	/**
	 * 修改系统资源
	 * 
	 * @Title: updateResource
	 * @param resourceVo
	 * @return boolean
	 */
	public void updateResource(SystemResourceVo resourceDo) throws Exception;

	/**
	 * 通过id获取系统资源
	 * 
	 * @Title: getResourceById
	 * @param resourceId
	 * @return SystemResourceDo
	 */
	public SystemResourceVo getResourceById(String resourceId) throws Exception;
	
	/**
	 * 通过资源名称判断资源是否存在
	 * 
	 * @Title: checkSourceExist
	 * @param resourceDo
	 * @return boolean
	 */
	public boolean checkSourceExist(SystemResourceVo resourceDo) throws Exception;
}
