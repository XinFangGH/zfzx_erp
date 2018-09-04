package com.thirdPayInterface.Huifu;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import chinapnr.SecureLink;

import com.thirdPayInterface.CommonRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayWebClient;
import com.thirdPayInterface.Huifu.HuifuUtil.MD5;
import com.thirdPayInterface.Huifu.HuifuUtil.ObjectFormatUtil;
import com.thirdPayInterface.MoneyMorePay.MoneyMore;
import com.zhiwei.core.util.AppUtil;

public class HuiFuInterfaceUtil{
	private static Log logger=LogFactory.getLog(HuiFuInterfaceUtil.class);
    /**
     * 获取系统配置参数
     */
	private static Map configMap = AppUtil.getConfigMap();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	
	/**读取属性文件
	 * 获取配置文件信息（正式环境和测试环境的配置文件）
	 * @return
	 */
	public static Map HuifuProperty() {
		Map huifuConfigMap=new HashMap();
		try{
			Properties props =  new  Properties(); 
			InputStream in=null; 
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
				in = HuiFuInterfaceUtil.class.getResourceAsStream("HuifuNormalEnvironment.properties"); 
			}else{
				in = HuiFuInterfaceUtil.class.getResourceAsStream("HuifuTestEnvironment.properties"); 
			}
			if(in!=null){
				props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		huifuConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    		logger.error(ex.getMessage());
    	}
		return huifuConfigMap;
	}
	
	/**获得地址
	 * 提交的URL地址
	 * @return
	 */
	private static String HuifuPayUrl() {
		Map thirdPayConfig=HuifuProperty();
		//汇付天下支付调用地址
		String huifuUrl=thirdPayConfig.get("thirdPay_Huifu_URL").toString();
		return huifuUrl;
	}
	
	/**
	 * 生成公共 数据
	 * @param FontHuiFuAction
	 * @return
	 */
	private static Huifu generatePublicData(Huifu huiFu,String regType,boolean pageCallBack, boolean notifyCallBack) {
		Map thirdPayConfig=HuifuProperty();
		if(thirdPayConfig!=null&&!thirdPayConfig.isEmpty()){//判断map中是否为空和有数值
			// 版本号
			String huiFuVer = thirdPayConfig.get("thirdPay_Huifu_Ver").toString();
			// 商户账号
			String huiFuNumber = thirdPayConfig.get("thirdPay_Huifu_platNumber").toString();
			huiFu.setVersion("10");
			huiFu.setCharSet(Huifu.CHARSETUTF8);
			huiFu.setCmdId(regType);
			huiFu.setMerPriv("");
			huiFu.setMerCustId(huiFuNumber);
			String erpUrl =(String) configMap.get("systemUrl");
			String BasePath = "";
			if(erpUrl!=null&&!erpUrl.equals("")){
				BasePath=erpUrl;
			}else{
				BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
			}
			if(notifyCallBack){
				huiFu.setBgRetUrl(BasePath+thirdPayConfig.get("thirdPay_Huifu_notifyUrl").toString().trim());//thirdPay_Huifu_notifyUrl
			}
			if(pageCallBack){
				huiFu.setRetUrl(BasePath+thirdPayConfig.get("thirdPay_Huifu_pageCallUrl").toString().trim());
			}
			return huiFu;
		}else{
			return null;
		}
	}
	/**
	 * 返回签名验证
	 * @param msgData
	 * @param chkValue
	 * @return
	 */
	public static boolean verifyChkValue(String merData,String chkValue){
		Map thirdPayConfig=HuifuProperty();
		String keyFile=AppUtil.getAppAbsolutePath()+thirdPayConfig.get("thirdPay_Huifu_publicKeyPath").toString().replace("\\", "/");;
		SecureLink sl=new SecureLink();
		boolean isSuccess=false;
		System.out.println("keyFile----"+keyFile);
		System.out.println("msgData----"+merData);
		System.out.println("chkValue----"+chkValue);
		int ret=sl.VeriSignMsg(keyFile,merData,chkValue);
		if (ret != 0){
	       	System.out.println("ret=" + ret );
	       	isSuccess=false;
		}else{
       		isSuccess=true;
		}
		return isSuccess;
	}
	
	/**
	 * 数据加密签名验证
	 * @param huiFuVO
	 * @param basePath
	 * @return
	 */
	private static String Sign(String MerData){
		Map thirdPayConfig=HuifuProperty();
		String MerKeyFile=AppUtil.getAppAbsolutePath()+thirdPayConfig.get("thirdPay_Huifu_privateKeyPath").toString().replace("\\", "/");
		SecureLink sl=new SecureLink();
        int ret=sl.SignMsg(thirdPayConfig.get("thirdPay_Huifu_MerId").toString(),MerKeyFile,MerData);
        String  ChkValue="";
        if (ret != 0) {
        	System.out.println("ret=" + ret );
        	ChkValue= "";
        }
        ChkValue= sl.getChkValue();
        return ChkValue;
	}
	
    /**
     *  注册
     * @param commonRequst
     * @return
     */
	public static CommonResponse register(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"UserRegister",true,true);
			if(null != huifu){
				huifu.setUsrId(commonRequst.getLoginname());
				//huifu.setUsrName(commonRequst.getTrueName());
				huifu.setIdType("00");//00 必须为身份证  编码 为 00
				huifu.setIdNo(commonRequst.getCardNumber());
				huifu.setUsrMp(commonRequst.getTelephone());
				huifu.setMerPriv(commonRequst.getRequsetNo());//保存请求的流水号
				huifu.setUsrEmail(commonRequst.getEmail()==null ? "" : commonRequst.getEmail());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getBgRetUrl().trim() 
									+ huifu.getRetUrl().trim()
									+ huifu.getUsrId().trim() 
									//+ huifu.getUsrName().trim()
									+ huifu.getIdType().trim() 
									+ huifu.getIdNo().trim() 
									+ huifu.getUsrMp().trim()
									+ huifu.getUsrEmail().trim()
									+ huifu.getMerPriv().trim();
				System.out.println("汇付注册拼接参数===="+MerData);
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				
				String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("注册申请成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("注册申请失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("注册对接失败");
		}
		return commonResponse;
	}
	 /**
     *  充值
     * @param commonRequst
     * @return
     */
	public static CommonResponse rechargeMoney(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"NetSave",true,true);
			if(null != huifu){
				huifu.setVersion("10");
				huifu.setDcFlag("D");//汇付天下默认D--借记类型
				if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATFORMPAY)){
					huifu.setUsrCustId(huifu.getMerCustId());
				}else{
					huifu.setUsrCustId(commonRequst.getThirdPayConfigId());
				}
				huifu.setOrdId(commonRequst.getRequsetNo());
				huifu.setOrdDate(sdf.format(new Date()));
				huifu.setTransAmt(commonRequst.getAmount().setScale(2).toString());
				huifu.setMerPriv(commonRequst.getRequsetNo());
				huifu.setDcFlag("");
				huifu.setCertId("");
				huifu.setGateBusiId("");
				huifu.setPageType("");
				huifu.setOpenBankId("");
				huifu.setOpenAcctId("");
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getUsrCustId().trim() 
									+ huifu.getOrdId().trim() 
									+ huifu.getOrdDate().trim() 
									+ huifu.getGateBusiId().trim()
									+ huifu.getOpenBankId().trim()
									+ huifu.getDcFlag().trim()
									+ huifu.getTransAmt().trim() 
									+ huifu.getRetUrl().trim() 
									+ huifu.getBgRetUrl().trim() 
									+ huifu.getOpenAcctId().trim()
									+ huifu.getCertId().trim()
									+ huifu.getMerPriv().trim()
									+ huifu.getPageType();
				String  MerData1 = huifu.getVersion().trim()+"|"
				+ huifu.getCmdId().trim() +"|"
				+ huifu.getMerCustId().trim() +"|"
				+ huifu.getUsrCustId().trim() +"|"
