package com.zhiwei.credit.core.creditUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


/**
 * @function file util 
 * 
 * 
 * 如何用JAVA实现文件的覆盖

try {
        BufferedWriter out = new BufferedWriter(new FileWriter("outfilename"));
        out.write("aString");
        out.close();
    } catch (IOException e) {
    }
这种情况就是覆盖的
    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("filename", true));
        out.write("aString");
        out.close();
    } catch (IOException e) {
    }
这种情况是添加
 * 
 * @author credit004
 *
 */
public class FileUtil {
	
	private static final int BUFFER_SIZE = 16*1024;//16K
	
	/**把 src 文件 复制 到 dst去*/
	public static boolean fileUpload(File src, File dst, final int size){
		
		try {
			InputStream in = null;
			OutputStream out = null;
			
			try {
				in = new BufferedInputStream(new FileInputStream(src),size);
				out = new BufferedOutputStream(new FileOutputStream(dst),size);//覆盖原文件
				
				byte[] buffer = new byte[size];
				
				while(in.read(buffer) > 0){
					out.write(buffer);
				}
			}finally{
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	/**把 src 文件 复制 到 dst去*/
	public static void fileUpload(File src, File dst){
		
		try {
			InputStream in = null;
			OutputStream out = null;
			
			try {
				in = new BufferedInputStream(new FileInputStream(src));
				out = new BufferedOutputStream(new FileOutputStream(dst));
				
				byte[] buffer = new byte[BUFFER_SIZE];
				
				while(in.read(buffer) > 0){
					out.write(buffer);
				}
			}finally{
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
//			file = null;
		}
		return false;
	}
	
	public static String getExtention(String fileName){
		
		int pos = fileName.lastIndexOf(".");
		
		return fileName.substring(pos);
		
	}
	
	public static String getExtentionUpper(String fileName){
		
		int pos = fileName.lastIndexOf(".")+1;
		
		return fileName.substring(pos).toUpperCase();
		
	}
	
	/**
	 * 返回上传的结果，成功与否
	 */
	public static boolean upload(String uploadFileName, String savePath, File uploadFile) {
		boolean flag = false;
		try {
			uploadForName(uploadFileName, savePath, uploadFile);
			flag = true;
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 上传文件并返回上传后的文件名
	 */
	public static String uploadForName(String uploadFileName, String savePath, File uploadFile) throws IOException {
		String newFileName = uploadFileName; //checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(savePath + newFileName);
//			BufferedImage bi = ImageIO.read(resFile);{
//				if(bi == null){
//				System.out.println(此文件不为图片文件&quot;);
//			}
			fis = new FileInputStream(uploadFile);
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return newFileName;
	}

	/**
	 * 判断文件名是否已经存在，如果存在则在后面家(n)的形式返回新的文件名，否则返回原始文件名 例如：已经存在文件名 log4j.htm
	 * 则返回log4j(1).htm
	 */
	public static String checkFileName(String fileName, String dir) {
		boolean isDirectory = new File(dir + fileName).isDirectory();
		if (FileUtil.isFileExist(fileName, dir)) {
			int index = fileName.lastIndexOf(".");
			StringBuffer newFileName = new StringBuffer();
			String name = isDirectory ? fileName : fileName.substring(0, index);
			String extendName = isDirectory ? "" : fileName.substring(index);
			int nameNum = 1;
			while (true) {
				newFileName.append(name).append("(").append(nameNum).append(")");
				if (!isDirectory) {
					newFileName.append(extendName);
				}
				if (FileUtil.isFileExist(newFileName.toString(), dir)) {
					nameNum++;
					newFileName = new StringBuffer();
					continue;
				}
				return newFileName.toString();
			}
		}
		return fileName;
	}

	public static boolean isFileExist(String fileName, String dir) {
		File files = new File(dir + fileName);
		return files.exists();
	}
	public static boolean isFileExist(String file) {
		File files = new File(file);
		return files.exists();
	}
	public static boolean isImageFile(File file){
		try {
			
			/*ImageInputStream iis = ImageIO.createImageInputStream(file);//resFile为需被Iterator&lt;
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {//文件不是图片
				System.out.println("此文件不为图片文件");
				return false;
			}else{
				return true;
			}*/
			BufferedImage bufferedImage = ImageIO.read(file);
//			BufferedImage bufferedImage = ImageIO.read(new File("E/个人文件夹/记录.txt"));
			if(bufferedImage == null){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
