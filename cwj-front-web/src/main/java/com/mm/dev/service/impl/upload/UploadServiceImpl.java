package com.mm.dev.service.impl.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.common.util.ThumbnailatorUtils;
import com.common.util.UUIDGenerator;
import com.common.util.GaussianBlur.GaussianBlurUtil;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.fastdfs.FileManager;
import com.mm.dev.service.upload.IUploadService;
import com.mm.dev.service.user.IUserFilesService;
import com.mm.dev.service.wechat.IWechatService;

/**
 * @Description: UploadServiceImpl
 * @author Jacky
 * @date 2017年8月6日 上午10:52:47
 */
@Service
public class UploadServiceImpl implements IUploadService {
	
	Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private IWechatService WechatService;
	
	@Autowired
	private IUserFilesService userFilesService;
	
	/**
	 * @Description: 处理上传图片
	 * @Datatime 2017年8月6日 上午10:51:24 
	 * @return boolean    返回类型
	 */
	@Async
	public boolean uploadImageFiles(UserFiles userFiles,MultipartFile file) throws Exception {
		String openId = userFiles.getOpenId();
		String fileNames = file.getOriginalFilename();
        InputStream fileInputStream = file.getInputStream();
        if(StringUtils.isNotEmpty(fileNames) && null != fileInputStream) {
        	String uploadPath = configProperties.getImageUrl();
        	String fileName = null;
        	// 如果大于1说明是分片处理
        	// 如果大于1说明是分片处理
        	int chunks = userFiles.getChunks();
        	int chunk = userFiles.getChunk();
        	int split = fileNames.lastIndexOf(".");
        	// 文件名 
        	fileName = fileNames.substring(0,split);
        	//原文件格式   
        	String OriginfileSuffix = fileNames.substring(split+1,fileNames.length());
        	//转换格式
        	String fileSuffix = configProperties.getFileSuffix();
        	//UUID生成文件全名
        	fileName = userFiles.getGuid();
        	String fileNewNames = fileName + "." + fileSuffix;
        	
        	// 临时目录用来存放所有分片文件
        	String tempFileDir = uploadPath + File.separator + openId;
        	File parentFileDir = new File(tempFileDir);
        	if (!parentFileDir.exists()) {
        		parentFileDir.mkdirs();
        		parentFileDir.canWrite();
        		parentFileDir.canExecute();
        	}
        	// 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台(默认每片为5M)
        	File tempPartFile = new File(parentFileDir, fileName + "_" + chunk + ".part");
        	FileUtils.copyInputStreamToFile(fileInputStream,tempPartFile);
        	fileInputStream.close();
        	// 是否全部上传完成
        	// 所有分片都存在才说明整个文件上传完成
        	boolean uploadDone = true;
        	for (int i = 0; i < chunks; i++) {
        		File partFile = new File(parentFileDir, fileName + "_" + i
        				+ ".part");
        		if (!partFile.exists()) {
        			uploadDone = false;
        		}
        	}
        	// 所有分片文件都上传完成
        	// 将所有分片文件合并到一个文件中
        	if (uploadDone) {
        		String blurFilePathId = UUIDGenerator.generate();
        		//原图大图
        		File destTempFile = new File(tempFileDir, fileNewNames);
        		
        		//原图小图
        		File smallDestTempFile = new File(tempFileDir, fileName + "_small." + fileSuffix);
        		
        		//模糊大图
        		File destBlurFile = new File(tempFileDir,blurFilePathId + "_blur." + fileSuffix);
        		
        		//模糊小图
        		File smallDestBlurFile = new File(tempFileDir,blurFilePathId + "_blur_small." + fileSuffix);
        		
        		for (int i = 0; i < chunks; i++) {
        			File partFile = new File(parentFileDir, fileName + "_" + i + ".part");
        			FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
        			FileUtils.copyFile(partFile, destTempfos);
        			partFile.delete();
        			destTempfos.close();
        		}
        		String destTempFilePath = FileManager.upload(file,fileSuffix);
        		long destTempFileLength = destTempFile.length();
        		logger.info("生成的原图大图片路径destTempFilePath={}===destTempFileLength={}",destTempFilePath,destTempFileLength);
        		
        		//原图小图
        		FileUtils.copyFile(destTempFile, smallDestTempFile);
        		String smallDestFileDir = tempFileDir + File.separator + fileName + "_small." + fileSuffix;
        		ThumbnailatorUtils.ImgScale(destTempFile, smallDestFileDir,configProperties.getScale());
        		String smallDestTempFilePath = FileManager.upload(smallDestTempFile,fileSuffix);
        		long smallDestTempFileLength = smallDestFileDir.length();
        		logger.info("生成的原图小图片路径smallDestTempFilePath={}===smallDestTempFileLength={}",smallDestTempFilePath,smallDestTempFileLength);
        		
        		//生成模糊大图片
        		FileUtils.copyFile(destTempFile, destBlurFile);
        		String blurFileDir = tempFileDir + File.separator + blurFilePathId +"_blur." + fileSuffix;
        		if(userFiles.getBlur() == 0) {
        			userFiles.setBlur(configProperties.getBlur());
        		}
        		//模糊大图
        		GaussianBlurUtil.setGaussianBlurImg(blurFileDir, configProperties.getBlur());
        		String destBlurFilePath = FileManager.upload(destBlurFile,fileSuffix);
        		long destBlurFileLength = destBlurFile.length();
        		logger.info("生成的模糊大图图片路径destBlurFilePath={}===destBlurFileLength={}",destBlurFilePath,destBlurFileLength);
        		
        		//模糊小图
        		FileUtils.copyFile(destTempFile, smallDestBlurFile);
        		String smallBlurFileDir = tempFileDir + File.separator + blurFilePathId +"_blur_small." + fileSuffix;
        		ThumbnailatorUtils.ImgScale(destTempFile, smallBlurFileDir,configProperties.getScale());
        		GaussianBlurUtil.setGaussianBlurImg(smallBlurFileDir, configProperties.getSmallBlur());
        		String smallDestBlurFilePath = FileManager.upload(smallDestBlurFile,fileSuffix);
        		long smallDestBlurFileLength = smallDestBlurFile.length();
        		logger.info("生成的模糊小图图片路径smallDestBlurFilePath={}===smallDestBlurFileLength={}",smallDestBlurFilePath,smallDestBlurFileLength);

        		
        		//生成二维码
//        		String qrcodeFileDir = tempFileDir + File.separator + fileName + configProperties.getQrcodeSuffic() + "." + fileSuffix;
//        		ZxingHandler.encode2("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configProperties.getAPPID()+"&redirect_uri=http%3a%2f%2fjacky.tunnel.qydev.com%2fwechat%2fcallback&response_type=code&scope=snsapi_base&state=3&connect_redirect=1#wechat_redirect", configProperties.getWidth(), configProperties.getHeight(),qrcodeFileDir);
//        		logger.info("生成的二维码图片路径={}===qrcodeFileDir{}=",qrcodeFileDir,new File(qrcodeFileDir).length());
        		
        		 //输出合并图片  
//        		String blurQrcodeFileDir = tempFileDir + File.separator + configProperties.getBlurPrefix() + "_"+ configProperties.getQrcodePrefix() + fileNames;
//              addImageLogo(blurFileDir, qrcodeFileDir, blurQrcodeFileDir, 0, 0);
                
        		//关联用户
        		String userFilesId = UUIDGenerator.generate();
        		userFiles.setId(userFilesId);
        		userFiles.setOpenId(openId);
        		userFiles.setCreateTime(new Date());
        		userFiles.setUpdateTime(new Date());
        		userFiles.setFileCategory(WechatConstant.file_category_image);
        		userFiles.setFileNames(fileNames);
//        		userFiles.setFileNewNames(fileNewNames);
        		userFiles.setFileSize(String.valueOf(destTempFileLength));
        		userFiles.setFileSuffic(OriginfileSuffix);
        		String random = userFiles.getRandom();
        		if(StringUtils.isNotEmpty(random) && WechatConstant.random_yes.equals(random)) {
        			userFiles.setPrice(randomPrice(userFiles.getPrice()));
        		}
        		userFiles.setFilePath(destTempFilePath);
    			userFiles.setSmallFilePath(smallDestTempFilePath);
    			userFiles.setBlurFilePath(destBlurFilePath);
    			userFiles.setSmallBlurFilePath(smallDestBlurFilePath);
        		logger.info("保存上传资源userFiles={}",userFiles);
        		userFilesService.saveUserFiles(userFiles);
        		FileUtils.deleteDirectory(parentFileDir);
        		WechatService.sendCustomMessages("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configProperties.getAPPID()+"&redirect_uri=http%3a%2f%2f"+configProperties.getHostPath()+"%2fwechat%2fcallback?id="+userFilesId+"&response_type=code&scope=snsapi_base&state=2&connect_redirect=1#wechat_redirect'>已为您生成好模糊图，点击查看</a>",userFiles.getOpenId());
        	} else {
        		// 临时文件创建失败
//        		if (chunk == chunks -1) {
//        			FileUtils.deleteDirectory(parentFileDir);
//        			logger.error("临时分配文件创建失败");
//        		}
        	}
        }
        return true;
	} 
	
	/**
	 * @Description: 处理上传视频
	 * @Datatime 2017年8月6日 上午10:51:24 
	 * @return boolean    返回类型
	 */
	@Async
	public boolean uploadVideoFiles(UserFiles userFiles,MultipartFile file) throws Exception {
		String openId = userFiles.getOpenId();
		String fileNames = file.getOriginalFilename();
        InputStream fileInputStream = file.getInputStream();
        if(StringUtils.isNotEmpty(fileNames) && null != fileInputStream) {
        	String uploadPath = configProperties.getImageUrl();
        	String fileName = null;
        	// 如果大于1说明是分片处理
        	int chunks = userFiles.getChunks();
        	int chunk = userFiles.getChunk();
        	// 获取文件后缀名 
		    String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        	//UUID生成文件全名
        	fileName = userFiles.getGuid();
        	String fileNewNames = fileName + "." + fileSuffix;
        	
        	// 临时目录用来存放所有分片文件
        	String tempFileDir = uploadPath + File.separator + openId;
        	File parentFileDir = new File(tempFileDir);
        	if (!parentFileDir.exists()) {
        		parentFileDir.mkdirs();
        		parentFileDir.canWrite();
        		parentFileDir.canExecute();
        	}
        	// 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台(默认每片为5M)
        	File tempPartFile = new File(parentFileDir, fileName + "_" + chunk + ".part");
        	FileUtils.copyInputStreamToFile(fileInputStream,tempPartFile);
        	fileInputStream.close();
        	// 是否全部上传完成
        	// 所有分片都存在才说明整个文件上传完成
        	boolean uploadDone = true;
        	for (int i = 0; i < chunks; i++) {
        		File partFile = new File(parentFileDir, fileName + "_" + i
        				+ ".part");
        		if (!partFile.exists()) {
        			uploadDone = false;
        		}
        	}
        	// 所有分片文件都上传完成
        	// 将所有分片文件合并到一个文件中
        	if (uploadDone) {
        		//视频文件
        		File destTempFile = new File(tempFileDir, fileNewNames);
        		for (int i = 0; i < chunks; i++) {
        			File partFile = new File(parentFileDir, fileName + "_" + i + ".part");
        			FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
        			FileUtils.copyFile(partFile, destTempfos);
        			partFile.delete();
        			destTempfos.close();
        		}
        		String destTempFilePath = FileManager.upload(destTempFile,fileSuffix);
        		long destTempFileLength = destTempFile.length();
        		logger.info("生成的视频文件路径destTempFilePath={}===destTempFileLength={}",destTempFilePath,destTempFileLength);
                
        		//关联用户
        		String userFilesId = UUIDGenerator.generate();
        		userFiles.setId(userFilesId);
        		userFiles.setOpenId(openId);
        		userFiles.setCreateTime(new Date());
        		userFiles.setUpdateTime(new Date());
        		userFiles.setFileCategory(WechatConstant.file_category_video);
        		userFiles.setFileNames(fileNames);
//        		userFiles.setFileNewNames(fileNewNames);
        		userFiles.setFileSize(String.valueOf(destTempFileLength));
        		userFiles.setFileSuffic(fileSuffix);
        		String random = userFiles.getRandom();
        		if(StringUtils.isNotEmpty(random) && WechatConstant.random_yes.equals(random)) {
        			userFiles.setPrice(randomPrice(userFiles.getPrice()));
        		}
        		userFiles.setFilePath(destTempFilePath);
        		userFiles.setSmallFilePath(destTempFilePath);
    			userFiles.setBlurFilePath("img/1.jpg");
    			userFiles.setSmallBlurFilePath("img/1.jpg");
        		logger.info("保存上传资源userFiles={}",userFiles);
        		userFilesService.saveUserFiles(userFiles);
        		FileUtils.deleteDirectory(parentFileDir);
        		WechatService.sendCustomMessages("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configProperties.getAPPID()+"&redirect_uri=http%3a%2f%2f"+configProperties.getHostPath()+"%2fwechat%2fcallback?id="+userFilesId+"&response_type=code&scope=snsapi_base&state=2&connect_redirect=1#wechat_redirect'>已为您生成好模糊图，点击查看</a>",userFiles.getOpenId());
        	} else {
        		// 临时文件创建失败
//        		if (chunk == chunks -1) {
//        			FileUtils.deleteDirectory(parentFileDir);
//        			logger.error("文件创建失败");
//        		}
        	}
        }
        return true;
	} 
	
	/**
	 * 随机产生特殊数字
	 * @param price
	 * @return
	 */
	private BigDecimal randomPrice(final BigDecimal price) {
		double[] price_0 = {0.66,1.86,2.22,3.14,5.20,8.88,6.66,9.99,1.96,1.88
				,16.68,18.88,16.66,10.88,19.99,19.69,13.96,18.69,18.96,15.20
				,21.66,21.68,26.66,28.88,29.99,29.69,26.96,28.69,28.96,20.88
				,31.66,31.68,36.66,38.88,39.99,39.69,36.96,38.69,38.96,30.88
				,41.66,41.68,46.66,48.88,49.99,49.69,46.96,48.69,48.96,40.88
				,51.66,51.68,56.66,58.88,59.99,59.69,56.96,58.69,58.96,50.88
				,63.66,66.68,66.66,68.88,69.99,69.69,66.96,68.69,68.96,60.88
				,71.66,71.68,76.66,78.88,79.99,79.69,76.96,78.69,78.96,70.88
				,81.66,81.68,86.66,88.88,89.99,89.69,86.96,88.69,88.96,80.88
				,91.66,91.68,96.66,98.88,99.99,99.69,96.96,98.69,98.96,90.88
		};
		int len = price.intValue();
		double[] copyOfRange = Arrays.copyOfRange(price_0, 0, len);
		Random random = new Random();//创建随机对象
		int arrIdx = random.nextInt(len);//随机数组索引
		double ds = copyOfRange[arrIdx];//获取数组值
		return new BigDecimal(Double.valueOf(ds).toString());
	}
}
