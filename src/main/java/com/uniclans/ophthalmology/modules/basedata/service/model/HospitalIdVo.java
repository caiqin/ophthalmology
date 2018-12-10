package com.uniclans.ophthalmology.modules.basedata.service.model;

import java.util.Date;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

public class HospitalIdVo extends PageVo{
	
	private String id;// `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String hospitalId;//ID
	private Date startTime;
	private Date endTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
