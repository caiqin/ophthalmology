package com.uniclans.ophthalmology.modules.permission.dao.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * 系统资源
 * @ClassName: SystemResource 
 * @Description: TODO
 *  
 */

@Entity
@Table(name = "tbl_system_resource")
public class SystemResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2653988064407507985L;
	
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

	@Column(name = "res_name")
	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	@Column(name = "res_url")
	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	@Column(name = "res_level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "supper_res_id")
	public String getSupperResId() {
		return supperResId;
	}

	public void setSupperResId(String supperResId) {
		this.supperResId = supperResId;
	}

	@Column(name = "res_type")
	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	@Column(name = "seq_num")
	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	@Column(name = "is_show")
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "delete_flag")
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Column(name = "res_code")
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	
}
