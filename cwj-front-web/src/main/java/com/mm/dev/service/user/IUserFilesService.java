package com.mm.dev.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.dev.common.base.service.BaseService;
import com.mm.dev.entity.order.OrderPayment;
import com.mm.dev.entity.user.UserFiles;

/**
 * @Description: IuserService
 * @author Jacky
 * @date 2017年8月4日 下午10:04:02
 */
public interface IUserFilesService extends BaseService<UserFiles>{
	
	/**
	 * @Description: 根据ID查询
	 * @Datatime 2017年8月5日 下午6:19:51 
	 * @return User    返回类型
	 */
	UserFiles getUserFiles(String id) throws Exception;
    
    /**
     * @Description: 分页查询列表
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
    Page<UserFiles> getAll(String openId,String delFlag,Pageable pageable) throws Exception;

	/**
	 * @Description: 保存当前用户上传的文件
	 * @Datatime 2017年8月5日 下午6:12:19 
	 * @return void    返回类型
	 */
	public void saveUserFiles(UserFiles userFiles) throws Exception;
	
	/**
	 * @Description: 根据opoenId,文件分类查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	public List<UserFiles> findByOpenIdAndFileCategory(String openId,String fileCategory) throws Exception;
	
	/**
	 * @Description: 根据ID查询模糊图片信息
	 * @Datatime 2017年8月7日 下午9:55:50 
	 * @return void    返回类型
	 */
	public Map<String, String> queryBlurInfoById(String id) throws Exception;
	
}
