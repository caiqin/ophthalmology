package com.uniclans.ophthalmology.basecomponent.utils;


import java.io.Serializable;

public class PageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private int pageNo = 1;

    private int pageSize = 10;
    
    private int rows=10;
    
    private int page=1;
    
    private String orderBy;
    
    private String orderName;
    
    

    public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
