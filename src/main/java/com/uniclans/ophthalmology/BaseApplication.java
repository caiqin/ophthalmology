package com.uniclans.ophthalmology;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration  
@ComponentScan(basePackages = {"com.uniclans.*"})
@ServletComponentScan
public class BaseApplication 
{
	private static Logger logger = LoggerFactory.getLogger(BaseApplication.class);
    public static void main( String[] args )
    {
    	logger.info( "启动服务" );
        SpringApplication.run(BaseApplication.class,args);
    }
    
    @Bean 
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //允许上传的文件最大值
        factory.setMaxFileSize("50MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("50MB");  
        return factory.createMultipartConfig();  
    } 
//    <bean id="cmsConfig" class="com.uniclans.smp.basecomponent.upload.common.CmsConfig">

//	<property name="webServers"><!-- 文件资源服务器列表 -->
//		<list>
//			<bean class="com.uniclans.smp.basecomponent.upload.common.WebServerConfig">
//				<property name="host" value="${cms.sftp.host}" />
//				<property name="username" value="${cms.sftp.username}" />
//				<property name="passwd" value="${cms.sftp.passwd}" />
//				<property name="port" value="${cms.sftp.port}" />
//				<property name="sitepath" value="${cms.sftp.sitepath}" />
//				<property name="sync" value="${cms.sftp.sync}" />
//			</bean>
//		</list>
//	</property>
//
//</bean>
     
    
}
