package com.mm.dev.service.impl.log;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mm.dev.common.base.service.impl.BaseServiceImpl;
import com.mm.dev.entity.log.Log;
import com.mm.dev.service.log.LogService;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * 系统日志服务实现类
 * @author Jacky
 */
@Transactional
@Service
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService{

    @Transactional(readOnly = true)
    @Override
    public PageInfo<Log> findPage(Integer pageNum, Integer pageSize, String username, String startTime, String endTime) {
        Example example = new Example(Log.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(username)){
            criteria.andLike("username", "%"+username+"%");
        }if(StrUtil.isNotEmpty(startTime) && StrUtil.isNotEmpty(endTime)){
            criteria.andBetween("createTime", DateUtil.beginOfDay(DateUtil.parse(startTime)), DateUtil.endOfDay(DateUtil.parse(endTime)));
        }
        //倒序
        example.orderBy("createTime").desc();

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Log> logList = this.selectByExample(example);

        return new PageInfo<Log>(logList);
    }
}
