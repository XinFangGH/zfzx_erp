package com.zhiwei.credit.service.sms.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.service.sms.MessageStrategyService;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;
 
 

/**
 * 华兴软通短信接口实现
 *svn:songwj
 *
 */
public class HXSmsManagerServerImpl implements MessageStrategyService {
	
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	public static final double Balance=0.1;// 每条短信金额
    //以下参数为服务器URL,以及发到服务器的参数，不用修改
   private static String strRegUrl = "http://www.stongnet.com/sdkhttp/reg.aspx";
   private static  String strBalanceUrl = "http://www.stongnet.com/sdkhttp/getbalance.aspx";
//   private static String strSmsUrl = "http://www.stongnet.com/sdkhttp/sendsms.aspx";
   private static String strSchSmsUrl = "http://www.stongnet.com/sdkhttp/sendschsms.aspx";
   private static String strStatusUrl = "http://www.stongnet.com/sdkhttp/getmtreport.aspx";
   private static String strUpPwdUrl = "http://www.stongnet.com/sdkhttp/uptpwd.aspx";
    //短信url参数
   private static String strSmsParam ;
   private static String strBalanceParam=""; //余额 参数
   
   private static String strSourceAdd = "";    //子通道号，可为空（预留参数一般为空）
   
   //第三方插件接口声明
   @Resource
	private PlThirdInterfaceLogService plThirdInterfaceLogService ;
   
   
   private String messageId ="";
   
