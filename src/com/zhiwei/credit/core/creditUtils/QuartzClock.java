package com.zhiwei.credit.core.creditUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.system.SysDatabaseBRDao;
import com.zhiwei.credit.model.system.SysDatabaseBR;

/**
 * 
 * 删除数据库垃圾记录 操作
 * 
 * @author 定时任务 chencc
 *
 */
@Controller  
@Scope("prototype")
public class QuartzClock extends CreditBaseAction  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	CreditBaseDao creditBaseDao;
	@Resource
	SysDatabaseBRDao sysDatabaseBRDao;
	public void executeClockTask() {
		String[] markStr = { "cs_person_tx", "cs_person_sfzz",
				"cs_person_sfzf", "cs_enterprise_dsdjz",
				"cs_enterprise_yyzzfb", "cs_enterprise_zzjgdmz" };
		for (int i = 0; i < markStr.length; i++) {
			String hql = "delete FileForm f where f.mark = ?";
			try {
				creditBaseDao.excuteSQL(hql,markStr[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try{
			String name = "";//文件名
		    String userName="";
		    String password="";
		    String host="";
		    String port="";
		    String dbName="";
		    String webUrl = AppUtil.getAppAbsolutePath();
		    File file = new File(webUrl+"\\backup");
			if(!file.exists()){
				file.mkdirs();
			}
			InputStream in = new BufferedInputStream(getClass().getResourceAsStream("/conf/jdbc.properties"));
		    Properties p = new Properties();
		    p.load(in);
			Date date = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		    String url=p.getProperty("jdbc.url");
			String[] ret=url.split("//")[1].split("/")[0].split(":");
			host=ret[0];
			port=ret[1];
			name = sdf.format(date)+".sql";
		    userName=p.getProperty("jdbc.username");
		    password=p.getProperty("jdbc.password");
		    dbName=p.getProperty("jdbc.databaseName");
		    Runtime rt = Runtime.getRuntime();
		    String backPath=webUrl+"\\backup\\"+name;
		    String mysql = "mysqldump -u" +userName+ " -p" + password + " --default-character-set=utf8 -h"+host+" -P"+port+" " + dbName +" >"+"\""+backPath+"\"";  
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
		    br.setIsAutoCreate(0);
		    String aValue=DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss");
		    br.setCreateDate(aValue);
		    this.sysDatabaseBRDao.save(br);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
