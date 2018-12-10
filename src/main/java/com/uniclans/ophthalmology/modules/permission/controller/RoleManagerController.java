package com.uniclans.ophthalmology.modules.permission.controller;

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
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemRoleVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.ISystemRoleViewComponent;

@Controller
@RequestMapping("/sys/roles")
public class RoleManagerController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ISystemRoleViewComponent systemRoleViewComponent;

	/**
	 * 查询界面(平台)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tosearchrole", produces = "text/html;charset=UTF-8")
	public String tosearchrole(ModelMap model) {
		return "/system/role_dataList";
	}

	/**
	 * 查询界面(供应商)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toapplyrole", produces = "text/html;charset=UTF-8")
	public String tosearchapplyrole(ModelMap model) {
		return "/system/role_supply_dataList";
	}

	/**
	 * 查询系统角色
	 * 
	 * @MethodName querySystemRoles
	 * @param systemRoleVo
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "querySystemRoles", produces = "text/html;charset=UTF-8")
	public String querySystemRoles(HttpServletRequest request, SystemRoleQueryVo queryVo, ModelMap modelMap)
			throws Exception {
		// 平台角色
		// queryVo.setRoleType("1");
		// 当前页
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Integer intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		Integer number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		PageFinder<SystemRoleVo> systemRoleVos = systemRoleViewComponent.getRoles(queryVo, intPage, number);
		return JsonUtils.toJson(systemRoleVos);
	}

	/**
	 * 查询供应商角色
	 * 
	 * @param request
	 * @param systemRoleVo
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "querySupplierRoles", produces = "text/html;charset=UTF-8")
	public String querySupplierRoles(HttpServletRequest request, SystemRoleQueryVo queryVo, ModelMap modelMap)
			throws Exception {

		queryVo.setRoleType("2");
		// 当前页
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Integer intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		Integer number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		PageFinder<SystemRoleVo> systemRoleVos = systemRoleViewComponent.getRoles(queryVo, intPage, number);
		return JsonUtils.toJson(systemRoleVos);
	}

	/**
	 * 到新增系统角色界面
	 * 
	 * @MethodName toAddSystemRole
	 * @return
	 */
	@RequestMapping(value = "toAddSystemRole", produces = "text/html;charset=UTF-8")
	public ModelAndView toAddSystemRole(ModelMap modelMap) {
		modelMap.put("roleType", 1);
		return new ModelAndView("system/add_system_role");
	}

	/**
	 * 到新增供应商角色界面
	 * 
	 * @param roleType
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "toAddSupplierRole", produces = "text/html;charset=UTF-8")
	public ModelAndView toAddSupplierRole(ModelMap modelMap) {
		modelMap.put("roleType", 2);
		return new ModelAndView("system/add_system_role");
	}

	/**
	 * 新增系统角色
	 * 
	 * @MethodName addSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	@RequestMapping(value = "addSystemRole", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addSystemRole(HttpServletRequest request, SystemRoleVo systemRoleVo, ModelMap map) throws Exception {

		try {
			boolean b = systemRoleViewComponent.addSystemRole(systemRoleVo);
			if (b) {
				map.put("result", true);
				return "{result:true,msg:'ok'}";
			}
			return "{result:false,msg:'isExist'}";
		} catch (Exception e) {
			map.put("result", false);
			e.printStackTrace();
			log.error("添加角色失败：" + e.getMessage(), e);
			return "{result:false,msg:'error'}";
		}
	}

	/**
	 * 到修改角色界面
	 * 
	 * @MethodName toEditSystemRole
	 * @param systemRoldId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toEditSystemRole", produces = "text/html;charset=UTF-8")
	public ModelAndView toEditSystemRole(String systemRoleId, ModelMap modelMap) throws Exception {
		SystemRoleVo systemRoleVo = systemRoleViewComponent.getSystemRoleVo(systemRoleId);
		modelMap.put("systemRoleVo", systemRoleVo);
		return new ModelAndView("system/edit_system_role");
	}

	/**
	 * 修改系统角色
	 * 
	 * @MethodName addSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	@RequestMapping(value = "updateSystemRole", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateSystemRole(HttpServletRequest request, SystemRoleVo systemRoleVo, ModelMap map)
			throws Exception {

		try {
			systemRoleViewComponent.updateSystemRole(systemRoleVo);
			map.put("result", true);
			return "{result:true}";
		} catch (Exception e) {
			map.put("result", false);
			e.printStackTrace();
			log.error("修改角色失败：" + e.getMessage(), e);
			return "{result:false}";
		}
	}

	/**
	 * 删除角色
	 * 
	 * @param systemRoleVo
	 *            角色Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleterole", produces = "text/html;charset=UTF-8")
	public String delRole(SystemRoleVo systemRoleVo, ModelMap model) {

		try {
			boolean b = systemRoleViewComponent.delSystemRole(systemRoleVo);
			if (b) {
				return "{result:true,msg:'OK'}";
			}
			return "{result:false,msg:'isExit'}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{result:false,msg:'error'}";
		}
	}

	/**
	 * 添加角色权限
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/torolePermission", produces = "text/html;charset=UTF-8")
	public ModelAndView torolePermission(String systemRoleId, ModelMap map) throws Exception {

		SystemRoleVo systemRoleVo = systemRoleViewComponent.getSystemRoleVo(systemRoleId);
		map.put("systemRoleVo", systemRoleVo);
		return new ModelAndView("system/to_add_rolepermission");
	}
	/**
	 * 添加角色权限
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toroleResouce", produces = "text/html;charset=UTF-8")
	public ModelAndView toroleResouce(String systemRoleId, ModelMap map) throws Exception {
		
		SystemRoleVo systemRoleVo = systemRoleViewComponent.getSystemRoleVo(systemRoleId);
		map.put("systemRoleVo", systemRoleVo);
		return new ModelAndView("system/to_add_roleresource");
	}

	/**
	 * 启用\停用系统角色
	 * 
	 * @MethodName validSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	@RequestMapping(value = "validSystemRole", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String validSystemRole(HttpServletRequest request, SystemRoleVo systemRoleVo, ModelMap map)
			throws Exception {

		try {
			boolean b = systemRoleViewComponent.remoteValid(systemRoleVo.getId());
			if (b) {
				return "{result:true,msg:'ok'}";
			}
			return "{result:false,msg:'isExist'}";
		} catch (Exception e) {
			map.put("result", false);
			e.printStackTrace();
			log.error("添加角色失败：" + e.getMessage(), e);
			return "{result:false,msg:'error'}";
		}
	}

}
