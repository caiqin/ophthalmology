package com.uniclans.ophthalmology.basecomponent.constans;

/**
 * MallMgt的常量接口
 * @ClassName: IMallMgtConstant 
 * @Description: TODO
 *
 */
public interface MgtConstant {
	
	boolean isDebug=true;

	/**
	 * MallMgt管理中登录用户的SessionKey
	 */
	String MGT_LOGIN_USER_SESSION_KEY=PermissionConstant.LOGIN_USER;
	
	int userState_ok = 0;
	int userState_lock= 1;
	
	String reCode_ok="0";
	String reDesc_ok="处理成功";
	String reCode_fail="1";
	/**
	 * 参数异常 Vo错误判断，100000 - 200000
	 */
	String reCode_param_error="100000";
	/**
	 * 权限限制  200000 - 300000
	 */
	String reCode_authority_limits="200000";
	
	/**
	 * do统一错误信息
	 */
	String reCode_do_error="100";
	
	
	
}
