package com.uniclans.ophthalmology.basecomponent.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.uniclans.ophthalmology.basecomponent.constans.MgtConstant;
import com.uniclans.ophthalmology.modules.permission.service.model.SystemUserVo;


/**
 * SessionUtil 操作
 * @ClassName: SessionUtil 
 * @Description: TODO
 *
 */
public class SessionUtil implements MgtConstant{
	
	/**
	 * 设置用户的登录信息
	 * @Title: setMgtUser 
	 * @Description: TODO
	 * @param request
	 * @param userVo
	 * @return  
	 */
	public static boolean setMgtUser(HttpServletRequest request,SystemUserVo userVo){
		try{
			request.getSession().setAttribute(MGT_LOGIN_USER_SESSION_KEY, userVo);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 用户是否登录
	 * @Title: isMgtSessionLogin 
	 * @Description: TODO
	 * @param request
	 * @return  
	 */
	public static boolean isMgtSessionLogin(HttpServletRequest request){
		try{
//			if(isDebug){
//				setMgtUser(request, initSystemUserVo());
//			}
			HttpSession objsesion = request.getSession();
			if(null == objsesion) return false;
			Object obj=objsesion.getAttribute(MGT_LOGIN_USER_SESSION_KEY);
			if(null == obj) return false;
			else{
				SystemUserVo userVo = (SystemUserVo)obj;
				if(StringUtils.isBlank(userVo.getId()) || StringUtils.isBlank(userVo.getLoginName())){
					return false;
				}else return true;
				
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 判断Session数据是否存在
	 * @Title: sessionObjExist 
	 * @Description: TODO
	 * @param request
	 * @param sessionKey
	 * @return  
	 */
	public static boolean sessionObjExist(HttpServletRequest request,String sessionKey){
		try{
			if(StringUtils.isBlank(sessionKey)) return false;
			Object obj = request.getSession().getAttribute(sessionKey);
			if(null == obj) return false;
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
//	private static SystemUserVo initSystemUserVo(){
//		String no = "15988888888";
//		SystemUserVo vo = new SystemUserVo();
//		vo.setUserName("admin");
//		vo.setLoginPassword("000000");
//		vo.setLoginType("1");
//		vo.setSex(1);
//		vo.setCreateDate(DateUtils.getCurrentDateTime());
//		vo.setEmail("admin@purcotton.com");
//		vo.setMobilePhone(no);
//		vo.setMSNNum(no);
//		vo.setQQNum(no);
//		vo.setTelPhone(no);
//		vo.setUserState(userState_ok);
//		return vo;
//	}
	
	/**
	 * 获取登录用户信息
	 * @Title: getMgtUserVo 
	 * @Description: TODO
	 * @param request
	 * @return  
	 */
	public static SystemUserVo getMgtUserVo(HttpServletRequest request){
		try{
//			if(isDebug){
//				//setMgtUser(request, initSystemUserVo());
//			}
			Object obj = request.getSession().getAttribute(MGT_LOGIN_USER_SESSION_KEY);
			if(null == obj) return null;
			else{
				SystemUserVo userVo = (SystemUserVo)obj;
				return userVo;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
	}
	 /**
	  * 保存信息到SESSION
	  * @Title: setDataToSession 
	  * @Description: TODO
	  * @param request
	  * @param key
	  * @param value  
	  */
	 public static void setDataToSession(HttpServletRequest request,String key,Object value){
		 request.getSession().setAttribute(key,value);
	 }
	 
	 
	 /**
	  * 获取session信息
	  * @Title: getDataFromSession 
	  * @Description: TODO
	  * @param request
	  * @param key
	  * @param clazz
	  * @return  
	  */
	 @SuppressWarnings("unchecked")
	public static <T>T getDataFromSession(HttpServletRequest request,String key,Class<T> clazz){
		 Object obj = request.getSession().getAttribute(key);
		 if(obj == null ) return null;
		 return (T)obj;
	 }
	 
	 /**
	  * 移除session 属性
	  * @Title: removeSessionAttribut 
	  * @Description: TODO
	  * @param request
	  * @param key  
	  */
	public static void removeSessionAttribut(HttpServletRequest request,String key){
		 request.getSession().removeAttribute(key);
	}
	
	 /**
	  * 移除登陆用户
	  * @Title: removeSessionAttribut 
	  * @Description: TODO
	  * @param request
	  * @param key  
	  */
	public static void removeSessionUser(HttpServletRequest request){
		 removeSessionAttribut(request,MGT_LOGIN_USER_SESSION_KEY);
		 
	}
	
	
}
