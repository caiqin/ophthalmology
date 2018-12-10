package com.uniclans.ophthalmology.basecomponent.upload.common;

/**
 * 网络服务器
 * @author 
 *
 */
public class WebServerConfig {
	/**
	 * 主机IP
	 */
	private String host;
	/**
	 * 同步端口号，默认22
	 */
	private int port = 22;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String passwd;
	/**
	 * 主机目录
	 */
	private String sitepath;
	/**
	 * 内容是否同步
	 */
	private boolean sync = true;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSitepath() {
		return sitepath;
	}
	public void setSitepath(String sitepath) {
		this.sitepath = sitepath;
	}
	public boolean isSync() {
		return sync;
	}
	public void setSync(boolean sync) {
		this.sync = sync;
	}
}
