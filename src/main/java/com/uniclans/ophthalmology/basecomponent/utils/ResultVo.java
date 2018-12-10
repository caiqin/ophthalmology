package com.uniclans.ophthalmology.basecomponent.utils;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","operations","roles","menus"})
public class ResultVo implements Serializable{
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	private boolean result;
	private String message;
	private Object body;
	private long nowTime=System.currentTimeMillis();
	public long getNowTime() {
		return nowTime;
	}

	public void setNowTime(long nowTime) {
		this.nowTime = nowTime;
	}

	public ResultVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public ResultVo(boolean result){
		this.result=result;
		this.body="";
	}
	
	public ResultVo(boolean result,Object body){
		this.result=result;
		this.body=body;
	}

	
}

