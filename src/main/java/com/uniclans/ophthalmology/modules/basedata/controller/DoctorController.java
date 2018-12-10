package com.uniclans.ophthalmology.modules.basedata.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.uniclans.ophthalmology.basecomponent.upload.component.IContentPublisher;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.basecomponent.utils.PageFinder;
import com.uniclans.ophthalmology.modules.basedata.service.component.IDoctorService;
import com.uniclans.ophthalmology.modules.basedata.service.model.DoctorVo;
/**
 * 医生Controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/doctor" )
public class DoctorController {

	private static Logger logger = LoggerFactory.getLogger(DoctorController.class);
	@Resource
	private IDoctorService doctorService;
	@Resource
	private IContentPublisher contentPublisher;
	@Value(value="${imgRes}")
	private String imgRes;
	@Value(value="${sitepath}")
	private String sitepath;
	/**
	 * 医生管理页面
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "list" )
	public ModelAndView toDoctorList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("basedata/doctor_dataList");
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
	public String doctorList(DoctorVo queryVo,
			HttpServletRequest request,ModelMap map) throws Exception
	{
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Integer intPage = Integer.parseInt(page);
		// 每页显示条数
		Integer number = Integer
				.parseInt(( rows == null || rows == "0" ) ? "10" : rows);
		queryVo.setPageNo(intPage);
		queryVo.setPageSize(number);
		try{
			// 每页的开始记录 第一页为1 第二页为number +1
			return  JsonUtils.toJson(doctorService.pagedDoctors(queryVo));

		}catch( Exception e ){
			logger.error("查询用户列表异常:" + e.getMessage(),e);
			return JsonUtils.toJson(new PageFinder<>(intPage, number, 0));
		}
	}
	/**
	 * 新增医生
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping( "add" )
	public String addDoctor(DoctorVo doctorVo,ModelMap modelMap)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			doctorService.addDoctor(doctorVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加医生成功，医生姓名："+doctorVo.getDoctorName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加医生失败："+e.getMessage(),e);
		}

		return JsonUtils.toJson(map);
	}
	/**
	 * 修改医生
	 * 
	 * @param doctorVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String updateHospital(DoctorVo doctorVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			doctorService.updateDoctor(doctorVo);
			map.put("result", true);
			map.put("message", "修改成功！");
			logger.info("修改医生成功，医生：" + doctorVo.getDoctorName());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "修改失败！");
			logger.error("修改医生失败：" + e.getMessage(), e);
		}
		
		return JsonUtils.toJson(map);
	}
	@ResponseBody
	@RequestMapping(value="deleteImg", produces = "text/json;charset=UTF-8")
	public String deleteImg(String id,String url) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		DoctorVo doctorVo = doctorService.getDoctor(id);
		String url2 = doctorVo.getUrl();
		this.contentPublisher.deleteFile(sitepath+url2);
		doctorVo.setUrl("");
		doctorService.updateDoctor(doctorVo);
		map.put("result", true);
		map.put("message", "删除成功！");
		return JsonUtils.toJson(map);
	}
	@ResponseBody
    @RequestMapping(value="upload", produces = "text/json;charset=UTF-8")
    public String uploadFile(MultipartHttpServletRequest request) throws Exception {
        MultipartFile file = null;
        MultiValueMap<String, MultipartFile> multiFiles = request.getMultiFileMap();
        List<String> imgFiles = new ArrayList<String>();

        Map<String,Object> imageMap=new HashMap<String,Object>();
        for (String key : multiFiles.keySet()) {
            file = multiFiles.getFirst(key);
            String filePath = request.getSession().getServletContext().getRealPath("") +"images"+File.separator+"ophthalmology"+ File.separator+"doctor"+ File.separator;
            File f1 = new File(filePath);

            if (!f1.exists()) {
                f1.mkdirs();
            }
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);// 取得上传文件后缀名
            String newUploadFileName = System.currentTimeMillis() + "." + suffix;// 文件名
            // 图片处理上传
            file.transferTo(new File(filePath + newUploadFileName));
            // 发布图片至图片服务器
            try {
               String url= File.separator+"ophthalmology"+ File.separator+"doctor"+ File.separator+newUploadFileName;
                imgFiles.add(url);
                imageMap.put("images", File.separator+"ophthalmology"+ File.separator+"doctor"+ File.separator+newUploadFileName);
       	         // 发布图片至图片服务器
       	        this.contentPublisher.synchronousPublish(request.getSession().getServletContext().getRealPath("") +"images", imgFiles);
             } catch (Exception e){
            	e.printStackTrace();
            }
        }
        
        // 发布图片至图片服务器
        return JsonUtils.toJson(imageMap);
    }
}
