package com.uniclans.ophthalmology.modules.permission.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.SessionUtil;
import com.uniclans.ophthalmology.modules.basedata.controller.DoctorController;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.service.model.UserQueryVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.ISystemUserViewComponent;



@Controller
@RequestMapping( "/sys/user" )
public class UserManagerController
{

	@Resource
	private ISystemUserViewComponent	userViewComponent;

	private static Logger log = LoggerFactory.getLogger(DoctorController.class);
	/**
	 * 到新增用户界面
	 * 
	 * @MethodName toAddSystemUser
	 * @return
	 */
	@RequestMapping( "toAddSystemUser" )
	public ModelAndView toAddSystemUser(ModelMap modelMap)
	{

		return new ModelAndView("system/add_system_user");
	}

	/**
	 * 新增用户
	 * 
	 * @MethodName createUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping( "/createuser" )
	public String createUser(SystemUserVo systemUserVo,ModelMap model,
			HttpServletRequest request)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		try
		{
			SystemUserVo systemUser = SessionUtil.getMgtUserVo(request);

			String loginType = "";
			if( systemUser != null )
			{
				loginType = systemUser.getLoginType();
				systemUserVo.setLoginType(loginType);
			}
			boolean b= userViewComponent.addUser(systemUserVo);
			if(b){
				map.put("result", true);
				map.put("message", "添加成功！");
			}else {
				map.put("result", false);
				map.put("message", "添加失败！登录名或手机号已存在");
			}
			
		}
		catch( Exception e )
		{

			e.printStackTrace();
			map.put("result", false);
			map.put("message", "添加失败！");
		}
		return JsonUtils.toJson(map);
	}

	/**
	 * 删除用户
	 * 
	 * @param systemUserVo
	 *            用户Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/deleteuser" )
	public String delUser(SystemUserVo systemUserVo,ModelMap model)
	{

		try
		{
			userViewComponent.deleteUser(systemUserVo);
			model.put("result",true);
			return "{result:true}";
		}
		catch( Exception e )
		{
			model.put("result",false);
			e.printStackTrace();
			return "{result:false}";
		}
	}

	/**
	 * 到修改用户界面
	 * 
	 * @MethodName toEditSystemUser
	 * @param systemUserId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "toEditSystemUser" )
	public ModelAndView toEditSystemUser(String systemUserId,ModelMap modelMap)
			throws Exception
	{
		SystemUserVo systemUserVo = userViewComponent
				.getSystemUserById(systemUserId);
		modelMap.put("systemUserVo",systemUserVo);
		return new ModelAndView("system/edit_system_user");
	}

	/**
	 * 修改用户
	 * 
	 * @param systemUserVo
	 *            用户Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/updateuser" )
	public String updateUser(SystemUserVo systemUserVo,ModelMap model)
	{

		try
		{
			userViewComponent.updateUser(systemUserVo);
			return "{'result':true}";
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return "{'result':false}";
		}
	}

	/**
	 * 查询界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/searchuser", produces = "text/html;charset=UTF-8" )
	public String search(ModelMap model)
	{
		return "/system/user_dataList";
	}

	/**
	 * 分页查询用户列表
	 * 
	 * @param userQueryVo
	 *            用户查询Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( value = "/getuserList", produces = "text/html;charset=UTF-8" )
	public String userdataList(UserQueryVo userQueryVo,
			HttpServletRequest request,ModelMap map) throws Exception
	{
		try
		{

			SystemUserVo systemUser = SessionUtil.getMgtUserVo(request);

			String loginType = "";
			if( systemUser != null )
			{
				loginType = systemUser.getLoginType();
			}
			userQueryVo.setLoginType(loginType);
//			ViewCondition userViewCondition = setConditions(userQueryVo);
			// 当前页
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer.parseInt(page);
			// 每页显示条数
			Integer number = Integer
					.parseInt(( rows == null || rows == "0" ) ? "10" : rows);
			// 每页的开始记录 第一页为1 第二页为number +1
			return  JsonUtils.toJson(userViewComponent.getUsers(userQueryVo,intPage,number));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			log.error("查询用户列表异常:" + e.getMessage(),e);
			return null;
		}

	}

	/*private ViewCondition setConditions(UserQueryVo userQueryVo)
			throws Exception
	{
		ViewCondition userViewCondition = new ViewCondition();
		String startDate = userQueryVo.getStartDate();
		startDate = startDate == null ? "" : startDate;
		String endDate = userQueryVo.getEndDate();
		endDate = endDate == null ? "" : endDate;
		String loginName = userQueryVo.getLoginName();
		loginName = loginName == null ? "" : loginName;
		String loginType = userQueryVo.getLoginType();
		loginType = loginType == null ? "" : loginType;
		String userName = userQueryVo.getUserName();
		userName = userName ==null?"":userName;
		Date start = null;
		Date end = null;
		if( !startDate.equals("") )
		{
			start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		}
		if( !endDate.equals("") )
		{
			end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		}
		if( !startDate.equals("") && !endDate.equals("") )
		{
			List<Date> dateList = new ArrayList<Date>(0);
			dateList.add(start);
			dateList.add(end);
			userViewCondition.putConditionValue(ViewConditionType.BETWEEN_AND,
					"SystemUserVo.createDate",dateList);
		}
		else if( !startDate.equals("") && endDate.equals("") )
		{
			userViewCondition.putConditionValue(
					ViewConditionType.GREATER_EQUAL,"SystemUserVo.createDate",
					start);
		}
		else if( !endDate.equals("") && startDate.equals("") )
		{
			userViewCondition.putConditionValue(ViewConditionType.LESS_EQUAL,
					"SystemUserVo.createDate",end);
		}
		if( loginName != null && !loginName.equals("") )
		{
			userViewCondition.putConditionValue(ViewConditionType.LIKE,
					"SystemUserVo.loginName",loginName);
		}
		if( loginType != null && !loginType.equals("") )
		{
			userViewCondition.putConditionValue(ViewConditionType.EQUAL,
					"SystemUserVo.loginType",loginType);
		}
		if(userName !=null&& !userName.equals("")){
			userViewCondition.putConditionValue(ViewConditionType.LIKE,
					"SystemUserVo.userName",userName);
		}
		return userViewCondition;
	}*/

