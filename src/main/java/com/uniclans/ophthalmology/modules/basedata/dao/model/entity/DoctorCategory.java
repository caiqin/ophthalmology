package com.uniclans.ophthalmology.modules.basedata.dao.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 医生专长分类
 * 
 * @author Stanley
 *
 */
@Entity
@Table(name = "tbl_doc_cat")
public class DoctorCategory {
	private String id;// id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String catId;// CAT_ID` varchar(32) NOT NULL COMMENT '类别编号',
	private String catName;// CAT_NAME` varchar(32) DEFAULT NULL COMMENT '名称',
	private String catDesc;// CAT_DESC` varchar(128) DEFAULT NULL COMMENT '描述',

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

	@Column(name = "CAT_ID")
	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	@Column(name = "CAT_NAME")
	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	@Column(name = "CAT_DESC")
	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	@Override
	public String toString() {
		return "DoctorCategory [id=" + id + ", catId=" + catId + ", catName=" + catName + ", catDesc=" + catDesc + "]";
	}

}
