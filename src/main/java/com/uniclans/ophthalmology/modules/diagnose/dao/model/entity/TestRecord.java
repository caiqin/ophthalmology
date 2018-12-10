package com.uniclans.ophthalmology.modules.diagnose.dao.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 检查记录表
 * 
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_test_rec")
public class TestRecord {
	private String id;// `ID`  'id',
	private String testRecordId;// `TEST_REC_ID`  '检查记录id',
	private String patientId;// `PATIENT_ID`  '检查用户ID',
	private String patientName;// `PATIENT_ID`  '检查用户ID',
	private String hospitalId;// `HOSPITAL_ID`  '检查医院ID',
	private String hospitalName;// `HOSPITAL_ID`  '检查医院ID',
	private String deviceId;// `DEVICE_ID`  '检查设备ID',
	private Date testTime; // `TEST_TIME`  '检查时间',
	private String testPic; // `TEST_PIC`  '原始检查照片存放位置',
	private String aiResult; // `AI_RESULT`  'AI判别结果',
	private String diagnoseState; // `DIAGNOSE_STATE`  '医生诊断状态，0未诊断，1已诊断，待复核'，2已诊断,
	private String aiResultDescription; // `AI_RESULT_description` 'AI判别结果',
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "TEST_REC_ID")
	public String getTestRecordId() {
		return testRecordId;
	}
	public void setTestRecordId(String testRecordId) {
		this.testRecordId = testRecordId;
	}
	@Column(name = "PATIENT_ID")
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	@Column(name = "HOSPITAL_ID")
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	@Column(name = "DEVICE_ID")
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Column(name = "TEST_TIME")
	public Date getTestTime() {
		return testTime;
	}
	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}
	@Column(name = "TEST_PIC")
	public String getTestPic() {
		return testPic;
	}
	public void setTestPic(String testPic) {
		this.testPic = testPic;
	}
	@Column(name = "AI_RESULT")
	public String getAiResult() {
		return aiResult;
	}
	public void setAiResult(String aiResult) {
		this.aiResult = aiResult;
	}
	@Column(name = "PATIENT_NAME")
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	@Column(name = "HOSPITAL_NAME")
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	@Column(name = "DIAGNOSE_STATE")
	public String getDiagnoseState() {
		return diagnoseState;
	}
	public void setDiagnoseState(String diagnoseState) {
		this.diagnoseState = diagnoseState;
	}
	@Column(name = "ai_result_description")
	public String getAiResultDescription() {
		return aiResultDescription;
	}
	public void setAiResultDescription(String aiResultDescription) {
		this.aiResultDescription = aiResultDescription;
	}
	
	
}