//				+ huifu.getSubAcctType().trim() 
//				+ huifu.getSubAcctId().trim() 
				+ huifu.getOrdId().trim() +"|"
				+ huifu.getOrdDate().trim() +"|"
				+ huifu.getTransAmt().trim() +"|"
				+ huifu.getRetUrl().trim() +"|"
				+ huifu.getBgRetUrl().trim() +"|"
				+ huifu.getMerPriv().trim();
				System.out.println("汇付注册拼接参数===="+MerData1);
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				
				String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("充值申请成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("充值申请失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("充值对接失败");
		}
		return commonResponse;
	}
	 /**
     *  取现
     * @param commonRequst
     * @return
     */
	public static CommonResponse withdrawsMoney(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"Cash",true,true);
			if(null != huifu){
				huifu.setUsrCustId(commonRequst.getThirdPayConfigId());
				huifu.setOrdId(commonRequst.getRequsetNo());
				huifu.setTransAmt(commonRequst.getAmount().toString());
				huifu.setMerPriv(commonRequst.getRequsetNo());
				huifu.setOpenAcctId(commonRequst.getBankCardNumber());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getOrdId().trim() 
									+ huifu.getUsrCustId().trim() 
									+ huifu.getTransAmt().trim() 
									+ huifu.getOpenAcctId().trim()
									+ huifu.getRetUrl().trim() 
									+ huifu.getBgRetUrl().trim() 
									+ huifu.getMerPriv().trim();
				System.out.println("汇付注册拼接参数===="+MerData);
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				
				String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("注册申请成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("注册申请失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("注册对接失败");
		}
		return commonResponse;
	}
	
	/**
	 * 汇付的投标方法
	 */
	public static CommonResponse biding(CommonRequst commonRequst){
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"InitiativeTender",true,true);
			if(null != huifu){
				huifu.setOrdDate(commonRequst.getOrderDate().trim());
				huifu.setUsrCustId(commonRequst.getThirdPayConfigId().trim());
				huifu.setOrdId(commonRequst.getRequsetNo().trim());//订单号
				huifu.setMaxTenderRate(commonRequst.getMaxTenderRate().trim());
				huifu.setTransAmt(commonRequst.getAmount().toString().trim());
				huifu.setBorrowerDetails(commonRequst.getBorrowerDetails().trim());
				huifu.setMerPriv(commonRequst.getRequsetNo().trim());//保存请求的流水号
				huifu.setIsFreeze(commonRequst.getIsFreeze().trim());
				huifu.setFreezeOrdId(commonRequst.getFreezeOrdId().trim());
				huifu.setReqExt(commonRequst.getReqExt());
				huifu.setPageType(commonRequst.getPageType());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim()
									+ huifu.getMerCustId().trim() 
									+ huifu.getOrdId().trim()
									+ huifu.getOrdDate().trim()
									+ huifu.getTransAmt().trim()
									+ huifu.getUsrCustId().trim()
									+ huifu.getMaxTenderRate().trim()
									+ huifu.getBorrowerDetails().trim()
									+ huifu.getIsFreeze().trim()
									+ huifu.getFreezeOrdId().trim()
									+ huifu.getRetUrl().trim()
									+ huifu.getBgRetUrl().trim() 
									+ huifu.getMerPriv().trim()
									+ huifu.getReqExt()
									+ huifu.getPageType();
				System.out.println("汇付注册拼接参数===="+MerData);
				huifu.setChkValue(Sign(MerData));
    				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					commonResponse.setResponseMsg("投标申请成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("投标申请失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("投标接口对接失败");
		}
		return commonResponse;
	}	
	/**
	 * 汇付发标的方法
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse createBid(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		Map thirdPayConfig=HuifuProperty();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"AddBidInfo",false,true);
			if(null != huifu){
				huifu.setVersion("20");
				huifu.setProId(commonRequst.getProId().trim());//项目编号
				huifu.setBidName(commonRequst.getBidName());
				huifu.setBidType("99");
				huifu.setBorrTotAmt(commonRequst.getBorrTotAmt().trim());//借款总金额
				huifu.setBorrCustId(commonRequst.getBorrCustId().trim());//借款人第三方账号
				huifu.setYearRate(commonRequst.getYearRate().toString().trim());//年利率
				huifu.setRetInterest(commonRequst.getRetInterest().trim());//
				huifu.setLastRetDate(commonRequst.getLastRetDate().trim());
				huifu.setBidStartDate(commonRequst.getBidStartDate().trim());//保存请求的流水号
				huifu.setBidEndDate(commonRequst.getBidEndDate().trim());
				huifu.setLoanPeriod(commonRequst.getLoanPeriod());
				huifu.setRetType(commonRequst.getRetType().trim());//还款类型
				huifu.setRetDate(commonRequst.getRetDate().trim());
				
				if(commonRequst.getBidType().equals(CommonRequst.HRY_PLANBUY)){//理财计划
					huifu.setVersion("20");
					huifu.setBidType("99");
					huifu.setMerPriv(commonRequst.getRequsetNo());
				}else{//散标
					huifu.setVersion("20");
					huifu.setGuarantType(commonRequst.getGuarantType().trim());
					huifu.setRiskCtlType(commonRequst.getRiskCtlType().trim());
					huifu.setRecommer(commonRequst.getRecommer().trim());
					huifu.setLimitMinBidAmt(commonRequst.getLimitMinBidAmt().trim());
					huifu.setLimitBidSum(commonRequst.getLimitBidSum().trim());
					huifu.setLimitMaxBidSum(commonRequst.getLimitMaxBidSum().trim());
					huifu.setLimitMinBidSum(commonRequst.getLimitMinBidSum());
					huifu.setOverdueProba(commonRequst.getOverdueProba().trim());
					huifu.setBidPayforState(commonRequst.getBidPayforState().trim());
					huifu.setBorrBusiCode(commonRequst.getBorrBusiCode().trim());
					huifu.setBorrPhone(commonRequst.getBorrPhone().trim());
					huifu.setBorrWork(commonRequst.getBorrWork().trim());
					huifu.setBorrWorkYear(commonRequst.getBorrWorkYear().trim());
					huifu.setBorrIncome(commonRequst.getBorrIncome().trim());
					huifu.setBorrEdu(commonRequst.getBorrEdu().trim());
					huifu.setBorrMarriage(commonRequst.getBorrMarriage().trim());
					huifu.setBorrAddr(commonRequst.getBorrAddr().trim());
					huifu.setBorrEmail(commonRequst.getBorrEmail().trim());
					huifu.setReqExt(commonRequst.getReqExt().trim());
				}
				
				huifu.setBidProdType("99");
				huifu.setBorrCertType(commonRequst.getBorrCertType().trim());
				huifu.setBorrCertId(commonRequst.getBorrCertId().trim());
				huifu.setBorrMobiPhone(commonRequst.getBorrMobiPhone().trim());
				huifu.setBorrType(commonRequst.getBorrType().trim());
				huifu.setBorrCustId(commonRequst.getBorrCustId());
				huifu.setBorrName(commonRequst.getBorrName().trim());
				huifu.setBorrPurpose(commonRequst.getBorrPurpose());
				
			
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim()
									+ huifu.getMerCustId().trim() 
									+ huifu.getProId().trim()
									+ huifu.getBidType()
									+ huifu.getBorrTotAmt().trim()
									+ huifu.getYearRate().trim()
									+ huifu.getRetInterest()
									+ huifu.getLastRetDate()
									+ huifu.getBidStartDate()
									+ huifu.getBidEndDate()
									+ huifu.getRetType()
									+ huifu.getRetDate()
									+ huifu.getGuarantType()
									+ huifu.getBidProdType()
									+ huifu.getRiskCtlType()
									+ huifu.getLimitMinBidAmt()
									+ huifu.getLimitBidSum()
									+ huifu.getLimitMaxBidSum()
									+ huifu.getLimitMinBidSum()
									+ huifu.getBidPayforState()
									+ huifu.getBorrType()
									+ huifu.getBorrCustId()
									+ huifu.getBorrBusiCode()
									+ huifu.getBorrCertType()
									+ huifu.getBorrCertId()
									+ huifu.getBorrMobiPhone()
									+ huifu.getBorrPhone()
									+ huifu.getBorrWorkYear()
									+ huifu.getBorrIncome()
									+ huifu.getBorrMarriage()
									+ huifu.getBorrEmail()
									+ huifu.getCharSet()
									+ huifu.getBgRetUrl()
									+ huifu.getMerPriv()
									+ huifu.getReqExt();
				String  MerData1 = huifu.getVersion().trim()+"|"
										+ huifu.getCmdId().trim()+"|"
										+ huifu.getMerCustId().trim()+"|" 
										+ huifu.getProId().trim()+"|"
										+ huifu.getBidType()+"|"
										+ huifu.getBorrTotAmt().trim()+"|"
										+ huifu.getYearRate().trim()+"|"
										+ huifu.getRetInterest()+"|"
										+ huifu.getLastRetDate()+"|"
										+ huifu.getBidStartDate()+"|"
										+ huifu.getBidEndDate()+"|"
										+ huifu.getRetType()+"|"
										+ huifu.getRetDate()+"|"
										+ huifu.getGuarantType()+"|"
										+ huifu.getBidProdType()+"|"
										+ huifu.getRiskCtlType()+"|"
										+ huifu.getLimitMinBidAmt()+"|"
										+ huifu.getLimitBidSum()+"|"
										+ huifu.getLimitMaxBidSum()+"|"
										+ huifu.getLimitMinBidSum()+"|"
										+ huifu.getBidPayforState()+"|"
										+ huifu.getBorrType()+"|"
										+ huifu.getBorrCustId()+"|"
										+ huifu.getBorrBusiCode()+"|"
										+ huifu.getBorrCertType()+"|"
										+ huifu.getBorrCertId()+"|"
										+ huifu.getBorrMobiPhone()+"|"
										+ huifu.getBorrPhone()+"|"
										+ huifu.getBorrWorkYear()+"|"
										+ huifu.getBorrIncome()+"|"
										+ huifu.getBorrMarriage()+"|"
										+ huifu.getBorrEmail()+"|"
										+ huifu.getCharSet()+"|"
										+ huifu.getBgRetUrl()+"|"
										+ huifu.getMerPriv()+"|"
										+ huifu.getReqExt()+"|";
						System.out.println(MerData);
				MD5 md5 = new MD5();
				MerData = MerData.replaceAll("null", "");
				huifu.setChkValue(Sign(md5.md5(MerData,"utf8")));//MerData
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				System.out.println("param="+param);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				System.out.println(result);
				JSONObject result1 = new JSONObject(result);
				if(result1.get("RespCode").toString().trim().equals("000")){
					//储存标的账号(发标的时候应用)
					commonResponse.setRemark(result1.get("ProId").toString().trim());
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("标的信息录入成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("标的信息失败");
				}
				commonResponse.setRequestInfo(result);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("投标接口对接失败");
		}
		return commonResponse;
	}

	/**
	 * 汇付的放款方法  
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse bidLoan(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"Loans",false,true);
			if(null != huifu){
				huifu.setVersion("20");
				huifu.setOrdId(commonRequst.getRequsetNo().trim());//订单号
				huifu.setOrdDate(commonRequst.getOrderDate().trim());
				huifu.setDivDetails(commonRequst.getDivDetails());
				huifu.setOutCustId(commonRequst.getThirdPayConfigId());//出账客户号
				huifu.setTransAmt(commonRequst.getAmount().toString().trim());
				huifu.setFee(commonRequst.getFee().setScale(2).toString());//放款或者扣款的手续费
				huifu.setSubOrdId(commonRequst.getSubOrdId());//投标id
				huifu.setSubOrdDate(commonRequst.getSubOrdDate());//投标日期
				huifu.setInCustId(commonRequst.getLoaner_thirdPayflagId());
				huifu.setFeeObjFlag(commonRequst.getFeeObjFlag());
				huifu.setIsDefault(commonRequst.getIsDefault());
				huifu.setIsUnFreeze(commonRequst.getIsUnFreeze());//是否解冻
				huifu.setUnFreezeOrdId(commonRequst.getUnFreezeOrdId());
				huifu.setFreezeTrxId(commonRequst.getFreezeTrxId());
				huifu.setMerPriv(commonRequst.getRequsetNo().trim());//保存请求的流水号
				huifu.setReqExt(commonRequst.getReqExt());
				huifu.setPageType(commonRequst.getPageType());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim()
									+ huifu.getMerCustId().trim() 
									+ huifu.getOrdId().trim()
									+ huifu.getOrdDate().trim()
									+ huifu.getOutCustId().trim()
									+ huifu.getTransAmt().trim()
									+ huifu.getFee().trim()
									+ huifu.getSubOrdId().trim()
									+ huifu.getSubOrdDate().trim()
									+ huifu.getInCustId().trim()
									+ huifu.getDivDetails().trim()
									+ huifu.getFeeObjFlag().trim() 
									+ huifu.getIsDefault().trim()
									+ huifu.getIsUnFreeze()
									+ huifu.getUnFreezeOrdId()
									+ huifu.getFreezeTrxId()
									+ huifu.getBgRetUrl()
									+ huifu.getMerPriv()
									+ huifu.getReqExt();
				MerData = MerData.replace("null", "");
				huifu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				System.out.println("result="+result);
				String ret = result.substring(0, 33);
				result = ret+"}";
				JSONObject object = new JSONObject(result);
				if(object.get("RespCode").toString().trim().equals("000")){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("放款成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("放款失败");
				}
				commonResponse.setRequestInfo(result);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("放款接口对接失败");
		}
		return commonResponse;
	}
	/**
	 * 理财计划起息方法
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse bidLoanPlmanage(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"Loans",false,true);
			if(null != huifu){
				huifu.setVersion("20");
				huifu.setOrdId(commonRequst.getRequsetNo().trim());//订单号
				huifu.setOrdDate(commonRequst.getOrderDate().trim());
				huifu.setDivDetails(commonRequst.getDivDetails());
				huifu.setOutCustId(commonRequst.getThirdPayConfigId());//出账客户号
				huifu.setTransAmt(commonRequst.getAmount().toString().trim());
				huifu.setFee(commonRequst.getFee().setScale(2).toString());//放款或者扣款的手续费
				huifu.setSubOrdId(commonRequst.getSubOrdId());//投标id
				huifu.setSubOrdDate(commonRequst.getSubOrdDate());//投标日期
				huifu.setInCustId(commonRequst.getLoaner_thirdPayflagId());
				huifu.setFeeObjFlag(commonRequst.getFeeObjFlag());
				huifu.setIsDefault(commonRequst.getIsDefault());
				huifu.setIsUnFreeze(commonRequst.getIsUnFreeze());//是否解冻
				huifu.setUnFreezeOrdId(commonRequst.getUnFreezeOrdId());
				huifu.setFreezeTrxId(commonRequst.getFreezeTrxId());
				huifu.setMerPriv(commonRequst.getRequsetNo().trim());//保存请求的流水号
				huifu.setReqExt(commonRequst.getReqExt());
				huifu.setPageType(commonRequst.getPageType());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim()
									+ huifu.getMerCustId().trim() 
									+ huifu.getOrdId().trim()
									+ huifu.getOrdDate().trim()
									+ huifu.getOutCustId().trim()
									+ huifu.getTransAmt().trim()
									+ huifu.getFee().trim()
									+ huifu.getSubOrdId().trim()
									+ huifu.getSubOrdDate().trim()
									+ huifu.getInCustId().trim()
									+ huifu.getDivDetails().trim()
									+ huifu.getFeeObjFlag().trim() 
									+ huifu.getIsDefault().trim()
									+ huifu.getIsUnFreeze()
									+ huifu.getUnFreezeOrdId()
									+ huifu.getFreezeTrxId()
									+ huifu.getBgRetUrl()
									+ huifu.getMerPriv()
									+ huifu.getReqExt();
				MerData = MerData.replace("null", "");
				huifu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				System.out.println("param="+param);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				System.out.println("result="+result);
				String ret = result.substring(0, 33);
				result = ret+"}";
				JSONObject object = new JSONObject(result);
				if(object.get("RespCode").toString().trim().equals("000")){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("起息成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("起息失败");
				}
				commonResponse.setRequestInfo(result);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("起息接口对接失败");
		}
		return commonResponse;
	}
	
	/**
	 * 理财计划派息,提前贖回 
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse repayment(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try{
			// 生成公共参数
			huifu = generatePublicData(huifu,"Repayment",false,true);
			if(null != huifu){
				huifu.setVersion("30");
				huifu.setProId(commonRequst.getProId());
				huifu.setOrdId(commonRequst.getRequsetNo().trim());//订单号
				huifu.setOrdDate(commonRequst.getOrderDate().trim());
				huifu.setOutCustId(commonRequst.getThirdPayConfigId());
				huifu.setSubOrdId(commonRequst.getSubOrdId().toString());
				huifu.setSubOrdDate(commonRequst.getSubOrdDate().toString());
				if(commonRequst.getFundType().equals("loanInterest")){
					huifu.setInterestAmt(commonRequst.getAmount().setScale(2).toString());
					huifu.setPrincipalAmt("0.00");
    			}else if(commonRequst.getFundType().equals("principalRepayment")){
    				huifu.setPrincipalAmt(commonRequst.getAmount().setScale(2).toString());
    				huifu.setInterestAmt("0.00");
    			}else if(commonRequst.getFundType().equals("plearly")){
    				BigDecimal interestMoney = new BigDecimal(commonRequst.getRetInterest());
    				huifu.setPrincipalAmt(commonRequst.getAmount().subtract(interestMoney).setScale(2).toString());
    				huifu.setInterestAmt(commonRequst.getRetInterest());
    			}else{
    				huifu.setInterestAmt("0.00");
    				huifu.setPrincipalAmt("0.00");
    			}
				huifu.setFee("0.00");
				huifu.setInCustId(commonRequst.getInvest_thirdPayConfigId());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim()
									+ huifu.getMerCustId().trim() 
									+ huifu.getProId().trim()
									+ huifu.getOrdId().trim()
									+ huifu.getOrdDate().trim()
									+ huifu.getOutCustId()
									+ huifu.getSubOrdId()
									+ huifu.getSubOrdDate()
									+ huifu.getPrincipalAmt()
									+ huifu.getInterestAmt()
									+ huifu.getFee()
									+ huifu.getInCustId()
									+ huifu.getBgRetUrl();
				MD5 md5 = new MD5();
				System.out.println("MerData="+MerData);
				huifu.setChkValue(Sign(md5.md5(MerData, "utf-8")));
    			System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("params="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println(outStr);
				if(outStr!=null){
					JSONObject object = new JSONObject(outStr);
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("派息成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.get("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("派息失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("还款接口对接失败");
		}
		return commonResponse;
	}
	/**
	 * 流标
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse bidFailed(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huiFu = new Huifu();
		try{
			huiFu = generatePublicData(huiFu, "TenderCancle", true, true);
			if(null!=huiFu){
				huiFu.setOrdId(commonRequst.getOrdId());
				huiFu.setOrdDate(commonRequst.getOrderDate());
				huiFu.setTransAmt(commonRequst.getAmount().toString());
				huiFu.setUsrCustId(commonRequst.getThirdPayConfigId());
				huiFu.setIsUnFreeze(commonRequst.getIsUnFreeze());
				huiFu.setUnFreezeOrdId(commonRequst.getUnFreezeOrdId());
				huiFu.setFreezeTrxId(commonRequst.getFreezeTrxId());
				huiFu.setMerPriv(commonRequst.getMerPriv());
				huiFu.setReqExt(commonRequst.getReqExt());
				huiFu.setPageType(commonRequst.getPageType());
				
				String MerData = huiFu.getVersion()
							   + huiFu.getCmdId()
							   + huiFu.getMerCustId()
							   + huiFu.getOrdId()
							   + huiFu.getOrdDate()
							   + huiFu.getTransAmt()
							   + huiFu.getUsrCustId()
							   + huiFu.getIsUnFreeze()
							   + huiFu.getUnFreezeOrdId()
							   + huiFu.getFreezeTrxId()
							   + huiFu.getRetUrl()
							   + huiFu.getBgRetUrl()
							   + huiFu.getMerPriv()
							   + huiFu.getReqExt()
							   + huiFu.getPageType();
				System.out.println("汇付注册拼接参数===="+MerData);
				huiFu.setChkValue(Sign(MerData));
    			System.out.println("加密后参数===="+huiFu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huiFu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				System.out.println(result);
				System.out.println("参数转map===="+params);
				String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("取消投标成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("取消投标失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return commonResponse;
	}
	/**
	 * 理财计划主动撤销
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse plplanFailed(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huiFu = new Huifu();
		try{
			huiFu = generatePublicData(huiFu, "TenderCancle", true, true);
			if(null!=huiFu){
				huiFu.setVersion("20");
				huiFu.setOrdId(commonRequst.getOrdId());
				huiFu.setOrdDate(commonRequst.getOrderDate());
				huiFu.setTransAmt(commonRequst.getAmount().toString());
				huiFu.setUsrCustId(commonRequst.getThirdPayConfigId());
				huiFu.setIsUnFreeze(commonRequst.getIsUnFreeze());
				huiFu.setUnFreezeOrdId(commonRequst.getRequsetNo());
				huiFu.setFreezeTrxId(commonRequst.getFreezeTrxId());
				huiFu.setMerPriv(commonRequst.getMerPriv());
				huiFu.setReqExt(commonRequst.getReqExt());
				huiFu.setPageType(commonRequst.getPageType());
				
				String MerData = huiFu.getVersion()
							   + huiFu.getCmdId()
							   + huiFu.getMerCustId()
							   + huiFu.getOrdId()
							   + huiFu.getOrdDate()
							   + huiFu.getTransAmt()
							   + huiFu.getUsrCustId()
							   + huiFu.getIsUnFreeze()
							   + huiFu.getUnFreezeOrdId()
							   + huiFu.getFreezeTrxId()
							   + huiFu.getRetUrl()
							   + huiFu.getBgRetUrl()
							   + huiFu.getMerPriv()
							   + huiFu.getReqExt()
							   + huiFu.getPageType();
				MerData = MerData.replace("null", "");
				huiFu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huiFu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				//String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				if(rett!=null){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("取消投标成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("取消投标失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return commonResponse;
	}
	/**
	 * 理财计划的资金解冻接口
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse unFreezePlmanage(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huiFu = new Huifu();
		try{
			huiFu = generatePublicData(huiFu, "UsrUnFreeze", false, true);
			if(null!=huiFu){
				huiFu.setVersion("10");
				huiFu.setOrdId(commonRequst.getOrdId());
				huiFu.setOrdDate(commonRequst.getOrderDate());
				huiFu.setTrxId(commonRequst.getTrxId());
				String MerData = huiFu.getVersion()
							   + huiFu.getCmdId()
							   + huiFu.getMerCustId()
							   + huiFu.getOrdId()
							   + huiFu.getOrdDate()
							   + huiFu.getTrxId()
							   + huiFu.getBgRetUrl();
				MerData = MerData.replace("null", "");
				huiFu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huiFu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				System.out.println("result="+result);
				if(result!=null){
					JSONObject object = new JSONObject(result);
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("理财计划的资金解冻接口成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.get("RespDesc").toString());
					}
					commonResponse.setRequestInfo(result);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("理财计划的资金解冻接口失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return  commonResponse;
	}
	/**
	 * 标的资金解冻接口
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse unFreeze(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huiFu = new Huifu();
		try{
			huiFu = generatePublicData(huiFu, "UsrUnFreeze", false, true);
			if(null!=huiFu){
				huiFu.setVersion("10");
				huiFu.setOrdId(commonRequst.getOrdId());
				huiFu.setOrdDate(commonRequst.getOrderDate());
				huiFu.setTrxId(commonRequst.getFreezeOrdId());
				huiFu.setMerPriv(commonRequst.getMerPriv());
				huiFu.setReqExt(commonRequst.getReqExt());
				huiFu.setPageType(commonRequst.getPageType());
				String MerData = huiFu.getVersion()
							   + huiFu.getCmdId()
							   + huiFu.getMerCustId()
							   + huiFu.getOrdId()
							   + huiFu.getOrdDate()
							   + huiFu.getTrxId()
							   /*+ huiFu.getRetUrl()*/
							   + huiFu.getBgRetUrl()
							   + huiFu.getMerPriv();
				huiFu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huiFu);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				//String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				if(result!=null){
					JSONObject object = new JSONObject(result);
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("标的流标成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.get("RespDesc").toString());
					}
					commonResponse.setRequestInfo(result);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("标的流标失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return  commonResponse;
	}
	
	
	/**
	 * 后台批量还款接口
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse batchRepay(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu  = new Huifu();
		try{
			huifu = generatePublicData(huifu, "BatchRepayment", false, true);
			if(null!=huifu){
				huifu.setVersion("20");
				huifu.setOutCustId(commonRequst.getOutCustId());//出账客户号
				huifu.setOutAcctId(commonRequst.getOutAcctId());//出账客户号
				huifu.setBatchId(commonRequst.getBatchId());//出账客户号
				huifu.setMerOrdDate(commonRequst.getMerOrdDate());//出账客户号
				huifu.setMerPriv(commonRequst.getMerPriv()!=null?commonRequst.getMerPriv():"");//出账客户号
				huifu.setReqExt(commonRequst.getReqExt()!=null?commonRequst.getReqExt():"");//出账客户号
				huifu.setProId(commonRequst.getProId());//出账客户号
				huifu.setInDetails(commonRequst.getInDetails());//出账客户号
				/*Version
				+CmdId + MerCustId + OutCustId +
				OutAcctId+ BatchId+ MerOrdDate + InDetails
				+ BgRetUrl+ MerPriv + ReqExt+ ProId*/
				String merData = huifu.getVersion()
							   + huifu.getCmdId() 
							   + huifu.getMerCustId()
							   + huifu.getOutCustId()
							   + huifu.getOutAcctId()
							   + huifu.getBatchId()
							   + huifu.getMerOrdDate()
							   + huifu.getInDetails()
							   + huifu.getBgRetUrl()
							   + huifu.getMerPriv()
							   + huifu.getReqExt()
							   + huifu.getProId();
				MD5 md5 = new MD5();
				huifu.setChkValue(Sign(md5.md5(merData, "utf-8")));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				System.out.println(result);
				JSONObject result1 = new JSONObject(result);
				if(result1.get("RespCode").toString().trim().equals("000")){
					//储存标的账号(发标的时候应用)
					commonResponse.setRemark(result1.get("ProId").toString().trim());
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("批量还款成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(result1.get("RespDesc").toString());
				}
				commonResponse.setRequestInfo(result);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return commonResponse;
	}
	/**
	 * 平台垫付接口
	 */
	public static CommonResponse transfer1(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu  = new Huifu();
		try{
			huifu = generatePublicData(huifu, "Transfer", false, true);
			if(null!=huifu){
				huifu.setOrdId(commonRequst.getRequsetNo());//出账客户号
				huifu.setOutCustId(commonRequst.getOutCustId());//出账客户号
				huifu.setOutAcctId(commonRequst.getOutAcctId());//出账客户号
				huifu.setTransAmt(commonRequst.getAmount().setScale(2).toString());
				huifu.setInCustId(commonRequst.getInCustId());
				huifu.setInAccId(commonRequst.getInAcctId());
				huifu.setMerPriv(commonRequst.getMerPriv()!=null?commonRequst.getMerPriv():"");//出账客户号
				/*Version +CmdId + OrdId + OutCustId +OutAcctId + TransAmt+ InCustId+ InAcctId+RetUrl + BgRetUrl+ MerPriv*/
				String merData = huifu.getVersion()
							   + huifu.getCmdId() 
							   + huifu.getOrdId()
							   + huifu.getOutCustId()
							   + huifu.getOutAcctId()
							   + huifu.getTransAmt()
							   + huifu.getInCustId()
							   + huifu.getInAccId()
							   + ""
							   + huifu.getBgRetUrl()
							   + huifu.getMerPriv();
				String merData1 = huifu.getVersion()+"|"
				   + huifu.getCmdId() +"|"
				   + huifu.getOrdId()+"|"
				   + huifu.getOutCustId()+"|"
				   + huifu.getOutAcctId()+"|"
				   + huifu.getTransAmt()+"|"
				   + huifu.getInCustId()+"|"
				   + huifu.getInAccId()+"|"
				   + huifu.getRetUrl()+"|"
				   + huifu.getBgRetUrl()+"|"
				   + huifu.getMerPriv();
				System.out.println(merData1);
				huifu.setChkValue(Sign(merData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				System.out.println(result);
				JSONObject result1 = new JSONObject(result);
				if(result1.get("RespCode").toString().trim().equals("000")){
					//储存标的账号(发标的时候应用)
					//commonResponse.setRemark(result1.get("ProId").toString().trim());
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					commonResponse.setResponseMsg("批量还款成功");
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg(result1.get("RespDesc").toString());
				}
				commonResponse.setRequestInfo(result);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return commonResponse;
	}
	
	
	/**
     *  LOANS：放款交易查询 
     *  REPAYMENT：还款交易查询
     *  TENDER：投标交易查询 
     *  CASH：取现交易查询
     *  FREEZE：冻结解冻交易查询
    * @param commonRequst
     * @return
     */
	public static CommonResponse queryTransStat(CommonRequst commonRequst) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"QueryTransStat",true,true);
			if(null != huifu){
				huifu.setOrdId(commonRequst.getQueryRequsetNo());
				huifu.setOrdDate(sdf.format(commonRequst.getStartDay()));
				if(commonRequst.getQueryType().equals("WITHDRAW_RECORD")){
					huifu.setQueryTransType("CASH");
				}else if(commonRequst.getQueryType().equals("REPAYMENT_RECORD")){
					huifu.setQueryTransType("REPAYMENT");
				}else if(commonRequst.getQueryType().equals("PAYMENT_RECORD")){
					huifu.setQueryTransType("LOANS");
				}else{
					huifu.setQueryTransType(commonRequst.getQueryType());
				}
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getOrdId().trim()
									+ huifu.getOrdDate().trim()
									+ huifu.getQueryTransType();
				huifu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				if(outStr!=null){
					JSONObject object = new JSONObject(outStr);
					if(object.getString("RespCode").toString().equals("000")){
						if(object.getString("TransStat").equals("S")){
							List<CommonRecord> recordList = new ArrayList<CommonRecord>();
							CommonRecord record = new CommonRecord();
							String time = object.getString("OrdDate").toString();
							Date d = sdf.parse(time);
							record.setCreateTime(sdf1.format(d));
							record.setAmount(object.getString("TransAmt"));
							if(commonRequst.getQueryType().equals("WITHDRAW_RECORD")){
								if(object.getString("TransStat").equals("S")){
									record.setStatus("SUCCESS");
								}else if(object.getString("TransStat").equals("I")){
									record.setStatus("INIT");
								}else if(object.getString("TransStat").equals("F")){
									record.setStatus("FAILD");
								}
							}else if(commonRequst.getQueryType().equals("REPAYMENT_RECORD")||commonRequst.getQueryType().equals("PAYMENT_RECORD")){
								if(object.getString("TransStat").equals("P")){
									record.setStatus("SUCCESS");
								}else if(object.getString("TransStat").equals("I")){
									record.setStatus("INIT");
								}else if(object.getString("TransStat").equals("F")){
									record.setStatus("FAILD");
								}
							}
							recordList.add(record);
							commonResponse.setRecordList(recordList);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("查询成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("取现尚未成功");
						}
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("查询成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("查询对接失败");
		}
		return commonResponse;
	}
	/**
	 * 交易明细查询(充值)
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse queryTransDetail(CommonRequst commonRequst) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"QueryTransDetail",true,true);
			if(null != huifu){
				huifu.setOrdId(commonRequst.getQueryRequsetNo());
				huifu.setQueryTransType("SAVE");
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getOrdId().trim()
									+ huifu.getQueryTransType();
				huifu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				if(outStr!=null){
					JSONObject object = new JSONObject(outStr);
					if(object.getString("RespCode").toString().equals("000")){
							List<CommonRecord> recordList = new ArrayList<CommonRecord>();
							CommonRecord record = new CommonRecord();
							String time = object.getString("OrdDate").toString();
							Date d = sdf.parse(time);
							record.setCreateTime(sdf1.format(d));
							record.setAmount(object.getString("TransAmt"));
							if(object.getString("TransStat").equals("S")){
								record.setStatus("SUCCESS");
							}else if(object.getString("TransStat").equals("I")){
								record.setStatus("INIT");
							}else if(object.getString("TransStat").equals("F")){
								record.setStatus("FAILD");
							}
							record.setUserNo(object.getString("UsrCustId"));
							record.setStatus("SUCCESS");
							recordList.add(record);
							commonResponse.setRecordList(recordList);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("查询成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("查询对接失败");
		}
		return commonResponse;
	}
	/**
     *  余额查询
     * @param commonRequst
     * @return
     */
	public static CommonResponse queryBalanceBg(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"QueryBalanceBg",true,true);
			if(null != huifu){
				huifu.setVersion("10");
				if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_GETPLATFORM)){
					commonResponse.setThirdPayConfigId(huifu.getMerCustId());
					huifu.setUsrCustId(huifu.getMerCustId());
				}else{
					huifu.setUsrCustId(commonRequst.getThirdPayConfigId());
				}
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getUsrCustId().trim();
				huifu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setAvailableAmount(new BigDecimal(object.getString("AvlBal").replaceAll(",", "").toString()));
						commonResponse.setFreezeAmount(new BigDecimal(object.getString("FrzBal").replaceAll(",", "").toString()));
						commonResponse.setAccountBalance(new BigDecimal(object.getString("AvlBal").replaceAll(",", "").toString()));
						commonResponse.setBalance(new BigDecimal(object.getString("AcctBal").replaceAll(",", "").toString()));
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("余额查询成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("余额查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("余额查询对接失败");
		}
		return commonResponse;
	}
	/**
     *  商户扣款对账
     * @param commonRequst
     * @return
     */
	public static CommonResponse trfReconciliation(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"TrfReconciliation",true,true);
			if(null != huifu){
				huifu.setBeginDate(sdf.format(commonRequst.getQueryStartDate()));
				huifu.setEndDate(sdf.format(commonRequst.getQueryEndDate()));
				if(commonRequst.getPageNumber()!=null&&!commonRequst.getPageNumber().equals("")){
					huifu.setPageNum(commonRequst.getPageNumber());
				}else{
					huifu.setPageNum("1");
				}
				huifu.setPageSize("100");//默认每页查询100条记录
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getBeginDate().trim()
									+ huifu.getEndDate().trim()
									+ huifu.getPageNum().trim()
									+ huifu.getPageSize().trim();
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){ 
						String dtoList= object.getString("TrfReconciliationDtoList");
						if(!dtoList.equals("[]")){
							List<CommonRecord> list=new ArrayList<CommonRecord>();
							JSONArray array = new JSONArray(dtoList);
							for(int i=0;i<array.length();i++){
								JSONObject arrayJect = array.getJSONObject(i);
								CommonRecord record = new CommonRecord();
								record.setAmount(arrayJect.getString("TransAmt"));
								Date d = sdf.parse(arrayJect.getString("PnrDate"));
								String time = sdf1.format(d);
								record.setTime(time);
								record.setRequestNo(arrayJect.getString("OrdId"));
								if(arrayJect.getString("TransStat").equals("S")){
									record.setStatus("交易成功");
								}else if(arrayJect.getString("TransStat").equals("F")){
									record.setStatus("交易失败");
								}else if(arrayJect.getString("TransStat").equals("I")){
									record.setStatus("交易初始化");
								}
								list.add(record);
							}
							commonResponse.setRecordList(list);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("商户扣款对账查询成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("未查询到数据");
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("商户扣款对账查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("商户扣款对账查询对接失败");
		}
		return commonResponse;
	}
	/**
     *  自动扣款转账（商户用）
     * @param commonRequst
     * @return
     */
	public static CommonResponse transfer(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"Transfer",false,true);
			if(null != huifu){
				huifu.setOutCustId(huifu.getMerCustId());
				huifu.setOutAcctId("MDT000001");
				huifu.setTransAmt(commonRequst.getAmount().toString());
				huifu.setInCustId(commonRequst.getThirdPayConfigId());
				huifu.setOrdId(commonRequst.getRequsetNo());
				huifu.setMerPriv(commonRequst.getRequsetNo());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getOrdId().trim() 
									+ huifu.getOutCustId().trim()
									+ huifu.getOutAcctId().trim()
									+ huifu.getTransAmt().trim()
									+ huifu.getInCustId().trim()
									//+ huifu.getRetUrl().trim()
									+ huifu.getBgRetUrl().trim()
									+ huifu.getMerPriv().trim()
									;
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("自动扣款转账（商户用）成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("自动扣款转账（商户用）请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("自动扣款转账（商户用）对接失败");
		}
		return commonResponse;
	}

	public static CommonResponse autobid(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"AutoTender",false,true);
			if(null != huifu){
				huifu.setVersion("20");
				huifu.setOrdId(commonRequst.getRequsetNo());
				huifu.setOrdDate(commonRequst.getOrderDate());
				huifu.setTransAmt(commonRequst.getAmount().toString());
				huifu.setUsrCustId(commonRequst.getThirdPayConfigId());
				huifu.setMaxTenderRate(commonRequst.getMaxTenderRate());
				huifu.setBorrowerDetails(commonRequst.getBorrowerDetails());
				huifu.setIsFreeze(commonRequst.getIsFreeze());
				huifu.setFreezeOrdId(commonRequst.getFreezeOrdId());
				huifu.setMerPriv(commonRequst.getMerPriv());
				huifu.setReqExt(commonRequst.getReqExt()!=null?commonRequst.getReqExt():"");
				//签名验证
				
				/*Version +CmdId + MerCustId + OrdId +
				OrdDate + TransAmt + UsrCustId +
				MaxTenderRate + BorrowerDetails +
				IsFreeze+ FreezeOrdId+ RetUrl +BgRetUrl +
				MerPriv+ ReqExt*/
				
				String  MerData = huifu.getVersion().trim()+
									 huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getOrdId().trim()
									+ huifu.getOrdDate().trim()
									+ huifu.getTransAmt().trim()
									+ huifu.getUsrCustId().trim()
									+ huifu.getMaxTenderRate().trim()
									+ huifu.getBorrowerDetails().trim()
									+ huifu.getIsFreeze().trim()
									+ huifu.getFreezeOrdId().trim()
									+ ""
									+ huifu.getBgRetUrl().trim()
									+ ""
									+ "";
				//签名验证
				String  MerData1 = huifu.getVersion().trim()+"|"
									+ huifu.getCmdId().trim()+"|" 
									+ huifu.getMerCustId().trim()+"|" 
									+ huifu.getOrdId().trim()+"|"
									+ huifu.getOrdDate().trim()+"|"
									+ huifu.getTransAmt().trim()+"|"
									+ huifu.getUsrCustId().trim()+"|"
									+ huifu.getMaxTenderRate().trim()+"|"
									+ huifu.getBorrowerDetails().trim()+"|"
									+ huifu.getIsFreeze().trim()+"|"
									+ huifu.getFreezeOrdId()+"|"
									+ huifu.getRetUrl()+"|"
									+ huifu.getBgRetUrl()+"|"
									+ ""+"|"
									+ "";
              System.out.println(MerData);
              System.out.println(MerData1);
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setLoanNo(object.getString("OrdId"));
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("自动扣款转账（商户用）成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("自动扣款转账（商户用）请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("自动扣款转账（商户用）对接失败");
		}
		return commonResponse;
	} 
	/**
	 * 取消挂牌
	 */
	public static CommonResponse cancelDeal(CommonRequst commonRequst){
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"Transfer",false,true);
			if(null != huifu){
				huifu.setOutCustId(huifu.getMerCustId());
				huifu.setOutAcctId("MDT000001");
				huifu.setTransAmt(commonRequst.getAmount().toString());
				huifu.setInCustId(commonRequst.getThirdPayConfigId());
				huifu.setOrdId(commonRequst.getRequsetNo());
				huifu.setMerPriv(commonRequst.getRequsetNo());
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getOrdId().trim() 
									+ huifu.getOutCustId().trim()
									+ huifu.getOutAcctId().trim()
									+ huifu.getTransAmt().trim()
									+ huifu.getInCustId().trim()
									+ huifu.getBgRetUrl().trim()
									+ huifu.getMerPriv().trim()
									;
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("自动扣款转账（商户用）成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("自动扣款转账（商户用）请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("自动扣款转账（商户用）对接失败");
		}
		return commonResponse;
	}
	/**
     *  充值对账
     * @param commonRequst
     * @return
     */
	public static CommonResponse saveReconciliation(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"SaveReconciliation",true,true);
			if(null != huifu){
				huifu.setBeginDate(sdf.format(commonRequst.getTransactionTime()));
				huifu.setEndDate(sdf.format(new Date()));
				if(commonRequst.getPageNumber()!=null&&!commonRequst.getPageNumber().equals("")){
					huifu.setPageNum(commonRequst.getPageNumber());
				}else{
					huifu.setPageNum("1");
				}
				huifu.setPageSize("100");//默认每页查询100条记录
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getBeginDate().trim()
									+ huifu.getEndDate().trim()
									+ huifu.getPageNum().trim()
									+ huifu.getPageSize().trim();
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){ 
						String dtoList= object.getString("SaveReconciliationDtoList");
						if(!dtoList.equals("[]")){
							List<CommonRecord> list=new ArrayList<CommonRecord>();
							JSONArray array = new JSONArray(dtoList);
							for(int i=0;i<array.length();i++){
								JSONObject arrayJect = array.getJSONObject(i);
								CommonRecord record = new CommonRecord();
								record.setAmount(arrayJect.getString("TransAmt"));
								Date d = sdf.parse(arrayJect.getString("OrdDate"));
								String time = sdf1.format(d);
								record.setTime(time);
								record.setRequestNo(arrayJect.getString("OrdId"));
								if(arrayJect.getString("TransStat").equals("S")){
									record.setStatus("交易成功");
								}else if(arrayJect.getString("TransStat").equals("F")){
									record.setStatus("交易失败");
								}else if(arrayJect.getString("TransStat").equals("I")){
									record.setStatus("交易初始化");
								}
								list.add(record);
							}
							commonResponse.setRecordList(list);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("商户扣款对账查询成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("未查询到数据");
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("商户扣款对账查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("商户扣款对账查询对接失败");
		}
		return commonResponse;
	}
	
	/**
     *  取现对账
     * @param commonRequst
     * @return
     */
	public static CommonResponse cashReconciliation(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"CashReconciliation",true,true);
			if(null != huifu){
				huifu.setBeginDate(sdf.format(commonRequst.getTransactionTime()));
				huifu.setEndDate(sdf.format(new Date()));
				if(commonRequst.getPageNumber()!=null&&!commonRequst.getPageNumber().equals("")){
					huifu.setPageNum(commonRequst.getPageNumber());
				}else{
					huifu.setPageNum("1");
				}
				huifu.setPageSize("100");//默认每页查询100条记录
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getBeginDate().trim()
									+ huifu.getEndDate().trim()
									+ huifu.getPageNum().trim()
									+ huifu.getPageSize().trim();
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){ 
						String dtoList= object.getString("CashReconciliationDtoList");
						if(!dtoList.equals("[]")){
							List<CommonRecord> list=new ArrayList<CommonRecord>();
							JSONArray array = new JSONArray(dtoList);
							for(int i=0;i<array.length();i++){
								JSONObject arrayJect = array.getJSONObject(i);
								CommonRecord record = new CommonRecord();
								record.setAmount(arrayJect.getString("TransAmt"));
								Date d = sdf.parse(arrayJect.getString("PnrDate"));
								String time = sdf1.format(d);
								record.setTime(time);
								record.setRequestNo(arrayJect.getString("OrdId"));
								if(arrayJect.getString("TransStat").equals("S")){
									record.setStatus("交易成功");
								}else if(arrayJect.getString("TransStat").equals("F")){
									record.setStatus("交易失败");
								}else if(arrayJect.getString("TransStat").equals("I")){
									record.setStatus("交易初始化");
								}
								list.add(record);
							}
							commonResponse.setRecordList(list);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("商户扣款对账查询成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("未查询到数据");
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("商户扣款对账查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("商户扣款对账查询对接失败");
		}
		return commonResponse;
	}
	/**
     *  放还款对账
     * @param commonRequst
     * @return
     */
	public static CommonResponse reconciliation(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"Reconciliation",true,true);
			if(null != huifu){
				huifu.setBeginDate(sdf.format(commonRequst.getTransactionTime()));
				huifu.setEndDate(sdf.format(new Date()));
				huifu.setQueryTransType("LOANS");//LOANS：放款交易查询 REPAYMENT：还款交易查询
				if(commonRequst.getPageNumber()!=null&&!commonRequst.getPageNumber().equals("")){
					huifu.setPageNum(commonRequst.getPageNumber());
				}else{
					huifu.setPageNum("1");
				}
				huifu.setPageSize("100");//默认每页查询100条记录
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getBeginDate().trim()
									+ huifu.getEndDate().trim()
									+ huifu.getPageNum().trim()
									+ huifu.getPageSize().trim()
									+ huifu.getQueryTransType().trim();
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){ 
						String dtoList= object.getString("ReconciliationDtoList");
						if(!dtoList.equals("[]")){
							List<CommonRecord> list=new ArrayList<CommonRecord>();
							JSONArray array = new JSONArray(dtoList);
							for(int i=0;i<array.length();i++){
								JSONObject arrayJect = array.getJSONObject(i);
								CommonRecord record = new CommonRecord();
								record.setAmount(arrayJect.getString("TransAmt"));
								Date d = sdf.parse(arrayJect.getString("OrdDate"));
								String time = sdf1.format(d);
								record.setTime(time);
								record.setRequestNo(arrayJect.getString("OrdId"));
								if(arrayJect.getString("TransStat").equals("S")){
									record.setStatus("交易成功");
								}else if(arrayJect.getString("TransStat").equals("F")){
									record.setStatus("交易失败");
								}else if(arrayJect.getString("TransStat").equals("I")){
									record.setStatus("交易初始化");
								}
								list.add(record);
							}
							commonResponse.setRecordList(list);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("商户扣款对账查询成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("未查询到数据");
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("商户扣款对账查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("商户扣款对账查询对接失败");
		}
		return commonResponse;
	}
	/**
     *   标的审核状态查询接口
     * @param commonRequst
     * @return
     */
	public static CommonResponse queryBidInfo(CommonRequst commonRequst) {
		CommonResponse commonResponse=new  CommonResponse();
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"QueryBidInfo",false,false);
			huifu.setVersion("20");
			if(null != huifu){
				huifu.setProId("HRYBID90");
				//签名验证
				huifu.setReqExt("1");
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim() 
									+ huifu.getProId()
									+ huifu.getReqExt()
									+ huifu.getMerPriv();
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				
				//String [] rett=ThirdPayWebClient.operateParameter(HuifuPayUrl(), params,Huifu.CHARSETUTF8);
				
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){ 
						String dtoList= object.getString("CashReconciliation");
						if(!dtoList.equals("[]")){
							List<CommonRecord> list=new ArrayList<CommonRecord>();
							JSONArray array = new JSONArray(dtoList);
							for(int i=0;i<array.length();i++){
								JSONObject arrayJect = array.getJSONObject(i);
								CommonRecord record = new CommonRecord();
								record.setAmount(arrayJect.getString("TransAmt"));
								record.setRequestNo(arrayJect.getString("OrdId"));
								if(arrayJect.getString("TransStat").equals("S")){
									record.setStatus("交易成功");
								}else if(arrayJect.getString("TransStat").equals("F")){
									record.setStatus("交易失败");
								}else if(arrayJect.getString("TransStat").equals("I")){
									record.setStatus("交易初始化");
								}
								list.add(record);
							}
							commonResponse.setRecordList(list);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("商户扣款对账查询成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("未查询到数据");
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("商户扣款对账查询请求失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("商户扣款对账查询对接失败");
		}
		return commonResponse;
	}
	/**
	 * 标的信息补录输入接口
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse addBidAttachInfo(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huiFu = new Huifu();
		try{
			huiFu = generatePublicData(huiFu, "AddBidAttachInfo", false, true);
			if(null!=huiFu){
				huiFu.setVersion("20");
				huiFu.setProId("HRYBID90");
				huiFu.setReqExt("1");
				String MerData = huiFu.getVersion()
							   + huiFu.getCmdId()
							   + huiFu.getMerCustId()
							   + huiFu.getProId()
							   + huiFu.getRetUrl()
							   + huiFu.getBgRetUrl()
							   + huiFu.getMerPriv()
							   + huiFu.getReqExt();
				huiFu.setChkValue(Sign(MerData));
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huiFu);
				String param=ThirdPayWebClient.generateParams(params,Huifu.CHARSETUTF8);
				String result =  ThirdPayWebClient.getWebContentByPost(HuifuPayUrl(),param,Huifu.CHARSETUTF8,12000);
				if(result!=null){
					JSONObject object = new JSONObject(result);
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("标的流标成功");
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.get("RespDesc").toString());
					}
					commonResponse.setRequestInfo(result);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("标的流标失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return  commonResponse;
	}
	public static CommonResponse queryPlateForm(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCommonRequst(commonRequst);
		Huifu huifu = new Huifu();
		try {
			// 生成公共参数
			huifu = generatePublicData(huifu,"QueryAccts",false,false);
			if(null != huifu){
				//签名验证
				String  MerData = huifu.getVersion().trim()
									+ huifu.getCmdId().trim() 
									+ huifu.getMerCustId().trim();
				huifu.setChkValue(Sign(MerData));
				System.out.println("加密后参数===="+huifu.getChkValue());
				// 生成注册map
				Map<String, String> params = ObjectFormatUtil.createMap(Huifu.class,huifu);
				System.out.println("参数转map===="+params);
				String param=ThirdPayWebClient.generateParams(params,MoneyMore.CHARSETUTF8);
				String outStr = ThirdPayWebClient.getUndecodeByPost(	HuifuPayUrl(), param, MoneyMore.CHARSETUTF8,120000);
				System.out.println("outStr="+outStr);
				JSONObject object = new JSONObject(outStr);
				if(outStr!=null&&object!=null){
					if(object.getString("RespCode").toString().equals("000")){
						commonResponse.setThirdPayConfigId(object.getString("MerCustId"));
						System.out.println(object.getString("AcctDetails"));
						String dtoList= object.getString("AcctDetails");
						if(!dtoList.equals("[]")){
							JSONArray array = new JSONArray(dtoList);
							for(int i=0;i<array.length();i++){
								System.out.println(array.get(i));
								JSONObject arrayJect = array.getJSONObject(i);
								if(arrayJect.getString("AcctType")!=null &&!"".equals(arrayJect.getString("AcctType"))){
									if(arrayJect.getString("AcctType").equals("MERDT")){//可用余额
										commonResponse.setBalance(new BigDecimal(arrayJect.getString("AvlBal")==null?"0":arrayJect.getString("AvlBal")));
										//commonResponse.setAccountBalance(arrayJect.getString("AcctBal")==null?"0":arrayJect.getString("AcctBal"));
										commonResponse.setFreezeBalance(arrayJect.getString("FrzBal")==null?"0":arrayJect.getString("FrzBal"));
									}
								}
							}
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("商户余额查询成功");
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg("未查询到数据");
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(object.getString("RespDesc").toString());
					}
					commonResponse.setRequestInfo(outStr);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("商户余额查询失败");
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("商户余额查询接口对接失败");
		}
		return commonResponse;
	}
	
}



