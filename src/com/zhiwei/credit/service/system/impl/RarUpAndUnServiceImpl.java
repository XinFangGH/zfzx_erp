package com.zhiwei.credit.service.system.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.zhiwei.credit.service.system.update.RarUpAndUnService;

public class RarUpAndUnServiceImpl implements RarUpAndUnService {

	/**
	 * 上传压缩类型文件
	 */
	@Override
	public void updateload(String srcRar,String targetRar) {

		File  f =  new File(srcRar);
		InputStream in = null;
		try {
			in = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedInputStream  buff = new BufferedInputStream(in);
		 
		File  ff = new File(targetRar);
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(ff));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int size = 0; 
		byte[] b = new byte[4096*4096]; 
		try {
			while ((size = buff.read(b)) != -1) { 
				out.write(b, 0, size); 
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	/** 
    * 根据原始rar路径，解压到指定文件夹下.      
    * @param srcRarPath 原始rar路径 
    * @param dstDirectoryPath 解压到的文件夹      
    */
	@Override
	  public void   unRarFile(String srcRarPath,String dstDirectoryPath) {
//	    	String  srcRarPath ="E://classes.rar";
//	    	String dstDirectoryPath="D://";
	        if (!srcRarPath.toLowerCase().endsWith(".rar")) {
	            System.out.println("非rar文件！");
	            return;
	        }
	        File dstDiretory = new File(dstDirectoryPath);
	        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
	            dstDiretory.mkdirs();
	        }
	        Archive a = null;
	        try {
	            a = new Archive(new File(srcRarPath));
	            if (a != null) {
	                a.getMainHeader().print(); // 打印文件信息.
	                FileHeader fh = a.nextFileHeader();
	                while (fh != null) {
	                    if (fh.isDirectory()) { // 文件夹 
	                        File fol = new File(dstDirectoryPath + File.separator
	                                + fh.getFileNameString());
	                        fol.mkdirs();
	                    } else { // 文件
	                        File out = new File(dstDirectoryPath + File.separator
	                                + fh.getFileNameString().trim());
	                        //System.out.println(out.getAbsolutePath());
	                        try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压. 
	                            if (!out.exists()) {
	                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录. 
	                                    out.getParentFile().mkdirs();
	                                }
	                                out.createNewFile();
	                            }
	                            FileOutputStream os = new FileOutputStream(out);
	                            a.extractFile(fh, os);
	                            os.close();
	                        } catch (Exception ex) {
	                            ex.printStackTrace();
	                        }
	                    }
	                    fh = a.nextFileHeader();
	                }
	                a.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
