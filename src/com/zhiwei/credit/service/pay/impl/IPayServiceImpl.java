package com.zhiwei.credit.service.pay.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hurong.core.util.DateUtil;
import com.zhiwei.credit.model.pay.MadaiLoanInfoBean;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.core.creditUtils.MD5;
import com.zhiwei.credit.model.pay.MoneyMoreMore;
import com.zhiwei.credit.model.pay.ResultBean;
import com.zhiwei.credit.service.pay.IPayService;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;
import com.zhiwei.credit.util.RsaHelper;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;



public class IPayServiceImpl implements IPayService {
	public static final String charSet=Constants.CHAR_UTF;
	@Override
	public String transfer(MoneyMoreMore moneyMoreMore,String basePath) {
		
		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/loan.action");
		moneyMoreMore.setReturnURL (basePath + "pay/transferReturnFont.do");
		moneyMoreMore.setNotifyURL (basePath + "pay/transferReturnBack.do");
		moneyMoreMore.setRandomTimeStamp(Common.getRandomNum(2)+DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS")); //启用防抵赖 
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
		//加密数据字符串
		String dataStr = moneyMoreMore.getLoanJsonList() + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() + moneyMoreMore.getTransferAction() + moneyMoreMore.getAction() + moneyMoreMore.getTransferType()/*+moneyMoreMore.getNeedAudit()*/ + moneyMoreMore.getRandomTimeStamp() + moneyMoreMore.getRemark1() + moneyMoreMore.getRemark2() + moneyMoreMore.getRemark3() +moneyMoreMore.getReturnURL() + moneyMoreMore.getNotifyURL();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		MD5 md5=new MD5();
		moneyMoreMore.setSignInfo (rsa.signData(md5.getMD5ofStr(dataStr), privatekey));
	
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("LoanJsonList", moneyMoreMore.getLoanJsonList());
		params.put("PlatformMoneymoremore",AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		params.put("TransferAction", moneyMoreMore.getTransferAction());
		params.put("Action", moneyMoreMore.getAction());
		params.put("TransferType", moneyMoreMore.getTransferType());
		//params.put("NeedAudit", moneyMoreMore.getNeedAudit());
		params.put("RandomTimeStamp", moneyMoreMore.getRandomTimeStamp());
		params.put("Remark1", moneyMoreMore.getRemark1());
		params.put("Remark2", moneyMoreMore.getRemark2());
		params.put("Remark3", moneyMoreMore.getRemark3());
		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		String retdata="";
		try {
			String param=UrlUtils.generateParams(params,"");
			retdata=  WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,36000000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retdata;
	}
	@Override
	public String[] balanceQuery(String moneymoremoreId,String type) {
		String[] money=new String[3];
		MoneyMoreMore moneyMoreMore=new MoneyMoreMore();
		moneyMoreMore.setPlatformId(moneymoremoreId);
		moneyMoreMore.setPlatformType(type);
		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/balancequery.action");
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
		String dataStr = moneyMoreMore.getPlatformId() + moneyMoreMore.getPlatformType() + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		MD5 md5=new MD5();
		moneyMoreMore.setSignInfo (rsa.signData(md5.getMD5ofStr(dataStr), privatekey));
	
		Map<String, String> params=new HashMap<String, String>();
		params.put("PlatformMoneymoremore", AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		params.put("PlatformId", moneyMoreMore.getPlatformId());
		params.put("PlatformType", moneyMoreMore.getPlatformType());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		
		String retdata = null;
		try {
			String param=UrlUtils.generateParams(params,charSet);
			retdata=  WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000);//返回数据
			System.out.println("============"+retdata);
			if(retdata!=null&&!retdata.equals("")){
			money=retdata.replace("\r", "").replace("\n", "").split("\\|");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return money;
	}
	
	@Override
	public String balanceQueryMoneys(String moneymoremoreId,String type) {
		String[] money=new String[3];
		MoneyMoreMore moneyMoreMore=new MoneyMoreMore();
		moneyMoreMore.setPlatformId(moneymoremoreId);
		moneyMoreMore.setPlatformType(type);
		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/balancequery.action");
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
		String dataStr = moneyMoreMore.getPlatformId() + moneyMoreMore.getPlatformType() + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		MD5 md5=new MD5();
		moneyMoreMore.setSignInfo (rsa.signData(md5.getMD5ofStr(dataStr), privatekey));
	
		Map<String, String> params=new HashMap<String, String>();
		params.put("PlatformMoneymoremore", AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		params.put("PlatformId", moneyMoreMore.getPlatformId());
		params.put("PlatformType", moneyMoreMore.getPlatformType());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
	
		String retdata = null;
		try {
			String param=UrlUtils.generateParams(params,charSet);
			retdata=  WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000);//返回数据
			if(retdata!=null&&!retdata.equals("")){
			money=retdata.replace("\r", "").replace("\n", "").split("\\|");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retdata;
	}
	
	
	/**
	 * 绑定
	 * @param moneyMoreMore
	 * @param basePath
	 * @return
	 */
	@Override
	public String bind(MoneyMoreMore moneyMoreMore, String basePath) {
		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/toloanbind.action");
		moneyMoreMore.setReturnURL (basePath + "pay/bandMorFont.do");
		moneyMoreMore.setNotifyURL (basePath + "pay/bandMorBack.do");
		
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
		
		String dataStr = moneyMoreMore.getLoanPlatformAccount() + moneyMoreMore.getLoanMoneymoremore() + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() + moneyMoreMore.getReturnURL() + moneyMoreMore.getNotifyURL();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));
		Map<String, String> params=new HashMap<String, String>();
		params.put("LoanPlatformAccount", moneyMoreMore.getLoanPlatformAccount());
		params.put("PlatformMoneymoremore", AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		params.put("LoanMoneymoremore", moneyMoreMore.getLoanMoneymoremore());
		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		
		String retStr="";
		
		try {
			String param=UrlUtils.generateParams(params,charSet);
			retStr=WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retStr;
	}
	
	/**
	 * 提现接口
	 * @param moneyMoreMore
	 * @param basePath
	 * @return
	 */
	@Override
	public String withdraws(MoneyMoreMore moneyMoreMore, String basePath) {
		String retdata ="";
		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/toloanwithdraws.action");
		moneyMoreMore.setReturnURL (basePath + "pay/withdrawFont.do");
		moneyMoreMore.setNotifyURL (basePath + "pay/backPay.do");
		
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
		String publickey = AppUtil.getSysConfig().get("MM_PublicKey").toString();
		
		String dataStr = moneyMoreMore.getWithdrawMoneymoremore() + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() + moneyMoreMore.getOrderNo() + moneyMoreMore.getAmount() + moneyMoreMore.getFeePercent() + moneyMoreMore.getCardNo() + moneyMoreMore.getCardType() + moneyMoreMore.getBankCode() + moneyMoreMore.getBranchBankName() + moneyMoreMore.getProvince() + moneyMoreMore.getCity() + moneyMoreMore.getReturnURL() + moneyMoreMore.getNotifyURL();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));
		moneyMoreMore.setCardNo (rsa.encryptData(moneyMoreMore.getCardNo(), publickey));
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("OrderNo", moneyMoreMore.getOrderNo());
		params.put("Amount", moneyMoreMore.getAmount());
		
		params.put("FeePercent", moneyMoreMore.getFeePercent());
		params.put("CardNo", moneyMoreMore.getCardNo());
		
		params.put("CardType", moneyMoreMore.getCardType());
		params.put("BankCode", moneyMoreMore.getBankCode());
		
		params.put("BranchBankName", moneyMoreMore.getBranchBankName());
		params.put("Province", moneyMoreMore.getProvince());
		
		params.put("City", moneyMoreMore.getCity());
		params.put("PlatformMoneymoremore", AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		params.put("WithdrawMoneymoremore", moneyMoreMore.getWithdrawMoneymoremore());
		
		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		
		try {
			String param=UrlUtils.generateParams(params,charSet);
			System.out.println(WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000));
			retdata = WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retdata;
	}
	
	
	/**
	 * 充值接口
	 * @param moneyMoreMore
	 * @param basePath
	 * @return
	 */
	@Override
	public String recharge(MoneyMoreMore moneyMoreMore, String basePath) {
		String retdata ="";
		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/toloanrecharge.action");
		moneyMoreMore.setReturnURL (basePath + "pay/rechargeFont.do");
		moneyMoreMore.setNotifyURL (basePath + "pay/backPay.do");
		
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
		
		String dataStr = moneyMoreMore.getRechargeMoneymoremore() +  AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() +  moneyMoreMore.getOrderNo() +  moneyMoreMore.getAmount() +  moneyMoreMore.getReturnURL() +  moneyMoreMore.getNotifyURL();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		moneyMoreMore.setSignInfo (rsa.signData(dataStr, privatekey));
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("RechargeMoneymoremore", moneyMoreMore.getRechargeMoneymoremore());
		params.put("PlatformMoneymoremore", AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());//平台乾多多标识固定值
		
		params.put("OrderNo", moneyMoreMore.getOrderNo());
		params.put("Amount", moneyMoreMore.getAmount());
		
		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		/*String url="";
		try{
		 url=UrlUtils.generateUrl(params,moneyMoreMore.getSubmitURL());
		}catch(Exception e){
		}*/
		try {
			String param=UrlUtils.generateParams(params,charSet);
			System.out.println(WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000));
			retdata=  WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retdata;
	}
	@Override
	public MoneyMoreMore loanJsonList(MoneyMoreMore moneyMoreMore) {
		List<MadaiLoanInfoBean> listmlib = new ArrayList<MadaiLoanInfoBean>();
		MadaiLoanInfoBean mlib = new MadaiLoanInfoBean();
		mlib.setLoanOutMoneymoremore(moneyMoreMore.getLoanOutMoneymoremore1());
		mlib.setLoanInMoneymoremore(moneyMoreMore.getLoanInMoneymoremore1());
		mlib.setOrderNo(moneyMoreMore.getOrderNo());
		mlib.setBatchNo(moneyMoreMore.getBatchNo1());
		mlib.setAmount(moneyMoreMore.getAmount1());
		mlib.setFullAmount(moneyMoreMore.getFullAmount1());
		mlib.setTransferName(moneyMoreMore.getTransferName1());
		mlib.setRemark(moneyMoreMore.getRemark1());
		//mlib.setSecondaryJsonList(secondaryJsonList(new BigDecimal(mlib.getAmount()),Double.valueOf(moneyMoreMore.getFeePercent())));
		listmlib.add(mlib);
		//设置 转账列表
		moneyMoreMore.setLoanJsonList(Common.JSONEncode(listmlib));
		return moneyMoreMore;
		
	}
	@Override
	public void registerAndBind(MoneyMoreMore moneyMoreMore, String basePath,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moneyReaease(MoneyMoreMore moneyMoreMore,String basePath,HttpServletResponse response) {

		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/toloanrelease.action");
		moneyMoreMore.setReturnURL (basePath + "pay/moneyReaeaseFont.do");
		moneyMoreMore.setNotifyURL (basePath + "pay/moneyReaeaseBack.do");
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
		//加密数据字符串
		String dataStr = moneyMoreMore.getMoneymoremoreId() + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() + moneyMoreMore.getOrderNo() + moneyMoreMore.getAmount() + moneyMoreMore.getRemark1() + moneyMoreMore.getRemark2() + moneyMoreMore.getRemark2() + moneyMoreMore.getReturnURL() + moneyMoreMore.getNotifyURL();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		moneyMoreMore.setSignInfo (rsa.signData(dataStr, privatekey));
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("MoneymoremoreId", moneyMoreMore.getMoneymoremoreId());
		params.put("PlatformMoneymoremore",AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		params.put("OrderNo", moneyMoreMore.getOrderNo());
		params.put("Amount", moneyMoreMore.getAmount());
		params.put("Remark1", moneyMoreMore.getRemark1());
		params.put("Remark2", moneyMoreMore.getRemark2());
		params.put("Remark3", moneyMoreMore.getRemark3());
		
		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		try {
			String url=UrlUtils.generateUrl(params,moneyMoreMore.getSubmitURL(),Constants.CHAR_UTF);
			WebClient.SendByUrl(response, url,Constants.CHAR_UTF);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	/**
	 * 审核接口
	 */
	
	@Override
	public ResultBean transferaudit(MoneyMoreMore moneyMoreMore, String basePath,HttpServletResponse response) {
		System.out.println("开始调用第三方支付接口");
		ResultBean resultBean=new ResultBean();
		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString() + "loan/toloantransferaudit.action");
		moneyMoreMore.setReturnURL (basePath + "pay/transferauditFont.do");//前台方法
		moneyMoreMore.setNotifyURL (basePath + "pay/transferauditBack.do");//后台方法
		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString(); 
		String dataStr = moneyMoreMore.getLoanNoList() + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() + moneyMoreMore.getAuditType() + moneyMoreMore.getRandomTimeStamp() + moneyMoreMore.getRemark1() + moneyMoreMore.getRemark2() + moneyMoreMore.getRemark3() + moneyMoreMore.getReturnURL() + moneyMoreMore.getNotifyURL();
		System.out.println("加密前："+dataStr);
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		MD5 md5=new MD5();
		moneyMoreMore.setSignInfo (rsa.signData(md5.getMD5ofStr(dataStr), privatekey));
		System.out.println("加密后："+moneyMoreMore.getSignInfo());
		Map<String, String> params=new HashMap<String, String>();
		params.put("LoanNoList", moneyMoreMore.getLoanNoList());
		params.put("PlatformMoneymoremore", AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		params.put("AuditType", moneyMoreMore.getAuditType());
		params.put("RandomTimeStamp", moneyMoreMore.getRandomTimeStamp());
		params.put("Remark1", moneyMoreMore.getRemark1());
		params.put("Remark2", moneyMoreMore.getRemark2());
		params.put("Remark3", moneyMoreMore.getRemark3());
		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		System.out.println("===========转账列表"+moneyMoreMore.getLoanNoList());
		String retStr="";
		try {
			String url=UrlUtils.generateUrl(params,moneyMoreMore.getSubmitURL(),Constants.CHAR_UTF);
			
			System.out.println(url);
			WebClient.SendByUrl(response, url,Constants.CHAR_UTF);
			/*
			String param=UrlUtils.generateParams(params);
			Gson gson=new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
			retStr=WebClient.getWebContentByPost(moneyMoreMore.getSubmitURL(),param,Constants.CHAR_UTF,360000);
			System.out.println("=================="+retStr);
			if(retStr!=null&&!retStr.equals("")){
				resultBean=setTransferauditCodeMsg(gson.fromJson(retStr, ResultBean.class));
			}
		*/} catch (IOException e) {
			e.printStackTrace();
		}
		return resultBean;
	}
	

	/*
	 *返回支付审核信息
	 * @param resultBean
	 * @return
	 */
	private ResultBean setTransferauditCodeMsg(ResultBean resultBean){
		System.out.println("注册返回==="+resultBean.getResultCode());
		if(resultBean.getResultCode().equals("88")){
			resultBean.setCodeMsg("成功！");
		}else if(resultBean.getResultCode().equals("01")){
			resultBean.setCodeMsg("乾多多流水号列表错误");
		}else if(resultBean.getResultCode().equals("02")){
			resultBean.setCodeMsg("审核类型错误");
		}else if(resultBean.getResultCode().equals("03")){
			resultBean.setCodeMsg("平台乾多多标识错误");
		}else if(resultBean.getResultCode().equals("04")){
			resultBean.setCodeMsg("未启用防抵赖");
		}else if(resultBean.getResultCode().equals("05")){
			resultBean.setCodeMsg("签名验证失败");
		}else if(resultBean.getResultCode().equals("06")){
			resultBean.setCodeMsg("");
		}else if(resultBean.getResultCode().equals("07")){
			resultBean.setCodeMsg("随机时间戳错误");
		}else if(resultBean.getResultCode().equals("08")){
			resultBean.setCodeMsg("自定义备注错误");
		}else if(resultBean.getResultCode().equals("09")){
			resultBean.setCodeMsg("操作人不唯一");
		}else if(resultBean.getResultCode().equals("10")){
			resultBean.setCodeMsg("余额不足");
		}else if(resultBean.getResultCode().equals("11")){
			resultBean.setCodeMsg("");
		}else if(resultBean.getResultCode().equals("12")){
			resultBean.setCodeMsg("支付密码输入错误");
		}else if(resultBean.getResultCode().equals("13")){
			resultBean.setCodeMsg("短信验证码输入错误");
		}else if(resultBean.getResultCode().equals("14")){
			resultBean.setCodeMsg("安保问题输入错误");
		}else if(resultBean.getResultCode().equals("15")){
			resultBean.setCodeMsg("密码、验证码或安保问题错误次数过多，锁定中");
		}
		return resultBean;
	}
	@Override
	public String secondaryJsonList(BigDecimal money, Double reePercent) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
