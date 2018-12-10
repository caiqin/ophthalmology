package com.uniclans.ophthalmology.modules.permission.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.modules.permission.service.model.RoleResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.IRoleResourceViewComponent;

@Controller
@RequestMapping("/sys/system/roleresource")
public class RoleResourceController {

	@Resource
	private IRoleResourceViewComponent roleResourceViewComponent;

	private Logger logger = Logger.getLogger(this.getClass());


	@ResponseBody
	@RequestMapping("/addroleresource")
	public String addUserPermission(String roleId, String perIds,String delIds, ModelMap model) {

		try {
			roleResourceViewComponent.saveRolesResource(roleId, perIds,delIds);
			model.put("result", true);
			return "{result:true}";
		} catch (Exception e) {
			model.put("result", false);
			e.printStackTrace();
			logger.error("添加角色权限失败：" + e.getMessage(), e);
			return "{result:false}";
		}
	}

	/**
	 * 分页查询角色权限列表
	 * 
	 * @param permissionQueryVo
	 *            权限查询Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/roleResourceList", produces = "text/html;charset=UTF-8")
	public String permissionList(RoleResourceQueryVo permissionQueryVo, HttpServletRequest request, ModelMap map)
			throws Exception {

		try {

			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
			// 每页显示条数
			Integer number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
			// 每页的开始记录 第一页为1 第二页为number +1
			return JsonUtils.toJson(roleResourceViewComponent.getResources(permissionQueryVo, intPage, number));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询权限列表异常:" + e.getMessage(), e);
			return null;
		}

	}

	/**
	 * 查询界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryRoleRes", produces = "text/html;charset=UTF-8")
	public String queryRolePer(ModelMap model) {
		return "/system/roleres_dataList";
	}

	@ResponseBody
	@RequestMapping("/delRoleResouce")
	public String delRolePermission(String rolePerId, ModelMap model) {

		try {
			boolean result = roleResourceViewComponent.delRolesResource(rolePerId);
			if (result) {
				model.put("result", true);
				return "{result:true}";
			} else
				return "{result:false}";
		} catch (Exception e) {
			model.put("result", false);
			e.printStackTrace();
			logger.error("删除角色权限失败：" + e.getMessage(), e);
			return "{result:false}";
		}
	}

	@ResponseBody
	@RequestMapping("/batchdelRoleres")
	public String batchDelRolePermission(String ids, ModelMap model) {

		try {
			boolean result = roleResourceViewComponent.batchDelRolesResource(ids);
			if (result) {
				model.put("result", true);
				return "{result:true}";
			} else
				return "{result:false}";
		} catch (Exception e) {
			model.put("result", false);
			e.printStackTrace();
			logger.error("删除角色权限失败：" + e.getMessage(), e);
			return "{result:false}";
		}
	}

}
