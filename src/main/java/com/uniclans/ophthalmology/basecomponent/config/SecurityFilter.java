package com.uniclans.ophthalmology.basecomponent.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

import com.uniclans.ophthalmology.basecomponent.constans.PermissionConstant;
import com.uniclans.ophthalmology.basecomponent.utils.ResultVo;
import com.uniclans.ophthalmology.basecomponent.utils.SessionUtil;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemResourceVo;


/**
 * 后台安全控制
 * 
 * @author 
 *
 */
@Order(1)
@WebFilter(filterName = "securityFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {
	
	private String excludedURL = "tologin,login,index,timeout,checkVerifyMsg,captcha,.jpg,upload";
	
	private String excluded[];
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		excluded = excludedURL.split(",");
	}


	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		ResultVo resultVo=new ResultVo();
		resultVo.setResult(false);
		String uri = request.getRequestURI();            
		boolean flag = true;
		for (int i = 0; i < excluded.length; i++) {
			if(uri.endsWith(excluded[i])){
				flag = false;
				break;
			}
		}
		if(flag&&!uri.contains("/res/")){
			if(!SessionUtil.isMgtSessionLogin(request)){
				if (isAjaxRequest(request)){ 
		            response.setHeader("sessionstatus", "timeout");  
		        }else{
		        	response.sendRedirect(request.getContextPath()+"/sys/main/timeout");
				}
				return ;
			}else{
				List<SystemResourceVo> resList = SessionUtil.getDataFromSession(request, PermissionConstant.USER_RES_LIST, ArrayList.class);
			}
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
		
	}

	@Override
	public void destroy() {

	}
	private static boolean isAjaxRequest(HttpServletRequest request){ 
		String headerX = request.getHeader("X-Requested-With");			
       return headerX != null  && headerX.equalsIgnoreCase("XMLHttpRequest");			
		
	}
}
