package com.mm.dev.controller.upload;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.mm.dev.common.annotation.OperationLog;
import com.mm.dev.common.base.controller.BaseController;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.exception.ServiceException;
import com.mm.dev.fastdfs.FileManager;
import com.mm.dev.service.upload.IUploadService;
import com.mm.dev.service.wechat.IWechatService;
import com.mm.dev.wechatUtils.ReturnMsgUtil;
import com.mm.dev.wechatUtils.UserSession;

/**
 * @Description: UploadController
 * @author Jacky
 * @date 2017年8月6日 上午10:53:01
 */
@Controller
@RequestMapping("/wechat/upload")
public class UploadController extends BaseController{
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private IWechatService WechatService;
	
	@Autowired
	private IUploadService uploadService;
	
	/**
	 * @throws Exception 
	 * @Description: 上传图片
	 * @DateTime:2017年8月1日 下午12:31:18
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@RequestMapping(value = "/image",method=RequestMethod.POST)
	@ResponseBody
	@OperationLog(value = "上传文件")
	public ReturnMsg<Object> fileImageUpload(UserFiles userFiles,HttpServletRequest request, HttpServletResponse response) throws Exception {
//		UserSession.setSession(WechatConstant.OPEN_ID, "oIrGq0x10Ii4_F0_FKD8XYhdr7Lk");
		String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
    	if(StringUtils.isNotEmpty(openId)) {
    		StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
    		Iterator<String> iterator = req.getFileNames();
    		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    		if (isMultipart) {
    			WechatService.sendCustomMessages("正在拼命处理中...",openId);
    			while (iterator.hasNext()) {
    				MultipartFile file = req.getFile(iterator.next());
    				if(null == userFiles || null == file) {
    					throw new ServiceException(ExceptionEnum.param_not_null);
    				}
    		        if (file.getSize() >= configProperties.getImageMaxSize())
    		        {
    		            throw new ServiceException(ExceptionEnum.upload_image_max_size_error);
    		        }
    		        
    		        String fileNames = file.getOriginalFilename();
	        		String ext = fileNames.substring(fileNames.lastIndexOf(".")+1,fileNames.length());
	        		String imageFileSuffixs = configProperties.getImageFileSuffixs();
	        		if(!ArrayUtils.contains(imageFileSuffixs.split(","), ext)){
	        			throw new ServiceException(ExceptionEnum.upload_image_ext_error);
	        		}
    				userFiles.setOpenId(openId);
    				uploadService.uploadImageFiles(userFiles, file);
    			}
    		}
    	} else {
    		 return ReturnMsgUtil.error(ExceptionEnum.not_login);
    	}
        return ReturnMsgUtil.success();
    }
	
	/**
	 * @throws Exception 
	 * @Description: 上传视频
	 * @DateTime:2017年8月1日 下午12:31:18
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@RequestMapping(value = "/video",method=RequestMethod.POST)
	@ResponseBody
	@OperationLog(value = "上传视频")
	public ReturnMsg<Object> fileVideoUpload(UserFiles userFiles,HttpServletRequest request, HttpServletResponse response) throws Exception {
//		UserSession.setSession(WechatConstant.OPEN_ID, "oIrGq0x10Ii4_F0_FKD8XYhdr7Lk");
		String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
    	if(StringUtils.isNotEmpty(openId)) {
    		StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
    		Iterator<String> iterator = req.getFileNames();
    		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    		if (isMultipart) {
    			WechatService.sendCustomMessages("正在拼命处理中...",openId);
    			while (iterator.hasNext()) {
    				MultipartFile file = req.getFile(iterator.next());
    				if(null == userFiles || null == file) {
    					throw new ServiceException(ExceptionEnum.param_not_null);
    				}
    		        if (file.getSize() >= configProperties.getImageMaxSize())
    		        {
    		            throw new ServiceException(ExceptionEnum.upload_video_max_size_error);
    		        }
    		        
    		        String fileNames = file.getOriginalFilename();
	        		String ext = fileNames.substring(fileNames.lastIndexOf(".")+1,fileNames.length());
	        		String videoFileSuffixs = configProperties.getVideoFileSuffixs();
	        		if(!ArrayUtils.contains(videoFileSuffixs.split(","), ext)){
	        			throw new ServiceException(ExceptionEnum.upload_video_ext_error);
	        		}
    				userFiles.setOpenId(openId);
    				uploadService.uploadVideoFiles(userFiles, file);
    			}
    		}
    	} else {
    		 return ReturnMsgUtil.error(ExceptionEnum.not_login);
    	}
        return ReturnMsgUtil.success();
    }
	
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	@ResponseBody
	public ReturnMsg<Object> add(UserFiles userFiles, HttpServletRequest request)
	        throws IOException, MyException {
		StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
		Iterator<String> iterator = req.getFileNames();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			while (iterator.hasNext()) {
				MultipartFile attach = req.getFile(iterator.next());
				// 获取文件后缀名 
			    String ext = attach.getOriginalFilename().substring(attach.getOriginalFilename().lastIndexOf(".")+1);
//			    FastDFSFile file = new FastDFSFile(attach.getBytes(),ext);
//			    NameValuePair[] meta_list = new NameValuePair[4];
//			    meta_list[0] = new NameValuePair("fileName", attach.getOriginalFilename());
//			    meta_list[1] = new NameValuePair("fileLength", String.valueOf(attach.getSize()));
//			    meta_list[2] = new NameValuePair("fileExt", ext);
//			    meta_list[3] = new NameValuePair("fileAuthor", "ivplay");
			    String filePath = FileManager.upload(attach,ext);
			    return ReturnMsgUtil.success(filePath);
			}
		}
	    return ReturnMsgUtil.success();
	}
}
