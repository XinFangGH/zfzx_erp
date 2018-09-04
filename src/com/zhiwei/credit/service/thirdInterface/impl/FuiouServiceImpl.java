package com.zhiwei.credit.service.thirdInterface.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.taskdefs.condition.Http;
import org.dom4j.Document;

import com.google.gson.Gson;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.action.pay.FontHuiFuAction;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.Fuiou;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;

import com.zhiwei.credit.service.thirdInterface.FuiouService;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;
import com.zhiwei.credit.util.FuiouUtil.SecurityUtils;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.XmlUtil;


public class FuiouServiceImpl implements FuiouService {
	@Resource
	private PlThirdInterfaceLogService plThirdInterfaceLogService;
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	/**
	 * 富有金账户注册方法
	 */
	@Override
	public String[] register(HttpServletResponse respose, BpCustMember mem,String regType, String basePath,HttpServletRequest request,WebBankcard webBankcard) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		Fuiou fuiou=new Fuiou();
		fuiou=generatePublicData(fuiou);
		fuiou=regDate(fuiou,mem,request,webBankcard,regType);
		Map<String, String> params =regParmasMap(fuiou);
		String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
		params.put("signature", sign);
		String outStr="";
		try {
			  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
			  System.out.println("param="+param);
			  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUGOLDREG, param, Fuiou.CHARSETUTF8,12000); 
			  System.out.println("outStr==="+outStr);	
			  Document doc=XmlUtil.stringToDocument(outStr);
				String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
				String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
				String  mchnt_txn_ssn=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
				String  signNature=doc.selectSingleNode("/ap/signature").getText();
			    Boolean issign=true;/*SecurityUtils.verifySign(mchnt_cd+"|"+mchnt_txn_ssn+"|"+resp_code, signNature);*/
			    if(issign){
			    	if(resp_code!=null&&resp_code.equals("0000")){
			    		 ret[0]=Constants.CODE_SUCCESS; 
				    	 ret[1]="用户注册成功"; 
			    	}else{
			    		ret[0]=Constants.CODE_FAILED; 
				    	ret[1]="用户注册失败：错误代码为"+resp_code; 
			    	}
			    }else{
			    	 ret[0]=Constants.CODE_FAILED;
			    	 ret[1]="签名验证不通过"; //账户余额 账户资金余额
			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	

	/**
	 * 生成注册map数据
	 * @param fuiou
	 * @return
	 */
	private Map<String, String> regParmasMap(Fuiou fuiou) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		if(fuiou.getMchnt_cd()!=null){
			params.put("mchnt_cd",fuiou.getMchnt_cd());
		}
		if(fuiou.getMchnt_txn_ssn()!=null){
			params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
		}
		if(fuiou.getCust_nm()!=null){
			params.put("cust_nm",fuiou.getCust_nm());
		}
		if(fuiou.getCertif_id()!=null){
			params.put("certif_id",fuiou.getCertif_id());
		}
		if(fuiou.getMobile_no()!=null){
			params.put("mobile_no",fuiou.getMobile_no());
		}
		if(fuiou.getEmail()!=null){
			params.put("email",fuiou.getEmail());
		}
		if(fuiou.getCity_id()!=null){
			params.put("city_id",fuiou.getCity_id());
		}
		if(fuiou.getParent_bank_id()!=null){
			params.put("parent_bank_id",fuiou.getParent_bank_id());
		}
		if(fuiou.getCapAcntNo()!=null){
			params.put("capAcntNo",fuiou.getCapAcntNo());
		}
		if(fuiou.getCapAcntNm()!=null){
			params.put("capAcntNm",fuiou.getCapAcntNm());
		}
		if(fuiou.getPassword()!=null){
			params.put("password",fuiou.getPassword());
		}
		if(fuiou.getLpassword()!=null){
			params.put("lpassword",fuiou.getLpassword());
		}
		if(fuiou.getRem()!=null){
			params.put("rem",fuiou.getRem());
		}
		if(fuiou.getBank_nm()!=null){
			params.put("bank_nm",fuiou.getBank_nm());
		}
		return params;
	}
	/**
	 * 注册参数准备方法
	 * @param fuiou
	 * @param bp
	 * @param request
	 * @param webBankcard 
	 */
	public Fuiou regDate(Fuiou fuiou,BpCustMember bp,HttpServletRequest request, WebBankcard webBankcard,String regType){
		fuiou.setMchnt_txn_ssn(regType+bp.getId().toString());//线上投资客户主键Id
		fuiou.setCust_nm(bp.getTruename());
		fuiou.setCertif_id(bp.getCardcode());
		fuiou.setMobile_no(bp.getTelphone());
		fuiou.setCity_id(webBankcard.getCityId().toString());//北京市
		fuiou.setParent_bank_id(webBankcard.getBankId());//工商银行
		fuiou.setBank_nm(webBankcard.getBankname());
		fuiou.setCapAcntNo(webBankcard.getCardNum());
		fuiou.setEmail(bp.getEmail());
		fuiou.setCapAcntNm("");
		fuiou.setPassword("");
		fuiou.setLpassword("");
		fuiou.setRem("");
		return fuiou;
	}
	

