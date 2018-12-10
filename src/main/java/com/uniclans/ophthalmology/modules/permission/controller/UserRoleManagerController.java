package com.uniclans.ophthalmology.modules.permission.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.IUserRoleViewComponent;


@Controller
@RequestMapping( "/sys/system/roleuser" )
public class UserRoleManagerController
{

	@Resource
	private IUserRoleViewComponent	userRoleViewComponent;

	private Logger					logger	= Logger.getLogger(this.getClass());

	/**
	 * 到新增用户角色界面(平台)
	 * 
	 * @MethodName toAddUserRole
	 * @return
	 * @throws Exception
	 */
	//@Privilege( type = PrivilegeType.LOGIN )
	@RequestMapping( "toAddUserRole" )
	public ModelAndView toAddUserRole(ModelMap modelMap) throws Exception
	{

		// 平台用户
		String loginType = "1";
		List<SystemUserVo> userVoList = userRoleViewComponent
				.getUsers(loginType);
		String jsonData = JsonUtils.toJson(userVoList);
		modelMap.put("userVoList",jsonData);
		return new ModelAndView("system/add_user_role");
	}

	/**
	 * 到新增用户角色界面(供应商)
	 * 
	 * @MethodName toSupplyAddUserRole
	 * @return
	 * @throws Exception
	 */
	//@Privilege( type = PrivilegeType.LOGIN )
	@RequestMapping( "toSupplyAddUserRole" )
	public ModelAndView toSupplierAddUserRole(ModelMap modelMap)
			throws Exception
	{

		// 供应商用户
		String loginType = "2";
		List<SystemUserVo> userVoList = userRoleViewComponent
				.getUsers(loginType);
		String jsonData = JsonUtils.toJson(userVoList);
		modelMap.put("userVoList",jsonData);
		return new ModelAndView("system/add_user_role");
	}

	/**
	 * 添加用户角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userIds
	 *            用户ID
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/addroleuser" )
	//@Privilege( type = PrivilegeType.LOGIN )
	public String addUserRole(String roleId,String userIds,ModelMap model)
	{

		try
		{
			userRoleViewComponent.saveUserRoles(roleId,userIds);
			model.put("result",true);
			return "{result:true}";
		}
		catch( Exception e )
		{
			model.put("result",false);
			e.printStackTrace();
			logger.error("添加用户角色失败：" + e.getMessage(),e);
			return "{result:false}";
		}
	}

	/**
	 * 添加用户角色
	 * 
	 * @param roleIds
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/addrolesinuser" )
	public String addRolesInUser(String userId,String roleIds,String deleteIds,ModelMap model)
	{

		try
		{
			userRoleViewComponent.addUserRoles(userId,roleIds,deleteIds);
			model.put("result",true);
			return "{result:true}";
		}
		catch( Exception e )
		{
			model.put("result",false);
			e.printStackTrace();
			logger.error("添加用户角色失败：" + e.getMessage(),e);
			return "{result:false}";
		}
	}
	
	/**
	 * 分页查询用户角色列表
	 * 
	 * @param permissionQueryVo
	 *            权限查询Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( value = "/userRoleList", produces = "text/html;charset=UTF-8" )
	public String userRoleList(String userId,
			HttpServletRequest request,ModelMap map) throws Exception
	{

		try
		{

			
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer
					.parseInt(( page == null || page == "0" ) ? "1" : page);
			// 每页显示条数
			Integer number = Integer
					.parseInt(( rows == null || rows == "0" ) ? "10" : rows);
			// 每页的开始记录 第一页为1 第二页为number +1
			return JsonUtils.toJson(userRoleViewComponent.getUserRoleList(
					userId,intPage,number));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询权限列表异常:" + e.getMessage(),e);
			return null;
		}

	}
	
	/**
	 * 查询界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/queryUseRole", produces = "text/html;charset=UTF-8" )
	public String queryUseRole(ModelMap model)
	{
		return "/system/userole_dataList";
	}
	
	@ResponseBody
	@RequestMapping( value = "/pageUseRoleList", produces = "text/html;charset=UTF-8" )
	public String pageUseRoleList(UserRoleQueryVo userRoleQueryVo,
			HttpServletRequest request,ModelMap map) throws Exception
	{

		try
		{
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer
					.parseInt(( page == null || page == "0" ) ? "1" : page);
			// 每页显示条数
			Integer number = Integer
					.parseInt(( rows == null || rows == "0" ) ? "10" : rows);
			// 每页的开始记录 第一页为1 第二页为number +1\
			PageFinder<UserRoleQueryVo> pageVoList = userRoleViewComponent.pageUserRoleList(userRoleQueryVo,intPage,number);
			return JsonUtils.toJson(pageVoList);

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询用户角色列表异常:" + e.getMessage(),e);
			return null;
		}

	}
	
	
	
	/**
	 * 删除用户角色
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/delrolesinuser" )
	public String delRolesinuser(String id,ModelMap model)
	{

		try
		{
			userRoleViewComponent.delUserRole(id);
			model.put("result",true);
			return "{result:true}";
		}
		catch( Exception e )
		{
			model.put("result",false);
			e.printStackTrace();
			logger.error("添加用户角色失败：" + e.getMessage(),e);
			return "{result:false}";
		}
	}
	
	
	/**
	 * 批量删除用户角色
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/batchDelete" )
	public String batchDelete(String ids,ModelMap model)
	{

		try
		{
			userRoleViewComponent.batchDelUserRole(ids);
			model.put("result",true);
			return "{result:true}";
		}
		catch( Exception e )
		{
			model.put("result",false);
			e.printStackTrace();
			logger.error("添加用户角色失败：" + e.getMessage(),e);
			return "{result:false}";
		}
	}
	
	
	
}
