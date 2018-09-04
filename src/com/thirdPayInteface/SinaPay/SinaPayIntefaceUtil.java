package com.thirdPayInteface.SinaPay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.thirdPayInteface.CommonRequst;
import com.thirdPayInteface.CommonResponse;
import com.thirdPayInteface.ThirdPayConstants;
import com.thirdPayInteface.ThirdPayWebClient;
import com.thirdPayInteface.SinaPay.SinaPay;
import com.zhiwei.credit.util.UrlUtils;
import com.thirdPayInteface.SinaPay.SinaPayUtil.Base64;
import com.thirdPayInteface.SinaPay.SinaPayUtil.GsonUtil;
import com.thirdPayInteface.SinaPay.SinaPayUtil.MD5;
import com.thirdPayInteface.SinaPay.SinaPayUtil.RSA;

public class SinaPayIntefaceUtil {
	private static Log logger=LogFactory.getLog(SinaPayIntefaceUtil.class);
    /**
     * 获取系统配置参数
     */
	private static Map configMap = AppUtil.getConfigMap();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	
	
	/**
	 * 获取新浪支付的第三方支付环境参数（用来获取正式环境和测试环境的新浪支付的支付环境）
	 * @return
	 */
	private static Map sinaPayProperty(){
		Map sinaPayConfigMap=new HashMap();
		try{
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
				sinaPayConfigMap=configMap;
			}else{
				Properties props =  new  Properties();    
		        InputStream in = Object.class.getResourceAsStream("/com/thirdPayInteface/SinaPay/SinaPayTestEnvironment.properties"); 
		    	props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		sinaPayConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    		logger.error(ex.getMessage());
    	}
		return sinaPayConfigMap;
	}
	/**
	 * RAS加密数据，使用Base64 编码
	 * @param orginalData
	 * @return
	 */
	private static String createBase64Data(String orginalData) {
		System.out.println("orginalData==="+orginalData);
		//获取加密敏感字段RSAKey
		String encrypt = sinaPayProperty().get("thirdPay_sinapay_RSAFieldEncodeKey").toString().toString();
		byte[] rasData = null;
		try {
			// 认证内容加密
			rasData = RSA.encryptByPublicKey(orginalData.getBytes("utf-8"), encrypt);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1);
		}
		String Base64OrgnialData = Base64.encode(rasData);
		return Base64OrgnialData;
	}
	
	/**
	 * 验证签名方法
	 * @param content  签名内容
	 * @param signMsg  网关返回签名           
	 * @param signType MD5/RSA
	 * @param signKey  密钥
	 * @param charset  字符集
	 * @return
	 * @throws Exception
	 */
	public static boolean Check_sign(String content,String signMsg,String signType, String charset) throws Exception {
		String signKey="";
		if ("MD5".equalsIgnoreCase(signType)) {
			signKey= sinaPayProperty().get("thirdPay_sinapay_MD5Key").toString().toString();
			return MD5.verify(content, signMsg, signKey, charset);
		} else if ("RSA".equalsIgnoreCase(signType)) {
			signKey =  sinaPayProperty().get("thirdPay_sinapay_RSAPublicKey").toString().toString();
			return RSA.verify(content, signMsg,signKey,charset);
		}
		return false;
	}

	/**
	 * 生成签名方法
	 * @param content
	 * @param signType
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String sign(String content, String signType, 
			String charset) throws Exception {
		String signKey="";
		if ("MD5".equalsIgnoreCase(signType)) {
			signKey= sinaPayProperty().get("thirdPay_sinapay_MD5Key").toString().toString();
			return MD5.sign(content, signKey, charset);
		} else if ("RSA".equalsIgnoreCase(signType)) {
			signKey =  sinaPayProperty().get("thirdPay_sinapay_RSAPrivateKey").toString().toString();
			return RSA.sign(content, signKey, charset);
		}
		return "";
	}
	/**
	 * 将map数据按照字母顺序排序
	 * @param params
	 * @param encode
	 * @return
	 */
    public static String createLinkString(Map<String, String> params, boolean encode) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(URLEncoder.encode(value, charset),charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            	 if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                     prestr = prestr + key + "=" + value;
                 } else {
                     prestr = prestr + key + "=" + value + "&";
                 }
          }
        return prestr;
    }
    /**
     * 实体对象  转map
     * @param request
     * @return
     */
    public static Map getParameterMap(SinaPay sinaPay,boolean isFilter) {
    	Map map = new HashMap();;
		try{
			Map siginMap=new HashMap();
			if(sinaPay!=null){
				Class type = sinaPay.getClass();
		        BeanInfo beanInfo = Introspector.getBeanInfo(type);
		        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
		        for(int i=0;i<propertyDescriptors.length;i++){
		        	 PropertyDescriptor descriptor=propertyDescriptors[i];
		        	 String propertyName = descriptor.getName();
		             if (!propertyName.equals("class")) {
		                 Method readMethod = descriptor.getReadMethod();
		                 Object result = readMethod.invoke(sinaPay, new Object[0]);
		                 if (result != null&&!"".equals(result)){
		                	 if(!propertyName.equals("sign_type")&&!propertyName.equals("sign_version")){
		                		 siginMap.put(propertyName, result);
			                 }
		                	 map.put(propertyName, URLEncoder.encode(result.toString()));
		                 }
		             }
		        }
		        /**
		         * 生成待签名明文
		         */
		        String signData=createLinkString(siginMap,isFilter);
		        System.out.println("signData=="+signData);
		        //生成签名方法调用
		        String sign =sign(signData,sinaPay.getSign_type(),sinaPay.get_input_charset());
		        System.out.println("sign=="+sign);
		        map.put("sign", sign);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  map;
    }
    
    /**
     * request 转map
     * @param request
     * @return
     */
    public static Map getParameterRequsetMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            if(!name.equals("sign")&&!name.equals("sign_type")&&!name.equals("sign_version")){
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        }
        return returnMap;
    }
    
    
    /**
     * request 转map
     * @param request
     * @return
     */
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()){
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            if(!name.equals("sign")&&!name.equals("sign_type")&&!name.equals("sign_version")){
	            Object valueObj = entry.getValue();
	            if(null == valueObj){
	                value = "";
	            }else if(valueObj instanceof String[]){
	                String[] values = (String[])valueObj;
	                for(int i=0;i<values.length;i++){
	                    value = values[i] + ",";
	                }
	                value = value.substring(0, value.length()-1);
	            }else{
	                value = valueObj.toString();
	            }
	            returnMap.put(name, value);
            }
        }
        return returnMap;
    }
	
	/**
	 * 填写接口需要的基本参数
	 * @param sinaPay   接口的基本参数
	 * @param notifyURL 是否有异步服务器端通知       （true表示有 false表示没有）
	 * @param pageURL 是否有对应页面跳转和页面回调通知  （ true表示有 false表示没有）
	 * @return
	 */
	private static SinaPay creatCommonData(SinaPay sinaPay, boolean notifyURL, boolean pageURL) {
		//获取新浪支付的支付环境参数
		Map thirdPayConfig=sinaPayProperty();
		sinaPay.setVersion(SinaPay.VERSION);//版本号
		String date=DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
		System.out.println(date);
		sinaPay.setRequest_time(date);//请求时间
		if(thirdPayConfig!=null){
			sinaPay.setPartner_id(thirdPayConfig.get("thirdPay_sinapay_platformNo").toString().trim());//商户版本号
			String BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
			if(notifyURL){
				sinaPay.setNotify_url(BasePath+thirdPayConfig.get("thirdPay_sinapay_notifyUrl").toString().trim());
			}
			if(pageURL){
				sinaPay.setReturn_url(BasePath+thirdPayConfig.get("thirdPay_sinapay_callbackUrl").toString().trim());
			}
		}
		sinaPay.set_input_charset(SinaPay.CHARTSET);//请求编码格式
		sinaPay.setSign_type(SinaPay.SIGNTYPE);//签名类型
		sinaPay.setSign_version(SinaPay.SIGNVERSION);//签名版本号
		sinaPay.setEncrypt_version(SinaPay.ENCRYPTVERSION);//加密版本号
		
		return sinaPay;
	}
	/**
	 * 生成平台分账
	 * @param sinaPay
	 * @return
	 */
	private static String createSplitList(SinaPay sinaPay) {
		// TODO Auto-generated method stub
		String  splitRecord=sinaPay.getPayee_identity_id()+"^"+sinaPay.getPayee_identity_type()+"^"+sinaPay.getAccount_type()+"^";
		return null;
	}
	
	/**
	 * 注册接口
	 * @param sinaPay
	 * @return
	 */
	public static Object[] register(CommonRequst commonRequst){
		Object[]  ret=new Object[3];
		try{
			SinaPay sinaPay=new SinaPay();
			//生成基本公共参数
			sinaPay =creatCommonData(sinaPay,false,false);
			sinaPay.setService(SinaPay.SERVICE_CREATEMEMBER);//接口名称
			//生成新浪支付的注册账号()
			String identityId=sinaPayProperty().get("thirdPay_sinapay_callbackUrl").toString().trim()+commonRequst.getTelephone();
			//接口参数（注册用户平台唯一第三方标示字段）
			sinaPay.setIdentity_id(identityId);
			//接口参数（注册用户类型：个人户企业户）
			if(commonRequst.getCustMemberType().equals(ThirdPayConstants.CUSTERTYPE_PERSON)){//个人客户会员
				sinaPay.setMember_type(SinaPay.MEMBER_TYPE1);
			}else{//企业户
				sinaPay.setMember_type(SinaPay.MEMBER_TYPE2);
			}
			sinaPay.setMemo(commonRequst.getBussinessType());//备注信息必填
			//注册用户类型。默认唯一字段UID
			sinaPay.setIdentity_type(SinaPay.IDENTITY_TYPE1);//用户标识类型
			//实体对象整合成map对象，并生成签名
			Map map =getParameterMap(sinaPay,false);
			//map对象经过编码转变成post提交的string对象
			String param=UrlUtils.generateParams(map,SinaPay.CHARTSET);
			String SinaPayURL=sinaPayProperty().get("thirdPay_sinapay_URL").toString().toString();
			String outStr =ThirdPayWebClient.getWebContentByPost(SinaPayURL+SinaPay.URL_MEMBER, param, SinaPay.CHARTSET,12000); 
			System.out.println("注册页面：outStr=="+outStr);
			System.out.println("注册页面1111：outStr=="+outStr);
			//json串转为实体对象
			SinaPay  pay=GsonUtil.fromJsonUnderScoreStyle(outStr, SinaPay.class);
			System.out.println("000000000000==="+pay.getResponse_code());
			if(pay!=null&&pay.getResponse_code().equals("APPLY_SUCCESS")){
				pay.setIdentity_id(sinaPay.getIdentity_id());
				pay.setMember_type(sinaPay.getMember_type());
				commonRequst.setThirdPayConfigId(identityId);
				commonRequst.setThirdPayConfig(ThirdPayConstants.SINAPAY);
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="开户成功";
				ret[2]=commonRequst;
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="开户失败";
				ret[2]=null;
			}
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="开户失败";
			ret[2]=null;
		}
		return ret;
	}
	
	/**
	 * 实名认证
	 * @param sinaPay
	 * @return
	 */
	public static Object[] certifyMember(CommonRequst commonRequst){
		Object[]  ret=new Object[3];
		try{
			SinaPay sinaPay=new SinaPay();
			sinaPay =creatCommonData(sinaPay,false,false);
			sinaPay.setService(SinaPay.SERVICE_CERTIFYMEMBER);//接口名称
			sinaPay.setMemo(commonRequst.getBussinessType());//备注信息必填
			sinaPay.setIdentity_id(commonRequst.getThirdPayConfigId());
			sinaPay.setIdentity_type(SinaPay.IDENTITY_TYPE1);//用户标识类型
			sinaPay.setNeed_confirm(SinaPay.ISCERTIFY);
			sinaPay.setCert_type(SinaPay.CART_TYPE1);
			//RAS  加密敏感信息
			String base64_real_name_encrypt =  createBase64Data(commonRequst.getTrueName());
			String base64_cert_no_encrypt = createBase64Data(commonRequst.getCardNumber());
			sinaPay.setReal_name(base64_real_name_encrypt);
			sinaPay.setCert_no(base64_cert_no_encrypt);
			Map map =getParameterMap(sinaPay,false);
			String param=UrlUtils.generateParams(map,SinaPay.CHARTSET);
			String SinaPayURL=sinaPayProperty().get("thirdPay_sinapay_URL").toString().toString();
			String outStr =ThirdPayWebClient.getWebContentByPost(SinaPayURL+SinaPay.URL_MEMBER, param, SinaPay.CHARTSET,12000); 
			System.out.println("个人客户设置实名认证信息：outStr=="+outStr);
			outStr=URLDecoder.decode(outStr, "utf-8");
			System.out.println("个人客户设置实名认证信息1111：outStr=="+outStr);
			//json串转为实体对象
			SinaPay  pay=GsonUtil.fromJsonUnderScoreStyle(outStr, SinaPay.class);
			System.out.println("000000000000==="+pay.getResponse_code());
			if(pay!=null&&pay.getResponse_code().equals("APPLY_SUCCESS")){
				pay.setIdentity_id(sinaPay.getIdentity_id());
				pay.setMember_type(sinaPay.getMember_type());
				pay.setReal_name(sinaPay.getReal_name());
				pay.setCert_no(sinaPay.getCert_no());
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="实名认证成功";
				ret[2]=commonRequst;
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]=pay.getResponse_message()!=null?pay.getResponse_message():"实名认证失败";
			}
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="实名认证失败-系统出错";
		}
		return ret;
	
	}
	
	/**
	 * 个人客户充值页面
	 * @param sinaPay
	 * @return
	 */
	public static  Object[] recharge(CommonRequst commonRequst){
		Object[]  ret=new Object[3];
		try{
			SinaPay  sinaPay=new SinaPay();
			sinaPay.setOut_trade_no(commonRequst.getRequsetNo());
			sinaPay.setMemo(commonRequst.getBussinessType());//备注信息必填-默认填写业务类型
			sinaPay.setIdentity_id(commonRequst.getThirdPayConfigId());
			sinaPay.setSummary("账户充值");
			sinaPay =creatCommonData(sinaPay,true,true);
			sinaPay.setService(SinaPay.SERVICE_RECHARGE);//接口名称
			sinaPay.setUser_fee("0");//充值手续费
			sinaPay.setAccount_type("BASIC");
			sinaPay.setAmount(commonRequst.getAmount().toString());
			String payMethodParam="^TESTBANK,DEBIT,C";
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
				payMethodParam="^"+commonRequst.getBankCode()+",DEBIT,C";
			}
			String payMethod="online_bank^"+commonRequst.getAmount().toString()+payMethodParam;
			sinaPay.setPay_method(payMethod);
			Map map =getParameterMap(sinaPay,false);
			String SinaPayURL=sinaPayProperty().get("thirdPay_sinapay_URL").toString().toString();
			ret =ThirdPayWebClient.operateParameter(SinaPayURL+SinaPay.URL_ORDER, map, SinaPay.CHARTSET); 
			System.out.println("充值生成的html：outStr=="+ret[1]);
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="充值失败";
		}
		return ret;
	}
	/**
	 * 基本户托管代收接口（一般用于投资和借款人（担保公司，平台）还款）
	 * @param sinaPay
	 * @return
	 */
	public static Object[]  reciveMoney(SinaPay sinaPay){
		Object[]  ret=new Object[2];
		try{
			sinaPay =creatCommonData(sinaPay,true,false);
			sinaPay.setService(SinaPay.SERVICE_CERTIFYMEMBER);//接口名称
			sinaPay.setNeed_confirm(SinaPay.ISCERTIFY);
			sinaPay.setCan_repay_on_failed("N");//不允许再次支付
			sinaPay.setTrade_close_time("20m");//20分钟后订单过期
			String payMethod="balance^BASIC";
			sinaPay.setPay_method(payMethod);
			Map map =getParameterMap(sinaPay,false);
			String param=UrlUtils.generateParams(map,SinaPay.CHARTSET);
			String SinaPayURL=sinaPayProperty().get("thirdPay_sinapay_URL").toString().toString();
			String outStr =ThirdPayWebClient.getWebContentByPost(SinaPayURL+SinaPay.URL_MEMBER, param, SinaPay.CHARTSET,12000); 
			System.out.println("个人客户设置实名认证信息：outStr=="+outStr);
			outStr=URLDecoder.decode(outStr, "utf-8");
			System.out.println("个人客户设置实名认证信息1111：outStr=="+outStr);
			//json串转为实体对象
			SinaPay  pay=GsonUtil.fromJsonUnderScoreStyle(outStr, SinaPay.class);
			System.out.println("000000000000==="+pay.getResponse_code());
			if(pay!=null&&pay.getResponse_code().equals("APPLY_SUCCESS")){
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]=pay;
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="实名认证失败";
			}
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="实名认证失败";
		}
		return ret;
	};
	
	/**
	 * 托管退款接口
	 * @param sinaPay
	 * @return
	 */
	public static Object[] bidFaild(SinaPay sinaPay){
		Object[]  ret=new Object[2];
		try{
			sinaPay =creatCommonData(sinaPay,true,false);
			sinaPay.setService(SinaPay.SERVICE_FAILBID);//接口名称
			Map map =getParameterMap(sinaPay,false);
			String param=UrlUtils.generateParams(map,SinaPay.CHARTSET);
			String SinaPayURL=sinaPayProperty().get("thirdPay_sinapay_URL").toString().toString();
			String outStr =ThirdPayWebClient.getWebContentByPost(SinaPayURL+SinaPay.URL_MEMBER, param, SinaPay.CHARTSET,12000); 
			System.out.println("个人客户设置实名认证信息：outStr=="+outStr);
			outStr=URLDecoder.decode(outStr, "utf-8");
			System.out.println("个人客户设置实名认证信息1111：outStr=="+outStr);
			//json串转为实体对象
			SinaPay  pay=GsonUtil.fromJsonUnderScoreStyle(outStr, SinaPay.class);
			System.out.println("000000000000==="+pay.getResponse_code());
			if(pay!=null&&pay.getResponse_code().equals("APPLY_SUCCESS")){
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]=pay;
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="实名认证失败";
			}
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="实名认证失败";
		}
		return ret;
	}
	  
	/**
	 * 单笔托管代付接口
	 * 使用场景是  放款或者单笔给投资人或平台还款
	 * @param sinaPay
	 * @return
	 */
	public static Object[] releaseMoney(SinaPay sinaPay){
		Object[]  ret=new Object[2];
		try{
			sinaPay =creatCommonData(sinaPay,true,false);
			sinaPay.setService(SinaPay.SERVICE_RELEASELOAN);//接口名称
			String splistAccount=createSplitList(sinaPay);
			sinaPay.setSplit_list("");//分账信息
			Map map =getParameterMap(sinaPay,false);
			String param=UrlUtils.generateParams(map,SinaPay.CHARTSET);
			String SinaPayURL=sinaPayProperty().get("thirdPay_sinapay_URL").toString().toString();
			String outStr =ThirdPayWebClient.getWebContentByPost(SinaPayURL+SinaPay.URL_MEMBER, param, SinaPay.CHARTSET,12000); 
			System.out.println("个人客户设置实名认证信息：outStr=="+outStr);
			outStr=URLDecoder.decode(outStr, "utf-8");
			System.out.println("个人客户设置实名认证信息1111：outStr=="+outStr);
			//json串转为实体对象
			SinaPay  pay=GsonUtil.fromJsonUnderScoreStyle(outStr, SinaPay.class);
			System.out.println("000000000000==="+pay.getResponse_code());
			if(pay!=null&&pay.getResponse_code().equals("APPLY_SUCCESS")){
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]=pay;
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="实名认证失败";
			}
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="实名认证失败";
		}
		return ret;
	}
	
	/**
	 * 页面通知处理
	 * @param request
	 * @return
	 */
	public static CommonResponse pageCallBackOprate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CommonResponse commonResponse =new CommonResponse();
		//回调通知响应编码
		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
		commonResponse.setResponseMsg("新浪支付已经接受了交易请求，请等待处理结果");
		return commonResponse;
	}
	
	/**
	 * 服务器端回调通知
	 * @param request
	 * @return
	 */
	public static CommonResponse notifyCallBackOprate(HttpServletRequest request) {
	//	String encrypt = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBpueNweMbYdb+CMl8dUNv5g5THYLD9Z33cAMA4GNjmPYsbcNQLyO5QSlLNjpbCwopt7b5lFP8TGLUus4x0Ed6S4Wd9KmNw6NLbszNEmppP9HXlT9sT4/ShL0CpVF4ofFS8O/gXwCTJjYZJ0HvK3GBTSP2C9WlipTpWQ+9QJugewIDAQAB";
		//业务类型(每个接口多种使用方式系统添加参数)
		//默认回调通知参数实体
		CommonResponse commonResponse=new CommonResponse();
		try {
			String bussinessType=request.getParameter("hurong_bussinessType");
			String sign = request.getParameter("sign");// 回调返回签名
			String sign_type = request.getParameter("sign_type");// 回调返回签名方式
			String notify_type = request.getParameter("notify_type");// 回调通知类型
			String _input_charset = request.getParameter("_input_charset");// 回调通知编码
			String memo=request.getParameter("memo");//获取业务类型
			request.removeAttribute("sign_type");
			request.removeAttribute("sign");
			request.removeAttribute("sign_version");
			request.removeAttribute("bussinessType");
			Map responseMap=getParameterRequsetMap(request);
			// 将返回参数不为空切参与签名的数据组成url格式
			String like_result = createLinkString(responseMap, false);
			System.out.println("待验证签名明文=="+like_result);
			Boolean isCheck=Check_sign(like_result.toString(), sign, sign_type,_input_charset);
			System.out.println("验证签名结果=="+isCheck);
			commonResponse.setBussinessType(memo);
			if (isCheck) {
				if(notify_type.equals(SinaPay.NOTIFY_RECHAGE)){//充值回调通知
					commonResponse.setRequestNo(responseMap.get("").toString());
					if(responseMap.get("deposit_status").toString().equals(SinaPay.RECHAGE_SUCCESS)){//充值成功
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("充值成功");
					}else{//充值失败结果
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("充值失败");
					}
				}else if(notify_type.equals(SinaPay.NOTIFY_TRADE)){//单笔交易通知
					
				}else if(notify_type.equals(SinaPay.NOTIFY_REFUND)){//单笔退款通知（流标）
					
				}else if(notify_type.equals(SinaPay.NOTIFY_BATCHTRADE)){//批量交易通知（还款）
					
				}else if(notify_type.equals(SinaPay.NOTIFY_WITHDRAW)){//取现交易通知
					
				}else if(notify_type.equals(SinaPay.NOTIFY_CHECK)){//审核通知
					
				}
			}else{
				
			}	
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return commonResponse;
	}



    


	
}
