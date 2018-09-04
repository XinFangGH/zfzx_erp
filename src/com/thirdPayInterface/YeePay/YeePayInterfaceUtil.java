package com.thirdPayInterface.YeePay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.xml.bind.JAXB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;
import com.thirdPayInterface.YeePay.Transfer;
import com.thirdPayInterface.YeePay.YeePay;
import com.thirdPayInterface.YeePay.YeePayReponse;
import com.thirdPayInterface.CommonDetail;
import com.thirdPayInterface.CommonRecord;
import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayWebClient;
import com.thirdPayInterface.YeePay.YeePayUtil.SignUtil;

@SuppressWarnings("unchecked")
public class YeePayInterfaceUtil {
	private static Log logger=LogFactory.getLog(YeePayInterfaceUtil.class);
    /**
     * 获取系统配置参数
     */
	private static Map configMap = AppUtil.getConfigMap();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	/**
	 * 获取
	 * @return
	 */
	private static String yeePayUrl(){
		String yeePayUrl="";
		Map yeePayConfigMap=yeePayProperty();
		//易宝支付调用地址
		yeePayUrl=yeePayConfigMap.get("thirdPay_yeepay_URL").toString();
		return yeePayUrl;
	}
	
	/**
	 * 获取易宝支付的第三方支付环境参数（用来获取正式环境和测试环境的新浪支付的支付环境）
	 * @return
	 */
	private static Map yeePayProperty(){
		Map yeePayConfigMap=new HashMap();
		try{
			InputStream in=null;
			//获取当前支付环境为正式环境还是测试环境
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
		       in = YeePayInterfaceUtil.class.getResourceAsStream("YeePayNormalEnvironment.properties"); 
			}else{
		        in = YeePayInterfaceUtil.class.getResourceAsStream("YeePayTestEnvironment.properties"); 
			}
			Properties props =  new  Properties(); 
			if(in!=null){
				props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		yeePayConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    		logger.error(ex.getMessage());
    	}
		return yeePayConfigMap;
	}
	
	public static YeePay generatePublicData(boolean pageCallBack, boolean notifyCallBack) {
		Map thirdPayConfig=yeePayProperty();
		if(thirdPayConfig!=null){
			YeePay yeepay=new YeePay();
			yeepay.setPlatformNo(thirdPayConfig.get("thirdPay_yeepay_platformNo").toString().trim());
			String erpUrl =(String) configMap.get("erpURL");
			String BasePath = "";
//			if(erpUrl!=null&&!erpUrl.equals("")){
//				BasePath=erpUrl;
//			}else{
				BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
//			}
			if(notifyCallBack){
				yeepay.setNotifyUrl(BasePath+thirdPayConfig.get("thirdPay_yeepay_notifyUrl").toString().trim());
			}
			if(pageCallBack){
				yeepay.setCallbackUrl(BasePath+thirdPayConfig.get("thirdPay_yeepay_callbackUrl").toString().trim());//页面回跳 URL
			}
			return yeepay;
		}else{
			return null;
		}
		
	}
	
	
	/**2014-07-15
	 * 得到reg与sign
	 * @param yeepay
	 * @return
	 */
	public static Map<String, String> getReqSign(YeePay yeepay,String service){
		Map<String, String> params = new HashMap<String, String>();
		StringWriter w = new StringWriter();
		JAXB.marshal(yeepay, w);
		String s = w.toString().trim();
		s = s.replaceAll(Pattern.quote("\n"), "");
		s = s.replace("&lt;", "<").replace("&gt;", ">");
		params.put("req", s);
		if(service!=null&&!"".equals(service)){
			params.put("service", service);
		}
		//获取支付环境参数，生成签名
		Map thirdPayConfig=yeePayProperty();
		String password=thirdPayConfig.get("thirdPay_yeepay_phapassword").toString();
		String filePath=thirdPayConfig.get("thirdPay_yeepay_phaKey").toString();
		filePath=AppUtil.getAppAbsolutePath()+filePath;
		String url = filePath.replace("\\", "/");
		String sign=SignUtil.sign(s, url, password);
		params.put("sign", sign);
		return params;
	}
	
	/**
	 * 将String 类型的xml  转换为实体对象T
	 * @param <T>
	 * @param outStr
	 * @param type
	 * @return
	 */
	public static <T> T JAXBunmarshal(String outStr,Class<T> type) throws Exception{
		InputStream is = new ByteArrayInputStream(outStr.getBytes("UTF-8"));
		return (T) JAXB.unmarshal(is, YeePayReponse.class);
	}
	
