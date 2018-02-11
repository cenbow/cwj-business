package com.common.constant;

/**
 * @ClassName: PrivilegeConstant 
 * @Description: 权限有关的常量
 * @author zhangxy
 * @date 2017年9月28日 下午1:30:47
 */
public interface PrivilegeConstant {

	/**
	 * session在缓存中的存活时间（30分钟）
	 */
	public static final int TIMEOUT_SESSION = 30 * 60;

	/**
	 * 验证码在缓存中的存活时间（10分钟）
	 */
	public static final int TIMEOUT_CODE = 10 * 60;

	/**
	 * Cookie存活期（3天）
	 */
	public static final int TIMEOUT_COOKIE = 3 * 24 * 60 * 60;

	/**
	 * 用户未登录的key值
	 */
	public static final String USER_NO_LOGIN_KEY = "no_login";

	/**
	 * 登录用户的session中的key值
	 */
	public static final String SESSION_LOGIN_USER = "common_login_user";

	/**
	 * 登录用户的session中的id值
	 */
	public static final String SESSION_LOGIN_USER_SESSION_ID = "common_login_user_session_id";

	/**
	 * 登录用户的cookie中的userName值
	 */
	public static final String COOKIE_LOGIN_USER_USERNAME = "cookie_login_user_username";

	/**
	 * 登录用户的cookie中的passWord值
	 */
	public static final String COOKIE_LOGIN_USER_PASSWORD = "cookie_login_user_password";

	/**
	 * 登录用户的cookie中的user_type值
	 */
	public static final String COOKIE_LOGIN_USER_TYPE = "cookie_login_user_type";

	/**
	 * PBE加密中的key值
	 */
	public static final String PBE_PASSWORD_KEY = "common_pbe_pwd_key_2016";

}
