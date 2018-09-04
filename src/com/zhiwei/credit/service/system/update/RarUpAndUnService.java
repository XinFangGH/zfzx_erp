package com.zhiwei.credit.service.system.update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface RarUpAndUnService {
 
	
	public void updateload(String srcRar,String targetRar);//上传压缩文件
	
	public  void   unRarFile(String srcRarPath,String dstDirectoryPath) ;//解压压缩包到指定的位置

}
