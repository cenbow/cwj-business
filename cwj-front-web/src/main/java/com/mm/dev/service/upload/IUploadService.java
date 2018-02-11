package com.mm.dev.service.upload;

import org.springframework.web.multipart.MultipartFile;

import com.mm.dev.entity.user.UserFiles;

/**
 * @Description: IUploadService
 * @author Jacky
 * @date 2017年8月6日 上午10:52:27
 */
public interface IUploadService {
	
	/**
	 * 处理图片上传
	 * @Description: TODO
	 * @Datatime 2017年8月6日 上午10:51:24 
	 * @return boolean    返回类型
	 */
	public boolean uploadImageFiles(UserFiles userFiles,MultipartFile file) throws Exception;
	
	/**
	 * 处理视频上传
	 * @Description: TODO
	 * @Datatime 2017年8月6日 上午10:51:24 
	 * @return boolean    返回类型
	 */
	public boolean uploadVideoFiles(UserFiles userFiles,MultipartFile file) throws Exception;
	
}
