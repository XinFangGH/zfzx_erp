package com.thirdPayInterface.FuDianPay;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiwei.core.util.AppUtil;
import com.rd.bds.common.property.CommonPro;
import com.rd.bds.sign.SecurityException;
import com.rd.bds.sign.util.CFCASignatureUtil;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayWebClient;
import com.thirdPayInterface.FuDianPay.FuDianUtils.HttpsUtil;

@SuppressWarnings("unchecked")
public class FuDianInterfaceUtil { 
	
	private static Log logger=LogFactory.getLog(FuDianInterfaceUtil.class);

	 /**
     * 获取系统配置参数
     */
	private static Map<String,String> configMap = AppUtil.getConfigMap();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 富滇公共数据获取方法
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static FuDian generatePublicData(boolean pageCallBack, boolean notifyCallBack,CommonRequst commonRequst){
		FuDian fuDian=new FuDian();
		Map thirdPayConfig=fuDianProperty();
		if(thirdPayConfig!=null&&!thirdPayConfig.isEmpty()){//判断map中是否为空和有数值
			fuDian.setMerchantNo(thirdPayConfig.get("thirdPay_fudian_platloginname").toString().trim());
//			String BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
			if(notifyCallBack){
				//银行规定，如果要回调，只能调用在白名单里的ip+端口号.
				String BasePath = ServletActionContext.getRequest().getScheme() + "://"+ thirdPayConfig.get("thirdPay_fudian_notifyIp").toString().trim()+ServletActionContext.getRequest().getContextPath() + "/";
				fuDian.setNotifyUrl(BasePath+thirdPayConfig.get("thirdPay_fudian_pageCallUrl").toString().trim());
			}
			/*if(pageCallBack){
				fuDian.setReturnUrl(BasePath+thirdPayConfig.get("thirdPay_fudian_pageCallUrl").toString().trim());
			}*/
			//订单请求日期
			fuDian.setOrderDate(sdf.format(new Date()));
			//流水号
			fuDian.setOrderNo(commonRequst.getRequsetNo());
			fuDian.setAccountNo(commonRequst.getThirdPayConfigId());
			fuDian.setUserName(commonRequst.getThirdPayConfigId0());
			fuDian.setExtMark(commonRequst.getBussinessType());
			
			return fuDian;
		}else{
			return null;
		}
	}
	
	
	
	
	
		/**读取富滇属性文件
		 * 获取富滇配置文件信息（正式环境和测试环境的配置文件）
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public static Map fuDianProperty() {
			Map fuiouConfigMap=new HashMap();
			try{
				Properties props =  new  Properties(); 
				InputStream in=null; 
				if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
					in = FuDianInterfaceUtil.class.getResourceAsStream("FuDianNormalEnvironment.properties"); 
				}else{
					in= FuDianInterfaceUtil.class.getResourceAsStream("FuDianTestEnvironment.properties"); 
				}
				if(in!=null){
					props.load(in);
			    	Iterator it= props.keySet().iterator();
			    	while(it.hasNext()){
			    		String key=(String)it.next();
			    		fuiouConfigMap.put(key, props.getProperty(key));
			    	}
				}
			}catch(Exception ex){
				ex.printStackTrace();
	    		logger.error(ex.getMessage());
	    	}
			return fuiouConfigMap;
		}

	
		
		/**
		 * 根据请求获取访问地址
		 * @param fuDian
		 * @return
		 * 2017-8-28
		 * @tzw
		 */
		private static String fudianPayUrl(FuDian fuDian) {
			Map thirdPayConfig=fuDianProperty();
			//富滇接口调用地址
			String url=thirdPayConfig.get("thirdPay_fudian_URL").toString();
			//支付类型
			String payType = fuDian.getThirdType();
			url += payType;
			System.out.println("三方请求地址："+url);
			return url;
		}
		
