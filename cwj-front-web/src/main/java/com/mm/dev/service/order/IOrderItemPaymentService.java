package com.mm.dev.service.order;

import java.util.List;

import com.mm.dev.common.base.service.BaseService;
import com.mm.dev.entity.order.OrderItemPayment;

/**
 * @ClassName: IOrderPaymentService 
 * @Description: 订单明细管理
 * @author zhangxy
 * @date 2017年8月10日 下午2:00:09
 */
public interface IOrderItemPaymentService extends BaseService<OrderItemPayment>{
	
	void batchUpdateSettleClose(List<OrderItemPayment> orderItemPaymentList) throws Exception;
	
}
