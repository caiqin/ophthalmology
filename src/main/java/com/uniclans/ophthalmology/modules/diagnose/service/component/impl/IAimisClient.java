package com.uniclans.ophthalmology.modules.diagnose.service.component.impl;

import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;

import com.tencent.aimis.vo.GetAiResultRsp;
import com.tencent.aimis.vo.ImagesInfo;
import com.tencent.aimis.vo.StudyUploadReq;
import com.tencent.aimis.vo.StudyUploadRsp;

public interface IAimisClient {

	public StudyUploadRsp studyUpload(StudyUploadReq req, ImagesInfo leftEye, ImagesInfo rightEye)
			throws ConnectTimeoutException, SocketTimeoutException, Exception;

	public GetAiResultRsp getAiResult(String id) throws ConnectTimeoutException, SocketTimeoutException, Exception;
}
