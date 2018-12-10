package com.uniclans.ophthalmology.modules.diagnose.controller;

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
import com.uniclans.ophthalmology.modules.diagnose.service.component.IDeviceService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.DeviceVo;
/**
 * 设备Controller
 * @author Stanley
 *
 */
@Controller
@RequestMapping( "/device" )
public class DeviceController {

	private static Logger logger = LoggerFactory.getLogger(DeviceController.class);
	@Resource
	private IDeviceService deviceService;
	@Resource
	private IContentPublisher contentPublisher;
	@Value(value="${imgRes}")
	private String imgRes;
	@Value(value="${sitepath}")
	private String sitepath;
	/**
	 * 设备管理页面
	 * @param systemUserId
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( "list" )
	public ModelAndView toDeviceList(ModelMap modelMap)
			throws Exception
	{
		return new ModelAndView("diagnose/device_dataList");
	}
	
	/**
	 * 
	 * 分页查询设备列表
	 * 
	 * @param queryVo
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping( value = "/getData", produces = "text/html;charset=UTF-8" )
	public String deviceList(DeviceVo queryVo,
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
			return  JsonUtils.toJson(deviceService.pagedDevices(queryVo));

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("查询设备列表异常:" + e.getMessage(),e);
			return null;
		}
	}
	/**
	 * 新增设备
	 * @param deviceVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping( "add" )
	public String addDevice(DeviceVo deviceVo,ModelMap modelMap)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			deviceService.addDevice(deviceVo);
			map.put("result", true);
			map.put("message", "添加成功！");
			logger.info("添加设备成功，设备名称："+deviceVo.getDeviceName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("message", "添加失败！");
			logger.error("添加设备失败："+e.getMessage(),e);
		}

		return JsonUtils.toJson(map);
	}
	/**
	 * 修改设备
	 * 
	 * @param deviceVo
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public String updateDevice(DeviceVo deviceVo, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			deviceService.updateDevice(deviceVo);
			map.put("result", true);
			map.put("message", "修改成功！");
			logger.info("修改设备成功，设备名称：" + deviceVo.getDeviceName());
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "修改失败！");
			logger.error("修改设备失败：" + e.getMessage(), e);
		}
		
		return JsonUtils.toJson(map);
	}
	@ResponseBody
	@RequestMapping(value="deleteImg", produces = "text/json;charset=UTF-8")
	public String deleteImg(String id,String url) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.contentPublisher.deleteFile("");
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
            String filePath = request.getSession().getServletContext().getRealPath("") +"images" + File.separator;
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
               String url= newUploadFileName;
                imgFiles.add(url);
                imageMap.put("images", "ophthalmology/"+newUploadFileName);
       	         // 发布图片至图片服务器
       	        this.contentPublisher.synchronousPublish(filePath, imgFiles);
             } catch (Exception e){
            	e.printStackTrace();
            }
        }
        
        // 发布图片至图片服务器
        return JsonUtils.toJson(imageMap);
    }
}
