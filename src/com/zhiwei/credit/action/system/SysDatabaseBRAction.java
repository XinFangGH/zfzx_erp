package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.model.system.SysDatabaseBR;
import com.zhiwei.credit.service.system.SysDatabaseBRService;
/**
 * 
 * @author 
 *
 */
public class SysDatabaseBRAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Resource
	private SysDatabaseBRService sysDatabaseBRService;
	private SysDatabaseBR sysDatabaseBR;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SysDatabaseBR getSysDatabaseBR() {
		return sysDatabaseBR;
	}

	public void setSysDatabaseBR(SysDatabaseBR sysDatabaseBR) {
		this.sysDatabaseBR = sysDatabaseBR;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SysDatabaseBR> list= sysDatabaseBRService.getAll(filter);
		Type type=new TypeToken<List<SysDatabaseBR>>(){}.getType();
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
	    String webUrl=this.getRequest().getRealPath("");
	    File file = new File(webUrl+"\\backup");
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				 SysDatabaseBR temp=this.sysDatabaseBRService.get(new Long(id));
			     File dfile = new File(webUrl+"\\backup\\"+temp.getFileName());
				 if(dfile.exists()){
						System.gc();
						dfile.delete();
				 }
				 sysDatabaseBRService.remove(new Long(id));
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
		SysDatabaseBR sysDatabaseBR=sysDatabaseBRService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(sysDatabaseBR));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		
		try
		{
			String name = "";//文件名
		    String userName="";
		    String password="";
		    String host="localhost";
		    String port="3306";
		    String dbName="hurong_ferp2.0";
		    //String webUrl=this.getRequest().getRealPath("");
		    String webUrl=AppUtil.getAppAbsolutePath();
		    File file = new File(webUrl+"\\backup");
			if(!file.exists()){
				file.mkdirs();
			}
			InputStream in = new BufferedInputStream(getClass().getResourceAsStream("/conf/jdbc.properties"));
		    Properties p = new Properties();
		    p.load(in);
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			name = sdf.format(date)+".sql";
			String url=p.getProperty("jdbc.url");
			String[] ret=url.split("//")[1].split("/")[0].split(":");
			host=ret[0];
			port=ret[1];
		    userName=p.getProperty("jdbc.username");
		    password=p.getProperty("jdbc.password");
		    dbName=p.getProperty("jdbc.databaseName");
		    Runtime rt = Runtime.getRuntime();
		    String backPath=webUrl+"\\backup\\"+name;
		    String mysql = "mysqldump -u" +userName+ " -p" + password + " --default-character-set=utf8 -h"+host+" -P"+port+"   --ignore-table="+dbName+".sys_databasebr  "+ dbName +" >"+"\""+backPath+"\"";  
		    Process proc = rt.exec("cmd.exe /c "+mysql);// 设置导出编码为utf8。这里必须是utf8
		    int tag = proc.waitFor();// 等待进程终止 
		    SysDatabaseBR br=new SysDatabaseBR();
		    File f=new File(backPath);
		    if(f.exists()){
		    	 FileInputStream fis = null;
		    	 fis = new FileInputStream(f);  
		    	 br.setFileSize(fis.available());
		    }
		    else{
		    	 br.setFileSize(0);
		    }
		    br.setFileName(name);
		    br.setIsAutoCreate(1);
		    String aValue=DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss");
		    br.setCreateDate(aValue);
		    this.sysDatabaseBRService.save(br);
			setJsonString("{success:true}");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
		
	}
	public String restore(){
		
		try
		{    
				String name = "";//文件名
			    String userName="";
			    String password="";
			    String dbName="hurong_ferp2.0";
				InputStream in = new BufferedInputStream(getClass().getResourceAsStream("/conf/jdbc.properties"));
			    Properties p = new Properties();
			    p.load(in);
			    userName=p.getProperty("jdbc.username");
			    password=p.getProperty("jdbc.password");
			    dbName=p.getProperty("jdbc.databaseName");
			    String sqlPath="";
				SysDatabaseBR sdBR=this.sysDatabaseBRService.get(id);
			    String webUrl=this.getRequest().getRealPath("");
			    name=sdBR.getFileName();
			    sqlPath=webUrl+"\\backup\\"+name;
				Runtime rt = Runtime.getRuntime();
				String mysql = "mysql -u"+userName+" -p"+password+ " "+dbName+" <"+"\""+ sqlPath+"\"";
			    Process proc  = rt.exec("cmd.exe /c "+mysql);
				int tag = proc.waitFor();// 等待进程终止
			    setJsonString("{success:true}");
		}
		catch (Exception e) {
			e.printStackTrace();
			setJsonString("{success:false}");
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	public void download(){
		
	   try{
		    String name="";
		    String sqlPath="";
			SysDatabaseBR sdBR=this.sysDatabaseBRService.get(id);
		    String webUrl=this.getRequest().getRealPath("");
		    name=sdBR.getFileName();
		    sqlPath=webUrl+"\\backup\\"+name;
		    ElementUtil.downloadFile(sqlPath,this.getResponse());
	   }
	   catch (Exception e) {
			e.printStackTrace();
	    }
	}
}
