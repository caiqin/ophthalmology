package com.uniclans.ophthalmology.basecomponent.upload.common;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class CmsConfig {

	/**
	 * 图片服务器
	 */
	private List<WebServerConfig> webServers;
	
	
	public List<WebServerConfig> getWebServers() {
		return webServers;
	}
	
	public void setWebServers(List<WebServerConfig> webServers) {
		this.webServers = webServers;
	}
	
}
