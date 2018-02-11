package com.mm.dev.dao.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.mm.dev.common.base.mapper.BaseMapper;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.user.UserRecommend;

/**
 * @Description: UserRecommendMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserRecommendMapper extends BaseMapper<UserRecommend>{

	public List<Map<String, String>> queryAllByOpenIdAndDelFlag(@Param("openId")String refOpenid, @Param("delFlag")String delFlag,Pageable pageable) throws Exception;
   	
}
