package com.mm.dev.entity.user;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mm.dev.common.base.model.BaseEntity;

/**
 * @Description: 用户文件实体类
 * @author Jacky
 * @date 2017年8月4日 下午7:16:40
 */
@Entity
@Table(name = "t_user_files")
public class UserFiles extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * openId
	 */
	private String openId;
	
	/**
	 * 文件全名
	 */
	private String fileNames;
	
	/**
	 * uuid生成唯一文件全名
	 */
	private String fileNewNames;
	
	/**
	 * 文件存储路径
	 */
	private String filePath;
	
	/**
	 * 模糊图片存储路径
	 */
	private String blurFilePath;
	
	/**
	 * 原图小图尺寸
	 */
	private String smallFilePath;
	
	/**
	 * 模糊图小图尺寸
	 */
	private String smallBlurFilePath;
	
	/**
	 * 文件后缀
	 */
	private String fileSuffic;
	
	/**
	 * 文件大小
	 */
	private String fileSize;
	
	/**
	 * 文件分类（1：图片 2：视频）
	 */
	private String fileCategory;
	
	/**
	 * 是否选择随机打赏（1： 是 2：否）
	 */
	@Column(insertable=false)
	private String random;
	
	/**
	 * 打赏金额最大值
	 */
	private BigDecimal price;
	
	/**
	 * 打赏最小值
	 */
	private BigDecimal priceMin;
	
	private String title;
	
	/**
	 * 打赏图片模糊度
	 */
	private Integer blur;
	
	@Transient
	private User user;
	
	/**
	 * 总分片数
	 */
	@Transient
	private int chunks = 1;
	
	/**
	 * 当前分片数
	 */
	@Transient
	private int chunk;
	
	/**
	 * 分片上传guid
	 */
	@Transient
	private String guid;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileCategory() {
		return fileCategory;
	}

	public void setFileCategory(String fileCategory) {
		this.fileCategory = fileCategory;
	}

	public String getFileSuffic() {
		return fileSuffic;
	}

	public void setFileSuffic(String fileSuffic) {
		this.fileSuffic = fileSuffic;
	}

	public String getFileNewNames() {
		return fileNewNames;
	}

	public void setFileNewNames(String fileNewNames) {
		this.fileNewNames = fileNewNames;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(BigDecimal priceMin) {
		this.priceMin = priceMin;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBlurFilePath() {
		return blurFilePath;
	}

	public void setBlurFilePath(String blurFilePath) {
		this.blurFilePath = blurFilePath;
	}

	public Integer getBlur() {
		return blur;
	}

	public void setBlur(Integer blur) {
		this.blur = blur;
	}

	public String getSmallFilePath() {
		return smallFilePath;
	}

	public void setSmallFilePath(String smallFilePath) {
		this.smallFilePath = smallFilePath;
	}

	public String getSmallBlurFilePath() {
		return smallBlurFilePath;
	}

	public void setSmallBlurFilePath(String smallBlurFilePath) {
		this.smallBlurFilePath = smallBlurFilePath;
	}

	public int getChunks() {
		return chunks;
	}

	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

	public int getChunk() {
		return chunk;
	}

	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
