package com.zhiwei.credit.service.sms.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.thirdInterface.PlThirdInterfaceLogDao;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.service.sms.MessageStrategyService;
import com.zhiwei.credit.service.sms.dao.DirectSendDTO;
import com.zhiwei.credit.service.sms.util.DomTest;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;



/**
 * svn:songwj
 */
public class SXTMessageServiceImpl implements MessageStrategyService {
	
	@SuppressWarnings("unused")
	private PlThirdInterfaceLogDao dao;
	
	private static Log logger = LogFactory.getLog(SXTMessageServiceImpl.class);

	//得到config.properties读取的所有资源
	@SuppressWarnings("unchecked")
	private static Map configMap = AppUtil.getConfigMap();
	//code未使用
	private String phone;
	@Resource
	private PlThirdInterfaceLogService plThirdInterfaceLogService ;
	private String projType;
	private String url;
	private String userID;
	private String accountID;
	private String password;
	
	private String messageId;
	
	/**
	 * 商讯通短信发送发送方法
	 * phone:接收信息的手机号
	 * content:手机接收信息的内容
	 */
	@Override
	public String sendMsg(String phone,String content) {
		
		//未发送短信时，先保存一条发送日志
		messageId =	plThirdInterfaceLogService.saveLog1("", "", content, "商信通短信接口",null, PlThirdInterfaceLog.MEMTYPE1,
				PlThirdInterfaceLog.TYPE2, PlThirdInterfaceLog.TYPENAME2,"接收手机："+phone,"","","");
//		
		this.phone = phone;
		projType = AppUtil.getProjStr();//获得项目名称

		if(null!=projType&&!"".equals(projType)){
			url = configMap.get("smsUrl").toString();//获得短信发送连接的地址
			userID = configMap.get("smsUserID").toString();//
			accountID = configMap.get("smsAccountID").toString();//连接短信供应商的账号
			password = configMap.get("smsPassword").toString();//连接短信供应商的密码
			
			String backMessageStr ="";
		backMessageStr= directSend(url,parseDTO(phone,content));
		System.out.println("backMessageStr===="+backMessageStr);
		if(backMessageStr.equals("Sucess")){
			
			backMessageStr = "Sucess";
		}else{
			backMessageStr = "Failure";
		}
		return  backMessageStr;
		}else{
			return null;
		}
	}
	
	//为发送的短信获取参数
	public DirectSendDTO parseDTO(String phone,String content){
		DirectSendDTO directSendDTO = new DirectSendDTO();
		directSendDTO.setUserID(userID);
		directSendDTO.setAccountID(accountID);
		directSendDTO.setPassword(password);
		
		directSendDTO.setPhones(phone);
		directSendDTO.setContent(content);
		directSendDTO.setSendType("1");
		return directSendDTO;
	}
	
	// 调用接口DirectGetStockDetails
	public String directGetStockDetails(String url) {
		return excute(url);
	}

	// 调用接口DirectFetchSMS.
	public String directFetchSMS(String url) {
		return excute(url);
	}

	// 调用接口DirectSend,没有参数为中文的url时可调用如下方法.
	public String excute(String url) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		try {
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "GBK");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			int beginPoint = str.indexOf("<RetCode>");
			int endPoint = str.indexOf("</RetCode>");
			String result = "RetCode=";
			return result + str.substring(beginPoint + 9, endPoint);
		} catch (HttpException e) {
			return "1";
		} catch (IOException e) {
			return "2";
		}
		finally {
			getMethod.releaseConnection();
		}
	}

	// 调用接口DirectSend,对于参数为中文的调用采用以下方法,为防止中文参数为乱码.
	public String directSend(String url, DirectSendDTO directSendDTO) {
		// String response = "";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		PostMethod getMethod = new UTF8PostMethod(url);
		
		NameValuePair[] data = {
				new NameValuePair("UserID", directSendDTO.getUserID()),
				new NameValuePair("Account", directSendDTO.getAccountID()),
				new NameValuePair("Password", directSendDTO.getPassword()),
				new NameValuePair("Phones", directSendDTO.getPhones()),
				new NameValuePair("SendType", directSendDTO.getSendType()),
				new NameValuePair("SendTime", directSendDTO.getSendTime()),
				new NameValuePair("PostFixNumber", directSendDTO
						.getPostFixNumber()),
				new NameValuePair("Content", directSendDTO.getContent()) };
		getMethod.setRequestBody(data);
		getMethod.addRequestHeader("Connection", "close");
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Long sendTime = new Date().getTime();
			logger.info("发送时间："+sdf.format(new Date())+","+directSendDTO.getContent()+" ,短信发送.....!");
			
			int statusCode = httpClient.executeMethod(getMethod);
		 
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "GBK");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			DomTest dom=new DomTest();
			String retCodeValue="";
			try {
				retCodeValue=dom.readXMLString4TagName(str, "RetCode");
			} catch (Exception e) {
				e.printStackTrace();
			}
	 
			//打印短信接口对接结果
			Long endTime = new Date().getTime();
			logger.info("返回时间："+sdf.format(new Date())+" ,手机号："+phone+" ,"+directSendDTO.getContent()+" ,短信发送成功!"+retCodeValue);

			if(null==retCodeValue){
				updateLog("0000","发送失败","短信接口调用失败,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间："+(endTime-sendTime)+"ms");
			}else if("Sucess".equals(retCodeValue)){
				updateLog("8888","发送成功","短信接口调用成功,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间："+(endTime-sendTime)+"ms");
			}else{
				updateLog("0000","发送失败","短信接口调用失败,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间："+(endTime-sendTime)+"ms");
			}
			return retCodeValue;
		} catch (HttpException e) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			logger.info("时间："+sdf.format(new Date())+" ,手机号："+phone+" ,"+directSendDTO.getContent()+" ,短信发送失败!");
			//logger.info("时间："+sdf.format(new Date())+" ,手机号："+phone+" ,验证码："+code+" ,短信发送失败!");
			updateLog("0000","发送失败","短信接口调用失败,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间：0ms");
			return "1";
		} catch (IOException e) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			logger.info("时间："+sdf.format(new Date())+" ,手机号："+phone+" ,"+directSendDTO.getContent()+" ,短信发送失败!");

		//	logger.info("时间："+sdf.format(new Date())+" ,手机号："+phone+" ,验证码："+code+" ,短信发送失败!");
			updateLog("0000","发送失败","短信接口调用失败,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间：0ms");
			return "2";
		} finally {
			getMethod.releaseConnection();
		}
	}

	public static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			return "UTF-8";
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
		plThirdInterfaceLogService.updateLog(respCode, msgExt, plain, "商信通短信接口",null, PlThirdInterfaceLog.MEMTYPE1,
				PlThirdInterfaceLog.TYPE2, PlThirdInterfaceLog.TYPENAME2,buyerName,timeDifference,"","",messageId);
	}
}
