package com.mm.dev.enums;

public enum UserInOutDetailPayTypeEnum {
	
	/** 支出打赏 */
	play_reward_out("支出打赏","0"),

	/** 进账打赏 */
	play_reward_in("进账打赏","1"),

	/** 支出提现 */
	withdraw_out("支出提现","2"),
	
	/** 进账分享打赏 */
	share_play_reward_in("进账分享打赏","3"),

	/** 进账退还的赏金 */
	return_play_reward_in("进账退还的赏金","4"),

	/** 进账系统退还 */
	system_return_play_reward_in("进账系统退还","5");
	
	
	// 成员变量
    private String description;
    private String index;
    
    private UserInOutDetailPayTypeEnum(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (UserInOutDetailPayTypeEnum t : UserInOutDetailPayTypeEnum.values()) {
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

