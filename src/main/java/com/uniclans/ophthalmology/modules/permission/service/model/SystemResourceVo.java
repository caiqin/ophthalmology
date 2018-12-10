package com.uniclans.ophthalmology.modules.permission.service.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SystemResourceVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2727014204412686350L;
	
	public static final String ECOS_RESC_TYPE_MENU="1";
	
	public static final String ECOS_RESC_TYPE_BUTTON="2";
	
	/**
	 * 后台资源类型
	 */
	public static final String ECOS_ADMIN_TYPE="1";
	
	public static final String ECOS_ERP_TYPE="2";

	private String id;

	private String resName;

	private String resUrl;

	private Integer level;

	private String supperResId;

	private String resType;

	private Integer seqNum;

	private Integer isShow;

	private Date createTime;

	private String remark;
	/**
	 * 资源编号
	 */
	private String resCode;
	/**
	 * 删除标识:0,未删除;1,已删除
	 */
	private String deleteFlag;
	
	private Integer haveChild = 0;
	
	private String supperResName;
	private Map<String,SystemResourceVo> child = new HashMap<String,SystemResourceVo>();
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getSupperResId() {
		return supperResId;
	}

	public void setSupperResId(String supperResId) {
		this.supperResId = supperResId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getHaveChild() {
		return haveChild;
	}

	public void setHaveChild(Integer haveChild) {
		this.haveChild = haveChild;
	}

	public String getSupperResName() {
		return supperResName;
	}

	public void setSupperResName(String supperResName) {
		this.supperResName = supperResName;
	}

	public Map<String, SystemResourceVo> getChild() {
		return child;
	}

	public void setChild(Map<String, SystemResourceVo> child) {
		this.child = child;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	@Override
	public String toString() {
		return "SystemResourceVo [id=" + id + ", resName=" + resName + ", resUrl=" + resUrl + ", level=" + level
				+ ", supperResId=" + supperResId + ", resType=" + resType + ", seqNum=" + seqNum + ", isShow=" + isShow
				+ ", createTime=" + createTime + ", remark=" + remark + ", resCode=" + resCode + ", deleteFlag="
				+ deleteFlag + ", haveChild=" + haveChild + ", supperResName=" + supperResName + ", child=" + child
				+ "]";
	}
	
	
	
}