	public static Boolean verifySign(String notify, String sign) {
		Boolean issign=false;
		//获取返回支付环境参数，验证签名正确与否
		Map thirdPayConfig=yeePayProperty();
		String signverify=thirdPayConfig.get("thirdPay_yeepay_signverify").toString().trim();
		if(signverify.equals("no")){
			issign=true;
		}else{
			issign=SignUtil.verifySign(notify, sign,signverify);
		}
		return issign;
	}
	/**
	 * 读取配置文件的信息
	 * @return
	 */
	public static Properties getyeePayProperties() {
		InputStream inputStream = SignUtil.class.getResourceAsStream("YeePay.properties");   
		  Properties p = new Properties();   
		  try {   
		   p.load(inputStream);  
		  } catch (IOException e1) {   
		   e1.printStackTrace();   
		  } 
		return p;
	}
	/**
	 * 拼接property
	 * @param extend
	 * @return
	 */
	public static String strProperty(Properties p){
		StringBuffer sb = new StringBuffer();
		if(p!=null){
			String str = p.toString().replace("{", "").replace("}", "");
			String strs[] = str.split(",");
			for(int i=0;i<strs.length;i++){
				String pro[] = strs[i].split("=");
				sb.append("<property name='"+pro[0].trim()+"' value='"+pro[1]+"'/>");
			}
		}
		return sb.toString().replace("'", "\"");
	}
//==================================接口开始=========================================
	/**
	 * 网关接口01--个人用户注册
	 */
	public static CommonResponse register(CommonRequst commonRequst){
		logger.info("调用易宝支付网关个人用户注册接口开始时间："+new Date());
		CommonResponse commonResponse=new CommonResponse();
		commonResponse.setReturnType(CommonResponse.RETURNTYPE_HTML);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getRequsetNo());//
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号(做过处理)
				yeepay.setRealName(commonRequst.getTrueName().trim());//会员真实姓名
				yeepay.setIdCardType("G2_IDCARD");//身份证类型 
				yeepay.setIdCardNo(commonRequst.getCardNumber());//身份证号 
				yeepay.setMobile(commonRequst.getTelephone());//手机号
				if(commonRequst.getEmail()!=null&&!"".equals(commonRequst.getEmail())){
					yeepay.setEmail(commonRequst.getEmail());//邮箱
				}else{
					yeepay.setEmail("无");//邮箱
				}
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String[] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTOREG, params,YeePay.CHARSETUTF8);
				logger.info("用户开户注册调用易宝参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("成功调用");
					commonResponse.setCommonRequst(commonRequst);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			    	commonResponse.setResponseMsg("系统报错");
			    	commonResponse.setCommonRequst(commonRequst);
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    	commonResponse.setResponseMsg("基本参数获取失败");
		    	commonResponse.setCommonRequst(commonRequst);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	commonResponse.setResponseMsg("个人用户注册对接失败");
	    	commonResponse.setCommonRequst(commonRequst);
		}
		
		return commonResponse;
	}

	/**
	 * 网关接口02--企业用户注册
	 * @return
	 */
	public static CommonResponse enterRegister(CommonRequst commonRequst){
		logger.info("调用易宝支付网关企业用户注册接口开始时间："+new Date());
		CommonResponse commonResponse=new CommonResponse();
		commonResponse.setReturnType(CommonResponse.RETURNTYPE_HTML);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getRequsetNo());//
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号(做过处理)
				yeepay.setEnterpriseName(commonRequst.getEnterpriseName());//企业用户名称
				yeepay.setBankLicense(commonRequst.getBankLicense());//企业银行许可证
				yeepay.setOrgNo(commonRequst.getOrgNo());//企业组织机构代码证
				yeepay.setBusinessLicense(commonRequst.getBusinessLicense());//企业营业执照代码证
				yeepay.setTaxNo(commonRequst.getTaxNo());//企业税务登记证
				yeepay.setLegal(commonRequst.getLegal());//企业法人
				yeepay.setLegalIdNo(commonRequst.getLegalIdNo());//企业法人身份证号码
				yeepay.setContact(commonRequst.getContact());//企业联系人
				yeepay.setContactPhone(commonRequst.getContactPhone());//企业联系人电话
				yeepay.setMemberClassType("ENTERPRISE");
				if(commonRequst.getEmail()!=null&&!"".equals(commonRequst.getEmail())){//邮箱
					yeepay.setEmail(commonRequst.getEmail());//邮箱
				}else{
					yeepay.setEmail("无");//邮箱
				}
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String[] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.ENTERPRISEREGISTER, params,YeePay.CHARSETUTF8);
				logger.info("用户开户注册调用易宝参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){//
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("企业用户注册申请提交成功");
					commonResponse.setCommonRequst(commonRequst);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			    	commonResponse.setResponseMsg("企业用户注册申请提交失败");
			    	commonResponse.setCommonRequst(commonRequst);
					
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    	commonResponse.setResponseMsg("基本参数获取失败");
		    	commonResponse.setCommonRequst(commonRequst);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	commonResponse.setResponseMsg("企业用户注册接口对接失败");
	    	commonResponse.setCommonRequst(commonRequst);
		}
		return commonResponse;
	}

	/** 
	 * 网关接口03--用户充值
	 */
	public static CommonResponse recharge(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户充值接口开始时间："+new Date());
		CommonResponse commonResponse=new CommonResponse();
		commonResponse.setReturnType(CommonResponse.RETURNTYPE_HTML);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				if(commonRequst.getThirdPayConfigId().equals("platform")){
					yeepay.setPlatformUserNo(yeepay.getPlatformNo());//平台
				}else{
					yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				}
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setAmount(commonRequst.getAmount().toString());//充值金额（非必填）
				yeepay.setFeeMode("PLATFORM");//费率模式
				yeepay.setPayProduct(commonRequst.getPayProduct());//是网关还是快捷支付
				Map<String, String> params=getReqSign(yeepay,null);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTORECH, params,YeePay.CHARSETUTF8);
				
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){//
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("用户充值申请提交");
					commonResponse.setCommonRequst(commonRequst);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			    	commonResponse.setResponseMsg(rett[1]);
			    	commonResponse.setCommonRequst(commonRequst);
				}
				logger.info("用户充值调用易宝参数："+commonResponse);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    	commonResponse.setResponseMsg("基本参数获取失败");
		    	commonResponse.setCommonRequst(commonRequst);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SYSTEMERROR);
	    	commonResponse.setResponseMsg("网关用户充值接口对接失败");
	    	commonResponse.setCommonRequst(commonRequst);
		}
		return commonResponse;
	}
	
	/** 
	 * 网关接口04--用户取现
	 */
	public static CommonResponse toWithdraw(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户取现接口开始时间："+new Date());
		CommonResponse commonResponse=new CommonResponse();
		commonResponse.setReturnType(CommonResponse.RETURNTYPE_HTML);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setAmount(commonRequst.getAmount().toString());//取现金额
				//谁垫付取现手续费
//				BigDecimal poundage=commonRequst.getFee();
//				if(poundage.compareTo(poundage)>0){//客户自行支付手续费
//					yeepay.setFeeMode("USER");
//				}else{//平台支付取现手续费
//					yeepay.setFeeMode("PLATFORM");
//				}
				Map thirdPayConfig=yeePayProperty();
				String poundage=thirdPayConfig.get("poundage").toString().trim();
				if(Integer.parseInt(poundage)>0){//客户自行支付手续费
					yeepay.setFeeMode("USER");
				}else{//平台支付取现手续费
					yeepay.setFeeMode("PLATFORM");
				}
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.TOWITHDRAW, params,YeePay.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){//
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("用户取现申请提交");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(rett[1]);
				}
				logger.info("用户取现调用易宝参数："+rett[1]);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户取现接口对接失败"+e.getMessage());
		}
		return commonResponse;
	}
	
	/** 
	 * 网关接口05--用户绑定银行卡接口
	 */
	public  static CommonResponse toBindBankCard(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户取现接口开始时间："+new Date());
		CommonResponse commonResponse=new CommonResponse();
		commonResponse.setReturnType(CommonResponse.RETURNTYPE_HTML);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//绑定银行卡流水号
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.TOBINDBANKCARD, params,YeePay.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){//
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("用户绑定银行卡申请提交");
					commonResponse.setCommonRequst(commonRequst);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(rett[1]);
					commonResponse.setCommonRequst(commonRequst);
				}
				logger.info("用户绑定银行卡调用易宝参数："+rett[1]);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户绑定银行卡接口对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			
		}
		return commonResponse;
	}
	
	/**
	 * 网关接口06--用户取消绑定银行卡
	 */
	public  static CommonResponse cancelBindBankCard(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户取消绑定银行卡接口开始时间："+new Date());
		CommonResponse commonResponse=new CommonResponse();
		commonResponse.setReturnType(CommonResponse.RETURNTYPE_HTML);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//绑定银行卡流水号
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTOUNBINDBANKCARD, params,YeePay.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){//
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("用户取消绑定银行卡申请提交");
					commonResponse.setCommonRequst(commonRequst);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(rett[1]);
					commonResponse.setCommonRequst(commonRequst);
				}
				logger.info("用户取消绑定银行卡调用易宝参数："+rett[1]);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户取消绑定银行卡接口对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
		}
		return commonResponse;
	}
	
	/** 
	 * 网关接口07--用户自动投标授权	 
	 *  2014-07-15
	 */
	public static CommonResponse autoTransferAuthorization(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户自动投标授权接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			cr.setCommonRequst(commonRequst);
			if(null != yeepay){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//自动投标授权流水号
				Map<String, String> params=getReqSign(yeepay,null);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String[] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTOAUTHORIZEAUTOTRANSFER, params,YeePay.CHARSETUTF8);
				
				//cr.setRequestInfo(rett[1]);
				cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
				
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					cr.setResponseMsg("用户自动投标授权申请提交");
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg(rett[1]);
				}
				logger.info("用户自动投标授权调用易宝参数："+rett[1]);
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			cr.setResponsecode(ThirdPayConstants.RECOD_FAILD);
			cr.setResponseMsg("用户自动投标授权接口对接失败"+e.getMessage());
			e.printStackTrace();
		}
		return cr;
	}
	
	/**
	 * 网关接口08--用户投标（资金冻结）接口
	 */
	public static CommonResponse fiancialTransfer(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户投标接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			cr.setCommonRequst(commonRequst);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				yeepay.setTransferAmount(commonRequst.getPlanMoney().toString());//标的金额
				yeepay.setTargetPlatformUserNo(commonRequst.getLoaner_thirdPayflagId());//目标收款人
				yeepay.setPaymentAmount(commonRequst.getAmount().toString());//冻结金额
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPTOTRANSFER, params,YeePay.CHARSETUTF8);
				cr.setRequestInfo(rett[1]);
				cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
						cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						cr.setResponseMsg("用户投标申请提交");
				}else{
						cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						cr.setResponseMsg(rett[1]);
				}
				logger.info("用户投标调用易宝参数："+rett[1]);
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户投标接口对接失败"+e.getMessage());
		}
		return cr;
	}
	
	/**
	 * 网关接口09--用户自动还款授权	 
     *  2014-07-15
	 */
	public static CommonResponse autoRepaymentAuthorization(CommonRequst commonRequst){
		logger.info("调用易宝支付网关用户自动还款授权接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			cr.setCommonRequst(commonRequst);
			if(null != yeepay){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String[] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTOAUTHORIZEAUTOREPAYMENT, params,YeePay.CHARSETUTF8);
				
				cr.setRequestInfo(rett[1]);
				cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
				
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					cr.setResponseMsg("用户自动还款授权申请提交");
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("用户自动还款授权申请失败");
				}
				logger.info("用户自动还款授权调用易宝参数："+rett[1]);
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户自动还款授权接口对接失败"+e.getMessage());
			e.printStackTrace();
		}
		return cr;
	}
	
	/**网关接口10--用户还款接口
	 * @return
	 */
	public static CommonResponse toRepaymentByLoaner(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户还款接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			cr.setCommonRequst(commonRequst);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				if(commonRequst.getRepaymemntList()!=null){//判断还款list是否为空
					List<Repayment> Transfer=new ArrayList<Repayment>();
					for(CommonRequestInvestRecord temp:commonRequst.getRepaymemntList()){
						Repayment rep = new Repayment();
						rep.setTargetUserNo(temp.getInvest_thirdPayConfigId());
						rep.setPaymentRequestNo(temp.getBidRequestNo());
						rep.setAmount(temp.getAmount().toString());
						rep.setFee(temp.getFee().toString());
						Transfer.add(rep);
					}
					yeepay.setRepayments(Transfer);
					Map<String, String> params=getReqSign(yeepay,null);
					System.out.println(params);
					//易宝支付调用地址
					String yeepayurl=yeePayUrl();
					String[] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTOREPAYMENT, params,YeePay.CHARSETUTF8);
					
//					cr.setRequestInfo(rett[1]);
					cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
					
					if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
						cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						cr.setResponseMsg("用户还款申请提交");
					}else{
						cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						cr.setResponseMsg(rett[1]);
					}
					logger.info("用户还款调用易宝参数："+rett[1]);
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("还款list不能为空");
				}
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			cr.setResponsecode(CommonResponse.RESPONSECODE_SYSTEMERROR);
			cr.setResponseMsg("用户还款接口对接失败"+e.getMessage());
			e.printStackTrace();
		}
		return cr;
	}
	/**
	 * 网关接口11--用户债权交易接口
	 * @param map
	 * @return
	 */
	public static CommonResponse obligationDeal(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户债权交易接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			cr.setCommonRequst(commonRequst);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//新的投标流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				yeepay.setAmount(commonRequst.getAmount().toString());//交易金额
				yeepay.setPaymentRequestNo(commonRequst.getQueryRequsetNo().toString());
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTRANSFERDEALINTEFACE, params,YeePay.CHARSETUTF8);
				
				//cr.setRequestInfo(rett[1]);
				cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
				
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){//
					cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					cr.setResponseMsg("用户债权交易申请提交");
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg(rett[1]);
				}
				logger.info("用户债权交易调用易宝参数："+rett[1]);
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户债权交易接口对接失败"+e.getMessage());
		}
		return cr;
	}
	
	
	/**
	 * 网关接口12--用户通用转账授权接口（给平台转账方法）
	 * @param map
	 * @return
	 */
	public static CommonResponse ommontransferIntrface(CommonRequst commonRequst){
		logger.info("调用易宝支付网关用户通用转账授权接口（给平台转账方法）开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			cr.setCommonRequst(commonRequst);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//交易流水号
				yeepay.setUserType("MEMBER");//平台用户类型
				yeepay.setBizType("TRANSFER");
				if(commonRequst.getDetailList()!=null){//是否存放了转账列表
					List<Detail> list=new ArrayList();
					for(CommonDetail temp:commonRequst.getDetailList()){
						Detail detail =new Detail();
						detail.setBizType("TRANSFER");
						detail.setAmount(temp.getAmount());
						if(temp.getTargetUserType().equals("plateForm")){
							detail.setTargetUserType("MERCHANT");
							detail.setTargetPlatformUserNo(yeepay.getPlatformNo());
						}else{
							detail.setTargetUserType("MEMBER");
							detail.setTargetPlatformUserNo(temp.getTargetPlatformUserNo());
						}
						list.add(detail);
					}
					yeepay.setDetails(list);
					Map<String, String> params=getReqSign(yeepay,null);
					System.out.println(params);
					//易宝支付调用地址
					String yeepayurl=yeePayUrl();
					String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYCOMMONTTRANSFER, params,YeePay.CHARSETUTF8);
					
					//cr.setRequestInfo(rett[1]);
					cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
					
					if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
						cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						cr.setResponseMsg("用户通用转账授权接口（给平台转账方法）交易申请提交");
					}else{
						cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						cr.setResponseMsg(rett[1]);
					}
					logger.info("用户通用转账授权接口（给平台转账方法）交易调用易宝参数："+rett[1]);
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("转账列表不能为空");
				}
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户债权交易接口对接失败"+e.getMessage());
		}
		return cr;
	}
	
	
	/**网关接口13--担保公司代偿接口
	 * @return
	 */
	public static CommonResponse guanteeRepayMent(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关担保公司代偿接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台担保公司编号标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				if(commonRequst.getRepaymemntList()!=null){//判断代偿还款list是否为空
					List<Repayment> Transfer=new ArrayList<Repayment>();
					for(CommonRequestInvestRecord temp:commonRequst.getRepaymemntList()){
						Repayment rep = new Repayment();
						rep.setTargetUserNo(temp.getInvest_thirdPayConfigId());
						rep.setPaymentRequestNo(temp.getBidRequestNo());
						rep.setAmount(temp.getAmount().toString());
						rep.setFee(temp.getFee().toString());
						Transfer.add(rep);
					}
					yeepay.setRepayments(Transfer);
					Map<String, String> params=getReqSign(yeepay,null);
					System.out.println(params);
					//易宝支付调用地址
					String yeepayurl=yeePayUrl();
					String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYGUANTEEREPAYMENT, params,YeePay.CHARSETUTF8);
					if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){//
						cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						cr.setResponseMsg("担保公司代偿申请提交");
					}else{
						cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						cr.setResponseMsg("担保公司代偿申请失败");
					}
					logger.info("担保公司代偿调用易宝参数："+ret[1]);
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("代偿还款list不能为空");
				}
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("担保公司代偿接口对接失败");
		}
		return cr;
		
	}
	
	/**
	 * 网关接口14--用户重置交易密码	 
     *  2014-07-15
	 */
	public static CommonResponse resetPassWord(CommonRequst commonRequst){
		logger.info("调用易宝支付网关用户重置交易密码接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYRESETPWD, params,YeePay.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					cr.setResponseMsg("用户重置交易密码申请提交");
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("担保公司代偿申请失败");
				}
				logger.info("用户重置交易密码调用易宝参数："+ret[1]);
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户重置交易密码接口对接失败");
		}
		return cr;
	}
	
	
	/**
	 * 网关接口15--修改手机号	 
     *  2014-07-15
	 */
	public static CommonResponse toResetMobile(CommonRequst commonRequst){
		logger.info("调用易宝支付网关修改手机号接口开始时间："+new Date());
		CommonResponse cr = new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(true,true);
			cr.setCommonRequst(commonRequst);
			if(yeepay!=null){
				yeepay.setRequestNo(commonRequst.getRequsetNo());
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());
				yeepay.setMobile(commonRequst.getTelephone());
				Map<String, String> params=getReqSign(yeepay,null);
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String [] rett=ThirdPayWebClient.operateParameter(yeepayurl+YeePay.YEEPAYTORESETMOBILE, params,YeePay.CHARSETUTF8);
				
				cr.setRequestInfo(rett[1]);
				cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
				
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					cr.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					cr.setResponseMsg("修改手机号申请提交");
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg(rett[1]);
				}
				logger.info("修改手机号调用易宝参数："+rett[1]);
			}else{	
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("修改手机号接口对接失败"+e.getMessage());
		}
		return cr;
	}
	
	
	
	/**
     *  直连接口01--注册用户查询接口
	 */
	public static CommonResponse registerQuery(CommonRequst commonRequst) {
		logger.info("调用易宝支付直连注册用户查询接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				if(commonRequst.getThirdPayConfigId().equals("platform")){
					yeepay.setPlatformUserNo(yeepay.getPlatformNo());//查询平台
				}else{
					yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				}
				Map<String, String> params=getReqSign(yeepay,"ACCOUNT_INFO");;
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("注册用户查询请求参数："+param);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("注册用户查询结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					Properties p=getyeePayProperties();
					if(response.getCode().equals("1")){
						if(commonRequst.getThirdPayConfigId().equals("platform")){
							commonResponse.setThirdPayConfigId(yeepay.getPlatformNo());
						}else{
							commonResponse.setThirdPayConfigId(commonRequst.getThirdPayConfigId());
						}
						 if(response.getBank()!=null){
							commonResponse.setBankName(p.getProperty(response.getBank()));
							commonResponse.setBankCode(response.getCardNo());
						 }
						 commonResponse.setAutoTender(response.getAutoTender());
						 //判断会员类型
						 String memberType=response.getMemberType().equals("ENTERPRISE")?"企业会员":"个人会员";
						 commonResponse.setCustMemberType(memberType);
						 String custmemberStatus=response.getActiveStatus().equals("ACTIVATED")?"正常会员":"未激活状态";
						 commonResponse.setCustmemberStatus(custmemberStatus);
						 commonResponse.setBalance(new BigDecimal(response.getBalance()));//账户金额
						 commonResponse.setAvailableAmount(new BigDecimal(response.getAvailableAmount()));//账户可用金额
						 commonResponse.setFreezeAmount(new BigDecimal(response.getFreezeAmount()));//账户冻结金额
						 String bindBankStatus=(response.getCardStatus()!=null)?(response.getCardStatus().equals("VERIFIED")?"已认证":"认证中"):"未绑卡";
						 commonResponse.setBindBankStatus(bindBankStatus);
						 commonResponse.setRequestInfo(outStr);
						 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						 commonResponse.setResponseMsg("用户信息查询成功");
					  }else{
						  commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						  commonResponse.setResponseMsg("注册用户信息查询失败");
					  }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SYSTEMERROR);
			commonResponse.setResponseMsg("用户重置交易密码接口对接失败"+e.getMessage());
		}
		return commonResponse;
	}
	
	/**
	 * 直连接口02--单笔交易查询(充值，取现，投标，放款，通用转账)
	 */
	public static CommonResponse singleQuery(CommonRequst commonRequst) {
		logger.info("调用易宝支付直连单笔交易查询(充值，取现，投标，放款，通用转账)接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setReturnType(CommonResponse.RETURNTYPE_JOSN);
		System.out.println("111");
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				System.out.println("22");
				yeepay.setRequestNo(commonRequst.getQueryRequsetNo());//请求流水号
				yeepay.setMode(commonRequst.getQueryType());//查询类型
				Map<String, String> params=getReqSign(yeepay,"QUERY");;
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				List<CommonRecord> recordList= null;
				if(yeepayurl!=null){
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("单笔交易查询(充值，取现，投标，放款，通用转账)请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("单笔交易查询(充值，取现，投标，放款，通用转账)结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					Properties p=getyeePayProperties();
					if(response.getCode().equals("1")){
						System.out.println("333");
						 if(response.getRecords().size()>0){
							 System.out.println("444");
							 CommonRecord commonRecord=new CommonRecord();
							 recordList =new ArrayList<CommonRecord>();
							Record record=response.getRecords().get(0);
							//根据查询类型封装record,PAYMENT_RECORD投标记录
							if("PAYMENT_RECORD".equals(commonRequst.getQueryType())){
								commonRecord.setPaymentAmount(record.getPaymentAmount());
								commonRecord.setSourceUserNo(record.getSourceUserNo());
								commonRecord.setCreateTime(record.getCreateTime());
								commonRecord.setLoanTime(record.getLoanTime());
								commonRecord.setStatus(record.getStatus());
							} 
							//还款记录
							else if("REPAYMENT_RECORD".equals(commonRequst.getQueryType())){
								commonRecord.setRepaymentAmount(record.getRepaymentAmount());
								commonRecord.setTargetUserNo(record.getTargetUserNo());
								commonRecord.setCreateTime(record.getCreateTime());
								commonRecord.setStatus(record.getStatus());
							}
							//提现记录
							 else if("WITHDRAW_RECORD".equals(commonRequst.getQueryType())){
									commonRecord.setAmount(record.getAmount());
									commonRecord.setUserNo(record.getUserNo());
									commonRecord.setCreateTime(record.getCreateTime());
									commonRecord.setStatus(record.getStatus());
									commonRecord.setRemitStatus(record.getRemitStatus());
								}
							//充值记录
							 else if("RECHARGE_RECORD".equals(commonRequst.getQueryType())){
									commonRecord.setAmount(record.getAmount());
									commonRecord.setUserNo(record.getUserNo());
									commonRecord.setCreateTime(record.getCreateTime());
									commonRecord.setStatus(record.getStatus());
								}
							//通用转账记录
							 else if("CP_TRANSACTION".equals(commonRequst.getQueryType())){
									commonRecord.setRequestNo(record.getRequestNo());
									commonRecord.setBizType(record.getBizType());
									commonRecord.setAmount(record.getAmount());
									commonRecord.setSubStatus(record.getSubStatus());
									commonRecord.setStatus(record.getStatus());
								}
							recordList.add(commonRecord);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("单笔交易查询(充值，取现，投标，放款，通用转账)成功");
							commonResponse.setCommonRequst(commonRequst);
							commonResponse.setRecordList(recordList);
							commonResponse.setRequestInfo(outStr);
						 }else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTRECIVEPARAMETER);
							commonResponse.setResponseMsg("单笔交易查询(充值，取现，投标，放款，通用转账)无效");
							commonResponse.setCommonRequst(commonRequst);
							commonResponse.setRecordList(recordList);
							commonResponse.setRequestInfo(outStr);
						 }
					  }else{
							 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							 commonResponse.setResponseMsg("单笔交易查询(充值，取现，投标，放款，通用转账)失败");
							 commonResponse.setCommonRequst(commonRequst);
							 commonResponse.setRecordList(recordList);
							 commonResponse.setRequestInfo(outStr);
					  }
				}else{
					 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					 commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
					 commonResponse.setCommonRequst(commonRequst);
					 commonResponse.setRecordList(recordList);
				}
			}else{
				 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				 commonResponse.setResponseMsg("基本参数获取失败");
				 commonResponse.setCommonRequst(commonRequst);
			}
		}catch(Exception e){
			e.printStackTrace();
			 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			 commonResponse.setResponseMsg("单笔交易查询(充值，取现，投标，放款，通用转账)接口对接失败"+e.getMessage());
			 commonResponse.setCommonRequst(commonRequst);
		}
		return commonResponse;
	}
	
	/**
	 * 直连接口03--取消投标
	 */
	public static CommonResponse revocationTransfer(CommonRequst commonRequst) {
		logger.info("调用易宝支付直连取消投标接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//自动还款的属性
				yeepay.setRequestNo(commonRequst.getQueryRequsetNo());//请求流水号
				Map<String, String> params=getReqSign(yeepay,"REVOCATION_TRANSFER");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("取消投标请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("取消投标结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						 commonResponse.setResponseMsg("取消投标成功");
					}else{
						 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						 commonResponse.setResponseMsg("取消投标失败:"+response.getDescription());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					 commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("取消投标调用易宝参数："+ret[1]);
				
			}else{
				 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				 commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			 commonResponse.setResponseMsg("取消投标接口对接失败");
		}
		return commonResponse;
	}
	
	/**
	 * 直连接口04--招标项目放款(2014-07-15)
	 * @return
	 */
	public static CommonResponse loan(CommonRequst commonRequst){
		logger.info("调用易宝支付直连项目放款接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//自动还款的属性
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setOrderNo(commonRequst.getBidType()+commonRequst.getBidId().toString());//标的号
				yeepay.setFee(commonRequst.getFee().toString());//平台方收取费用
				if(commonRequst.getLoanList()!=null){//判断放款list是否为空
					List<Transfer> li=new ArrayList<Transfer>();
					for(CommonRequestInvestRecord temp:commonRequst.getLoanList()){
						Transfer t1=new Transfer();
						t1.setRequestNo(temp.getRequestNo());//投资人投资记录的流水号
						t1.setSourcePlatformUserNo(temp.getInvest_thirdPayConfigId());//投资人的第三方标识
						t1.setSourceUserType("MEMBER");
						t1.setTargetPlatformUserNo(temp.getLoaner_thirdPayConfigId());//借款人的第三方标识
						t1.setTargetUserType("MEMBER");
						t1.setTransferAmount(temp.getAmount().toString());//放款金额
						li.add(t1);
					}
					yeepay.setTransfers(li);
					Map<String, String> params=getReqSign(yeepay,"LOAN");
					System.out.println(params);
					//易宝支付调用地址
					String yeepayurl=yeePayUrl();
					if(yeepayurl!=null){	
						String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
						logger.info("项目放款请求参数："+param);
						System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
						String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
						logger.info("项目放款查询结果："+outStr);
						System.out.println(outStr);
						YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
						if(response.getCode().equals("1")){
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("项目放款成功");
							commonResponse.setCommonRequst(commonRequst);
							commonResponse.setReturnType(CommonResponse.RETURNTYPE_JOSN);
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("项目放款失败");
							commonResponse.setCommonRequst(commonRequst);
							commonResponse.setReturnType(CommonResponse.RETURNTYPE_JOSN);
						}
						commonResponse.setRequestInfo(outStr);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
						commonResponse.setCommonRequst(commonRequst);
						commonResponse.setReturnType(CommonResponse.RETURNTYPE_JOSN);
					}
					logger.info("用户还款调用易宝参数："+ret[1]);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("还款list不能为空");
					commonResponse.setCommonRequst(commonRequst);
					commonResponse.setReturnType(CommonResponse.RETURNTYPE_JOSN);
				}
				
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				commonResponse.setReturnType(CommonResponse.RETURNTYPE_JOSN);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户重置交易密码接口对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			commonResponse.setReturnType(CommonResponse.RETURNTYPE_JOSN);
		}
		return commonResponse;
	}
	/**
	 * 自动投标2.o接口
	 */
	
	public static CommonResponse  autoTransfer2(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户投标接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//出款人平台用户编号
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setUserType("MEMBER");
				yeepay.setBizType("TENDER");//业务类型
				//封装资金明细记录
				List<Detail> details = new ArrayList<Detail>();
				Detail detail = new Detail();
				detail.setAmount(commonRequst.getAmount().toString());//交易金额
				detail.setTargetUserType("MEMBER");//用户类型,默认
				detail.setTargetPlatformUserNo(commonRequst.getLoaner_thirdPayflagId());//借款用户编号
				detail.setBizType("TENDER");
				details.add(detail);
				yeepay.setDetails(details);
				yeepay.setRemark("用户自动投标");
				//项目信息
				Properties p = new Properties();
				p.setProperty("tenderOrderNo",commonRequst.getBidType()+"-"+commonRequst.getBidId());//项目编号，根据标的类型和标的id拼接生成
				p.setProperty("tenderName", commonRequst.getBidName());//项目名称
				p.setProperty("tenderAmount", commonRequst.getPlanMoney().toString());//项目金额
				p.setProperty("tenderDescription", "自动投标");//项目描述
				p.setProperty("borrowerPlatformUserNo", commonRequst.getLoaner_thirdPayflagId());//借款人
				p.setProperty("tenderSumLimit", commonRequst.getPlanMoney().toString());//累计投标金额限制，如果此参数不为空，则项目累计已投金额+本次投标金额不能超过此参数
				String str = strProperty(p);
				yeepay.setExtend(str);
				Map<String, String> params=getReqSign(yeepay,"AUTO_TRANSACTION");
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
				String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
				YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
				if(response.getCode().equals("1")){
					cr.setLoanNo(commonRequst.getRequsetNo());
					cr.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					cr.setResponseMsg("自动投标成功");
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("自动投标失败");
				}
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户投标接口对接失败"+e.getMessage());
		}
		return cr;
}
	/**
	 * 直连接口05--自动投标
	 */
	
	public static CommonResponse  autoTransfer(CommonRequst commonRequst) {
		logger.info("调用易宝支付直连自动投标接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//自动还款的属性
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId().toString());//标的号
				yeepay.setTransferAmount(commonRequst.getPlanMoney().toString());//标的金额
				yeepay.setTargetPlatformUserNo(commonRequst.getLoaner_thirdPayflagId());//目标收款人
				yeepay.setPaymentAmount(commonRequst.getAmount().toString());//冻结金额
				Map<String, String> params=getReqSign(yeepay,"AUTO_TRANSFER");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("自动投标请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("自动投标结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("自动投标成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("自动投标失败");
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("自动投标调用易宝参数："+ret[1]);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户自动投标接口对接失败"+e.getMessage());
		}
		return commonResponse;
	}
	
	/**
	 * 直连接口06--取消自动投标授权
	 * @return
	 */
	public static CommonResponse cancelTransferAuthorization(CommonRequst commonRequst) {
		logger.info("调用易宝支付直连取消自动投标授权接口开始时间："+new Date());
		CommonResponse cr=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			cr.setCommonRequst(commonRequst);
			if(null != yeepay){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				Map<String, String> params=getReqSign(yeepay,"CANCEL_AUTHORIZE_AUTO_TRANSFER");
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();//易宝支付调用地址
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("取消自动投标授权请求参数："+param);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("取消自动投标授权结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					
					cr.setRequestInfo(outStr);
					cr.setReturnType(CommonResponse.RETURNTYPE_JOSN);
					
					if(response.getCode().equals("1")){
						cr.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						cr.setResponseMsg("取消自动投标授权成功");
					}else{
						cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						cr.setResponseMsg("取消自动投标授权失败");
					}
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("取消自动投标授权调用易宝参数："+cr.getResponseMsg());
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户取消自动投标授权接口对接失败"+e.getMessage());
			e.printStackTrace();
		}
		return cr;
	}
	
	/**
	 * 直连接口07--取消自动还款授权  
	 * @return
	 */
	public static CommonResponse cancelRepaymentAuthorization(CommonRequst commonRequst) {
		logger.info("调用易宝支付直连取消自动还款授权接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//取消自动投标授权的属性
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());
				Map<String, String> params=getReqSign(yeepay,"CANCEL_AUTHORIZE_AUTO_REPAYMENT");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("取消自动还款授权请求参数："+param);
					
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("取消自动还款授权结果："+outStr);
					
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					
					cr.setRequestInfo(outStr);
					cr.setReturnType(CommonResponse.RETURNTYPE_JOSN);
					
					if(response.getCode().equals("1")){
						cr.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						cr.setResponseMsg("取消自动还款授权成功");
					}else{
						cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						cr.setResponseMsg("取消自动还款授权失败");
					}
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("取消自动还款授权调用易宝参数："+cr.getResponseMsg());
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户取消自动还款授权接口对接失败"+e.getMessage());
			e.printStackTrace();
		}
		return cr;
	}
	
	 /**
     * 直连接口08--通用转账授权审核接口 
     * @param map
     * @return
     */
	public static CommonResponse checkCommonTransfer(CommonRequst commonRequst) {
		logger.info("调用易宝支付直连通用转账授权审核接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//取消自动投标授权的属性
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setMode(commonRequst.getConfimStatus()?"CANCEL":"CONFIRM");//表示是否通过转账审核
				Map<String, String> params=getReqSign(yeepay,"COMPLETE_TRANSACTION");
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("通用转账授权审核请求参数："+param);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					System.out.println("outStr===="+outStr);
					logger.info("通用转账授权审核授权结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("通用转账授权审核成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("通用转账授权审核失败");
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("通用转账授权审核调用易宝参数："+ret[1]);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("通用转账授权审核接口对接失败");
		}
		return commonResponse;
	}
	
	/**
	 * 直连接口09--平台给用户转账
	 * @param commonRequst
	 * @return
	 */
	public static Object[] plate_transfer(CommonRequst commonRequst){
		logger.info("调用易宝支付直连平台给用户转账接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//平台给用户转账的属性
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setSourceUserType("MERCHANT");//出款人类型
				yeepay.setSourcePlatformUserNo(yeepay.getPlatformNo());//出款人编号
				yeepay.setAmount(commonRequst.getAmount().setScale(2).toString());//划款金额
				yeepay.setTargetUserType("MEMBER");//收款人类型(个人会员企业会员都取值为MEMBER)
				yeepay.setTargetPlatformUserNo(commonRequst.getThirdPayConfigId());//收款编号
				Map<String, String> params=getReqSign(yeepay,"PLATFORM_TRANSFER");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("平台给用户转账请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("平台给用户转账结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						  ret[0]=ThirdPayConstants.RECOD_SUCCESS;
						  ret[1]="平台给用户转账成功";
						  ret[2]=commonResponse;
					}else{
						  ret[0]=ThirdPayConstants.RECOD_FAILD;
						  ret[1]="平台给用户转账失败";
						  ret[2]=commonResponse;
					}
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="基本参数-请求地址没有填写获取失败";
					ret[2]=commonResponse;
				}
				logger.info("平台给用户转账调用易宝参数："+ret[1]);
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="平台给用户转账接口对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return ret;
	}
	
	/**
	 * 直连接口10--平台准备金代偿还款
	 * @param map
	 * @return
	 */
	public static CommonResponse toRepaymentByReserve(CommonRequst commonRequst){
		logger.info("调用易宝支付直连平台准备金代偿还款接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				//yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//平台准备金代偿还款的属性
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				if(commonRequst.getRepaymemntList()!=null){//判断还款list是否为空
					List<Repayment> Transfer=new ArrayList<Repayment>();
					for(CommonRequestInvestRecord temp:commonRequst.getRepaymemntList()){
						Repayment rep = new Repayment();
						rep.setTargetUserNo(temp.getInvest_thirdPayConfigId());
						rep.setPaymentRequestNo(temp.getBidRequestNo());
						rep.setAmount(temp.getAmount().toString());
						//rep.setFee(temp.getFee().toString());
						rep.setFee("0");
						Transfer.add(rep);
					}
					yeepay.setRepayments(Transfer);
					Map<String, String> params=getReqSign(yeepay,"RESERVE_REPAYMENT");
					System.out.println(params);
					//易宝支付调用地址
					String yeepayurl=yeePayUrl();
					if(yeepayurl!=null){	
						String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
						logger.info("平台准备金代偿还款请求参数："+param);
						System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
						String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
						logger.info("平台准备金代偿还款结果："+outStr);
						YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
						if(response.getCode().equals("1")){
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("平台准备金代偿还款成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("平台准备金代偿还款失败:"+response.getDescription());
						}
						commonResponse.setRequestInfo(outStr);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
					}
					logger.info("平台准备金代偿还款调用易宝参数："+ret[1]);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数获取失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("平台准备金代偿还款接口对接失败");
		}
		return commonResponse;
	
	}
	
	/**直连接口16--平台帮助用户还款接口  
	 * @return
	 */
	public static CommonResponse toRepaymentDirectlyByLoaner(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户还款接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			cr.setCommonRequst(commonRequst);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				if(commonRequst.getRepaymemntList()!=null){//判断还款list是否为空
					List<Repayment> Transfer=new ArrayList<Repayment>();
					for(CommonRequestInvestRecord temp:commonRequst.getRepaymemntList()){
						Repayment rep = new Repayment();
						rep.setTargetUserNo(temp.getInvest_thirdPayConfigId());
						rep.setPaymentRequestNo(temp.getBidRequestNo());
						rep.setAmount(temp.getAmount().toString());
						rep.setFee(temp.getFee().toString());
						Transfer.add(rep);
					}
					yeepay.setRepayments(Transfer);
					Map<String, String> params=getReqSign(yeepay,"AUTO_REPAYMENT");
					System.out.println(params);
					//易宝支付调用地址
					String yeepayurl=yeePayUrl();
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("标的信息查询请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("标的信息查询结果："+outStr);
					//查询出来结果如何展示到页面
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					cr.setReturnType(CommonResponse.RETURNTYPE_JOSN);
					cr.setRequestInfo(outStr);
					if(response.getCode().equals("1")){
						cr.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						cr.setResponseMsg("用户还款成功");
					}else{
						cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						cr.setResponseMsg(response.getDescription());
					}
					logger.info("用户还款调用易宝参数："+cr);
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg("还款list不能为空");
				}
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户还款接口对接失败"+e.getMessage());
			e.printStackTrace();
		}
		return cr;
	}
	/**平台帮助用户还款接口  2.0
	 * @return
	 */
	public static CommonResponse toRepaymentDirectlyByLoaner2(CommonRequst commonRequst) {
		logger.info("调用易宝支付网关用户还款接口开始时间："+new Date());
		CommonResponse cr=new CommonResponse();
		cr.setCommonRequst(commonRequst);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,true);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//出款人平台用户编号
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setUserType("MEMBER");
				yeepay.setBizType("REPAYMENT");//业务类型
				//封装资金明细记录
				if(commonRequst.getRepaymemntList()!=null){//判断还款list是否为空
					List<Detail> details = new ArrayList<Detail>();
					for(CommonRequestInvestRecord temp:commonRequst.getRepaymemntList()){
						Detail detail = new Detail();
						detail.setAmount(temp.getAmount().subtract(temp.getFee()).toString());//交易金额
						detail.setTargetUserType("MEMBER");//用户类型,默认
						detail.setTargetPlatformUserNo(temp.getInvest_thirdPayConfigId());//收款用户编号
						detail.setBizType("REPAYMENT");
						details.add(detail);
						//服务费用
						if(temp.getFee().compareTo(new BigDecimal("0"))>0){
							Detail detailFee = new Detail();
							detailFee.setAmount(temp.getFee().toString());//交易金额
							detailFee.setTargetUserType("MERCHANT");//用户类型,默认
							detailFee.setTargetPlatformUserNo(yeepay.getPlatformNo());//收款用户编号
							detailFee.setBizType("COMMISSION");
							details.add(detailFee);
						}
					}
					yeepay.setRemark("用户还款");
					yeepay.setDetails(details);
				}
				//项目信息
				Properties p = new Properties();
				p.setProperty("tenderOrderNo",commonRequst.getBidType()+"-"+commonRequst.getBidId());//项目编号，根据标的类型和标的id拼接生成
				String str = strProperty(p);
				yeepay.setExtend(str);
				Map<String, String> params=getReqSign(yeepay,"AUTO_TRANSACTION");
				String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
				System.out.println("params="+params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
				System.out.println("outStr="+outStr);
				//查询出来结果如何展示到页面
				YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
				cr.setReturnType(CommonResponse.RETURNTYPE_JOSN);
				cr.setRequestInfo(outStr);
				if(response.getCode().equals("1")){
					cr.setResponsecode(CommonResponse.RESPONSECODE_FREEZE);
					cr.setResponseMsg("用户还款申请成功");
				}else{
					cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					cr.setResponseMsg(response.getDescription());
				}
				cr.setRequestInfo(outStr);
				cr.setReturnType(CommonResponse.RETURNTYPE_HTML);
				logger.info("还款投标调用易宝参数："+outStr);
			}else{
				cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				cr.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			cr.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			cr.setResponseMsg("用户还款接口对接失败"+e.getMessage());
		}
		return cr;
	}
	/**
	 * 直连接口12--账户资金冻结（无方向冻结）
	 * @param map
	 * @return
	 */
	public static CommonResponse freeze(CommonRequst commonRequst){
		logger.info("调用易宝支付直连账户资金冻结（无方向冻结）接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//账户资金冻结（无方向冻结）的属性
				yeepay.setRequestNo(commonRequst.getRequsetNo());//投标流水号
				yeepay.setAmount(commonRequst.getAmount().toString());//冻结金额
				yeepay.setExpired("");//自动解冻时间点(不设置自动解冻时间)
				Map<String, String> params=getReqSign(yeepay,"FREEZE");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("账户资金冻结（无方向冻结）请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("账户资金冻结（无方向冻结）结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("账户资金冻结（无方向冻结）成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("账户资金冻结（无方向冻结）失败");
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("账户资金冻结（无方向冻结）调用易宝参数："+ret[1]);
				
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("账户资金冻结（无方向冻结）接口对接失败");
		}
		return commonResponse;
	
	}
	
	
	/**
	 * 直连接口13--账户主动冻结资金解冻（无方向冻结资金解冻）
	 * @param map
	 * @return unfreeze
	 */
	public static CommonResponse unfreeze(CommonRequst commonRequst){
		logger.info("调用易宝支付直连账户主动冻结资金解冻（无方向冻结资金解冻）接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//账户主动冻结资金解冻（无方向冻结资金解冻）的属性
				yeepay.setFreezeRequestNo(commonRequst.getRequsetNo());//解冻请求流水号
				Map<String, String> params=getReqSign(yeepay,"UNFREEZE");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("账户主动冻结资金解冻（无方向冻结资金解冻）请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("账户主动冻结资金解冻（无方向冻结资金解冻）结果："+outStr);
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("账户主动冻结资金解冻（无方向冻结资金解冻）成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("账户主动冻结资金解冻（无方向冻结资金解冻）失败");
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("账户主动冻结资金解冻（无方向冻结资金解冻）调用易宝参数："+ret[1]);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("账户主动冻结资金解冻（无方向冻结资金解冻）接口对接失败");
		}
		return commonResponse;
	
	}
	/**
	 * 直连接口15--标的信息查询
	 * @param map
	 * @return unfreeze
	 */
	public static CommonResponse BidPlanInfo(CommonRequst commonRequst){
		logger.info("调用易宝支付直连标的信息查询接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setPlatformUserNo(commonRequst.getThirdPayConfigId());//商户平台会员标识
				//标的信息查询的属性
				yeepay.setOrderNo(commonRequst.getBidType()+"-"+commonRequst.getBidId());//标的号
				Map<String, String> params=getReqSign(yeepay,"PROJECT_QUERY");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("标的信息查询请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("标的信息查询结果："+outStr);
					//查询出来结果如何展示到页面
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					
					if(response.getCode().equals("1")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("标的信息查询成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("标的信息查询失败");
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("标的信息查询调用易宝参数："+ret[1]);
				
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("标的信息查询接口对接失败");
		}
		return commonResponse;
	
	}
	/**
	 * 直连接口17--平台划款1.0接口
	 * @param map
	 * @return unfreeze
	 */
	public static CommonResponse platformTransfer1(CommonRequst commonRequst){
		logger.info("调用易宝支付直连平台划款接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setAmount(commonRequst.getAmount().toString());//交易金额
				yeepay.setSourceUserType("MERCHANT");//出款人类型
				yeepay.setSourcePlatformUserNo(yeepay.getPlatformNo());//出款人编号
				yeepay.setTargetUserType("MEMBER");//收款人类型
				yeepay.setTargetPlatformUserNo(commonRequst.getThirdPayConfigId());//收款编号
				//标的信息查询的属性
				Map<String, String> params=getReqSign(yeepay,"PLATFORM_TRANSFER");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("平台划款请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("平台划款结果："+outStr);
					//查询出来结果如何展示到页面
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("平台划款成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("平台划款失败:"+response.getDescription());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("平台划款参数："+ret[1]);
				
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("平台划款接口对接失败"+e.getMessage());
		}
		return commonResponse;
		
	}
	/**
	 * 平台划款(发红包、平台派息、优惠券奖励)---> 2.0接口
	 * @param map
	 * @return unfreeze
	 */
	public static CommonResponse platformTransfer(CommonRequst commonRequst){
		logger.info("调用易宝支付直连平台划款接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setRequestNo(commonRequst.getRequsetNo());//请求流水号
				yeepay.setPlatformUserNo(yeepay.getPlatformNo());
				yeepay.setUserType("MERCHANT");
				yeepay.setBizType("TRANSFER");
				//封装资金明细
				List<Detail> details = new ArrayList<Detail>();
				Detail detail = new Detail();
				detail.setAmount(commonRequst.getAmount().toString());
				detail.setTargetUserType("MEMBER");
				detail.setBizType("TRANSFER");
				detail.setTargetPlatformUserNo(commonRequst.getThirdPayConfigId());//收款编号
				details.add(detail);
				yeepay.setDetails(details);
				Map<String, String> params=getReqSign(yeepay,"DIRECT_TRANSACTION");
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("平台划款请求参数："+param);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					System.out.println("outStr="+outStr);
					logger.info("平台划款结果："+outStr);
					//查询出来结果如何展示到页面
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					if(response.getCode().equals("1")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("平台划款成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("平台划款失败:"+response.getDescription());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("平台划款参数："+ret[1]);
				
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("平台划款接口对接失败"+e.getMessage());
		}
		return commonResponse;
		
	}
	/**
	 * 直连接口18--业务对账  平台账户注册会员交易记录查询
	 * @param map
	 * @return unfreeze
	 */
	public static CommonResponse reconciliation(CommonRequst commonRequst){
		logger.info("调用易宝支付直连业务对账接口开始时间："+new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//公共商户号
			YeePay yeepay=generatePublicData(false,false);
			if(yeepay!=null){
				yeepay.setDate(sdf.format(commonRequst.getTransactionTime()));
				//标的信息查询的属性
				Map<String, String> params=getReqSign(yeepay,"RECONCILIATION");
				System.out.println(params);
				//易宝支付调用地址
				String yeepayurl=yeePayUrl();
				if(yeepayurl!=null){	
					String param=ThirdPayWebClient.generateParams(params,YeePay.CHARSETUTF8);
					logger.info("业务对账请求参数："+param);
					System.out.println("YeePay.YEEPAYDIRECT===="+YeePay.YEEPAYDIRECT);
					String outStr=ThirdPayWebClient.getWebContentByPost(yeepayurl+YeePay.YEEPAYDIRECT, param, YeePay.CHARSETUTF8,120000); 
					logger.info("业务对账结果："+outStr);
					//查询出来结果如何展示到页面
					YeePayReponse response =JAXBunmarshal(outStr,YeePayReponse.class);
					List<CommonRecord> recordList = new ArrayList<CommonRecord>();
					if(response.getCode().equals("1")){
							List<Record> record=response.getRecords();
							for(Record r:record){
								CommonRecord  rd = new CommonRecord();
								rd.setPlatformNo(r.getSourceUserNo());
								rd.setRequestNo(r.getRequestNo());
								rd.setAmount(r.getAmount());
								if(r.getBizType().equals("RECHARGE")){
									rd.setBizType("充值");
								}else if(r.getBizType().equals("WITHDRAW")){
									rd.setBizType("取现");
								}else if(r.getBizType().equals("PAYMENT")){
									rd.setBizType("放款");
								}else if(r.getBizType().equals("REPAYMENT")){
									rd.setBizType("还款");
								}
								rd.setFee(r.getFee());
								rd.setBalance(r.getBalance());
								recordList.add(rd);
							}
							commonResponse.setRecordList(recordList);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("业务对账查询成功");
						
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("业务对账查询失败:"+response.getDescription());
					}
					commonResponse.setRequestInfo(outStr);
					commonResponse.setThirdPayConfigId(yeepay.getPlatformNo());
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("基本参数-请求地址没有填写获取失败");
				}
				logger.info("平台划款参数："+ret[1]);
				
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("业务对账接口对接失败"+e.getMessage());
		}
		return commonResponse;
		
	}
	
	
//==================================接口开始=========================================	
}
