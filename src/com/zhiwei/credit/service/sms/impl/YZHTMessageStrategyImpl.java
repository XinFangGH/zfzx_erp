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
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;
import com.zhiwei.credit.service.thirdInterface.impl.PlThirdInterfaceLogServiceImpl;
import com.zhiwei.credit.util.MD5;

/**
 * 宇展盈讯
 * svn:songwj
 */
public class YZHTMessageStrategyImpl implements MessageStrategyService {
	private PlThirdInterfaceLogDao dao ;
	private static Log logger = LogFactory.getLog(YZHTMessageStrategyImpl.class);
	
	@Resource
	PlThirdInterfaceLogService plThirdInterfaceLogService = new PlThirdInterfaceLogServiceImpl(dao);
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	private String projType;
	private String url;
	private String userID;
	private String accountID;
	private String password;
	
	private String messageId="";
	/**
	 * 宇展盈讯短信发送
	 */
	@Override
	public String sendMsg(String phone,String content) {
		
		String[] strArr=new String[2];
		
		//未发送短信时，先保存一条发送日志
		messageId =	plThirdInterfaceLogService.saveLog1("", "", content, "宇展盈讯短信接口",null, PlThirdInterfaceLog.MEMTYPE1,
				PlThirdInterfaceLog.TYPE2, PlThirdInterfaceLog.TYPENAME2,"接收手机："+phone,"","","");
		
		projType = AppUtil.getProjStr();
		if(null!=projType&&!"".equals(projType)){
			
			url = configMap.get("smsUrl").toString();
			userID = configMap.get("smsUserID").toString();
			accountID = configMap.get("smsAccountID").toString();
			password = configMap.get("smsPassword").toString();
			
			String retValue = directSend(url,parseDTO(phone,content));
			logger.info("["+new Date()+"]发送短信："+content);
			if(retValue!=null&&retValue.length()>0){
				if(retValue.indexOf("\r")==-1){
					if(retValue.equals("209")){
						logger.info("短信服务商返回信息："+retValue+"。----余额不足");
					}else{
						logger.info("短信服务商返回信息："+retValue+"。");
					}
					return null;
				}else{
					logger.info("短信服务商返回信息："+retValue+"。");
					String retv = retValue.substring(0, retValue.indexOf("\r"));
					if(retv.equals("111")){
						return "Sucess";
					}
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
		return directSend("",parseDTO(phone,content));
	}
	//获取参数
	public DirectSendDTO parseDTO(String phone,String content){
		DirectSendDTO directSendDTO = new DirectSendDTO();
		directSendDTO.setAccountID(accountID);
		MD5 md5 = new MD5();
		directSendDTO.setPassword(md5.md5(password, "UTF-8"));
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
		// String responseValue;
		PostMethod getMethod = new UTF8PostMethod(url);
		NameValuePair[] data = {
				new NameValuePair("account", directSendDTO.getAccountID()),
				new NameValuePair("password", directSendDTO.getPassword()),
				new NameValuePair("mobile", directSendDTO.getPhones()),
				new NameValuePair("content", directSendDTO.getContent()) };
		getMethod.setRequestBody(data);
		getMethod.addRequestHeader("Connection", "close");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Long sendTime = new Date().getTime();
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
			Long endTime = new Date().getTime();
			logger.info("发送时间："+sdf.format(new Date())+" ,"+directSendDTO.getContent()+" ,短信发送.....!");
			
			if(null==str){
				updateLog("0000","发送失败","短信接口调用失败,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间："+(endTime-sendTime)+"ms");
			
			}else if("111".equals(str.substring(0, 3))){
				updateLog("8888","发送成功","短信接口调用成功,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间："+(endTime-sendTime)+"ms");
			}else{
				updateLog("0000","发送失败","短信接口调用失败,发送短信："+directSendDTO.getContent(),"接收手机："+directSendDTO.getPhones(),"时间："+(endTime-sendTime)+"ms");
			}
			//logger.trace("[彩云金融短信验证]时间："+sdf.format(new Date())+" ,手机号："+phone+" ,验证码："+code+" ,短信发送成功!");
			if(str.equals("111")){
				return "Success";
				
//				strArr[0]=Constants.CODE_SUCCESS;
//				strArr[1]="短信发送成功！";
			}else{
				return str;
			}
			
		} catch (HttpException e) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//logger.trace("[彩云金融短信验证]时间："+sdf.format(new Date())+" ,手机号："+phone+" ,验证码："+code+" ,短信发送失败!");
			return "1";
		} catch (IOException e) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//	logger.trace("[彩云金融短信验证]时间："+sdf.format(new Date())+" ,手机号："+phone+" ,验证码："+code+" ,短信发送失败!");
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
	 *保存日志 
	 * @param respCode
	 * @param msgExt
	 * @param plain
	 * @param buyerName
	 * @param timeDifference
	 */
	public void updateLog(String respCode,String msgExt,String plain,String buyerName,String timeDifference){
		
		plThirdInterfaceLogService.updateLog(respCode, msgExt, plain, "宇展盈讯短信接口",null, PlThirdInterfaceLog.MEMTYPE1,
				PlThirdInterfaceLog.TYPE2, PlThirdInterfaceLog.TYPENAME2,buyerName,timeDifference,"","",messageId);
	}

}
