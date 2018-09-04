package com.zhiwei.credit.service.thirdPay;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.thirdPay.BpThirdpayCity;

/**
 * 
 * @author 
 *
 */
@WebService
public interface ThirdpayClientService {
	/**
	 * 富有充值接口
	 * @param csInvestmentperson 投资人
	 * @param money 金额
	 * @param remark 备注
	 * @return
	 */
	public String[] rechargeFuiou(CsInvestmentperson csInvestmentperson,
			BigDecimal money, String remark,HttpServletRequest req);
	/**
	 * 富有还款接口
	 * @param csInvestmentperson 投资人
	 * @param money 金额
	 * @param remark 备注
	 * @return
	 */
	public String[] directPayFuiou(CsInvestmentperson csInvestmentperson,
			BigDecimal money, String remark,HttpServletRequest req) ;
	
	/**
	 * 国付宝  非托管账户 充值  接口
	 * 
	 * @param respose
	 * @param bankCode
	 *            银行代码
	 * @param buyerContact
	 *            买方联系方式
	 * @param buyerName
	 *            买方姓名
	 * @param goodsName
	 *            商品名称
	 * @param goodsDetail
	 *            商品简介
	 * @param remark1
	 * @param remark2
	 * @param tranAmt
	 *            交易金额
	 * @param ip
	 *            用户IP
	 * @param userType
	 *            用户类型
	 * @param charSet
	 *            编码格式
	 * @param timeOut
	 *            超时
	 * @return
	 */
	@WebMethod
	public String rechargeGoPay(HttpServletResponse respose, String bankCode,
			String buyerContact, String buyerName, String goodsName,
			String goodsDetail, String remark1, String remark2, String tranAmt,
			String ip, String userType, String charSet, String timeOut);

	/**
	 * 国付宝 非托管账户 提现 接口
	 * @param respose
	 * @param recvBankAcctName 开户名称
	 * @param recvBankAcctNum  账号
	 * @param recvBankBranchName 网点名称
	 * @param recvBankCity 收款方银行所在城市
	 * @param recvBankName 银行名称 
	 * @param recvBankProvince 所在省份
	 * @param tranAmt 金额
	 * @param description 备注
	 * @param charSet 编码格式
	 * @param timeOut 超时时间
	 * @return
	 */
	@WebMethod
	public String withdrawGoPay(HttpServletResponse respose,
			String recvBankAcctName, String recvBankAcctNum,
			String recvBankBranchName, String recvBankCity,
			String recvBankName, String recvBankProvince,
			String tranAmt, String description, String charSet, String timeOut);
}


