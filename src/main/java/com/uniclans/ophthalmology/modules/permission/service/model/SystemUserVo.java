package com.uniclans.ophthalmology.modules.permission.service.model;

import java.io.Serializable;
import java.util.Date;

import com.uniclans.ophthalmology.basecomponent.utils.PageQuery;

public class SystemUserVo extends PageQuery implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3601822223164597414L;
	/**
	 * 用户ID
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String userName;
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
	private String deleteFlag;
	/**
	 * 机构编号
	 */
	private String organId;
	
	private String userNo;
	
	private String customName;
	/**
	 * 极光别名
	 */
	private String jgAlias;
	/**
	 * 验证码类型 1、注册 2、找回密码 3、修改密码
	 */
	private Integer identifyingCodeType;
	
	private String identifyingCode;

	private String token;
	private String base64Img;
	private String oldPassword;
	private String doctorId;//医生编号
	private String doctorName;//医生姓名
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getJgAlias() {
		return jgAlias;
	}
	public void setJgAlias(String jgAlias) {
		this.jgAlias = jgAlias;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setIdentifyingCodeType(Integer identifyingCodeType) {
		this.identifyingCodeType = identifyingCodeType;
	}
	public String getBase64Img() {
		return base64Img;
	}
	public void setBase64Img(String base64Img) {
		this.base64Img = base64Img;
	}
	public String getIdentifyingCode() {
		return identifyingCode;
	}
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}
	public int getIdentifyingCodeType() {
		return identifyingCodeType;
	}
	public void setIdentifyingCodeType(int identifyingCodeType) {
		this.identifyingCodeType = identifyingCodeType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQQNum() {
		return QQNum;
	}
	public void setQQNum(String qQNum) {
		QQNum = qQNum;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
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
	
	
}
