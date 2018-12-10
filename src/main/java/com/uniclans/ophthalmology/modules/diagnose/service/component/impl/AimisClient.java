package com.uniclans.ophthalmology.modules.diagnose.service.component.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tencent.aimis.Aimis;
import com.tencent.aimis.vo.GetAiResultReq;
import com.tencent.aimis.vo.GetAiResultRsp;
import com.tencent.aimis.vo.ImagesInfo;
import com.tencent.aimis.vo.StudyUploadReq;
import com.tencent.aimis.vo.StudyUploadRsp;
import com.uniclans.ophthalmology.basecomponent.utils.JsonUtils;
@Service
public class AimisClient implements IAimisClient{
	@Value(value = "${tencent.ai.partnerId}")
	private String partnerId;
	@Value(value = "${tencent.ai.token}")
	private String token;
	@Value(value = "${tencent.ai.url}")
	private String url;
	@Value(value = "${tencent.ai.isDebug}")
	private boolean isDebug;


	public StudyUploadRsp studyUpload(StudyUploadReq req,ImagesInfo leftEye,ImagesInfo rightEye)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		Aimis api = new Aimis(partnerId, token, url, isDebug);// 初始化api对象
		System.out.println("start test StudyUpload...");
		// 非空,图片在医院侧编号
		leftEye.setImageId("1");
		// 可空,影像的医院侧内网地址
		leftEye.setUrl("x");
		// 可空,眼底必须上传，描述图像对应的部位，眼底需要传 未知：0，左眼：1， 右眼：2。其他待定
		leftEye.setDescPosition("1");
		// 非空,影像的base64编码内容,文件绝对路径
		// leftEye.setContent(Utils.getImageBase64("/Users/Stanley/Documents/data/od.jpg"));

		// 非空,图片在医院侧编号
		rightEye.setImageId("2");
		// 可空,影像的医院侧内网地址
		rightEye.setUrl("x");
		// 可空,眼底必须上传，描述图像对应的部位，眼底需要传 未知：0，左眼：1， 右眼：2。其他待定
		rightEye.setDescPosition("2");
		// 非空,影像的base64编码内容,文件绝对路径
		// rightEye.setContent(Utils.getImageBase64("/Users/Stanley/Documents/data/os.jpg"));

		List<ImagesInfo> images = new ArrayList<ImagesInfo>();
		images.add(leftEye);
		images.add(rightEye);

		// 非空,上传的影像图片的数组,数组一次不超过5M,不超过20张
		req.setImages(images);
		/*
		 * //非空,患者在医院侧的编号 req.setPatientId("201"); //非空,患者姓名 req.setPatientName("张三");
		 * //非空,患者性别（0未知 1 男 2 女 3 其他） req.setPatientGender("1"); //非空,患者生日，格式2017-08-22
		 * req.setPatientBirthday("2017-07-24"); //非空,本次检查在医院侧的序列号
		 * req.setStudyId("mytest0914");
		 */
		// 非空,检查类型 1 食管癌 2 眼底 3 肠镜
		req.setStudyType("2");
		// 非空,检查名称
		req.setStudyName("眼底筛查");
		// 非空,检查日期，返回1970年1月1日至今的时间戳单位秒
		req.setStudyDate(System.currentTimeMillis() / 1000);
		StudyUploadRsp rsp = api.studyUpload(req);
		System.out.println(JsonUtils.toJson(req));
		System.out.println("response:" + JsonUtils.toJson(rsp));
		int ii = 0;
		while (ii < 10 && rsp != null && 0 != rsp.getCode()) {
			if (rsp != null && 0 == rsp.getCode()) {
				System.out.println("end StudyUpload OK...");
			} else {
				rsp = api.studyUpload(req);
				System.out.println(JsonUtils.toJson(req));
				System.out.println("重新上传后response:" + JsonUtils.toJson(rsp));
				System.out.println("end StudyUpload error...");
			}
			Thread.sleep(1000);
			ii++;
		}
		return rsp;
	}

	public GetAiResultRsp getAiResult(String id)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		Aimis api = new Aimis(partnerId, token, url, isDebug);// 初始化api对象
		System.out.println("start  GetAiResult...");
		GetAiResultReq req = new GetAiResultReq();
		// 非空,检查id
		req.setStudyId(id);
		// 非空,检查类型 1 食管癌 2 眼底 3 肠镜
		req.setStudyType("2");
		GetAiResultRsp rsp = api.getAiResult(req);
		System.out.println("response:" + JSON.toJSONString(rsp));

		if (rsp != null && 0 == rsp.getCode()) {
			System.out.println("end  GetAiResult OK...");
		} else {
			System.out.println("end  GetAiResult error...");
		}
		return rsp;
	}

}
