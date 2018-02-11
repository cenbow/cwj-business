package com.mm.dev.controller.user;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.util.ObjectUtils;
import com.common.util.ThumbnailatorUtils;
import com.common.util.GaussianBlur.GaussianBlurUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mm.dev.common.annotation.OperationLog;
import com.mm.dev.common.base.controller.BaseController;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.order.OrderPayment;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.enums.PaymentStatusEnum;
import com.mm.dev.service.order.IOrderPaymentService;
import com.mm.dev.service.user.IUserFilesService;
import com.mm.dev.service.user.IUserService;
import com.mm.dev.wechatUtils.ReturnMsgUtil;
import com.mm.dev.wechatUtils.UserSession;
/**
 * @ClassName: wechartController 
 * @Description: 用户上传的文件管理类
 * @author zhangxy
 * @date 2017年7月31日 下午3:03:05
 */
@Controller
@RequestMapping("/userFiles")
public class UserFilesController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(UserFilesController.class);
	
	@Autowired
	private IUserFilesService userFileService;
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private IOrderPaymentService orderPaymentService;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * @Description: 根据opoenId分页查询文件列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	@RequestMapping(value = "/{start}/{count}/list", method = RequestMethod.GET)
	@ResponseBody
	@OperationLog(value = "根据opoenId分页查询文件列表")
	public ReturnMsg<Object> findListByOpenIdAndFileCategory(
			@RequestParam(value="openId",required=false) String openId,
			@RequestParam(value="isMyOrder",required=false) String isMyOrder,
			@PathVariable("start") int start,
			@PathVariable("count") int count,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Page<UserFiles> userFileList = null;
		try {
//			UserSession.setSession(WechatConstant.OPEN_ID, "oIrGq0x10Ii4_F0_FKD8XYhdr7Lk");
//			openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			boolean isMy = false;
			if(StringUtils.isNotEmpty(openId)) {
				String sessionOpenId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
				if (openId.equals(sessionOpenId)) {
					isMy = true;
				}
			}
			if(!ObjectUtils.isAllBlank(start,count)) {
				Sort sort = new Sort(Direction.DESC, "updateTime");
				Pageable pageable = new PageRequest(start,count, sort);
				//查询我打赏过的
				if("true".equals(isMyOrder)) {
					//只能看自己打赏的
					if(isMy) {
						userFileList = userService.queryOrderPaymentUserFilesByOpenId(openId,pageable);
					}
					return ReturnMsgUtil.success(userFileList);
				}
				userFileList = userService.queryUserFilesList(openId,pageable);
				List<UserFiles> content = userFileList.getContent();
				if(!isMy && null != content) {
					for (UserFiles userFiles : content) {
						userFiles.setSmallFilePath(userFiles.getSmallBlurFilePath());
						userFiles.setFilePath(null);
					}
				}
			}
		} catch (Exception e) {
			logger.error("根据opoenId={}分页查询文件列表",openId,e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		}
		return ReturnMsgUtil.success(userFileList);
	}
	
	/**
	 * @Description: 根据文件id删除上传的文件
	 * @Datatime 2017年8月12日 下午7:48:17 
	 * @return ReturnMsg<Object>    返回类型
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	@OperationLog(value = "根据文件id删除上传的文件")
	public ReturnMsg<Object> deleteById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
//			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			if(StringUtils.isNotEmpty(openId)) {
				userFileService.deleteById(id);
			}
		} catch (Exception e) {
			logger.error("根据文件id={}删除上传的文件异常",id,e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		}
		return ReturnMsgUtil.success();
	}
	
	/**
	 * @Description: 根据文件ID查询模糊图片信息
	 * @DateTime:2017年11月3日 上午10:42:24
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@RequestMapping(value = "/query/blur/{id}", method = RequestMethod.GET)
	@ResponseBody
	@OperationLog(value = "根据文件ID查询模糊图片信息")
	public ReturnMsg<Object> findById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> blurInfoMap = null;
		try {
			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
//			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			if(StringUtils.isNotEmpty(openId)) {
				blurInfoMap = userFileService.queryBlurInfoById(id);
				//隐藏图片路径
				blurInfoMap.remove("filePath");
			}
		} catch (Exception e) {
			logger.error("根据文件ID={}查询模糊图片信息异常",id,e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		}
		return ReturnMsgUtil.success(blurInfoMap);
	}
	
	/**
	 * @Description: 根据订单号，文件ID查询单个文件信息
	 * @DateTime:2017年11月7日 下午3:04:46
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@OperationLog(value = "根据订单号，文件ID查询单个文件信息")
	@RequestMapping(value = "/query/baseInfo/{orderNo}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ReturnMsg<Object> queryInfoById(@PathVariable("orderNo") String orderNo,@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserFiles userFile = null;
		try {
			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
			if(StringUtils.isNotEmpty(openId)) {
				OrderPayment orderPayment = new OrderPayment();
				orderPayment.setOrderNo(orderNo);
				OrderPayment orderPaymentInfo = orderPaymentService.findOne(orderPayment);
				if(null != orderPaymentInfo) {
					String delFlag = orderPaymentInfo.getDelFlag();
					String paymentStatus = orderPaymentInfo.getPaymentStatus();
					if(WechatConstant.delete_flag_1.equals(delFlag) && PaymentStatusEnum.success.getIndex().equals(paymentStatus)) {
						userFile = userFileService.findById(id);
					}
				}
			} else {
				logger.error("根据订单号，文件ID查询单个文件信息openId为空");
				return ReturnMsgUtil.error(ExceptionEnum.user_not_exist);
			}
		} catch (Exception e) {
			logger.error("根据订单号={},文件ID={}查询单个文件信息异常",orderNo,id,e);
			return ReturnMsgUtil.error(ExceptionEnum.query_files_error);
		}
		return ReturnMsgUtil.success(userFile);
	}
	
	/**
	 * @Description: 根据文件ID获取打赏二维码
	 * @DateTime:2017年8月10日 上午11:29:22
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@RequestMapping("/getPayShareQrImage")
	@ResponseBody
	@OperationLog(value = "根据文件ID获取打赏二维码")
	public ReturnMsg<Object> getPayShareQrImage(String id,HttpServletRequest request, HttpServletResponse response){
		String qrcodeImagePath = null;
		if(StringUtils.isNotEmpty(id)) {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 指定纠错等级
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			// 指定编码格式
			hints.put(EncodeHintType.CHARACTER_SET, "GBK");
			//设值白框间距
			hints.put(EncodeHintType.MARGIN, 1);
			
			try {
				String hostPath = configProperties.getHostPath();
				BitMatrix bitMatrix = new MultiFormatWriter().encode("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configProperties.getAPPID()+"&redirect_uri=http%3a%2f%2f"+hostPath+"%2fwechat%2fcallback?id="+id+"&response_type=code&scope=snsapi_base&state=3&connect_redirect=1#wechat_redirect",
						BarcodeFormat.QR_CODE, configProperties.getWidth(), configProperties.getHeight(), hints);
				response.setContentType("image/jpeg");  
				// 将图像输出到Servlet输出流中。  
				ServletOutputStream sos = response.getOutputStream();  
				MatrixToImageWriter.writeToStream(bitMatrix, "jpeg", sos);
				sos.close();  
			} catch (Exception e) {
				logger.error("根据ID={}获取打赏二维码异常",id,e);
			} 
		}
		return ReturnMsgUtil.success(qrcodeImagePath);
	}
	
	/**
	 * @Description: 根据文件ID获取模糊图片流
	 * @param imgPath : 原图片路径
	 * @DateTime:2017年8月31日 下午1:26:43
	 * @return void
	 * @throws
	 */
	@RequestMapping("/getBlurImg")
	@OperationLog(value = "根据文件ID获取模糊图片流")
	public void getBlurImg(@RequestParam("id") String id, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			String format = "png";// 图像类型
			if(StringUtils.isNotEmpty(id)) {
				String imgPath = null;
				Map<String, String> queryBlurInfoById = userFileService.queryBlurInfoById(id);
				if(null != queryBlurInfoById) {
					imgPath = queryBlurInfoById.get("filePath");
					if(StringUtils.isNotEmpty(imgPath)){
						imgPath = imgPath.substring(7,imgPath.length());
					}
				}
				imgPath = configProperties.getImageUrl()+File.separator+imgPath;
				File file = new File(imgPath);
				BufferedImage gaussianBlurImgToBufferedImage = null;
				if(null != file && 204800<file.length()) {
					BufferedImage imgBufferedImage = ThumbnailatorUtils.ImgBufferedImage(imgPath,0.1f,format);
					gaussianBlurImgToBufferedImage = GaussianBlurUtil.setGaussianBlurImgToBufferedImage(imgBufferedImage, configProperties.getBlur());
				} else {
					gaussianBlurImgToBufferedImage = GaussianBlurUtil.setGaussianBlurImgToBufferedImage(imgPath, configProperties.getBlur());
				}
				ImageIO.write(gaussianBlurImgToBufferedImage, format, response.getOutputStream());
			}
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("根据文件ID={}生获取模糊图片流异常",id,e);
		}
	}
	
}

