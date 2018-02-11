package com.mm.dev.entity.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mm.dev.common.base.model.BaseEntity;
import com.mm.dev.entity.user.UserFiles;

/**
 * @Description:订单支付
 * @author Jacky
 * @date 2017年8月4日 下午7:21:45
 */
@Entity
@Table(name = "t_order_item_payment")
public class OrderItemPayment extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	
	/**
	 * 商品编号
	 */
	private String productId;
	
	/**
	 * 实付金额
	 */
	private BigDecimal productPrice;
	
	/**
	 * 是否已结算
	 */
	@Column(insertable=false)
	private String settleClose;
	
	/**
	 * 文件
	 */
	@Transient
	private UserFiles userFiles;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public UserFiles getUserFiles() {
		return userFiles;
	}

	public void setUserFiles(UserFiles userFiles) {
		this.userFiles = userFiles;
	}

	public String getSettleClose() {
		return settleClose;
	}

	public void setSettleClose(String settleClose) {
		this.settleClose = settleClose;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
}
