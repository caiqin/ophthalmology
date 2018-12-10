package com.uniclans.ophthalmology.modules.permission.dao.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * 后台用户角色中间表
 * @ClassName: UserRole 
 * @Description: TODO
 *  
 */

@Entity
@Table(name = "tbl_system_user_role")
public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9015239251534294624L;
	
	private String id;
	
	private String userId;
	
	private String roleId;
	
	private String enabled;

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

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "role_id")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "enabled")
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	
	

}
