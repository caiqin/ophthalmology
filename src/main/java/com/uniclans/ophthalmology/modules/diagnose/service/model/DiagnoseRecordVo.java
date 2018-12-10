package com.uniclans.ophthalmology.modules.diagnose.service.model;

import java.util.Date;

import javax.persistence.Column;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 医生确诊记录表Vo
 * 
 * @author Stanley
 *
 */
public class DiagnoseRecordVo extends PageVo {
	private String id;// `ID`'id',
	private String diagnoseRecId;// `DIAGNOSE_REC_ID` '诊断记录编号',
	private String doctorId;// `DOCTOR_ID` '医生编号',
	private String doctorName;// `DOCTOR_ID` '医生编号',
	private String testRecordId;// `TEST_REC_ID` '检查记录Id',
	private String resultRemark;// `RESULT_REMARK` '结果备注',
	private String patientId;// `PATIENT_ID` '患者编号',
	private String patientName;// `PATIENT_ID` '患者编号',
	private Date diagnoseTime;// `DIAGNOSE_TIME` '确诊时间',
	private String diseaseLeft;//disease与疾病类型表对应，多个疾病用逗号分隔
	private String diseaseRight;//disease与疾病类型表对应，多个疾病用逗号分隔
	private String suggest;//建议
	private String hospitalName;//检查医院
	private String startTime;
	private String endTime;
	private String aiResult;
	private String diagnoseResult;
	
	private TestRecordVo testRecordVo;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiagnoseRecId() {
		return diagnoseRecId;
	}

	public void setDiagnoseRecId(String diagnoseRecId) {
		this.diagnoseRecId = diagnoseRecId;
	}

	@Column(name = "DOCTOR_ID")
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getTestRecordId() {
		return testRecordId;
	}

	public void setTestRecordId(String testRecordId) {
		this.testRecordId = testRecordId;
	}

	public String getResultRemark() {
		return resultRemark;
	}

	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Date getDiagnoseTime() {
		return diagnoseTime;
	}

	public void setDiagnoseTime(Date diagnoseTime) {
		this.diagnoseTime = diagnoseTime;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	

	public String getDiseaseLeft() {
		return diseaseLeft;
	}

	public void setDiseaseLeft(String diseaseLeft) {
		this.diseaseLeft = diseaseLeft;
	}

	public String getDiseaseRight() {
		return diseaseRight;
	}

	public void setDiseaseRight(String diseaseRight) {
		this.diseaseRight = diseaseRight;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public TestRecordVo getTestRecordVo() {
		return testRecordVo;
	}

	public void setTestRecordVo(TestRecordVo testRecordVo) {
		this.testRecordVo = testRecordVo;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getAiResult() {
		return aiResult;
	}

	public void setAiResult(String aiResult) {
		this.aiResult = aiResult;
	}

	public String getDiagnoseResult() {
		return diagnoseResult;
	}

	public void setDiagnoseResult(String diagnoseResult) {
		this.diagnoseResult = diagnoseResult;
	}

}
