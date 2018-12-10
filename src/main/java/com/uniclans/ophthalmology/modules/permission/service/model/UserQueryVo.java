package com.uniclans.ophthalmology.modules.permission.service.model;

public class UserQueryVo
{

	private String	startDate;
	private String	endDate;
	private String	userName;
	private String	loginName;
	private String	userState;
	private String	loginType;

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

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getUserState()
	{
		return userState;
	}

	public void setUserState(String userState)
	{
		this.userState = userState;
	}

	public String getLoginType()
	{
		return loginType;
	}

	public void setLoginType(String loginType)
	{
		this.loginType = loginType;
	}

}
