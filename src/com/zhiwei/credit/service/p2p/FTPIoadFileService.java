package com.zhiwei.credit.service.p2p;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;

public interface FTPIoadFileService {

	/**
	 * ftp上传
	 * @param myUpload 上传的文件
	 * @param appointFileSetFolder 文件类型
	 * @param tablename 表明 
	 * @param selectId  上传文件信息的主键值  
	 * @param extendname 文件扩展名
	 * @param setname    上传图片的功能
	 * @param creatorid  上传操作者主键
	 * @return
	 */
	public Map<String, String> ftpUploadFile(File myUpload,String appointFileSetFolder,String tablenameFirst ,String tableNameTwo,String selectId,String extendname,String setname,Integer creatorid);

	
	/**
	 * 文件上传方法    
	 * @param srcPath  源文件路径
	 * @param serverPath   上传文件路径
	 * @param dirStr    
	 */
	public String upLoadFile(String srcPath,String serverPath);
	/**
	 * 查询服务器上文件的路径
	 * 返回的路径是一个，也可以是多个，多的路径用“&”拼接的
	 * @param tableAndId
	 * @return 
	 */
	public String getWebpath(String tableAndId) ;//获得文件在服务器上的路径
	
	/**
	 * 删除服务器上的文件
	 * @param deleteFilePath  图片的删除路径
	 */
	public String removeFile(String deleteFilePath);//删除服务器上的文件

	/**
	 * Ftp文件下载
	 * svn：songwj
	 * @param srcStr 要下载的文件路径
	 * @param disStr 下载文件存放的位置
	 */
	public  void  ftpDownFile(String srcStr,String disStr,String filename);
	public List<FileForm> getWebpathList(String tableAndId);

	//新增图片上传方法
	Map<String,String> ftpUploadFile1(File file, String webPath, String filepath, String dir, String extendname,String guid);


}
