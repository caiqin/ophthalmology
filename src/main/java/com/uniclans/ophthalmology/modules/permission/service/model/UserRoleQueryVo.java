package com.uniclans.ophthalmology.modules.permission.service.model;

import java.io.Serializable;

public class UserRoleQueryVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -231469522901553973L;

	private String id;

	private String userId;

	private String userName;
	
	private String roleId;
	
	private String roleName;

	private String isvalid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	
	
	
}