		/**
		 * 签名方法
		 * @param fuDian
		 * @return
		 * @throws SecurityException
		 * 2017-8-31
		 * @tzw
		 */
		private static String sign(FuDian fuDian) throws SecurityException {
			// 签名
			String merchant = fuDian.getMerchantNo();
			String pfxPass = CommonPro.getDemoCustomPfxPass();
			String pfxFilePath = CommonPro.getDemoCustomPfxPath();
			String cerFilePath = CommonPro.getDemoHostCerPath();
			String resultData = CFCASignatureUtil.sign(merchant, JSON.toJSONString(fuDian), pfxFilePath, pfxPass,
					cerFilePath);
			System.out.println("请求参数加密明文:"+ JSON.toJSONString(fuDian));
			System.out.println("请求参数reqData:"+ resultData);
			return resultData;
		}
		
		
	
	/**
	 * 富滇个人开户方法
	 * @param commonRequst
	 * @return
	 * 2017-8-25
	 * @tzw
	 */
	public static CommonResponse accountRegister(CommonRequst commonRequst) {
		logger.info("调用富滇开户注册接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(true,true,commonRequst);
			if(null!=fuDian){
				//客户姓名
				fuDian.setRealName(commonRequst.getTrueName());
				//身份证号码
				fuDian.setIdentityCode(commonRequst.getCardNumber());
				//证件号码
				fuDian.setIdentityType(FuDian.IDENTITYTYPE);
				//电话号码
				fuDian.setMobilePhone(commonRequst.getTelephone());
				
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANGOLDREG);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String[] result = ThirdPayWebClient.operateParameter(url, params, FuDian.CHARSETUTF8);
				    //String rest = ThirdPayWebClient.getWebContentByPost(fuiouUrl+url, param, Fuiou.CHARSETUTF8, 12000);
					if(result!=null&&result[0].equals(ThirdPayConstants.RECOD_SUCCESS)){
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						commonResponse.setResponseMsg("注册申请提交成功");
						commonResponse.setCommonRequst(commonRequst);
				    }else{
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("注册申请提交失败");
						commonResponse.setCommonRequst(commonRequst);
				    }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}





	/**
	 * 开户注册（企业开通第三方）
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse accountRefRegister(CommonRequst commonRequst){
		logger.info("调用富滇企业开户注册接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(false,true,commonRequst);
			if(null!=fuDian){
				//法人姓名
				fuDian.setArtificialRealName(commonRequst.getLegal()!=null?commonRequst.getLegal():"");
				//身份证号码
				fuDian.setArtificialIdentityCode(commonRequst.getCardNumber()!=null?commonRequst.getCardNumber():"");
				//企业名称
				fuDian.setCorpName(commonRequst.getTrueName()!=null?commonRequst.getTrueName():"");
				//手机号码
				fuDian.setMobilePhone(commonRequst.getTelephone()!=null?commonRequst.getTelephone():"");
				String threeCardCode = commonRequst.getThreeCardCode();
				if (StringUtils.isEmpty(threeCardCode)) {//非三证合一
					fuDian.setThreeCertUnit("0");
					fuDian.setCreditCode(FuDian.EMPTY);
				}else {//三证合一
					fuDian.setThreeCertUnit("1");
					fuDian.setCreditCode(threeCardCode);//统一社会信用代码
				}
				fuDian.setLicenceCode(commonRequst.getBusinessLicense()!=null?commonRequst.getBusinessLicense():"");//营业执照编号
				fuDian.setOrgCode(commonRequst.getOrgNo()!=null?commonRequst.getOrgNo():"");//组织机构代码
				fuDian.setTaxRegCode(commonRequst.getTaxNo()!=null?commonRequst.getTaxNo():"");//税务登记号
				fuDian.setCorpType(commonRequst.getGuarType()!=null?commonRequst.getGuarType():"1");//公司开户类型：1企业，2担保公司 
				//开通企业账户不需要此参数
				fuDian.setExtMark(null);
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANCORPREG);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					JSONObject resObj = JSON.parseObject(resp);
					String req = resObj.getString("retCode");
					String retMsg = resObj.getString("retMsg");
					System.out.println("同步请求返回值：" + resp);
					if(StringUtils.isNotEmpty(req) && req.equals("0000")){
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						commonResponse.setResponseMsg("注册申请提交成功");
						commonResponse.setCommonRequst(commonRequst);
				    }else{
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(retMsg);
						commonResponse.setCommonRequst(commonRequst);
				    }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}




	/**
	 * 绑定银行卡
	 * @param commonRequst
	 * @return
	 * 2017-8-29
	 * @tzw
	 */
	public static CommonResponse bandCard(CommonRequst commonRequst) {
		logger.info("调用富滇绑卡接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(true,true,commonRequst);
			if(null!=fuDian){
				//签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址   绑卡
					if (commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BINDCARD)) {
						fuDian.setThirdType(FuDian.FUDIANBINDCARD);
					}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_REPLACECARD)){//换卡
						fuDian.setThirdType(FuDian.FUDIANCHANGECARD);
					}
					
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String[] result = ThirdPayWebClient.operateParameter(url, params, FuDian.CHARSETUTF8);
					if(result!=null&&result[0].equals(ThirdPayConstants.RECOD_SUCCESS)){
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						commonResponse.setResponseMsg("绑卡申请提交成功");
						commonResponse.setCommonRequst(commonRequst);
				    }else{
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("绑卡申请提交失败");
						commonResponse.setCommonRequst(commonRequst);
				    }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}
	/**
	 * 修改手机号码
	 * @param commonRequst
	 * @return
	 * 2017-8-30
	 * @tzw
	 */
	public static CommonResponse changePhone(CommonRequst commonRequst) {
		logger.info("调用富滇修改手机号接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(true,true,commonRequst);
			if(null!=fuDian){
				//业务
				fuDian.setNewPhone(commonRequst.getTelephone());
				fuDian.setType(FuDian.UPDATEPHONETYPE1);//修改手机号方式
				
				//签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址   绑卡
					fuDian.setThirdType(FuDian.FUDIANCHANGEPHONE);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String[] result = ThirdPayWebClient.operateParameter(url, params, FuDian.CHARSETUTF8);
					if(result!=null&&result[0].equals(ThirdPayConstants.RECOD_SUCCESS)){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						commonResponse.setResponseMsg("申请提交成功");
						commonResponse.setCommonRequst(commonRequst);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("申请提交失败");
						commonResponse.setCommonRequst(commonRequst);
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}





	/**
	 * 充值接口
	 * @param commonRequst
	 * @return
	 * 2017-8-31
	 * @tzw
	 */
	public static CommonResponse recharge(CommonRequst commonRequst) {
		logger.info("调用富滇充值接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(true,true,commonRequst);
			if(null!=fuDian){
				/*
				 * 业务处理
				 * 增加方法只需修改此处参数，以及请求路径参数即可
				 */
				fuDian.setAmount(commonRequst.getAmount().toString());
				fuDian.setFee(commonRequst.getFee().toString());
				fuDian.setPayType(commonRequst.getBankFastPay());
				
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址  充值
					//手机端
					if (StringUtils.isNotEmpty(commonRequst.getIsMobile()) && "1".equals(commonRequst.getIsMobile())) {
						fuDian.setThirdType(FuDian.FUDIANRECHARGEPHONE);
					}else { //pc端
						fuDian.setThirdType(FuDian.FUDIANRECHARGE);
					}
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String[] result = ThirdPayWebClient.operateParameter(url, params, FuDian.CHARSETUTF8);
					if(result!=null&&result[0].equals(ThirdPayConstants.RECOD_SUCCESS)){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						commonResponse.setResponseMsg("申请提交成功");
						commonResponse.setCommonRequst(commonRequst);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("申请提交失败");
						commonResponse.setCommonRequst(commonRequst);
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}




	/**
	 * 取现接口
	 * @param commonRequst
	 * @return
	 * 2017-9-4
	 * @tzw
	 */
	public static CommonResponse withdraw(CommonRequst commonRequst) {
		logger.info("调用富滇取现接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(true,true,commonRequst);
			if(null!=fuDian){
				/*
				 * 业务处理
				 * 增加方法只需修改此处参数，以及请求路径参数即可
				 */
				fuDian.setAmount(commonRequst.getAmount().toString());
				fuDian.setFee(commonRequst.getFee().toString());
				fuDian.setVerifyType(FuDian.VERIFYTYPE0);
				
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址  取现
					fuDian.setThirdType(FuDian.FUDIANWITHDRAW);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String[] result = ThirdPayWebClient.operateParameter(url, params, FuDian.CHARSETUTF8);
					if(result!=null&&result[0].equals(ThirdPayConstants.RECOD_SUCCESS)){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
						commonResponse.setResponseMsg("申请提交成功");
						commonResponse.setCommonRequst(commonRequst);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("申请提交失败");
						commonResponse.setCommonRequst(commonRequst);
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}





	/**
	 * 发标接口
	 * @param commonRequst
	 * @return
	 * 2017-9-4
	 * @tzw
	 */
	public static CommonResponse createBid(CommonRequst commonRequst) {
		logger.info("调用发标接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(false,true,commonRequst);
			if(null!=fuDian){
				
				//业务参数start-------------------------------
				boolean flag = true; 
				//标的名称
				fuDian.setLoanName(commonRequst.getBidName());
				//标的金额
				fuDian.setAmount(commonRequst.getBorrTotAmt());
				// 标的完成时间
//				fuDian.setExpectRepayTime(commonRequst.getBidEndDate());
				//投标截止日期
				fuDian.setEndTime(commonRequst.getBidEndDate());
				//年利率
				fuDian.setInterestRate(commonRequst.getYearRate());


				if(flag) {//发标类型  1 普通
					fuDian.setLoanType(FuDian.LOANTYPE1);
					fuDian.setVouchUserName(""); //担保公司富滇银行账户名
					fuDian.setVouchAccountNo("");//担保公司富滇银行账号
				}else {//担保标   担保公司用户名和账号必填
					fuDian.setLoanType(FuDian.LOANTYPE3);
					fuDian.setVouchUserName("");//担保公司富滇银行账户名
					fuDian.setVouchAccountNo("");//担保公司富滇银行账号
				}
				
				//业务参数end--------------------------------
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANCREATEBID);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					if (StringUtils.isNotEmpty(resp)) {
						JSONObject resObj = JSON.parseObject(resp);
						String req = resObj.getString("retCode");
						String retMsg = resObj.getString("retMsg");
						System.out.println("同步请求返回值：" + resp);
						if(StringUtils.isNotEmpty(req) && req.equals("0000")){
							String content = resObj.getString("content");//主体
							JSONObject result = JSON.parseObject(content);
							String OrderDate = result.getString("orderDate");//发标的订单日期
							String OrderNo =result.getString("orderNo");//发标的订单流水号
							String loanTxNo =result.getString("loanTxNo");//标的号
							if (content.contains("loanAccNo")) {
								String loanAccNo =result.getString("loanAccNo");//标的号
								commonResponse.setLoanAccNo(loanAccNo);
							}
							commonResponse.setLoanOrderNo(OrderNo);
							commonResponse.setLoanOrderDate(OrderDate);
							commonResponse.setLoanTxNo(loanTxNo);
							commonResponse.setRemark(loanTxNo);
					    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							commonResponse.setResponseMsg("提交成功");
							commonResponse.setCommonRequst(commonRequst);
					    }else{
					    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg(retMsg);
							commonResponse.setCommonRequst(commonRequst);
					    }
					}else {
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("提交失败");
						commonResponse.setCommonRequst(commonRequst);
					}
					
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}




	/**
	 * 流标接口
	 * @param commonRequst
	 * @return
	 * 2017-9-4
	 * @tzw
	 */
	public static CommonResponse preAuthorizationCancel(
			CommonRequst commonRequst) {
		logger.info("调用发标接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(false,true,commonRequst);
			if(null!=fuDian){
				
				//业务参数start-------------------------------
				
				fuDian.setLoanOrderDate(commonRequst.getOrderDate());//loanOrderDate
				fuDian.setLoanOrderNo(commonRequst.getQueryRequsetNo());//发标的订单流水号
				fuDian.setLoanTxNo(commonRequst.getLoanNoList());//三方返回的标的号
				
				//业务参数end--------------------------------
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANCANCELBID);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					JSONObject resObj = JSON.parseObject(resp);
					String req = resObj.getString("retCode");
					String retMsg = resObj.getString("retMsg");
					System.out.println("同步请求返回值：" + resp);
					if(StringUtils.isNotEmpty(req) && req.equals("0000")){
						String content = resObj.getString("content");//主体
						JSONObject result = JSON.parseObject(content);
						String status =result.getString("status");//标的状态  0：开标、1：投标中、2：还款中、3：已还款、4：结束、5：撤标
						if (StringUtils.isNotEmpty(status)) {
							if (status.equals("5")) {
								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
								commonResponse.setResponseMsg("流标成功");
							}else {
								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
								commonResponse.setResponseMsg("流标申请提交成功");
							}
						}
						commonResponse.setCommonRequst(commonRequst);
				    }else{
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(retMsg);
						commonResponse.setCommonRequst(commonRequst);
				    }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}





	/**
	 * 满标放款接口
	 * @param commonRequst
	 * @return
	 * 2017-9-5
	 * @tzw
	 */
	public static CommonResponse allot(CommonRequst commonRequst) {
		logger.info("调用放款接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(false,true,commonRequst);
			if(null!=fuDian){
				
				//业务参数start-------------------------------
				
				fuDian.setLoanOrderDate(commonRequst.getOrderDate());//loanOrderDate
				fuDian.setLoanOrderNo(commonRequst.getLoanNoList());//发标的订单流水号
				fuDian.setLoanTxNo(commonRequst.getTaxNo());//三方返回的标的号
				fuDian.setLoanFee(commonRequst.getFee().toString());
				//增加预计还款日期  
				fuDian.setExpectRepayTime(commonRequst.getBidEndDate());
				
				//业务参数end--------------------------------
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANFULL);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					JSONObject resObj = JSON.parseObject(resp);
					String req = resObj.getString("retCode");
					String retMsg = resObj.getString("retMsg");
					System.out.println("同步请求返回值：" + resp);
					if(StringUtils.isNotEmpty(req) && req.equals("0000")){
						String content = resObj.getString("content");//主体
						JSONObject result = JSON.parseObject(content);
						String status =result.getString("status");//标的状态  0：开标、1：投标中、2：还款中、3：已还款、4：结束、5：撤标
						if (StringUtils.isNotEmpty(status)) {
							if (status.equals("2")) {
								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
								commonResponse.setResponseMsg("放款成功");
							}else {
								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
								commonResponse.setResponseMsg("放款申请提交成功");
							}
						}
						commonResponse.setCommonRequst(commonRequst);
				    }else{
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(retMsg);
						commonResponse.setCommonRequst(commonRequst);
				    }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}


	/**
	 * 富滇用户信息查询接口
	 * @param commonRequst
	 * @return
	 * 2017-9-11
	 * @tzw
	 */
	public static CommonResponse balanceQuery(CommonRequst commonRequst) {
		logger.info("调用用户信息查询接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(false,false,commonRequst);
			if(null!=fuDian){
				
				//业务参数start-------------------------------
				
				
				//业务参数end--------------------------------
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANQUERYUSER);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					if (StringUtils.isNotEmpty(resp)) {
						JSONObject resObj = JSON.parseObject(resp);
						String req = resObj.getString("retCode");
						String retMsg = resObj.getString("retMsg");
						System.out.println("同步请求返回值：" + resp);
						if(StringUtils.isNotEmpty(req) && req.equals("0000")){
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
							String content = resObj.getString("content");//主体
							commonResponse.setCommonRequst(commonRequst);
							dealQueryMessage(commonResponse,content);
					    }else{
					    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
							commonResponse.setResponseMsg(retMsg);
							commonResponse.setCommonRequst(commonRequst);
					    }
					}else {
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg("三方请求失败");
						commonResponse.setCommonRequst(commonRequst);
					}
					
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
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
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
		}
		return commonResponse;
	}

	/**
	 * 处理查询个人信息返回数据
	 * @param commonResponse
	 * @param content
	 * @return
	 * 2017-9-11
	 * @tzw
	 */
	private static CommonResponse dealQueryMessage(CommonResponse commonResponse,String content){
		//账户可用余额  以元为单位
		if (content.contains("balance")) {
			String balance = JSON.parseObject(content).getString("balance");
			commonResponse.setBalance(new BigDecimal(balance));
		}
		
		//账户可提现金额  以元为单位
		if (content.contains("withdrawBalance")) {
			String withDrawbalance = JSON.parseObject(content).getString("withdrawBalance");
			commonResponse.setWithDrawbalance(new BigDecimal(withDrawbalance));
		}
		
		//账户冻结余额  以元为单位
		if (content.contains("freezeBalance")) {
			String freezeBalance = JSON.parseObject(content).getString("freezeBalance");
			commonResponse.setFreezeAmount(new BigDecimal(freezeBalance));
		}
		
		//证件号码
		if (content.contains("identityCode")) {
			String identityCode = JSON.parseObject(content).getString("identityCode");
			commonResponse.setCardCode(identityCode);
		}
		
		//授权结果
		if (content.contains("authorization")) {
			String authorization = JSON.parseObject(content).getString("authorization");

			//loanInvest，投资授权0000代表成功，其他代表失败或者没有授权；
			if (authorization.contains("loanInvest")) {
				String loanInvest = JSON.parseObject(authorization).getString("loanInvest");
				commonResponse.setLoanInvest(loanInvest);
			}
			
			//loanRepay，还款授权0000代表成功，其他代表失败或者没有授权
			if (authorization.contains("loanRepay")) {
				String loanRepay = JSON.parseObject(authorization).getString("loanRepay");
				commonResponse.setLoanRepay(loanRepay);
			}
		}
		
		//账户状态   1代表账户状态正常，2代表账户冻结，3代表账户挂失，4账户销户
		if (content.contains("status")) {
			String state = JSON.parseObject(content).getString("status");
			commonResponse.setState(state);
		}
		
		return commonResponse;
	}
	
	
	
	/**
	 * 自动投标接口
	 * @param commonRequst
	 * @return
	 * 2017-9-12
	 * @tzw
	 */
	public static CommonResponse preAuthorization(CommonRequst commonRequst) {
		logger.info("调用发标接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(false,true,commonRequst);
			if(null!=fuDian){
				
				//业务参数start-------------------------------
				
				//投标金额
				fuDian.setAmount(commonRequst.getAmount().toString());
				//奖励金额
				fuDian.setAward("0");
				//发标的时候三方生成的标的标识
				fuDian.setLoanTxNo(commonRequst.getProId());
				
				//业务参数end--------------------------------
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANFASTINVEST);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					JSONObject resObj = JSON.parseObject(resp);
					String req = resObj.getString("retCode");
					String retMsg = resObj.getString("retMsg");
					System.out.println("同步请求返回值：" + resp);
					if(StringUtils.isNotEmpty(req) && req.equals("0000")){
//						String content = resObj.getString("content");//主体
//						JSONObject result = JSON.parseObject(content);
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("投标成功");
						commonResponse.setCommonRequst(commonRequst);
				    }else{
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(retMsg);
						commonResponse.setCommonRequst(commonRequst);
				    }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}
	
	
	
	/**
	 * 商户转账接口
	 * @param commonRequst
	 * @return
	 */
	public static CommonResponse transfers(CommonRequst commonRequst) {
		logger.info("调用发标接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			FuDian fuDian=generatePublicData(false,false,commonRequst);
			if(null!=fuDian){
				
				//业务参数start-------------------------------
				//转账金额
				fuDian.setAmount(commonRequst.getAmount().toString());
				//业务参数end--------------------------------
				// 签名
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址
					fuDian.setThirdType(FuDian.FUDIANMERCHANTTRANSFER);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					JSONObject resObj = JSON.parseObject(resp);
					String req = resObj.getString("retCode");
					String retMsg = resObj.getString("retMsg");
					System.out.println("同步请求返回值：" + resp);
					if(StringUtils.isNotEmpty(req) && req.equals("0000")){
						String content = resObj.getString("content");//主体
						//获取结果
						if (content.contains("status")) {
							String status = JSON.parseObject(content).getString("status");
							if ("1".equals(status)) {
								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
								commonResponse.setResponseMsg("转账成功");
							}else {
								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
								commonResponse.setResponseMsg(retMsg);
							}
						}
						commonResponse.setCommonRequst(commonRequst);
						
				    }else{
				    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(retMsg);
						commonResponse.setCommonRequst(commonRequst);
				    }
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("用户开户注册对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="用户开户注册对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}
	
	
	/**
	 * 订单查询接口
	 * 不跳页面   无同步/异步回调
	 * @param commonRequst
	 * @return
	 * 2017-9-11
	 * @tzw
	 */
	public static CommonResponse queryDealInfo(CommonRequst commonRequst) {
		logger.info("调用富滇查询接口开始时间："+new Date());
		Object[]  ret=new Object[3];
		CommonResponse commonResponse=new  CommonResponse();
		try{
			//实时回调
			FuDian fuDian=generatePublicData(false,false,commonRequst);
			if(null!=fuDian){
				/*
				 * 业务处理
				 * 增加方法只需修改此处参数，以及请求路径参数即可
				 */
				fuDian.setQueryOrderDate(commonRequst.getSubOrdDate());//查询订单日期
				fuDian.setQueryOrderNo(commonRequst.getOldBidRequestNo());//查询订单流水号
				fuDian.setQueryType(commonRequst.getQueryType());//交易类型
				
				String resultData = sign(fuDian);
				if(null!=resultData){
					//获取富滇请求地址 
					fuDian.setThirdType(FuDian.QUERYDEALINFO);
					String url=fudianPayUrl(fuDian);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("reqData", resultData);
					String resp = HttpsUtil.getInstance().doPostRetString(url, null, params);
					JSONObject resObj = JSON.parseObject(resp);
					System.out.println("同步请求返回值：" + resp);
					String req = resObj.getString("retCode");
					String retMsg = resObj.getString("retMsg");
					String content = null;
					content = resObj.getString("content");
					if(StringUtils.isNotEmpty(req) && req.equals("0000")){
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						commonResponse.setResponseMsg("信息获取成功");
						commonResponse.setCommonRequst(commonRequst);
						//获取状态
						if (content.contains("queryState")) {
							String state = JSON.parseObject(content).getString("queryState");
							commonResponse.setState(state);
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						commonResponse.setResponseMsg(retMsg);
						commonResponse.setCommonRequst(commonRequst);
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					commonResponse.setResponseMsg("生成签名失败");
					commonResponse.setCommonRequst(commonRequst);
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="生成签名失败";
					ret[2]=commonResponse;
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				commonResponse.setResponseMsg("基本参数获取失败");
				commonResponse.setCommonRequst(commonRequst);
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonResponse;
			}
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("对接失败"+e.getMessage());
			commonResponse.setCommonRequst(commonRequst);
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="对接失败"+e.getMessage();
			ret[2]=commonResponse;
		}
		return commonResponse;
	}

	
}
	
	
