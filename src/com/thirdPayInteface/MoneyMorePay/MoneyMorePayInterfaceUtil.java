package com.thirdPayInteface.MoneyMorePay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
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

import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.pay.MoneyMoreMore;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.UrlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.Constants;
import com.zhiwei.credit.model.pay.ResultBean;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;
import com.thirdPayInteface.CommonRequestInvestRecord;
import com.thirdPayInteface.CommonRequst;
import com.thirdPayInteface.CommonResponse;
import com.thirdPayInteface.ThirdPayConstants;
import com.thirdPayInteface.ThirdPayUtil;
import com.thirdPayInteface.ThirdPayWebClient;
import com.thirdPayInteface.MoneyMorePay.MoneyMoreUtil.MD5;
import com.thirdPayInteface.MoneyMorePay.MoneyMoreUtil.SignUtil;
import com.thirdPayInteface.MoneyMorePay.MoneyResponse.MadaiLoanInfoBean;
import com.thirdPayInteface.MoneyMorePay.MoneyResponse.QueryExtractionBean;
import com.thirdPayInteface.MoneyMorePay.MoneyResponse.QueryRechargeBean;
import com.thirdPayInteface.MoneyMorePay.MoneyResponse.QueryTransferBean;
import com.thirdPayInteface.SinaPay.SinaPay;
import com.thirdPayInteface.YeePay.Repayment;
import com.thirdPayInteface.YeePay.YeePay;
import com.thirdPayInteface.YeePay.YeePayInterfaceUtil;
import com.zhiwei.core.util.AppUtil;


