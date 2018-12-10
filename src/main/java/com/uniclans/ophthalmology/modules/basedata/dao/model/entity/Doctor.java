package com.uniclans.ophthalmology.modules.basedata.dao.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 医生实体类
 * 
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_doctor")
public class Doctor {
	private String id;// ` int(11) NOT NULL COMMENT 'id',;
	private String doctorId;// DOCTOR_ID//` varchar(32) NOT NULL COMMENT '医生编号',
	private String doctorName;// DOCTOR_NAME` varchar(32) DEFAULT NULL COMMENT '医生姓名',
	private String doctorIntroduce;// DOCTOR_INTRO` varchar(256) DEFAULT NULL COMMENT '医生介绍',
	private String hospitalId;// HOSPITAL_ID` varchar(32) DEFAULT NULL COMMENT '医院编号',
	private String hospitalName;// HOSPITAL_NAME` varchar(32) DEFAULT NULL COMMENT '所属医院',
	private String docCatId;// DOC_CAT_ID` varchar(32) DEFAULT NULL COMMENT '医生专长',
	private String docCatName;// DOC_CAT_ID` varchar(32) DEFAULT NULL COMMENT '医生专长',
	private String url;// '医生签名照片url',

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

	@Column(name = "DOCTOR_ID")
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "DOCTOR_NAME")
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Column(name = "DOCTOR_INTRO")
	public String getDoctorIntroduce() {
		return doctorIntroduce;
	}

	public void setDoctorIntroduce(String doctorIntroduce) {
		this.doctorIntroduce = doctorIntroduce;
	}

	@Column(name = "HOSPITAL_ID")
	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "HOSPITAL_NAME")
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@Column(name = "DOC_CAT_ID")
	public String getDocCatId() {
		return docCatId;
	}

	public void setDocCatId(String docCatId) {
		this.docCatId = docCatId;
	}

	@Column(name = "DOC_CAT_NAME")
	public String getDocCatName() {
		return docCatName;
	}

	public void setDocCatName(String docCatName) {
		this.docCatName = docCatName;
	}

	@Column(name = "url")
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
