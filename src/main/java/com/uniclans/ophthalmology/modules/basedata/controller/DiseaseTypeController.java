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
import com.uniclans.ophthalmology.modules.basedata.service.component.IDiseaseTypeService;
import com.uniclans.ophthalmology.modules.basedata.service.model.DiseaseTypeVo;
/**
 * 疾病种类controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping("/diseasetype")
public class DiseaseTypeController {
	private static Logger logger = LoggerFactory.getLogger(DiseaseTypeController.class);
	@Resource
	private IDiseaseTypeService diseaseTypeService;
	

	/**
	 * 疾病种类管理页面
	 * 
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView toList(ModelMap modelMap) throws Exception {
		return new ModelAndView("basedata/diseasetype_dataList");
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
	public String diseaseTypeList(DiseaseTypeVo queryVo, HttpServletRequest request, ModelMap map) throws Exception {
		try {
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer.parseInt(page);
			// 每页显示条数
			Integer number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
			queryVo.setPageNo(intPage);
			queryVo.setPageSize(number);
			// 每页的开始记录 第一页为1 第二页为number +1
			return JsonUtils.toJson(diseaseTypeService.pagedDiseaseTypes(queryVo));

		} catch (Exception e) {
			logger.error("查询疾病种类列表异常:" + e.getMessage(), e);
			return "";
		}
	}

	/**
	 * 新增疾病种类
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public String addDiseaseType(DiseaseTypeVo diseaseTypeVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			diseaseTypeService.addDiseaseType(diseaseTypeVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加疾病种类成功，名称：" + diseaseTypeVo.getDiseaseTypeName());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加疾病种类失败：" + e.getMessage(), e);
		}

		return JsonUtils.toJson(map);
	}
	/**
	 * 修改疾病种类
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String updateDiseaseType( DiseaseTypeVo diseaseTypeVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			diseaseTypeService.updateDiseaseType(diseaseTypeVo);
			map.put("result", true);
			map.put("message", "修改成功！");
			logger.info("修改疾病种类成功.");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("修改疾病种类失败：" + e.getMessage(), e);
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
			return JsonUtils.toJson(diseaseTypeService.getAll());
		} catch (Exception e) {
			logger.error("查询疾病种类列表异常:" + e.getMessage(), e);
			return "";
		}
	}
}