public class MoneyMorePayInterfaceUtil {
	private static Log logger=LogFactory.getLog(MoneyMorePayInterfaceUtil.class);
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
	private static String moneyMorePayUrl(){
		Map moneyMoreConfigMap=moneyMoreProperty();
		//双乾支付调用地址
		String moneyMorePayUrl=moneyMoreConfigMap.get("MM_PostUrl").toString();
		return moneyMorePayUrl;
	}
	/**
	 * 获取双乾支付的第三方支付环境参数
	 * @return
	 */
	private static Map moneyMoreProperty(){
		Map moneyMoreConfigMap=new HashMap();
		try{
			InputStream in=null;
			//获取当前支付环境为正式环境还是测试环境
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
		       in = MoneyMorePayInterfaceUtil.class.getResourceAsStream("MoneyMoreNormalEnvironment.properties"); 
			}else{
		        in = MoneyMorePayInterfaceUtil.class.getResourceAsStream("MoneyMoreTestEnvironment.properties"); 
			}
			Properties props =  new  Properties(); 
			if(in!=null){
				props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		moneyMoreConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    		logger.error(ex.getMessage());
    	}
		return moneyMoreConfigMap;
	}
	/**
	 * 获取通用参数
	 * @param returnURL
	 * @param notifyURL
	 * @param randomStamp
	 * @return
	 */
	public static MoneyMore generatePublicData(String returnURL, String notifyURL,boolean randomStamp) {
		// TODO Auto-generated method stub
		Map thirdPayConfig=moneyMoreProperty();
		if(thirdPayConfig!=null){
			MoneyMore moneyMore=new MoneyMore();
			if(randomStamp){
				//是否启用防抵赖 
				moneyMore.setRandomTimeStamp(Common.getRandomNum(2)+DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS")); 
			}
			//平台乾多多标识
			moneyMore.setPlatformMoneymoremore(thirdPayConfig.get("MM_PlatformMoneymoremore").toString().trim());
			String BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
			if(!returnURL.equals("")){
				moneyMore.setReturnURL(BasePath+returnURL);//页面返回网址
			}
			if(!notifyURL.equals("")){
				moneyMore.setNotifyURL(BasePath+notifyURL);//后台通知网址 
			}
			return moneyMore;
		}else{
			return null;
		}
		
	}
	 /**
     * 实体对象  转map
     * @param request
     * @return
     */
    public static Map getParameterMap(MoneyMore moneyMore,String dataStr,Map moneyMoreConfigMap) {
    	Map map = new HashMap();;
		try{
			if(moneyMore!=null&&moneyMoreConfigMap!=null){
		        String privatekey =moneyMoreConfigMap.get("MM_PrivateKey").toString();
				SignUtil rsa = SignUtil.getInstance();
				MD5 md5=new MD5();
				String sign =  (rsa.signData(md5.getMD5Info(dataStr), privatekey));
				//封装Map
				map = ThirdPayUtil.createMap(moneyMore.getClass(), moneyMore);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  map;
    }
	/**
	 * 签名
	 * @param moneyMore
	 * @param dataStr
	 * @return
	 */
	public static MoneyMore getReqSign(MoneyMore moneyMore,String dataStr){
		Map moneyMoreConfigMap=moneyMoreProperty();
		String privatekey = moneyMoreConfigMap.get("MM_PrivateKey").toString();
		// 签名
		SignUtil rsa = SignUtil.getInstance();
		MD5 md5=new MD5();
		moneyMore.setSignInfo (rsa.signData(md5.getMD5Info(dataStr), privatekey));
		return moneyMore;
	}
	
	//==================================接口开始=========================================
	/**
	 * 网关接口01--个人用户注册
	 */
	public static Object[] register(CommonRequst commonRequst){
		logger.info("调用双乾支付网关用户注册接口开始时间："+new Date());
		Map moneyMoreConfigMap=moneyMoreProperty();
		CommonResponse commonResponse=new  CommonResponse();
		ResultBean resultBean=new ResultBean();
		Object[]  ret=new Object[3];
		try{
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				moneyMore.setLoanPlatformAccount(commonRequst.getLoginname());//网贷平台登录名
				moneyMore.setRegisterType(commonRequst.getRegisterType());//1表示全自动，2表示半自动
				moneyMore.setMobile(commonRequst.getTelephone());//手机号
				moneyMore.setEmail(commonRequst.getEmail());//邮箱
				moneyMore.setAccountType(null);//"" 为个人客户，1 为企业客户
				moneyMore.setRealName(commonRequst.getTrueName().trim());//会员真实姓名
				moneyMore.setIdentificationNo(commonRequst.getCardNumber());//身份证好/营业执照号
				moneyMore.setImage1("");//身份证/营业执照正面
				moneyMore.setImage2("");//身份证/营业执照反面
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());////自定义备注1
				//拼接字符串生成签名
				String dataStr = moneyMore.getRegisterType()+moneyMore.getAccountType()+ moneyMore.getMobile() 
								+ moneyMore.getEmail() +moneyMore.getRealName()+ moneyMore.getIdentificationNo() 
								+ moneyMore.getImage1() + moneyMore.getImage2() +moneyMore.getLoanPlatformAccount() 
								+ moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString() + moneyMore.getRandomTimeStamp() 
								+ moneyMore.getRemark1() + moneyMore.getRemark2() + moneyMore.getRemark3() + moneyMore.getReturnURL() 
								+ moneyMore.getNotifyURL();
				
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				//map对象经过编码转变成post提交的string对象
				if(commonRequst.getRegisterType()!=null&&commonRequst.getRegisterType()==1){//全自动
					String param=UrlUtils.generateParams(map,MoneyMore.CHARSETUTF8);
					String rett=ThirdPayWebClient.getWebContentByPost(moneyMorePayurl, param,MoneyMore.CHARSETUTF8,12000);
					if(rett!=null&&!rett.equals("")){
						Gson gson=new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
						resultBean=setBindCodeMsg(gson.fromJson(rett, ResultBean.class));
						if(resultBean.getResultCode().equals("88")){
							commonResponse.setThirdPayConfigId(resultBean.getMoneymoremoreId());
							commonResponse.setTruename(commonRequst.getTrueName().trim());
							commonResponse.setCardCode(commonRequst.getCardNumber());
							ret[0]=ThirdPayConstants.RECOD_SUCCESS;
							ret[1]="个人用户注册成功";
							ret[2]=commonResponse;
						}else{
							ret[0]=ThirdPayConstants.RECOD_FAILD;
							ret[1]=resultBean.getCodeMsg();
							ret[2]=commonRequst;
						}
					}
				}else{//半自动
					String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
					logger.info("调用双乾支付网转账接口参数："+rett[1]);
					if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
						ret[0]=ThirdPayConstants.RECOD_SUCCESS;
						ret[1]="个人用户注册申请提交";
						ret[2]=commonRequst;
					}else{
						ret[0]=ThirdPayConstants.RECOD_FAILD;
						ret[1]=rett[1];
						ret[2]=commonRequst;
					}
				}
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="个人用户注册对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		
		return ret;
	}
	/**
	 * 转账接口--------------->>(投标),(发红包)
	 * @param commonRequst
	 * @return
	 */
	public static Object[] transfer(CommonRequst commonRequst){
		logger.info("调用双乾支付网转账接口开始时间："+new Date());
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=null;
			if(commonRequst.getTransferAction().equals("1")){//投标
				 moneyMore=generatePublicData("","",true);
				 moneyMore.setNeedAudit(null);//需要审核
			}else{//发红包
				 moneyMore=generatePublicData("","",true);
				 moneyMore.setNeedAudit(moneyMore.ACTION_1);//自动通过
			}
			if(moneyMore!=null){
				//生成转账列表
				moneyMore = loanJsonList(commonRequst,moneyMore);
				moneyMore.setTransferAction(moneyMore.TACTION_1);//转账类型1.投标，2.还款，	3.其他
				moneyMore.setAction(moneyMore.ACTION_1);//操作类型1.手动转账，2.自动转账
				moneyMore.setTransferType(moneyMore.TTYPE_2);//转账方式，1.桥连	2.直连
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());//自定义备注2
				moneyMore.setRemark3("");
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr = moneyMore.getLoanJsonList() + moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString()
								+ moneyMore.getTransferAction() + moneyMore.getAction() + moneyMore.getTransferType() + moneyMore.getNeedAudit()
								+ moneyMore.getRandomTimeStamp() + moneyMore.getRemark1() + moneyMore.getRemark2() + moneyMore.getRemark3() 
								+ moneyMore.getReturnURL() + moneyMore.getNotifyURL();
				
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付网转账接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户投标申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 转账接口--------------->>(还款)
	 * @param commonRequst
	 * @return
	 */
	public static Object[] repayment(CommonRequst commonRequst){
		logger.info("调用双乾支付网转账接口开始时间："+new Date());
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				//生成转账列表
				List<CommonRequestInvestRecord> repaymemntList = commonRequst.getRepaymemntList();
				List<MadaiLoanInfoBean> listmlib = new ArrayList<MadaiLoanInfoBean>();
				//设置LoanJsonList还款列表
				for(CommonRequestInvestRecord temp:repaymemntList){
					MadaiLoanInfoBean mlib = new MadaiLoanInfoBean();
					mlib.setLoanOutMoneymoremore(temp.getInvest_thirdPayConfigId());//投资人第三方标识
					mlib.setLoanInMoneymoremore(temp.getLoaner_thirdPayConfigId());//借款人第三方标识
					mlib.setOrderNo(temp.getRequestNo());//交易流水号
					mlib.setBatchNo(commonRequst.getBidId());//标的编号
					mlib.setAmount(temp.getAmount().toString());//交易金额
					mlib.setFullAmount(commonRequst.getPlanMoney().toString());//满标标额
					mlib.setTransferName(commonRequst.getTransferName());//用途
					mlib.setRemark(commonRequst.getRemark1());//定义备注
					listmlib.add(mlib);
				}
				moneyMore.setLoanJsonList(Common.JSONEncode(listmlib));
				moneyMore.setTransferAction(moneyMore.TACTION_1);//转账类型1.投标，2.还款，	3.其他
				moneyMore.setAction(moneyMore.ACTION_1);//操作类型1.手动转账，2.自动转账
				moneyMore.setTransferType(moneyMore.TTYPE_2);//转账方式，1.桥连	2.直连
				moneyMore.setNeedAudit(moneyMore.ACTION_1);//通过是否需要审核，空需要审核，1自动通过
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());//自定义备注2
				moneyMore.setRemark3("");
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr=moneyMore.getLoanJsonList() +  moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString() + moneyMore.getTransferAction() 
								+ moneyMore.getAction() +  moneyMore.getTransferType() + moneyMore.getNeedAudit() + moneyMore.getRandomTimeStamp()
								+ moneyMore.getRemark1() + moneyMore.getRemark2() + moneyMore.getRemark3() 
								+ moneyMore.getReturnURL() + moneyMore.getNotifyURL();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付网转账接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户转账申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	
	/**
	 * 余额查询接口
	 * @param commonRequst
	 * @return
	 */
	public static Object[] balanceQueryMoneys(CommonRequst commonRequst){
		logger.info("调用双乾支付余额查询接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				moneyMore.setPlatformId(commonRequst.getThirdPayConfigId());//查询的第三方标识号
				moneyMore.setPlatformType(commonRequst.getPlatformType());//平台乾多多账户类型，当查询平台账户时必填，1.托管账户，2.自有账户
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr = moneyMore.getPlatformId() + moneyMore.getPlatformType() + moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String param=ThirdPayWebClient.generateParams(map,MoneyMore.CHARSETUTF8);
				String  retdata=ThirdPayWebClient.getWebContentByPost(moneyMorePayurl, param,MoneyMore.CHARSETUTF8,120000);
				logger.info("注册用户查询结果："+retdata);
				if(retdata!=null){
					if(retdata!=null&&!retdata.equals("")){
						String[] money=retdata.replace("\r", "").replace("\n", "").split("\\|");
						commonResponse.setBalance(new BigDecimal(money[0]));//网贷平台可提现余额
						commonResponse.setAvailableAmount(new BigDecimal(money[1]));//可投标余额(子账户可用余额+公共账户可用余额)
						commonResponse.setFreezeAmount(new BigDecimal(money[2]));//子账户冻结余额
					}
					 ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					 ret[1]="用户信息查询成功";
					 ret[2]=commonResponse;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="用户信息查询失败";
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 充值接口
	 * @param commonRequst
	 * @return
	 */
	public static Object[] rechareMoney(CommonRequst commonRequst){
		logger.info("调用双乾支付充值接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				moneyMore.setRechargeMoneymoremore(commonRequst.getThirdPayConfigId());//充值的钱多多标识
				moneyMore.setOrderNo(commonRequst.getRequsetNo());//交易流水号
				moneyMore.setAmount(commonRequst.getAmount().doubleValue());//交易金额
				moneyMore.setRechargeType(commonRequst.getRechargeType());//充值类型
				moneyMore.setFeeType(commonRequst.getFeeType());//费用类型
				moneyMore.setCardNo(commonRequst.getCardNumber());//银行卡号
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());//自定义备注2
				moneyMore.setRemark3("");
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr = moneyMore.getRechargeMoneymoremore() + moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString()+ moneyMore.getOrderNo() 
								 + moneyMore.getAmount() + moneyMore.getRechargeType()+ moneyMore.getFeeType() + moneyMore.getCardNo() + moneyMore.getRandomTimeStamp() 
								 + moneyMore.getRemark1()  + moneyMore.getRemark2()  + moneyMore.getRemark3() + moneyMore.getReturnURL()  + moneyMore.getNotifyURL();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付充值接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户充值申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 提现接口
	 * @param commonRequst
	 * @return
	 */
	public static Object[] withdrawsMoney(CommonRequst commonRequst){
		logger.info("调用双乾支付提现接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		Map moneyMoreConfigMap=moneyMoreProperty();  
		//得到公钥
		String publickey =moneyMoreConfigMap.get("MM_PublicKey").toString();
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				SignUtil rsa = SignUtil.getInstance();
				moneyMore.setCity(commonRequst.getCity());//开户行城市
				moneyMore.setProvince(commonRequst.getProvince());//开户行省份
				moneyMore.setBranchBankName(commonRequst.getBankBranchName());//开户行支行名称
				moneyMore.setBankCode(commonRequst.getBankCode());//银行代码
				moneyMore.setOrderNo(commonRequst.getRequsetNo());//交易流水号
				moneyMore.setAmount(commonRequst.getAmount().doubleValue());//交易金额
				moneyMore.setCardNo(rsa.encryptData(commonRequst.getCardNumber(), publickey).replaceAll("\r", "").replaceAll("\n", ""));//银行卡号
				moneyMore.setCardType(Integer.valueOf(commonRequst.getCardType()));//银行卡类型
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());//自定义备注2
				moneyMore.setRemark3("");
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr = moneyMore.getWithdrawMoneymoremore() + moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString() 
								+ moneyMore.getOrderNo() + moneyMore.getAmount() + moneyMore.getFeeQuota() + moneyMore.getCardNo()  
								+ moneyMore.getCardType() + moneyMore.getBankCode() + moneyMore.getBranchBankName() 
								+ moneyMore.getProvince() + moneyMore.getCity() + moneyMore.getRandomTimeStamp() 
							    + moneyMore.getRemark1()  + moneyMore.getRemark2()  + moneyMore.getRemark3() + moneyMore.getReturnURL()  + moneyMore.getNotifyURL();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付提现接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户提现申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 授权接口
	 * @param commonRequst
	 * @return
	 */
	public static Object[] authorize(CommonRequst commonRequst){
		logger.info("调用双乾支付授权接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				moneyMore.setMoneymoremoreId(commonRequst.getThirdPayConfigId());//用户钱多多标识
				moneyMore.setAuthorizeTypeOpen(commonRequst.getAutoAuthorizationType());//开启授权类型
				moneyMore.setAuthorizeTypeClose(commonRequst.getAuthorizeTypeClose());//关闭授权类型
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());//自定义备注2
				moneyMore.setRemark3("");
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr = moneyMore.getMoneymoremoreId() + moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString() 
								+ moneyMore.getAuthorizeTypeOpen() +  moneyMore.getAuthorizeTypeClose() +  moneyMore.getRandomTimeStamp() 
								+ moneyMore.getRemark1() + moneyMore.getRemark2() + moneyMore.getRemark3() + moneyMore.getReturnURL() +  moneyMore.getNotifyURL();		
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付授权接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户授权申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 审核接口----->>（放款），（流标）
	 * @param commonRequst
	 * @return
	 */
	public static Object[] transferaudit(CommonRequst commonRequst){
		logger.info("调用双乾支付审核接口开始时间："+new Date());
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=null;
			if(commonRequst.getAuditType().equals(MoneyMore.TACTION_2)){//退回，流标
				 moneyMore=generatePublicData("","",true);
			}else{//通过，放款
				 moneyMore=generatePublicData("","",true);
			}
			if(moneyMore!=null){
				moneyMore.setLoanNoList(commonRequst.getLoanNoList());//乾多多流水号列表
				moneyMore.setAuditType(commonRequst.getAuditType());//审核类型
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());//自定义备注2
				moneyMore.setRemark3("");
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr = moneyMore.getLoanNoList() + moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString()  + moneyMore.getAuditType() 
								+ moneyMore.getRandomTimeStamp() + moneyMore.getRemark1() + moneyMore.getRemark2() + moneyMore.getRemark3() 
								+ moneyMore.getReturnURL() + moneyMore.getNotifyURL();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付审核接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户审核申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 姓名匹配接口
	 * @param commonRequst
	 * @return
	 */
	public static Object[] identityMatching(CommonRequst commonRequst){
		logger.info("调用双乾支付姓名匹配接口开始时间："+new Date());
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore =generatePublicData("","",true);
			if(moneyMore!=null){
				moneyMore.setIdentityJsonList(commonRequst.getIdentityJsonList());
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr =  moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString() + moneyMore.getIdentityJsonList() + moneyMore.getRandomTimeStamp() + moneyMore.getNotifyURL();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付姓名匹配接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户姓名匹配申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 对账接口
	 * @param commonRequst
	 * @return
	 */
	public static Object[] reconciliation(CommonRequst commonRequst){
		logger.info("调用双乾对账接口接口开始时间："+new Date());
		CommonResponse commonResponse=new  CommonResponse();
		Map moneyMoreConfigMap=moneyMoreProperty();       
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				moneyMore.setAction(commonRequst.getAction());//查询类型
				moneyMore.setLoanNo(commonRequst.getQueryRequsetNo());//乾多多流水号
				moneyMore.setOrderNo(commonRequst.getRequsetNo());//网贷平台订单号
				moneyMore.setBatchNo(commonRequst.getBidId());//网贷平台标号
				moneyMore.setBeginTime(commonRequst.getStartDay().toString());//开始时间
				moneyMore.setEndTime(commonRequst.getEndDay().toString());//结束时间
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_LOANORDERQUERY;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_LOANORDERQUERY;
				}
				//加密数据字符串
				String dataStr = moneyMore.getPlatformMoneymoremore()+moneyMore.getAction()+moneyMore.getLoanNo()+moneyMore.getOrderNo()+moneyMore.getBatchNo()
				                 +moneyMore.getBeginTime()+moneyMore.getEndTime();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String param=UrlUtils.generateParams(map,MoneyMore.CHARSETUTF8);
				String rett=ThirdPayWebClient.getWebContentByPost(moneyMorePayurl, param,MoneyMore.CHARSETUTF8,12000);
				if(commonRequst.getAction().equals("")){//转账对账
					QueryTransferBean transferInquiryBean=new QueryTransferBean();
					Gson gson=new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
					transferInquiryBean=gson.fromJson(rett, QueryTransferBean.class);
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户对账申请提交成功";
					ret[2]=commonResponse;
				}else if(commonRequst.getAction().equals(1)){//充值对账
					QueryRechargeBean queryRechargeBean=new QueryRechargeBean();
					Gson gson=new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
					queryRechargeBean=gson.fromJson(rett, QueryRechargeBean.class);
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户对账申请提交成功";
					ret[2]=commonResponse;
				}else if(commonRequst.getAction().equals(2)){//提现对账
					QueryExtractionBean queryExtractionBean=new QueryExtractionBean();
					Gson gson=new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
					queryExtractionBean=gson.fromJson(rett, QueryExtractionBean.class);
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="用户对账申请提交成功";
					ret[2]=commonResponse;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]="用户对账申请提交失败";
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="对账接口基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="对账接口接对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 认证、提现银行卡绑定、代扣授权三合一接口
	 * @param commonRequst
	 * @return
	 */
	public static Object[] toloanfastpay(CommonRequst commonRequst){
		logger.info("调用双乾支付三合一接口开始时间："+new Date());
		Map moneyMoreConfigMap=moneyMoreProperty();  
		//得到公钥
		String publickey =moneyMoreConfigMap.get("MM_PublicKey").toString();
		Object[]  ret=new Object[3];
		try {
			//公共商户号，第一个参数是页面返回网址，第二个参数是后台通知地址，第三个参数表示是否启用防抵赖 
			MoneyMore moneyMore=generatePublicData("","",true);
			if(moneyMore!=null){
				SignUtil rsa = SignUtil.getInstance();
				moneyMore.setMoneymoremoreId(commonRequst.getThirdPayConfigId());//用户钱多多标识
				moneyMore.setAction(commonRequst.getAction());//操作类型
				moneyMore.setCardNo(rsa.encryptData(commonRequst.getCardNumber(), publickey).replaceAll("\r", "").replaceAll("\n", ""));//银行卡号
				moneyMore.setWithholdBeginDate(commonRequst.getWithholdBeginDate());//代扣开始日期
				moneyMore.setWithholdEndDate(commonRequst.getWithholdEndDate());//代扣结束日期
				moneyMore.setSingleWithholdLimit(commonRequst.getSingleWithholdLimit());//单笔代扣限额
				moneyMore.setTotalWithholdLimit(commonRequst.getTotalWithholdLimit());//代扣总限额
				moneyMore.setRemark1(commonRequst.getRemark1());//自定义备注1
				moneyMore.setRemark2(commonRequst.getRemark2());//自定义备注2
				moneyMore.setRemark3("");
				//双乾支付调用地址
				String moneyMorePayurl=moneyMorePayUrl();
				if(moneyMorePayurl.equals("normal")){
					moneyMorePayurl=MoneyMore.MONEYMORE_NORMAL_TOREG;
				}else{
					moneyMorePayurl=moneyMorePayurl+MoneyMore.MONEYMORE_TEST_TOREG;
				}
				//加密数据字符串
				String dataStr = moneyMore.getMoneymoremoreId() +  moneyMoreConfigMap.get("MM_PlatformMoneymoremore").toString()  + moneyMore.getAction()
								+ moneyMore.getCardNo() + moneyMore.getWithholdBeginDate() + moneyMore.getWithholdEndDate() 
								+ moneyMore.getSingleWithholdLimit() + moneyMore.getTotalWithholdLimit() + moneyMore.getRandomTimeStamp()
								+ moneyMore.getRemark1() + moneyMore.getRemark2() + moneyMore.getRemark3() 
								+ moneyMore.getReturnURL() + moneyMore.getNotifyURL();
				//实体对象整合成map对象，并生成签名
				Map map =getParameterMap(moneyMore,dataStr,moneyMoreConfigMap);
				String [] rett=ThirdPayWebClient.operateParameter(moneyMorePayurl, map,MoneyMore.CHARSETUTF8);
				logger.info("调用双乾支付三合一接口参数："+rett[1]);
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					ret[0]=ThirdPayConstants.RECOD_SUCCESS;
					ret[1]="三合一接口申请提交";
					ret[2]=commonRequst;
				}else{
					ret[0]=ThirdPayConstants.RECOD_FAILD;
					ret[1]=rett[1];
					ret[2]=commonRequst;
				}
			
			}else{
				ret[0]=ThirdPayConstants.RECOD_FAILD;
				ret[1]="基本参数获取失败";
				ret[2]=commonRequst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret[0]=ThirdPayConstants.RECOD_FAILD;
			ret[1]="三合一接口对接失败"+e.getMessage();
			ret[2]=commonRequst;
		}
		return ret;
	}
	
	/**
	 * 生成转账列表
	 * @param moneyMoreMore
	 * @return
	 */
	public static MoneyMore loanJsonList(CommonRequst commonRequst,MoneyMore moneyMore){		
		List<MadaiLoanInfoBean> listmlib = new ArrayList<MadaiLoanInfoBean>();
		MadaiLoanInfoBean mlib = new MadaiLoanInfoBean();
		mlib.setLoanOutMoneymoremore(commonRequst.getThirdPayConfigId());//投资人第三方标识
		mlib.setLoanInMoneymoremore(commonRequst.getLoaner_thirdPayflagId());//借款人第三方标识
		mlib.setOrderNo(commonRequst.getRequsetNo());//交易流水号
		mlib.setBatchNo(commonRequst.getBidId());//标的编号
		mlib.setAmount(commonRequst.getAmount().toString());//交易金额
		mlib.setFullAmount(commonRequst.getPlanMoney().toString());//满标标额
		mlib.setTransferName(commonRequst.getTransferName());//用途
		mlib.setRemark(commonRequst.getRemark1());//定义备注
		listmlib.add(mlib);
		//设置 转账列表
		moneyMore.setLoanJsonList(Common.JSONEncode(listmlib));
		return moneyMore;
		
	}
	
	/**
	 * 返回注册绑定
	 * @param resultBean
	 * @return
	 */
	private static ResultBean setBindCodeMsg(ResultBean resultBean){
		System.out.println("注册返回==="+resultBean.getResultCode());
		if(resultBean.getResultCode().equals("88")){
			resultBean.setCodeMsg("成功！");
		}else if(resultBean.getResultCode().equals("01")){
			resultBean.setCodeMsg("注册类型错误");
		}else if(resultBean.getResultCode().equals("02")){
			resultBean.setCodeMsg("手机号错误");
		}else if(resultBean.getResultCode().equals("03")){
			resultBean.setCodeMsg("邮箱错误");
		}else if(resultBean.getResultCode().equals("04")){
			resultBean.setCodeMsg("真实姓名错误");
		}else if(resultBean.getResultCode().equals("05")){
			resultBean.setCodeMsg("身份证号错误");
		}else if(resultBean.getResultCode().equals("06")){
			resultBean.setCodeMsg("身份证图片错误");
		}else if(resultBean.getResultCode().equals("07")){
			resultBean.setCodeMsg("平台乾多多标识错误");
		}else if(resultBean.getResultCode().equals("08")){
			resultBean.setCodeMsg("加密验证失败");
		}else if(resultBean.getResultCode().equals("09")){
			resultBean.setCodeMsg("手机和邮箱已存在");
		}else if(resultBean.getResultCode().equals("10")){
			resultBean.setCodeMsg("邮箱已存在");
		}else if(resultBean.getResultCode().equals("11")){
			resultBean.setCodeMsg("手机已存在");
		}else if(resultBean.getResultCode().equals("12")){
			resultBean.setCodeMsg("支付密码错误");
		}else if(resultBean.getResultCode().equals("13")){
			resultBean.setCodeMsg("安保问题错误");
		}else if(resultBean.getResultCode().equals("14")){
			resultBean.setCodeMsg("用户网贷平台账号错误");
		}else if(resultBean.getResultCode().equals("15")){
			resultBean.setCodeMsg("用户网贷平台账号错误");
		}else if(resultBean.getResultCode().equals("16")){
			resultBean.setCodeMsg("网贷平台账号已绑定");
		}
		return resultBean;
	}
}
