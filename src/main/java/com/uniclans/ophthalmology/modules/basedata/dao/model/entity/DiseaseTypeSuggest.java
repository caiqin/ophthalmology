package com.uniclans.ophthalmology.modules.basedata.dao.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 疾病类型表
 * 
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_disease_type_suggest")
public class DiseaseTypeSuggest {
	private String id;// `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String diseaseTypeId;// `DISEASE_TYPE_ID` varchar(32) DEFAULT NULL COMMENT '疾病类别编号',
	private String diseaseTypeName;// `DISEASE_TYPE_ID` varchar(32) DEFAULT NULL COMMENT '疾病类别编号',
	private String suggestId;// `suggest_id` varchar(32) DEFAULT NULL COMMENT '疾病类别名称',
	private String suggestContent;// `suggest_content` varchar(128) DEFAULT NULL COMMENT '描述',

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

	@Column(name = "DISEASE_TYPE_ID")
	public String getDiseaseTypeId() {
		return diseaseTypeId;
	}

	public void setDiseaseTypeId(String diseaseTypeId) {
		this.diseaseTypeId = diseaseTypeId;
	}

	@Column(name = "suggest_id")
	public String getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(String suggestId) {
		this.suggestId = suggestId;
	}

	@Column(name = "suggest_content")
	public String getSuggestContent() {
		return suggestContent;
	}

	public void setSuggestContent(String suggestContent) {
		this.suggestContent = suggestContent;
	}

	@Column(name = "disease_type_name")
	public String getDiseaseTypeName() {
		return diseaseTypeName;
	}

	public void setDiseaseTypeName(String diseaseTypeName) {
		this.diseaseTypeName = diseaseTypeName;
	}


}
