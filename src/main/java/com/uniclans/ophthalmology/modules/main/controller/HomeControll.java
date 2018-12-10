/**
 * 
 */
package com.uniclans.ophthalmology.modules.main.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.uniclans.ophthalmology.basecomponent.constans.PermissionConstant;
import com.uniclans.ophthalmology.basecomponent.constans.SystemManagerConstants;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.SessionUtil;
import com.uniclans.ophthalmology.modules.main.common.HomeUtils;
import com.uniclans.ophthalmology.modules.main.viewcomponent.IUserLoginViewComponent;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.ISystemUserViewComponent;


/**
 * @ClassName: HomeControll
 * @Description: TODO
 * 
 */
@Controller
@RequestMapping("/sys/main")
public class HomeControll {

	@Resource
	private ISystemUserViewComponent systemUserViewComponent;
	@Resource
	private IUserLoginViewComponent userLoginViewComponent;
	private static Logger logger = LoggerFactory.getLogger(HomeControll.class);
	
	/**
	 * 转到登陆首页
	 * 
	 * @return
	 */
	@RequestMapping("/tologin")
	public ModelAndView tologin( ModelMap modelMap) {
		return new ModelAndView("system/main/login");
	}

	/**
	 * 登录
	 * 
	 * @param loginName
	 *            用户名称
	 * @param loginPassword
	 *            用户密码
	 * @param validateCode
	 *            验证码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public ModelAndView login(String loginName, String loginPassword,
			String validateCode, String cookieTime, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Map<String, Object> warnMap = new HashMap<String, Object>();
		String indexUrl = "system/main/login";

		// 校验用户是否存在
		if ((loginName==null||loginName.equals(""))&&(loginPassword==null||loginPassword.equals(""))) {
			warnMap.put("warnMessagekey",
					PermissionConstant.LOGINUSER_OR_PASSWORD_NOT_EMPTY);
			return new ModelAndView(indexUrl, warnMap);
		}
		SystemUserVo systemUserVo = systemUserViewComponent
				.getSystemUserByName(loginName, loginPassword);
		if (systemUserVo == null) {
			warnMap.put("warnMessagekey",
					PermissionConstant.LOGINUSER_OR_PASSWORD_NOT_CORRECT);
			return new ModelAndView(indexUrl, warnMap);
		}

		// 用户被锁定则不让登录
		if (systemUserVo.getUserState().equals(SystemManagerConstants.SYSTEM_USER_STATE_1)) {
			warnMap.put("warnMessagekey",
					PermissionConstant.LOGINUSER_HASLOCKED);
			return new ModelAndView(indexUrl, warnMap);
		}

		// 用户被删除则不让登录
		if (systemUserVo.getDeleteFlag().equals(SystemManagerConstants.DELETE_FLAG_1)) {
			warnMap.put("warnMessagekey",
					PermissionConstant.LOGINUSER_HASDELETED);
			return new ModelAndView(indexUrl, warnMap);
		}

		// 记录访问者IP地址
		// systemUser.setLoginIp(ServletUtils.getIpAddr(request));

		HttpSession session = request.getSession();

		// 获取登录用户资源
		List<SystemResourceVo> resList = userLoginViewComponent.getUserResources(systemUserVo,"1","");
//		System.out.println(JsonUtils.toJson(resList));
		SessionUtil.setDataToSession(request, PermissionConstant.USER_RES_LIST,
				resList);
		Object resObject=SessionUtil.getDataFromSession(request, PermissionConstant.USER_RES_LIST, Object.class);
		Object resbuttonObject=SessionUtil.getDataFromSession(request, PermissionConstant.USER_RESC_BUTTON_LIST, Object.class);
		List<SystemResourceVo> resButtonList=userLoginViewComponent.getUserResources(systemUserVo,"2","");
		Map<String,SystemResourceVo> resButtonMap=null;  //按钮权限
		if(resObject==null){
			// 获取登录用户菜单资源
			 resList = userLoginViewComponent.getUserResources(systemUserVo, null,SystemResourceVo.ECOS_RESC_TYPE_MENU);
			 SessionUtil.setDataToSession(request, PermissionConstant.USER_RES_LIST,resList);
		}
		if(resbuttonObject==null){
			// 获取登录用户按钮资源
			 resButtonList = userLoginViewComponent.getUserResources(systemUserVo, null,SystemResourceVo.ECOS_RESC_TYPE_BUTTON);
			 resButtonMap=HomeUtils.listToMap(resButtonList);
			 SessionUtil.setDataToSession(request, PermissionConstant.USER_RESC_BUTTON_LIST,resButtonMap);
		}
		// session保存登陆用户
		SessionUtil.setMgtUser(request, systemUserVo);
		// 设置cookie保存登录信息
		/*
		 * if(cookieTime != null && cookieTime.equals("on")){
		 * CookieUtils.addCookie(request, response,
		 * Constant.LOGIN_SYSTEM_USER_COOKIE_ID, systemUser.getId(),
		 * Constant.LOGIN_COOKIE_TIME); }
		 */

		// 设置cookie保存登录信息
		/*
		 * if(cookieTime != null && cookieTime.equals("on")){
		 * CookieUtils.addCookie(request, response,
		 * Constant.LOGIN_SYSTEM_USER_COOKIE_ID, systemUser.getId(),
		 * Constant.LOGIN_COOKIE_TIME); }
		 */
		// return new ModelAndView("system/main");
		// 用户登陆成功后，判断是否已经有该用户名登陆的session；若有，则将其注销
		// HttpSessionCollector.clearSessionByUsername(loginName);
		SessionUtil.setDataToSession(request,
				PermissionConstant.LOGIN_USERNAME, loginName);
		SessionUtil.setDataToSession(request,
				PermissionConstant.SYSTEM_PRIVILEGE,
				PermissionConstant.PRIVILEGE_LOGIN);

