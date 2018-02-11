package com.mm.dev.service.order;

import com.mm.dev.common.base.service.BaseService;
import com.mm.dev.entity.order.OrderPayment;

/**
 * @ClassName: IOrderPaymentService 
 * @Description: TODO
 * @author zhangxy
 * @date 2017年8月10日 下午2:00:09
 */
public interface IOrderPaymentService extends BaseService<OrderPayment>{
	
	/**
	 * 微信支付回调通知 更新订单、支付状态
	 * @param tranData
	 * @throws Exception
	 */
	public void updateByOrderNo(OrderPayment orderPayment) throws Exception;
	
	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public OrderPayment saveOrderInfo(OrderPayment orderPayment) throws Exception;
	
	/**
	 * @Description: 根据订单号查询订单明细
	 * @DateTime:2017年11月9日 上午10:41:10
	 * @return OrderPayment
	 * @throws
	 */
	public OrderPayment findOrderPayment(String orderNo) throws Exception;
	
	/**
	 * @Description: 根据订单号更新发布人账户余额
	 * @DateTime:2017年11月9日 上午11:29:27
	 * @return void
	 * @throws
	 */
	public void updateUserBalanceInfoByOrderNo(String orderNo) throws Exception;
}
