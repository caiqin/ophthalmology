package com.uniclans.ophthalmology.modules.basedata.dao.model.entity;

import java.util.Date;

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
@Table(name = "tbl_patient_info")
public class Patient {
	private String id;// `ID` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String patientId;// `PATIENT_ID` varchar(32) NOT NULL COMMENT '患者编号',
	private String wechatId;// `WECHAT_ID` varchar(32) DEFAULT NULL COMMENT '微信id',
	private String name;// `NAME` varchar(16) DEFAULT NULL COMMENT '姓名',
	private int age;// `AGE` int(11) DEFAULT NULL COMMENT '年龄',
	private String gender;// `GENDER` varchar(2) DEFAULT NULL COMMENT '性别，0男，1女',
	private String cardAddress;// `CARD_ADDRESS` varchar(32) DEFAULT NULL COMMENT '身份证地址',
	private String realAddress;// `REAL_ADDRESS` varchar(32) DEFAULT NULL COMMENT '真实地址',
	private String cardNo;// `CARD_NO` varchar(32) DEFAULT NULL COMMENT '身份证编号',
	private String mobileNo;// `MOBILE_NO` varchar(16) DEFAULT NULL COMMENT '手机号',
	private String codeUrl;//二维码地址
	private Date createTime;
	private Date updateTime;
	private String creator;//创建人
	private Date birthDay;//生日
	private String hospitalId;//所属医院编号
	private String hospitalName;//所属医院名称
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

	@Column(name = "PATIENT_ID")
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@Column(name = "WECHAT_ID")
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AGE")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column(name = "GENDER")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "CARD_ADDRESS")
	public String getCardAddress() {
		return cardAddress;
	}

	public void setCardAddress(String cardAddress) {
		this.cardAddress = cardAddress;
	}

	@Column(name = "REAL_ADDRESS")
	public String getRealAddress() {
		return realAddress;
	}

	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}

	@Column(name = "CARD_NO")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "MOBILE_NO")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "CODE_URL")
	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "birthday")
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "hospital_id")
	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "hospital_name")
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", patientId=" + patientId + ", wechatId=" + wechatId + ", name=" + name + ", age="
				+ age + ", gender=" + gender + ", cardAddress=" + cardAddress + ", realAddress=" + realAddress
				+ ", cardNo=" + cardNo + ", mobileNo=" + mobileNo + "]";
	}

}
