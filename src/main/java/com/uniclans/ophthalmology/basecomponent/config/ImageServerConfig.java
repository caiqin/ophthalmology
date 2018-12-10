package com.uniclans.ophthalmology.basecomponent.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uniclans.ophthalmology.basecomponent.upload.common.CmsConfig;
import com.uniclans.ophthalmology.basecomponent.upload.common.WebServerConfig;

@Configuration
public class ImageServerConfig {

	@Value(value = "${cms.sftp.host}")
	private String host;
	
	@Value(value = "${cms.sftp.username}")
	private String username;
	
	@Value(value = "${cms.sftp.passwd}")
	private String passwd;
	
	@Value(value = "${cms.sftp.sync}")
	private boolean sync;
	
	@Value(value = "${cms.sftp.port}")
	private int port;
	
	@Value(value = "${cms.sftp.sitepath}")
	private String sitepath;

	@Bean
	public CmsConfig cmsConfig() {
		CmsConfig cmsConfig = new CmsConfig();
		List<WebServerConfig> webServers = new ArrayList<WebServerConfig>();
		WebServerConfig webServer = new WebServerConfig();
		webServer.setHost(host);
		webServer.setPasswd(passwd);
		webServer.setPort(port);
		webServer.setSync(sync);
		webServer.setUsername(username);
		webServer.setSitepath(sitepath);
		webServers.add(webServer);
		cmsConfig.setWebServers(webServers);
		return cmsConfig;
	}
}
