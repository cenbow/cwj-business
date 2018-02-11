package com.mm.dev.dao.mapper.order;

import com.mm.dev.common.base.mapper.BaseMapper;
import com.mm.dev.entity.order.OrderPayment;

/**
 * @Description: UserInOutDetailMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface OrderPaymentMapper extends BaseMapper<OrderPayment>{

	public void updateByOrderNo(OrderPayment orderPayment);
	
	/**
	 * @Description: 根据订单号查询订单明细
	 * @DateTime:2017年11月9日 上午10:41:10
	 * @return OrderPayment
	 * @throws
	 */
	public OrderPayment findOrderPayment(String orderNo) throws Exception;
   	
}
