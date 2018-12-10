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
@Table(name = "tbl_disease_type")
public class DiseaseType {
	private String id;// `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String diseaseTypeId;// `DISEASE_TYPE_ID` varchar(32) DEFAULT NULL COMMENT '疾病类别编号',
	private String diseaseTypeName;// `DISEASE_TYPE_NAME` varchar(32) DEFAULT NULL COMMENT '疾病类别名称',
	private String diseaseTypeDesc;// `DISEASE_TYPE_DESC` varchar(128) DEFAULT NULL COMMENT '描述',

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

	@Column(name = "DISEASE_TYPE_NAME")
	public String getDiseaseTypeName() {
		return diseaseTypeName;
	}

	public void setDiseaseTypeName(String diseaseTypeName) {
		this.diseaseTypeName = diseaseTypeName;
	}

	@Column(name = "DISEASE_TYPE_DESC")
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