		return new ModelAndView("redirect:mainFrame");
	}
	

	/**
	 * 系统管理入口
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mainFrame")
	//@Privilege(type = PrivilegeType.LOGIN)
	public ModelAndView mainFrame(HttpServletRequest request,
			String root_struc, RedirectAttributesModelMap modelMap) throws Exception {

		HttpSession session = request.getSession();

		SystemUserVo systemUserVo = SessionUtil.getMgtUserVo(request);
		SystemUserVo systemUser = null;
		try {
			if (null != systemUserVo)
				systemUser = systemUserViewComponent
						.getSystemUserById(systemUserVo.getId());
		} catch (Exception e) {
			logger.error("获取用户信息失败"+e.getMessage(),e);
		}
		if (systemUser == null) {
			return new ModelAndView("redirect:tologin");
		}
//		map.put("systemUser", systemUser);

		@SuppressWarnings("unchecked")
		List<SystemResourceVo> resListStr = (List<SystemResourceVo>) SessionUtil
				.getDataFromSession(request, PermissionConstant.USER_RES_LIST,
						SystemResourceVo.class);

		List<String> urlString = new ArrayList<String>();
		if (resListStr != null && resListStr.size() > 0) {
			for (SystemResourceVo sourceVo : resListStr) {
				urlString.add(sourceVo.getResUrl());
			}
			SessionUtil.setDataToSession(request,
					PermissionConstant.USER_RES_URL_LIST, urlString);
		}
		// else
		// {
		// map.put("warnMessagekey",
		// PermissionConstant.LOGINUSER_NO_ONE_PERMISSION);
		// return new ModelAndView("premissions/no_permission",map);
		// }

		//map.put("resListStr", resListStr);
		modelMap.addFlashAttribute("systemUser", systemUser.getUserName());
		return new ModelAndView("redirect:index", modelMap);
	}

	/**
	 * 没有权限提示页
	 * 
	 * @return
	 */
	@RequestMapping("/noPrivilege")
	public ModelAndView noPrivilege(HttpServletRequest request,
			HttpServletResponse response, ModelMap map) {
		map.put("warnMessagekey",
				PermissionConstant.LOGINUSER_NO_URL_PERMISSION);
		return new ModelAndView("system/main/no_permission", map);
	}
	
	/**
	 * 超时跳转到登录页面
	 * @return
	 */
	@RequestMapping("/timeout")
	public ModelAndView toindex(){
		return new ModelAndView("system/main/timeout");
	}

	/**
	 * @methodName：checkVerifyMsg
	 * @Description: 验证页面验证码
	 * @param loginName
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "/checkVerifyMsg")
	public String checkVerifyMsg(String validation, HttpServletRequest request)
			throws Exception {
		String code = (String) request.getSession().getAttribute(
				"captcha");
		Map<String,Object> map =new HashMap<>();
		if (code.toUpperCase().equals(validation.toUpperCase())) {
			map.put("valid", true);
			return JsonUtils.toJson(map);
		}
		map.put("valid", false);
		return JsonUtils.toJson(map);

	}

	@RequestMapping(value = "/index", produces = "text/html;charset=UTF-8")
	public ModelAndView toIndexPage(HttpServletRequest request, ModelMap model)
			throws Exception {
		SystemUserVo systemUserVo = SessionUtil.getMgtUserVo(request);
		if (systemUserVo == null) {
			return new ModelAndView("redirect:tologin.ihtml");
		}
		model.put("systemUserVo", systemUserVo);
		String toPage = "system/main/index";
		return new ModelAndView(toPage, model);
	}

	@RequestMapping(value = "/dataList", produces = "text/html;charset=UTF-8")
	public ModelAndView dtaList(HttpServletRequest request, ModelMap model)
			throws Exception {
		String toPage = "system/main/dataList";
		return new ModelAndView(toPage, model);
	}

	@ResponseBody
	@RequestMapping(value = "/leftPage", produces = "text/html;charset=UTF-8")
	public void leftPage(HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws Exception {
		response.setContentType("application/x-json;charset=utf-8");
		
		@SuppressWarnings("unchecked")
		List<SystemResourceVo> resListStr = (List<SystemResourceVo>) SessionUtil
				.getDataFromSession(request, PermissionConstant.USER_RES_LIST,
						SystemResourceVo.class);
		
		PrintWriter pw = response.getWriter();
		String json = "";
		String t = request.getParameter("t");
		if ("t".equalsIgnoreCase(t)) {
			//json = buildTutorial("../");
			json = buildExamStr(resListStr);
		}
//		else {
//			json = buildExamStr();
//		}
//		System.out.println(json);
		pw.print(json);
		pw.close();
	}

	

	private String buildExamStr(List<SystemResourceVo> resListStr) throws Exception {

		return userLoginViewComponent.toConverString(resListStr);
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
	@RequestMapping( "/updatePassword" )
	public String remotePassword(SystemUserVo systemUserVo,ModelMap model)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		try
		{
			systemUserViewComponent.updatePassword(systemUserVo);
			map.put("result", true);
			map.put("message", "修改密码成功！");
		}catch( Exception e )
		{
			logger.error("修改密码失败："+e.getMessage(),e);
			map.put("result", false);
			map.put("message", "修改密码失败,"+e.getMessage());
		}
		return JsonUtils.toJson(map);
	}
	/**
	 * @methodName：loginOut
	 * @Description: 登出
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SessionUtil.removeSessionUser(request);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:tologin";
	}
}
