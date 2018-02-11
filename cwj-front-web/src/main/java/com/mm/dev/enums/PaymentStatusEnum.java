package com.mm.dev.enums;

public enum PaymentStatusEnum {

	/** 等待支付 */
	wait("等待支付","1"),

	/** 支付成功 */
	success("支付成功","2"),

	/** 支付失败 */
	failure("支付失败","3");

	
	// 成员变量
    private String description;
    private String index;
    
    private PaymentStatusEnum(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (PaymentStatusEnum t : PaymentStatusEnum.values()) {
            if (t.getIndex() == index) {
                return t.description;
            }
        }
        return null;
    }

	public  String getDescription() {
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

