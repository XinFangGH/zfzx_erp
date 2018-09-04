package com.zhiwei.credit.service.system;

public interface FTPService {
	
	public void FtpUp();

	public void ftpUploadFile(String zipPath, String des);
	
	/**
	 *ftp文件上传
	 *svn：songwj 
	 * @param srcPath  要上传文件的路径
	 * @param FileToServerPath  上传到服务器的路径
	 * @param description    文件名称
	 */
	public void FtpUploadFile(String srcPath,String fileToServerPath,String description,String dirStr);
	
	
	/**
	 * Ftp文件下载
	 * svn：songwj
	 * @param srcStr 要下载的文件路径
	 * @param disStr 下载文件存放的位置
	 */
	public  void  ftpDownFile(String srcStr,String disStr);
	
	//Ftp文件删除
	public void removeFile(String deleteFilePath);

}
