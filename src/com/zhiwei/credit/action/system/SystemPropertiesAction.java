package com.zhiwei.credit.action.system;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import net.sf.json.JSONObject;
import sun.net.ftp.FtpClient;

import com.thirdPayInterface.ThirdPayWebClient;
import com.thirdPayInterface.Fuiou.Fuiou;
import com.thirdPayInterface.Huifu.Huifu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.messageAlert.model.SmsTemplate;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jms.MailMessageProducer;
import com.zhiwei.core.model.MailModel;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.PropertiesUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.JDBCUtilHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.sms.MessageStrategyService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.util.UrlUtils;

@SuppressWarnings("unchecked")
public class SystemPropertiesAction extends BaseAction{

	@Resource
	private FileFormService fileFormService;
	@Resource
	private FTPIoadFileService fTPUploadFileimpl;
	@Resource
	private SysConfigService sysConfigService;
	
	// 上传文件参数
	private String extendname;//附件扩展名
	private File fileUpload;
	private String fileUploadFileName;
	public final int UPLOAD_SIZE = 1024 * 1024 * 10;
	
	private static final String uploadPath="attachFiles/uploads";
	
	
	private static String profilepath = AppUtil.servletContext.getRealPath("/WEB-INF/classes/conf/sendmessage_config.properties");
	/////////////////
	//static String profilepath="sendmessage_config.properties"; 
	
	
	
	
	//private static String serverPath="/upload/image/";//FTP上传到P2P服务器的路径
	private static String serverPath="attachFiles/upload/image/";//FTP上传到P2P服务器的路径
	
	private String thirdPayEnvType;//第三方环境
	private String countCode;//统计代码
	private String copyRight;//站点版权
	private String workTime;//工作时间
	private String beianInfo;//备案信息
	private String attest;//认证信息
	private String baiduMap;//百度地图
	private String baiduMapMarker;//百度地图Marker
	private String companyAddress;//公司地址
	private String customerEmail;//客服邮箱
	private String consumerHotline;//客服电话
	private String consumerQQ;//客服QQ
	
