package com.mm.dev.service;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SecretTest extends BaseTest{
	
	@Autowired
    StringEncryptor stringEncryptor;//密码解码器自动注入
	
    @Value("${PLAY.WECHAT.APPKEY}")
    private String password;

    @Test
    public void test() {
    	System.out.println(stringEncryptor.decrypt("DMivSkE2piCRpUdvOaM5Zm6VHxXbYuR/dy0fj83UNIlA6Lpa79qbAimmA7EzQ4C+"));
        System.out.println(stringEncryptor.encrypt("e09870c0ed1545c3dbacebaa09dddd8f"));
        System.out.println(stringEncryptor.encrypt("d4624c36b6795d1d99dcf0547af5443d"));
        System.out.println(stringEncryptor.encrypt("d05892c82d128bc38b73f953009f1aed"));
//        System.out.println(stringEncryptor.encrypt("jacky1991"));
//        System.out.println(stringEncryptor.encrypt("ZhangFeng826"));
        
    }
}
