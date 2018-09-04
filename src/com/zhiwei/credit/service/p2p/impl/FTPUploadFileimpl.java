package com.zhiwei.credit.service.p2p.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;



import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.FileUtil;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.util.FtpUtils;

/**
 * ftp文件上传的相关方法
 * @author songwenjie
 *
 */
public class FTPUploadFileimpl  implements FTPIoadFileService {
	
	@Resource
	private FileFormService  fileFormService;
	
	
	String dirStr;//创建文件夹需要的字段
	
	String filename;//文件名称
	
	String filepath;//路径
	
	String webPath;//网络路径
	
	String mark ;//mark字段
	
     /***************************FTP文件上传方法********************************/

	//需要传入的参数：myUpload appointFileSetFolder tablename selectId extendname setname creatorid
	//ftp上传
	
	/**
	 * ftp上传
	 * @param myUpload  
	 * @param appointFileSetFolder
	 * @param tablename
	 * @param selectId
	 * @param extendname
	 * @param setname
	 * @param creatorid  
	 * @return 
	 */
	public Map<String,String> ftpUploadFile(File myUpload,String appointFileSetFolder,String tablenameFirst,String tablenameTwo,String selectId,String extendname,String setname,Integer creatorid){
		//声明map存放需要返回的值
		Map<String,String> map = new HashMap<String,String>();
	
		//判断文件上传的类型
		extendname = StringUtil.checkExtName(extendname);
		 boolean extType = StringUtil.checkExtType(extendname);
		if(extType){
			//指定上传文件所在的位置
			 filepath = "";
			webPath = joinServerPath(myUpload,appointFileSetFolder,tablenameFirst,tablenameTwo,selectId,extendname);
			System.out.println(webPath);
			//需要创建文件夹的路径
			dirStr ="";
			dirStr = createDirStr(appointFileSetFolder,tablenameFirst,tablenameTwo,selectId,4);
			System.out.println(dirStr);
			FtpUploadFile(myUpload.getAbsolutePath(),filepath, filename,dirStr);//保存方法
			//获取需要保存的mark值，方便进行查询
			mark = "";
			mark = createDirStr(appointFileSetFolder,tablenameFirst,tablenameTwo,selectId,3);

			FileForm fileFrom =	saveCsFile(myUpload,filename,extendname,setname,filepath,creatorid,mark,webPath);
			
			map.put("webpath", fileFrom.getWebPath());
			map.put("filename", fileFrom.getFilename());
			map.put("extendname",fileFrom.getExtendname());
			map.put("tablename", tablenameFirst);
			map.put("tableId", selectId);
			map.put("filedId", fileFrom.getFileid().toString());//文件保存值
			if(creatorid!=null){
				map.put("creatorid", creatorid.toString());
			}
			map.put("mark", mark);
		}
		
		
	  return  map;
	}

//新增图片上传方法
	public Map<String,String> ftpUploadFile1(File file, String webPath, String filepath, String dir,String extendname,String guid){
		//声明map存放需要返回的值
		Map<String,String> map = new HashMap<String,String>();

		//判断文件上传的类型
		extendname = StringUtil.checkExtName(extendname);
		filename = guid+extendname;
		boolean extType = StringUtil.checkExtType(extendname);
		if(extType){
			//指定上传文件所在的位置
			//filepath = webPath;
			//webPath = joinServerPath(myUpload,appointFileSetFolder,extendname);
			//System.out.println(webPath);
			//需要创建文件夹的路径
			dirStr = dir;
			//dirStr = createDirStr(appointFileSetFolder,tablenameFirst,tablenameTwo,selectId,4);
			System.out.println(dirStr);
			FtpUploadFile(file.getAbsolutePath(),filepath, filename,dirStr);//保存方法

		}

		return  map;
	}

