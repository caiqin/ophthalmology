package com.uniclans.ophthalmology.modules.basedata.service.model;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 疾病类型表
 * 
 * @author Stanley
 *
 */
public class DiseaseTypeVo extends PageVo{
	private String id;// `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String diseaseTypeId;// `DISEASE_TYPE_ID` varchar(32) DEFAULT NULL COMMENT '疾病类别编号',
	private String diseaseTypeName;// `DISEASE_TYPE_NAME` varchar(32) DEFAULT NULL COMMENT '疾病类别名称',
	private String diseaseTypeDesc;// `DISEASE_TYPE_DESC` varchar(128) DEFAULT NULL COMMENT '描述',

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

	public String getDiseaseTypeName() {
		return diseaseTypeName;
	}

	public void setDiseaseTypeName(String diseaseTypeName) {
		this.diseaseTypeName = diseaseTypeName;
	}

	public String getDiseaseTypeDesc() {
		return diseaseTypeDesc;
	}

	public void setDiseaseTypeDesc(String diseaseTypeDesc) {
		this.diseaseTypeDesc = diseaseTypeDesc;
	}

	@Override
	public String toString() {
		return "DiseaseType [id=" + id + ", diseaseTypeId=" + diseaseTypeId + ", diseaseTypeName=" + diseaseTypeName
				+ ", diseaseTypeDesc=" + diseaseTypeDesc + "]";
	}

}
