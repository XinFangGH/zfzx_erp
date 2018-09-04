package com.zhiwei.credit.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class FileHelper {

	static File file ;
	
	/**
	 * @删除文件
	 * input 输入 output 输出
	 * */
	public static boolean deleteFile(String url){
		
		boolean flag = false;
		
		try{
			if(null == file){
				
				file = new File(url);
				
				if(file.isFile()){
					
					if(file.canWrite()){
						
						file.delete();
					}
					
					flag = true;
					
				}
			}else{
				
				if(url == file.getPath()){
					
					file.delete();
					
					flag = true;
				}
			}
			
		}catch(Exception e){
			
			flag = false ;
			
			e.printStackTrace();
		}finally{
			
			file = null;
			
			System.gc();
		}
		return flag;
	} 
	
	/** 
     * 新建目录 
     * @param folderPath String 如 c:/fqf 
     * @return boolean 
     */ 
   public static void newFolder(String folderPath) { 
       try { 
           String filePath = folderPath; 
           filePath = filePath.toString(); 
           java.io.File myFilePath = new java.io.File(filePath); 
           if (!myFilePath.exists()) { 
               myFilePath.mkdir(); 
           } 
       } 
       catch (Exception e) { 
           e.printStackTrace(); 
       } 
   } 
	
	
	/** 
     * 复制单个文件 
     * @param oldPath String 原文件路径 如：c:/fqf.txt 
     * @param newPath String 复制后路径 如：f:/fqf.txt 
     * @return boolean 
     */ 
   public static void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //文件存在时 
               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //字节数 文件大小 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
               fs.close();
           } 
       } 
       catch (Exception e) { 
           e.printStackTrace(); 
       } 
   } 

   public static boolean fileUpload(File upFile , File targetFile , byte[] b){
		InputStream is = null ;
		OutputStream os = null ;
		try{
			is =  new FileInputStream(upFile);
			os= new FileOutputStream(targetFile);
			int length = 0;   
	        while((length = is.read(b))>0){   
	        	os.write(b, 0, length);   
	        } 
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(null != is){
					is.close();   
				}if(null != os){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}   
		}
		return true;
	}
   
   public static boolean deleteFile(File f){
	   if (f.isFile())
           f.delete();
	   return true;
   }

   
   public static boolean deleteDir(File f){ 
	   if(f.isDirectory()){
		   File[] files = f.listFiles();
		   for(int i=0;i<files.length;i++){
			   if(files[i].isDirectory()) deleteDir(files[i]);
			   else deleteFile(files[i]);
		   }
	   }
	   f.delete();
	   return true ;
   }
   
   
   
   public static void downLoadFile(String filePath,String fileType ,String downName,HttpServletResponse response){
		OutputStream out = null;// 创建一个输出流对象
		String headerStr = downName;
		try{
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			InputStream is=new FileInputStream(filePath);
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="+ headerStr+"."+fileType);// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			// 输入流 和 输出流
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				 out.write(b, 0, len);
			}
			is.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
   }
   
   
   
   
}


