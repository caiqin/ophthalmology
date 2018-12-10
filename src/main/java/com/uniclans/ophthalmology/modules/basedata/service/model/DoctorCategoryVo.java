package com.uniclans.ophthalmology.modules.basedata.service.model;

import com.uniclans.ophthalmology.basecomponent.utils.PageVo;

/**
 * 医生专长分类
 * 
 * @author Stanley
 *
 */
public class DoctorCategoryVo extends PageVo{
	private String id;// id` varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
	private String catId;// CAT_ID` varchar(32) NOT NULL COMMENT '类别编号',
	private String catName;// CAT_NAME` varchar(32) DEFAULT NULL COMMENT '名称',
	private String catDesc;// CAT_DESC` varchar(128) DEFAULT NULL COMMENT '描述',

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

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
