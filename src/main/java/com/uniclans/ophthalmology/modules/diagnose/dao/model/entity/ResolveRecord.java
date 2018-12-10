package com.uniclans.ophthalmology.modules.diagnose.dao.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 患者检查结果分配记录表
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_resolve_rec")
public class ResolveRecord {
	private String id;// `ID`  'id',
	private String resolveRecordId;// `RESOLVE_REC_ID`  '分配记录编号',
	private String patientId;// `PATIENT_ID`  '患者编号',
	private String testRecordId;// `TEST_REC_ID`  '检查记录编号',
	private String hospitalId;// `HOSPITAL_ID`  '医院编号',
	private String doctorId;// `DOCTOR_ID`  '医生编号',
	private Date resolveTime;// `RESOLVE_TIME`  '诊断时间',
	private String resolveUserId;// `RESOLVE_USER_ID`  '分派报告给医生的用户ID，系统分配的是默认值0',
	private String diagnoseState;// `DIAGNOSE_STATE`  '诊断状态，0为待诊断，1为诊断完成',
	
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
	@Column(name = "RESOLVE_REC_ID")
	public String getResolveRecordId() {
		return resolveRecordId;
	}
	public void setResolveRecordId(String resolveRecordId) {
		this.resolveRecordId = resolveRecordId;
	}
	@Column(name = "PATIENT_ID")
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	@Column(name = "TEST_REC_ID")
	public String getTestRecordId() {
		return testRecordId;
	}
	public void setTestRecordId(String testRecordId) {
		this.testRecordId = testRecordId;
	}
	@Column(name = "HOSPITAL_ID")
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	@Column(name = "DOCTOR_ID")
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	@Column(name = "RESOLVE_TIME")
	public Date getResolveTime() {
		return resolveTime;
	}
	public void setResolveTime(Date resolveTime) {
		this.resolveTime = resolveTime;
	}
	@Column(name = "RESOLVE_USER_ID")
	public String getResolveUserId() {
		return resolveUserId;
	}
	public void setResolveUserId(String resolveUserId) {
		this.resolveUserId = resolveUserId;
	}
	@Column(name = "DIAGNOSE_STATE")
	public String getDiagnoseState() {
		return diagnoseState;
	}
	public void setDiagnoseState(String diagnoseState) {
		this.diagnoseState = diagnoseState;
	}

	
}
