package com.mm.dev.controller.user;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.dev.common.annotation.OperationLog;
import com.mm.dev.common.base.controller.BaseController;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.controller.wechat.WechatController;
import com.mm.dev.entity.user.User;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.service.user.IUserService;
import com.mm.dev.wechatUtils.UserSession;

@Controller
@RequestMapping("/user")
public class UserController  extends BaseController{
	private Logger logger = LoggerFactory.getLogger(WechatController.class);
	
	@Autowired
	private IUserService userService;
	
	
	/**
	 * @Description: 根据openId获取用户信息
	 * @Datatime 2017年8月5日 下午3:16:51 
	 * @return void    返回类型
	 */
	@RequestMapping("/queryUserInfo")
	@ResponseBody
	@OperationLog(value = "根据openId获取用户信息")
	public ReturnMsg<User> queryUserInfo(String openId,HttpServletResponse response) throws Exception {
		ReturnMsg<User> returnMsg = new ReturnMsg<>();
		try {
			if(StringUtils.isEmpty(openId)){
				openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
//				openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			}
			if(StringUtils.isNotEmpty(openId)) {
				User userInfo = userService.queryUserBaseInfoByopenId(openId);
				returnMsg.setStatus(true);
				returnMsg.setData(userInfo);
			} else {
				returnMsg.setStatus(false);
			}
		} catch (Exception e) {
			logger.error("根据openId获取用户信息异常",e);
			returnMsg.setStatus(false);
		}
		return returnMsg;
	}
	

	/**
	 * @Description: 根据openId获取用户账号信息
	 * @Datatime 2017年8月5日 下午3:16:51 
	 * @return void    返回类型
	 */
	@RequestMapping("/queryUserBalanceInfo")
	@ResponseBody
	@OperationLog(value = "根据openId获取用户账号信息")
	public ReturnMsg<User> queryUserBalanceInfoByOpenId(HttpServletResponse response) throws Exception {
		ReturnMsg<User> returnMsg = new ReturnMsg<>();
		try {
			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
//			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			if(StringUtils.isNotEmpty(openId)) {
				User userInfo = userService.queryUserBalanceInfoByOpenId(openId);
				returnMsg.setStatus(true);
				returnMsg.setData(userInfo);
			} else {
				returnMsg.setStatus(false);
			}
		} catch (Exception e) {
			logger.error("根据openId获取用户信息异常",e);
			returnMsg.setStatus(false);
		}
		return returnMsg;
	}
}
