package com.uib.common.enums;

public enum PaymentStatusEnum {
	
	/** 未支付 */
	unpaid("未知付", "0"),

	/** 部分支付 */
	partialPayment("部分支付","1"),

	/** 已支付 */
	paid("已支付","2"),

	/** 部分退款 */
	partialRefunds("部分退款","3"),

	/** 已退款 */
	refunded("已退款","4");
	
	// 成员变量
    private String description;
    private String index;
    
    private PaymentStatusEnum(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (PaymentStatusEnum t : PaymentStatusEnum.values()) {
            if (t.getIndex().equals(index) ) {
                return t.description;
            }
        }
        return null;
    }
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	
    
}
