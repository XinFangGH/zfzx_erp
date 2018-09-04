package com.thirdInterface.pay.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.p2p.BpCustMember;

/**
 * 第三方支付接口引擎
 * 所有第三方支付接口都实现该接口，Action中都统一调用该接口
 * @author Yuan.zc
 *
 */
public interface ThirdPayEngine {
	/*
	 * 跳出页面
	 */
	public static String  PostType_0="0";
	/*
	 * 返回 json 或者 xml 或者 string
	 */
	public static String  PostType_1="1";
	/**
	 * 获取项目名称
	 */
	public static String  PROSTR=AppUtil.getProStr();
	/**
	 * 还款接口 
	 * @param fund; 借款人款项台帐
	 *@param bpFundIntentList 投资人借款台帐
	 *@param outMem 付款人
	 *@param fullMoney 满标额
	 * @param basePath  url 地址
	 * @param postType 提交方式  0  跳出页面  1  返回 json 或者 xml 或者 string
	 * @param rep 
	 * @param req
	 * @param remk0
	 * * @param remk1
	 * * @param remk2
	 * @return retArr[] 
	 */
 public String[] repayment(SlFundIntent fund,List<BpFundIntent> bpFundIntentList,BpCustMember outMem,BigDecimal fullMoney,String basePath,String postType,HttpServletResponse rep,HttpServletRequest req,String remk0,String remk1,String remk2);
}