	/**
	 * ERP获得系统环境参数
	 * @return
	 */
	public String getPCInfo(){
		try {
			InetAddress addr = InetAddress.getLocalHost();
			Properties props=System.getProperties();
			ServletContext application=this.getSContext();
			String ip=addr.getHostAddress().toString(); //获取本机ip  
			String hostName=addr.getHostName().toString(); //获取本机计算机名称
			
			StringBuffer sb=new StringBuffer("{\"success\":true,\"data\":{");
			sb.append("\"hostName\":\"").append(hostName).append("\",").
			append("\"hostIp\":\"").append(ip).append("\",").
			append("\"OSName\":\"").append(props.getProperty("os.name")).append("\",").
			append("\"OSVersion\":\"").append(props.getProperty("os.version")).append("\",").
			append("\"JavaVersion\":\"").append(props.getProperty("java.version")).append("\",").
			append("\"UserName\":\"").append(props.getProperty("user.name")).append("\",").
			append("\"Tomcat\":\"").append(application.getServerInfo()).append("\",");
			
			Connection conn=null;
			try {
				conn=JDBCUtilHelper.getConnectionByJDBC(AppUtil.getAppAbsolutePath());
				DatabaseMetaData dbmd = conn.getMetaData();
				sb.append("\"SQL\":\"").append(dbmd.getDatabaseProductName()).append("\"");
			} catch (SQLException e) {
				closeConnect(conn);
				e.printStackTrace();
			}finally{
				closeConnect(conn);
			}  
			sb.append("}}");
			JsonUtil.responseJsonString(sb.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}   
		return SUCCESS;
	}
	
	/**
	 * 关闭jdbc连接
	 * @param conn
	 */
	public static void closeConnect(Connection conn){
		try {
			if(null!=conn){
				conn.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 查看P2P系统基础配置参数
	 * @return
	 */
	public String getP2PBaseInfo(){
		//1.获得configMap对象
		Map erp_map=AppUtil.getConfigMap();
		//2.获得P2P项目的configMap对象
		Map p2p_map=AppUtil.getP2PSysConfig();
		try {
			//3.后台发送post请求,读取/WEB-INF/classes/conf/config.properties和/WEB-INF/classes/conf/sendmessage_config.properties属性文件
			//将结果重新写入p2p项目的configMap对象中,并将内容拼接成json串返回
			String url=erp_map.get("p2pUrl")+erp_map.get("p2p_config").toString();
			String result=ThirdPayWebClient.getWebContentByPost(url,"","UTF-8",12000);
			JSONObject map = JSONObject.fromObject(JSONObject.fromObject(result.replaceAll("&nbsp;"," ")).get("data"));
			Iterator ite_data=map.keys();
			while(ite_data.hasNext()){
				String temp=ite_data.next().toString();
				p2p_map.put(temp,map.get(temp));
			}
			//4.拼接json串返回到ERP页面
			String countCode="";//站长统计
			String attest="";//网站认证
			int sms_count=0;
			StringBuffer sms=new StringBuffer("");
			String tempSms="";
			Iterator<?> ite=map.keySet().iterator();
			while(ite.hasNext()){
				String key=ite.next().toString();
				if(key.startsWith("sms_") && !key.equals("sms_benname")){
					sms_count++;
					sms.append("{\"模板"+sms_count+"\":").append("\""+map.get(key)+"\"},");
				}
			}
			if(null!=sms.toString() && !"".equals(sms.toString())){
				tempSms=sms.substring(0,sms.length()-1);
			}
			if(null!=map.get("countCode") && !"".equals(map.get("countCode"))){
				countCode=map.get("countCode").toString().replaceAll("\r|\n","").replace("\"","\\\"");
			}
			
			//查询网站认证
			QueryFilter filter=new QueryFilter();
			filter.addFilter("Q_configKey_S_EQ","attest");
			List<SysConfig> list = sysConfigService.getAll(filter);
			if(null!=list && list.size()>0){
				attest=list.get(0).getDataValue().replace("\n","").replace("\"","\\\"");
			}
			StringBuffer sb=new StringBuffer("{\"success\":true,\"data\":{");
			sb.append("\"siteName\":\"").append(null==map.get("subject")?"":map.get("subject")).append("\",").
			append("\"Email_Address\":\"").append(null==map.get("mail.from")?"":map.get("mail.from")).append("\",").
			append("\"Email_UserName\":\"").append(null==map.get("mail.username")?"":map.get("mail.username")).append("\",").
			append("\"Email_Pass\":\"").append(null==map.get("mail.password")?"":map.get("mail.password")).append("\",").
			append("\"SMTP\":\"").append(null==map.get("mail.host")?"":map.get("mail.host")).append("\",").
			append("\"SMS_Address\":\"").append(null==map.get("smsUrl")?"":map.get("smsUrl")).append("\",").
			append("\"SMS_UserName\":\"").append(null==map.get("smsAccountID")?"":map.get("smsAccountID")).append("\",").
			append("\"SMS_Pass\":\"").append(null==map.get("smsPassword")?"":map.get("smsPassword")).append("\",").
			append("\"SMS_Count\":\"").append(sms_count).append("\",").
			append("\"SMS_Template\":[").append(tempSms).append("],").
			append("\"thirdPayType\":\"").append(null==map.get("thirdPayType")?"":map.get("thirdPayType")).append("\",").
			append("\"thirdPayEnvironmentType\":\"").append(null==map.get("thirdPayEnvironmentType")?"":map.get("thirdPayEnvironmentType")).append("\",").
			append("\"thirdPayConfig\":\"").append(null==map.get("thirdPayConfig")?"":map.get("thirdPayConfig")).append("\",").
			append("\"thirdPayURL\":\"").append(null==map.get("thirdPayURL")?"":map.get("thirdPayURL")).append("\",").
			append("\"copyRight\":\"").append(null==map.get("copyRight")?"":map.get("copyRight")).append("\",").
			append("\"workTime\":\"").append(null==map.get("workTime")?"":map.get("workTime")).append("\",").
			append("\"beianInfo\":\"").append(null==map.get("beianInfo")?"":map.get("beianInfo")).append("\",").
			append("\"countCode\":\"").append(countCode).append("\",").
			append("\"attest\":\"").append(attest).append("\",").
			append("\"baiduMap\":\"").append(null==map.get("baiduMap")?"":map.get("baiduMap")).append("\",").
			append("\"baiduMapMarker\":'").append(null==map.get("baiduMapMarker")?"":map.get("baiduMapMarker")).append("',").
			append("\"companyAddress\":\"").append(null==map.get("companyAddress")?"":map.get("companyAddress")).append("\",").
			append("\"customerEmail\":\"").append(null==map.get("customerEmail")?"":map.get("customerEmail")).append("\",").
			append("\"consumerHotline\":\"").append(null==map.get("consumerHotline")?"":map.get("consumerHotline")).append("\",").
			append("\"consumerQQ\":\"").append(null==map.get("consumerQQ")?"":map.get("consumerQQ")).append("\"");
			
			List<FileForm> file_list=fileFormService.listByMark("system_p2p");
			if(null!=file_list && file_list.size()>0){
				sb.append(",\"fileData\":[");
				for(int i=0;i<file_list.size();i++){
					sb.append("{\"remark\":");
					sb.append("\""+file_list.get(i).getRemark()+"\"");
					sb.append(",\"id\":");
					sb.append("\""+file_list.get(i).getFileid()+"\"");
					sb.append(",\"webPath\":");
					sb.append("\""+file_list.get(i).getWebPath()+"\"");
					if(i!=file_list.size()-1){
						sb.append("},");
					}else{
						sb.append("}");
					}
				}
				sb.append("]");
			}
			sb.append("}}");
			JsonUtil.responseJsonString(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * ERP查看系统基础配置参数
	 * @return
	 */
	public String getBaseInfo(){
		int sms_count=0;
		StringBuffer sms=new StringBuffer("");
		Map<?,?> map=AppUtil.getConfigMap();
		Iterator<?> ite=map.keySet().iterator();
		while(ite.hasNext()){
			String key=ite.next().toString();
			if(key.startsWith("sms_") && !key.equals("sms_benname")){
				sms_count++;
				sms.append("{\"模板"+sms_count+"\":").append("\""+map.get(key)+"\"},");
			}
		}
		StringBuffer sb=new StringBuffer("{\"success\":true,\"data\":{");
		sb.append("\"SystemVersion\":\"").append(AppUtil.getSystemVersion()).append("\",").
		append("\"isGroupVersion\":\"").append("true".equals(AppUtil.getSystemIsGroupVersion())?"是":"否").append("\",").
		append("\"isOA\":\"").append("true".equals(AppUtil.getSystemIsOAVersion())?"是":"否").append("\",").
		append("\"FTP_IP\":\"").append(map.get("ftpIp")).append("\",").
		append("\"FTP_UserName\":\"").append(map.get("ftpUsName")).append("\",").
		append("\"FTP_Pass\":\"").append(map.get("ftpPss")).append("\",").
		append("\"FTP_Port\":\"").append(map.get("ftpPort")).append("\",").
		append("\"imagePath\":\"").append(map.get("imagePathFormat").toString().split("\\{")[0]).append("\",").
		append("\"Email_Address\":\"").append(map.get("mail.from")).append("\",").
		append("\"Email_UserName\":\"").append(map.get("mail.username")).append("\",").
		append("\"Email_Pass\":\"").append(map.get("mail.password")).append("\",").
		append("\"SMTP\":\"").append(map.get("mail.host")).append("\",").
		append("\"SMS_Address\":\"").append(map.get("smsUrl")).append("\",").
		append("\"SMS_UserName\":\"").append(map.get("smsAccountID")).append("\",").
		append("\"SMS_Pass\":\"").append(map.get("smsPassword")).append("\",").
		append("\"SMS_Count\":\"").append(sms_count).append("\",").
		append("\"SMS_Template\":[").append(sms.subSequence(0,sms.length()-1)).append("],").
		append("\"thirdPayType\":\"").append(map.get("thirdPayType")).append("\",").
		append("\"thirdPayEnvironmentType\":\"").append(map.get("thirdPayEnvironmentType")).append("\",").
		append("\"thirdPayConfig\":\"").append(map.get("thirdPayConfig")).append("\",").
		append("\"thirdPayURL\":\"").append(map.get("thirdPayURL")).append("\",").
		append("\"p2pUrl\":\"").append(map.get("p2pUrl")).append("\"");
		
		List<FileForm> file_list=fileFormService.listByMark("system");
		if(null!=file_list && file_list.size()>0){
			sb.append(",\"fileData\":[");
			for(int i=0;i<file_list.size();i++){
				sb.append("{\"remark\":");
				sb.append("\""+file_list.get(i).getRemark()+"\"");
				sb.append(",\"id\":");
				sb.append("\""+file_list.get(i).getFileid()+"\"");
				sb.append(",\"webPath\":");
				sb.append("\""+file_list.get(i).getWebPath()+"\"");
				if(i!=file_list.size()-1){
					sb.append("},");
				}else{
					sb.append("}");
				}
			}
			sb.append("]");
		}
		sb.append("}}");
		JsonUtil.responseJsonString(sb.toString());
		return SUCCESS;
	}
	
	public String getP2pBaseInfo() throws IOException{
		Map erp_map=AppUtil.getConfigMap();
		String result =  ThirdPayWebClient.getWebContentByPost(erp_map.get("p2pUrl").toString()+"/system/toGetP2pInfoSystemProperties.do","",Huifu.CHARSETUTF8,12000);
		JsonUtil.responseJsonString(result);
		return SUCCESS;
	}
	
	
	public String updateP2pMessInfo() throws IOException{
		Map<String,String> params = new HashMap();
		Map erp_map=AppUtil.getConfigMap();
		String smsUrl = this.getRequest().getParameter("smsUrl");
		String smsUserName = this.getRequest().getParameter("smsUserName");
		String smsUserPass = this.getRequest().getParameter("smsUserPass");
		if(smsUrl!=null && !"".equals(smsUrl)){
			params.put("smsUrl", smsUrl);
		}
		
		if(smsUserName!=null && !"".equals(smsUserName)){
			params.put("smsAccountID", smsUserName);
		}
		    
		if(smsUserPass!=null && !"".equals(smsUserPass)){
			params.put("smsPassword", smsUserPass);
		}
 
		String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
		String result =  ThirdPayWebClient.getWebContentByPost(erp_map.get("p2pUrl").toString()+"/system/updateBaseInfoSystemProperties.do",param,Huifu.CHARSETUTF8,12000);
	    //setJsonString("{success:true,e:true,result:'保存成功！'}");
	    setJsonString(result.toString());
		return SUCCESS;
	}
	/**  
	    * 根据主键key读取主键的值value  
	    * @param filePath 属性文件路径  
	    * @param key 键名  
	    */   
	    public static String readValue(String filePath, String key) {   
	        Properties props = new Properties();   
	        try {   
	            InputStream in = new BufferedInputStream(new FileInputStream(   
	                    filePath));   
	            props.load(in);   
	            String value = props.getProperty(key);   
	            System.out.println(key +"键的值是："+ value);   
	            return value;   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            return null;   
	        }   
	    }   
	
	 private static Properties props = new Properties();   
	    static {   
	        try {   
	            props.load(new FileInputStream(profilepath));   
	        } catch (FileNotFoundException e) {   
	            e.printStackTrace();   
	            System.exit(-1);   
	        } catch (IOException e) {          
	            System.exit(-1);   
	        }   
	    } 
	
	public void updateProperty(String keyname,String keyvalue){
        try {   
            props.load(new FileInputStream(profilepath));   
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。   
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。   
            OutputStream fos = new FileOutputStream(profilepath);              
            props.setProperty(keyname, keyvalue);   
            // 以适合使用 load 方法加载到 Properties 表中的格式，   
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流   
            props.store(fos, "Update '" + keyname + "' value");   
            fos.close();
        } catch (IOException e) {   
            System.err.println("属性文件更新错误");   
        }  
	}
	
	 public static void writeProperties(String keyname,String keyvalue) {          
	        try {   
	            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。   
	            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。   
	            OutputStream fos = new FileOutputStream(profilepath);   
	            props.setProperty(keyname, keyvalue);   
	            // 以适合使用 load 方法加载到 Properties 表中的格式，   
	            // 将此 Properties 表中的属性列表（键和元素对）写入输出流   
	            Map<String,String> map=AppUtil.getConfigMap();
	            map.put(keyname, keyvalue);
	            props.store(fos, "Update '" + keyname + "' value");   
	        } catch (IOException e) {   
	            System.err.println("属性文件更新错误");   
	        }   
	    } 
	
	/**
	 *更新短信配置文件 
	 */
	public String updateBaseInfo(){
		String smsUrl = this.getRequest().getParameter("smsUrl");
		String smsUserName = this.getRequest().getParameter("smsUserName");
		String smsUserPass = this.getRequest().getParameter("smsUserPass");
		if(smsUrl!=null && !"".equals(smsUrl)){
			readValue(profilepath, "smsUrl");   
			writeProperties("smsUrl", smsUrl);
		}
		
		if(smsUserName!=null && !"".equals(smsUserName)){
			readValue(profilepath, "smsAccountID");   
			writeProperties("smsAccountID", smsUserName);
		}
		    
		if(smsUserPass!=null && !"".equals(smsUserPass)){
			readValue(profilepath, "smsPassword");   
			updateProperty("smsPassword", smsUserPass);
		}
	    //getBaseInfo();
	    StringBuffer sb = new StringBuffer("{\"success\":true,e:true,result:'保存成功！',\"data\":{");
		sb.append("\"smsUrl\":\"").append(smsUrl).append("\",").
		append("\"smsAccountID\":\"").append(smsUserName).append("\",").
		append("\"smsPassword\":\"").append(smsUserPass).append("\",").
		append("}}");
	    //setJsonString("{success:true,e:true,result:'保存成功！'}");
	    setJsonString(sb.toString());
		return SUCCESS;
	}
	
	
	
	/**
	 * ERP基础参数配置中的logo上传方法
	 */
	@SuppressWarnings({ "deprecation"})
	public void uploadFile(){
		AppUser user = ContextUtil.getCurrentUser();
		if(user!=null){
			//判断文件上传的类型
			fileUploadFileName = StringUtil.checkExtName(fileUploadFileName);
			 boolean extType = StringUtil.checkExtType(fileUploadFileName);
			if(extType){
				StringBuffer temp_flag=new StringBuffer("");//记录上传成功或者失败的提示信息
				String mark = getRequest().getParameter("mark");
				String remark = getRequest().getParameter("remark");
				String realPath = super.getRequest().getRealPath("/");
				String basePath="attachFiles/uploads/systemLogo";
				File file = new File(realPath + basePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String guid = UUID.randomUUID().toString();
				String path = basePath + "/" + guid+ extendname;
				file = new File(realPath + basePath+ "/"+ guid + extendname);
				boolean flag = FileHelper.fileUpload(fileUpload, file,new byte[UPLOAD_SIZE]);
				//   1.先删除上传的文件
				List<FileForm>  list=fileFormService.ajaxGetFilesList(remark,mark);
				if(null!=list && list.size()>0){
					fileFormService.remove(list.get(0));
					String[] s = list.get(0).getWebPath().split(uploadPath);
					String t = s[1].toString().substring(1);
					File f = new File(realPath+uploadPath+"/"+t);
					if(f.exists()){
						FileHelper.deleteFile(f);
					}
				}
				//   2.保存新的上传文件
				FileForm fileInfo = new FileForm();
				if (flag) {
					fileInfo.setMark(mark);
					fileInfo.setRemark(remark);
					fileInfo.setContentType("");
					fileInfo.setSetname(fileUploadFileName);
					fileInfo.setFilename(fileUploadFileName);
					fileInfo.setFilepath(path);
					fileInfo.setExtendname(extendname);
					fileInfo.setCreatetime(DateUtil.getNowDateTimeTs());
					Long sl = fileUpload.length();
					fileInfo.setFilesize(sl.intValue());
					fileInfo.setCreatorid(ContextUtil.getCurrentUserId().intValue());
					fileInfo.setWebPath(path);
					fileInfo.setBusinessType("SmallLoan");
					fileFormService.save(fileInfo);
					AppUtil.getConfigMap().put(remark,path);
					//   3.如果是p2p的文件则需要通过ftp上传
					if("system_p2p".equals(mark)){
						String tempPath=serverPath+fileInfo.getSetname();
						String temp = fTPUploadFileimpl.upLoadFile(file.getAbsolutePath(),tempPath);
						if("true".equals(temp)){
							temp_flag.append("上传成功");
							try {
								//   4.更新p2p项目的config对象
								Map<String,String> params=new HashMap<String,String>();
								params.put(remark,tempPath);
								String param= UrlUtils.generateParams(params,"UTF-8");
								Map erp_map=AppUtil.getConfigMap();
								String url=erp_map.get("p2pUrl")+erp_map.get("p2p_save").toString();
								//后台发送post请求
								ThirdPayWebClient.getWebContentByPost(url,param,"UTF-8",12000);
								temp_flag.append(",保存成功");
							} catch (Exception e) {
								temp_flag.append(",保存失败");
								e.printStackTrace();
							}
						}else{
							temp_flag.append("上传失败");
						}
					}
				}
				String jsonStr = "{\"success\": true,\"fileid\":" + fileInfo.getFileid()+ ",\"webPath\":'" + fileInfo.getWebPath() + "',\"msg\":\""+temp_flag+"\"}";
				JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");
			}
		}
	}
	
	/**
	 * ERP删除基础配置参数的logo
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String delFile(){
		try{
			AppUser user = ContextUtil.getCurrentUser();
			if(user!=null){
				String realPath = super.getRequest().getRealPath("/");
				String fileId = getRequest().getParameter("fileId");
				FileForm  fileForm=fileFormService.getById(Integer.valueOf(fileId));
				boolean deletePath = false;
				if(user.getId().equals("1")){//超级管理员
					deletePath = true;
				}else{//其他用户删除附件验证是否是本人上传的附件
					if(user.getId().equals(fileForm.getCreatorid().toString())){
						deletePath = true;
					}
				}
				if(deletePath){
					if(null!=fileForm){
						fileFormService.remove(fileForm);
						AppUtil.getConfigMap().remove(fileForm.getRemark());
						String[] s = fileForm.getWebPath().split(uploadPath);
						String t = s[1].toString().substring(1);
						File f = new File(realPath+uploadPath+"/"+t);
						if(f.exists()){
							FileHelper.deleteFile(f);
						}
						//更新p2p项目的config对象和config.properties属性文件
						Map<String,String> params=new HashMap<String,String>();
						params.put("remark",fileForm.getRemark());
						params.put("filePath",serverPath+fileForm.getSetname());
						String param= UrlUtils.generateParams(params,"UTF-8");
						Map erp_map=AppUtil.getConfigMap();
						String url=erp_map.get("p2pUrl")+erp_map.get("p2p_delete").toString();
						//后台发送post请求
						ThirdPayWebClient.getWebContentByPost(url,param,"UTF-8",12000);
					}
					setJsonString("{success:true,msg:'删除成功!'}");
				}else{
					setJsonString("{success:true,msg:'删除失败，权限不足!'}");
				}
			}
		}catch(Exception e){
			setJsonString("{success:true,msg:'删除失败!'}");
		}
		return SUCCESS;
	}
	
	/**
	 * ERP测试ftp连接
	 * @return
	 */
	public String testFtpConn(){/*
		String ftpIp=this.getRequest().getParameter("ftpIp");
		int ftpPort=Integer.valueOf(this.getRequest().getParameter("ftpPort"));
		FtpClient ftpClient=new FtpClient();
		try {
			ftpClient.openServer(ftpIp,ftpPort);
			setJsonString("{success:true,msg:'连接成功!'}");
		} catch (IOException e) {
			e.printStackTrace();
			setJsonString("{success:true,msg:'连接失败!'}");
			// 关闭连接  
			try {
				ftpClient.closeServer();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally{
			// 关闭连接  
			try {
				ftpClient.closeServer();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}*/
		return SUCCESS;
	}
	
	/**
	 * ERP测试短信连接
	 * @return
	 */
	public String testSMSConn(){
		String type=this.getRequest().getParameter("type");//平台类型用以区分是ERP还是P2P
		//获得短信提供上名称
		String sms_bean="";
		if("ERP".equals(type)){
			sms_bean=AppUtil.getConfigMap().get("sms_benname").toString();
		}else if("P2P".equals(type)){
			sms_bean=AppUtil.getP2PSysConfig().get("sms_benname").toString();
		}
		MessageStrategyService messageStrategy = (MessageStrategyService) AppUtil.getBean(sms_bean);
		String phone=this.getRequest().getParameter("telphone");
		String sms_test="";
		if("hXSmsManagerService".equals(sms_bean)){//华兴软通
			sms_test=AppUtil.getConfigMap().get("sms_test").toString();
			sms_test=sms_test.replace("${subject}", AppUtil.getConfigMap().get("subject")==null?"":AppUtil.getConfigMap().get("subject").toString());
		}else if("yXTessageService".equals(sms_bean)){//亿信通
			sms_test=AppUtil.getConfigMap().get("sms_yx_test").toString();
		}
		String result=messageStrategy.sendMsg(phone,sms_test);
		String[] reltArr = result.split("&");
		String[] resultArr = reltArr[0].split("=");
		if(resultArr[1].equals("0")){
			setJsonString("{\"success\":true,\"id\":0,\"msg\":\"【短信接口调用成功,发送短信："+sms_test+"】,接收手机："+phone+"!\"}");
		}else{
			setJsonString("{\"success\":false,\"id\":0,\"msg\":\"【短信接口调用失败,发送短信："+sms_test+"】,接收手机："+phone+"!\"}");
		}
		return SUCCESS;
	}
	
	
	public String testSMSConn1(){
		String type=this.getRequest().getParameter("type");//平台类型用以区分是ERP还是P2P
		//获得短信提供上名称
		String sms_bean="";
		if("ERP".equals(type)){
			sms_bean=AppUtil.getConfigMap().get("sms_benname").toString();
		}else if("P2P".equals(type)){
			sms_bean=AppUtil.getP2PSysConfig().get("sms_benname").toString();
		}
		MessageStrategyService messageStrategy = (MessageStrategyService) AppUtil.getBean(sms_bean);
		String phone= "13161939898";//this.getRequest().getParameter("telphone");
		String sms_test="";
		if("hXSmsManagerService".equals(sms_bean)){//华兴软通
			sms_test=AppUtil.getConfigMap().get("sms_yx_test").toString().substring(0, AppUtil.getConfigMap().get("sms_yx_test").toString().length()-4);
		}else if("yXTessageService".equals(sms_bean)){//亿信通
			sms_test=AppUtil.getConfigMap().get("sms_yx_test").toString().trim().substring(0,AppUtil.getConfigMap().get("sms_yx_test").toString().length()-3);
		}
			String result=messageStrategy.sendMsg(phone,sms_test);
			if(result == null){
				setJsonString("{\"success\":false,\"id\":0,\"msg\":\"【短信接口调用失败】!\"}");
			}else{
				String[] reltArr = result.split("&");
				String[] resultArr = reltArr[0].split("=");
				if(resultArr[1].equals("28") || resultArr[1].equals("2") || resultArr[1].equals("3")|| resultArr[1].equals("0")){
					setJsonString("{\"success\":true,\"id\":0,\"msg\":\"【短信接口调用成功】!\"}");
				}else{
					setJsonString("{\"success\":false,\"id\":0,\"msg\":\"【短信接口调用失败，账号密码错误】!\"}");
				}
			}
		return SUCCESS;
	}
	
	public String testP2pMess() throws IOException{
		try{
			String result =  ThirdPayWebClient.getWebContentByPost("http://127.0.0.1:8086/interface_p2p6.0/system/testSMSConnSystemProperties.do","",Huifu.CHARSETUTF8,12000);
			JsonUtil.responseJsonString(result);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			setJsonString("{\"success\":false,\"id\":0,\"msg\":\"【调用接口指出错】!\"}");
		}
		return SUCCESS;
	}
	
	/**
	 * ERP测试是否可以发送邮件
	 * @return
	 */
	public String testEmailConn(){
		String email=this.getRequest().getParameter("email");
		String tempPath="mail/testEmail.vm";
		try{
			String content = AppUtil.getConfigMap().get("email_test").toString();
			content=content.replace("${subject}", AppUtil.getConfigMap().get("subject")==null?"":AppUtil.getConfigMap().get("subject").toString());
			MailMessageProducer mailMessageProducerThread = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
			MailModel mailModel=new MailModel();
			Map<String,String> model=new HashMap<String,String>();
			model.put("project",AppUtil.getConfigMap().get("subject").toString());
			model.put("content",content);
			mailModel.setMailTemplate(tempPath);
			mailModel.setTo(email);
			mailModel.setMailData(model);
			//把邮件加至发送列队中去
			mailMessageProducerThread.send(mailModel);
			setJsonString("{\"success\":true,\"id\":0,\"msg\":\"邮件发送成功!\"}");
		}catch(Exception e){
			setJsonString("{\"success\":true,\"id\":0,\"msg\":\"邮件发送失败!\"}");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * ERP基础配置参数保存方法
	 */
	public String save(){
//		String thirdPayEnvType=this.getRequest().getParameter("thirdPayEnvType");//第三方环境
		try{
			Map map=AppUtil.getConfigMap();
			String filePath = AppUtil.getContext().getRealPath("/WEB-INF/classes/conf/config.properties");
			PropertiesUtil.writeKey(filePath, "thirdPayEnvironmentType",thirdPayEnvType);
			setJsonString("{success:true,msg:'保存成功!'}");
			
			map.put("thirdPayEnvironmentType",thirdPayEnvType);
		}catch(Exception e){
			setJsonString("{success:true,msg:'保存失败!'}");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * P2P基础配置参数保存方法
	 */
	public String saveP2P(){
		try{
			//1.获得configMap
			Map erp_map=AppUtil.getConfigMap();
			Map p2p_map=AppUtil.getP2PSysConfig();
			//2.将request请求参数封装成Map对象
			Map<String, String> params=AppUtil.getParameterRequsetMap(this.getRequest());
			//3.将参数进行编码
			String param=UrlUtils.generateParams(params,"UTF-8");
			String url=erp_map.get("p2pUrl")+erp_map.get("p2p_save").toString();
			//4.后台发送post请求
			ThirdPayWebClient.getWebContentByPost(url,param,"UTF-8",12000);
			//5.保存网站认证信息到数据库
			String attest=this.getRequest().getParameter("attest");
			saveToSysConfig("attest",attest,"",p2p_map);
			setJsonString("{success:true,msg:'保存成功!'}");
		}catch(Exception e){
			setJsonString("{success:true,msg:'保存失败!'}");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 获取erp短信模板
	 * @return
	 */
	public String getSmsTemplate(){
		//获取短信模板内容
		List<SmsTemplate> list =getSendMessageConfig();
		//获取短信模板使用描述
		List<SmsTemplate> listUse =getSenduseExplainConfig();
		//把短信模板使用描述   添加到短信模板内容实体中
		for(SmsTemplate smste:list){
			for(SmsTemplate st:listUse){
				if(smste.getKey().equals(st.getKey())){
					smste.setUseExplain(st.getUseExplain());
				}
			}
		}
		Type type=new TypeToken<List<SmsTemplate>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 获取p2p短信模板
	 * @return
	 */
	public String getSmsP2PTemplate(){
		
		//获取短信模板内容
		List<SmsTemplate> list =getSmsP2PTemplateContent("1");
		//获取短信模板使用描述
		List<SmsTemplate> listUse =getSmsP2PTemplateContent("2");
		//把短信模板使用描述   添加到短信模板内容实体中
		for(SmsTemplate smste:list){
			for(SmsTemplate st:listUse){
				if(smste.getKey().equals(st.getKey())){
					smste.setUseExplain(st.getUseExplain());
				}
			}
		}
		Type type=new TypeToken<List<SmsTemplate>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 开始测试发送短信
	 * @return
	 */
	public String getSendInfo(){
		Map<String,Object> map = getAllSenduseExplainConfig();
		String content = this.getRequest().getParameter("content");
		String telphone = this.getRequest().getParameter("telphone");
		if(content!=null&&!content.equals("")){
			String replaceStr = map.get("replaceStr").toString();
			String replaceStrs[] = replaceStr.split("&");
			for(int i=0;i<replaceStrs.length;i++){
				String keyValue = replaceStrs[i];
				String keyValueStr[] = keyValue.split("=");
				content=content.replace(keyValueStr[0], keyValueStr[1]);
			}
			  MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(map.get("sms_benname").toString());
			  String result = messageStrategy.sendMsg(telphone,content);
			  if(result!=null&&!result.equals("")){
				 String  str[] = result.split("&");
				 if(str.length>1){
					 String strs[] = str[1].split("=");
					 if(!strs[1].contains("??")){
						 setJsonString("{success:true,msg:'"+strs[1]+"'}");
					 }else{
						 setJsonString("{success:true,msg:'链接失败'}");
					 }
				 }
			  }else{
				  setJsonString("{success:true,msg:'发送失败，请检查短信模板是否正确！'}");
			  }
		}
		return SUCCESS;
	}
	/**
	 * 修改短信模板内容和描述
	 * @return
	 */
	public String updateSms(){
		String key = this.getRequest().getParameter("key");
		String useExplain = this.getRequest().getParameter("useExplain");
		String content = this.getRequest().getParameter("content");
		/*try {
			useExplain = new String(useExplain.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			content = new String(content.getBytes("ISO-8859-1"),"utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		if(key!=null&&useExplain!=null&&content!=null&&!content.equals("")&&!useExplain.equals("")&&!key.equals("")){
			//修改模板内容
			String appAbsolutePath = AppUtil.getAppAbsolutePath();
			String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sendmessage_config.properties";
			try {
				FileInputStream fs=new FileInputStream(configFilePath);
				Properties p=new Properties();
				p.load(fs);
				p.setProperty(key,content);
				FileOutputStream fos=new FileOutputStream(configFilePath);
				p.store(fos, configFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//修改模板描述
			String configFilePathExplain=appAbsolutePath+"WEB-INF/classes/conf/senduseExplain_config.properties";
			try {
				FileInputStream fs=new FileInputStream(configFilePathExplain);
				Properties p=new Properties();
				p.load(fs);
				p.setProperty(key,useExplain);
				FileOutputStream fos=new FileOutputStream(configFilePathExplain);
				p.store(fos, configFilePathExplain);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setJsonString("{success:true,msg:'修改模板成功！'}");
		}else{
			setJsonString("{success:true,msg:'修改模板失败，请检查短信模板是否正确！'}");
		}
		return SUCCESS;
	}
	/**
	 * 修改p2p短信模板内容和描述
	 * @return
	 */
	public String updateP2PSms(){
		try {
			String key = this.getRequest().getParameter("key");
			String useExplain = this.getRequest().getParameter("useExplain");
			String content = this.getRequest().getParameter("content");
			/*try {
				useExplain = new String(useExplain.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			try {
				content = new String(content.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}*/
			if(key!=null&&useExplain!=null&&content!=null&&!content.equals("")&&!useExplain.equals("")&&!key.equals("")){
				//1.获得configMap
				Map erp_map=AppUtil.getConfigMap();
				//2.将request请求参数封装成Map对象
				//修改短信模板内容
				Map<String, String> params= new HashMap<String, String>();
				params.put(key, content);
				//3.将参数进行编码
				String param=UrlUtils.generateParams(params,"UTF-8");
				String url=erp_map.get("p2pUrl")+erp_map.get("p2p_save").toString();
				//4.后台发送post请求
				ThirdPayWebClient.getWebContentByPost(url,param,"UTF-8",12000);
				//修改短信模板描述
				Map<String, String> params1= new HashMap<String, String>();
				params1.put(key, useExplain);
				//3.将参数进行编码
				String param1=UrlUtils.generateParams(params1,"UTF-8");
				String url1=erp_map.get("p2pUrl")+erp_map.get("p2p_saveExplain").toString();
				//4.后台发送post请求
				ThirdPayWebClient.getWebContentByPost(url1,param1,"UTF-8",12000);
				
				setJsonString("{success:true,msg:'修改P2P模板成功!'}");
			}else{
				setJsonString("{success:true,msg:'修改P2P模板失败，请检查短信模板是否正确！'}");
			}
		} catch (Exception e) {
			setJsonString("{success:true,msg:'修改P2P模板失败，请检查链接是否顺畅！'}");
		}
		return SUCCESS;
	}
	public void saveToSysConfig(String key,String value,String typeName,Map map) {
		try{
			QueryFilter filter=new QueryFilter();
			filter.addFilter("Q_configKey_S_EQ",key);
			List<SysConfig> list = sysConfigService.getAll(filter);
			if(null!=list && list.size()>0){
				SysConfig sysConfig=list.get(0);
				sysConfig.setDataValue(value);
				sysConfigService.merge(sysConfig);
			}else{
				SysConfig sysConfig=new SysConfig();
				sysConfig.setConfigKey(key);
				sysConfig.setDataValue(value);
				sysConfig.setDataType(Short.valueOf("1"));//1=varchar
				sysConfig.setTypeKey("p2p_config");
				sysConfig.setTypeName("P2P项目公有属性");
				sysConfig.setConfigName(typeName);//必填项
				sysConfigService.save(sysConfig);
			}
			map.put(key,value);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 *获取erp短信模板内容
	 * @return
	 */
	public List<SmsTemplate> getSendMessageConfig(){
		List<SmsTemplate> smsList = new ArrayList<SmsTemplate>();
		Map<String,Object> map =new HashMap<String,Object>();
		String appAbsolutePath = AppUtil.getAppAbsolutePath();
    	String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sendmessage_config.properties";
    	Properties props=new Properties();
    	try{
    		FileInputStream fis=new FileInputStream(configFilePath);
    		Reader r = new InputStreamReader(fis, "UTF-8"); 
    		props.load(r);
    		Iterator it= props.keySet().iterator();
    		while(it.hasNext()){
    			String key=(String)it.next();
    			//查找字符串中是否包含“sms_”
    			Pattern p = Pattern.compile("sms_");
    			Matcher m = p.matcher(key);
    			while (m.find()) {
    				if(!key.equals("sms_benname")){
    					SmsTemplate sms = new SmsTemplate();
    					sms.setKey(key);
    					sms.setContent(props.get(key).toString());
    					smsList.add(sms);
    				}
    			}
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	return smsList;
	}
	/**
	 * 获取erp短信模板使用说明
	 * @return
	 */
	public List<SmsTemplate> getSenduseExplainConfig(){
		List<SmsTemplate> smsList = new ArrayList<SmsTemplate>();
		Map<String,Object> map =new HashMap<String,Object>();
		String appAbsolutePath = AppUtil.getAppAbsolutePath();
    	String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/senduseExplain_config.properties";
    	Properties props=new Properties();
    	try{
    		FileInputStream fis=new FileInputStream(configFilePath);
    		Reader r = new InputStreamReader(fis, "UTF-8"); 
    		props.load(r);
    		Iterator it= props.keySet().iterator();
    		while(it.hasNext()){
    			String key=(String)it.next();
    			//查找字符串中是否包含“sms_”
    			Pattern p = Pattern.compile("sms_");
    			Matcher m = p.matcher(key);
    			while (m.find()) {
    				SmsTemplate sms = new SmsTemplate();
    				sms.setKey(key);
    				sms.setUseExplain(props.get(key).toString());
    				smsList.add(sms);
    			}
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	return smsList;
	}
	/**
	 * 获取p2p短信模板内容
	 * @return
	 */
	public List<SmsTemplate> getSmsP2PTemplateContent(String type){
		List<SmsTemplate> smsList = new ArrayList<SmsTemplate>();
		try {
			//1.获得configMap对象
			Map erp_map=AppUtil.getConfigMap();
			String p2p_config = "";
			if(type.equals("1")){//获取内容
				p2p_config = erp_map.get("p2p_config").toString();
			}else{//获取描述
				p2p_config = erp_map.get("p2p_configExplain").toString();
			}
			String url=erp_map.get("p2pUrl")+p2p_config;
			String result=ThirdPayWebClient.getWebContentByPost(url,"","UTF-8",12000);
			JSONObject map = JSONObject.fromObject(JSONObject.fromObject(result.replaceAll("&nbsp;"," ")).get("data"));
			Iterator ite_data=map.keys();
			while(ite_data.hasNext()){
    			String key=(String)ite_data.next();
    			//查找字符串中是否包含“sms_”
    			Pattern p = Pattern.compile("sms_");
    			Matcher m = p.matcher(key);
    			while (m.find()) {
    				if(!key.equals("sms_benname")){
    					SmsTemplate sms = new SmsTemplate();
    					sms.setKey(key);
    					if(type.equals("1")){
    						sms.setContent(map.get(key).toString());
    					}else{
    						sms.setUseExplain(map.get(key).toString());
    					}
    					smsList.add(sms);
    				}
    			}
    		
			}
		} catch (Exception e) {
		}
		return smsList;
	}
	/**
	 * 获取短信模板所有配置信息
	 * @return
	 */
	public Map<String,Object> getAllSenduseExplainConfig(){
		List<SmsTemplate> smsList = new ArrayList<SmsTemplate>();
		Map<String,Object> map =new HashMap<String,Object>();
		String appAbsolutePath = AppUtil.getAppAbsolutePath();
    	String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sendmessage_config.properties";
    	Properties props=new Properties();
    	try{
    		FileInputStream fis=new FileInputStream(configFilePath);
    		Reader r = new InputStreamReader(fis, "UTF-8"); 
    		props.load(r);
    		Iterator it= props.keySet().iterator();
    		while(it.hasNext()){
    			String key=(String)it.next();
    			map.put(key, props.get(key).toString());
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	return map;
	}
	
	
	/**
	 * 获取erp短信模板.xml
	 * @return
	 */
	public String getSmsTemplateXml(){
		//获取短信模板内容
		List<SmsTemplate> list =getSmsXml();
		Type type=new TypeToken<List<SmsTemplate>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 *使用Dom4j读取xml文件，拿到短信模板
	 * @return
	 */
	public List<SmsTemplate> getSmsXml(){
		List<SmsTemplate> smsList = new ArrayList<SmsTemplate>();
		String appAbsolutePath = AppUtil.getAppAbsolutePath();
    	String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sms.xml";
    	try{
    		Document document=null;  
	        SAXReader reader=new SAXReader();  
	        InputStream ifile = new FileInputStream(configFilePath); //防止出现中文路径报错
	        InputStreamReader ir = new InputStreamReader(ifile, "UTF-8"); 
	        document=reader.read(ir);  
	        //查找结点  
	        List list=document.selectNodes("/sms/templates/template");  
	        Iterator it=list.iterator();  
	        while(it.hasNext()){
	            Element element=(Element) it.next();//节点遍历  
	            SmsTemplate smsTemplate = new SmsTemplate();
	            smsTemplate.setKey(element.attribute("key").getValue());
	            smsTemplate.setUseExplain(element.attribute("useExplain").getValue());
	            smsTemplate.setProhibitUse(element.attribute("prohibitUse").getValue());
	            smsTemplate.setIsTest(element.attribute("isTest").getValue());
	            smsTemplate.setContent(element.getText().trim());
	            smsList.add(smsTemplate);
	        }
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	return smsList;
	}
	
	/**
	 * 修改短信模板信息
	 * @return
	 */
	public String updateSmsXml(){
		String key = this.getRequest().getParameter("key");
		String useExplain = this.getRequest().getParameter("useExplain");
		String content = this.getRequest().getParameter("content");
		String prohibitUse = this.getRequest().getParameter("prohibitUse");
		String isTest = this.getRequest().getParameter("isTest");
		
		
		if(key!=null&&useExplain!=null&&content!=null&&!content.equals("")&&!useExplain.equals("")&&!key.equals("")
				&& prohibitUse !=null && isTest !=null){
			
			//修改模板内容
			String appAbsolutePath = AppUtil.getAppAbsolutePath();
			String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sms.xml";
			try {
				Document document=null;  
		        SAXReader reader=new SAXReader();  
		        InputStream ifile = new FileInputStream(configFilePath); //防止出现中文路径报错
		        InputStreamReader ir = new InputStreamReader(ifile, "UTF-8"); 
		        document=reader.read(ir);  
		        //查找结点  
		        List list=document.selectNodes("/sms/templates/template");  
		        Iterator it=list.iterator();
		        while(it.hasNext()){
		            Element element=(Element) it.next();//节点遍历  
		            Attribute attr=element.attribute("key");//节点属性值
		            if(attr.getValue().equals(key)){//遍历出与页面传过的的key值相等的节点，处理对应节点数据
		            	element.attribute("prohibitUse").setValue(prohibitUse);
		            	element.attribute("isTest").setValue(isTest);
		            	element.attribute("useExplain").setValue(useExplain);
		            	element.setText(content);
		            }
		        }
		        XMLWriter xmlwriter = new XMLWriter(new FileOutputStream(configFilePath));
		        xmlwriter.write(document);
		        xmlwriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			setJsonString("{success:true,msg:'修改模板成功！'}");
		}else{
			setJsonString("{success:true,msg:'修改模板失败，请检查短信模板是否正确！'}");
		}
		return SUCCESS;
	}
	
	public String getExtendname() {
		return extendname;
	}

	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public int getUPLOAD_SIZE() {
		return UPLOAD_SIZE;
	}

	public static String getUploadpath() {
		return uploadPath;
	}

	public String getThirdPayEnvType() {
		return thirdPayEnvType;
	}

	public void setThirdPayEnvType(String thirdPayEnvType) {
		this.thirdPayEnvType = thirdPayEnvType;
	}

	public String getCountCode() {
		return countCode;
	}

	public void setCountCode(String countCode) {
		this.countCode = countCode;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getBeianInfo() {
		return beianInfo;
	}

	public void setBeianInfo(String beianInfo) {
		this.beianInfo = beianInfo;
	}

	public String getAttest() {
		return attest;
	}

	public void setAttest(String attest) {
		this.attest = attest;
	}

	public String getBaiduMap() {
		return baiduMap;
	}

	public void setBaiduMap(String baiduMap) {
		this.baiduMap = baiduMap;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getConsumerHotline() {
		return consumerHotline;
	}

	public void setConsumerHotline(String consumerHotline) {
		this.consumerHotline = consumerHotline;
	}

	public String getConsumerQQ() {
		return consumerQQ;
	}

	public void setConsumerQQ(String consumerQQ) {
		this.consumerQQ = consumerQQ;
	}

	public String getBaiduMapMarker() {
		return baiduMapMarker;
	}

	public void setBaiduMapMarker(String baiduMapMarker) {
		this.baiduMapMarker = baiduMapMarker;
	}
}