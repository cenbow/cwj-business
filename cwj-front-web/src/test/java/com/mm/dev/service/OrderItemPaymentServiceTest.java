package com.mm.dev.service;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.util.UUIDGenerator;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.order.OrderItemPayment;
import com.mm.dev.service.order.IOrderItemPaymentService;

public class OrderItemPaymentServiceTest extends BaseTest{
	
	@Autowired
	private IOrderItemPaymentService orderItemPaymentService;
	
	@Test
	public void test() throws Exception {
		OrderItemPayment orderItemPayment = new OrderItemPayment();
		orderItemPayment.setId(UUIDGenerator.generate());
		orderItemPayment.setOrderId("123");
		orderItemPayment.setProductId("456");
		orderItemPaymentService.save(orderItemPayment);
	}
	
	@Test
	public void testBatchUpdate() throws Exception {
		ArrayList<OrderItemPayment> arrayList = new ArrayList<OrderItemPayment>();
		OrderItemPayment orderItemPayment = new OrderItemPayment();
		orderItemPayment.setSettleClose(WechatConstant.settle_close_2);
		orderItemPayment.setId("2c9229eb5f98eecc015f98f8a7360003");
		
		OrderItemPayment orderItemPayment2 = new OrderItemPayment();
		orderItemPayment2.setSettleClose(WechatConstant.settle_close_2);
		orderItemPayment2.setId("2c9229eb5f98eecc015f98f929fd0005");
		arrayList.add(orderItemPayment);
		arrayList.add(orderItemPayment2);
		orderItemPaymentService.batchUpdateSettleClose(arrayList);
	}
}
