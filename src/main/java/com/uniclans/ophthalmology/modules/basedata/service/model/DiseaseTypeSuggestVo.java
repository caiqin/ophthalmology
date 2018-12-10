package com.uniclans.ophthalmology.modules.basedata.service.model;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 疾病类型表
 * 
 * @author Stanley
 *
 */
public class DiseaseTypeSuggestVo extends PageVo{
	private String id;// `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String diseaseTypeId;// `DISEASE_TYPE_ID` varchar(32) DEFAULT NULL COMMENT '疾病类别编号',
	private String diseaseTypeName;// `DISEASE_TYPE_ID` varchar(32) DEFAULT NULL COMMENT '疾病类别编号',
	private String suggestId;// `suggest_id` varchar(32) DEFAULT NULL COMMENT '疾病类别名称',
	private String suggestContent;// `suggest_content` varchar(128) DEFAULT NULL COMMENT '描述',

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiseaseTypeId() {
		return diseaseTypeId;
	}

	public void setDiseaseTypeId(String diseaseTypeId) {
		this.diseaseTypeId = diseaseTypeId;
	}

	public String getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(String suggestId) {
		this.suggestId = suggestId;
	}

	public String getSuggestContent() {
		return suggestContent;
	}

	public void setSuggestContent(String suggestContent) {
		this.suggestContent = suggestContent;
	}

	public String getDiseaseTypeName() {
		return diseaseTypeName;
	}

	public void setDiseaseTypeName(String diseaseTypeName) {
		this.diseaseTypeName = diseaseTypeName;
	}


}
