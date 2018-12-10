package com.uniclans.ophthalmology.modules.permission.dao.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/** 
 * 后台用户
 * @ClassName: SystemUser 
 * @Description: TODO
 *  
 */

@Entity
@Table(name = "tbl_system_user")
public class SystemUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6859906792580520940L;
	/**
	 * 用户ID
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户名
	 */
	private String userNo;
	/**
	 * 性别:0,女;1,男
	 */
	private String sex = "1";
	
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 登录密码
	 */
	private String loginPassword;
	/**
	 * 手机
	 */
	private String mobilePhone;
	/**
	 * 电话
	 */
	private String telPhone;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * QQ号码
	 */
	private String QQNum;
	/**
	 * 用户状态:0,正常;1,锁定
	 */
	private String userState = "0";
	
	/**
	 * 用户类型:1,系统用户;2,平台用户
	 */
	private String loginType;
	
	/**
	 * 机构编号
	 */
	private String organId;
	/**
	 * 岗位编号
	 */
	private String positionNo;

	/**
	 * 账户创建时间
	 */
	private Date createDate;
	/**
	 * 删除标识:0,已删除;1,未删除
	 */
	private String deleteFlag ="0";
	private String doctorId;//医生编号
	private String doctorName;//医生姓名


	@GenericGenerator(name = "generator", strategy = "uuid.hex")
    @GeneratedValue(generator = "generator")
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "sex", nullable = false, length = 2)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "login_name")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "login_password")
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name = "mobile_phone")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "TEL_PHONE")
	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "QQ_num")
	public String getQQNum() {
		return QQNum;
	}

	public void setQQNum(String qQNum) {
		QQNum = qQNum;
	}


	@Column(name = "user_state")
	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "delete_flag")
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "login_type")
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	@Column(name = "organ_id")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	@Column(name = "position_no")
	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	@Column(name = "user_no")
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "doctor_id")
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "doctor_name")
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Override
	public String toString() {
		return "SystemUser [id=" + id + ", userName=" + userName + ", userNo=" + userNo + ", sex=" + sex
				+ ", loginName=" + loginName + ", loginPassword=" + loginPassword + ", mobilePhone=" + mobilePhone
				+ ", telPhone=" + telPhone + ", email=" + email + ", QQNum=" + QQNum + ", userState=" + userState
				+ ", loginType=" + loginType + ", organId=" + organId + ", positionNo=" + positionNo + ", createDate="
				+ createDate + ", deleteFlag=" + deleteFlag + "]";
	}
	
	

}
