package com.mm.dev.enums;

/**
 * @Description: 异常枚举类错误代码
 * @author Jacky
 * @date 2017年8月3日 下午7:13:48
 */
public enum ExceptionEnum {
	/** 成功 */
	success("成功",0),
	/** 系统内部异常 */
	system_error("系统内部异常",500),
	
	/** 参数不能为空 */
	param_not_null("参数不能为空", 1001), 
	
	/** 未登录 */
	not_login("未登录", 1002), 
	
	/** 该用户已存在 */
	user_exist("该会员已存在", 1003),
	
	/** 该用户不存在 */
	user_not_exist("该会员不存在",1004),
	
	save_order_error("保存订单异常",1005),
	
	query_files_error("根据ID查询文件异常",1006),
	
	withdrawal_amount_max_error("每日提现总金额不能大于每日最大提现额度",1007),
	
	withdrawal_day_limit_max_error("每日提现次数不能大于每日最大次数限制",1008),
	
	withdrawal_amount_min__max_error("单次提现不能小于2元或者大于最大提现额度",1008),
	
	upload_image_max_size_error("图片大小不能大于10M",1009),
	
	
	upload_video_max_size_error("视频大小不能大于100M",10010),
	
	upload_image_ext_error("上传的图片格式错误",10011),
	
	upload_video_ext_error("上传的视频格式错误",10012)
	;

	private String msg;
	private Integer code;

	private ExceptionEnum(String msg, Integer code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Integer code) {
		for (ExceptionEnum t : ExceptionEnum.values()) {
			if (t.getCode().equals(code)) {
				return t.msg;
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
