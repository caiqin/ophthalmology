package com.uniclans.ophthalmology.modules.permission.service.model;

import java.io.Serializable;

public class UserRoleVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8850103324855680051L;

	private String id;

	private String userId;

	private String roleId;

	private String enabled;

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	
}
