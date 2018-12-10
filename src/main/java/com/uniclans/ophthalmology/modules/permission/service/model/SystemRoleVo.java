package com.uniclans.ophthalmology.modules.permission.service.model;

import java.io.Serializable;
import java.util.Date;

public class SystemRoleVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -956108132583719472L;

	private String id;

	private String roleName;

	/**
	 * 权限类型 1 平台权限 2供应商权限
	 */
	private String roleType;

	private String isValid;

	private Date createTime;

	private Date updateTime;
	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除标识:0,已删除;1,未删除
	 */
	private String deleteFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
