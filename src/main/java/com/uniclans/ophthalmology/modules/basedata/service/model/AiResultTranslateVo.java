package com.uniclans.ophthalmology.modules.basedata.service.model;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

public class AiResultTranslateVo extends PageVo{
	
	private String id;// `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String aiCode;//ai编号
	private String description;//描述
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getAiCode() {
		return aiCode;
	}

	public void setAiCode(String aiCode) {
		this.aiCode = aiCode;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
