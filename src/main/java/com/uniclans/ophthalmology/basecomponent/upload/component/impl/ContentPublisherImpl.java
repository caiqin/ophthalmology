package com.uniclans.ophthalmology.basecomponent.upload.component.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uniclans.ophthalmology.basecomponent.upload.common.CmsConfig;
import com.uniclans.ophthalmology.basecomponent.upload.common.Ssh2FilePublisher;
import com.uniclans.ophthalmology.basecomponent.upload.common.WebServerConfig;
import com.uniclans.ophthalmology.basecomponent.upload.component.IContentPublisher;

/**
 * 发布线程
 * @author 
 *
 */
@Service
public class ContentPublisherImpl implements IContentPublisher{
	@Resource
	private CmsConfig cmsConfig;
	/**
	 * 主机列表
	 */
	protected List<WebServerConfig> host;
	/**
	 * html文件主机
	 */
	protected List<WebServerConfig> htmlHost;
	
	public void setHost(List<WebServerConfig> host) {
		this.host = host;
	}
	
	public void setHtmlHost(List<WebServerConfig> htmlHost) {
		this.htmlHost = htmlHost;
	}

	public ContentPublisherImpl() {
		super();
	}
	
	/**
	 * 同步传输 同步文件到资源服务器
	 * @param localpath 本地文件绝对路径，如：d:/html
	 * @param htmlFiles 相对路径文件列表，如：/img/a.jpg
	 */
	public void synchronousPublish(String localpath, List<String> files)throws Exception {
		if (host==null) {
			host = cmsConfig.getWebServers();
		}
	//	List<Thread> threads=new ArrayList<Thread>();
		
		for (int i = 0; i < host.size(); i++) {
			if (host.get(i).isSync()) {
				Thread thread=new HostPublisher(host.get(i), localpath, files);
				thread.start();
				thread.join();
				 //threads.add(thread);
			}
		}
		for (int i = 0; i < files.size(); i++) {
         	File file=new File(localpath+files.get(i));
         	file.delete();
		}
	}
	/**
	 * 异步传输 同步文件到资源服务器
	 * @param localpath 本地文件绝对路径，如：d:/html
	 * @param files 相对路径文件列表，如：/img/a.jpg
	 */
	public void asynchronousPublish(String localpath, List<String> files) {
		if (host==null) {
			host = cmsConfig.getWebServers();
		}
		
		for (int i = 0; i < host.size(); i++) {
			if (host.get(i).isSync()) {
				Thread thread=new HostPublisher(host.get(i), localpath,files);
				thread.start();
			}
		}
	}
	/**
	 * 同步文件到资源服务器
	 * @param localpath 本地文件绝对路径，如：d:/html
	 * @param files 相对路径文件列表，如：/img/a.jpg
	 */
	/*public void publishHtml(String localpath, List<String> htmlFiles) {
		if (htmlHost==null) {
			htmlHost = cmsConfig.getHtmlServers();
		}
		for (int i = 0; i < htmlHost.size(); i++) {
			System.out.println("-----------"+htmlHost.get(i).getHost()+"*******"+htmlFiles+"+++++++"+htmlHost.get(i).isSync());
			if (htmlHost.get(i).isSync()) {
				new HostPublisher(htmlHost.get(i), localpath, htmlFiles).start();
			}
		}
	}*/
	/**
	 * 发布器
	 * @author ccl
	 *
	 */
	private class HostPublisher extends Thread{
		private WebServerConfig server;
		/**
		 * 相对路径文件
		 */
		private List<String> htmlFiles;
		/**
		 * 本地目录
		 */
		private String localpath;
		public HostPublisher(WebServerConfig server, String localpath, List<String> htmlFiles) {
			super();
			this.server = server;
			this.localpath = localpath;
			this.htmlFiles = htmlFiles;
		}

		@Override
		public void run() {
			Ssh2FilePublisher filePublisher = null;
			try {
                filePublisher = new Ssh2FilePublisher(server.getHost(), server.getPort(),
                		server.getUsername(), server.getPasswd(), server.getSitepath());
                filePublisher.getConnection();
                //装载文件
                for (int i = 0; i < htmlFiles.size(); i++) {
                	File file=new File(localpath+htmlFiles.get(i));
                	filePublisher.upload(file, server.getSitepath()+htmlFiles.get(i));
				}
                filePublisher.closeConnection();
               
            } catch (Exception e) {
                e.printStackTrace();
            } catch (InternalError e) {
                e.printStackTrace();
            } finally {
                if (filePublisher != null) {
                    filePublisher.closeConnection();
                }
            }
		}
	}
	@Override
	public void deleteFile(String path) {
		Ssh2FilePublisher filePublisher = null;
		if (host==null) {
			host = cmsConfig.getWebServers();
		}
		if(host!=null&&!host.isEmpty()) {
			WebServerConfig server = host.get(0);
	            filePublisher = new Ssh2FilePublisher(server.getHost(), server.getPort(),
	            		server.getUsername(), server.getPasswd(), server.getSitepath());
	            filePublisher.getConnection();
	            filePublisher.deleteFile(path);
	            filePublisher.closeConnection();
		}
	}
}
