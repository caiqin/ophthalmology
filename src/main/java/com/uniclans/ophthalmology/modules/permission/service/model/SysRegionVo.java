package com.uniclans.ophthalmology.modules.permission.service.model;

import java.io.Serializable;
import java.util.List;

public class SysRegionVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String tNo;

	private String isleaf;

	private Integer child;

	private Integer tLevel;

	private Integer sort;


	private String region;

	private String parentNo;

	
	private List<SysRegionVo> sysRegionVos;
	
	public List<SysRegionVo> getSysRegionVos() {
		return sysRegionVos;
	}

	public void setSysRegionVos(List<SysRegionVo> sysRegionVos) {
		this.sysRegionVos = sysRegionVos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String gettNo() {
		return tNo;
	}

	public void settNo(String tNo) {
		this.tNo = tNo;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public Integer getChild() {
		return child;
	}

	public void setChild(Integer child) {
		this.child = child;
	}

	public Integer gettLevel() {
		return tLevel;
	}

	public void settLevel(Integer tLevel) {
		this.tLevel = tLevel;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

}
