package com.zhiwei.core.web.servlet;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.FileUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.FileAttachService;
import com.zhiwei.credit.service.system.GlobalTypeService;

/**
 * 文件上传类
 * @author 智维软件
 *
 */
public class FileUploadServlet extends HttpServlet{
 
	private static final long serialVersionUID = 1L;

	private Log logger=LogFactory.getLog(FileUploadServlet.class);
	
	@SuppressWarnings("unused")
	private ServletConfig servletConfig=null;
	
	private FileAttachService fileAttachService=(FileAttachService)AppUtil.getBean("fileAttachService");

	private AppUserService appUserService = (AppUserService)AppUtil.getBean("appUserService");
	
	private GlobalTypeService globalTypeService = (GlobalTypeService)AppUtil.getBean("globalTypeService");
	
	private String uploadPath=""; // 上传文件的目录   
	private String tempPath=""; // 临时文件目录 
	
	private String fileCat="others";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String flexUserId = req.getParameter("flexUserId"); // flex中获取登录用户的id
		String fileTypeId = req.getParameter("fileTypeId"); // 附件类型id
		GlobalType globalType = null;
		if(StringUtils.isNotEmpty(fileTypeId)){
			globalType = globalTypeService.get(new Long(fileTypeId));
		}
		//指定保存至某个目录,若提交时，指定了该参数值，则表示保存的操作　
		String filePath="";
		String fileId="";
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 缓存大小
			factory.setSizeThreshold(4096);
			factory.setRepository(new File(tempPath));
			ServletFileUpload fu=new ServletFileUpload(factory);

