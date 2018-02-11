package com.mm.dev.dao.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.mm.dev.common.base.mapper.BaseMapper;
import com.mm.dev.entity.user.UserWithdrawalApply;

/**
 * @Description: UserRecommendMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserWithdrawalApplyMapper extends BaseMapper<UserWithdrawalApply>{

	public List<Map<String, String>> queryAllByApplyOpenIdAndDelFlag(@Param("openId")String refOpenid, @Param("delFlag")String delFlag,Pageable pageable) throws Exception;
	
	/**
	 * @Description: 统计当天提现记录
	 * @DateTime:2017年11月13日 下午2:00:53
	 * @return List<UserWithdrawalApply>
	 * @throws
	 */
	Map<String, String> queryApplyCountByOpenIdAndApplyTime(UserWithdrawalApply userWithDrawalApply) throws Exception;
   	
}
