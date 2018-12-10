package com.uniclans.ophthalmology.modules.basedata.dao.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 医院和社康信息表
 * 
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_hospital")
public class Hospital {
	private String id;// `ID` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String hospitalId;// `HOSPITAL_ID` varchar(32) NOT NULL COMMENT '医院编号',
	private String hospitalName;// `HOSPITAL_NAME` varchar(32) DEFAULT NULL COMMENT '医院名称',
	private String hospitalAddress;// `HOSPITAL_ADD` varchar(32) DEFAULT NULL COMMENT '医院地址',
	private String hospitalIntroduce;// `HOSPITAL_INTRO` varchar(256) DEFAULT NULL COMMENT '医院介绍',
	private int level;//级别
	private String parentNo;//上级医院编号
	private String url;//优惠二维码
	private String code;//优惠编号
	private String status;//二维码状态0启用1禁用
	
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

	@Column(name = "HOSPITAL_ADD")
	public String getHospitalAddress() {
		return hospitalAddress;
	}

	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}

	@Column(name = "HOSPITAL_INTRO")
	public String getHospitalIntroduce() {
		return hospitalIntroduce;
	}

	public void setHospitalIntroduce(String hospitalIntroduce) {
		this.hospitalIntroduce = hospitalIntroduce;
	}

	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	@Column(name = "parent_no")
	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Hospital [id=" + id + ", hospitalId=" + hospitalId + ", hospitalName=" + hospitalName
				+ ", hospitalAddress=" + hospitalAddress + ", hospitalIntroduce=" + hospitalIntroduce + "]";
	}

}
