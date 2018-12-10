package com.uniclans.ophthalmology.modules.basedata.controller;

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
import com.uniclans.ophthalmology.modules.basedata.service.component.IDoctorCategoryService;
import com.uniclans.ophthalmology.modules.basedata.service.model.DoctorCategoryVo;
/**
 * 医生专长controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping("/doctorCat")
public class DoctorCategoryController {
	private static Logger logger = LoggerFactory.getLogger(DoctorCategoryController.class);
	@Resource
	private IDoctorCategoryService doctorCategoryService;
	

	/**
	 * 医生专长管理页面
	 * 
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView toHospitalList(ModelMap modelMap) throws Exception {
		return new ModelAndView("basedata/doctorCat_dataList");
	}

	/**
	 * 
	 * 分页查询医生专长列表
	 * 
	 * @param queryVo
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getData", produces = "text/html;charset=UTF-8")
	public String hospitalList(DoctorCategoryVo queryVo, HttpServletRequest request, ModelMap map) throws Exception {
		try {
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer.parseInt(page);
			// 每页显示条数
			Integer number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
			queryVo.setPageNo(intPage);
			queryVo.setPageSize(number);
			// 每页的开始记录 第一页为1 第二页为number +1
			return JsonUtils.toJson(doctorCategoryService.pagedDoctorCategorys(queryVo));

		} catch (Exception e) {
			logger.error("查询医院列表异常:" + e.getMessage(), e);
			return "";
		}
	}

	/**
	 * 新增医生专长
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public String addHospital(DoctorCategoryVo doctorCategoryVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			doctorCategoryService.addDoctorCategory(doctorCategoryVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加医生专长成功，名称：" + doctorCategoryVo.getCatName());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加医生专长失败：" + e.getMessage(), e);
		}

		return JsonUtils.toJson(map);
	}
	/**
	 * 修改医生专长
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String updateHospital(DoctorCategoryVo doctorCategoryVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			doctorCategoryService.updateDoctorCategory(doctorCategoryVo);
			map.put("result", true);
			map.put("message", "修改成功！");
			logger.info("修改医生专长成功.");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("修改医生专长失败：" + e.getMessage(), e);
		}
		
		return JsonUtils.toJson(map);
	}
	/**
	 * 查询所有数据
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllData", produces = "text/html;charset=UTF-8")
	public String getAll(HttpServletRequest request, ModelMap map) throws Exception {
		try {
			return JsonUtils.toJson(doctorCategoryService.getAll());
		} catch (Exception e) {
			logger.error("查询医生专长列表异常:" + e.getMessage(), e);
			return "";
		}
	}
}
