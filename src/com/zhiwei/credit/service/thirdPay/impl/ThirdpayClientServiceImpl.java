package com.zhiwei.credit.service.thirdPay.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.UUIDGenerator;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.thirdPay.BpThirdpayCity;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.service.thirdPay.BpThirdpayCityService;
import com.zhiwei.credit.service.thirdPay.ThirdpayClientService;
import com.zhiwei.credit.service.thirdPay.fuiou.ThirdPayService;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.incomeforrsp;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.incomefor.InComeForReqType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.payfor.PayForReqType;
import com.zhiwei.credit.service.thirdPay.goPay.GoPayThirdPayService;
import com.zhiwei.credit.util.Common;

/**
 * 
 * @author 
 *
 */
@WebService(targetNamespace="http://thirdPay.service.credit.zhiwei.com/", endpointInterface = "com.zhiwei.credit.service.thirdPay.ThirdpayClientService") 
public class ThirdpayClientServiceImpl  implements ThirdpayClientService{
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private ThirdPayService thirdPayService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private BpThirdpayCityService bpThirdpayCityService;
	
	@Resource 
	private GoPayThirdPayService goPayThirdPayService;
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	@Override
	public String[] rechargeFuiou(CsInvestmentperson csInvestmentperson,
			BigDecimal money, String remark,HttpServletRequest req) {
		String[] ret = new String[2];
		try {
			// 传输的数据
			InComeForReqType inComeForReqType = new InComeForReqType();
			// 获取系统配置信息
			String mid = sysConfigService.findByKey("platformFuiou")
					.getDataValue();
			String url = sysConfigService.findByKey("fuiouURL").getDataValue();
			String password = sysConfigService.findByKey("fuiouPassword")
					.getDataValue();
			inComeForReqType.setVer(sysConfigService.findByKey("fuiouVer")
					.getDataValue());

			// 生成数据
			inComeForReqType = generateLoanData(inComeForReqType,
					csInvestmentperson, money, remark,req);
			String xml = "";
			xml = thirdPayService.loan(mid, password, url, inComeForReqType,
					null, null);
			// 解析xml
			if(xml!=null){
			Document rootDoc = XmlUtil.stringToDocument(xml);
			Element element = rootDoc.getRootElement();
			Iterator<Element> it = element.elements().iterator();
			while (it.hasNext()) {
				Element el = it.next();
				if(el.getName().equals("ret")){
					ret[0] = el.getText();
				}
				if(el.getName().equals("memo")){
					ret[1] = el.getText();
				}
			}
			}else{
				ret[0] = "faild";
				ret[1] = "连接出错";
			}
		} catch (Exception e) {
			ret[0] = "faild";
			ret[1] = "faild";
		}
		return ret;
	}
	@Override
	public String[] directPayFuiou(CsInvestmentperson csInvestmentperson,
			BigDecimal money, String remark,HttpServletRequest req) {
		String[] ret = new String[2];
		try {
			// 传输的数据
			PayForReqType payForReqType = new PayForReqType();
			// 获取系统配置信息
			String mid = sysConfigService.findByKey("platformFuiou")
					.getDataValue();
			String url = sysConfigService.findByKey("fuiouURL").getDataValue();
			String password = sysConfigService.findByKey("fuiouPassword")
					.getDataValue();
			payForReqType.setVer(sysConfigService.findByKey("fuiouVer")
					.getDataValue());

			// 生成数据
			payForReqType = generateRepaymentData(payForReqType,
					csInvestmentperson, money, remark,req);
			String xml = "";
			xml = thirdPayService.directPay(mid, password, url, payForReqType,
					null, null);
			// 解析xml
			if(xml!=null){
			Document rootDoc = XmlUtil.stringToDocument(xml);
			Element element = rootDoc.getRootElement();
			Iterator<Element> it = element.elements().iterator();
			while (it.hasNext()) {
				Element el = it.next();
				if(el.getName().equals("ret")){
					ret[0] = el.getText();
				}
				if(el.getName().equals("memo")){
					ret[1] = el.getText();
				}
			}
			}else{
				ret[0] = "faild";
				ret[1] = "连接出错";
			}
		} catch (Exception e) {
			ret[0] = "faild";
			ret[1] = "faild";
		}
		return ret;
	}
	
	
	/**
	 * 
	 * 富友接口
	 * 生成 充值 放款数据  
	 * @param inComeForReqType
	 * @param CsInvestmentperson
	 *            投资人信息 money 充值金额 remark 备注
	 * @return
	 */
	private InComeForReqType generateLoanData(
			InComeForReqType inComeForReqType,
			CsInvestmentperson csInvestmentperson, BigDecimal money,
			String remark,HttpServletRequest req) {
		if (inComeForReqType == null) {
			inComeForReqType = new InComeForReqType();
		}
		String merdt = DateUtil.dateToStr(new Date(), "yyyyMMdd"); // 请求日期 c(8)
		// yyyyMMdd
		String orderno = Common.getRandomNum(2)
				+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS"); // 请求流水
		// c(10,30)
		// 数字串，当天必须唯一
		String entseq = UUIDGenerator.getUUID(); // 企业流水号 c(1,32)
		// 填写后，系统体现在交易查询和外部通知中
		QueryFilter filter = new QueryFilter(req);
		filter.addFilter("Q_isInvest_SN_EQ", "3"); // 是否是投资客户
		filter.addFilter("Q_enterpriseid_N_EQ", csInvestmentperson
				.getInvestId().toString());// 客户ID
		filter.addFilter("Q_iscredit_SN_EQ", "0");// 是否是放款账户
		List<EnterpriseBank> bankList = enterpriseBankService.getAll(filter);

		String bankno = ""; // 总行代码 c(4) 参见总行代码表
		String accntno = ""; // 账号 c(10,28) 用户账号
		String accntnm = ""; // 账户名称 c(30) 用户账户名称
		if (bankList != null && bankList.size() > 0) {
			// 获取银行
			CsBank bank = csBankService.get(bankList.get(0).getBankid());
			if (bank != null) {
				bankno = bank.getRemarks(); // 总行代码 c(4) 参见总行代码表
			}
			accntno = bankList.get(0).getAccountnum(); // 账号 c(10,28) 用户账号
			accntnm = bankList.get(0).getName(); // 账户名称 c(30) 用户账户名称
		}

		String mobile = csInvestmentperson.getCellphone(); // 手机号 c(11)
		// 为将来短信通知时使用
		String certtp = "";
		if (csInvestmentperson.getCardtype().equals("309")) {
			certtp = "0"; // 证件类型
		} else {
			certtp = "7"; // 证件类型
		}
		String certno = csInvestmentperson.getCardnumber(); // 证件号
		String amt = String.valueOf(((money.intValue()) * 100));
		// // 金额 n(1,12) 单位：分
		String memo = ""; // 备注 c(1,64)
		// 填写后，系统体现在交易查询和外部通知中

		inComeForReqType.setAccntnm(accntnm);
		inComeForReqType.setAccntno(accntno);
		inComeForReqType.setAmt(amt);
		inComeForReqType.setBankno(bankno);
		inComeForReqType.setEntseq(entseq);
		inComeForReqType.setMemo(memo);
		inComeForReqType.setMerdt(merdt);
		inComeForReqType.setMobile(mobile);
		inComeForReqType.setOrderno(orderno);
		inComeForReqType.setCerttp(certtp);
		inComeForReqType.setCertno(certno);
		return inComeForReqType;
	}
	
