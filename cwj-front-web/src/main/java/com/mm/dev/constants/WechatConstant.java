package com.mm.dev.constants;
/**
 * @ClassName: WechatConstant 
 * @Description: 常量类
 * @author zhangxy
 * @date 2017年8月4日 上午9:04:34
 */
public interface WechatConstant {
	
	/**
	 * 用户名
	 */
	public String NICK_NAME = "nickName";
	
	/**
	 * 用户id
	 */
	public String USER_ID = "userId";
	
	/**
	 * openid
	 */
	public String OPEN_ID = "openId";
	
	/**
	 * 用户手机号码
	 */
	public String wx_user_phone = "phone";
	
	/**
	 * 验证码1
	 */
	public String wx_mess_code_1 = "mess_code_1";
	
	/**
	 * 未关注
	 */
	String attention_status_1 = "1";
	
	/**
	 * 已关注
	 */
	String attention_status_2 = "2";
	
	/**
	 * 图片
	 */
	String file_category_image = "1";
	
	/**
	 * 视频
	 */
	String file_category_video = "2";
	
	/**
	 * 未删除 有效
	 */
	String delete_flag_1 = "1";
			
	/**
	 * 已删除 无效
	 */
	String delete_flag_2 = "2";
	
	/**
	 * 操作成功日志
	 */
	int log_type_success = 0;
	
	/**
	 * 操作异常日志
	 */
	int log_type_error = 1;
	
	/**
	 * 是否已结算（2：是）
	 */
	String settle_close_2 = "2";
	
	/**
	 * 随机
	 */
	String random_yes = "1";
	
	/**
	 * 不随机
	 */
	String random_no = "2";
	
}


