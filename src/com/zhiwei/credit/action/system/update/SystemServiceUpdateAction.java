package com.zhiwei.credit.action.system.update;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.system.update.SystemServiceUpdate;
import com.zhiwei.credit.service.system.update.SystemServiceUpdateService;
import com.zhiwei.credit.service.system.update.RarUpAndUnService;
/**
 * 
 * @author 
 *
 */
public class SystemServiceUpdateAction extends BaseAction{
	@Resource
	private SystemServiceUpdateService systemServiceUpdateService;
	private SystemServiceUpdate systemServiceUpdate;
	@Resource
	private RarUpAndUnService rarUpAndUnService;
	
	private Long id;
	private File SystemServiceUpdateUpLoadWinmyUpload;//声明一个文件类型
	public String isPublication;//选择服务器的类型  1 p2p ； 0 erp
	public File SystemUploadWinfileUpload;
	public String filename;//文件名
	
	/**
	 * 更新文件到指定的位置
	 * @return
	 */
	public String tihuanFile(){
		if(id!=null &&!"".equals(id)){
			SystemServiceUpdate   sys  =	systemServiceUpdateService.get(id);
			if(sys!=null){
				if(sys.getFilepath()!=null && !"".equals(sys.getFilepath())){
					String filePath = sys.getFilepath();
					String srcPath = filePath.substring(0, filePath.lastIndexOf("."));
					String destPath =	AppUtil.getAppAbsolutePath();
					File srcFile = new File(srcPath);
					File destFile = new File(destPath);
					try {
						copyFolder(srcFile,destFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	private   void copyFolder(File src, File dest) throws IOException {  
	    if (src.isDirectory()) {  
	        if (!dest.exists()) {  
	            dest.mkdir();  
	        }  
	        String files[] = src.list();  
	        for (String file : files) {  
	            File srcFile = new File(src, file);  
	            File destFile = new File(dest, file);  
	            // 递归复制  
	            copyFolder(srcFile, destFile);  
	        }  
	    } else {  
//	    	rarUpAndUnService.updateload(src.getAbsolutePath(),dest.getAbsolutePath());
	        InputStream in = new FileInputStream(src);  
	        OutputStream out = new FileOutputStream(dest);  
	        byte[] buffer = new byte[1024];  
	        int length;  
	        while ((length = in.read(buffer)) > 0) {  
	            out.write(buffer, 0, length);  
	        }  
	        in.close();  
	        out.close();  
	    }  
	}  
	/**
	 * 上传文件
	 * @return
	 */
	public String upLoadFiles(){
		String fileName = filename.substring(filename.lastIndexOf("\\")+1, filename.length());
		String  upUrl = AppUtil.getAppAbsolutePath()+"attachFiles\\"+fileName;
		rarUpAndUnService.updateload(SystemUploadWinfileUpload.getAbsolutePath(),upUrl);
		SystemServiceUpdate as= new SystemServiceUpdate();
		as.setUpdatesize( SystemUploadWinfileUpload.length());//文件大小
		as.setUpdatetime(new Date());//上传
		as.setProjname(isPublication);//项目名称 erp或者p2p
		as.setIfsuccess(1);
	    as.setFilepath(upUrl);
		systemServiceUpdateService.save(as);
		jsonString="{success:true}";
		return SUCCESS;
	}
	/**
	 * 解压  
	 * @return
	 */
	public   String jieyaFile(){
		SystemServiceUpdate  systemUpdate =		systemServiceUpdateService.get(id);
		if(systemUpdate!=null){
			if(systemUpdate.getFilepath()!=null && !"".equals(systemUpdate.getFilepath())){
				String  filepath= systemUpdate.getFilepath();
				String fileJieya = filepath.substring(0, filepath.lastIndexOf("\\"));
				System.out.println(fileJieya);
				rarUpAndUnService.unRarFile(systemUpdate.getFilepath(), fileJieya);
			}
			
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SystemServiceUpdate> list= systemServiceUpdateService.getAll(filter);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				SystemServiceUpdate a =list.get(i);
				if(list.get(i).getUpdatetime()!=null && !"" .equals(list.get(i).getUpdatetime())){
					list.get(i).setDisUpTime(sdf.format(list.get(i).getUpdatetime()));
					if(a.getUpdatesize()!=null && !"".equals(a.getUpdatesize())){
						if(a.getUpdatesize()>1024*1024){
							list.get(i).setDisFilesize(a.getUpdatesize()/1024/1024+"MB");
						}else if(a.getUpdatesize()>1024) {
							list.get(i).setDisFilesize(a.getUpdatesize()/1024+"KB");
						}else{
							list.get(i).setDisFilesize(a.getUpdatesize()+"B");
						}
					}
				}
			}
		}
		
		Type type=new TypeToken<List<SystemServiceUpdate>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				systemServiceUpdateService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SystemServiceUpdate systemServiceUpdate=systemServiceUpdateService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(systemServiceUpdate));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
 
	 
	/**
	 * 添加及保存操作
	 */
	public String save(){
		 System.out.println(SystemServiceUpdateUpLoadWinmyUpload );
		 
		String saveUrl="D:\\testupload\\";
		System.out.println("服务器的类型==="+isPublication);
		 
		
	 
		if(systemServiceUpdate.getId()==null){
			systemServiceUpdateService.save(systemServiceUpdate);
		}else{
			SystemServiceUpdate orgSystemServiceUpdate=systemServiceUpdateService.get(systemServiceUpdate.getId());
			try{
				BeanUtil.copyNotNullProperties(orgSystemServiceUpdate, systemServiceUpdate);
				systemServiceUpdateService.save(orgSystemServiceUpdate);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public File getSystemServiceUpdateUpLoadWinmyUpload() {
		return SystemServiceUpdateUpLoadWinmyUpload;
	}

	public void setSystemServiceUpdateUpLoadWinmyUpload(
			File systemServiceUpdateUpLoadWinmyUpload) {
		SystemServiceUpdateUpLoadWinmyUpload = systemServiceUpdateUpLoadWinmyUpload;
	}

	public String getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(String isPublication) {
		this.isPublication = isPublication;
	}
	 
	public File getSystemUploadWinfileUpload() {
		return SystemUploadWinfileUpload;
	}

	public void setSystemUploadWinfileUpload(File systemUploadWinfileUpload) {
		SystemUploadWinfileUpload = systemUploadWinfileUpload;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SystemServiceUpdate getSystemServiceUpdate() {
		return systemServiceUpdate;
	}

	public void setSystemServiceUpdate(SystemServiceUpdate systemServiceUpdate) {
		this.systemServiceUpdate = systemServiceUpdate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
