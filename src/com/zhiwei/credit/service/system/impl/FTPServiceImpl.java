package com.zhiwei.credit.service.system.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.core.creditUtils.CompressUtil;
import com.zhiwei.credit.core.creditUtils.FileUtil;
import com.zhiwei.credit.model.system.SystemLog;
import com.zhiwei.credit.service.system.FTPService;
import com.zhiwei.credit.service.system.SystemLogService;
import com.zhiwei.credit.util.FtpUtils;

public class FTPServiceImpl implements FTPService {
	@Resource
	private SystemLogService systemLogService;
	
	private static Map configMap = AppUtil.getSysConfig();
	
	
	@Override
	public void FtpUp() {
		Date d=new Date();
		String inputFile=AppUtil.getAppAbsolutePath();
		String outPutFile=AppUtil.getZipOutPutPath()+DateUtil.dateToStr(d, "yyyy-MM-dd")+".zip";
		zipCompress(inputFile,outPutFile);
	}
	
	public  void zipCompress(String inputFile,String outPutFile){
		String description="";
		long startT=System.currentTimeMillis(); //记录开始前的时间
		try {
			CompressUtil.zip(inputFile, outPutFile);
			long endT=System.currentTimeMillis();//记录结束后的时间
			long totT=endT-startT;// 耗时
			System.out.println("压缩耗时："+totT+"ms。");
			description="压缩成功！压缩耗时："+totT+"ms ,生成路径："+outPutFile;
			//ftp 上传
			ftpUploadBak(outPutFile,description);
		} catch (Exception e) {
			e.printStackTrace();
			description="压缩失败！ 错误信息："+e.getMessage();
			FtpLog(description);
		}
	}
	
	public  void ftpUploadBak(String zipPath,String des){
		String description=des;
		String ip="";//ip
		String us="";//账号
		String ps="";//密码
		int port=21;//端口 默认为 21
		ip=AppUtil.getFtpIp();
		us=AppUtil.getFtpUsName();
		ps=AppUtil.getFtpPss();
		port=Integer.valueOf(AppUtil.getFtpPort());
		FtpUtils ftpUpload=new FtpUtils(); 
		long startT=System.currentTimeMillis(); //记录开始前的时间
		   /*   try { 
		       ftpUpload.connect(ip,port,us,ps); 
		       ftpUpload.uploadDirectory(zipPath,true,"zyht","zyht",ip,us,ps); 
		       long endT=System.currentTimeMillis();//记录结束后的时间
				long totT=endT-startT;// 耗时
				System.out.println("上传耗时："+totT+"ms。");
				description=description+";上传成功！ 上传耗时："+totT+"ms";
		       ftpUpload.close(); 
		      } catch (IOException e) {
		       e.printStackTrace(); 
		       description=description+";上传失败！ 失败信息："+e.getMessage();
		      }catch (Exception e) {
					e.printStackTrace();
					description=description+";上传失败！ 失败信息："+e.getMessage();
			}*/
		      //ftp 上传日志
		      FtpLog(description);  
	}
	
	private void FtpLog(String description){

		SystemLog sysLog = new SystemLog();

		sysLog.setClassName(this.getClass().getName());
		sysLog.setMethodName(this.getClass().getMethods()[0].toString());
		sysLog.setParams("");
		sysLog.setModelName("");//所属模块暂时不加
		sysLog.setIp("0.0.0.0");
		sysLog.setErr("");
		sysLog.setFlag("1");

		sysLog.setCreatetime(new Date());
		sysLog.setUserId(Long.valueOf(1));
		sysLog.setUsername("系统自动备份");
		sysLog.setExeOperation(description);
		sysLog.setOrgid(Long.valueOf(1));
		systemLogService.save(sysLog);
	
	}
	
	public  void ftpUploadFile(String zipPath,String des){
		String description=des;

////		String ip="127.0.0.1";//ip
////		String us="ftpuser";//帐号
////		String ps="123456";//密码
////		int port=21;//端口 默认为 21
//		String ip = configMap.get("ftpId").toString();
//		String us=configMap.get("ftpUser").toString();//帐号
//		String ps=configMap.get("ftpPs").toString();//密码
//		int port=Integer.valueOf(configMap.get("ftpPort").toString());//端口 默认为 21

		String ip="";//ip
		String us="";//账号
		String ps="";//密码
		int port=21;//端口 默认为 21
		ip=AppUtil.getFtpIp();
		us=AppUtil.getFtpUsName();
		ps=AppUtil.getFtpPss();
		port=Integer.valueOf(AppUtil.getFtpPort());
		FtpUtils ftpUpload=new FtpUtils(); 

		long startT=System.currentTimeMillis(); //记录开始前的时间
		     /* try { 
		       ftpUpload.connect(ip,port,us,ps); //连接ftp服务器
		       ftpUpload.uploadDirectory(zipPath,true,"zyht","zyht",ip,us,ps); 
		       FileUtil.mkDirectory(zipPath.substring(0,zipPath.lastIndexOf("/")));
		       long endT=System.currentTimeMillis();//记录结束后的时间
				long totT=endT-startT;// 耗时
				System.out.println("上传耗时："+totT+"ms。");
				description=description+";上传成功！ 上传耗时："+totT+"ms";
		       ftpUpload.close(); 
		      } catch (IOException e) {
		       e.printStackTrace(); 
		       description=description+";上传失败！ 失败信息："+e.getMessage();
		      }catch (Exception e) {
					e.printStackTrace();
					description=description+";上传失败！ 失败信息："+e.getMessage();
			}*/
		      //ftp 上传日志
		      FtpLog(description);  
	}
	 
	