	/**
	 * 生成 还款数据
	 * 富友接口
	 * @param PayForReqType
	 * @param slFundIntent
	 * @return
	 */
	private PayForReqType generateRepaymentData(PayForReqType payForReqType,
			CsInvestmentperson csInvestmentperson, BigDecimal money,
			String remark,HttpServletRequest req) {
		try {
			if (payForReqType == null) {
				payForReqType = new PayForReqType();
			}
			String merdt = DateUtil.dateToStr(new Date(), "yyyyMMdd"); // 请求日期// c(8)// yyyyMMdd
			String orderno = Common.getRandomNum(2)
					+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS"); // 请求流水// c(10,30)
			// 数字串，当天必须唯一
			String entseq = UUIDGenerator.getUUID(); // 企业流水号 c(1,32)
			
			// 获取投资人信息
			QueryFilter filter = new QueryFilter(req);
			filter.addFilter("Q_isInvest_SN_EQ", "3"); // 是否是投资客户
			filter.addFilter("Q_enterpriseid_N_EQ", csInvestmentperson
					.getInvestId().toString());// 客户ID
			filter.addFilter("Q_iscredit_SN_EQ", "0");// 是否是放款账户
			List<EnterpriseBank> bankList = enterpriseBankService.getAll(filter);

			String bankno = ""; // 总行代码 c(4) 参见总行代码表
			String accntno = ""; // 账号 c(10,28) 用户账号
			String accntnm = ""; // 账户名称 c(30) 用户账户名称
			String cityno = ""; // 城市代码 c(4) 否参见地市代码表
			String branchnm = ""; // 支行名称c(100) 是支行名称，中行、建行、广发必填
			if (bankList != null && bankList.size() > 0) {
				// 获取银行
				CsBank bank = csBankService.get(bankList.get(0).getBankid());
				branchnm =bank.getBankname()+ bankList.get(0).getBankOutletsName(); // 支行名称c(100)
																	// 是支行名称，中行、建行、广发必填 例如：中国银行股份有限公司北京西单支行
				String[] s = bankList.get(0).getAreaName().split("-");
				QueryFilter filterCity = new QueryFilter(req);
				filterCity.addFilter("Q_name_S_EQ", s[s.length - 1]);
				filterCity.addFilter("Q_typeKey_S_EQ", configMap.get("thirdPayConfig").toString());
				List<BpThirdpayCity> listCity = bpThirdpayCityService
						.getAll(filterCity);
				if (listCity != null && listCity.size() > 0) {
					cityno = listCity.get(0).getNumber(); // 城市代码 c(4) 否参见地市代码表
				}
				if (bank != null) {
					bankno = bank.getRemarks(); // 总行代码 c(4) 参见总行代码表
				}
				accntno = bankList.get(0).getAccountnum(); // 账号 c(10,28) 用户账号
				accntnm = bankList.get(0).getName(); // 账户名称 c(30) 用户账户名称
			}

			String mobile = csInvestmentperson.getCellphone(); // 手机号 c(11) 为将来短信通知时使用

			String certno = csInvestmentperson.getCardnumber(); // 证件号
			String amt = (money.multiply(new BigDecimal(100))).toString(); // 金额 n(1,12)
																// 单位：分
			String memo = remark; // 备注 c(1,64)
			// 填写后，系统体现在交易查询和外部通知中

			payForReqType.setAccntnm(accntnm);
			payForReqType.setAccntno(accntno);
			payForReqType.setAmt(amt);
			payForReqType.setBankno(bankno);
			payForReqType.setEntseq(entseq);
			payForReqType.setMemo(memo);
			payForReqType.setMerdt(merdt);
			payForReqType.setMobile(mobile);
			payForReqType.setBranchnm(branchnm);
			payForReqType.setCityno(cityno);
			payForReqType.setOrderno(orderno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payForReqType;
	}
	@Override
	public String rechargeGoPay(HttpServletResponse respose, String bankCode,
			String buyerContact, String buyerName, String goodsName,
			String goodsDetail, String remark1, String remark2, String tranAmt,
			String ip, String userType, String charSet, String timeOut) {
		return goPayThirdPayService.recharge(respose, bankCode, buyerContact, buyerName, goodsName, goodsDetail, remark1, remark2, tranAmt, ip, userType, charSet, timeOut);
	}
	@Override
	public String withdrawGoPay(HttpServletResponse respose,
			String recvBankAcctName, String recvBankAcctNum,
			String recvBankBranchName, String recvBankCity,
			String recvBankName, String recvBankProvince, String tranAmt,
			String description, String charSet, String timeOut) {
		return goPayThirdPayService.withdraw(respose, recvBankAcctName, recvBankAcctNum, recvBankBranchName, recvBankCity, recvBankName, recvBankProvince, tranAmt, description, charSet, timeOut);
	}
}