package com.uniclans.ophthalmology.modules.permission.service.model;

import java.util.Date;

public class ResourceQueryVo
{

	private String	startDate;
	private String	endDate;
	private String	id;
	private String	resId;

	private String	resName;

	private String	resUrl;

	private Integer		level;

	private String	supperResId;

	private String	resType;

	private Integer		seqNum;

	private Integer		isShow;

	private Date	createTime;

	private String	remark;

	/**
	 * 删除标识:0,已删除;1,未删除
	 */
	private Integer		deleteFlag;

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

	public String getResName()
	{
		return resName;
	}

	public void setResName(String resName)
	{
		this.resName = resName;
	}

	public String getResUrl()
	{
		return resUrl;
	}

	public void setResUrl(String resUrl)
	{
		this.resUrl = resUrl;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
	}

	public String getSupperResId()
	{
		return supperResId;
	}

	public void setSupperResId(String supperResId)
	{
		this.supperResId = supperResId;
	}

	public String getResType()
	{
		return resType;
	}

	public void setResType(String resType)
	{
		this.resType = resType;
	}

	public Integer getSeqNum()
	{
		return seqNum;
	}

	public void setSeqNum(Integer seqNum)
	{
		this.seqNum = seqNum;
	}

	public Integer getIsShow()
	{
		return isShow;
	}

	public void setIsShow(Integer isShow)
	{
		this.isShow = isShow;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Integer getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	

}
