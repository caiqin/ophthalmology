package com.uniclans.ophthalmology.modules.diagnose.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.SessionUtil;
import com.uniclans.ophthalmology.modules.diagnose.service.component.IDiagnoseRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.component.ITestRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DiagnoseRecordVo;
import com.uniclans.ophthalmology.modules.diagnose.service.model.TestRecordVo;
import com.uniclans.ophthalmology.modules.diagnose.viewcomponent.IReportComponent;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
/**
 * 检查记录  报表
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/testrecordreport" )
public class TestRecordReportController {

	private static Logger logger = LoggerFactory.getLogger(TestRecordReportController.class);
	
	@Resource
	private IReportComponent reportComponent;
	@Resource
	private ITestRecordService testRecordService;
	
	@Resource
	private IDiagnoseRecordService diagnoseRecordService;
	
	@RequestMapping( "report" )
	public ModelAndView toTestRecordList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("diagnose/testrecord_report");
	}
	
	@ResponseBody
	@RequestMapping( value = "/getData", produces = "text/html;charset=UTF-8" )
	public String testRecordList(TestRecordVo queryVo,
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
	

	@RequestMapping( "diagnoseReport" )
	public ModelAndView toDiagnoseRecordList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("diagnose/diagnoserecord_report");
	}
	@ResponseBody
	@RequestMapping( value = "/getDiagnoseData", produces = "text/html;charset=UTF-8" )
	public String diagnoseRecordList(DiagnoseRecordVo queryVo,
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
			SystemUserVo mgtUserVo = SessionUtil.getMgtUserVo(request);
			
//			diagnoseRecordService.p
			// 每页的开始记录 第一页为1 第二页为number +1
			return  JsonUtils.toJson(diagnoseRecordService.pagedDiagnoseRecordReports(queryVo));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询检查记录列表异常:" + e.getMessage(),e);
			return null;
		}
	}
	
	/**
	 * 导出
	 * @param model
	 * @param orderQueryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping( value = "/export",method=RequestMethod.GET, produces = "text/html;charset=UTF-8" )
	public ModelAndView export(ModelMap model,DiagnoseRecordVo queryVo,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			
			ExcelView viewExcel = new ExcelView();
			queryVo.setPageNo(0);
			queryVo.setPageSize(Integer.MAX_VALUE);
			HSSFWorkbook workBook = reportComponent.generateWorkbookDiagnose(queryVo);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("fileName", "report.xls");
			viewExcel.buildExcelDocument(map, workBook, request, response);
			return new ModelAndView(viewExcel,model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
