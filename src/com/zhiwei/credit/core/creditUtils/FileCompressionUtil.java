package com.zhiwei.credit.core.creditUtils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zhiwei.core.util.AppUtil;

public class FileCompressionUtil {
	/**
	* 压缩图片方法
	* 
	* @param oldFile
	*            要压缩的图片路径
	* @param newFile
	*            压缩后添加的后缀名(在扩展名前添加,不会改变格式)
	* @param width
	*            压缩宽
	* @param height
	*            压缩高
	* @param percentage
	*            是否等比例压缩,true则宽高比自动调整
	* @return
	* @throws Exception
	*/
	public static String[] reduceImg(String oldFile, String fileName, int widthdist,
		    int heightdist, boolean percentage) {
		//StringBuffer data = new StringBuffer(); 
		String[] data = new String[3];
		String truename=null;
		   try {
		    File srcfile = new File(oldFile);
		    if (!srcfile.exists()) {
		    data[0]="Y";
		    data[1]="文件已存在！";
		    data[2]="";
		    }
		    Image src = javax.imageio.ImageIO.read(srcfile);
		    if (percentage) {
			     // 为等比压缩计算输出的宽高
			     double rate1 = ((double) src.getWidth(null))
			       / (double) widthdist + 0.1;
			     double rate2 = ((double) src.getHeight(null))
			       / (double) heightdist + 0.1;
			     double rate = rate1 > rate2 ? rate1 : rate2;
			     int new_w = (int) (((double) src.getWidth(null)) / rate);
			     int new_h = (int) (((double) src.getHeight(null)) / rate);
			     // 设定宽高
			     BufferedImage tag = new BufferedImage(new_w, new_h,
			       BufferedImage.TYPE_INT_RGB);
			     // 设定文件扩展名
			     String path = oldFile.substring(0, oldFile.lastIndexOf('/'));//
			     FileUtil.mkDirectory(path+"/"+fileName); //创建压缩存放文件的目录
			     
			     String imageName = oldFile.substring(oldFile.lastIndexOf("/"), oldFile.lastIndexOf('.')); //文件真实名
			     
			     	//文件扩展名
			     String filePrex = oldFile
			       .substring(0, oldFile.lastIndexOf('.'));
			     
			     String extendname = oldFile.substring(filePrex.length());
			     //新文件名
			     String newFile = path+"/"+fileName+""+imageName+"_"+fileName+extendname;
			     //文件的存储路径
			     truename = imageName+"_"+fileName+extendname;
			     truename = truename.substring(1);
			     // 生成图片
			     // 两种方法,效果与质量都相同,效率差不多
	//		     tag.getGraphics().drawImage(src.getScaledInstance(widthdist,heightdist, Image.SCALE_SMOOTH), 0, 0, null);
			     tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,Image.SCALE_AREA_AVERAGING), 0, 0, null);
			     FileOutputStream out = new FileOutputStream(newFile);
			     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			     encoder.encode(tag);
			     out.close();
			    } else {
			     // 设定宽高
			     BufferedImage tag = new BufferedImage(widthdist, heightdist,
			       BufferedImage.TYPE_INT_RGB);
			     // 设定文件扩展名
			     String path = oldFile.substring(0, oldFile.lastIndexOf('/'));//
			     FileUtil.mkDirectory(path+"/"+fileName); //创建压缩存放文件的目录
			     
			     String imageName = oldFile.substring(oldFile.lastIndexOf("/"), oldFile.lastIndexOf('.')); //文件真实名
			     
			     	//文件扩展名
			     String filePrex = oldFile
			       .substring(0, oldFile.lastIndexOf('.'));
			     
			     String extendname = oldFile.substring(filePrex.length());
			     //新文件名
			     String newFile = path+"/"+fileName+""+imageName+"_"+fileName+extendname;
			     truename = imageName+"_"+fileName+extendname;
			     truename = truename.substring(1);
			     // 生成图片
			     // 两种方法,效果与质量都相同,第二种效率比第一种高,约一倍
			     // tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
			     tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,Image.SCALE_AREA_AVERAGING), 0, 0, null);
			     FileOutputStream out = new FileOutputStream(newFile);
			     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			     encoder.encode(tag);
			     out.close();
			  }
		    data[2]=truename;
		   }catch (IOException ex) {
		    ex.printStackTrace();
		    data[0]="N";
		    data[1]="压缩文件失败!";
		    data[2]="";
		   }
		   return data;
		}
}
