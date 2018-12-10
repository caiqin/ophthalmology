package com.uniclans.ophthalmology.modules.permission.service.model;

public class SystemRoleQueryVo
{

	private String	startDate;
	private String	endDate;
	private String	id;
	private String	roleName;
	/**
	 * 角色类型 :1,平台用户角色;2,供应商用户角色
	 */
	private String	roleType;
	private Integer		isValid;

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public String getRoleType()
	{
		return roleType;
	}

	public void setRoleType(String roleType)
	{
		this.roleType = roleType;
	}

	public Integer getIsValid()
	{
		return isValid;
	}

	public void setIsValid(Integer isValid)
	{
		this.isValid = isValid;
	}

}