	public String HXSmsManagerServerImp() throws UnsupportedEncodingException{
		
	//以下为所需的参数，测试时请修改,中文请先转为16进制再发送
//	String strReg = configMap.get("softwareSerialNo").toString();   //注册号（由华兴软通提供）
//    String strPwd =configMap.get("softwareSerialNoHx").toString();                 //密码（由华兴软通提供）
//    
	String strReg = "101100-WEB-JZWW-347585";   //注册号（由华兴软通提供）
    String strPwd ="OAVIVIYA";                 //密码（由华兴软通提供）
   
    String strTim = paraTo16("2012-2-16 12:00:00"); //定时发送时间
    String strPhone = "13391750223,18701657767";//手机号码，多个手机号用半角逗号分开，最多1000个
    String strContent = paraTo16("httpD试emo测ja va"+ (new Date()).toString());       //短信内容
    
    String strUname =  paraTo16("华兴"); //用户名，不可为空
    String strMobile = "13391750223";            //手机号，不可为空
    String strRegPhone = "01065688262";             //座机，不可为空
    String strFax = "01065685318";               //传真，不可为空
    String strEmail = "hxrt@stongnet.com";       //电子邮件，不可为空
    String strPostcode = "100080";               //邮编，不可为空
    String strCompany = paraTo16("华兴软通");    //公司名称，不可为空
    String strAddress = paraTo16("天阳ja");//公司地址，不可为空
    
    String strNewPwd = "BBBBBBBB";
   
    String strRegParam = "reg=" + strReg + "&pwd=" + strPwd + "&uname=" + strUname + "&mobile=" + strMobile + "&phone=" + strRegPhone + "&fax=" + strFax + 
                         "&email=" + strEmail + "&postcode=" + strPostcode + 
                         "&company=" + strCompany + "&address=" + strAddress;
 
    String strSchSmsParam = "reg=" + strReg + "&pwd=" + strPwd + "&sourceadd=" + strSourceAdd + "&tim=" + strTim + "&phone=" + strPhone + "&content=" + strContent;;
    String strStatusParam = "reg=" + strReg + "&pwd=" + strPwd;
    String strUpPwdParam = "reg=" + strReg + "&pwd=" + strPwd + "&newpwd=" + strNewPwd;
    //返回值
    String strRes ;
    
    //以下为HTTP接口主要方法，测试时请打开对应注释进行测试
    //注册
    //strRes = HXSmsManagerServer.postSend(strRegUrl, strRegParam);
    
    //查询余额
    strRes = postSend(strBalanceUrl, strBalanceParam);
    
    //发送短信
    strRes = postSend(configMap.get("smsUrl").toString(), strSmsParam);
    
    //定时短信
    //strRes = HXSmsManagerServer.postSend(strSchSmsUrl, strSchSmsParam);
    
    //状态报告
    //strRes = HXSmsManagerServer.postSend(strStatusUrl, strStatusParam);
    
    //修改密码
    //strRes = HXSmsManagerServer.postSend(strUpPwdUrl, strUpPwdParam);
    
   return strRes;
}
	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param content 内容
	 * @return
	 */
	@Override
	public  String sendMsg(String phone,String content){
		//未发送短信时，先保存一条发送日志
		messageId =	plThirdInterfaceLogService.saveLog1("", "", content, "华兴软通短信接口",null, PlThirdInterfaceLog.MEMTYPE1,
				PlThirdInterfaceLog.TYPE2, PlThirdInterfaceLog.TYPENAME2,"接收手机："+phone,"","","");		
		String relt="";
		try {
			setSmsParam(phone,content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
//		--*****relt=====result=0&message=短信发送成功&smsid=20140729100341609/
		Long sendTime = new Date().getTime();
		relt= postSend(configMap.get("smsUrl").toString(), strSmsParam);
		
		Long endTime = new Date().getTime();
		System.out.println("接口调用成功="+relt);
		String[] reltArr = relt.split("&");
		String[] resultArr = reltArr[0].split("=");
		if(resultArr.length>0){
			if(resultArr[1].equals("0")){
				updateLog("8888","发送成功","短信接口调用成功,发送短信："+content,"接收手机："+phone,"时间："+(endTime-sendTime)+"ms");
			}else{
				updateLog("0000","发送失败","短信接口调用失败,发送短信："+content,"接收手机："+phone,"时间："+(endTime-sendTime)+"ms");
			}
		}else{
				updateLog("0000","发送失败","短信接口调用失败,发送短信："+content,"接收手机："+phone,"时间："+(endTime-sendTime)+"ms");
		}
		return relt;
	}
	
  
//	/**
//	 * 查询余额方法
//	 * @return
//	 * @throws UnsupportedEncodingException 
//	 */
//	public static String getBalance() throws UnsupportedEncodingException{
//		String relt="";
//		setBalanceParam();
//		relt= HXSmsManagerServer.postSend(strBalanceUrl, strBalanceParam);
//		return relt;
//	}
//	
//	
//	public static String getSend(String strUrl,String param){
//		URL url = null;
//		HttpURLConnection connection = null;
//		
//		try {
//			url = new URL(strUrl + "?" + param);
//			connection = (HttpURLConnection) url.openConnection();
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setRequestMethod("GET");
//			connection.setUseCaches(false);
//			connection.connect();
//
//			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//			StringBuffer buffer = new StringBuffer();
//			String line = "";
//			while ((line = reader.readLine()) != null) {
//				buffer.append(line);
//			}
//		
//			reader.close();
//			return buffer.toString();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if (connection != null) {
//				connection.disconnect();
//				}
//		}
//	}
	/**
	 * 设置发送短信参数url
	 * @param strPhone
	 * @param content
	 * @throws UnsupportedEncodingException 
	 */
	private static void setSmsParam(String strPhone,String content) throws UnsupportedEncodingException{
	    String strContent = paraTo16(content);       //短信内容
	    String strReg=setRegAndPwd()[0];
	    String strPwd=setRegAndPwd()[1];
	    strSmsParam = "reg=" + strReg + "&pwd=" + strPwd + "&sourceadd=" + strSourceAdd + "&phone=" + strPhone + "&content=" + strContent;;
	    System.out.println("strSmsParam==="+strSmsParam);
	
	}
	/**
	 * 设置查询余额参数url
	 * @throws UnsupportedEncodingException 
	 */
	private static void setBalanceParam() throws UnsupportedEncodingException{
	    String strReg=setRegAndPwd()[0];
	    String strPwd=setRegAndPwd()[1];
	    strBalanceParam = "reg=" + strReg + "&pwd=" + strPwd;
	}
	/**
	 * 获取账号密码 公用
	 * @return
	 */
	private static String[] setRegAndPwd(){
		String[] str=new String[2];
		str[0]=configMap.get("smsAccountID").toString();
		str[1]=configMap.get("smsPassword").toString();
		return str;
	}
	
	/**
	 * 发送方法
	 * @param strUrl
	 * @param param
	 * @return
	 */
	  public  String postSend(String strUrl,String param){
	      
			URL url = null;
			HttpURLConnection connection = null;
			try {
				
				url = new URL(strUrl);
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.connect();
				
				//POST方法时使用
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
				out.writeBytes(param);
				out.flush();
				out.close();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				reader.close();
				System.out.println("buffer.toString------------------"+buffer.toString());
				return buffer.toString();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		}
	  
	  /**
		 * 调用更新日志
		 * @param respCode
		 * @param msgExt
		 * @param plain
		 * @param buyerName
		 * @param timeDifference
		 */
		public void updateLog(String respCode,String msgExt,String plain,String buyerName,String timeDifference){
			plThirdInterfaceLogService.updateLog(respCode, msgExt, plain, "华兴软通短信接口",null, PlThirdInterfaceLog.MEMTYPE1,
					PlThirdInterfaceLog.TYPE2, PlThirdInterfaceLog.TYPENAME2,buyerName,timeDifference,"","",messageId);
		}
		
		
	  /**
		 * 转为16进制方法
		 * @param str
		 * @return
		 * @throws UnsupportedEncodingException 
		 */
		public static String paraTo16(String str) throws UnsupportedEncodingException {
				String hs = "";
				
				byte[] byStr = str.getBytes("UTF-8");
				for(int i=0;i<byStr.length;i++)	{
					String temp = "";
					temp = (Integer.toHexString(byStr[i]&0xFF));
					if(temp.length()==1) temp = "%0"+temp;
					else temp = "%"+temp;
					hs = hs+temp;
				}
				return hs.toUpperCase();
		}
	 
}