	//拼接上传后文件存放的位置
	/**
	 * myUpload：要上传的文件
	 * appointFileSetFolder：存放文件类型
	 * tableName：要进行文件上传的表明
	 * selectId：要上传文件信息的主键值
	 * 9 webfile/业务逻辑/业务表/文件  文件夹的层级
	 */
	public String joinServerPath(File myUpload,String appointFileSetFolder,String tablenameFirst,String tablenameTwo,String selectId,String extendname){
		String fileToServerPath ="";
		
//		path = ""; 
	 
		if(myUpload != null ){
			//存放位置：attachFiles+指定类别文件夹+表明+主键值
			filepath+="attachFiles";
			filepath += "\\";
			filepath += createDirStr(appointFileSetFolder,tablenameFirst,tablenameTwo,selectId,1);
			String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());//得到当前时间，精确到毫秒
		
			filepath += nowTime;//拼接保存文件名称
			filepath +=extendname;//拼接上传文件的后缀名
			fileToServerPath += "attachFiles/";
			fileToServerPath += createDirStr(appointFileSetFolder,tablenameFirst,tablenameTwo,selectId,2);
			fileToServerPath += nowTime;
			fileToServerPath +=extendname;
			filename =nowTime+extendname;//拼接文件名保存数据时使用
		}
		return fileToServerPath;
	}
	
	/**
	 * 拼接文件存放的路径，创建文件夹使用
	 * pathtype 为1 返回的是webpath；2返回filepath；3返回的mark
	 * @return
	 */
	public  String createDirStr(String appointFileSetFolder,String tablenameFirst,String tablenameTwo,String selectId,Integer pathType){
		
		//拼接文件路径
		//包含两层关系 例如：贷款项目-->贷款材料
		//对应关系:贷款材料-->tablenameFirst   贷款项目-->tablenameTwo
		String dirStr ="";
		if(pathType == 1){//获得webpath
			if(appointFileSetFolder  != null && !appointFileSetFolder.equals("")){
				dirStr += appointFileSetFolder;//拼接表明
				dirStr += "\\";
			}
			if(tablenameFirst  != null && !tablenameFirst.equals("") ){
				dirStr += tablenameFirst;//拼接表明
				if(tablenameTwo != null && !tablenameTwo.equals("")){//如果存在两层关系
					dirStr += "\\";
					dirStr += tablenameTwo;//拼接表明
				}
				if(selectId!=null && !"".equals(selectId)){
					dirStr += "\\"+selectId;//拼接主键值
					
				}
				dirStr += "\\";
				
			}
		}
		
		if(pathType == 2){//获得文件路径
			if(appointFileSetFolder  != null && !appointFileSetFolder.equals("")){
				dirStr += appointFileSetFolder;//拼接表明
				dirStr += "/";
			}
			if(tablenameFirst  != null && !tablenameFirst.equals("") ){
				dirStr += tablenameFirst;//拼接表明
				if(tablenameTwo != null && !tablenameTwo.equals("")){
					dirStr += "/";
					dirStr += tablenameTwo;//拼接表明
				}
				if(selectId!=null && !selectId.equals("")){
					dirStr += "/"+selectId;//拼接主键值
				
				}
				dirStr += "/";
			}
		}
		
		if(pathType == 3){//获得的mark值，（表明.主键） 的形式
			 if(tablenameFirst  != null && !tablenameFirst.equals("") ){
				 if(tablenameTwo  != null && !tablenameTwo.equals("") ){
						 dirStr += tablenameTwo;//拼接表明
						 dirStr += "."+selectId;//拼接主键值
					 }else{
						dirStr += tablenameFirst;//拼接表明
						
						if(selectId!=null && !selectId.equals("")){
							dirStr += "."+selectId;//拼接主键值
						}
					 }
			 }
		}
		
		if(pathType == 4){//获得的mark值，获得服务器上创建文件夹的路径
			if(appointFileSetFolder  != null && !appointFileSetFolder.equals("")){
//				dirStr +="attachFiles";
//				dirStr += "/";
				dirStr += appointFileSetFolder;//拼接表明
				dirStr += "/";
			} 
			 if(tablenameFirst  != null && !tablenameFirst.equals("") ){
					dirStr += tablenameFirst;//拼接表明
					if(tablenameTwo!=null  && !tablenameTwo.equals("")){
						dirStr += "/";
						dirStr += tablenameTwo;//拼接表明
					} 
					if(selectId!=null && !selectId.equals("")){
						dirStr += "/"+selectId;//拼接主键值
					}
			 }
		}
		
		return dirStr;
	}
	
	@SuppressWarnings("unchecked")
	private static Map configMap = AppUtil.getSysConfig();

	/**
	 * svn:songwj
	 * 1、上传文件所在的路径 
	 * 2、上传的服务器的位置 
	 * 3、上传文件的名称
	 * 文件上传方法
	 */
	public void  FtpUploadFile(String srcPath, String fileToServerPath, String description, String dirStr){
	
		System.out.println("******************进入上传方法FtpUploadFile内*********************************");
		//读取配置文件获取ftp的ip 账号  密码  端口号
		String ip = AppUtil.getFtpIp(); 
		String us=AppUtil.getFtpUsName();//帐号
		String ps=AppUtil.getFtpPss();//密码
		System.out.println(ip);
		System.out.println(us);
		System.out.println(ps);
		int port=Integer.valueOf(AppUtil.getFtpPort());//端口 默认为 21
		
		FtpUtils ftpUpload=new FtpUtils(); 
		long startT=System.currentTimeMillis(); //记录开始前的时间
		
		try {
			//dirStr="/home/test/";
			ftpUpload.connect(ip, port, us, ps,dirStr);//连接ftp服务器
			ftpUpload.upload(description,srcPath);  
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
			}
	}

	//保存上传文件记录信息,cs_file
	public  FileForm  saveCsFile(File  myUpload,String filename,String extendname ,String setname,String filePath,Integer creatorid,String mark,String webPath){
		AppUser user = ContextUtil.getCurrentUser();
		if(user!=null){
			creatorid = Integer.valueOf(user.getId());
		}
		FileForm fileinfo = new FileForm();
		FileForm aa = new FileForm();
		fileinfo.setFilename(filename);//保存文件名
		fileinfo.setSetname(setname);//数据库中setname字段
		fileinfo.setFilepath(filepath);//数据库中filepath字段
		fileinfo.setExtendname(extendname);//文件后缀名
		Long sl=myUpload.length();
		fileinfo.setFilesize(sl.intValue());//文件的大小
		fileinfo.setCreatorid(creatorid);//文件创建着
		fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());//文件创建时间
		fileinfo.setContentType("application/octet-stream");
		fileinfo.setMark(mark);
		fileinfo.setWebPath(webPath);
		aa = fileFormService.save(fileinfo);
		return aa;
	}
	
	/********************************获取webpath***************************************/
	
	/**
	 * 获取webpath路径
	 * tableAndId：表名和主键值
	 */
	public String getWebpath(String tableAndId){
		String webpaths= "";
		List<FileForm>	list = fileFormService.listByMark(tableAndId);
		if(list != null){
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					list.get(i).getMark();
					webpaths += list.get(i).getWebPath()+"&";
				}
				webpaths =	webpaths.substring(0, webpaths.length() -1);
			}
		}
    	return webpaths;
	}
	
	/********************************删除服务器上指定的文件***********************************/
	
	/**
	 *从ftp上删除指定的文件
	 * @param fileName
	 */
	public String  removeFile(String deleteFilePath){
		
		String  str ="";
		
		//从配置文件中获取ftp连接的ip地址、账号、密码、端口号
		String ip = AppUtil.getFtpIp();
		String us=AppUtil.getFtpUsName()  ;//帐号
		String ps= AppUtil.getFtpPss() ;//密码
		int port=  Integer.valueOf(AppUtil.getFtpPort());//端口 默认为 21
		
		/*FtpUtils ftpUpload=new FtpUtils(); 
		try {
			ftpUpload.connect(ip, port, us, ps);
			System.out.println("deleteFilePath------------"+deleteFilePath);
			str  = ftpUpload.removeFile(deleteFilePath);
			
		} catch (IOException e) {
			e.printStackTrace();
		}*///连接ftp服务器
		return str;
	}
	
	/**************************FTP文件下载********************************/
	
	/**
	 * 文件下载
	 * @param srcStr 要下载的文件路径
	 * @param disStr 下载文件存放的位置
	 * svn：songwj
	 */
	public  void  ftpDownFile(String srcStr,String disStr,String filename){
		
		
		// 读取配置文件读取ftp ip地址 端口号  账户  密码
		String ip = AppUtil.getFtpIp();
		String us = AppUtil.getFtpUsName();//帐号
		String ps = AppUtil.getFtpPss();//密码
		int port = Integer.valueOf(AppUtil.getFtpPort());//端口 默认为 21
		
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
		}*/
		//连接ftp服务器
