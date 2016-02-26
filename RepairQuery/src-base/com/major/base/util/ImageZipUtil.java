package com.major.base.util;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @Title: ImageZipUtil.java
 * @Description: TODO(图片处理类)
 * @author eleven
 * @date 2014-3-3 上午11:45:09
 *
 */
public final class ImageZipUtil {
	
	/** 私有构造函数 ：请不要实例化工具类*/
	private ImageZipUtil() {
		throw new Error("Please Dont't Instance UtilClass!");
	}
	
	/**
	 * 
	 * @Title: zipImageFile
	 * @Description: TODO(等比例压缩图片文件:先保存原文件，再压缩、上传)
	 * @param oldFile  要进行压缩的文件
	 * @param newFile  新文件
	 * @param width    宽度 //设置宽度时（高度传入0，等比例缩放）
	 * @param height   高度 //设置高度时（宽度传入0，等比例缩放）
	 * @param quality  质量
	 * @return
	 * @return: String 返回压缩后的文件的全路径
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	public static String zipImageFile(File oldFile,File newFile, int width, int height,
			float quality) {
		if (oldFile == null) {
			return null;
		}
		try {
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(oldFile);
			int w = srcFile.getWidth(null);
			System.out.println(w);
			int h = srcFile.getHeight(null);
			System.out.println(h);
			double bili;
			if(width>0){
				bili=width/(double)w;
				height = (int) (h*bili);
			}else{
				if(height>0){
					bili=height/(double)h;
					width = (int) (w*bili);
				}
			}
			/** 宽,高设定 */
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
			//String filePrex = oldFile.getName().substring(0, oldFile.getName().indexOf('.'));
			/** 压缩后的文件名 */
			//newImage = filePrex + smallIcon+  oldFile.getName().substring(filePrex.length());

			/** 压缩之后临时存放位置 */
			FileOutputStream out = new FileOutputStream(newFile);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/** 压缩质量 */
			jep.setQuality(quality, true);
			encoder.encode(tag, jep);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile.getAbsolutePath();
	}

   /**
    * 
    * @Title: zipWidthHeightImageFile
    * @Description: TODO(按宽度高度压缩图片文件:先保存原文件，再压缩、上传)
    * @param oldFile  要进行压缩的文件全路径
    * @param newFile  新文件
    * @param width    宽度
    * @param height   高度
    * @param quality  质量
    * @return
    * @return: String 返回压缩后的文件的全路径
    * @author: eleven
    * @date: 2014-3-3
    * @history: Date  Author  Version  Description
    * 			 ---------------------------------------------------------*
    * 			 2014-3-3  eleven v1.0.0   修改原因
    */
	public static String zipWidthHeightImageFile(File oldFile,File newFile, int width, int height,
			float quality) {
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(oldFile);
			//int w = srcFile.getWidth(null);
			//System.out.println(w);
			//int h = srcFile.getHeight(null);
			//System.out.println(h);

			/** 宽,高设定 */
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
			//String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
			/** 压缩后的文件名 */
			//newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());

			/** 压缩之后临时存放位置 */
			FileOutputStream out = new FileOutputStream(newFile);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/** 压缩质量 */
			jep.setQuality(quality, true);
			encoder.encode(tag, jep);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newImage;
	}
}
