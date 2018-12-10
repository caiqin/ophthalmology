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
 * 用户角色
 * 
 * @ClassName: SystemRole
 * @Description: TODO
 * 
 */

@Entity
@Table(name = "tbl_system_role")
public class SystemRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5971726724463884510L;

	private String id;

	private String roleName;

	/**
	 * 角色类型 :1,平台用户角色;2,供应商用户角色
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
	private String deleteFlag = "1";

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

	@Column(name = "role_name", nullable = false, length = 32)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_type")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Column(name = "is_valid")
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
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

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "delete_flag")
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
