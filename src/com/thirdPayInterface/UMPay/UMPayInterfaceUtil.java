package com.thirdPayInterface.UMPay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.thirdPayInterface.CommonRecord;
import com.thirdPayInterface.UMPay.UMPay;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayWebClient;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayRecordService;
import com.thirdPayInterface.UMPay.UMPayUtil.SignUtil;
import com.thirdPayInterface.UMPay.UMPayUtil.SunBase64;
import com.zhiwei.core.command.QueryFilter;
import com.thirdPayInterface.UMPay.responUtil.UMPayBidAccount;
import com.thirdPayInterface.UMPay.responUtil.UMPayBidTransferCompare;
import com.thirdPayInterface.UMPay.responUtil.UMPayRecharge;
import com.thirdPayInterface.UMPay.responUtil.UMPayTransfer;
import com.thirdPayInterface.UMPay.responUtil.UMPayWithdraw;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;

public class UMPayInterfaceUtil {

	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	protected static Log logger=LogFactory.getLog(UMPayInterfaceUtil.class);
	public static final Map<String,String> requestValue=new HashMap<String,String>();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	static  ThirdPayRecordService thirdPayRecordService = (ThirdPayRecordService) AppUtil.getBean("thirdPayRecordService");

	/**
	 * 准备UMP公共支付数据
	 * @param umpay 
	 * @return
	 */
	private static UMPay generalPublicDate(UMPay umpay,Boolean notifyUrl,Boolean pageUrl,String bussinessType) {
		Map thirdPayConfig=umProperty();
		umpay.setVersion("1.0");//版本号
		umpay.setCharset(UMPay.UTF8);//参数字符编码集
		umpay.setRes_format("HTML");//响应数据格式
		umpay.setSign_type("RSA");//签名方式
		umpay.setMer_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());//商户编号
		String BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
		if(notifyUrl){
			umpay.setNotify_url(BasePath+thirdPayConfig.get("thirdPay_notifyUrl").toString());
		}
		if(pageUrl){
			umpay.setRet_url(BasePath+thirdPayConfig.get("thirdPay_callbackUrl").toString());
		}
		return umpay;
	}
	/**
	 * 获取第三方支付环境参数
	 * @return
	 */
	private static Map umProperty(){
		Map umConfigMap=new HashMap();
		try{
			InputStream in=null;
			//获取当前支付环境为正式环境还是测试环境
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
		       in = UMPayInterfaceUtil.class.getResourceAsStream("UMPayNormalEnvironment.properties"); 
			}else{
		        in = UMPayInterfaceUtil.class.getResourceAsStream("UMPayTestEnvironment.properties"); 
			}
			Properties props =  new  Properties(); 
			if(in!=null){
				props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		umConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    		logger.error(ex.getMessage());
    	}
		return umConfigMap;
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
	public static CommonResponse autoAuthorization(CommonRequst commonRequst) {
		CommonResponse cr = new CommonResponse();
		cr.setCommonRequst(commonRequst);
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付无密交易授权接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_NOPASSWORD);
			//读取联动给注册用户开的用户账号
			umpay.setUser_id(commonRequst.getThirdPayConfigId());
			//自动授权类型
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMAUTH)){//无密还款授权
				umpay.setUser_bind_agreement_list(UMPay.NO_PASSWORD_REPAYMENT);
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付无密交易授权接口调用生成的传输参数params=="+params);
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(thirdPayConfig.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			System.out.println("ret[1]=="+ret[1]);
			logger.info("联动优势支付无密交易授权接口调用生成的签名sign=="+sign);
			cr.setRequestInfo(ret[1]);
			if(null != ret && ThirdPayConstants.RECOD_SUCCESS.equals(ret[0])){
				cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
				cr.setResponseMsg("无密交易授权开通成功");
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("无密交易授权开通失败");
			}
			return cr;
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("无密交易授权接口对接失败");
			logger.info("联动优势支付无密交易授权接口调用出错,原因:"+e.getMessage());
			return cr;
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
	public static CommonResponse transferInterface(CommonRequst commonRequst) {
       CommonResponse response  = new  CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
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
			umpay.setProject_id(commonRequst.getBidType()+commonRequst.getBidId());
			//业务类型
			String bussinessType="";
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_BID)){
				bussinessType="01";
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_LOANERREPAYMENT)){
				bussinessType="03";
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREPAREPAY)){//平台垫付
				bussinessType="04";
			}
			umpay.setServ_type(bussinessType);
			//转账方类型（取值范围：01投资者02融资人03 P2P平台04担保方05资金使用方）
			if(bussinessType!=null&&!"".equals(bussinessType)&&bussinessType.equals("04")){
				umpay.setPartic_type("04");
			}else{
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				umpay.setPartic_type(commonRequst.getCustMemberType());
			}
			//标的转入
			umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
			//转账方账户类型
			if(bussinessType!=null&&!"".equals(bussinessType)&&bussinessType.equals("04")){
				umpay.setPartic_acc_type("02");
				umpay.setPartic_user_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());//thirdPayConfig
			}else{
				umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
			}
			//读取联动给注册用户开的用户账号
			
			//读取需要充值的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getAmount();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setAmount(amount.toString());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付投标类有密码转账调用生成的传输参数params=="+params);
			//当交易类型是债权交易时，用直连的方法
			if(bussinessType!=null&&!"".equals(bussinessType)&&bussinessType.equals("04")){
				String ret=ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
				logger.info("联动优势支付注册接口调用后收到的通知"+ret);
				Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
				Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
				System.out.println("验证签名isSign=="+isSign);
				if(true){
					if(htmlReturnMap.get("ret_code").equals("0000")){
						response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						response.setResponseMsg("交易成功");
						response.setCommonRequst(commonRequst);
					}else{
						response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
						response.setCommonRequst(commonRequst);
					}
				}
			}else{
			Map<String,String> htmlmap=UMPayInterfaceUtil.createHtmlMap(umpay);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			htmlmap.put("sign", sign);
			String[] ret= ThirdPayWebClient.operateParameter(configMap.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付支付投标类有密码转账接口调用生成的签名sign=="+sign);
			logger.info("联动优势支付支付投标类有密码转账接口调用调用的url=="+configMap.get("thirdPay_umpay_URL").toString());
			System.out.println("ret[1]===="+ret[1]);
			if(null!=ret&&ret[0].equals(ThirdPayConstants.RECOD_SUCCESS)){
				response.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
				response.setResponseMsg("操作成功");
				response.setCommonRequst(commonRequst);
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				response.setResponseMsg("操作失败");
				response.setCommonRequst(commonRequst);
			}
		}
		}catch(Exception e){
			logger.info("联动优势支付支付投标类有密码转账接口调用出错,原因:"+e.getMessage());
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("系统报错");
			response.setCommonRequst(commonRequst);
		}
		return  response;
	
	}

	//######################################网关接口（结束）#############################################
	
	//######################################直连接口（开始）#############################################
	
	
	/**
	 * 直连01
	 * 联动优势支付注册接口
	 * @param map
	 * @return
	 */
	public static CommonResponse regiest(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		try{
			logger.info("联动优势支付注册接口调用");
			Map thirdPayConfig=umProperty();
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_REGIST);//接口名称
			umpay.setIdentity_type(UMPay.IDENTITY_CARD);//证件类型
			umpay.setEmail(commonRequst.getEmail());//邮箱
			umpay.setMobile_id(commonRequst.getTelephone());//手机号
			String cardCode=UMPayInterfaceUtil.Encrypt(commonRequst.getCardNumber(),UMPay.GBK);
			umpay.setIdentity_code(cardCode);//证件号
			String userName=UMPayInterfaceUtil.Encrypt(commonRequst.getTrueName(),UMPay.GBK);
			umpay.setMer_cust_name(userName);//用户姓名
			umpay.setMer_cust_id("TG"+commonRequst.getCardNumber()+commonRequst.getCustMemberId());//商户用户唯一标识
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			String ret=ThirdPayWebClient.getUndecodeByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付注册接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付注册接口调用后收到的通知后验证签名结果isSign="+isSign);
			commonResponse.setRequestInfo(ret);
			if(isSign){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
					commonResponse.setThirdPayConfig(ThirdPayConstants.UMPAY);
					commonResponse.setThirdPayConfigId(htmlReturnMap.get("user_id").toString());
					commonResponse.setThirdPayConfigId0(htmlReturnMap.get("account_id").toString());
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("个人用户注册签名验证失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("联动优势支付注册接口调用出错");
		}
		return commonResponse;
		
	}
	

	
	/**
	 * 直连接口02
	 * 联动优势支付平台账户查询接口
	 * @param map
	 * @return
	 */
	public static CommonResponse queryPlatformInfo(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			logger.info("联动优势支付账户查询接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_PTP_MER_QUERY);
			//读取联动给注册用户开的用户账号
			umpay.setQuery_mer_id(umpay.getMer_id());
			umpay.setAccount_type("01");
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付账户查询接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getUndecodeByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付注册接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付支付账户查询接口调用后收到的通知后验证签名结果isSign="+isSign);
			if(isSign){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("查询成功");
					commonResponse.setCustmemberStatus("已激活");
					if(htmlReturnMap.containsKey("balance")){
						String moneyChange=new BigDecimal(htmlReturnMap.get("balance")).divide(new BigDecimal(100)).toString();
						commonResponse.setBalance(new BigDecimal(moneyChange));
						commonResponse.setAvailableAmount(new BigDecimal(moneyChange));
						commonResponse.setFreezeAmount(new BigDecimal(0.00));
					}

					commonResponse.setThirdPayConfigId(umpay.getMer_id());
					if(htmlReturnMap.containsKey("card_id")){
						commonResponse.setBankCode(htmlReturnMap.get("card_id"));
						commonResponse.setBindBankStatus("已绑定"); 
						commonResponse.setBankName(htmlReturnMap.get("gate_id"));
					}
					if(htmlReturnMap.containsKey("user_bind_agreement_list")){
						String agreementlist=htmlReturnMap.get("user_bind_agreement_list");
						String agreementName="开启无密交易：";
						String[] splitAgreementlist=agreementlist.split("|");
						if(splitAgreementlist.length>0){
							for(String temp:splitAgreementlist){
								if(temp.equals(UMPay.NO_PASSWORD_INVST)){
									agreementName=agreementName+"自动投标，";
									commonResponse.setAutoTender("true");
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
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("查询失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("签名验证失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付账户查询接口调用出错,原因:"+e.getMessage());
		}
		return commonResponse;
	}
	
	/**
	 * 直连接口03
	 * 联动优势支付非投标类无密码转账接口
	 * 主要针对平台转账给投资人（无密码操作）
	 * @param map
	 * @return
	 */
	public static CommonResponse normalNOPassWordTransferInterface(CommonRequst commonRequst) {
		CommonResponse response = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付非投标类无密码转账调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_PLATFORMTRANSFER);
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			umpay.setMer_date(sf.format(new Date()));
			if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==1){//企业
				//对公账户转账（企业用户）
				umpay.setPartic_acc_type(UMPay.PARTIPAY_PUBLIC);
			}else{
				//对私账户转账（个人用户）
				umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
			}
			//转账方向
			umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);
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
			String ret=ThirdPayWebClient.getUndecodeByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付非投标类无密码转账调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付非投标类无密码转账调用后收到的通知后验证签名结果isSign="+isSign);
			response.setRequestInfo(htmlReturnMap.toString());
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}else{
					response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				response.setResponseMsg("平台转账交易签名验证失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("联动优势支付非投标类无密码转账调用出错");
		}
		return response;
	}
	/**
	 * 直连接口03
	 * 联动优势支付非投标类无密码转账接口
	 * 主要针对用户转账给平台（无密码操作）
	 * @param map
	 * @return
	 */
	public static CommonResponse transferUserTo(CommonRequst commonRequst) {
		CommonResponse response = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付非投标类无密码转账调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_PLATFORMTRANSFER);
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			umpay.setMer_date(sf.format(new Date()));
			//对私账户转账（个人用户）
			umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
			//转账方向
			umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);
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
			String ret=ThirdPayWebClient.getUndecodeByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付非投标类无密码转账调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付非投标类无密码转账调用后收到的通知后验证签名结果isSign="+isSign);
			response.setRequestInfo(htmlReturnMap.toString());
			if(true){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}else{
					response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				response.setResponseMsg("平台转账交易签名验证失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("联动优势支付非投标类无密码转账调用出错");
		}
		return response;
	}
	/**
	 * 直连接口04
	 * 联动优势支付发标接口
	 * 主要针对erp发标接口（联动优势建立标的账户）
	 * @param map
	 * @return
	 */
	public static CommonResponse createBidAccount(CommonRequst commonRequst) {
		CommonResponse response = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付发标接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_CREATBIDACCOUNT);
			//发标时间标的主键Id和标的标识
			umpay.setProject_id(commonRequst.getBidType()+commonRequst.getBidId());
			umpay.setProject_name(commonRequst.getBidName());
			//读取需要标的金额（联动交易金额是以分为单位）
			BigDecimal  amount=commonRequst.getBidMoney();
			amount=amount.multiply(new BigDecimal(100)).setScale(0);
			umpay.setProject_amount(amount.toString());
			if(commonRequst.getLoanAccType()!=null&&!"".equals(commonRequst.getLoanAccType())){
				umpay.setLoan_acc_type(commonRequst.getLoanAccType());
			}
			if(null!=commonRequst.getWarranty_account()&&!"".equals(commonRequst.getWarranty_account())&&!"none".equals(commonRequst.getWarranty_account())){
				umpay.setWarranty_user_id(commonRequst.getWarranty_account());//设置代偿方
			}
			//读取借款人联动优势给借款人开通的第三方账号
			umpay.setLoan_user_id(commonRequst.getThirdPayConfigId());
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付发标接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			logger.info("联动优势支付发标接口生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getUndecodeByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付发标接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付发标接口调用后收到的通知后验证签名结果isSign="+isSign);
			response.setRequestInfo(htmlReturnMap.toString());
			if(isSign){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}else{
					response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				response.setResponseMsg("签名验证失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("联动优势支付发标接口调用出错");
		}
		return response;
	}
	
	/**
	 * 直连接口05
	 * 联动优势支付更新标接口
	 * 主要针对erp修改标接口（联动优势更新标的账户）
	 * @param map
	 * @return
	 */
	public static CommonResponse updateBidAccount(CommonRequst commonRequst) {
		CommonResponse response = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付更新标接口调用");
			Object[] backData=new Object[3];
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_UPDATEBIDACCOUNT);
			//发标时间标的主键Id和标的标识
			umpay.setProject_id(commonRequst.getBidType()+commonRequst.getBidId());
			umpay.setChange_type(UMPay.UPDATE_TYPE1);
			umpay.setProject_state(commonRequst.getBidIdStatus());
			if(commonRequst.getLoanAccType()!=null&&!"".equals(commonRequst.getLoanAccType())){
				umpay.setLoan_acc_type(commonRequst.getLoanAccType());
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付更新标接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付更新标接口生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getUndecodeByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			System.out.println("ret="+ret);
			logger.info("联动优势支付更新标接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			response.setRequestInfo(htmlReturnMap.toString());
			if(isSign){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}else{
					response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				response.setResponseMsg("签名验证失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("联动优势支付更新标接口调用出错");
		}
		return response;
	
	}
	
	/**
	 * 直接接口06
	 * 联动优势支付投标类转账接口（标账户转出）
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
	public static Object[] NoPasswordTransferInterface1(CommonRequst commonRequst) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付投标类无密码转账接口调用");
			Object[] backData=new Object[3];
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
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_AUTOBID)||commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_MONEYPLANBIDFAILD)){
				umpay.setServ_type(UMPay.BUSSINESS_BIDFAILE);//标的转账接口业务类型（流标返款）
				umpay.setPartic_type(UMPay.TRABSFER_INVEST);//收款方类型
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_LOAN)||commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_MONEYPLANLOAN)){
				umpay.setServ_type(UMPay.BUSSINESS_PLATFORMFEE);//标的转账接口业务类型 放款
				umpay.setPartic_type(UMPay.TRABSFER_PLATFORM);//收款方类型
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
			logger.info("联动优势支付投标类无密码转账接口调用出错,原因:"+e.getMessage());
			return null;
		}
	
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
	 * 直接接口06
	 * 联动优势支付投标类无密码转账接口
	 * 主要针对投标类（账户转出）
	 * P2P平台发起的标的转出（对应业务场景为流标后还款、满标后缴费或放款、还款后返款、偿付后返款、债权转让的返款、撤资后的返款）请求，并完成标的账户资金划转到其他客户账户和相应记录的更新
	 * @param map
	 * @return
	 */
	public static CommonResponse NoPasswordTransferInterface(CommonRequst commonRequst) {
		Object[] backData=new Object[3];
		CommonResponse commonResponse = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
			logger.info("联动优势支付投标类无密码转账接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_TRANSFER);
			//获取流标订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			//生成订单日期
			String merDate=sdf.format(new Date());
			umpay.setMer_date(merDate);
			//发标时间标的主键Id和标的标识
			umpay.setProject_id(commonRequst.getBidType()+commonRequst.getBidId());
			//转账方类型（取值范围：01投资者02融资人03 P2P平台04担保方05资金使用方）
			//流标返款（从标的账户将钱返回给投资人）
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELBID)||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMCANCELUSER)){
				//读取联动给注册用户开的用户账号
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				umpay.setServ_type(UMPay.BUSSINESS_BIDFAILE);//标的转账接口业务类型（流标返款）
				umpay.setPartic_type(UMPay.TRABSFER_INVEST);//收款方类型
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);
				if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==1){
					umpay.setPartic_acc_type("02");//流标收款人是企业用户
					umpay.setPartic_acc_type(UMPay.PARTIPAY_PUBLIC);
				}else{
					umpay.setPartic_acc_type("01");//流标收款人是个人用户
					umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
				}
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOAN)||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMLOANUSER)){
				//读取联动给注册用户开的用户账号
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				umpay.setServ_type(UMPay.BUSSINESS_LOAN);//标的转账接口业务类型 放款
				umpay.setPartic_type(UMPay.TRABSFER_LOANER);//收款方类型
				if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==1){
					umpay.setPartic_acc_type("02");//放款收款人是企业用户
				}else{
					umpay.setPartic_acc_type("01");//放款收款人是个人用户
				}	
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMBACKMONEY)
					||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BACKDEAL)){//理财计划返款  ，债权交易返款，
				//读取联动给注册用户开的用户账号
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				umpay.setServ_type(UMPay.BUSSINESS_REPAYMENTUSER);//标的转账接口业务类型 放款
				umpay.setPartic_type(UMPay.TRABSFER_INVEST);//收款方类型
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);//标账户转出
				if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==1){
					umpay.setPartic_acc_type("02");//放款收款人是企业用户
				}else{
					umpay.setPartic_acc_type("01");//放款收款人是个人用户
				}				
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATEFORMRECHAGE)){//平台收费
				//读取联动给注册用户开的用户账号
				umpay.setPartic_user_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());//umpay.getMer_id()
				umpay.setServ_type(UMPay.BUSSINESS_PLATFORMFEE);//标的转账接口业务类型 返款
				umpay.setPartic_type(UMPay.TRABSFER_PLATFORM);//平台收款
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);
				umpay.setPartic_acc_type("02");//返款收款人是平台
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMGIVEUSER) 
					|| commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMSIGNOUTUSER)){//还款（往标的账户转账）
				umpay.setService(UMPay.SER_NOPASSWORDBID);
				umpay.setServ_type(UMPay.BUSSINESS_REPAYMENT);//标的转账接口业务类型 还款
				//标的转账方向(收入方向)
				umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
				//读取联动给注册用户开的用户账号
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				umpay.setPartic_type(UMPay.TRABSFER_LOANER);//收款方类型
				if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==1){
					umpay.setPartic_acc_type("02");//放款收款人是企业用户
				}else{
					umpay.setPartic_acc_type("01");//放款收款人是个人用户
				}	
				
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_AUTOBID)){//自动投标
				umpay.setService(UMPay.SER_NOPASSWORDBID);
				//标的转账方向(收入方向)
				umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
				umpay.setServ_type(UMPay.BUSSINESS_INVEST);//无密投标
				umpay.setPartic_type(UMPay.TRABSFER_INVEST);//转账方类型-投资人
				//转账方账户类型
				if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==1){//对公还款
					umpay.setPartic_acc_type(UMPay.PARTIPAY_PUBLIC);
					umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
					umpay.setLoan_acc_type(commonRequst.getLoanAccType());
				}else{//对私还款
					umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);
					umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				}			
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RETURNMONEY)){//投资人返款
				//读取联动给注册用户开的用户账号
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				umpay.setServ_type(UMPay.BUSSINESS_REPAYMENTUSER);//标的转账接口业务类型 放款
				if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==2){
					umpay.setServ_type(UMPay.BUSSINESS_PLATFORMFEE);//标的转账接口业务类型 放款
					umpay.setPartic_type(UMPay.TRABSFER_PLATFORM);//收款方类型 平台账户
				}else{
					umpay.setPartic_type(UMPay.TRABSFER_INVEST);//收款方类型 投资者
				}
				//标的转账方向(支出方向)
				umpay.setTrans_action(UMPay.DIRECTION_PLATFORM);//标账户转出
                 if(commonRequst.getAccountType()!=null&&!"".equals(commonRequst.getAccountType())&&(commonRequst.getAccountType()==1||commonRequst.getAccountType()==2)){
                	 umpay.setPartic_acc_type("02");//放款收款人是企业投资人                	 
                 }else{
                	 umpay.setPartic_acc_type("01");//放款收款人是个人用户
                 }
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HELPPAY)){
				//标的转账方向(收入方向)
				umpay.setService(UMPay.SER_NOPASSWORDBID);
				umpay.setTrans_action(UMPay.DIRECTION_CUSTOMER);
				umpay.setServ_type(UMPay.BUSSINESS_REPAYMENT);//标的转账接口业务类型 还款
				umpay.setPartic_type(UMPay.TRABSFER_LOANER);//转账方类型-借款人
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
				if(commonRequst.getAccountType()!=null&&commonRequst.getAccountType()==1){//对公还款
					umpay.setPartic_acc_type(UMPay.PARTIPAY_PUBLIC);
					umpay.setLoan_acc_type(commonRequst.getLoanAccType());
				}else{//对私还款
					umpay.setPartic_acc_type(UMPay.PARTIPAY_PERSON);				
				}
			}
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
			String ret=ThirdPayWebClient.getUndecodeByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付投标类无密码转账接口调用后收到的通知"+ret);
			Map<String,String> htmlReturnMap=UMPayInterfaceUtil.parseHTMLMethod(ret);
			Boolean isSign=UMPayInterfaceUtil.verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付投标类无密码转账接口调用后收到的通知后验证签名结果isSign="+isSign);
				if(htmlReturnMap.get("ret_code").equals("0000")){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("交易成功");
					commonResponse.setCommonRequst(commonRequst);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
					commonResponse.setCommonRequst(commonRequst);
				}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付投标类无密码转账接口调用出错,原因:"+e.getMessage());
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("联动优势支付投标类无密码转账接口调用出错");
			commonResponse.setCommonRequst(commonRequst);
		}
		return commonResponse;
	}
	/**
	 * 平台商户充值
	 * @param commonRequst
	 * @return
	 */
	public  static CommonResponse platformRecharge(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");  
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,true,true,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_ENPRISE_RECHARGE);
			//获取充值订单号
			umpay.setOrder_id(commonRequst.getRequsetNo());
			umpay.setGate_id(commonRequst.getBankCode());
			umpay.setPay_type(UMPay.BANK_ENTERPRISE);
			//生成订单日期
			umpay.setMer_date(sf.format(new Date()));
			umpay.setRecharge_mer_id(umpay.getMer_id());
			umpay.setAccount_type("01");
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
			String[] ret= ThirdPayWebClient.operateParameter(thirdPayConfig.get("thirdPay_umpay_URL").toString(),htmlmap,UMPay.UTF8);
			logger.info("联动优势支付个人有密码充值接口调用生成的签名sign=="+sign);
			if(ret!=null&&ret.length>1){
				if(ret[0]!=null&&ret[0].equals("SUCCESS")){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("平台充值申请提交成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("平台充值申请提交失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("接口调用异常");
			}
			commonResponse.setCommonRequst(commonRequst);
		}catch(Exception e){
			logger.info("联动优势支付充值接口调用出错,原因:"+e.getMessage());
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("联动优势支付充值接口调用出错");
			commonResponse.setCommonRequst(commonRequst);
		}
		return commonResponse;
	}
	
	/**
	 * 平台账户流水查询
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse platformQulideQuery(CommonRequst commonRequst) {
		CommonResponse response = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd"); 
			logger.info("联动优势支付标账户查询调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.SER_TRANSEQ_SEARCH);	//企业客户		
			umpay.setPartic_user_id(umpay.getMer_id());
			umpay.setAccount_type("02");
			umpay.setMer_id(umpay.getMer_id());
			//发标时间标的主键Id和标的标
			umpay.setStart_date(sf.format(commonRequst.getQueryStartDate()));
			umpay.setEnd_date(sf.format(commonRequst.getQueryEndDate()));
			umpay.setPage_num((commonRequst.getPageNumber()!=null&&!"".equals(commonRequst.getPageNumber()))?commonRequst.getPageNumber():"1");
			Map<String,String> returnMap=creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付标账户查询调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付标账户查询生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付标账户查询调用后收到的通知"+ret);
			System.out.println(ret);
			Map<String,String> htmlReturnMap=parseHTMLMethod(ret);
			if(htmlReturnMap.containsKey("trans_detail")){
				System.out.println("trans_detail=="+htmlReturnMap.get("trans_detail").toString());
			}
			Boolean isSign=true;//verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付标账户查询调用后收到的通知后验证签名结果isSign="+isSign);
			if(isSign){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					if(htmlReturnMap.containsKey("trans_detail")){
						String[] rett=htmlReturnMap.get("trans_detail").toString().split("\\|");
						if(rett!=null&&rett.length>0){
							List<CommonRecord> list=new ArrayList<CommonRecord>();
							for(int i=0;i<rett.length;i++){
								String[] retinfo=rett[i].split(",");
								if(retinfo!=null&&retinfo.length>0){
									CommonRecord record =new CommonRecord();
									for(int j=0;j<retinfo.length;j++){
										String key=retinfo[j];
										String[] keyStr=key.split("=");
										if(keyStr!=null&&keyStr.length>0){
											if(keyStr[0].equals("order_id")){//交易订单号
												if(keyStr.length>1&&!"".equals(keyStr[1])){
													record.setRequestNo(keyStr[1]);
												}
												
											}else if(keyStr[0].equals("acc_check_date")){//交易时间
												if(keyStr.length>1&&!"".equals(keyStr[1])){
													SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd"); 
													Date d = sf.parse(keyStr[1]);
													record.setTime(sf1.format(d));
												}
												
											}else if(keyStr[0].equals("amount")){//交易金额
												if(keyStr.length>1&&!"".equals(keyStr[1])){
													BigDecimal amount=new BigDecimal(keyStr[1]).divide(new BigDecimal(100),3,BigDecimal.ROUND_HALF_UP).setScale(2);
													record.setAmount(amount.toString());
												}
											}else if(keyStr[0].equals("balance")){//交易金额
												if(keyStr.length>1&&!"".equals(keyStr[1])){
													BigDecimal amount=new BigDecimal(keyStr[1]).divide(new BigDecimal(100),3,BigDecimal.ROUND_HALF_UP).setScale(2);
													record.setBalance(amount.toString());
												}

											}else if(keyStr[0].equals("com_amt")){//手续费
												if(keyStr.length>1&&!"".equals(keyStr[1])){
													BigDecimal amount=new BigDecimal(keyStr[1]).divide(new BigDecimal(100),3,BigDecimal.ROUND_HALF_UP).setScale(2);
													record.setFee(amount.toString());

												}
											}else if(keyStr[0].equals("dc_mark")){//资金借贷方向
												String  markType="";
												if(keyStr.length>1&&keyStr[1].equals("01")){
													markType="入账";
												}else if(keyStr.length>1&&keyStr[1].equals("02")){
													markType="出账";
												}else if(keyStr.length>1&&keyStr[1].equals("99")){
													markType="其它";
												}
												record.setMarkType(markType);
											}else if(keyStr[0].equals("trans_type")){//交易类型
												String type=keyStr.length>1?keyStr[1]:"";
												String msg="";
												if(type.equals("01")){
													msg="充值";
												}else if(type.equals("02")){
													msg="提现";
												}else if(type.equals("03")){
													msg="标的转账";
												}else if(type.equals("04")){
													msg="转账";
												}else if(type.equals("99")){
													msg="退费等其他交易";
												}
												record.setBizType(msg);
											}else if(keyStr[0].equals("trans_state")){//交易状态
												String type=keyStr.length>1?keyStr[1]:"";
												String msg="";
												if(type.equals("01")){
													msg="成功";
												}else if(type.equals("02")){
													msg="冲正";
												}else if(type.equals("99")){
													msg="其它";
												}
												record.setStatus(msg);
											}
											
										}
									}
									list.add(record);	
								}
							}
							if(list.size()>0){
								commonRequst.setRecord(list);
							}
						}
					}
					response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					response.setResponseMsg("查询成功");
				}else{
					response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
				}
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
				response.setResponseMsg("签名验证失败");
			}
		
		}catch(Exception e){
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			response.setResponseMsg("联动优势支付平台账户流水查询调用出错");
		}
		return response;
	
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
	
	
	/**
	 * 直连接口02
	 * 联动优势充值对账文件下载接口
	 * @param map
	 * @return
	 */
	public static CommonResponse downAccountFile_recharge(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd"); 
			logger.info("联动优势对账文件下载 接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.DOWNLOAD_SETTLE_FILE_P);
			umpay.setSettle_date_p2p(sd.format(commonRequst.getTransactionTime()));
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RECHARGEFILE)){
				umpay.setSettle_type_p2p(UMPay.DOWN_FILETYPE1);
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付账户查询接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(),params,UMPay.UTF8,12000);
			if(ret!=null){
				String[] str = ret.split(",");
				ArrayList al = new ArrayList();
				for(int i=0;i<str.length;i++){
					al.add(str[i]);
				}
				//标的对账接口
				String result = (String)al.get(5);
				List<UMPayRecharge> listRecharge = new ArrayList();
				if(str.length<16){//未返回对账记录或者当日不存在借款记录
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(result.substring(0,result.length()-15));
					commonResponse.setuMPayRechargeList(null);
				}else{//正常返回借款记录
					String ret2 = (String) ret.subSequence(57, ret.length()-33);
					String[] str1 = ret2.split(",");
					String newString = "";
					for(int i =0;i<str1.length;i++){
						if(str1[i].length()>=40){
							String order = (String) str1[i].subSequence(0, 16);
							String newOrder = str1[i].replace(order, order+",");
							str1[i] = newOrder;
						}
						newString = newString+","+str1[i];
					}
					System.out.println("新拼接的字符串是"+newString);
					String[] str5 =  newString.split(",");
					UMPayRecharge umpayRecharge = new UMPayRecharge();
					System.out.println(str5.length);
					if((str5.length-1)%8==0){//可以被8整除
						int u = str5.length/8;
						for(int i = 0;i<u;i++){
							umpayRecharge = new UMPayRecharge();
							umpayRecharge.setP2pRequestNo(str5[8*i+1]);
							System.out.println("P2P平台请求流水号"+str5[8*i+1]);
							umpayRecharge.setCreateDate(str5[8*i+2]);
							System.out.println("P2P平台交易日期"+str5[8*i+2]);
							umpayRecharge.setAccountNo(str5[8*i+3]);
							System.out.println("账户号,"+str5[8*i+3]);
							umpayRecharge.setPlateFormNo(str5[8*i+4]);
							System.out.println("商户号"+str5[8*i+4]);
							umpayRecharge.setMoney(str5[8*i+5]);
							System.out.println("金额"+str5[8*i+5]);
							umpayRecharge.setAccountNoDate(str5[8*i+6]);
							System.out.println("资金账户托管平台日期"+str5[8*i+6]);
							umpayRecharge.setAccountNoTime(str5[8*i+7]);
							System.out.println("资金账户托管平台时间"+str5[8*i+7]);
							umpayRecharge.setAccountOrderNo(str5[8*i+8]);
							System.out.println("资金账户托管平台流水号"+str5[8*i+8]);
							listRecharge.add(umpayRecharge);
						}
					}
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("生成充值对账记录成功");
					commonResponse.setuMPayRechargeList(listRecharge);
				}
			    return commonResponse;
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("查询有误");
				commonResponse.setuMPayRechargeList(null);
			    return commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付对账下载接口调用出错,原因:"+e.getMessage());
			
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("联动优势支付对账下载接口调用出错");
			commonResponse.setuMPayRechargeList(null);
		    return commonResponse;
		}
	}
	
	/**
	 * 直连接口02
	 * 联动优势提现对账文件下载接口
	 * @param map
	 * @return
	 */
	public static CommonResponse downAccountFile_withdraw(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			logger.info("联动优势对账文件下载接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.DOWNLOAD_SETTLE_FILE_P);
			umpay.setSettle_date_p2p(sd.format(commonRequst.getTransactionTime()));
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_WITHDRAWFILE)){
				umpay.setSettle_type_p2p(UMPay.DOWN_FILETYPE2);
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付账户查询接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(),params,UMPay.UTF8,12000);
          
			System.out.println(".."+ret+"...");
			String[] str = ret.split(",");
			System.out.println(str.length);
			ArrayList al = new ArrayList();
			for(int i=0;i<str.length;i++){
				al.add(str[i]);
			}
			//标的对账接口
			String result = (String)al.get(5);
			List<UMPayWithdraw> listWithdraw = new ArrayList();
			if(str.length<16){//未返回对账记录或者当日不存在提现记录
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg(result.substring(0,result.length()-15));
				commonResponse.setuMPayWithdrawList(null);
			}else{//正常返回借款记录
				String ret2 = (String) ret.subSequence(57, ret.length()-44);
				System.out.println("..."+ret2+"...");
				String[] str1 = ret2.split(",");
				String newString = "";
				for(int i =0;i<str1.length;i++){
					if(str1[i].length()>=23){
						String order = (String) str1[i].subSequence(0, 16);
						String newOrder = str1[i].replace(order, order+",");
						str1[i] = newOrder;
					}
					newString = newString+","+str1[i];
				}
				System.out.println("新拼接的字符串是"+newString);
				String[] str5 =  newString.split(",");
				System.out.println(str5.length);
				 
				if((str5.length-1)%10==0){//可以被8整除
					int u = str5.length/10;
					for(int i = 0;i<u;i++){
						UMPayWithdraw umpayWithdraw = new UMPayWithdraw();
						umpayWithdraw.setPlateFormNo(str5[10*i+1]);
						umpayWithdraw.setTransferType(str5[8*i+2]);
						
						umpayWithdraw.setPlateOrderNo(str5[10*i+3]);
						umpayWithdraw.setOrderDate(str5[10*i+4]);
						BigDecimal money=(new BigDecimal(str5[10*i+5])).divide(new BigDecimal(100));
						umpayWithdraw.setTransferMoney(money.toString());
						
						umpayWithdraw.setTransferFee(str5[10*i+6]);
						
						umpayWithdraw.setCheckAccountDate(str5[10*i+7]);
						
						umpayWithdraw.setSaveAccountDate(str5[10*i+8]);
						
						umpayWithdraw.setTransferState(new String(str5[10*i+9].getBytes("iso-8859-1"),UMPay.UTF8));
						umpayWithdraw.setTransferOrderNo(str5[10*i+10]);
						listWithdraw.add(umpayWithdraw);
					}
				}
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
				commonResponse.setResponseMsg("生成提现对账记录成功");
				commonResponse.setuMPayWithdrawList(listWithdraw);
			}
		    return commonResponse;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付对账下载接口调用出错,原因:"+e.getMessage());
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("联动优势支付对账下载接口调用出错");
			commonResponse.setuMPayWithdrawList(null);
		    return commonResponse;
		}
	}
	
	/**
	 * 直连接口02
	 * 联动优势标的对账文件下载接口
	 * @param map
	 * @return
	 * @throws IOException 
	 */
	public static CommonResponse downAccountFile(CommonRequst commonRequst)  {
		CommonResponse commonResponse = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			logger.info("联动优势对账文件下载接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.DOWNLOAD_SETTLE_FILE_P);
			umpay.setSettle_date_p2p(sd.format(commonRequst.getTransactionTime()));
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDBALANCEFILE)){
				umpay.setSettle_type_p2p(UMPay.DOWN_FILETYPE3);
			}
			//读取联动给注册用户开的用户账号
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付账户查询接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口生成的签名sign=="+sign);
		    String ret = ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(),params,UMPay.UTF8,12000);
		    System.out.println("接受到的数据=="+ret);
		    String[] str = ret.split("\n");
			
			//标的对账接口
			
			List<UMPayBidAccount> listBid = new ArrayList();
			if(str.length<3){//未返回对账记录或者当日不存在借款记录
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg(null);
				commonResponse.setuMPayBidAccountList(null);
			}else{//正常返回借款记录
				for(int i=1;i<str.length-2;i++){
					String s[]=str[i].split(",");
					UMPayBidAccount   umpayBid = new UMPayBidAccount();
					umpayBid.setBidId(s[0]);
					umpayBid.setBidAccount(s[1]);
					umpayBid.setState(s[2]);
					umpayBid.setBalance(s[3]);
					System.out.println(umpayBid.getBalance()+ "   "+s[4]);
					umpayBid.setCreateDate(s[4]);
					if(s[5]!=null&&!"".equals(s[5])){//投资人
						String[] list = s[5].split("\\|");
						List list1 = new ArrayList();
						for(int u=0;u<list.length;u++){
							list1.add(list[u]);
						}
//								System.out.println(list1);
						umpayBid.setInvestPersonList(list1);
					}
					if(s[6]!=null&&!"".equals(s[6])){
//								System.out.println("借款人"+s[6]);
						String[] list = s[6].split("\\|");
//								System.out.println("借款人"+list.length);
						List list1 = new ArrayList();
						for(int v=0;v<list.length;v++){
							list1.add(list[v]);
						}
//								System.out.println(list1);
						umpayBid.setBorrowPersonList(list1);
					}

					if(s[7]!=null&&!"".equals(s[7])){
						String[] list = s[i+8].split("\\|");
						List list1 = new ArrayList();
						for(int v=0;v<list.length;v++){
							list1.add(list[v]);
						}
//								System.out.println(list1);
						umpayBid.setMoneyUserList(list1);
					}
					if(s[8]!=null&&!"".equals(s[8])){
						String[] list = s[8].split("\\|");
						List list1 = new ArrayList();
						for(int v=0;v<list.length;v++){
							list1.add(list[v]);
						}
//								System.out.println(list1);
						umpayBid.setBondsmanList(list1);
					}
					if(s[9]!=null&&!"".equals(s[9])){
						String[] list = s[9].split("\\|");
						List list1 = new ArrayList();
						for(int v=0;v<list.length;v++){
							list1.add(list[v]);
						}
						umpayBid.setPayerList(list1);
					}
//							 System.out.println(s[0]+" *  "+s[1]+" * "+s[2]+" * "+s[3]+" * "+s[4]+" * "+s[5]
//							                     +" * "+s[6]+" * "+s[7]+" * "+s[8]+" * "+s[9]+" * ");
					listBid.add(umpayBid);
				}
                System.out.println(listBid.get(11).getBalance()+"........");    
                commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
                commonResponse.setResponseMsg("生成标的对账记录成功");
                commonResponse.setuMPayBidAccountList(listBid);
			}
		    return commonResponse;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付对账下载接口调用出错,原因:"+e.getMessage());
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("联动优势支付对账下载接口调用出错");
			commonResponse.setuMPayBidAccountList(null);
			return commonResponse;
		}
	}
	
	/**
	 * 直连接口02
	 * 联动优势标的转账对账文件下载接口
	 * @param map
	 * @return
	 */
	public static CommonResponse downAccountFile_bidTransfer(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			logger.info("联动优势对账文件下载接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.DOWNLOAD_SETTLE_FILE_P);
			umpay.setSettle_date_p2p(sd.format(commonRequst.getTransactionTime()));
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDTRANSFERFILE)){
				umpay.setSettle_type_p2p(UMPay.DOWN_FILETYPE4);
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付账户查询接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(),params,UMPay.UTF8,12000);
			
			System.out.println(".."+ret+"...");
			
			String[] str = ret.split("\n");
			System.out.println(str.length);
			System.out.println(str[1]);
			
			List<UMPayBidTransferCompare> lisBidTransfer = new ArrayList();
			if(str.length<3){//未返回对账记录或者当日不存在提现记录
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg(null);
				commonResponse.setuMPayBidTransferCompareList(null);
			}else{
				System.out.println(str.length-2);
				for(int i=1;i<=str.length-2;i++){
					String s[]=str[i].split(",");
					UMPayBidTransferCompare   umpayBid = new UMPayBidTransferCompare();
					umpayBid.setP2pRequest(s[0]);
					umpayBid.setP2pDate(s[1]);
					umpayBid.setBidId(s[2]);
					umpayBid.setPayAccount(s[3]);
					umpayBid.setIncomeNumber(s[4]);
					umpayBid.setBidAccountNumber(s[5]);
				    umpayBid.setBalance(s[6]);
					umpayBid.setDirection(s[7]);
				    umpayBid.setType(s[8]);
				    umpayBid.setPayDate(s[9]);
				    umpayBid.setPayTime(s[10]);
				    umpayBid.setPayRequest(s[11]);
				    umpayBid.setAccountDate(s[12]);
				/*
					System.out.println(s[0]+" *  "+s[1]+" * "+s[2]+" * "+s[3]+" * "+s[4]+" * "+s[5]
					                     +" * "+s[6]+" * "+s[7]+" * "+s[8]+" * "+s[9]+" * ");*/
					lisBidTransfer.add(umpayBid);
				}
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
				commonResponse.setResponseMsg("生成标的转账对账记录成功");
				commonResponse.setuMPayBidTransferCompareList(lisBidTransfer);
			}
		    return commonResponse;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付对账下载接口调用出错,原因:"+e.getMessage());
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("联动优势支付对账下载接口调用出错");
			commonResponse.setuMPayBidTransferCompareList(null);
			return commonResponse;
		}
	}
	
	/**
	 * 联动优势转账对账文件下载接口
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse downAccountFile_Transfer(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
			logger.info("联动优势对账文件下载接口调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			umpay.setService(UMPay.DOWNLOAD_SETTLE_FILE_P);
			umpay.setSettle_date_p2p(sd.format(commonRequst.getTransactionTime()));
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRANSFERFILE)){
				umpay.setSettle_type_p2p(UMPay.DOWN_FILETYPE5);
			}
			Map<String,String> returnMap=UMPayInterfaceUtil.creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付支付账户查询接口调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付支付账户查询接口生成的签名sign=="+sign);
			String ret=ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(),params,UMPay.UTF8,12000);
			System.out.println(".."+ret+"...");
			
			String[] str = ret.split("\n");
			System.out.println(str.length);
			System.out.println(str[1]);
			
			List<UMPayTransfer> lisBidTransfer = new ArrayList<UMPayTransfer>();
			if(str.length<3){//未返回对账记录或者当日不存在提现记录
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg(null);
				commonResponse.setuMPayTransferList(null);
			}else{
				System.out.println(str.length-2);
				for(int i=1;i<=str.length-2;i++){
					String s[]=str[i].split("\\,");
					UMPayTransfer   umpayBid = new UMPayTransfer();
					umpayBid.setTransferOrderNo(s[0]);
					umpayBid.setTransferData(s[1]);
					umpayBid.setTransferPay(s[2]);
					umpayBid.setTransferRectipt(s[3]);
					umpayBid.setTransferMoney(s[4]);
					umpayBid.setTransferPayData(s[5]);
				    umpayBid.setTransferPayTime(s[6]);
				    umpayBid.setTransferPayOrderNo(s[7]);
				    umpayBid.setTransferAccountData(s[8]);
					/*System.out.println(s[0]+" * "+s[1]+" * "+s[2]+" * "+s[3]+" * "+s[4]+" * "+s[5]
					                     +" * "+s[6]+" * "+s[7]+" * "+s[8]);*/
					lisBidTransfer.add(umpayBid);
				}
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
				commonResponse.setResponseMsg("生成转账对账记录成功");
				commonResponse.setuMPayTransferList(null); 
			}
		    return commonResponse;
		}catch(Exception e){
			e.printStackTrace();
			logger.info("联动优势支付对账下载接口调用出错,原因:"+e.getMessage());
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("联动优势支付对账下载接口调用出错");
			commonResponse.setuMPayTransferList(null);
			return commonResponse;
		}
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
		    							commonResponse.setResponseMsg("开通授权平台还款成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("开通授权平台还款失败，失败原因："+bind[2]);
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
		    							commonResponse.setResponseMsg("关闭授权平台还款成功");
	    							}else{
	    								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    							commonResponse.setResponseMsg("关闭授权平台还款失败，失败原因："+bind[2]);
	    							}
	    						}
	    						requestValue.remove(map.get("user_bind_agreement_list")+map.get("user_id").toString());
	    					}
	    				}else if (service.equals(UMPay.SER_TRANSFER)){//标类转账交易通知
	    					logger.info("联动优势服务器端回调函数通知调用标类转账操作方法开始");
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						commonResponse.setBussinessType(service);
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    							commonResponse.setResponseMsg("标账户转账成功");
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("标账户转账失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
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
		    							commonResponse.setResponseMsg("自动授权无密码交易授权失败，失败原因："+bind[2]);
	    							}
	    						}
	    						requestValue.remove(map.get("user_bind_agreement_list")+map.get("user_id").toString());
	    					}
	    				}else if (service.equals(UMPay.SER_TRANSFER)){//标类转账交易通知
	    					logger.info("联动优势服务器端回调函数通知调用标类转账操作方法开始");
	    					if(!requestValue.containsKey(map.get("order_id"))){
	    						requestValue.put(map.get("order_id"),map.get("order_id"));
	    					}
	    					synchronized(requestValue.get(map.get("order_id"))){
	    			          //TODO 添加处理业务方法
	    						commonResponse.setRequestNo(map.get("order_id"));
	    						commonResponse.setBussinessType(service);
	    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    							commonResponse.setResponseMsg("标账户转账成功");
	    						}else{
	    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    							commonResponse.setResponseMsg("标账户转账失败");
	    						}
	    						requestValue.remove(map.get("order_id"));
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
	/**
	 * 直连接口11
	 * 联动优势支付账户的流水查询查询接口
	 * @param map
	 * @return
	 */
	public static CommonResponse accountQulideQuery(CommonRequst commonRequst) {
		Object[] backData=new Object[3];
		CommonResponse response = new CommonResponse();
		try{
			Map thirdPayConfig=umProperty();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd"); 
			logger.info("联动优势支付标账户查询调用");
			UMPay umpay= new UMPay();
			umpay=UMPayInterfaceUtil.generalPublicDate(umpay,false,false,commonRequst.getBussinessType());
			String queryAccountType=commonRequst.getBussinessType();
			umpay.setService("transfer_search");
			if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDFLOWQUERY)){//标的账户流水查询
				umpay.setService("project_account_search");
				//umpay.setMer_date(sf.format(commonRequst.getTransactionTime()));
				umpay.setProject_id(commonRequst.getBidType()+commonRequst.getBidId());
				umpay.setMer_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());
				umpay.setAccount_type("03");
				umpay.setBusi_type("03");
				umpay.setOrder_id(commonRequst.getQueryRequsetNo());
				umpay.setPartic_user_id(commonRequst.getThirdPayConfigId());
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYPLATF)){
				if(commonRequst.getQueryType().equals("RECHARGE_RECORD")){
					//充值流水查询
					umpay.setMer_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());
					umpay.setQuery_mer_id(commonRequst.getThirdPayConfigId());
					umpay.setOrder_id(commonRequst.getQueryRequsetNo().toString());
					umpay.setBusi_type(commonRequst.getRemark1()!=null?commonRequst.getRemark1():"01");
					umpay.setUser_id(commonRequst.getThirdPayConfigId());
					umpay.setIs_find_account("01");
					if(commonRequst.getTransactionTime()!=null){
						umpay.setMer_date(sf.format(commonRequst.getTransactionTime()));
					}
				}else if(commonRequst.getQueryType().equals("WITHDRAW_RECORD")){
					//提现流水查询
					umpay.setMer_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());
					umpay.setQuery_mer_id(commonRequst.getThirdPayConfigId());
					umpay.setOrder_id(commonRequst.getQueryRequsetNo().toString());
					umpay.setBusi_type(commonRequst.getRemark1()!=null?commonRequst.getRemark1():"02");
					umpay.setUser_id(commonRequst.getThirdPayConfigId());
					umpay.setIs_find_account("01");
					if(commonRequst.getTransactionTime()!=null){
						umpay.setMer_date(sf.format(commonRequst.getTransactionTime()));
					}				
				}else if(commonRequst.getQueryType().equals("REPAYMENT_RECORD")||commonRequst.getQueryType().equals("PAYMENT_RECORD")){
					//放款及还款查询
					umpay.setMer_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());
					umpay.setQuery_mer_id(commonRequst.getThirdPayConfigId());
					umpay.setProject_id(commonRequst.getBidType()+commonRequst.getBidId());
					umpay.setOrder_id(commonRequst.getQueryRequsetNo().toString());
					umpay.setBusi_type(commonRequst.getRemark1()!=null?commonRequst.getRemark1():"03");
					umpay.setUser_id(commonRequst.getThirdPayConfigId());
					umpay.setIs_find_account("01");
					if(commonRequst.getTransactionTime()!=null){
						umpay.setMer_date(sf.format(commonRequst.getTransactionTime()));
					}
				}
			}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYACCOUNT)){//平台账户流水查询
				umpay.setService("transeq_search");
				umpay.setAccount_type("02");
				umpay.setAccount_id(thirdPayConfig.get("thirdPay_umpay_MemberID").toString());
			}
			//发标时间标的主键Id和标的标
			if(commonRequst.getQueryStartDate()!=null){
				umpay.setStart_date(sf.format(commonRequst.getQueryStartDate()));
			}
			if(commonRequst.getQueryEndDate()!=null){
				umpay.setEnd_date(sf.format(commonRequst.getQueryEndDate()));
			}
			umpay.setPage_num((commonRequst.getPageNumber()!=null&&!"".equals(commonRequst.getPageNumber()))?commonRequst.getPageNumber():"1");
			Map<String,String> returnMap=creatDataMap(umpay);
			String params=returnMap.get("plain").toString().trim();
			System.out.println("生成的传输参数params=="+params);
			logger.info("联动优势支付标账户查询调用生成的传输参数params=="+params);
			String sign=returnMap.get("sign").toString().trim();
			System.out.println("生成的签名sign=="+sign);
			logger.info("联动优势支付标账户查询生成的签名sign=="+sign);
			logger.info("联动优势支付标账户查询调用的url=="+thirdPayConfig.get("thirdPay_umpay_URL").toString());
			String ret=ThirdPayWebClient.getWebContentByPost(thirdPayConfig.get("thirdPay_umpay_URL").toString(), params,UMPay.UTF8,12000);
			logger.info("联动优势支付标账户查询调用后收到的通知"+ret);
			System.out.println(ret);
			Map<String,String> htmlReturnMap=parseHTMLMethod(ret);
			if(htmlReturnMap.containsKey("trans_detail")){
				System.out.println("trans_detail=="+htmlReturnMap.get("trans_detail").toString());
			}
			Boolean isSign=true;//verifySign(htmlReturnMap);
			System.out.println("验证签名isSign=="+isSign);
			logger.info("联动优势支付标账户查询调用后收到的通知后验证签名结果isSign="+isSign);
			if(isSign){
				if(htmlReturnMap.get("ret_code").equals("0000")){
					List<CommonRecord> list=new ArrayList<CommonRecord>();
					CommonRecord p1 =new CommonRecord();
					//查询标的账户 解析数据单独处理
					if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDFLOWQUERY)){
						QueryFilter filter  = new QueryFilter();
						filter.addFilter("Q_bidId_L_EQ", Long.valueOf(commonRequst.getBidId()));
						filter.addFilter("Q_recordNumber_NOTNULL",null);
						filter.addFilter("Q_code_S_EQ", "responsecode_success");
						List<ThirdPayRecord> list1 = thirdPayRecordService.getAll(filter);
						ThirdPayRecord record2 = list1.get(list1.size()-1);
						p1.setRequestNo(record2.getRecordNumber());//交易流水号
						p1.setTransferType(record2.getInterfaceName());//交易类型（方向）
						p1.setTime(record2.getRequestTime().toString());
						if(record2.getDealMoney()!=null){//交易金额
							p1.setAmount(record2.getDealMoney().toString());
						}
						if(htmlReturnMap.containsKey("balance")){//剩余金额
							p1.setLeftMoney((new BigDecimal(htmlReturnMap.get("balance").toString()).divide(new BigDecimal(100))).toString());
						}
						if(htmlReturnMap.containsKey("project_account_state")){//交易状态
							if(htmlReturnMap.get("project_account_state").toString().equals("-1")){//取消-1
								p1.setStatus("取消");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("90")){//初始90
								p1.setStatus("初始");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("91")){//	建标中91
								p1.setStatus("建标中");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("92")){//建标成功92
								p1.setStatus("建标成功");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("93")){//建标失败93
								p1.setStatus("建标失败");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("94")){//标的锁定94
								p1.setStatus("标的锁定");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("0")){//开标0
								p1.setStatus("开标");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("01")){//投资中1
								p1.setStatus("投资中");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("02")){//还款中2
								p1.setStatus("还款中");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("03")){//已还款3
								p1.setStatus("已还款");
							}else if(htmlReturnMap.get("project_account_state").toString().equals("04")){//结束4
								p1.setStatus("结束");
							}
						}
						list.add(p1);
					}else{//其他业务统一处理
						if(commonRequst.getQueryRequsetNo()!=null&&!"".equals(commonRequst.getQueryRequsetNo())){
							ThirdPayRecord record = thirdPayRecordService.getByOrderNo(commonRequst.getQueryRequsetNo());
							if(record.getThirdPayFlagId()!=null){
								p1.setUserNo(record.getThirdPayFlagId());
								p1.setSourceUserNo(record.getThirdPayFlagId());
								p1.setTargetUserNo(record.getThirdPayFlagId());
							}
						}
						if(htmlReturnMap.containsKey("amount")){
							p1.setAmount((new BigDecimal(htmlReturnMap.get("amount").toString()).divide(new BigDecimal(100)).toString()));
							p1.setPaymentAmount((new BigDecimal(htmlReturnMap.get("amount").toString()).divide(new BigDecimal(100)).toString()));
							p1.setRepaymentAmount((new BigDecimal(htmlReturnMap.get("amount").toString()).divide(new BigDecimal(100)).toString()));
						}
						if(htmlReturnMap.containsKey("tran_state")){
							if(htmlReturnMap.get("tran_state").equals("2")){
								p1.setStatus("交易成功");
							}else{
								p1.setStatus("交易失败");
							}
						}
						if(htmlReturnMap.containsKey("mer_date")){
							p1.setCreateTime(htmlReturnMap.get("mer_date"));
						}
						list.add(p1);
					}
					response.setRecordList(list);
					response.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					response.setResponseMsg("查询成功");
					response.setCommonRequst(commonRequst);
				}else{
					response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					response.setResponseMsg(htmlReturnMap.get("ret_msg").toString());
					response.setCommonRequst(commonRequst);
				}
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				response.setResponseMsg("签名验证失败");
				response.setCommonRequst(commonRequst);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("联动优势支付标账户查询调用出错");
			response.setCommonRequst(commonRequst);
			logger.info("联动优势支付标账户查询调用出错,原因:"+e.getMessage());
		}
		return response;
	}
}