	/**
	 * 登陆富有系统方法
	 */
	@Override
	public String[] loginToFuiou(HttpServletResponse response,
			BpCustMember mem, String string, String basePath) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		Fuiou fuiou=new Fuiou();
		fuiou=generatePublicData(fuiou);
		fuiou=generateUserLoginData(fuiou,mem);
		Map<String, String> params =loginParmasMap(fuiou);
		String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
		params.put("signature", sign);
		try {
			  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
			  System.out.println("param="+param);
			  String url = UrlUtils.generateUrl(params, configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOULOGIN, Fuiou.CHARSETUTF8);
			  WebClient.SendByUrl(response, url,Fuiou.CHARSETUTF8);
			  ret[0]=Constants.CODE_SUCCESS; //可用余额
		      ret[1]="用户登陆页面跳转成功"; //账户余额 账户资金余额
			  plThirdInterfaceLogService.saveLog("", "", "",
						"富有金账户用户登陆页面跳转接口", null, PlThirdInterfaceLog.MEMTYPE1,
						PlThirdInterfaceLog.TYPE1, PlThirdInterfaceLog.TYPENAME1,
						"", "", "", "");
			  

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 登陆富有系统参数生成map参数
	 */
	private Map<String, String> loginParmasMap(Fuiou fuiou) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		if(fuiou.getMchnt_cd()!=null){
			params.put("mchnt_cd",fuiou.getMchnt_cd());
		}
		if(fuiou.getMchnt_txn_ssn()!=null){
			params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
		}
		if(fuiou.getCust_no()!=null){
			params.put("cust_no",fuiou.getCust_no());
		}
		if(fuiou.getLocation()!=null){
			params.put("location",fuiou.getLocation());
		}
		return params;
	}



	/**
	 * 用户登陆富有参数准备方法
	 * @param fuiou
	 * @param mem
	 * @return
	 */
	private Fuiou generateUserLoginData(Fuiou fuiou, BpCustMember mem) {
		// TODO Auto-generated method stub
		String orderNum = Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
		fuiou.setMchnt_txn_ssn(orderNum+mem.getId().toString());
		fuiou.setCust_no(mem.getThirdPayFlag0());
		fuiou.setLocation("");
		return fuiou;
	}
	
	/**
	 *富有查询用户信息查询
	 *svn:songwj
	 *BpCustMember:会员表
	 */
	@Override
	public BpCustMember getBpCustMemberMessage(BpCustMember bpCustMember) {
		Fuiou fuiou=new Fuiou();
		fuiou=generatePublicData(fuiou);
		fuiou=currentMemberMessage(fuiou,bpCustMember);
		Map<String, String> params =CurrentMessageParmasMap(fuiou);
		String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
		params.put("signature", sign);
		String outStr="";
		
		try {
			  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
			  System.out.println("param="+param);
			  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUMEMBERMESSAGE, param, Fuiou.CHARSETUTF8,12000); 
			  System.out.println("outStr=="+outStr);
			  Document doc=XmlUtil.stringToDocument(outStr);
				String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();//响应代码
				String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();//商户代码
				String  mchnt_txn_ssn=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();//流水号
			    String  signNature=doc.selectSingleNode("/ap/signature").getText();
			    if(resp_code!=null&&resp_code.equals("0000")){
			    	String  mobile_no=doc.selectSingleNode("/ap/plain/results/result/mobile_no").getText();//手机号
					String  cust_nm=doc.selectSingleNode("/ap/plain/results/result/cust_nm").getText();//姓名
					String  certif_id=doc.selectSingleNode("/ap/plain/results/result/certif_id").getText();//身份证号码
					String  email=doc.selectSingleNode("/ap/plain/results/result/email").getText();//邮箱地址
					String  city_id=doc.selectSingleNode("/ap/plain/results/result/city_id").getText();//开户行地区代码
					String  parent_bank_id=doc.selectSingleNode("/ap/plain/results/result/parent_bank_id").getText();//开户行行别
					String  bank_nm=doc.selectSingleNode("/ap/plain/results/result/bank_nm").getText();//开户行支行名称
					String  capAcntNo=doc.selectSingleNode("/ap/plain/results/result/capAcntNo").getText();//账号
			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bpCustMember;
	}


	/**
	 * 富有账户查询账户数据准备
	 * svn:songwj
	 * @param fuiou
	 * @param bpCustMember
	 * @return
	 */
	private Fuiou currentMemberMessage(Fuiou fuiou, BpCustMember bpCustMember) {
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMdd");
		
		fuiou.setMchnt_txn_ssn(bpCustMember.getId().toString());
		fuiou.setMchnt_txn_dt(sfDate.format(new Date()));
		fuiou.setUser_ids("18612610238");//登录账号
		return fuiou;
	}

    /**
	 * 富有金账户信息查询map生成
	 * svn:songwj
	 * @param fuiou
	 * @return
	 */
		private Map<String, String> CurrentMessageParmasMap(Fuiou fuiou) {
			Map<String, String> params = new HashMap<String, String>();
			if(fuiou.getMchnt_cd()!=null){
				params.put("mchnt_cd",fuiou.getMchnt_cd());
			}
			if(fuiou.getMchnt_txn_ssn()!=null){
				params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
			}
			if(fuiou.getUser_ids()!=null){
				params.put("user_ids",fuiou.getUser_ids());
			}
			if(fuiou.getMchnt_txn_dt()!=null){
				params.put("mchnt_txn_dt",fuiou.getMchnt_txn_dt());
			}
			return params;
		}
		
		
		/**
		 * 富有用户信息修改
		 * svn:songwj
		 * BpCustMember:会员表
		 * cityId
		 * parentBankId
		 * bankNum
		 * capAcntNo
		 */
		public String[] updateMemberMessage(BpCustMember bpCustMember,String cityId ,String parentBankId,String bankNum,String capAcntNo){
			String[] ret=new String[2];
			Fuiou fuiou=new Fuiou();
			fuiou=generatePublicData(fuiou);//传入商户代码字段
			fuiou=regUpdateMemberDate(fuiou,bpCustMember, cityId , parentBankId, bankNum, capAcntNo);//把需要的参数值复制给fuion
			Map<String, String> params =regUpdateMemberParmasMap(fuiou);//设置参数
			String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);//创建签名
			System.out.println("sign="+sign);
			params.put("signature", sign);//添加签名参数
			String outStr = "";
			try {
				  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
				  System.out.println("param="+param);
				  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUUPDATEMEMBERMESSAGE, param, Fuiou.CHARSETUTF8,12000); 
				  System.out.println("outStr=="+outStr);
				  Document doc=XmlUtil.stringToDocument(outStr);
					String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
					
				    String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
					String  mchnt_txn_ssn=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
					String  signNature=doc.selectSingleNode("/ap/signature").getText();
//				    Boolean issign=SecurityUtils.verifySign(mchnt_cd+"|"+mchnt_txn_ssn+"|"+resp_code, signNature); 
//				    if(issign){
//				    	if(resp_code!=null&&resp_code.equals("0000")){
//				    		 ret[0]=Constants.CODE_SUCCESS; //可用余额
//					    	 ret[1]="账户信息更新成功"; //账户余额 账户资金余额
//				    	}else{
//				    		ret[0]=Constants.CODE_FAILED; //可用余额
//					    	ret[1]="用户注册失败：错误代码为"+resp_code; //账户余额 账户资金余额
//				    	}
//				    }else{
//				    	 ret[0]=Constants.CODE_FAILED;
//				    	 ret[1]="签名验证不通过"; //账户余额 账户资金余额
//				    }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret;
		}
		
