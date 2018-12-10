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
import com.uniclans.ophthalmology.modules.basedata.service.component.IAiResultTranslateService;
import com.uniclans.ophthalmology.modules.basedata.service.model.AiResultTranslateVo;
/**
 * 患者Controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/airesult" )
public class AiResultTranslateController {

	private static Logger logger = LoggerFactory.getLogger(AiResultTranslateController.class);
	@Resource
	private IAiResultTranslateService aiResultTranslateService;
	/**
	 * 患者管理页面
	 * @param systemUserId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "list" )
	public ModelAndView toPatientList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("basedata/airesult_dataList");
	}
	
	/**
	 * 
	 * 分页查询列表
	 * 
	 * @param queryVo
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( value = "/getData", produces = "text/html;charset=UTF-8" )
	public String patientList(AiResultTranslateVo queryVo,
			HttpServletRequest request,ModelMap map) throws Exception
	{
		try
		{
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer.parseInt(page);
			// 每页显示条数
			Integer number = Integer
					.parseInt(( rows == null || rows == "0" ) ? "10" : rows);
			queryVo.setPageNo(intPage);
			queryVo.setPageSize(number);
			// 每页的开始记录 第一页为1 第二页为number +1
			return  JsonUtils.toJson(aiResultTranslateService.pageData(queryVo));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询ai结果描述列表异常:" + e.getMessage(),e);
			return null;
		}
	}
	@ResponseBody
	@RequestMapping( value = "/getAll", produces = "text/html;charset=UTF-8" )
	public String getAll(AiResultTranslateVo queryVo,
			HttpServletRequest request,ModelMap map) throws Exception
	{
		try
		{
			queryVo.setPageNo(0);
			queryVo.setPageSize(Integer.MAX_VALUE);
			// 每页的开始记录 第一页为1 第二页为number +1
			return  JsonUtils.toJson(aiResultTranslateService.pageData(queryVo).getData());
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询ai结果描述列表异常:" + e.getMessage(),e);
			return null;
		}
	}
	/**
	 * 新增ai结果
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping( "add" )
	public String addAiResult(HttpServletRequest request,AiResultTranslateVo aiResultTranslateVo,ModelMap modelMap)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			aiResultTranslateService.add(aiResultTranslateVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加ai结果成功，："+aiResultTranslateVo.getDescription());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加ai结果失败："+e.getMessage(),e);
		}

		return JsonUtils.toJson(map);
	}
	/**
	 * 修改患者
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String updateHospital(AiResultTranslateVo aiResultTranslateVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			aiResultTranslateService.update(aiResultTranslateVo);
			map.put("result", true);
			map.put("message", "修改成功！");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "修改失败！");
			logger.error("修改Ai结果失败：" + e.getMessage(), e);
		}
		return JsonUtils.toJson(map);
	}
	/**
	 * 删除
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String id, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			aiResultTranslateService.delete(id);
			map.put("result", true);
			map.put("message", "删除成功！");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "删除失败！");
			logger.error("删除Ai结果失败：" + e.getMessage(), e);
		}
		return JsonUtils.toJson(map);
	}
}