//		ftpUpload.close();
	}
	
	//上传文件
	public String upLoadFile(String srcPath,String fileToServerPath){
		
		String  ifSuccess ="";
		if(fileToServerPath!=null && !"".equals(fileToServerPath)){
		
			if(fileToServerPath.indexOf("\\" )!= -1){
				fileToServerPath=fileToServerPath.replace("\\", "/");
			}  
			String dir = fileToServerPath;
			fileToServerPath = AppUtil.getConfigMap().get("fileURL")+"/"+fileToServerPath;
			String dirStr1 ="";
			
			if(dir.indexOf("\\") != -1){
				String[] arr = dir.split("\\");
				if(arr.length > 0){
					 for(int i=1;i<arr.length-1;i++){
						 dirStr1+=arr[i]+"/";
					 }
				}
				dirStr1 = dirStr1.substring(0, dirStr1.length()-1);
			}else if(dir.indexOf("/") != -1){
				String[] arr = dir.split("\\/");
				if(arr.length > 0){
					 for(int i=1;i<arr.length-1;i++){
						 dirStr1+=arr[i]+"/";
					 }
				}
				dirStr1 = dirStr1.substring(0, dirStr1.length()-1);
			}
		 
			//读取配置文件获取ftp的ip 账号  密码  端口号
			String ip = AppUtil.getFtpIp();
			String us=AppUtil.getFtpUsName();//帐号
			String ps=AppUtil.getFtpPss();//密码
			int port=Integer.valueOf(AppUtil.getFtpPort());//端口 默认为 21
			
			FtpUtils ftpUpload=new FtpUtils(); 
			long startT=System.currentTimeMillis(); //记录开始前的时间
			try {
				ftpUpload.connect(ip, port, us, ps,dirStr1);//连接ftp服务器
				ftpUpload.upload(null,srcPath);  
				long endT=System.currentTimeMillis();//记录结束后的时间
				long totT=endT-startT;// 耗时
				System.out.println("上传耗时："+totT+"ms。");
//				description=description+";上传成功！ 上传耗时："+totT+"ms";
		        ftpUpload.close(); 
		        ifSuccess ="true";
			 } catch (IOException e) {
			       e.printStackTrace(); 
	//		       description=description+";上传失败！ 失败信息："+e.getMessage();
			       ifSuccess ="false";
		     }catch (Exception e) {  
					e.printStackTrace();
	//					description=description+";上传失败！ 失败信息："+e.getMessage();
					 ifSuccess ="false";
		     }
		}
	     return ifSuccess;
	}

	public void ftpDownFile1(String srcStr, String disStr) {
		// 读取配置文件读取ftp ip地址 端口号  账户  密码
		String ip = AppUtil.getFtpIp();
		String us = AppUtil.getFtpUsName();//帐号
		String ps = AppUtil.getFtpPss();//密码
		int port = Integer.valueOf(AppUtil.getFtpPort());//端口 默认为 21
		
		FtpUtils ftpUpload=new FtpUtils(); 
		long startT=System.currentTimeMillis(); //记录开始前的时间
		
		/*try {
			ftpUpload.connect(ip, port, us, ps);
//			ftpUpload.ftpDownFile(srcStr, disStr);
			long endT=System.currentTimeMillis();//记录结束后的时间
			long totT=endT-startT;// 耗时
			System.out.println("下载上传耗时："+totT+"ms。");
	         
		} catch (IOException e) {
			e.printStackTrace();
		}//连接ftp服务器
		ftpUpload.close();*/
		
	}

	@Override
	public List<FileForm> getWebpathList(String tableAndId) {
		List<FileForm>	list  = null;
		list = fileFormService.listByMark(tableAndId);
    	return list;
	}
	
	
}
