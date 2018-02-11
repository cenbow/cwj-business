package com.mm.dev.controller.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.util.UUIDGenerator;
import com.mm.dev.common.annotation.OperationLog;
import com.mm.dev.common.base.controller.BaseController;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.user.User;
import com.mm.dev.entity.user.UserInOutDetail;
import com.mm.dev.entity.user.UserWithdrawalApply;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.enums.UserInOutDetailPayTypeEnum;
import com.mm.dev.enums.WithdrawalApplyStatusEnum;
import com.mm.dev.exception.ServiceException;
import com.mm.dev.service.user.IUserInOutDetailService;
import com.mm.dev.service.user.IUserService;
import com.mm.dev.service.user.IUserWithdrawalApplyService;
import com.mm.dev.wechatUtils.ReturnMsgUtil;
import com.mm.dev.wechatUtils.UserSession;
/**
 * @ClassName: wechartController 
 * @Description: 我的提现
 * @author zhangxy
 * @date 2017年7月31日 下午3:03:05
 */
@Controller
@RequestMapping("/userWithdrawal")
public class UserWithdrawalApplyController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(UserWithdrawalApplyController.class);
	
	@Autowired
	private IUserWithdrawalApplyService userWithdrawalApplyService;
	
	@Autowired
	private IUserInOutDetailService userInOutDetailService;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * @Description: 分页查询我的提现记录
	 * @DateTime:2017年8月11日 下午12:09:34
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@RequestMapping("/{start}/{count}/record/list")
	@ResponseBody
	@OperationLog(value = "分页查询我的提现记录")
	public ReturnMsg<Object> getUserWithdrawalRecordList(@PathVariable("start") int start,@PathVariable("count") int count, HttpServletResponse response){
		List<Map<String, String>> userWithdrawalRecordList = null;
		try {
			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
//			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			logger.info("分页查询我的提现记录查询参数openId=",openId);
			if(StringUtils.isNotEmpty(openId)) {
				Sort sort = new Sort(Direction.DESC, "createTime");
				Pageable pageable = new PageRequest(start,count, sort);
				userWithdrawalRecordList = userWithdrawalApplyService.queryAllByApplyOpenIdAndDelFlag(openId, WechatConstant.delete_flag_1, pageable);
			} else {
				return ReturnMsgUtil.error(ExceptionEnum.user_not_exist);
			}
		} catch (Exception e) {
			logger.error("分页查询我的提现记录异常",e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		} 
		return ReturnMsgUtil.success(userWithdrawalRecordList);
	}
	
	/**
	 * @Description: 保存提现记录
	 * @DateTime:2017年11月9日 下午4:31:24
	 * @return ReturnMsg<Object>
	 * @throws Exception 
	 */
	@RequestMapping(path = "/save",method = RequestMethod.POST)
	@ResponseBody
	@OperationLog(value = "保存提现记录")
	public ReturnMsg<Object> getUserWithdrawalRecordList(String amount, HttpServletResponse response) throws Exception{
		try {
			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
//			String openId = "oIrGq0x10Ii4_F0_FKD8XYhdr7Lk";
			logger.info("保存提现记录查询参数openId=",openId);
			if(StringUtils.isNotEmpty(openId) && StringUtils.isNotEmpty(amount)) {
				BigDecimal amountb = new BigDecimal(amount);
				User userBalance = userService.queryUserBalanceInfoByOpenId(openId);
				String feeScale = userBalance.getFeeScale();
				//提现规则校验
				checkWithDrawal(amount,userBalance);
				
				UserWithdrawalApply userWithdrawalApply = new UserWithdrawalApply();
				userWithdrawalApply.setId(UUIDGenerator.generate());
				userWithdrawalApply.setApplyOpenId(openId);
				userWithdrawalApply.setApplyAmount(amountb);
				userWithdrawalApply.setFee(amountb.multiply(new BigDecimal(feeScale).divide(new BigDecimal("100"))));
				userWithdrawalApply.setApplyStatus(WithdrawalApplyStatusEnum.wait.getIndex());
				userWithdrawalApply.setApplyTime(new Date());
				userWithdrawalApplyService.save(userWithdrawalApply);
				
				//进账记录
				UserInOutDetail userInOutDetail2 = new UserInOutDetail();
				
				userInOutDetail2.setId(UUIDGenerator.generate());
				userInOutDetail2.setFee(amountb.multiply(new BigDecimal(feeScale).divide(new BigDecimal("100"))));
				userInOutDetail2.setAmount(amountb);
				userInOutDetail2.setPayType(UserInOutDetailPayTypeEnum.withdraw_out.getIndex());
				//交易人
				userInOutDetail2.setOpenId(openId);
				//被交易人
				userInOutDetail2.setTraderOpenId(openId);
				userInOutDetailService.save(userInOutDetail2);
				
				User user = new User();
				user.setOpenId(openId);
				user.setWithdrawBalance(new BigDecimal(amount));
				userService.updateUserBalanceInfoByOpenId(user);
			} else {
				return ReturnMsgUtil.error(ExceptionEnum.user_not_exist);
			}
		} catch (ServiceException se) {
			logger.error("保存提现记录异常",se);
			throw se;
		} catch (Exception e) {
			logger.error("保存提现记录异常",e);
			throw e;
		} 
		return ReturnMsgUtil.success();
	}
	
	/**
	 * @Description: 校验提现规则
	 * 每日提现总金额不能大于每日最大提现额度
	 * 每日提现次数不能大于每日最大次数限制
	 * @DateTime:2017年11月13日 下午2:27:28
	 * @return boolean
	 * @throws
	 */
	public void checkWithDrawal(String amount,User userBalance) throws Exception {
		if (null != userBalance) {
			String withdrawDayLimit = userBalance.getWithdrawDayLimit();
			BigDecimal withdrawDayMaxMoney = userBalance.getWithdrawDayMaxMoney();
			BigDecimal amountb = new BigDecimal(amount);
			//单次提现金额不能大于最大提现额度小于2元
			if(amountb.intValue() < 2 || amountb.compareTo(withdrawDayMaxMoney) > 0) {
				throw new ServiceException(ExceptionEnum.withdrawal_amount_min__max_error);
			}
			
			UserWithdrawalApply userWithdrawalApply = new UserWithdrawalApply();
			userWithdrawalApply.setApplyOpenId(userBalance.getOpenId());
			Map<String, String> applyCountMap = userWithdrawalApplyService.queryApplyCountByOpenIdAndApplyTime(userWithdrawalApply);
			if(null != applyCountMap) {
				String total = String.valueOf(applyCountMap.get("total"));
				String totalAmount = String.valueOf(applyCountMap.get("totalAmount"));
				
				//当天提现次数不能大于最大限制
				if(StringUtils.isNotEmpty(total) && Integer.valueOf(total) >= Integer.valueOf(withdrawDayLimit)) {
					throw new ServiceException(ExceptionEnum.withdrawal_day_limit_max_error);
				}
				
				//当天提现总金额不能大于最大限制
				if(StringUtils.isNotEmpty(totalAmount) && new BigDecimal(totalAmount).add(amountb).compareTo(withdrawDayMaxMoney) > -1) {
					throw new ServiceException(ExceptionEnum.withdrawal_amount_max_error);
				}
			}
		}
	}
}

