package com.uniclans.ophthalmology.modules.permission.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.SessionUtil;
import com.uniclans.ophthalmology.modules.permission.service.model.ResourceQueryVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
import com.uniclans.ophthalmology.modules.permission.viewcomponent.IResourceViewComponent;


@Controller
@RequestMapping("/sys/system/resource")
public class ResourceManagerController {

	@Resource
	private IResourceViewComponent resourceViewComponent;

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 到新增系统资源界面
	 * 
	 * @MethodName toAddSystemRole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toAddResource")
	public ModelAndView toAddResource(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		SystemUserVo systemUser = SessionUtil.getMgtUserVo(request);
		String resourceList = resourceViewComponent.getResourcesStr(systemUser);
		modelMap.put("resourceList", resourceList);
		return new ModelAndView("system/add_system_resource");
	}

	/**
	 * 异步得到资源树
	 * 
	 * @MethodName toGetResourceTree
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getResourceTree")
	public String toGetResourceTree(HttpServletRequest request,
			ModelMap modelMap) throws Exception {
		SystemUserVo systemUser = SessionUtil.getMgtUserVo(request);
		return resourceViewComponent.getResourcesStr(systemUser);
	}

	/**
	 * 新增系统资源
	 * 
	 * @MethodName addSystemRole
	 * @param systemRoleVo
	 * @return
	 */
	@RequestMapping("addResource")
	@ResponseBody
	public String addResource(HttpServletRequest request,
			SystemResourceVo resourceVo, ModelMap map) throws Exception {

		try {
			boolean b = resourceViewComponent.saveResource(resourceVo);
			if(b){
				map.put("result", true);
				return "{result:true,msg:'ok'}";
			}else{
				return "{result:false,msg:'isExist'}";
			}
		} catch (Exception e) {
			map.put("result", false);
			e.printStackTrace();
			log.error("添加资源失败：" + e.getMessage(), e);
			return "{result:false,msg:'error'}";
		}
	}

	/**
	 * 查询资源界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchResource", produces = "text/html;charset=UTF-8")
	public String search(ModelMap model) {
		return "/system/resource_dataList";
	}

	/**
	 * 分页查询资源列表
	 * 
	 * @MethodName queryResourceList
	 * @param resourceQueryVo
	 *            资源查询Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryResourceList", produces = "text/html;charset=UTF-8")
	public String queryResourceList(ResourceQueryVo resourceQueryVo,
			HttpServletRequest request, ModelMap map) throws Exception {
		try {

			SystemUserVo systemUser = SessionUtil.getMgtUserVo(request);

			String loginType = "";
			if (systemUser != null) {
				loginType = systemUser.getLoginType();
			}
			resourceQueryVo.setResType(loginType);
//			ViewCondition viewCondition = setConditions(resourceQueryVo);
			// 当前页
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer.parseInt( page);
			// 每页显示条数
			Integer number = Integer
					.parseInt((rows == null || rows == "0") ? "10" : rows);
			// 每页的开始记录 第一页为1 第二页为number +1
			return JsonUtils.toJson(resourceViewComponent.getResourcesByPage(
					resourceQueryVo, intPage, number));

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询用户列表异常:" + e.getMessage(), e);
			return null;
		}

	}

	/*private ViewCondition setConditions(ResourceQueryVo resourceQueryVo)
			throws Exception {
		ViewCondition viewCondition = new ViewCondition();
		String startDate = resourceQueryVo.getStartDate();
		startDate = startDate == null ? "" : startDate;
		String endDate = resourceQueryVo.getEndDate();
		endDate = endDate == null ? "" : endDate;
		String resName = "";
		if (resourceQueryVo.getResName() != null
				&& !resourceQueryVo.getResName().equals("")) {
			resName = URLDecoder.decode(resourceQueryVo.getResName(), "utf-8");
		}

		String resId = resourceQueryVo.getResId();
		resId = resId == null ? "" : resId;
		Date start = null;
		Date end = null;
		if (!startDate.equals("")) {
			start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		}
		if (!endDate.equals("")) {
			end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		}
		if (!startDate.equals("") && !endDate.equals("")) {
			viewCondition.putConditionValue(ViewConditionType.BETWEEN_AND,
					"SystemResourceVo.createTime",new Date[]{start,end});
		} else if (!startDate.equals("") && endDate.equals("")) {
			viewCondition.putConditionValue(ViewConditionType.GREATER_EQUAL,
					"SystemResourceVo.createTime", start);
		} else if (!endDate.equals("") && startDate.equals("")) {
			viewCondition.putConditionValue(ViewConditionType.LESS_EQUAL,
					"SystemResourceVo.createTime", end);
		}
		if (resName != null && !resName.equals("")) {
			viewCondition.putConditionValue(ViewConditionType.LIKE,
					"SystemResourceVo.resName", resName);
		}
		if (resId != null && !resId.equals("")) {
			viewCondition.putConditionValue(ViewConditionType.EQUAL,
					"SystemResourceVo.id", resId);
		}

		return viewCondition;
	}*/

