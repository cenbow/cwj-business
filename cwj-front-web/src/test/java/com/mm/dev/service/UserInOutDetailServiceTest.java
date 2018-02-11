package com.mm.dev.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSONObject;
import com.common.util.UUIDGenerator;
import com.mm.dev.entity.user.UserInOutDetail;
import com.mm.dev.enums.UserInOutDetailPayTypeEnum;
import com.mm.dev.service.user.IUserInOutDetailService;

public class UserInOutDetailServiceTest extends BaseTest{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserInOutDetailService userInOutDetailService;
	
	@Test
	public void testSave() throws Exception {
		UserInOutDetail userInOutDetail = new UserInOutDetail();
		userInOutDetail.setId(UUIDGenerator.generate());
		userInOutDetail.setOpenId("oIrGq0x10Ii4_F0_FKD8XYhdr7Lk");
		userInOutDetail.setTraderOpenId("oIrGq0x10Ii4_F0_FKD8XYhdr7Lk");
		userInOutDetail.setFee(new BigDecimal("205").multiply(new BigDecimal("0.1")));
		userInOutDetail.setAmount(new BigDecimal("205"));
		userInOutDetail.setPayType(UserInOutDetailPayTypeEnum.withdraw_out.getIndex());
		userInOutDetailService.save(userInOutDetail);
	}
	
	public static void main(String[] args) {
		System.out.println(new BigDecimal("205").multiply(new BigDecimal("0.1")).doubleValue());
	}
	
	@Test
	public void testFindAll() throws Exception {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
    	List<Map<String, String>> findAllByOpenIdAndDelFlag = userInOutDetailService.queryAllByOpenIdAndDelFlag("o5z7ywOP7qycrtAAxIqDfgMbfcFY", pageable);
    	String jsonString = JSONObject.toJSONString(findAllByOpenIdAndDelFlag);
    	logger.info("jpa分页第一页：{}",jsonString);
	}
	
	@Test
	public void testBatchSaveList() throws Exception {
		ArrayList<UserInOutDetail> userInOutDetailList = new ArrayList<UserInOutDetail>();
		//支出记录
		UserInOutDetail userInOutDetail = new UserInOutDetail();
		userInOutDetail.setId(UUIDGenerator.generate());
		userInOutDetail.setAmount(new BigDecimal("222.00"));
		userInOutDetail.setPayType(UserInOutDetailPayTypeEnum.play_reward_out.getIndex());
		//交易人
		userInOutDetail.setOpenId("oIrGq0x10Ii4_F0_FKD8XYhdr7Lk");
		//被交易人
		userInOutDetail.setTraderOpenId("oIrGq05R1gtVvBrvMs-g7_zm3i78");
		userInOutDetail.setCreateTime(new Date());
		userInOutDetail.setUpdateTime(new Date());
		userInOutDetailList.add(userInOutDetail);
		
		//进账记录
		UserInOutDetail userInOutDetail2 = new UserInOutDetail();
		userInOutDetail2.setId(UUIDGenerator.generate());
		userInOutDetail2.setAmount(new BigDecimal("222.00"));
		userInOutDetail2.setPayType(UserInOutDetailPayTypeEnum.play_reward_in.getIndex());
		//交易人
		userInOutDetail2.setOpenId("oIrGq05R1gtVvBrvMs-g7_zm3i78");
		//被交易人
		userInOutDetail2.setTraderOpenId("oIrGq0x10Ii4_F0_FKD8XYhdr7Lk");
		userInOutDetail2.setCreateTime(new Date());
		userInOutDetail2.setUpdateTime(new Date());
		userInOutDetailList.add(userInOutDetail2);
		userInOutDetailService.batchSaveList(userInOutDetailList);
	}
}
