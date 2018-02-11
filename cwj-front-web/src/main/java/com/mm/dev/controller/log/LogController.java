package com.mm.dev.controller.log;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.mm.dev.common.base.controller.BaseController;
import com.mm.dev.entity.log.Log;
import com.mm.dev.service.log.LogService;

/**
 *
 * @author cuiP
 * Created by JK on 2017/5/5.
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    @Resource
    private LogService logService;


    /**
     * 分页查询日志列表
     *
     * @param pageNum   当前页码
     * @param username  用户名
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param modelMap
     * @return
     */
    @GetMapping
    @ResponseBody
    public PageInfo<Log> list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            String username, String startTime, String endTime, ModelMap modelMap) {
    	PageInfo<Log> pageInfo = null;
        try {
            logger.debug("分页查询日志列表参数! pageNum = {}, username = {}, username = {}, startTime = {}, endTime = {}", pageNum, username, startTime, endTime);
            pageInfo = logService.findPage(pageNum, PAGESIZE, username, startTime, endTime);
            logger.info("分页查询日志列表结果！ pageInfo = {}", pageInfo);
        } catch (Exception e) {
        	logger.error("分页查询日志列表失败! e = {}", e);
        }
        return pageInfo;
    }


    /**
     * 根据主键ID删除管理员
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        try {
            logger.debug("删除日志! id = {}", id);
            logService.deleteById(id);
            logger.info("删除日志成功! id = {}", id);
            return ResponseEntity.ok("已删除!");
        } catch (Exception e) {
        	logger.error("删除日志失败! id = {}, e = {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 批量删除日志
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/batch/{ids}")
    @ResponseBody
    public ResponseEntity<String> deleteBatch(@PathVariable("ids") List<Object> ids) {
        try {
            logger.debug("批量删除日志! ids = {}", ids);

            if (null == ids) {
            	logger.info("批量删除日志不存在! ids = {}", ids);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            logService.deleteByCondition(Log.class, "id", ids);
            logger.info("批量删除日志成功! ids = {}", ids);

            return ResponseEntity.ok("已删除!");
        } catch (Exception e) {
        	logger.error("批量删除日志失败! ids = {}, e = {}", ids, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 查询日志详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Log view(@PathVariable("id")String id, ModelMap modelMap){
        Log log = logService.findById(id);
        return log;
    }

}
