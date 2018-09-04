package com.zhiwei.credit.service.thirdInterface;

import java.math.BigDecimal;


import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;


public interface FuiouService{

	
	/**
	 * 用户开户
	 * @param mem 投资客户
	 * @param regType 注册类型 后台 用户 开户  BgRegister  前台 用户 开户 UserRegister 目前使用前台用户开户
	 * @param  basePath 访问路径
	 * @return
	 */
	public String[] register(HttpServletResponse respose,BpCustMember mem, String regType,String basePath,HttpServletRequest request,WebBankcard webBankcard);
	/**
	 * 用户登陆富有金系统
	 * @param response
	 * @param mem
	 * @param string
	 * @param basePath
	 * @return
	 */
	public String[] loginToFuiou(HttpServletResponse response,
			BpCustMember mem, String string, String basePath);
	
	/**
	 * 富有查询当前账户信息
	 * svn:songwj
	 * @param bpCustMember
	 * @return
	 */
	public BpCustMember  getBpCustMemberMessage(BpCustMember bpCustMember);
	
	/**
	 * 用户信息修改
	 * svn:songwj
	 * BpCustMember:会员表
	 * cityId：
	 * parentBankId
	 * bankNum：
	 * capAcntNo：
	 */
	public String[] updateMemberMessage(BpCustMember bpCustMember,String cityId ,String parentBankId,String bankNum,String capAcntNo);
	/**
	 * 富有查询当前账户余额
	 * @param bpCustMember
	 * @return
	 */
	public BpCustMember getCurrentMoney(BpCustMember bpCustMember);

	/**
	 * 富有用户交易明细查询
	 * @param user_ids
	 * @param start_day
	 * @param end_day
	 */
	public void dealInfoQuery(String user_ids, Date start_day, Date end_day);
	/**
	 * 预授权 2014-07-09
	 * @param mchnt_txn_ssn  我们系统的唯一标识
	 * @param out_cust_no 出账账户账号（可以是个人也可以是商户）
	 * @param in_cust_no 入账账户账号（可以是个人也可以是商户）
	 * @param amt 预授权金额
	 * @param rem 授权备注信息
	 * @return
	 */
	public String[] preAuth(String mchnt_txn_ssn,String out_cust_no,String in_cust_no,String amt,String rem,HttpServletRequest request);
	/**
	 * 预授权撤销
	 * @param request
	 * @param response
	 * @param outCustNo
	 * @param inCustNo
	 * @return
	 */
	public String[] preAuthRev(String mchnt_txn_ssn,String out_cust_no,String in_cust_no,String contract_no,String rem,HttpServletRequest request);
	/**
	 * 转账（商户和个人之间）
	 * @param request
	 * @return
	 */
	public String[] traAcc(String orderNum,String out_cust_no,String in_cust_no,String amt,String contract_no,String rem);
	
	/**
	 * 划拨（个人与个人之间）
	 * svn:songwj
	 * BpCustMember:会员表
	 * outCustNo:付款登陆账户
	 * inCustNo：收款登陆账户
	 * amt：划拨金额
	 * contractNo：预授权合同号
	 * rem：备注
	 */
	 
	public String[] transferPersonToPerson(String orderNum,String outCustNo,String inCustNo,String  amt,String contractNo,String rem);
	
}