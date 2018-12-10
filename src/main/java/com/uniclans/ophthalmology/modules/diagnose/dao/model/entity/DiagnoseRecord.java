package com.uniclans.ophthalmology.modules.diagnose.dao.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 医生确诊记录表
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_diagnose_rec")
public class DiagnoseRecord {
	private String id;// `ID`'id',
	private String diagnoseRecId;// `DIAGNOSE_REC_ID` '诊断记录编号',
	private String doctorId;// `DOCTOR_ID` '医生编号',
	private String doctorName;// `DOCTOR_ID` '医生编号',
	private String testRecordId;// `TEST_REC_ID`  '与疾病类型表对应，多个疾病用逗号分隔',
	private String resultRemark;// `RESULT_REMARK`  '结果备注',
	private String patientId;// `PATIENT_ID`  '患者编号',
	private String patientName;// `PATIENT_ID` '患者编号',
	private Date diagnoseTime;// `DIAGNOSE_TIME`  '确诊时间',
	private String suggest;//建议	
	private String diseaseLeft;//disease与疾病类型表对应，多个疾病用逗号分隔
	private String diseaseRight;//disease与疾病类型表对应，多个疾病用逗号分隔
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
	@Column(name = "DIAGNOSE_REC_ID")
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
	@Column(name = "TEST_REC_ID")
	public String getTestRecordId() {
		return testRecordId;
	}
	public void setTestRecordId(String testRecordId) {
		this.testRecordId = testRecordId;
	}
	@Column(name = "RESULT_REMARK")
	public String getResultRemark() {
		return resultRemark;
	}
	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
	}
	@Column(name = "PATIENT_ID")
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	@Column(name = "DIAGNOSE_TIME")
	public Date getDiagnoseTime() {
		return diagnoseTime;
	}
	public void setDiagnoseTime(Date diagnoseTime) {
		this.diagnoseTime = diagnoseTime;
	}
	@Column(name = "DOCTOR_NAME")
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	@Column(name = "PATIENT_NAME")
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	@Column(name = "disease_left")
	public String getDiseaseLeft() {
		return diseaseLeft;
	}
	public void setDiseaseLeft(String diseaseLeft) {
		this.diseaseLeft = diseaseLeft;
	}
	@Column(name = "disease_right")
	public String getDiseaseRight() {
		return diseaseRight;
	}
	public void setDiseaseRight(String diseaseRight) {
		this.diseaseRight = diseaseRight;
	}
	@Column(name = "suggest")
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	
	
}
