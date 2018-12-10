package com.uniclans.ophthalmology.modules.diagnose.service.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 检查记录Vo
 * 
 * @author Stanley
 *
 */
public class TestRecordVo extends PageVo {
	private String id;// `ID` 'id',
	private String testRecordId;// `TEST_REC_ID` '检查记录id',
	private String patientId;// `PATIENT_ID` '检查用户ID',
	private String patientName;// `PATIENT_ID` '检查用户ID',
	private String hospitalId;// `HOSPITAL_ID` '检查医院ID',
	private String hospitalName;// `HOSPITAL_ID` '检查医院ID',
	private String deviceId;// `DEVICE_ID` '检查设备ID',
	private Date testTime; // `TEST_TIME` '检查时间',
	private String testPic; // `TEST_PIC` '原始检查照片存放位置',
	private String aiResult; // `AI_RESULT` 'AI判别结果',
	private String aiResultDescription; // `AI_RESULT_description` 'AI判别结果',
	private String diagnoseState; // `DIAGNOSE_STATE`  '医生诊断状态，0未诊断，1已诊断，待复核'，2已诊断,
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date endTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestRecordId() {
		return testRecordId;
	}

	public void setTestRecordId(String testRecordId) {
		this.testRecordId = testRecordId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public String getTestPic() {
		return testPic;
	}

	public void setTestPic(String testPic) {
		this.testPic = testPic;
	}

	public String getAiResult() {
		return aiResult;
	}

	public void setAiResult(String aiResult) {
		this.aiResult = aiResult;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDiagnoseState() {
		return diagnoseState;
	}

	public void setDiagnoseState(String diagnoseState) {
		this.diagnoseState = diagnoseState;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAiResultDescription() {
		return aiResultDescription;
	}

	public void setAiResultDescription(String aiResultDescription) {
		this.aiResultDescription = aiResultDescription;
	}
	

}
