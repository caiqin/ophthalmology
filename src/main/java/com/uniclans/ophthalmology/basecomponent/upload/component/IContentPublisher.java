package com.uniclans.ophthalmology.basecomponent.upload.component;

import java.util.List;

import com.uniclans.ophthalmology.basecomponent.upload.common.WebServerConfig;


public interface IContentPublisher {
	
	/**
	 * 同步传输 同步文件到资源服务器
	 * @param localpath 本地文件绝对路径，如：d:/html
	 * @param files 相对路径文件列表，如：/img/a.jpg
	 */
	public void synchronousPublish(String localpath, List<String> files)throws Exception ;
	/**
	 * 异步传输 同步文件到资源服务器
	 * @param localpath 本地文件绝对路径，如：d:/html
	 * @param files 相对路径文件列表，如：/img/a.jpg
	 */
	public void asynchronousPublish(String localpath, List<String> files);
	
	
	public void deleteFile(String path);
}
