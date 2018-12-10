package com.uniclans.ophthalmology.modules.basedata.dao.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_ai_result_translate")
public class AiResultTranslate {
	
	private String id;// `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String aiCode;//ai编号
	private String description;//描述
	
	
	
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
	@Column(name = "ai_code")
	public String getAiCode() {
		return aiCode;
	}

	public void setAiCode(String aiCode) {
		this.aiCode = aiCode;
	}
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