	/**
	 * 检查用户名是否重复
	 * 
	 * @MethodName checkUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping( "/checkuser" )
	public String checkUser(SystemUserVo systemUserVo,ModelMap model)
	{

		try
		{
			boolean com = userViewComponent.checkUserExist(systemUserVo);
			if( com )
			{
				return "false";
			}
			else
			{
				return "true";
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * 到新增用户角色界面
	 * 
	 * @MethodName toUserRole
	 * @param systemUserId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "toUserRole" )
	public ModelAndView toUserRole(String systemUserId,ModelMap modelMap)
			throws Exception
	{
		SystemUserVo systemUserVo = userViewComponent
				.getSystemUserById(systemUserId);
		modelMap.put("systemUserVo",systemUserVo);
		return new ModelAndView("system/to_add_roleuser");
	}

	/**
	 * 锁定用户
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/lockuser" )
	public String lockUser(String userId,ModelMap model)
	{
		Map<String, Object> map = new HashMap<>();
		try {
			userViewComponent.lockUser(userId);
			map.put("result", true);
			map.put("message", "锁定用户成功");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "锁定用户失败");
			log.error("锁定用户失败："+e.getMessage(),e);
		}
		return JsonUtils.toJson(map);
	}

	/**
	 * 解锁用户
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/unlockuser" )
	public String unLockUser(String userId,ModelMap model)
	{
		Map<String, Object> map = new HashMap<>();
		try {
			userViewComponent.unLockUser(userId);
			map.put("result", true);
			map.put("message", "解锁用户成功");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "解锁用户失败");
			log.error("解锁用户失败："+e.getMessage(),e);
		}
		return JsonUtils.toJson(map);
	}
	
	/**
	 * 到修改用户密码界面
	 * 
	 * @MethodName toEditSystemUser
	 * @param systemUserId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "toEditUserPassword" )
	public ModelAndView toEditUserPassword(String systemUserId,ModelMap modelMap)
			throws Exception
	{
		SystemUserVo systemUserVo = userViewComponent
				.getSystemUserById(systemUserId);
		modelMap.put("systemUserVo",systemUserVo);
		return new ModelAndView("system/remote_user_password");
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @param systemUserVo
	 *            用户Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/remotePassword" )
	public String remotePassword(SystemUserVo systemUserVo,ModelMap model)
	{

		try
		{
			userViewComponent.remotePassword(systemUserVo);
			return "{result:true}";
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return "{result:false}";
		}
	}
	/**
	 * 关联医生
	 * 
	 * @param systemUserVo
	 *            用户Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( "/relationDoctor" )
	public String relationCustom(SystemUserVo systemUserVo)
	{
		Map<String,Object> map = new HashMap<>();
		try
		{
			userViewComponent.relation(systemUserVo);
			map.put("result", true);
			map.put("message", "关联医生成功");
		}
		catch( Exception e )
		{
			log.error("用户关联医生失败："+e.getMessage(),e);
			map.put("result", false);
			map.put("message", "关联医生失败");
		}
		return JsonUtils.toJson(map);
	}
}
