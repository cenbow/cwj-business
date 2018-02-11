package com.mm.dev.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.log.Log;
import com.mm.dev.service.log.LogService;
import com.mm.dev.wechatUtils.UserSession;

public class LogServiceTest extends BaseTest{
	
	@Autowired
	private LogService logService;
	
	@Test
	public void testSave() throws Exception {
		try {
			Log log = new Log();
			log.setUsername((String)UserSession.getSession(WechatConstant.NICK_NAME));
			log.setLogType(WechatConstant.log_type_success);
			log.setExceptionCode(null);
			log.setExceptionDetail(null);
			logService.save(log);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testDelete() {
		logService.deleteById("489");
	}
}
