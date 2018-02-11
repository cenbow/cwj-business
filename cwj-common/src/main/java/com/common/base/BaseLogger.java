package com.common.base;

import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;

/**
 * @ClassName: BaseLogger 
 * @Description: Base日志处理类
 * @author zhangxy
 * @date 2017年8月25日 上午11:32:12
 */
public class BaseLogger implements BaseConstant {

	protected final transient Log logger = LogFactory.get(this.getClass());

}
