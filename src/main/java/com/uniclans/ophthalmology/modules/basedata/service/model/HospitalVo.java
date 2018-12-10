package com.uniclans.ophthalmology.modules.basedata.service.model;

import java.util.HashMap;
import java.util.Map;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 医院和社康信息表
 * 
 * @author Stanley
 *
 */
public class HospitalVo extends PageVo{
	private String id;// `ID` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String hospitalId;// `HOSPITAL_ID` varchar(32) NOT NULL COMMENT '医院编号',
	private String hospitalName;// `HOSPITAL_NAME` varchar(32) DEFAULT NULL COMMENT '医院名称',
	private String hospitalAddress;// `HOSPITAL_ADD` varchar(32) DEFAULT NULL COMMENT '医院地址',
	private String hospitalIntroduce;// `HOSPITAL_INTRO` varchar(256) DEFAULT NULL COMMENT '医院介绍',
	private int level;//级别
	private String parentNo;//上级医院编号
	private String parentName;//上级医院名称
	private String url;//优惠二维码
	private String code;//优惠编号
	private String status;//二维码状态0启用1禁用
	
	
	
	private Map<String,HospitalVo> child = new HashMap<String,HospitalVo>();
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getHospitalAddress() {
		return hospitalAddress;
	}

	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}

	public String getHospitalIntroduce() {
		return hospitalIntroduce;
	}

	public void setHospitalIntroduce(String hospitalIntroduce) {
		this.hospitalIntroduce = hospitalIntroduce;
	}

	
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	
	public Map<String, HospitalVo> getChild() {
		return child;
	}

	public void setChild(Map<String, HospitalVo> child) {
		this.child = child;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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
