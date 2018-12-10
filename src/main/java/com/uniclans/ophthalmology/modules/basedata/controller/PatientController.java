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
import com.uniclans.ophthalmology.basecomponent.utils.SessionUtil;
import com.uniclans.ophthalmology.modules.basedata.service.component.IPatientService;
import com.uniclans.ophthalmology.modules.basedata.service.model.PatientVo;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;
/**
 * 患者Controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/patient" )
public class PatientController {

	private static Logger logger = LoggerFactory.getLogger(PatientController.class);
	@Resource
	private IPatientService patientService;
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
		return new ModelAndView("basedata/patient_dataList");
	}
	
	/**
	 * 
	 * 分页查询医生列表
	 * 
	 * @param queryVo
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( value = "/getData", produces = "text/html;charset=UTF-8" )
	public String patientList(PatientVo queryVo,
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
			return  JsonUtils.toJson(patientService.pagedPatients(queryVo,userNo));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询患者列表异常:" + e.getMessage(),e);
			return null;
		}
	}
	/**
	 * 新增患者
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping( "add" )
	public String addDoctor(HttpServletRequest request,PatientVo patientVo,ModelMap modelMap)
	{
		SystemUserVo mgtUserVo = SessionUtil.getMgtUserVo(request);
		String userNo = mgtUserVo.getUserNo();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			patientVo.setCreator(userNo);
			patientService.addPatient(patientVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加患者成功，患者姓名："+patientVo.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加患者失败："+e.getMessage(),e);
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
	public String updateHospital(PatientVo patientVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			patientService.updatePatient(patientVo);
			map.put("result", true);
			map.put("message", "修改成功！");
			logger.info("修改患者成功，患者：" + patientVo.getName());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "修改失败！");
			logger.error("修改患者失败：" + e.getMessage(), e);
		}
		
		return JsonUtils.toJson(map);
	}
}
