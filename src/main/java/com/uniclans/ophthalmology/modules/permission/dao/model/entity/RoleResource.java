package com.uniclans.ophthalmology.modules.permission.dao.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/** 
 * 角色权限中间表
 * @ClassName: RolePemossion 
 * @Description: TODO
 *  
 */

@Entity
@Table(name = "tbl_system_role_resource")
public class RoleResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5190063187530517563L;
	
	private String id;
	
	private String roleId;
	
	private String resourceId;
	
	private Integer enabled;

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

	@Column(name = "role_id")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "resource_id")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "enabled")
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	

}
