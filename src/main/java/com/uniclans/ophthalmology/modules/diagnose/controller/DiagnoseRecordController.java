package com.uniclans.ophthalmology.modules.diagnose.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uniclans.ophthalmology.basecomponent.utils.HttpUtils;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.SessionUtil;
import com.uniclans.ophthalmology.modules.basedata.viewcomponent.IExportComponent;
import com.uniclans.ophthalmology.modules.diagnose.service.component.IDiagnoseRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DiagnoseRecordVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
/**
 * 诊断记录Controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/diagnoserecord" )
public class DiagnoseRecordController {
	@Value(value="${http.url}")
	private String url;
	private static Logger logger = LoggerFactory.getLogger(DiagnoseRecordController.class);
	@Resource
	private IDiagnoseRecordService diagnoseRecordService;
	@Resource
	private IExportComponent exportComponent;
	/**
	 * 诊断记录管理页面
	 * @param systemUserId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "list" )
	public ModelAndView toDiagnoseRecordList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("diagnose/diagnoserecord_dataList");
	}
	
	/**
	 * 
	 * 分页查询诊断记录列表
	 * 
	 * @param queryVo
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( value = "/getData", produces = "text/html;charset=UTF-8" )
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
			String userNo = mgtUserVo.getUserNo();
			// 每页的开始记录 第一页为1 第二页为number +1
			return  JsonUtils.toJson(diagnoseRecordService.pagedDiagnoseRecords(queryVo,userNo));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询诊断记录列表异常:" + e.getMessage(),e);
			return null;
		}
	}
	@ResponseBody
	@RequestMapping( value = "/export", produces = "text/html;charset=UTF-8" )
	public ModelAndView exportDiagnoseRecordList(DiagnoseRecordVo queryVo,ModelMap model,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try {
			
			ExcelView viewExcel = new ExcelView();
			queryVo.setPageNo(0);
			queryVo.setPageSize(Integer.MAX_VALUE);
			HSSFWorkbook workBook = exportComponent.generateWorkbookDiagnose(queryVo);
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
	@ResponseBody
	@RequestMapping( value = "/getDetail", produces = "text/html;charset=UTF-8" )
	public String getDetail(DiagnoseRecordVo queryVo,
			HttpServletRequest request,ModelMap map) throws Exception
	{
		try
		{
			return  JsonUtils.toJson(diagnoseRecordService.getDetail(queryVo.getTestRecordId()));
		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询诊断记录详情异常:" + e.getMessage(),e);
			return null;
		}
	}
	/**
	 * 新增医生诊断记录
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public String adddiagnoseRecord(DiagnoseRecordVo diagnoseRecordVo,HttpServletRequest request) {
		SystemUserVo editor = SessionUtil.getMgtUserVo(request);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			diagnoseRecordVo.setDoctorId(editor.getDoctorId());
			diagnoseRecordVo.setDoctorName(editor.getDoctorName());
			String id = diagnoseRecordService.add(diagnoseRecordVo);
			HttpUtils.sendGet(url, "id="+id);//发送消息
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加医生诊断成功，名称：" + diagnoseRecordVo);
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加医生诊断失败：" + e.getMessage(), e);
		}

		return JsonUtils.toJson(map);
	}
}
