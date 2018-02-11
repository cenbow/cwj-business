package com.mm.dev.service.log;

import com.github.pagehelper.PageInfo;
import com.mm.dev.common.base.service.BaseService;
import com.mm.dev.entity.log.Log;

/**
 * 系统日志服务接口
 * @author Jacky
 */
public interface LogService extends BaseService<Log> {

    /**
     * 分页查询日志列表
     * @param pageNum
     * @param pageSize
     * @param username
     * @param startTime
     * @param endTime
     * @return
     */
    PageInfo<Log> findPage(Integer pageNum, Integer pageSize, String username, String startTime, String endTime);
}
