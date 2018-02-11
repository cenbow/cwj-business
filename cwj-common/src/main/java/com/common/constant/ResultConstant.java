package com.common.constant;

/**
 * @ClassName: ResultConstant 
 * @Description: 结果有关常量
 * @author zhangxy
 * @date 2017年9月28日 下午1:31:00
 */
public interface ResultConstant {

    // 失败
    public static final int RESULT_FAILURE = 0;

    // 成功
    public static final int RESULT_SUCCESS = 1;

    // 系统未登录
    public static final int RESULT_NO_LOGIN = 2;

    // 数据不存在
    public static final int RESULT_NO_EXISTS = 3;

    // 数据已存在
    public static final int RESULT_HAS_EXISTS = 4;

    // 系统异常
    public static final int RESULT_EXCEPTION = 5;

    // 系统超时
    public static final int RESULT_TIME_OUT = 6;

    // code错误
    public static final int RESULT_CODE_ERROR = 7;

    // 数据错误
    public static final int RESULT_DATA_ERROR = 8;

    // 格式错误
    public static final int RESULT_FORMAT_ERROR = 9;

    // 数据无效
    public static final int RESULT_DATA_AVAILABLE = 10;

    // 数据为空
    public static final int RESULT_DATA_NULL = 11;

    // 签名失败
    public static final int RESULT_SIGN_ERROR = 12;

}
