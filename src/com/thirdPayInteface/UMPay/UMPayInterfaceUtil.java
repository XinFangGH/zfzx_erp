package com.thirdPayInteface.UMPay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;

import com.thirdPayInteface.CommonRequst;
import com.thirdPayInteface.CommonResponse;
import com.thirdPayInteface.ThirdPayConstants;
import com.thirdPayInteface.ThirdPayWebClient;

import com.thirdPayInteface.UMPay.UMPayUtil.PkCertFactory;
import com.thirdPayInteface.UMPay.UMPayUtil.SignUtil;
import com.thirdPayInteface.UMPay.UMPayUtil.SunBase64;

public class UMPayInterfaceUtil {

	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	protected static Log logger=LogFactory.getLog(UMPayInterfaceUtil.class);
	public static final Map<String,String> requestValue=new HashMap<String,String>();
	/**
	 * 准备UMP公共支付数据
	 * @param umpay
	 * @return
	 */
	private static UMPay generalPublicDate(UMPay umpay,Boolean notifyUrl,Boolean pageUrl,String bussinessType) {
		umpay.setVersion("1.0");
		umpay.setCharset(UMPay.UTF8);
		umpay.setRes_format("HTML");
		umpay.setSign_type("RSA");
		umpay.setMer_id(configMap.get("thirdPay_umpay_MemberID").toString());
		String BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
		if(notifyUrl){
			umpay.setNotify_url(BasePath+configMap.get("thirdPay_umpay_notifyUrl").toString()+"?HRBT="+bussinessType);
		}
		if(pageUrl){
			umpay.setRet_url(BasePath+configMap.get("thirdPay_umpay_callbackUrl").toString()+"?HRBT="+bussinessType);
		}
		return umpay;
	}
	/**
	 * 
	 * <br>description : 用联动公钥加密
	 * @param s
	 * @return
	 * @throws Exception
	 * @version     1.0
	 * @date    2014-11-11
	 */
	public static String Encrypt(String s,String charset)throws Exception{
		X509Certificate cert = PkCertFactory.getCert();
	    byte[] keyBytes = cert.getPublicKey().getEncoded();
	    // 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		String str = SunBase64.encode(cipher.doFinal(s.getBytes(charset))).replace("\n", "");
		return str;
	}
	/**
	 * 获取需要拼接成HTML页面需要的参数map列表
	 * @param umpay
	 * @return
	 */
	private static Map<String, String> createHtmlMap(UMPay umpay) {
		Map map = new HashMap();;
		try{
			if(umpay!=null){
				Class type = umpay.getClass();
		        BeanInfo beanInfo = Introspector.getBeanInfo(type);
		        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
		        for(int i=0;i<propertyDescriptors.length;i++){
		        	 PropertyDescriptor descriptor=propertyDescriptors[i];
		        	 String propertyName = descriptor.getName();
		             if (!propertyName.equals("class")) {
		                 Method readMethod = descriptor.getReadMethod();
		                 Object result = readMethod.invoke(umpay, new Object[0]);
		                 if (result != null&&!"".equals(result)){
		                	 map.put(propertyName, result);
		                 }
		             }
		        }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据项目标对象获取签名对象以及页面参数列表map对象
	 * @param umpay
	 * @return
	 */
	public static Map<String,String> creatDataMap(UMPay umpay){
		Map returnMap = new HashMap();
		try{
			if(umpay!=null){
				Map map = UMPayInterfaceUtil.createHtmlMap(umpay);
		        if(!map.isEmpty()){
		        	Object[] obj = map.keySet().toArray();
					Arrays.sort(obj);
					String plain,sign,str,value,valueEncoder= null;
					StringBuffer plainString = new StringBuffer();
					StringBuffer signString = new StringBuffer();
					for(int i = 0 ; i < obj.length ; i++){
						str = obj[i].toString();
						value = map.get(str).toString();
						System.out.println("str=="+str+"    value=="+value);
						if(value==null||"".equals(value)){
							continue;
						}
						if("sign_type".equals(str)){
							value=URLEncoder.encode(value,"UTF-8");
							plainString.append(str).append("=").append(value).append("&");
						}else{
							valueEncoder =URLEncoder.encode(value,"UTF-8");
							plainString.append(str).append("=").append(valueEncoder).append("&");
							signString.append(str).append("=").append(value).append("&");
						}
					}
					plain = plainString.subSequence(0, plainString.length()-1).toString();
					sign = signString.substring(0,signString.length()-1).toString();
					System.out.println("请求数据获得的签名明文串为signString: "+sign);
					logger.info("请求数据获得的签名明文串为："+ sign);
					String merId =umpay.getMer_id().trim();
					sign = SignUtil.sign(sign,merId.trim());
					System.out.println("请求数据获得的签名串为sign: "+sign);
					logger.info("请求数据获得的签名串为：" + sign);
					plain=plain+"&sign="+URLEncoder.encode(sign,"UTF-8");
					returnMap.put("plain", plain);
					returnMap.put("sign", sign);
		        }
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return returnMap;
	}
	/**
	 * 用来解析html方法
	 * @param html
	 * @return
	 */
	public static Map<String,String> parseHTMLMethod(String html){
		try{
			String returnDate="";
			if(html.contains("&reg")){
				html=html.replaceAll("&reg", "&changeO");
			}
			Document document=Jsoup.parse(html);
			if(document!=null){
				Element head=document.head();
			    System.out.println("head=="+head);
			    
			    List<Node> list=head.childNodes();
			    if(list!=null){
			    	 for(Node temp:list){
			    		 System.out.println("temp=="+temp.attr("name"));
			    		 String metaName=temp.attr("name");
			    		 String content=temp.attr("content");
			    		 if(content.contains("&changeO")){
			    			 content=content.replaceFirst("&changeO", "&reg");
							}
			    		 if(metaName!=null&&!"".equals(metaName)&&metaName.equals("MobilePayPlatform")){
			    			 returnDate= content;
			    			 break;
			    		 }
			    	 } 
			    }
			}
			if(!"".equals(returnDate)){
				logger.info("解析成功，content"+returnDate);
				Map<String,String> map=new HashMap<String,String>();
				String[] ret=returnDate.split("&");
				if(ret!=null&&ret.length>0){
					for(String strr:ret){
						logger.info("解析成功，往mapput数据"+strr);
						map.put(strr.split("=",2)[0], strr.split("=",2)[1]);
					}
					logger.info("解析成功，生成了map："+map);
					return map;
				}else{
					logger.info("解析失败，content数据split分解出错："+ret.length);
					return null;
				}
			}else{
				logger.info("没有获取解析的content数据");
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("解析HTML字符串出错,原因"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 验证收到签名
	 * @param map
	 * @return
	 */
	public static Boolean verifySign(Map<String,String> map){
		Boolean signValue=false;
		try{
			Object[] obj = map.keySet().toArray();
			Arrays.sort(obj);
			String plain,sign,str,value,valueEncoder= null;
			StringBuffer signString = new StringBuffer();
			for(int i = 0 ; i < obj.length ; i++){
				str = obj[i].toString();
				value = map.get(str).toString();
				System.out.println("str=="+str+"    value=="+value);
				if(value==null||"".equals(value)){
					continue;
				}
				if("sign".contains(str)||"sign_type".contains(str)){
				}else{
					signString.append(str).append("=").append(value).append("&");
				}
			}
			sign = signString.substring(0,signString.length()-1).toString();
			System.out.println("请接收参数获得的签名明文串为signString: "+sign);
			System.out.println("接收到的签名sign: "+map.get("sign").toString());
			signValue = SignUtil.verify(map.get("sign").toString(), sign);
			System.out.println("验证签名结果: "+signValue);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("验证签名方法报错,原因"+e.getMessage());
		}
		return signValue;
		
	}
	/**
	 * 接口输出对第三方回调通知已经正确接收的返回html
	 * @param map
	 * @return
	 */
	public static String createReaponseHTML() {
		UMPay umpay=new UMPay();
		umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,null);
		umpay.setCharset(null);
		umpay.setRet_code("0000");
		Map<String,String> map=UMPayInterfaceUtil.creatDataMap(umpay);
		if(!map.isEmpty()&&map.containsKey("plain")){
			String html="<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>"
				+"<html>"
				+"<head>"
				+"<META NAME='MobilePayPlatform' CONTENT="+map.get("plain").toString()+" />"
				+"</head>"	
				+"<body>"
				+"</body>"
				+"</html>";
			return html;
		}else{
			return null;
		}
	}
    //######################################网关接口（开始）#############################################
	/**
	 * 网关接口01
	 * 联动优势支付个人客户充值接口
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("requestNo",String);//充值交易记录流水号
	 * map.put("orderDate",String);//生成订单的日期
	 * map.put("thirdPayflagId",String);//充值客户的联动用户号
	 * map.put("thirdPayflagId0",String);//充值客户的联动账户号
	 * map.put("amount",BigDecimal);//充值用户充值金额
	 * map.put("bankCode",String);//充值选择的银行代码
	 * @param map
	 * @return
	 */
	public static Object[] recharge(CommonRequst commonRequst) {
		Object[] backData=new Object[3];
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付个人有密码充值接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_RECHARGE);
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			umpay.setMer_date(DateUtil.dateToStr(new Date(),"yyyyMMdd"));
			//默认是个人网银支付
			umpay.setPay_type(UMPay.BANK_PERSON);
			//读取联动给注册用户开的用户账号
			umpay.setUser_id(commonRequst.getThirdPayConfigId());
			//读取联动给注册用户开的资金账户
			umpay.setAccount_id(commonRequst.getThirdPayConfigId0());
			//读取需要充值的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setAmount(amount.toString());
			//读取进行充值的银行卡
			umpay.setGate_id(commonRequst.getBankCode());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付个人有密码充值接口调用生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付个人有密码充值接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付个人有密码充值接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			backData[0]=ThirdPayConstants.RECOD_SUCCESS;
			backData[1]=ret[1];
			backData[2]=commonRequst;
		}catch(Exception e){
			logger.info("联动优势支付充值接口调用出错,原因:"+e.getMessage());
			e.printStackTrace();
			backData[0]=ThirdPayConstants.RECOD_FAILD;
			backData[1]="联动优势支付充值接口调用出错";
			backData[2]=commonRequst;
		}
		return backData;
	}

	/**
	 * 网关接口02
	 * 联动优势支付取现接口
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("bathpath",String);//当前系统访问地址
	 * map.put("requestNo",String);//取现交易记录流水号
	 * map.put("orderDate",String);//生成取现订单的日期
	 * map.put("thirdPayflagId",String);//取现客户的联动用户号
	 * map.put("thirdPayflagId0",String);//取现客户的联动账户号
	 * map.put("amount",BigDecimal);//取现用户取现金额
	 * @param map
	 * @return
	 */
	public static Object[] toWithdraw(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付个人有密码取现接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_WITHDRAWRECHARGE);
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			String merDate=sf.format(commonRequst.getTransactionTime());
			umpay.setMer_date(merDate);
			//读取联动给注册用户开的用户账号
			umpay.setUser_id(commonRequst.getThirdPayConfigId());
			//读取联动给注册用户开的资金账户
			umpay.setAccount_id(commonRequst.getThirdPayConfigId0());
			//读取需要充值的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setAmount(amount.toString());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付个人有密码取现接口调用生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付个人有密码取现接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付个人有密码取现接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			backData[0]=ret[0];
			backData[1]=ret[1];
			backData[2]=commonRequst;
			return backData;
		}catch(Exception e){
			logger.info("联动优势支付个人有密码取现接口调用出错,原因:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 网关接口03
	 * 联动优势支付绑定银行卡接口
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("bathpath",String);//当前系统访问地址
	 * map.put("requestNo",String);//绑卡记录流水号
	 * map.put("orderDate",String);//生成绑卡记录的日期
	 * map.put("thirdPayflagId",String);//绑卡客户的联动用户号
	 * map.put("cardCode",String);//绑卡客户的身份证号码
	 * map.put("custmerName",BigDecimal);//绑卡用户姓名
	 * map.put("bankCardNumber",BigDecimal);//绑卡用户银行卡号
	 * @param map
	 * @return
	 */
	public static Object[] toBindBankCard(CommonRequst commonRequst) {
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付个人客户银行卡绑定接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_BINDCARD);
			//umpay.setNotify_url(map.get("bathpath").toString()+configMap.get("thirdPay_umpay_notifyUrl").toString());
			//umpay.setRet_url(map.get("bathpath").toString()+configMap.get("thirdPay_umpay_pageUrl").toString());
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			umpay.setMer_date(format.format(commonRequst.getTransactionTime()));
			//读取联动给注册用户开的用户账号
			umpay.setUser_id(commonRequst.getThirdPayConfigId());
			//进行绑卡人选择证件类型，默认身份证
			umpay.setIdentity_type(UMPay.IDENTITY_CARD);
			//进行绑卡人的身份证号码
			String cardCode=UMPayInterfaceUtil.Encrypt(commonRequst.getCardNumber(),UMPay.GBK);
			umpay.setIdentity_code(cardCode);
			//进行绑卡人的银行卡开户名
			String userName=UMPayInterfaceUtil.Encrypt(commonRequst.getTrueName(),UMPay.GBK);
			umpay.setAccount_name(userName);
			//进行绑卡的银行卡卡号
			String bankCardNumber=UMPayInterfaceUtil.Encrypt(commonRequst.getBankCardNumber(),UMPay.GBK);
			umpay.setCard_id(bankCardNumber);
			//是否开启快捷支付（默认不开启页面也不显示）
			umpay.setIs_open_fastPayment(UMPay.FASTPAYMENT_NO);
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付个人客户银行卡绑定接口调用生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付个人客户银行卡绑定接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付个人客户银行卡绑定接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			backData[0]=ret[0];
			backData[1]=ret[1];
			backData[2]=commonRequst;
			return backData;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付个人客户银行卡绑定接口调用出错,原因:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 网关接口04
	 * 联动优势支付无密交易授权接口
	 * 涉及（投标授权，还款授权，还有无密码快捷交易即指）
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("bathPath",String);//当前系统访问地址
	 * map.put("thirdPayflagId",String);//做授权的第三方支付用户号
	 * map.put("thirdPayflagId0",String);//做授权的第三方支付账号
	 * map.put("autoAuthorizationType",String);//做授权的交易类型  invest  自动投标授权     repayment 还款授权    nopassword 无密充值授权
	 * @param map
	 * @return
	 */
	public static Object[] autoAuthorization(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付无密交易授权接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_NOPASSWORD);
			//umpay.setNotify_url(map.get("bathPath").toString()+configMap.get("thirdPay_umpay_notifyUrl").toString());
			//umpay.setRet_url(map.get("bathPath").toString()+configMap.get("thirdPay_umpay_pageUrl").toString());
			//读取联动给注册用户开的用户账号
			umpay.setUser_id(commonRequst.getThirdPayConfigId());
			//读取联动给注册用户开的资金账户
			//umpay.setAccount_id(map.get("thirdPayflagId0").toString());
			//自动授权类型
			String autoAuthorizationType=commonRequst.getAutoAuthorizationType();
			if(autoAuthorizationType.equals("invest")){//投标授权
				umpay.setUser_bind_agreement_list(UMPay.NO_PASSWORD_INVST);
			}else if(autoAuthorizationType.equals("repayment")){//无密还款授权
				umpay.setUser_bind_agreement_list(UMPay.NO_PASSWORD_REPAYMENT);
			}else if(autoAuthorizationType.equals("nopassword")){
				umpay.setUser_bind_agreement_list(UMPay.NO_PASSWORD_FASTPAYMENT);
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付无密交易授权接口调用生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			System.out.println("ret[1]=="+ret[1]);
			logger.info("联动优势支付无密交易授权接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付无密交易授权接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			backData[0]=ThirdPayConstants.RECOD_SUCCESS;
			backData[1]=ret[1];
			backData[2]=commonRequst;
			return backData;
		}catch(Exception e){
			logger.info("联动优势支付无密交易授权接口个人客户银行卡绑定接口调用出错,原因:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 网关接口05
	 * 联动优势支付取消无密交易授权接口
	 * 涉及（投标授权，还款授权，还有无密码快捷交易即指）
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("bathPath",String);//当前系统访问地址
	 * map.put("thirdPayflagId",String);//做授权的第三方支付用户号
	 * map.put("thirdPayflagId0",String);//做授权的第三方支付账号
	 * map.put("autoAuthorizationType",String);//做授权的交易类型  invest  自动投标授权     repayment 还款授权    nopassword 无密充值授权
	 * @param map
	 * @return
	 */
	public static Object[] cancelAuthorization(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付取消无密交易授权接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_CANCELNOPASSWORD);
			//读取联动给注册用户开的用户账号
			umpay.setUser_id(commonRequst.getThirdPayConfigId());
			//读取联动给注册用户开的资金账户
			//umpay.setAccount_id(map.get("thirdPayflagId0").toString());
			//自动授权类型
			String autoAuthorizationType=commonRequst.getAutoAuthorizationType();
			if(autoAuthorizationType.equals("invest")){
				umpay.setUser_unbind_agreement_list(UMPay.NO_PASSWORD_INVST);
			}else if(autoAuthorizationType.equals("repayment")){
				umpay.setUser_unbind_agreement_list(UMPay.NO_PASSWORD_REPAYMENT);
			}else if(autoAuthorizationType.equals("nopassword")){
				umpay.setUser_unbind_agreement_list(UMPay.NO_PASSWORD_FASTPAYMENT);
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付取消无密交易授权接口调用生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付取消无密交易授权接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付取消无密交易授权接口无密交易授权接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			backData[0]=ret[0];
			backData[1]=ret[1];
			backData[2]=commonRequst;
			return backData;
		}catch(Exception e){
			logger.info("联动优势支付取消无密交易授权接口调用出错,原因:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 网关接口06
	 * 联动优势支付非投标类有密码转账接口
	 * 涉及（个人向平台转账）
	 * @param map
	 * @return
	 */
	public static Object[] normalTransferInterface(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付非投标类有密码转账接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_NOMALTRANSFER);
			//读取联动给注册用户开的用户账号
			umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
			//读取转账客户类型
			umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
			//读取需要转账的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setAmount(amount.toString());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付非投标类有密码转账接口生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付非投标类有密码转账接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付非投标类有密码转账接口调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			backData[0] =ret[0];
			backData[1] =ret[1];
			backData[2] =commonRequst;
			return backData;
		}catch(Exception e){
			logger.info("联动优势支付非投标类有密码转账接口调用出错,原因:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 网关接口07
	 * 联动优势支付投标类有密码转账接口
	 * 涉及（涉及投资人投资，借款人还款）
     * Map<String,object> map  第三方支付投标需要的map参数
	 * map.put("basePath",String) 只当前的绝对路径
	 * map.put("requestNo",string)交易流水号
	 * map.put("orderDate",String)生成交易订单日期
	 * map.put("amount",BigDecimal) 投标金额
	 * map.put("bidPlanId",Long) 标的id
	 * map.put("bidType",String) 标的类型
	 * map.put("thirdPayflagId",String)转账人的联动优势支付开户账号
	 * map.put("transferUserType",String)转账方账户类型
	 * map.put("businessType",String)转账的类型属于投资还是还款
	 * @param map
	 * @return
	 */
	public static Object[] transferInterface(CommonRequst commonRequst) {

		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
			logger.info("联动优势支付投标类有密码转账接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_TRANSFER);
			
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			umpay.setMer_date(sdf.format(commonRequst.getTransactionTime()));
			
			//发标时间标的主键Id和标的标识
			umpay.setProject_id(/*map.get("bidType").toString()+"-"+*/commonRequst.getBidId());
			//业务类型
			String bussinessType="";
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_BID)){
				bussinessType="01";
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_LOANERREPAYMENT)){
				bussinessType="03";
			}
			umpay.setServ_type(bussinessType);
			//转账方类型（取值范围：01投资者02融资人03 P2P平台04担保方05资金使用方）
			umpay.setPartic_type(commonRequst.getCustMemberType());
			//标的转入
			umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
			//转账方账户类型
			umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
			
			//读取联动给注册用户开的用户账号
			umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
			//读取需要充值的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setAmount(amount.toString());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付投标类有密码转账调用生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付支付投标类有密码转账接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付支付投标类有密码转账接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			System.out.println("ret[1]===="+ret[1]);
			backData[0]=ret[0];
			backData[1]=ret[1];
			backData[2]=commonRequst;
			return backData;
		}catch(Exception e){
			logger.info("联动优势支付支付投标类有密码转账接口调用出错,原因:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	
	}

	//######################################网关接口（结束）#############################################
	
	//######################################直连接口（开始）#############################################
	
	
	/**
	 * 直连01
	 * 联动优势支付注册接口
	 * @param map
	 * @return
	 */
	public static Object[] regiest(CommonRequst commonRequst) {
		try{
			logger.info("联动优势支付注册接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_REGIST);
			umpay.setIdentity_type(UMPay.IDENTITY_CARD);
			umpay.setEmail(commonRequst.getEmail());
			umpay.setMobile_id(commonRequst.getTelephone());
			String cardCode=UMPayInterfaceUtil.Encrypt(commonRequst.getCardNumber(),UMPay.GBK);
			umpay.setIdentity_code(cardCode);
			String userName=UMPayInterfaceUtil.Encrypt(commonRequst.getTrueName(),UMPay.GBK);
			umpay.setMer_cust_name(userName);
			umpay.setMer_cust_id("TG"+commonRequst.getCardNumber()+commonRequst.getCustMemberId());
			System.out.println(umpay.getMer_cust_id());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付注册接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付注册接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付注册接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付注册接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付注册接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;
					commonRequst.setThirdPayConfig(ThirdPayConstants.UMPAY);
					commonRequst.setThirdPayConfigId(htmlReturnMap.get("user_id").toString());
					commonRequst.setThirdPayConfigId0(htmlReturnMap.get("account_id").toString());
					backData[1]="注册成功";
					backData[2]=commonRequst;
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=null;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;
				backData[1]="签名验证失败";
				backData[2]=null;
			}
			return backData;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付注册接口调用出错,原因:"+e.getMessage());
			return null;
		}
		
	}
	

	
	/**
	 * 直连接口02
	 * 联动优势支付账户查询接口
	 * @param map
	 * @return
	 */
	public static Object[] queryCustmerInfo(CommonRequst commonRequst) {
		try{
			logger.info("联动优势支付账户查询接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_QUERYCUSTOMER);
			//读取联动给注册用户开的用户账号
			umpay.setUser_id(commonRequst.getThirdPayConfigId());
			umpay.setIs_find_account(UMPay.IS_HAVESELECT);
			umpay.setIs_select_agreement(UMPay.IS_HAVEAUTHORIZATION);
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付账户查询接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付注册接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付支付账户查询接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;;
					if(htmlReturnMap.containsKey("balance")){
						String moneyChange=new BigDecimal(htmlReturnMap.get("balance")).divide(new BigDecimal(100)).toString();
						htmlReturnMap.put("balance", moneyChange);
					}
					if(htmlReturnMap.containsKey("user_bind_agreement_list")){
						String agreementlist=htmlReturnMap.get("user_bind_agreement_list");
						String agreementName="开启无密交易：";
						String[] splitAgreementlist=agreementlist.split("|");
						if(splitAgreementlist.length>0){
							for(String temp:splitAgreementlist){
								if(temp.equals(UMPay.NO_PASSWORD_INVST)){
									agreementName=agreementName+"自动投标，";
								}else if(temp.equals(UMPay.NO_PASSWORD_REPAYMENT)){
									agreementName=agreementName+"自动还款，";
								}
							}
						}
						htmlReturnMap.put("user_bind_agreement_list", agreementName);
					}
					if(htmlReturnMap.containsKey("mail_addr")){
						htmlReturnMap.put("mail", htmlReturnMap.get("mail_addr"));
					}
					htmlReturnMap.put("mer_cust_name", htmlReturnMap.get("cust_name"));
					htmlReturnMap.put("user_id", htmlReturnMap.get("plat_user_id"));
					if(htmlReturnMap.get("account_state").equals("1")){
						htmlReturnMap.put("account_state", "账户正常");
					}else if(htmlReturnMap.get("account_state").equals("2")){
						htmlReturnMap.put("account_state", "账户已挂失");
					}else if(htmlReturnMap.get("account_state").equals("3")){
						htmlReturnMap.put("account_state", "账户已冻结");
					}else if(htmlReturnMap.get("account_state").equals("4")){
						htmlReturnMap.put("account_state",  "账户已锁定");
					}else if(htmlReturnMap.get("account_state").equals("9")){
						htmlReturnMap.put("account_state", "已销户");
					}
					
					backData[1]=htmlReturnMap;
					backData[2]=commonRequst;
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=null;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;;
				backData[1]="签名验证失败";
				backData[2]=null;
			}
			return backData;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付账户查询接口调用出错,原因:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 直连接口03
	 * 联动优势支付非投标类无密码转账接口
	 * 主要针对平台转账给投资人（无密码操作）
	 * @param map
	 * @return
	 */
	public static Object[] normalNOPassWordTransferInterface(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付非投标类无密码转账调用");
			Object[] backData=new Object[2];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_PLATFORMTRANSFER);
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			umpay.setMer_date(commonRequst.getTransactionTime().toString());
			//对私账户转账（个人用户）
			umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
			//转账方向
			umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
			//读取联动给注册用户开的用户账号
			umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
			//读取需要转账的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setAmount(amount.toString());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付非投标类无密码转账调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付非投标类无密码转账生成的签名sign=="+sign);
			logger.info("联动优势支付非投标类无密码转账调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付非投标类无密码转账调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付非投标类无密码转账调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;
					backData[1]=htmlReturnMap;
					backData[2]=commonRequst;
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=null;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;
				backData[1]="签名验证失败";
				backData[2]=null;
			}
			return backData;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付非投标类无密码转账调用出错,原因:"+e.getMessage());
			return null;
		}
	
	}
	
	/**
	 * 直连接口04
	 * 联动优势支付发标接口
	 * 主要针对erp发标接口（联动优势建立标的账户）
	 * @param map
	 * @return
	 */
	public static Object[] createBidAccount(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付发标接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_CREATBIDACCOUNT);
			//发标时间标的主键Id和标的标识
			umpay.setProject_id(commonRequst.getBidId());
			umpay.setProject_name(commonRequst.getBidName());
			//读取需要标的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setProject_amount(amount.toString());
			//读取借款人联动优势给借款人开通的第三方账号
			umpay.setLoan_user_id(commonRequst.getLoaner_thirdPayflagId());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付发标接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付发标接口生成的签名sign=="+sign);
			logger.info("联动优势支付发标接口调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付发标接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付发标接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;
					backData[1]=htmlReturnMap;
					backData[2]=commonRequst;
					
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=null;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;
				backData[1]="签名验证失败";
				backData[2]=null;
			}
			return backData;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付发标接口调用出错,原因:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 直连接口05
	 * 联动优势支付更新标接口
	 * 主要针对erp修改标接口（联动优势更新标的账户）
	 * @param map
	 * @return
	 */
	public static Object[] UpdateBidAccount(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付更新标接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_UPDATEBIDACCOUNT);
			//发标时间标的主键Id和标的标识
			umpay.setProject_id(commonRequst.getBidId());
			umpay.setChange_type(UMPay.UPDATE_TYPE1);
			umpay.setProject_state(commonRequst.getBidIdStatus());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付更新标接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付更新标接口生成的签名sign=="+sign);
			logger.info("联动优势支付发标接口调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付更新标接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付更新标接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;
					backData[1]=htmlReturnMap;
					backData[2]=commonRequst;
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=null;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;
				backData[1]="签名验证失败";
				backData[2]=null;
			}
			return backData;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付更新标接口调用出错,原因:"+e.getMessage());
			return null;
		}
	
	}
	
	/**
	 * 直接接口06
	 * 联动优势支付投标类无密码转账接口
	 * 主要针对投标类（账户转出）
	 * P2P平台发起的标的转出（对应业务场景为流标后还款、满标后缴费或放款、还款后返款、偿付后返款、债权转让的返款、撤资后的返款）请求，并完成标的账户资金划转到其他客户账户和相应记录的更新
	 * @param map
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("requestNo",String); //流水号
	 * map.put("orderDate",String);//交易日期
	 * map.put("bidType",String);//标的类型
	 * map.put("bidPlanId",String);//标的主键id
	 * map.put("businessType",String);//业务类型
	 * map.put("transferType",String);//转账方类型（取值范围：01投资者02融资人03 P2P平台04担保方05资金使用方）
	 * map.put("transferUserType",String);//转账方账户类型（取值范围：01投资者02融资人03 P2P平台04担保方05资金使用方）
	 * map.put("thirdPayflagId"，String);////联动给注册用户开的用户账号
	 * map.put("transferDirection"，String);//标的转账方向
	 * map.put("amount"，BigDecimal);//转账金额  单位元
	 * @return
	 */
	public static Object[] NoPasswordTransferInterface(CommonRequst commonRequst) {
		Object[] backData=new Object[3];
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付投标类无密码转账接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_TRANSFER);
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			String merDate=sdf.format(commonRequst.getTransactionTime());
			umpay.setMer_date(merDate);
			//发标时间标的主键Id和标的标识
			umpay.setProject_id(commonRequst.getBidId());
			//转账方类型（取值范围：01投资者02融资人03 P2P平台04担保方05资金使用方）
			//流标返款（从标的账户将钱返回给投资人）
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_BIDFAILD)||commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_MONEYPLANBIDFAILD)){
				umpay.setServ_type(UMPay.BUSSINESS_BIDFAILE);//标的转账接口业务类型（流标返款）
				umpay.setPartic_type(UMPay.TRABSFER_INVEST);//收款方类型
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_LOAN)||commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_MONEYPLANLOAN)){
				umpay.setServ_type(UMPay.BUSSINESS_PLATFORMFEE);//标的转账接口业务类型 放款
				umpay.setPartic_type(UMPay.TRABSFER_LOANER);//收款方类型
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);
			}
			//umpay.setPartic_acc_type(map.get("transferUserType").toString());
			
			//读取联动给注册用户开的用户账号
			umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
			//读取需要充值的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setAmount(amount.toString());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付投标类无密码转账接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付投标类无密码转账接口生成的签名sign=="+sign);
			logger.info("联动优势支付投标类无密码转账接口调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付投标类无密码转账接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付投标类无密码转账接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;
					backData[1]="交易成功";
					backData[2]=commonRequst;
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=commonRequst;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;
				backData[1]="签名验证失败";
				backData[2]=commonRequst;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付投标类无密码转账接口调用出错,原因:"+e.getMessage());
			backData[0]=ThirdPayConstants.RECOD_FAILD;
			backData[1]="联动优势支付投标类无密码转账接口调用出错";
			backData[2]=commonRequst;
		}
		return backData;
	}

	/**
	 * 直接接口07  下发注册验证码
	 * @param commonRequst
	 * @return
	 */
	public static Object[]  sendcheckCode(CommonRequst commonRequst){
		Object[] backData=new Object[3];
		try{
			logger.info("联动优势下发验证码接口调用");
			UMPay umpay= new UMPay();
			umpay.setService(UMPay.SER_SENDCODE);
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setMobile_id(commonRequst.getTelephone());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
			//生成订单日期
			String merDate=sdf.format(new Date());
			umpay.setMer_date(merDate);
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势下发验证码接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付注册接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付注册接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付注册接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付注册接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;
					commonRequst.setRequsetNo(htmlReturnMap.get("sms_trace"));
					backData[1]="下发验证码成功";
					backData[2]=commonRequst;
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=null;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;
				backData[1]="签名验证失败";
				backData[2]=null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付注册接口调用出错,原因:"+e.getMessage());
			backData[0]=ThirdPayConstants.RECOD_FAILD;
			backData[1]="联动优势下发验证码调用出错";
			backData[2]=null;
		}
		return backData;
	}
	
	/**
	 * 直连01
	 * 联动优势支付注册接口
	 * @param map
	 * @return
	 */
	public static Object[] checkCodeRegiest(CommonRequst commonRequst) {
		Object[] backData=new Object[3];
		try{
			logger.info("联动优势验证码注册接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_CHECKCODEREGISTER);
			umpay.setIdentity_type(UMPay.IDENTITY_CARD);
			umpay.setEmail(commonRequst.getEmail());
			umpay.setMobile_id(commonRequst.getTelephone());
			//产生验证码成功的流水号
			umpay.setSms_trace(commonRequst.getRequsetNo());
			//下发验证码
			umpay.setVerification_code(commonRequst.getCheckCode());
			String cardCode=UMPayInterfaceUtil.Encrypt(commonRequst.getCardNumber(),UMPay.GBK);
			umpay.setIdentity_code(cardCode);
			String userName=UMPayInterfaceUtil.Encrypt(commonRequst.getTrueName(),UMPay.GBK);
			umpay.setMer_cust_name(userName);
			umpay.setMer_cust_id("TG"+commonRequst.getCardNumber()+commonRequst.getCustMemberId());
			System.out.println(umpay.getMer_cust_id());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付注册接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付注册接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付注册接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(configMap.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付注册接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付注册接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					backData[0]=ThirdPayConstants.RECOD_SUCCESS;
					commonRequst.setThirdPayConfig(ThirdPayConstants.UMPAY);
					commonRequst.setThirdPayConfigId(htmlReturnMap.get("user_id").toString());
					commonRequst.setThirdPayConfigId0(htmlReturnMap.get("account_id").toString());
					backData[1]="注册成功";
					backData[2]=commonRequst;
				}else{
					backData[0]=ThirdPayConstants.RECOD_FAILD;
					backData[1]=htmlReturnMap.get("ret_msg").toString();
					backData[2]=null;
				}
			}else{
				backData[0]=ThirdPayConstants.RECOD_FAILD;
				backData[1]="签名验证失败";
				backData[2]=null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付注册接口调用出错,原因:"+e.getMessage());
			backData[0]=ThirdPayConstants.RECOD_FAILD;
			backData[1]="联动优势支付注册接口调用出错";
			backData[2]=null;
		}
		return backData;
	}
	//######################################直连接口（结束）#############################################
	/**
	 * 公共方法
	 * 将回调通知（页面回调和服务器端回调）收到的request转换成map对象 
	 */
	private static Map createResponseMap(HttpServletRequest request){
		/**
		 * 准备将回调通知参数整合成map
		 */
		Map<String,String> map =new HashMap<String,String>();
		Enumeration paramEnu=request.getParameterNames();
		String parameter="";
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    		String paramValue=(String)request.getParameter(paramName);
    		map.put(paramName, paramValue);
    		logger.info("联动优势回调通知方法收到的参数记录："+paramName+"="+paramValue);
    		parameter=parameter+(paramName+"="+paramValue+"&");
    		logger.info("联动优势回调函数通知方法收到的参数记录连接："+paramName+"="+paramValue);
    	}
    	logger.info("联动优势回调函数通知方法收到的参数记录parameter:"+parameter);
		return map;
	};
	
	/**
	 * 页面回调
	 * 通知业务数据处理
	 */
	public static CommonResponse pageCallBackOprate(HttpServletRequest request) {
		//业务类型(每个接口多种使用方式系统添加参数)
		//默认回调通知参数实体
		CommonResponse commonResponse=new CommonResponse();
		//系统调用第三方回调的url中默认携带参数
		String bussinessType=request.getParameter("HRBT");
		request.removeAttribute("HRBT");//将我们默认传递的参数从request中移除
		//将业务参数返回给业务层
		commonResponse.setBussinessType(bussinessType);
		try{
			logger.info("联动优势页面回调函数通知方法");
			//用来标识是否完成
			
			/**
			 * 准备将回调通知参数整合成map
			 */
			Map<String,String> map =createResponseMap(request);
	    	if(!map.isEmpty()){
	    		Boolean isSign=false;
	    		//联动优势验证签名方法
	    		logger.info("联动优势页面回调函数通知签名验证结果isSign="+isSign);
	    		if(true){
	    			String service=request.getParameter("service");
	    			if(service!=null&&!"".equals(service)){//其他业务处理方法
	    				if(service.equals(UMPay.NOTIFY_RECHARGE)){//充值交易回调通知
	    					logger.info("联动优势服务器端回调函数通知调用充值业务操作方法开始");
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    							commonResponse.setResponseMsg("充值成功");
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("充值失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
	    					}
	    				}else if(service.equals(UMPay.NOTIFY_WITHDRAW)){//取现回调通知（第一次申请结果通知）
	    					logger.info("联动优势服务器端回调函数通知调用取现业务操作方法开始");
	    					// TODO Auto-generated method stub
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    							commonResponse.setResponseMsg("取现成功");
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("取现失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
	    					}
	    				}else if(service.equals(UMPay.NOTIFY_BINDCARD)){//绑卡回调通知
	    					logger.info("联动优势服务器端回调函数通知调用绑卡业务操作方法开始");
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							if(map.containsKey("last_four_cardid")){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("绑卡成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
		    							commonResponse.setResponseMsg("绑卡受理中，等待时间大约2个小时");
	    							}
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("绑卡失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
	    					}
	    					
	    				}/*else if(service.equals(UMPay.NOTIFY_BINDCARD)){//更换银行卡回调通知（第一次申请结果）
	    					
	    				}else if(service.equals(UMPay.NOTIFY_BINDCARD)){//更换银行卡回调通知（第二次结果通知）
	    					
	    				}*/else if(service.equals(UMPay.NOTIFY_BINDAGERRMENT)){//无密交易授权回调通知
	    					logger.info("联动优势服务器端回调函数通知页面回调函数通知调用无密交易授权业务操作方法开始");
	    					String[] ret=new String[2];
	    					// TODO Auto-generated method stub
	    					if(!requestValue.containsKey(map.get("user_bind_agreement_list"))){
	    						requestValue.put(map.get("user_bind_agreement_list")+map.get("user_id").toString(),map.get("user_bind_agreement_list")+map.get("user_id").toString());
	    					}
	    					synchronized(requestValue.get(map.get("user_bind_agreement_list")+map.get("user_id").toString())){
	    						String bind_agreement=map.get("user_bind_agreement_list").toString().trim();
	    						String[] bind=bind_agreement.split(",");
	    						//授权用户的第三方账号
	    						commonResponse.setThirdPayConfigId(map.get("user_id").toString());
	    						if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_INVST)){//投资授权
	    							//TODD 处理 投标授权的业务方法
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("自动投标授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("第三方自动投标授权失败，失败原因："+bind[2]);
	    							}
	    						}else if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_REPAYMENT)){//还款授权
	    							//TODD 处理 还款授权的业务方法
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("自动还款授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("第三方自动还款授权失败，失败原因："+bind[2]);
	    							}
	    						}else if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_FASTPAYMENT)){//NO_PASSWORD_FASTPAYMENT
	    							//TODD 处理 无密充值快捷协议和开通快捷支付银行卡不同的业务方法
	    							
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("自动授权无密码交易授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("绑卡失败");
	    					    		ret[1]="自动授权无密码交易授权失败，失败原因："+bind[2];
	    							}
	    						}else if (service.equals(UMPay.SER_TRANSFER)){//投标回调通知
	    	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    	    					//充值交易回调通知
	    	    					if(!requestValue.containsKey(map.get("order_id"))){
	    	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    	    					}
	    	    					synchronized(requestValue.get(map.get("order_id"))){
	    	    			          //TODO 添加处理业务方法
	    	    						commonResponse.setRequestNo(map.get("order_id"));
	    	    						commonResponse.setBussinessType(service);
	    	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    	    							commonResponse.setResponseMsg("投标成功");
	    	    						}else{
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	    							commonResponse.setResponseMsg("投标失败");
	    	    						}
	    	    						requestValue.remove(map.get("order_id"));
	    	    					}
	    	    				
	    	    				}else if (service.equals(UMPay.SER_TRANSFER)){//还款回调通知
	    	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    	    					if(!requestValue.containsKey(map.get("order_id"))){
	    	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    	    					}
	    	    					synchronized(requestValue.get(map.get("order_id"))){
	    	    			          //TODO 添加处理业务方法
	    	    						commonResponse.setRequestNo(map.get("order_id"));
	    	    						commonResponse.setBussinessType(service);
	    	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    	    							commonResponse.setResponseMsg("还款成功");
	    	    						}else{
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	    							commonResponse.setResponseMsg("还款失败");
	    	    						}
	    	    						requestValue.remove(map.get("order_id"));
	    	    					}
	    	    				
	    	    				}else if (service.equals(UMPay.SER_TRANSFER)){//流标回调通知
	    	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    	    					if(!requestValue.containsKey(map.get("order_id"))){
	    	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    	    					}
	    	    					synchronized(requestValue.get(map.get("order_id"))){
	    	    			          //TODO 添加处理业务方法
	    	    						commonResponse.setRequestNo(map.get("order_id"));
	    	    						commonResponse.setBussinessType(service);
	    	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    	    							commonResponse.setResponseMsg("流标成功");
	    	    						}else{
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	    							commonResponse.setResponseMsg("流标失败");
	    	    						}
	    	    						requestValue.remove(map.get("order_id"));
	    	    					}
	    	    				
	    	    				}else if (service.equals(UMPay.SER_TRANSFER)){//放款回调通知
	    	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    	    					if(!requestValue.containsKey(map.get("order_id"))){
	    	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    	    					}
	    	    					synchronized(requestValue.get(map.get("order_id"))){
	    	    			          //TODO 添加处理业务方法
	    	    						commonResponse.setRequestNo(map.get("order_id"));
	    	    						commonResponse.setBussinessType(service);
	    	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    	    							commonResponse.setResponseMsg("放款成功");
	    	    						}else{
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	    							commonResponse.setResponseMsg("放款失败");
	    	    						}
	    	    						requestValue.remove(map.get("order_id"));
	    	    					}
	    	    				
	    	    				}
	    	    				
	    						requestValue.remove(map.get("user_bind_agreement_list")+map.get("user_id").toString());
	    					}
	    				}else if(service.equals(UMPay.NOTIFY_CANCELAGERRMENT)){//取消无密交易授权回调通知
	    					logger.info("联动优服务器端回调函数通知页面回调函数通知调用取消无密交易授权业务操作方法开始");
	    					String[] ret=new String[2];
	    					// TODO Auto-generated method stub
	    					if(!requestValue.containsKey(map.get("user_unbind_agreement_list"))){
	    						requestValue.put(map.get("user_unbind_agreement_list")+map.get("user_id").toString(),map.get("user_unbind_agreement_list")+map.get("user_id").toString());
	    					}
	    					synchronized(requestValue.get(map.get("user_unbind_agreement_list")+map.get("user_id").toString())){
	    						String bind_agreement=map.get("user_unbind_agreement_list").toString().trim();
	    						String[] bind=bind_agreement.split(",");
	    						//授权用户的第三方账号
	    						commonResponse.setThirdPayConfigId(map.get("user_id").toString());
	    						if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_INVST)){//解除投资授权
	    							//TODD 处理 投标授权的业务方法
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    								commonResponse.setResponseMsg("解除自动投标授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    								commonResponse.setResponseMsg("解除第三方自动投标授权失败，失败原因："+bind[2]);
	    							}
	    						}else if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_REPAYMENT)){//还款授权
	    							//TODD 处理 还款授权的业务方法
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("解除自动还款授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("解除第三方自动还款授权失败，失败原因："+bind[2]);
	    							}
	    						}
	    						requestValue.remove(map.get("user_bind_agreement_list")+map.get("user_id").toString());
	    					}
	    				}else if(service.equals(UMPay.NOTIFY_NOMALTRANSFER)){//普通转账回调通知
	    					logger.info("联动优势服务器端回调函数通知调用普通转账操作方法开始");
	    					
	    				}else if(service.equals(UMPay.NOTIFY_BIDTRANSFER)){//标类的转账回调通知
	    					logger.info("联动优势服务器端回调函数通知调用标类的转账业务操作方法开始");
	    				}
	    				
	    			}else{
	    				logger.info("联动优势页面回调函数通知无业务类型字段service，目前认为是取现服务器端通知");
	    			}
	    			
	    		}else{
	    			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
					commonResponse.setResponseMsg("签名验证失败");
		    		logger.info("联动优势页面回调函数通知出错，原因：联动优势页面回调函数通知签名验证没有通过，请等待一段时间后查询个人中心资金交易明细，或者联系管理员");
	    		}
	    	}else{
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTRECIVEPARAMETER);
				commonResponse.setResponseMsg("没有收到任何回调通知");
	    		logger.info("联动优势页面回调函数通知出错，原因：没有收到任何交易参数，请等待一段时间后查询个人中心资金交易明细，或者联系管理员");
	    	}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SYSTEMERROR);
			commonResponse.setResponseMsg("系统报错，联系管理员");
			logger.info("联动优势页面回调函数通知出错，原因："+e.getMessage());
		}
		
		
		return commonResponse;
	}
	
	/**
	 * 服务器端回调
	 * 通知业务数据处理
	 * @param request
	 * @return
	 */
	public static CommonResponse notifyCallBackOprate(HttpServletRequest request) {
		//业务类型(每个接口多种使用方式系统添加参数)
		//默认回调通知参数实体
		CommonResponse commonResponse=new CommonResponse();
		//系统调用第三方回调的url中默认携带参数
		String bussinessType=request.getParameter("HRBT");
		request.removeAttribute("HRBT");//将我们默认传递的参数从request中移除
		//将业务参数返回给业务层
		commonResponse.setBussinessType(bussinessType);
		try{
			logger.info("联动优势服务器端回调函数通知方法开始");
			//用来标识是否完成
			Boolean isSign=false;
			/**
			 * 准备将回调通知参数整合成map
			 */
			Map<String,String> map =createResponseMap(request);
	    	if(!map.isEmpty()){
	    		//联动优势验证签名方法
	    		isSign=verifySign(map);
	    		logger.info("联动优势服务器端回调函数通知签名验证结果isSign="+isSign);
	    		if(isSign){
	    			String service=request.getParameter("service");
	    			//生成需要返回给第三方的参数
	    			String responsehtml=createReaponseHTML();
	    			if(service!=null&&!"".equals(service)){//其他业务处理方法
	    				if(service.equals(UMPay.NOTIFY_RECHARGE)){//充值交易回调通知
	    					logger.info("联动优势服务器端回调函数通知调用充值业务操作方法开始");
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    							commonResponse.setResponseMsg("充值成功");
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("充值失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
	    					}
	    				}else if(service.equals(UMPay.NOTIFY_WITHDRAW)){//取现回调通知（第一次申请结果通知）
	    					logger.info("联动优势服务器端回调函数通知调用取现业务操作方法开始");
	    					String[] ret=new String[2];
	    					// TODO Auto-generated method stub
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    							commonResponse.setResponseMsg("取现成功");
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("取现失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
	    					}
	    				}else if(service.equals(UMPay.NOTIFY_BINDCARD)){//绑卡回调通知
	    					logger.info("联动优势服务器端回调函数通知调用绑卡业务操作方法开始");
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							if(map.containsKey("last_four_cardid")){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("绑卡成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
		    							commonResponse.setResponseMsg("绑卡受理中，等待时间大约2个小时");
	    							}
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("绑卡失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
	    					}
	    					
	    				}/*else if(service.equals(UMPay.NOTIFY_BINDCARD)){//更换银行卡回调通知（第一次申请结果）
	    					
	    				}else if(service.equals(UMPay.NOTIFY_BINDCARD)){//更换银行卡回调通知（第二次结果通知）
	    					
	    				}*/else if(service.equals(UMPay.NOTIFY_BINDAGERRMENT)){//无密交易授权回调通知
	    					logger.info("联动优势服务器端回调函数通知页面回调函数通知调用无密交易授权业务操作方法开始");
	    					String[] ret=new String[2];
	    					// TODO Auto-generated method stub
	    					if(!requestValue.containsKey(map.get("user_bind_agreement_list"))){
	    						requestValue.put(map.get("user_bind_agreement_list")+map.get("user_id").toString(),map.get("user_bind_agreement_list")+map.get("user_id").toString());
	    					}
	    					synchronized(requestValue.get(map.get("user_bind_agreement_list"))){
	    						String bind_agreement=map.get("user_bind_agreement_list").toString().trim();
	    						String[] bind=bind_agreement.split(",");
	    						//授权用户的第三方账号
	    						commonResponse.setThirdPayConfigId(map.get("user_id").toString());
	    						if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_INVST)){//投资授权
	    							//TODD 处理 投标授权的业务方法
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("自动投标授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("第三方自动投标授权失败，失败原因："+bind[2]);
	    							}
	    						}else if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_REPAYMENT)){//还款授权
	    							//TODD 处理 还款授权的业务方法
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("自动投标授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("第三方自动还款授权失败，失败原因："+bind[2]);
	    							}
	    						}else if(bind!=null&&bind[0].equals(UMPay.NO_PASSWORD_FASTPAYMENT)){//NO_PASSWORD_FASTPAYMENT
	    							//TODD 处理 无密充值快捷协议和开通快捷支付银行卡不同的业务方法
	    							
	    							if(bind[1].equals(UMPay.CODE_SUCESS)){
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
		    							commonResponse.setResponseMsg("自动授权无密码交易授权成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("绑卡失败");
	    					    		ret[1]="自动授权无密码交易授权失败，失败原因："+bind[2];
	    							}
	    						}else if (service.equals(UMPay.SER_TRANSFER)){//还款回调通知
	    	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    	    					if(!requestValue.containsKey(map.get("order_id"))){
	    	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    	    					}
	    	    					synchronized(requestValue.get(map.get("order_id"))){
	    	    			          //TODO 添加处理业务方法
	    	    						commonResponse.setRequestNo(map.get("order_id"));
	    	    						commonResponse.setBussinessType(service);
	    	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    	    							commonResponse.setResponseMsg("还款成功");
	    	    						}else{
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	    							commonResponse.setResponseMsg("还款失败");
	    	    						}
	    	    						requestValue.remove(map.get("order_id"));
	    	    					}
	    	    				
	    	    				}else if (service.equals(UMPay.SER_TRANSFER)){//流标回调通知
	    	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    	    					if(!requestValue.containsKey(map.get("order_id"))){
	    	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    	    					}
	    	    					synchronized(requestValue.get(map.get("order_id"))){
	    	    			          //TODO 添加处理业务方法
	    	    						commonResponse.setRequestNo(map.get("order_id"));
	    	    						commonResponse.setBussinessType(service);
	    	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    	    							commonResponse.setResponseMsg("流标成功");
	    	    						}else{
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	    							commonResponse.setResponseMsg("流标失败");
	    	    						}
	    	    						requestValue.remove(map.get("order_id"));
	    	    					}
	    	    				
	    	    				}else if (service.equals(UMPay.SER_TRANSFER)){//放款回调通知
	    	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    	    					if(!requestValue.containsKey(map.get("order_id"))){
	    	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    	    					}
	    	    					synchronized(requestValue.get(map.get("order_id"))){
	    	    			          //TODO 添加处理业务方法
	    	    						commonResponse.setRequestNo(map.get("order_id"));
	    	    						commonResponse.setBussinessType(service);
	    	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    	    							commonResponse.setResponseMsg("放款成功");
	    	    						}else{
	    	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	    							commonResponse.setResponseMsg("放款失败");
	    	    						}
	    	    						requestValue.remove(map.get("order_id"));
	    	    					}
	    	    				
	    	    				}
	    						requestValue.remove(map.get("user_bind_agreement_list")+map.get("user_id").toString());
	    					}
	    				}else if(service.equals(UMPay.NOTIFY_CANCELAGERRMENT)){//取消无密交易授权回调通知
	    					logger.info("联动优服务器端回调函数通知页面回调函数通知调用取消无密交易授权业务操作方法开始");
	    					
	    				}else if(service.equals(UMPay.NOTIFY_NOMALTRANSFER)){//普通转账回调通知
	    					logger.info("联动优势服务器端回调函数通知调用普通转账操作方法开始");
	    					
	    				}else if(service.equals(UMPay.NOTIFY_BIDTRANSFER)){//标类的转账回调通知
	    					logger.info("联动优势服务器端回调函数通知调用标类的转账业务操作方法开始");
	    					
	    					
	    				}else if (service.equals(UMPay.SER_TRANSFER)){//投标回调通知
	    					logger.info("联动优势服务器端回调函数通知调用标类的投标业务操作方法开始");
	    					//充值交易回调通知
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						commonResponse.setBussinessType(service);
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    							commonResponse.setResponseMsg("投标成功");
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("投标失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
	    					}
	    				
	    				}
	    				
	    			}else{//默认为第三方接收到银行的最后的通知(默认取现最后一次通知)
	    				
	    			}
	    			commonResponse.setReturnMsg(responsehtml);
	    			logger.info("联动优势服务器端回调函数通知给联动优势的相应html="+responsehtml);
	    		}else{
	    			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
					commonResponse.setResponseMsg("签名验证失败");
		    		logger.info("联动优势服务器端回调函数通知出错，原因：联动优势服务器端回调函数通知签名验证没有通过，请等待一段时间后查询个人中心资金交易明细，或者联系管理员");
	    		}
	    	}else{
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTRECIVEPARAMETER);
				commonResponse.setResponseMsg("没有收到任何回调通知");
	    		logger.info("联动优势服务器端回调函数通知出错，原因：没有收到任何交易参数，请等待一段时间后查询个人中心资金交易明细，或者联系管理员");
	    	}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势服务器端回调函数通知出错，原因："+e.getMessage());
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SYSTEMERROR);
			commonResponse.setResponseMsg("系统报错，联系管理员");
		}
		commonResponse.setReturnType("json");
		return  commonResponse;
	}

}
