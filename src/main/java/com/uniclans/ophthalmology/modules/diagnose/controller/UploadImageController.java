package com.uniclans.ophthalmology.modules.diagnose.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.uniclans.ophthalmology.basecomponent.upload.component.IContentPublisher;
import com.uniclans.ophthalmology.basecomponent.utils.DateUtils;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
import com.uniclans.ophthalmology.modules.diagnose.service.component.ITestRecordService;
import com.uniclans.ophthalmology.modules.diagnose.service.model.TestRecordVo;

@Controller
@RequestMapping("/image")
public class UploadImageController {

	private static Logger logger = LoggerFactory.getLogger(UploadImageController.class);
	@Value(value="${imgRes}")
	private String imgRes;
	@Value(value="${prePath}")
	private String prePath;
	@Resource
	private IContentPublisher contentPublisher;
	@Resource
	private ITestRecordService testRecordService;
	
	
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	public String fileUpload(HttpServletRequest request,MultipartFile odFile,MultipartFile osFile,String appId,String patientId,String deviceId) throws IllegalStateException, IOException {
		
		Map<String,Object> map = new HashMap<>();
		if (odFile==null||osFile==null) {
			map.put("result", false);
			map.put("message", "文件不能为空");
			logger.info("上传失败，文件为空");
			return JsonUtils.toJson(map);
		}else {
			List<String> imgFiles = new ArrayList<String>();
			String folder = DateUtils.getCurrentDate("yyyyMMddHHmmSS");
			String filePath = request.getSession().getServletContext().getRealPath("");
			String path = prePath+File.separator+"images" + File.separator + folder + File.separator;
			File f1 = new File(filePath+path);
            if (!f1.exists()) {
                f1.mkdirs();
            }
            String fileName = odFile.getOriginalFilename() ;
            String jpgOdFile = fileName.endsWith(".fdt")?fileName.replace(".fdt", ".jpg"):fileName;
			File newFile = new File(filePath + path + jpgOdFile);
			odFile.transferTo(newFile);
			imgFiles.add(path+jpgOdFile);
			String odUrl =  path+jpgOdFile;
			
			fileName = osFile.getOriginalFilename() ;
			String jpgOsFile = (fileName.endsWith(".fdt")?fileName.replace(".fdt", ".jpg"):fileName);
			File nFile = new File(filePath + path + jpgOsFile);
			osFile.transferTo(nFile);
			imgFiles.add(path+jpgOsFile);
			String osUrl =   path+jpgOsFile;
			try {
				this.contentPublisher.synchronousPublish(request.getSession().getServletContext().getRealPath(""), imgFiles);
				TestRecordVo vo = new TestRecordVo();
				vo.setDeviceId(deviceId);
				vo.setPatientId(patientId);
				String testPic = "[{\"id\":\"1\",\"type\":\"R\",\"url\":\""+odUrl+"\"},{\"id\":\"2\",\"type\":\"L\",\"url\":\""+osUrl+"\"}]";
				vo.setTestPic(testPic);
				testRecordService.addTestRecord(vo);
				logger.info("患者："+patientId+"检查图像上传成功，osUrl:"+osUrl+".odUrl:"+odUrl);
			} catch (Exception e) {
				logger.error("上传检查图像失败："+e.getMessage(),e);
			}
		}
		map.put("result", true);
		map.put("message", "文件上传成功");
		return JsonUtils.toJson(map);
	}
public static void main(String[] args) {
	String odUrl = "ophthalmology/OD-COLOR-180829-154536-0001.jpg";
	String osUrl = "ophthalmology/OD-COLOR-180829-154536-0001.jpg";
	String testPic = "[{\"id\":\"1\",\"type\":\"R\",\"url\":\""+odUrl+"\"},{\"id\":\"2\",\"type\":\"L\",\"url\":\""+osUrl+"\"}]";
	System.out.println(testPic);
}
}
