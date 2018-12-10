package com.uniclans.ophthalmology.modules.permission.service.model;

import java.io.Serializable;

public class SystemRoleResourceVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2868487540254433959L;

	private String id;

	private String roleId;

	private String resourceId;

	private Integer enabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
	
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	

}
