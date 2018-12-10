package com.uniclans.ophthalmology.modules.permission.dao.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_system_area")
public class SysRegion implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String tNo;
	
	private Integer tLevel;
	
	private Integer sort;
	
	private String parentNo;

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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "t_no",unique = true)   
	public String gettNo() {
		return tNo;
	}

	public void settNo(String tNo) {
		this.tNo = tNo;
	}


	@Column(name = "t_level")
	public Integer gettLevel() {
		return tLevel;
	}

	public void settLevel(Integer tLevel) {
		this.tLevel = tLevel;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "parent_no")
	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
}
