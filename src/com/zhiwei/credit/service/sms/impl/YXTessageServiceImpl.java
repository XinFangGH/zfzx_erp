package com.zhiwei.credit.service.sms.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.service.sms.MessageStrategyService;

/**
 * 一信通
 * @author XiRuiJie
 */
public class YXTessageServiceImpl implements MessageStrategyService {
	
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	private static HttpClient httpclient = new HttpClient();
	
	@Override
	public String sendMsg(String phone, String content) {
		/*****************************************一信通--短信发送接口*********************************************/
		String[] ret = new String[2];
		String info = null;
		ret = judge(phone);
		if(ret[0].equals("8888")){
			 try {
				 String  SpCode = configMap.get("smsSpCode").toString();//获得企业编号
			     String  LoginName = configMap.get("smsAccountID").toString().trim();//获得登陆名称
			     String  Password = configMap.get("smsPassword").toString().trim();//获得登陆密码
			     String  SendUrl = configMap.get("smsUrl").toString().trim();//获得登陆密码
			     java.net.URLEncoder.encode(SendUrl,"utf-8");
			     PostMethod post = new PostMethod(SendUrl);
			 
				 post.getParams().setParameter("http.protocol.content-charset", "gbk");
			     post.addParameter("SpCode",SpCode);
			     post.addParameter("LoginName",LoginName);
			     post.addParameter("Password",Password);
			     post.addParameter("MessageContent", content);
			     post.addParameter("UserNumber", ret[1]);
			     post.addParameter("SerialNumber",getSerialNumber());
			     post.addParameter("ScheduleTime", getScheduleTime());
			     post.addParameter("ExtendAccessNum", "");
			     post.addParameter("f", "1");
	//		     System.out.println("#################################");
	//		     System.out.println("一信通|| 企业编号=="+SpCode+" ||登陆账号=="+LoginName+" ||登陆密码=="+Password+" ||接收短信的手机号=="+phone+" ||发送内容=="+content);
		    	 httpclient.executeMethod(post);
		    	 info = new String(post.getResponseBody(), "gbk");
		     } catch (Exception e) {
		    	e.printStackTrace();
		     }  
//		     System.out.println("一信通返回结果==="+info);
		}else{
		    System.out.println("#################################");
			System.out.println(ret[1]);
		}
	    return info;
	}
	
	/**
	 * 获得接收手机的号码
	 * @param telphone
	 * @return
	 */
	public String[] judge(String telphone){
		String[] ret = new String[2];
		if(telphone!=null && !telphone.equals("")){
			int  i =telphone.length();
			String aa =telphone.substring(i-1, i)  ;
			 if(aa.equals(",") ){
				 ret[0] ="8888";
				 ret[1] = telphone.substring(0, i-1);
			 }else{
				 ret[0] ="8888";
				 ret[1]  =telphone;
			 }
		}else{
			 ret[0] ="0000";
			 ret[1]="没有输入接收短信的电话号码";
		}
		return ret;
	}
	/**
	 * 获得预约发送时间
	 * @return
	 */
	public String getScheduleTime(){
		Date d= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(d).toString();
	}
	/**
	 * 获得流水号
	 * @return
	 */
	public String getSerialNumber(){
		Date d= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(d).toString();
	}

}