		/**
		 * 修改用户信息参数准备方法
		 * @param fuiou
		 * @param bp
		 * @param request
		 * cityId：开户行地区代码
		 * parentBankId：开户行行别
		 * bankNum：开户行支行名称
		 */
		public Fuiou regUpdateMemberDate(Fuiou fuiou,BpCustMember  bp ,String cityId ,String parentBankId,String bankNum,String capAcntNo){
			fuiou.setMchnt_txn_ssn(bp.getId().toString());//流水号
//			fuiou.setCertif_id(bp.getCardcode());//身份证号
//			fuiou.setMobile_no(bp.getTelphone());//手机号
//			fuiou.setEmail(bp.getEmail());//邮箱
//			fuiou.setCity_id(cityId);//开户行地区代码
//			fuiou.setParent_bank_id(parentBankId);//开户行行别
//			fuiou.setBank_nm(bankNum);//bank_nm
//			fuiou.setCapAcntNo(capAcntNo);//账号
			
			fuiou.setCapAcntNo("81273460189734");//账号
			return fuiou;
		}
		
		/**
		 * 生成用户信息修改map数据
		 * @param fuiou
		 * @return
		 */
		private Map<String, String> regUpdateMemberParmasMap(Fuiou fuiou) {
			// TODO Auto-generated method stub
			Map<String, String> params = new HashMap<String, String>();
			if(fuiou.getCapAcntNo()!=null){
				params.put("capAcntNo",fuiou.getCapAcntNo());
			}
			if(fuiou.getMchnt_cd()!=null){
				params.put("mchnt_cd",fuiou.getMchnt_cd());
			}
			if(fuiou.getMchnt_txn_ssn()!=null){
				params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
			}
			return params;
		}
	
