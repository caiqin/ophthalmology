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

import com.uniclans.ophthalmology.basecomponent.upload.component.IContentPublisher;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.modules.basedata.service.component.IHospitalService;
import com.uniclans.ophthalmology.modules.basedata.service.model.HospitalVo;
import com.uniclans.ophthalmology.modules.basedata.viewcomponent.IHospitalViewComponent;

/**
 * 医院Controller
 * 
 * @author Stanley
 *
 */
@Controller
@RequestMapping("/hospital")
public class HospitalController {
	@Resource
	private IHospitalService hospitalService;
	@Resource
	private IHospitalViewComponent hospitalViewComponent;
	@Resource
	private IContentPublisher contentPublisher;
	
	private static Logger logger = LoggerFactory.getLogger(HospitalController.class);
	/**
	 * 医院管理页面
	 * 
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView toHospitalList(ModelMap modelMap) throws Exception {
		return new ModelAndView("basedata/hospital_dataList");
	}
	@RequestMapping("toAdd")
	public ModelAndView toAddHospital(ModelMap modelMap) throws Exception {
		String hospitalList = hospitalViewComponent.getHospitalStr();
		modelMap.put("hospitalList", hospitalList);
		return new ModelAndView("basedata/add_hospital");
	}
	@RequestMapping("toEdit")
	public ModelAndView toEditHospital(ModelMap modelMap,String hospitalId) throws Exception {
		String hospitalList = hospitalViewComponent.getHospitalStr();
		modelMap.put("hospitalList", hospitalList);
		modelMap.put("hospitalVo", hospitalService.getHospital(hospitalId));
		return new ModelAndView("basedata/edit_hospital");
	}

	/**
	 * 
	 * 分页查询医院列表
	 * 
	 * @param queryVo
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getData", produces = "text/html;charset=UTF-8")
	public String hospitalList(HospitalVo queryVo, HttpServletRequest request, ModelMap map) throws Exception {
		try {
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Integer intPage = Integer.parseInt(page);
			// 每页显示条数
			Integer number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
			queryVo.setPageNo(intPage);
			queryVo.setPageSize(number);
			// 每页的开始记录 第一页为1 第二页为number +1
			return JsonUtils.toJson(hospitalService.pagedHospitals(queryVo));

		} catch (Exception e) {
			logger.error("查询医院列表异常:" + e.getMessage(), e);
			return "";
		}
	}

	/**
	 * 生成二维码
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("createqrcode")
	public String createQRCode(HospitalVo hospitalVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			hospitalService.createQrCode(hospitalVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("生成二维码成功。");
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("生成二维码失败：" + e.getMessage(), e);
		}
		
		return JsonUtils.toJson(map);
	}
	
	/**
	 * 新增医院
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public String addHospital(HospitalVo hospitalVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			hospitalService.addHospital(hospitalVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加医院成功，医院：" + hospitalVo.getHospitalName());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加医院失败：" + e.getMessage(), e);
		}

		return JsonUtils.toJson(map);
	}
	/**
	 * 启用禁用二维码
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("enableQr")
	public String enableQr(HospitalVo hospitalVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			hospitalService.enableQr(hospitalVo);
			map.put("result", true);
			map.put("message", "操作成功！");
			logger.info("启用禁用医院二维码成功，医院：" + hospitalVo.getHospitalId());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "操作失败！");
			logger.error("启用禁用医院二维码失败：" + e.getMessage(), e);
		}
		
		return JsonUtils.toJson(map);
	}
	/**
	 * 修改医院
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String updateHospital(HospitalVo hospitalVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			hospitalService.updateHospital(hospitalVo);
			map.put("result", true);
			map.put("message", "修改成功！");
			logger.info("修改医院成功，医院：" + hospitalVo.getHospitalName());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("修改医院失败：" + e.getMessage(), e);
		}
		
		return JsonUtils.toJson(map);
	}
	/**
	 * 查询所有医院
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/getAllData", produces = "text/html;charset=UTF-8")
	public String getAll(HttpServletRequest request, ModelMap map) throws Exception {
		try {
			return JsonUtils.toJson(hospitalService.getAll());
		} catch (Exception e) {
			logger.error("查询医院列表异常:" + e.getMessage(), e);
			return "";
		}
	}
	@ResponseBody
	@RequestMapping(value = "/getTree", produces = "text/html;charset=UTF-8")
	public String getTree(HttpServletRequest request, ModelMap map) throws Exception {
		try {
			String hospitalList = hospitalViewComponent.getHospitalStr();
			return hospitalList;
		} catch (Exception e) {
			logger.error("查询医院树异常:" + e.getMessage(), e);
			return "";
		}
	}
}