	/**
	 * svn:songwj
	 * 1、上传文件所在的路径 
	 * 2、上传的服务器的位置
	 * 3、上传文件的名称
	 * 文件上传方法
	 */
	public void  FtpUploadFile(String srcPath,String fileToServerPath,String description,String dirStr){
		
		// 设置ftp服务器的参数信息
		String ip="127.0.0.1";//ftp的ip地址
		String us="ftpuser";//帐号
		String ps="123456";//密码
		int port=21;//端口 默认为 21
//		String ip = configMap.get("ftpIp").toString();
//		String us=configMap.get("ftpUser").toString();//帐号
//		String ps=configMap.get("ftpPs").toString();//密码
//		int port=Integer.valueOf(configMap.get("ftpPort").toString());//端口 默认为 21
		
		
		
		FtpUtils ftpUpload=new FtpUtils(); 
		long startT=System.currentTimeMillis(); //记录开始前的时间
		/*try {
			ftpUpload.connect(ip, port, us, ps);//连接ftp服务器
			ftpUpload.swjUploadDirectory(fileToServerPath,srcPath,true,"zyht","zyht",ip,us,ps,dirStr);  
			long endT=System.currentTimeMillis();//记录结束后的时间
			long totT=endT-startT;// 耗时
			System.out.println("上传耗时："+totT+"ms。");
			description=description+";上传成功！ 上传耗时："+totT+"ms";
	        ftpUpload.close(); 
			
		 } catch (IOException e) {
		       e.printStackTrace(); 
		       description=description+";上传失败！ 失败信息："+e.getMessage();
		      }catch (Exception e) {
					e.printStackTrace();
					description=description+";上传失败！ 失败信息："+e.getMessage();
			}*/
	}
	
//	String srcStr ="D:/新建文件夹/tomcat6/webapps/hurong_proj_yn_cyjr/attachFiles/webfile/p2p.202/201407/20140731180306806.png";
//	String disStr ="E:/aaa.png";
	/**
	 * Ftp文件下载
	 * svn：songwj
	 * @param srcStr 要下载的文件
	 * @param disStr 下载文件存放的路径
	 */
	public  void  ftpDownFile(String srcStr,String disStr){
		// 设置ftp服务器的参数信息
//		String ip="127.0.0.1";//ftp的ip地址
//		String us="ftpuser";//帐号
//		String ps="123456";//密码
//		int port=21;//端口 默认为 21
		
		String ip = configMap.get("ftpId").toString();
		String us=configMap.get("ftpUser").toString();//帐号
		String ps=configMap.get("ftpPs").toString();//密码
		int port=Integer.valueOf(configMap.get("ftpPort").toString());//端口 默认为 21
		
		FtpUtils ftpUpload=new FtpUtils(); 
		long startT=System.currentTimeMillis(); //记录开始前的时间
		
		/*try {
			ftpUpload.connect(ip, port, us, ps);
			ftpUpload.ftpDownFile(srcStr, disStr);
			long endT=System.currentTimeMillis();//记录结束后的时间
			long totT=endT-startT;// 耗时
			System.out.println("下载上传耗时："+totT+"ms。");
	         
		} catch (IOException e) {
			e.printStackTrace();
		}//连接ftp服务器
		ftpUpload.close();*/
	}
	
	/**
	 *从ftp上删除指定的文件
	 * @param fileName
	 */
	public void removeFile(String deleteFilePath){
		

		// 设置ftp服务器的参数信息
//		String ip="127.0.0.1";//ftp的ip地址
//		String us="ftpuser";//帐号
//		String ps="123456";//密码
//		int port=21;//端口 默认为 21
		String ip = configMap.get("ftpId").toString();
		String us=configMap.get("ftpUser").toString();//帐号
		String ps=configMap.get("ftpPs").toString();//密码
		int port=Integer.valueOf(configMap.get("ftpPort").toString());//端口 默认为 21
		FtpUtils ftpUpload=new FtpUtils(); 
		/*try {
			ftpUpload.connect(ip, port, us, ps);
			ftpUpload.removeFile(deleteFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}*///连接ftp服务器
	
		
	}
	
	
	
}
