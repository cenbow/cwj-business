package com.mm.dev.dao.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.order.OrderItemPayment;

/**
 * @Description: OrderItemPaymentDao
 * @author Jacky
 * @date 2017年8月6日 下午9:45:39
 */
@Repository
public interface OrderItemPaymentDao extends JpaRepository<OrderItemPayment, String> {

}
