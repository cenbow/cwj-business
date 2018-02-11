package com.mm.dev.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.util.OrderNoGenerateUtil;
import com.common.util.UUIDGenerator;
import com.mm.dev.entity.order.OrderPayment;
import com.mm.dev.enums.PaymentMethodEnum;
import com.mm.dev.enums.PaymentStatusEnum;
import com.mm.dev.enums.WXBankTypeEnum;
import com.mm.dev.service.order.IOrderPaymentService;
import com.xiaoleilu.hutool.json.JSONUtil;

public class OrderPaymentServiceTest extends BaseTest{
	
	@Autowired
	private IOrderPaymentService orderPaymentService;
	
	@Test
	public void test() throws Exception {
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setId(UUIDGenerator.generate());
		orderPayment.setOrderNo(OrderNoGenerateUtil.getOrderNo());
		orderPayment.setPayer("张晓雨是个帅哥");
		orderPayment.setPaymentMethod(PaymentMethodEnum.WECHATPAY.getIndex());
		orderPayment.setPaymentStatus(PaymentStatusEnum.success.getIndex());
		orderPayment.setPaymentTime(new java.util.Date());
		orderPayment.setAmount(new BigDecimal("100"));
		orderPayment.setAccount("微信");
		orderPayment.setBank("中国银行");
		orderPayment.setCreateTime(new Date());
		orderPayment.setUpdateTime(new Date());
		orderPaymentService.saveOrderInfo(orderPayment);
	}
	
	@Test
	public void testUpdate() throws Exception {
		// 更新订单状态 支付状态 商品库存
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setOrderNo("2017110713440001");
		orderPayment.setPaymentTime(new Date());
		orderPayment.setPaymentMethod(PaymentMethodEnum.WECHATPAY.getIndex());
		orderPayment.setPaymentStatus(PaymentStatusEnum.success.getIndex());
		orderPayment.setPaymentBank(WXBankTypeEnum.getDescription("XAB_DEBIT"));
		orderPaymentService.updateByOrderNo(orderPayment);
	}
	
	@Test
	public void testFindOne() {
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setOrderNo("2017110717130001");
		orderPayment.setPaymentStatus(PaymentStatusEnum.success.getIndex());
		OrderPayment findOne = orderPaymentService.findOne(orderPayment);
		System.out.println(JSONUtil.toJsonPrettyStr(findOne));
	}
	
	@Test
	public void testFindOrderPaymentInfo() throws Exception {
		OrderPayment findOrderPayment = orderPaymentService.findOrderPayment("2017110717130001");
		System.out.println(JSONUtil.toJsonPrettyStr(findOrderPayment));
	}
	
	@Test
	public void testUpdateUserBalanceInfoByOrderNo() throws Exception {
		orderPaymentService.updateUserBalanceInfoByOrderNo("2017110717130001");
	}
	
	
	
}
