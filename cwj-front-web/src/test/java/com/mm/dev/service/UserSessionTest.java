package com.mm.dev.service;

import org.junit.Test;

import com.mm.dev.constants.WechatConstant;
import com.mm.dev.wechatUtils.UserSession;

public class UserSessionTest extends BaseTest{

	@Test
	public void test() {
		UserSession.setSession(WechatConstant.OPEN_ID, "111111");
		System.out.println(UserSession.getSession(WechatConstant.OPEN_ID));
	}
}
