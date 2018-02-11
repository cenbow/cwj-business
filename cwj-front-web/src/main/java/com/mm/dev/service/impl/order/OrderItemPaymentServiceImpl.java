package com.mm.dev.service.impl.order;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mm.dev.common.base.service.impl.BaseServiceImpl;
import com.mm.dev.dao.mapper.order.OrderItemPaymentMapper;
import com.mm.dev.entity.order.OrderItemPayment;
import com.mm.dev.service.order.IOrderItemPaymentService;


/**
 * @ClassName: OrderItemPaymentServiceImpl 
 * @Description: 订单明细管理
 * @author zhangxy
 * @date 2017年8月10日 下午2:00:09
 */
@Service
public class OrderItemPaymentServiceImpl extends BaseServiceImpl<OrderItemPayment> implements IOrderItemPaymentService{
	Logger logger = LoggerFactory.getLogger(OrderItemPaymentServiceImpl.class);
	
	@Autowired
	private OrderItemPaymentMapper orderItemPaymentMapper;
	
	public void batchUpdateSettleClose(List<OrderItemPayment> orderItemPaymentList) throws Exception{
		orderItemPaymentMapper.batchUpdateSettleClose(orderItemPaymentList);
	}
}
