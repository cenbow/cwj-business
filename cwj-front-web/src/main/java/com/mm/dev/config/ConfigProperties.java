package com.mm.dev.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "PLAY")
public class ConfigProperties {
	
	/**
	 * APPID
	 */
	@Value("${PLAY.WECHAT.APPID}")
	private String APPID;
	
	/**
	 * APPKEY
	 */
	@Value("${PLAY.WECHAT.APPKEY}")
	private String APPSECRET;
	
	/**
	 * 图片上传目的文件夹
	 */
	@Value("${PLAY.UPLOAD.IMAGE.PATH}")
	private String imageUrl;
	
	/**
	 * 图片上传最大大小
	 */
	@Value("${PLAY.UPLOAD.IMAGE.SIZE}")
	private int imageMaxSize;
	
	/**
	 * 视频上传最大大小
	 */
	@Value("${PLAY.UPLOAD.VIDEO.SIZE}")
	private int videoMaxSize;
	
	public int getVideoMaxSize() {
		return videoMaxSize;
	}

	public void setVideoMaxSize(int videoMaxSize) {
		this.videoMaxSize = videoMaxSize;
	}

	/**
	 * 图片后缀名
	 */
	@Value("${PLAY.UPLOAD.IMAGE.FILESUFFIX}")
	private String fileSuffix;
	
	/**
	 * 上传图片支持的后缀名
	 */
	@Value("${PLAY.UPLOAD.IMAGE.SUFFIXS}")
	private String imageFileSuffixs;
	
	/**
	 * 上传视频支持的后缀名
	 */
	@Value("${PLAY.UPLOAD.VIDEO.SUFFIXS}")
	private String videoFileSuffixs;
	
	/**
	 * 服务器地址
	 */
	@Value("${PLAY.WECHAT.HOST}")
	private String hostPath;
	
	@Value("${PLAY.WECHAT.MCHID}")
	private String mchId;
	
	/**
	 * 模糊图片宽度
	 */
	@Value(value="${PLAY.BLURIMAGE.WIDTH}")
	private int width;
	
	/**
	 * 模糊图片高度
	 */
	@Value(value="${PLAY.BLURIMAGE.HEIGHT}")
	private int height;
	
	/**
	 * 模糊图片模糊度
	 */
	@Value(value="${PLAY.BLURIMAGE.BLUR}")
	private int blur;
	
	/**
	 * 小图模糊图片模糊度
	 */
	@Value(value="${PLAY.BLURIMAGE.SMALLBLUR}")
	private int smallBlur;
	
	
	/**
	 * 模糊图片压缩比例（1最高质量 原图）
	 */
	@Value(value="${PLAY.BLURIMAGE.SCALE}")
	private Float scale;
	
	/**
	 * 模糊图片最大值
	 */
	@Value(value="${PLAY.BLURIMAGE.PRICE}")
	private BigDecimal price;
	
	/**
	 * 模糊图片最小值
	 */
	@Value(value="${PLAY.BLURIMAGE.PRICEMIN}")
	private BigDecimal priceMin;
	
	/**
	 * 模糊图片金额是否随机(1：是 2:否)
	 */
	@Value(value="${PLAY.BLURIMAGE.RANDOM}")
	private String random;
	
	public String getAPPID() {
		return APPID;
	}

	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}

	public String getAPPSECRET() {
		return APPSECRET;
	}

	public void setAPPSECRET(String aPPSECRET) {
		APPSECRET = aPPSECRET;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getHostPath() {
		return hostPath;
	}

	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBlur() {
		return blur;
	}

	public void setBlur(int blur) {
		this.blur = blur;
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

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public int getImageMaxSize() {
		return imageMaxSize;
	}

	public void setImageMaxSize(int imageMaxSize) {
		this.imageMaxSize = imageMaxSize;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	public int getSmallBlur() {
		return smallBlur;
	}

	public void setSmallBlur(int smallBlur) {
		this.smallBlur = smallBlur;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getImageFileSuffixs() {
		return imageFileSuffixs;
	}

	public void setImageFileSuffixs(String imageFileSuffixs) {
		this.imageFileSuffixs = imageFileSuffixs;
	}

	public String getVideoFileSuffixs() {
		return videoFileSuffixs;
	}

	public void setVideoFileSuffixs(String videoFileSuffixs) {
		this.videoFileSuffixs = videoFileSuffixs;
	}
}
