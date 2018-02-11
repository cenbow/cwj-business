package com.common.util.GaussianBlur;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.common.util.GaussianBlur.filter.GaussianFilter;
public class GaussianBlurUtil {
    public GaussianBlurUtil(){}
    
    /**
     * @Description:生成高斯模糊图片
     * @param imgPath   : 生成模糊图片路径
     * @param filterNum : 设置模糊度
     * @DateTime:2017年8月1日 下午2:53:56
     * @return void
     * @throws
     */
    public static void setGaussianBlurImg(String imgPath, int filterNum) {
    	try{
            File file = new File(imgPath);
            BufferedImage b1 = ImageIO.read(file);
            GaussianFilter filter = new GaussianFilter(filterNum);
            BufferedImage blurredImage = filter.filter(b1, new BufferedImage(b1.getWidth(), b1.getHeight(), 2));
            ImageIO.write(blurredImage, "png", file);
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN))
                Desktop.getDesktop().open(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Description:生成高斯模糊图片
     * @param source    : 原图片
     * @param filterNum : 设置模糊度
     * @DateTime:2017年8月1日 下午2:53:56
     * @return void
     * @throws
     */
    public static BufferedImage setGaussianBlurImgToBufferedImage(BufferedImage source, int filterNum) {
    	BufferedImage blurredImage = null;
    	try{
            GaussianFilter filter = new GaussianFilter(filterNum);
            blurredImage = filter.filter(source, new BufferedImage(source.getWidth(), source.getHeight(), 2));
        } catch(Exception e) {
            e.printStackTrace();
        }
    	return blurredImage;
    }
    
    /**
     * @Description:生成高斯模糊图片
     * @param source    : 原图片
     * @param filterNum : 设置模糊度
     * @DateTime:2017年8月1日 下午2:53:56
     * @return void
     * @throws
     */
    public static BufferedImage setGaussianBlurImgToBufferedImage(String imgPath, int filterNum) {
    	BufferedImage blurredImage = null;
    	try{
    		File file = new File(imgPath);
            BufferedImage b1 = ImageIO.read(file);
            GaussianFilter filter = new GaussianFilter(filterNum);
            blurredImage = filter.filter(b1, new BufferedImage(b1.getWidth(), b1.getHeight(), 2));
        } catch(Exception e) {
            e.printStackTrace();
        }
    	return blurredImage;
    }
    
	public static void main(String[] args) {
        String imgPath = "target\\2c9229eb5fa56e01015fa56e01eb0000.png";
        int filterNum = 60;
        setGaussianBlurImg(imgPath, filterNum);
	}
}
