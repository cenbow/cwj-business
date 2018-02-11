package com.mm.dev.service.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.util.UUIDGenerator;
import com.mm.dev.common.base.service.impl.BaseServiceImpl;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.dao.jpa.user.OrderPaymentDao;
import com.mm.dev.dao.mapper.order.OrderPaymentMapper;
import com.mm.dev.entity.order.OrderItemPayment;
import com.mm.dev.entity.order.OrderPayment;
import com.mm.dev.entity.user.User;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.user.UserInOutDetail;
import com.mm.dev.enums.UserInOutDetailPayTypeEnum;
import com.mm.dev.service.order.IOrderItemPaymentService;
import com.mm.dev.service.order.IOrderPaymentService;
import com.mm.dev.service.user.IUserInOutDetailService;
import com.mm.dev.service.user.IUserService;


/**
 * @ClassName: OrderPaymentServiceImpl 
 * @Description: 订单管理
 * @author zhangxy
 * @date 2017年8月10日 下午2:00:09
 */
@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl<OrderPayment> implements IOrderPaymentService{
	Logger logger = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);
	
	@Autowired
	private OrderPaymentDao orderPaymentDao;
	
	@Autowired
	private OrderPaymentMapper orderPaymentMapper;
	
	@Autowired
	private IOrderItemPaymentService orderItemPaymentService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserInOutDetailService userInOutDetailService;
	
	/**
	 * 微信支付回调通知 更新订单、支付状态
	 * @param tranData
	 * @throws Exception
	 */
	public void updateByOrderNo(OrderPayment orderPayment) throws Exception{
		orderPaymentMapper.updateByOrderNo(orderPayment);
	}
	
	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public OrderPayment saveOrderInfo(OrderPayment orderPayment) throws Exception{
		 return orderPaymentDao.save(orderPayment);
	}
	
	/**
	 * @Description: 根据订单号查询订单明细
	 * @DateTime:2017年11月9日 上午10:41:10
	 * @return OrderPayment
	 * @throws
	 */
	public OrderPayment findOrderPayment(String orderNo) throws Exception {
		return orderPaymentMapper.findOrderPayment(orderNo);
	}
	
	/**
	 * @Description: 根据订单号更新发布人账户余额,更新订单明细结算状态，新增收支明细记录
	 * @DateTime:2017年11月9日 上午11:29:27
	 * @return void
	 * @throws
	 */
	public void updateUserBalanceInfoByOrderNo(String orderNo) throws Exception {
		if(StringUtils.isNotEmpty(orderNo)) {
			OrderPayment findOrderPayment = findOrderPayment(orderNo);
			if(null != findOrderPayment) {
				//付款金额
				BigDecimal amount = findOrderPayment.getAmount();
				//付款人
				String payer = findOrderPayment.getPayer();
				List<OrderItemPayment> orderItemPaymentList = findOrderPayment.getOrderItemPaymentList();
				if(CollectionUtils.isNotEmpty(orderItemPaymentList)) {
					//发布人
					String openId = orderItemPaymentList.get(0).getUserFiles().getOpenId();
					ArrayList<UserInOutDetail> userInOutDetailList = new ArrayList<UserInOutDetail>();
					for (OrderItemPayment orderItemPayment : orderItemPaymentList) {
						UserFiles userFiles = orderItemPayment.getUserFiles();
						if(null != userFiles) {
							orderItemPayment.setSettleClose(WechatConstant.settle_close_2);
						}
					}
					
					//批量更新结算状态
					orderItemPaymentService.batchUpdateSettleClose(orderItemPaymentList);
					//更新发布人账户余额，与收入总额
					User user = new User();
					user.setOpenId(openId);
					user.setBalance(amount);
					user.setTotalIncome(amount);
					userService.updateUserBalanceInfoByOpenId(user);
					
					//支出记录
					UserInOutDetail userInOutDetail = new UserInOutDetail();
					userInOutDetail.setId(UUIDGenerator.generate());
					userInOutDetail.setAmount(amount);
					userInOutDetail.setPayType(UserInOutDetailPayTypeEnum.play_reward_out.getIndex());
					//交易人
					userInOutDetail.setOpenId(payer);
					//被交易人
					userInOutDetail.setTraderOpenId(openId);
					userInOutDetail.setCreateTime(new Date());
					userInOutDetail.setUpdateTime(new Date());
					userInOutDetailList.add(userInOutDetail);
					
					//进账记录
					UserInOutDetail userInOutDetail2 = new UserInOutDetail();
					userInOutDetail2.setId(UUIDGenerator.generate());
					userInOutDetail2.setAmount(amount);
					userInOutDetail2.setPayType(UserInOutDetailPayTypeEnum.play_reward_in.getIndex());
					//交易人
					userInOutDetail2.setOpenId(openId);
					//被交易人
					userInOutDetail2.setTraderOpenId(payer);
					userInOutDetail2.setCreateTime(new Date());
					userInOutDetail2.setUpdateTime(new Date());
					userInOutDetailList.add(userInOutDetail2);
					
					//创建收支明细记录
					if(CollectionUtils.isNotEmpty(userInOutDetailList)) {
						userInOutDetailService.batchSaveList(userInOutDetailList);
					}
				}
			}
		} 
	}
}
