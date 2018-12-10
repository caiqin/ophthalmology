package com.uniclans.ophthalmology.modules.basedata.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.modules.basedata.service.component.IHospitalIdService;
import com.uniclans.ophthalmology.modules.basedata.service.model.HospitalIdVo;
/**
 * 患者Controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/hospitalid" )
public class HospitalIdController {

	private static Logger logger = LoggerFactory.getLogger(HospitalIdController.class);
	@Resource
	private IHospitalIdService hospitalIdService;
	/**
	 * 管理页面
	 * @param systemUserId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "list" )
	public ModelAndView toPatientList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("basedata/hospitalid_dataList");
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
	public String hospitalIdList(HospitalIdVo queryVo,
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
			return  JsonUtils.toJson(hospitalIdService.pageData(queryVo));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询医院编号列表异常:" + e.getMessage(),e);
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
	@RequestMapping( "import" )
	public String importData(HttpServletRequest request,MultipartFile importFile,ModelMap modelMap)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		if(importFile==null) {
			map.put("result", false);
			map.put("message", "导入失败！");
			return JsonUtils.toJson(map);
		}
		try {
			String folder = DateUtils.getCurrentDate("yyyyMMddHHmmSS");
			String filePath = request.getSession().getServletContext().getRealPath("");
			String path = File.separator+"excel" + File.separator + folder + File.separator;
			File f1 = new File(filePath+path);
            if (!f1.exists()) {
                f1.mkdirs();
            }
            String fileName = importFile.getOriginalFilename() ;
			File newFile = new File(filePath + path + fileName);
			importFile.transferTo(newFile);
			
			hospitalIdService.importData(newFile);
			map.put("result", true);
			map.put("message", "导入成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("message", "导入失败！");
			logger.error("导入医院编号失败："+e.getMessage(),e);
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
	public String delete(String ids, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			hospitalIdService.delete(ids);
			map.put("result", true);
			map.put("message", "删除成功！");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "删除失败！");
			logger.error("删除医院Id失败：" + e.getMessage(), e);
		}
		return JsonUtils.toJson(map);
	}
}
