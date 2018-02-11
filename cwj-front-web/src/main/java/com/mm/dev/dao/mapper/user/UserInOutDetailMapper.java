package com.mm.dev.dao.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.mm.dev.common.base.mapper.BaseMapper;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.user.UserInOutDetail;

/**
 * @Description: UserInOutDetailMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserInOutDetailMapper extends BaseMapper<UserInOutDetail>{

	public List<Map<String, String>> queryAllByOpenIdAndDelFlag(@Param("openId")String refOpenid,Pageable pageable) throws Exception;
   	
}
