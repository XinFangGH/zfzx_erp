package com.zhiwei.core.util;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadURLFile {

		/** 
	     * 执行下载 
	     * @param rfPosit 远程文件开始下载位置 
	     */  
	    public static void start(long rfPosit,URL remoteFile,File storeFile){  
	        HttpURLConnection httpConn = null;  
	        InputStream httpIn = null;  
	        try{  
	            System.out.println("打开HttpURLConnection.");           
	            httpConn = (HttpURLConnection)remoteFile.openConnection();  
	            httpConn.setRequestProperty("User-Agent","NetFox");   
	            httpConn.setRequestProperty("RANGE","bytes="+rfPosit+"-");  
	              
	            System.out.println("得到HttpInputStream.");  
	            httpIn = httpConn.getInputStream();  
	            writeFile(httpIn,storeFile);  
	              
	            System.out.println("关闭HttpURLConnection.");  
	            httpConn.disconnect();  
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	    }  
	      
	    /** 
	     * 从HttpInputStream中读数据并写到本地文件中 
	     * @param in HttpInputStream 
	     */  
	    private static void writeFile(InputStream in,File storeFile){  
	        RandomAccessFile fileOut = null;  
	        int buffer_len = 512;  
	        byte[] buffer = new byte[buffer_len];  
	        int readLen = 0;  
	          
	        try{  
	            System.out.println("写本地文件.");  
	              
	            fileOut = new RandomAccessFile(storeFile, "rw");  
	            fileOut.seek(fileOut.length());  
	              
	            while(-1 != (readLen = in.read(buffer, 0, buffer_len))){  
	                fileOut.write(buffer, 0, readLen);  
	            }     
	              
	            fileOut.close();  
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	    }  
}


