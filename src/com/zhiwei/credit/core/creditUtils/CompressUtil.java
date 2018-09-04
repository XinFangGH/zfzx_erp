package com.zhiwei.credit.core.creditUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.zhiwei.credit.core.commons.CreditException;

/***
 * 压缩/解压缩工具
 * @author Jiang Wanyu
 *
 */
public class CompressUtil {
	
	 /** 
     *  
     * @param inputFileName 输入一个文件夹 
     * @param zipFileName   输出一个压缩文件夹，打包后文件名字 
     * 
     *  String inputFileName = "D:\\temp\\test";    //你要压缩的文件夹  
     *  String zipFileName = "D:\\temp\\test.zip";  //压缩后的zip文件
     *  
     *  压缩后会覆盖掉原来的压缩文件
     *    
     * @throws Exception 
     */  
    public static void zip(String inputFileName, String zipFileName) throws Exception {  
        zip(zipFileName, new File(inputFileName));
    }  
    
    /**
     * 
     * @param zipFileName
     * @param inputFile
     * @throws Exception
     */
    public static void zip(String zipFileName, File inputFile) throws Exception {  
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));  
        zip(out, inputFile, "");  
        out.close();  
    }  
    
    
    /** 
     * 解压zip格式的压缩文件到指定位置 
     * @param zipFileName 压缩文件 
     * @param extPlace 解压目录 
     * 
     * @author Jiang Wanyu
     */  
    public static void unzip(String zipFileName, String extPlace) throws Exception {  
        
        File f = new File(zipFileName);
        ZipFile zipFile = new ZipFile(zipFileName);
        
        if((!f.exists()) && (f.length() <= 0)) {
            throw new CreditException("要解压的文件不存在!");
        }
        
        String strPath, gbkPath, strtemp;
        File tempFile = new File(extPlace);
        
        strPath = tempFile.getAbsolutePath();
        Enumeration e = zipFile.getEntries();
        
        while(e.hasMoreElements()){
        	
            ZipEntry zipEnt = (ZipEntry)e.nextElement();
            gbkPath=zipEnt.getName();
            
            if(zipEnt.isDirectory()){
                strtemp = strPath + File.separator + gbkPath;
                File dir = new File(strtemp);
                dir.mkdirs();
                continue;
            } else {
                //读写文件
                InputStream is = zipFile.getInputStream(zipEnt);
                BufferedInputStream bis = new BufferedInputStream(is);
                gbkPath=zipEnt.getName();
                strtemp = strPath + File.separator + gbkPath;
                
                //建目录  
                String strsubdir = gbkPath;
                for(int i = 0; i < strsubdir.length(); i++) {
                    if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                        String temp = strPath + File.separator + strsubdir.substring(0, i);
                        File subdir = new File(temp);
                        if(!subdir.exists())
                        subdir.mkdir();
                    }
                }
                FileOutputStream fos = new FileOutputStream(strtemp);
                BufferedOutputStream bos = new BufferedOutputStream(fos);//默认覆盖原有文件
                
                int c;
                while((c = bis.read()) != -1) {
                    bos.write((byte) c);
                }
                
                bos.close();  
                fos.close();
            }  
        }  
    }  
    
    
    /**private Method*/
    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
    	 out.setEncoding("gbk");//设置编码,解决中文乱码的问题  
        if (f.isDirectory()) {  //判断是否为目录  
            File[] fl = f.listFiles(); 
           
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));  
            base = base.length() == 0 ? "" : base + "/";  
            for (int i = 0; i < fl.length; i++) {  
                zip(out, fl[i], base + fl[i].getName());  
            }  
        } else {                //压缩目录中的所有文件  
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));  
            FileInputStream in = new FileInputStream(f);  
            int b;  
            while ((b = in.read()) != -1) {  
                out.write(b);  
            }  
            in.close();  
        }  
    }
	
    
}
