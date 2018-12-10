package com.uniclans.ophthalmology.modules.basedata.service.model;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 医生实体类
 * 
 * @author Stanley
 *
 */
public class DoctorVo extends PageVo{
	private String id;// ` int(11) NOT NULL COMMENT 'id',;
	private String doctorId;// DOCTOR_ID//` varchar(32) NOT NULL COMMENT '医生编号',
	private String doctorName;// DOCTOR_NAME` varchar(32) DEFAULT NULL COMMENT '医生姓名',
	private String doctorIntroduce;// DOCTOR_INTRO` varchar(256) DEFAULT NULL COMMENT '医生介绍',
	private String hospitalId;// HOSPITAL_ID` varchar(32) DEFAULT NULL COMMENT '医院编号',
	private String hospitalName;// HOSPITAL_NAME` varchar(32) DEFAULT NULL COMMENT '所属医院',
	private String docCatId;// DOC_CAT_ID` varchar(32) DEFAULT NULL COMMENT '医生专长',
	private String docCatName;// DOC_CAT_ID` varchar(32) DEFAULT NULL COMMENT '医生专长名称',
	private String url;// '医生签名照片url',
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorIntroduce() {
		return doctorIntroduce;
	}

	public void setDoctorIntroduce(String doctorIntroduce) {
		this.doctorIntroduce = doctorIntroduce;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDocCatId() {
		return docCatId;
	}

	public void setDocCatId(String docCatId) {
		this.docCatId = docCatId;
	}

	public String getDocCatName() {
		return docCatName;
	}

	public void setDocCatName(String docCatName) {
		this.docCatName = docCatName;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", doctorId=" + doctorId + ", doctorName=" + doctorName + ", doctorIntroduce="
				+ doctorIntroduce + ", hospitalId=" + hospitalId + ", hospitalName=" + hospitalName + ", docCatId="
				+ docCatId + "]";
	}

}