	/**
	 * 到修改系统资源界面
	 * 
	 * @MethodName toUpdateResource
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toUpdateResource")
	public ModelAndView toUpdateResource(HttpServletRequest request,
			String resourceId, ModelMap map) throws Exception {
		SystemResourceVo resourceVo = resourceViewComponent
				.getResourceById(resourceId);
		SystemUserVo systemUser = SessionUtil.getMgtUserVo(request);
		String resourceList = resourceViewComponent.getResourcesStr(systemUser);
		map.put("resourceList", resourceList);
		map.put("resourceVo", resourceVo);
		return new ModelAndView("system/edit_system_resource");
	}

	/**
	 * 修改系统资源
	 * 
	 * @MethodName updateResource
	 * @param resourceVo
	 * @return
	 */
	@RequestMapping("updateResource")
	@ResponseBody
	public String updateResource(HttpServletRequest request,
			SystemResourceVo resourceVo, ModelMap map) throws Exception {

		try {
			resourceViewComponent.updateResource(resourceVo);
			map.put("result", true);
			return "{result:true}";
		} catch (Exception e) {
			map.put("result", false);
			e.printStackTrace();
			log.error("修改资源失败：" + e.getMessage(), e);
			return "{result:false}";
		}
	}

	/**
	 * 获取系统资源
	 * 
	 * @MethodName ajaxGetResource
	 * @param resourceVo
	 * @return
	 */
	@RequestMapping("ajaxGetResource")
	@ResponseBody
	public String getResource(HttpServletRequest request,
			HttpServletResponse response, SystemResourceVo resourceVo,
			ModelMap map) throws Exception {

		try {
			List<SystemResourceVo> resourceList = new ArrayList<SystemResourceVo>();
			SystemResourceVo sourceVo = resourceViewComponent
					.getResourceById(resourceVo.getId());
			resourceList.add(sourceVo);
			String listData = JsonUtils.toJson(resourceList);
			String jsonData = "{ \"listData\":" + listData + "}";
			response.getWriter().append(jsonData);
			return null;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 删除系统资源
	 * 
	 * @MethodName updateResource
	 * @param resourceVo
	 * @return
	 */
	@RequestMapping("delResource")
	@ResponseBody
	public String delResource(HttpServletRequest request,
			SystemResourceVo resourceVo, ModelMap map) throws Exception {

		try {
			resourceViewComponent.delResource(resourceVo);
			map.put("result", true);
			return "{result:true}";
		} catch (Exception e) {
			map.put("result", false);
			e.printStackTrace();
			log.error("修改资源失败：" + e.getMessage(), e);
			return "{result:false}";
		}
	}
	
	
	
	/**
	 * 获取资源列表
	 * 
	 * @MethodName queryResourceList
	 * @param resourceQueryVo
	 *            资源查询Vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryResource", produces = "text/html;charset=UTF-8")
	public String queryResource(ResourceQueryVo resourceQueryVo,
			HttpServletRequest request, ModelMap map) throws Exception {
		try {

			SystemUserVo systemUser = SessionUtil.getMgtUserVo(request);

			String loginType = "";
			if (systemUser != null) {
				loginType = systemUser.getLoginType();
			}
			resourceQueryVo.setResType(loginType);
			String resId = resourceQueryVo.getResId();
			if(resId==null||"".equals(resId)){
				resId = "-1";
			}
//			ViewCondition viewCondition = new ViewCondition();
//			viewCondition.putConditionValue(ViewConditionType.EQUAL,
//					"SystemResourceVo.id", resId);
			// 当前页
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer
					.parseInt((page == null || page == "0") ? "1" : page);
			// 每页显示条数
			Integer number = Integer
					.parseInt((rows == null || rows == "0") ? "10" : rows);
			// 每页的开始记录 第一页为1 第二页为number +1
			return JsonUtils.toJson(resourceViewComponent.getResourcesByPage(
					resourceQueryVo, intPage, number));

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询用户列表异常:" + e.getMessage(), e);
			return null;
		}

	}

}
