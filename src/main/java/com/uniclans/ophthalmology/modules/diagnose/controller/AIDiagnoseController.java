package com.uniclans.ophthalmology.modules.diagnose.controller;

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
import com.uniclans.ophthalmology.modules.diagnose.service.component.ITestRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.TestRecordVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
/**
 * 检查记录Controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/aidiagnose" )
public class AIDiagnoseController {

	private static Logger logger = LoggerFactory.getLogger(AIDiagnoseController.class);
	@Resource
	private ITestRecordService testRecordService;
	
	/**
	 * 检查记录管理页面
	 * @param systemUserId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "list" )
	public ModelAndView toTestRecordList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("diagnose/ai_diagnose_dataList");
	}
	
	/**
	 * 
	 * 分页查询检查记录列表
	 * 
	 * @param queryVo
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( value = "/getData", produces = "text/html;charset=UTF-8" )
	public String diagnoseRecordList(TestRecordVo queryVo,
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
			queryVo.setAiResult("notNull");
			SystemUserVo mgtUserVo = SessionUtil.getMgtUserVo(request);
			String userNo = mgtUserVo.getUserNo();
			
			// 每页的开始记录 第一页为1 第二页为number +1
			return  JsonUtils.toJson(testRecordService.pagedTestRecords(queryVo,userNo));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询检查记录列表异常:" + e.getMessage(),e);
			return null;
		}
	}
}