	        @SuppressWarnings("unchecked")
			List<FileItem> fileItems = fu.parseRequest(req); 
	        //取得相关参数值
	        for(FileItem fi:fileItems){
	        	if("file_cat".equals(fi.getFieldName())){
	        		fileCat=fi.getString();
	        		//break;
	        	}
	        	if("file_path".equals(fi.getFieldName())){
	        		filePath=fi.getString();
	        	}
	        	if("fileId".equals(fi.getFieldName())){
	        		fileId=fi.getString();
	        	}
	        }
	        logger.info("fileId:" + fileId);
	        Iterator<FileItem> i = fileItems.iterator();
	        //目前处理每次只上传一个文件
	        while(i.hasNext()) {
	            
	        	FileItem fi = (FileItem)i.next();
	        
	            if(fi.getContentType()==null){
	            	continue;
	            }
	            
	            //返回文件名及路径及fileId.
	            String path = fi.getName();
	            
		        int start=path.lastIndexOf("\\");
		        
		        //原文件名
		        String fileName=path.substring(start+1);
		        //判断上传的文件类型  
		        fileName = StringUtil.checkExtName(fileName);
		        boolean extType = StringUtil.checkExtType(fileName);
		        if(extType){
		        	String relativeFullPath=null;
			        
			        if(!"".equals(filePath)){
			        	relativeFullPath=filePath;
			        }else if(!"".equals(fileId)){
			          FileAttach fileAttach=fileAttachService.get(new Long(fileId));
			          relativeFullPath=fileAttach.getFilePath();
			          logger.info("exist filePath:" + relativeFullPath);
			        }else{
			        	relativeFullPath=fileCat+"/" + FileUtil.generateFilename(fileName);
			        }
			        
			        int index=relativeFullPath.lastIndexOf("/");

			        File dirPath=new File(uploadPath+"/" + relativeFullPath.substring(0,index+1));
			        
			        if(!dirPath.exists()){
			        	dirPath.mkdirs();
			        }

			        fi.write(new File(uploadPath+"/" + relativeFullPath));
			        FileAttach file=null;
			        
			        if(!"".equals(filePath)){
			        	file=fileAttachService.getByPath(filePath);
			        	file.setTotalBytes(fi.getSize());
			        	file.setNote(getStrFileSize(fi.getSize()));
			        	fileAttachService.save(file);
			        }
			        if(!"".equals(fileId)){
			        	file=fileAttachService.get(new Long(fileId));
			        	file.setTotalBytes(fi.getSize());
			        	file.setNote(getStrFileSize(fi.getSize()));
			        	fileAttachService.save(file);
			        }
			        if(file==null) {
			        	file=new FileAttach();
				        file.setCreatetime(new Date());
				        AppUser curUser = ContextUtil.getCurrentUser();
				        if(StringUtils.isNotEmpty("isFlex") && StringUtils.isNotEmpty(flexUserId)){
				        	curUser = appUserService.get(new Long(flexUserId));
				        }
				        if(curUser != null){
				        	file.setCreator(curUser.getFullname());
				        	file.setCreatorId(curUser.getUserId());
				        } else {
				        	file.setCreator("UNKown");
				        }
				        int dotIndex=fileName.lastIndexOf(".");
				        file.setExt(fileName.substring(dotIndex+1));
				        file.setFileName(fileName);
				        file.setFilePath(relativeFullPath);
				        file.setFileType(fileCat);
				        if(globalType == null){
				        	globalType = globalTypeService.findByFileType(fileCat);
				        }  
				        if(globalType != null){			        	
				        	file.setGlobalType(globalType);
				        }
				        file.setTotalBytes(fi.getSize());
				        file.setNote(getStrFileSize(fi.getSize()));
				        file.setCreatorId(curUser.getUserId());
				        file.setDelFlag(FileAttach.FLAG_NOT_DEL);
				        fileAttachService.save(file);
			        }
			        StringBuffer sb = new StringBuffer("");
			        String isFlex = req.getParameter("isFlex"); // 判断是否为flex文件上传操作
			        if(StringUtils.isNotEmpty(isFlex) && isFlex.equalsIgnoreCase("true")){
			        	 sb = new StringBuffer("{\"success\":\"true\"");
					     sb.append(",\"fileId\":").append(file.getFileId())
					     .append(",\"fileName\":\"").append(file.getFileName())
					     .append("\",\"filePath\":\"").append(file.getFilePath())
					     .append("\",\"message\":\"upload file success.("+ fi.getSize()+" bytes)\"");
					     sb.append("}");
			        } else {
				        sb = new StringBuffer("{success:true");
				        sb.append(",fileId:").append(file.getFileId())
				        .append(",fileName:'").append(file.getFileName())
				        .append("',filePath:'").append(file.getFilePath()).append("',message:'upload file success.("+ fi.getSize()+" bytes)'");
				        sb.append("}");
			        }
			        resp.setContentType("text/html;charset=UTF-8");
					PrintWriter writer = resp.getWriter();
					
					writer.println(sb.toString());
		        }else{
		        	resp.getWriter().write("{'success':false,'message':'上传文件类型错误,请重新选择文件'}");
		        }
	        }
	        
	    }    
	    catch(Exception e) {    
	    	resp.getWriter().write("{'success':false,'message':'error..."+e.getMessage()+"'}");
	    }    
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.servletConfig=config;
	}
	
	public void init() throws ServletException {
	   
		//初始化上传的路径及临时文件路径
		
		uploadPath=getServletContext().getRealPath("/attachFiles/");
		
	    File uploadPathFile=new File(uploadPath);
	    if(!uploadPathFile.exists()){
	    	uploadPathFile.mkdirs();
	    }
	    tempPath=uploadPath+"/temp";
	    
	    File tempPathFile=new File(tempPath); 
	    if(!tempPathFile.exists()){
	    	tempPathFile.mkdirs();
	    }
	}
	
	
	/*------------------------------------------------------------
	保存文档到服务器磁盘，返回值true，保存成功，返回值为false时，保存失败。
	--------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public boolean saveFileToDisk(String officefileNameDisk)
	{
		File officeFileUpload = null;
		FileItem officeFileItem =null ;
		
		boolean result=true ;
		try
		{
			if(!"".equalsIgnoreCase(officefileNameDisk)&&officeFileItem!=null)
			{
				officeFileUpload =  new File(uploadPath+officefileNameDisk);
				officeFileItem.write(officeFileUpload);
			}
		}catch(FileNotFoundException e){
			
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		}
		return result;	
	}
	
	private String getStrFileSize(double size){
	    DecimalFormat df=new DecimalFormat("0.00");
		if(size>1024*1024){
			 double ss=size/(1024*1024);
		 	 return df.format(ss)+" M";
		}else if(size>1024){
			double ss=size/1024;
			return df.format(ss)+" KB";
		}else{
			return size+" bytes";
		}
    }

	
}
