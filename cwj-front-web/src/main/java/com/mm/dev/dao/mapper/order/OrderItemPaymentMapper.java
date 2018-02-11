package com.mm.dev.dao.mapper.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mm.dev.common.base.mapper.BaseMapper;
import com.mm.dev.entity.order.OrderItemPayment;

/**
 * @Description: UserInOutDetailMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface OrderItemPaymentMapper extends BaseMapper<OrderItemPayment>{

	public void batchUpdateSettleClose(@Param("orderItemPaymentList") List<OrderItemPayment> orderItemPaymentList) throws Exception;
}