	/**
	 * 用户查询余额方法
	 */
	@Override
	public BpCustMember getCurrentMoney(BpCustMember bpCustMember) {
		// TODO Auto-generated method stub
		Fuiou fuiou=new Fuiou();
		fuiou=generatePublicData(fuiou);
		fuiou=currentMoneyDate(fuiou,bpCustMember);
		Map<String, String> params =CurrentMoneyParmasMap(fuiou);
		String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
		params.put("signature", sign);
		String outStr="";
		try {
			  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
			  System.out.println("param="+param);
			  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUCURRENTMONEY, param, Fuiou.CHARSETUTF8,12000); 
			  System.out.println("outStr=="+outStr);
			  Document doc=XmlUtil.stringToDocument(outStr);
				String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
				String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
				String  mchnt_txn_ssn=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
			 //   Boolean issign=SecurityUtils.verifySign(ca_balance+"|"+cf_balance+"|"+ct_balance+"|"+cu_balance+"|"+mchnt_cd+"|"+mchnt_txn_ssn+"|"+resp_code+"|"+user_id, signNature);
			    if(resp_code!=null&&resp_code.equals("0000")){
			    	String  user_id=doc.selectSingleNode("/ap/plain/results/result/user_id").getText();
					String  ct_balance=doc.selectSingleNode("/ap/plain/results/result/ct_balance").getText();
					String  ca_balance=doc.selectSingleNode("/ap/plain/results/result/ca_balance").getText();
					String  cf_balance=doc.selectSingleNode("/ap/plain/results/result/cf_balance").getText();
					String  cu_balance=doc.selectSingleNode("/ap/plain/results/result/cu_balance").getText();
					String  signNature=doc.selectSingleNode("/ap/signature").getText();
				    	if(ct_balance!=null&&!"".equals(ct_balance)){//账户总额
				    		bpCustMember.setTotalMoney((new BigDecimal(ct_balance)).divide(new BigDecimal(100)));
				    	}else{
				    		bpCustMember.setTotalMoney(new BigDecimal(0));
				    	}
				    	
				    	if(ca_balance!=null&&!"".equals(ca_balance)){//可用余额
				    		bpCustMember.setAvailableInvestMoney((new BigDecimal(ca_balance)).divide(new BigDecimal(100)));
				    	}else{
				    		bpCustMember.setAvailableInvestMoney(new BigDecimal(0));
				    	}
				    	if(cf_balance!=null&&!"".equals(cf_balance)){//冻结余额
				    		bpCustMember.setFreezeMoney((new BigDecimal(cf_balance)).divide(new BigDecimal(100)));
				    	}else{
				    		bpCustMember.setFreezeMoney(new BigDecimal(0));
				    	}
				    	if(cu_balance!=null&&!"".equals(cu_balance)){//未转结余额
				    		bpCustMember.setUnChargeMoney((new BigDecimal(ct_balance)).divide(new BigDecimal(100)));
				    	}else{
				    		bpCustMember.setUnChargeMoney(new BigDecimal(0));
				    	}
				    	
			    	}else{
			    		bpCustMember.setTotalMoney(new BigDecimal(0));
			    		bpCustMember.setAvailableInvestMoney(new BigDecimal(0));
			    		bpCustMember.setFreezeMoney(new BigDecimal(0));
			    		bpCustMember.setUnChargeMoney(new BigDecimal(0));
			    	}
			    /* if(issign){
			    	
			    }else{
			    	 ret[0]=Constants.CODE_FAILED;
			    	 ret[1]="签名验证不通过"; //账户余额 账户资金余额
			    }*/
			 /* plThirdInterfaceLogService.saveLog("", "", outStr,
						"汇付余额查询接口", null, PlThirdInterfaceLog.MEMTYPE1,
						PlThirdInterfaceLog.TYPE1, PlThirdInterfaceLog.TYPENAME1,
						"", "", "", "");*/
			  
			  /*Gson gson=new Gson();
			  FontHuiFuAction huifu=new FontHuiFuAction();
			  huifu=gson.fromJson(outStr, FontHuiFuAction.class);
			  String msgData =  huifu.getCmdId() + huifu.getRespCode() + huifu.getMerCustId() + huifu.getUsrCustId()+ huifu.getAvlBal()+ huifu.getAcctBal()+ huifu.getFrzBal();
		      boolean isSuccess = this.DecodSign(msgData, huifu.getChkValue());
		      if(isSuccess){
		    	  ret[0]=huifu.getAvlBal(); //可用余额
		    	  ret[1]=huifu.getAcctBal(); //账户余额 账户资金余额
		    	  ret[2]=huifu.getFrzBal(); //冻结金额
		      }*/
		} catch (Exception e) {
			e.printStackTrace();
			bpCustMember.setTotalMoney(new BigDecimal(0));
    		bpCustMember.setAvailableInvestMoney(new BigDecimal(0));
    		bpCustMember.setFreezeMoney(new BigDecimal(0));
    		bpCustMember.setUnChargeMoney(new BigDecimal(0));  
		
		}
		return bpCustMember;
	}
   /**
    * 富有金账户查询账户余额map生成
    * @param fuiou
    * @return
    */
	private Map<String, String> CurrentMoneyParmasMap(Fuiou fuiou){
		Map<String, String> params = new HashMap<String, String>();
		if(fuiou.getMchnt_cd()!=null){
			params.put("mchnt_cd",fuiou.getMchnt_cd());
		}
		if(fuiou.getMchnt_txn_ssn()!=null){
			params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
		}
		if(fuiou.getCust_no()!=null){
			params.put("cust_no",fuiou.getCust_no());
		}
		if(fuiou.getMchnt_txn_dt()!=null){
			params.put("mchnt_txn_dt",fuiou.getMchnt_txn_dt());
		}
		
		
		return params;
	}


	/**
	 * 富有金账户查询账户余额数据准备
	 * @param fuiou
	 * @param bpCustMember
	 * @return
	 */
	private Fuiou currentMoneyDate(Fuiou fuiou, BpCustMember bpCustMember) {
		// TODO Auto-generated method stub
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMdd");
		fuiou.setMchnt_txn_ssn(bpCustMember.getId().toString());
		fuiou.setCust_no(bpCustMember.getThirdPayFlag0());
		fuiou.setMchnt_txn_dt(sfDate.format(new Date()));
		return fuiou;
	}
    /**
     * 富有金账户交易明细查询
     * userIds：用户登陆富有系统的登陆名以”|”分隔（最多只能同时查10个用户）
	 * startDay:查询其实日期
	 * endDay：查询结束日期（起始和截止日期不能跨月）
     */
	@Override
	public void dealInfoQuery(String userIds, Date startDay, Date endDay) {

		// TODO Auto-generated method stub
		Fuiou fuiou=new Fuiou();
		fuiou=generatePublicData(fuiou);
		fuiou=dealInfoQueryDate(fuiou,userIds,startDay,endDay);
		Map<String, String> params =dealInfoQueryParmasMap(fuiou);
		String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
		params.put("signature", sign);
		String outStr="";
		try {
			  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
			  System.out.println("param="+param);
			  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUDEALINFOQUERY, param, Fuiou.CHARSETUTF8,12000); 
			  System.out.println("outStr=="+outStr);
			  Document doc=XmlUtil.stringToDocument(outStr);
				String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
				String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
				String  mchnt_txn_ssn=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
			   // Boolean issign=SecurityUtils.verifySign(ca_balance+"|"+cf_balance+"|"+ct_balance+"|"+cu_balance+"|"+mchnt_cd+"|"+mchnt_txn_ssn+"|"+resp_code+"|"+user_id, signNature);
			    if(resp_code!=null&&resp_code.equals("0000")){
			    	/*String  user_id=doc.selectSingleNode("/ap/plain/results/result/user_id").getText();
					String  ct_balance=doc.selectSingleNode("/ap/plain/results/result/ct_balance").getText();
					String  ca_balance=doc.selectSingleNode("/ap/plain/results/result/ca_balance").getText();
					String  cf_balance=doc.selectSingleNode("/ap/plain/results/result/cf_balance").getText();
					String  cu_balance=doc.selectSingleNode("/ap/plain/results/result/cu_balance").getText();*/
					String  signNature=doc.selectSingleNode("/ap/signature").getText();
			    }else{
			    	
			    }
			    /* if(issign){
			    	
			    }else{
			    	 ret[0]=Constants.CODE_FAILED;
			    	 ret[1]="签名验证不通过"; //账户余额 账户资金余额
			    }*/
			 /* plThirdInterfaceLogService.saveLog("", "", outStr,
						"汇付余额查询接口", null, PlThirdInterfaceLog.MEMTYPE1,
						PlThirdInterfaceLog.TYPE1, PlThirdInterfaceLog.TYPENAME1,
						"", "", "", "");*/
			  
			  /*Gson gson=new Gson();
			  FontHuiFuAction huifu=new FontHuiFuAction();
			  huifu=gson.fromJson(outStr, FontHuiFuAction.class);
			  String msgData =  huifu.getCmdId() + huifu.getRespCode() + huifu.getMerCustId() + huifu.getUsrCustId()+ huifu.getAvlBal()+ huifu.getAcctBal()+ huifu.getFrzBal();
		      boolean isSuccess = this.DecodSign(msgData, huifu.getChkValue());
		      if(isSuccess){
		    	  ret[0]=huifu.getAvlBal(); //可用余额
		    	  ret[1]=huifu.getAcctBal(); //账户余额 账户资金余额
		    	  ret[2]=huifu.getFrzBal(); //冻结金额
		      }*/
		} catch (Exception e) {
			e.printStackTrace();
			 
		
		}
		
	
		
	}
	
	/**
	 * 富有金账户交易明细查询map生成
	 * @param fuiou
	 * @return
	 */
	private Map<String, String> dealInfoQueryParmasMap(Fuiou fuiou) {
		Map<String, String> params = new HashMap<String, String>();
		if(fuiou.getMchnt_cd()!=null){
			params.put("mchnt_cd",fuiou.getMchnt_cd());
		}
		if(fuiou.getMchnt_txn_ssn()!=null){
			params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
		}
		if(fuiou.getUser_ids()!=null){
			params.put("user_ids",fuiou.getUser_ids());
		}
		if(fuiou.getStart_day()!=null){
			params.put("start_day",fuiou.getStart_day());
		}
		if(fuiou.getEnd_day()!=null){
			params.put("end_day",fuiou.getEnd_day());
		}
		return params;
	}



	/**
	 * 富有金账户交易明细参数储备
	 * @param fuiou
	 * @param userIds
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	private Fuiou dealInfoQueryDate(Fuiou fuiou, String userIds, Date startDay,
			Date endDay) {
		// TODO Auto-generated method stub
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMdd");
		fuiou.setMchnt_txn_ssn(userIds);
		fuiou.setUser_ids(userIds);
		fuiou.setStart_day(sfDate.format(startDay));
		fuiou.setEnd_day(sfDate.format(startDay));
		return fuiou;
	}


	

	/**2014-07-08
	 * 预授权 
	 */
	@Override
	public String[] preAuth(String mchnt_txn_ssn,String out_cust_no,String in_cust_no,String amt,String rem,HttpServletRequest request){
		Fuiou fuiou=new Fuiou();
		//得到公共的商务代码
		fuiou=generatePublicData(fuiou);
		//得到相应的属性
		fuiou=publicPreData(fuiou, mchnt_txn_ssn,out_cust_no,in_cust_no,amt,rem);
		//存储到map<key,value> 键值中
		Map<String, String> params =repAutoMap(fuiou);
		String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
		params.put("signature", sign);
		String outStr="";
		String[] ret=new String[4];
		try{
			  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
			  System.out.println("param="+param);
			  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUPREAUTO, param, Fuiou.CHARSETUTF8,12000); 
			  System.out.println("预授权状态：");
			  System.out.println(outStr);
			  Document doc=XmlUtil.stringToDocument(outStr);
			  //得到resp_code返回状态
			  String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
			  //得到mchnt_cd商务代码
			  String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
			  //得到mchnt_txn_ssn流水号
			  String  mchnt_txn_ssn_r=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
			 //得到signNature签名数据
			  String  signNature=doc.selectSingleNode("/ap/signature").getText();
			  if(resp_code.equals("0000")){
				  String contract_no=doc.selectSingleNode("/ap/contract_no").getText();
				  ret[0]=Constants.CODE_SUCCESS;
				  ret[1]=resp_code;
				  ret[2]=mchnt_txn_ssn_r;//流水号
				  ret[3]=contract_no;
			  }else{
				  ret[0]=Constants.CODE_FAILED;
				  ret[1]=resp_code;
				  ret[2]=mchnt_txn_ssn_r;//流水号
				  ret[3]=null;
			  }
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ret;
	}
	/**2014-07-08
	 * 预授权的属性存储在map<key,value>中
	 * @param fuiou
	 * @return
	 */
	private Map<String, String> repAutoMap(Fuiou fuiou) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		if(fuiou.getMchnt_cd()!=null){
			params.put("mchnt_cd",fuiou.getMchnt_cd());
		}
		if(fuiou.getMchnt_txn_ssn()!=null){
			params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
		}
		if(fuiou.getOut_cust_no()!=null){
			params.put("out_cust_no",fuiou.getOut_cust_no());
		}
		if(fuiou.getIn_cust_no()!=null){
			params.put("in_cust_no",fuiou.getIn_cust_no());
		}
		if(fuiou.getAmt()!=null){
			params.put("amt",fuiou.getAmt());
		}
		if(fuiou.getRem()!=null){
			params.put("rem",fuiou.getRem());
		}
		return params;
	}
	
	/**
	 * 
	 * @param fuiou  富有实体类
	 * @param mchnt_txn_ssn  我们系统的唯一标识
	 * @param out_cust_no 出账账户账号（可以是个人也可以是商户）
	 * @param in_cust_no 入账账户账号（可以是个人也可以是商户）
	 * @param amt 预授权金额
	 * @param rem 授权备注信息
	 * @return
	 */
    public Fuiou publicPreData(Fuiou fuiou,String mchnt_txn_ssn,String out_cust_no,String in_cust_no,String amt,String rem){
		fuiou.setMchnt_txn_ssn(mchnt_txn_ssn);//流水号
		fuiou.setOut_cust_no(out_cust_no);//收款
		fuiou.setIn_cust_no(in_cust_no);//付款
		fuiou.setAmt(amt);//金额
		fuiou.setRem(rem);
		return fuiou;
	}

	
	/**2014-07-09
	 * 预授权撤销
	 */
	@Override
	public String[] preAuthRev(String mchnt_txn_ssn,String out_cust_no,String in_cust_no,String contract_no,String rem,HttpServletRequest request){
		String[] ret=new String[2];
		Fuiou fuiou=new Fuiou();
		//得到公共的商务代码
		fuiou=generatePublicData(fuiou);
		//得到相应的属性
		fuiou=publicPreCancData(fuiou, mchnt_txn_ssn,out_cust_no,in_cust_no, contract_no,rem);
		//属性值存储在map<key,value>中
		Map<String, String> params =repAutoCancMap(fuiou);
		String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
		params.put("signature", sign);
		String outStr="";
		try{
			  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
			  System.out.println("param="+param);
			  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUPREAUTOCANC, param, Fuiou.CHARSETUTF8,12000); 
			  System.out.println("预授权撤销状态：");
			  System.out.println(outStr);
			  Document doc=XmlUtil.stringToDocument(outStr);
			  //得到resp_code返回状态
			  String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
			  //得到mchnt_cd商务代码
			  String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
			  //得到mchnt_txn_ssn流水号
			  String  mchnt_txn_ssn_r=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
			 //得到signNature签名数据
			  String  signNature=doc.selectSingleNode("/ap/signature").getText();
			  if(resp_code.equals("0000")){
				  ret[0]=Constants.CODE_SUCCESS;
				  ret[1]=mchnt_txn_ssn_r;
			  }else{
				  ret[0]=Constants.CODE_FAILED;
				  ret[1]=resp_code;
			  }
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ret;
	}
	
	/**2014-07-09
	 * 预授权撤销的属性存储在map<key,value>中
	 * @param fuiou
	 * @return
	 */
	private Map<String, String> repAutoCancMap(Fuiou fuiou) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		if(fuiou.getMchnt_cd()!=null){
			params.put("mchnt_cd",fuiou.getMchnt_cd());
		}
		if(fuiou.getMchnt_txn_ssn()!=null){
			params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
		}
		if(fuiou.getOut_cust_no()!=null){
			params.put("out_cust_no",fuiou.getOut_cust_no());
		}
		if(fuiou.getIn_cust_no()!=null){
			params.put("in_cust_no",fuiou.getIn_cust_no());
		}
		if(fuiou.getContract_no()!=null){
			params.put("contract_no",fuiou.getContract_no());
		}
		if(fuiou.getRem()!=null){
			params.put("rem",fuiou.getRem());
		}
		return params;
	}
	/**2014-07-09
	 * 预授权撤销属性赋值测试
	 * @param fuiou
	 * @param request
	 * @return
	 */
	public Fuiou publicPreCancData(Fuiou fuiou,String mchnt_txn_ssn,String out_cust_no,String in_cust_no,String contract_no,String rem){
		fuiou.setMchnt_txn_ssn(mchnt_txn_ssn);//流水号
		fuiou.setOut_cust_no(out_cust_no);//收款
		fuiou.setIn_cust_no(in_cust_no);//付款
		fuiou.setContract_no(contract_no);
		fuiou.setRem(rem);
		return fuiou;
	}
    
	

		
		
		/**
		 * 划拨（个人与个人之间）
		 * svn：songwj
		 * BpCustMember：
		 * outCustNo：付款登陆账户
		 * inCustNo：收款登陆账户
		 * amt：划拨金额
		 * contractNo：预授权合同号
		 * rem：备注
		 */
		public String[] transferPersonToPerson(String orderNum,String outCustNo,String inCustNo,String  amt,String contractNo,String rem){
			String[] ret=new String[2];
			Fuiou fuiou=new Fuiou();
			fuiou=generatePublicData(fuiou);//传入商户代码字段
			fuiou=transferPersonToPersonDate(fuiou,orderNum, outCustNo, inCustNo,  amt, contractNo, rem);//把需要的参数值复制给fuion
			Map<String, String> params =transferPersonToPersonParmasMap(fuiou);//设置参数
			String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);//创建签名
			System.out.println("sign="+sign);
			params.put("signature", sign);//添加签名参数
			String outStr = "";
			try {
				  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
				  System.out.println("param="+param);
				  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUTRANSFERPERSONTOPERSON, param, Fuiou.CHARSETUTF8,12000); 
				  System.out.println("outStr=="+outStr);
				  Document doc=XmlUtil.stringToDocument(outStr);
				  String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
				  String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
				  String  mchnt_txn_ssn=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
				  String  signNature=doc.selectSingleNode("/ap/signature").getText();
				  if(resp_code.equals("0000")){
					  ret[0]=Constants.CODE_SUCCESS;
					  ret[1]=mchnt_txn_ssn;
				  }else{
					  ret[0]=Constants.CODE_FAILED;
					  ret[1]=resp_code;
				  }
			} catch (Exception e) {
				e.printStackTrace();
				ret[0]=Constants.CODE_FAILED;
				ret[1]="系统出错了";
			}
			return ret;
		}
		
		/**
		 * 划拨（个人和个人）参数准备方法
		 * svn:songwj
		 * @param fuiou
		 * @param bp
		 * @param outCustNo:付款登陆账户
		 * @param inCustNo：收款登陆账户
		 * @param amt；划拨金额
		 * @param contractNo：预授权合同号
		 * @param rem：备注
		 * @return
		 */
		public  Fuiou  transferPersonToPersonDate(Fuiou fuiou,String orderNum,String outCustNo,String inCustNo,String  amt,String contractNo,String rem){
			fuiou.setMchnt_txn_ssn(orderNum);//流水号
			fuiou.setOut_cust_no(outCustNo);//收款
			fuiou.setIn_cust_no(inCustNo);//付款
			fuiou.setAmt(amt);//转帐金额
			fuiou.setContract_no(contractNo);//授权合同号
			fuiou.setRem(rem);//备注
			return fuiou;
		}
		
		/**
		 * 划拨（个人和个人）map数据
		 * @param fuiou
		 * @return
		 */
		private Map<String, String> transferPersonToPersonParmasMap(Fuiou fuiou) {
			// TODO Auto-generated method stub
			Map<String, String> params = new HashMap<String, String>();
		
			if(fuiou.getMchnt_cd()!=null){
				params.put("mchnt_cd",fuiou.getMchnt_cd());
			}
			if(fuiou.getMchnt_txn_ssn()!=null){
				params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
			}
			if(fuiou.getOut_cust_no()!=null){
				params.put("out_cust_no",fuiou.getOut_cust_no());
			}
			if(fuiou.getIn_cust_no()!=null){
				params.put("in_cust_no",fuiou.getIn_cust_no());
			}
			if(fuiou.getAmt()!=null){
				params.put("amt",fuiou.getAmt());
			}
			if(fuiou.getContract_no()!=null){
				params.put("contract_no",fuiou.getContract_no());
			}
			if(fuiou.getRem()!=null){
				params.put("rem",fuiou.getRem());
			}
			return params;
		}
		


		/**2014-07-09
		 * 转账(商户和个人之间)
		 */
		@Override
		public String[] traAcc(String orderNum,String out_cust_no,String in_cust_no,String amt,String contract_no,String rem){
			String[] ret=new String[2];
			Fuiou fuiou=new Fuiou();
			//得到公共的商务代码
			fuiou=generatePublicData(fuiou);
			//得到相应的属性
			fuiou=publicTraAccData(fuiou, orderNum, out_cust_no, in_cust_no, amt, contract_no, rem);
			//属性值存储在map<key,value>
			Map<String, String> params =traAccMap(fuiou);
			String sign=autoCreatSignnature(params,Fuiou.CHARSETUTF8);
			params.put("signature", sign);
			String outStr="";
			try{
				  String param=UrlUtils.generateParams(params,Fuiou.CHARSETUTF8);
				  System.out.println("param="+param);
				  outStr =WebClient.getWebContentByPost(configMap.get("thirdPay_fuiou_URL").toString()+Fuiou.FUIOUTRAACC, param, Fuiou.CHARSETUTF8,12000); 
				  System.out.println("转账状态：");
				  System.out.println(outStr);
				  //解析xml
				  Document doc=XmlUtil.stringToDocument(outStr);
				  //得到resp_code返回状态
				  String  resp_code = doc.selectSingleNode("/ap/plain/resp_code").getText();
				  //得到mchnt_cd商务代码
				  String  mchnt_cd=doc.selectSingleNode("/ap/plain/mchnt_cd").getText();
				  //得到mchnt_txn_ssn流水号
				  String  mchnt_txn_ssn=doc.selectSingleNode("/ap/plain/mchnt_txn_ssn").getText();
				 //得到signNature签名数据
				  String  signNature=doc.selectSingleNode("/ap/signature").getText();
				  if(resp_code.equals("0000")){
					  ret[0]=Constants.CODE_SUCCESS;
					  ret[1]="转账成功";
				  }else{
					  ret[0]=Constants.CODE_FAILED;
					  ret[1]="转账失败：失败编码--"+resp_code;
				  }
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return ret;
		}
		/**2014-07-09
		 * 转账属性存储在map<key,value>中
		 * @param fuiou
		 * @return
		 */
		private Map<String, String> traAccMap(Fuiou fuiou) {
			// TODO Auto-generated method stub
			Map<String, String> params = new HashMap<String, String>();
			if(fuiou.getMchnt_cd()!=null){
				params.put("mchnt_cd",fuiou.getMchnt_cd());
			}
			if(fuiou.getMchnt_txn_ssn()!=null){
				params.put("mchnt_txn_ssn",fuiou.getMchnt_txn_ssn());
			}
			if(fuiou.getOut_cust_no()!=null){
				params.put("out_cust_no",fuiou.getOut_cust_no());
			}
			if(fuiou.getIn_cust_no()!=null){
				params.put("in_cust_no",fuiou.getIn_cust_no());
			}
			if(fuiou.getAmt()!=null){
				params.put("amt",fuiou.getAmt());
			}
			if(fuiou.getContract_no()!=null){
				params.put("contract_no",fuiou.getContract_no());
			}
			if(fuiou.getRem()!=null){
				params.put("rem",fuiou.getRem());
			}
			return params;
		}
		/**2014-07-09
		 * 转账属性赋值测试
		 * @param fuiou
		 * @param request
		 * @return
		 */
		public Fuiou publicTraAccData(Fuiou fuiou,String orderNum,String out_cust_no,String in_cust_no,String amt,String contract_no,String rem){
			fuiou.setMchnt_txn_ssn(orderNum);//流水号
			fuiou.setOut_cust_no(out_cust_no);//收款
			fuiou.setIn_cust_no(in_cust_no);//付款
			fuiou.setAmt(amt);//转帐金额
			fuiou.setContract_no(contract_no);//授权合同号
			fuiou.setRem(rem);//备注
			return fuiou;
		}
	
	

	/**
	 * 富有公共数据获取方法
	 * @return
	 */
	public Fuiou generatePublicData(Fuiou fuiou){
		fuiou.setMchnt_cd(configMap.get("thirdPay_fuiou_platNumber").toString());
		return fuiou;
	}
	/**
	 * 生成签名参数
	 * @param params
	 * @param charsetutf8
	 * @return
	 */
	private String autoCreatSignnature(Map<String, String> params,String charsetutf8) {
		StringBuilder sign=CreateLinkString(params);
		System.out.println("sign=="+sign.toString());
		String signNature=SecurityUtils.sign(sign.toString());
		System.out.println("signNature=="+signNature);
		return signNature;
	}
	
	/** 
	 * 功能：把数组所有元素排序，并按照“参数值”的模式用“!”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public  StringBuilder CreateLinkString(Map params){
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
		StringBuilder prestr = new StringBuilder();
		String key="";
		String value="";
		StringBuilder prestr1 = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			key=(String) keys.get(i);
			value = (String) params.get(key);
			System.out.println(key+"=="+value);
			if(key.equalsIgnoreCase("signature")){
				continue;
			}
			prestr1.append(key).append("|");
			prestr.append(value).append("|");
		}
		StringBuilder keyvalue=prestr1.deleteCharAt(prestr1.length()-1);
		System.out.println("keyprestr1==="+keyvalue.toString());
		StringBuilder valuekey=prestr.deleteCharAt(prestr.length()-1);
		System.out.println("keyprestr==="+valuekey.toString());
		return valuekey;
	}

}