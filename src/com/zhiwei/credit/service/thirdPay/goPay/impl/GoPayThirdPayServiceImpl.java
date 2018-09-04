package com.zhiwei.credit.service.thirdPay.goPay.impl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.codec.digest.DigestUtils;

import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.pay.MoneyMoreMore;
import com.zhiwei.credit.model.thirdPay.GoPayVO;
import com.zhiwei.credit.model.thirdPay.GoZhiFuVO;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.service.thirdPay.fuiou.util.HttpClientHelper;
import com.zhiwei.credit.service.thirdPay.goPay.GoPayThirdPayService;
import com.zhiwei.credit.service.thirdPay.goPay.utils.GopayUtils;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;

public class GoPayThirdPayServiceImpl implements GoPayThirdPayService {
	@Resource
	private SysConfigService sysConfigService;
	private String URL = "";

	@Override
	public String recharge(HttpServletResponse respose, String bankCode,
			String buyerContact, String buyerName, String goodsName,
			String goodsDetail, String remark1, String remark2, String tranAmt,
			String ip, String userType, String charSet, String timeOut) {
		String outStr = null;
		if (timeOut == null || timeOut.equals("")) {
			timeOut = "1200000";
		}
		// 国付宝VO
		GoPayVO goPayVO = new GoPayVO();
		// 生成公共参数
		goPayVO = generateRechargePublicData(goPayVO);
		// 生成参数
		goPayVO = generateRechargeParamatsData(goPayVO, bankCode, buyerContact,
				buyerName, goodsName, goodsDetail, remark1, remark2, tranAmt,
				ip, userType);

		// 组织加密明文
		String signValue = generateRechargePlain(goPayVO);
		// 生成充值map
		Map<String, String> params = generateRechargeMap(goPayVO, signValue);
		try {
			String url = UrlUtils.generateUrl(params, URL, charSet);
			System.out.println(url);
			WebClient.SendByUrl(respose, url,Constants.CHAR_UTF);
			/*
			 * String param=UrlUtils.generateParams(params,null); outStr =
			 * WebClient.getWebContentByPost(URL, param, charSet,
			 * Integer.valueOf(timeOut)); System.out.println(outStr);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outStr;
	}

	@Override
	public String withdraw(HttpServletResponse respose,
			String recvBankAcctName, String recvBankAcctNum,
			String recvBankBranchName, String recvBankCity,
			String recvBankName, String recvBankProvince, String tranAmt,
			String description, String charSet, String timeOut) {
		String outStr = null;
		if (timeOut == null || timeOut.equals("")) {
			timeOut = "1200000";
		}
		GoZhiFuVO goZhiFuVO = new GoZhiFuVO();
		// 生成直付数据
		goZhiFuVO = generateWithdrawPublicData(goZhiFuVO);
		// 生成 参数
		goZhiFuVO = generateWithdrawParamatsData(goZhiFuVO, recvBankAcctName,
				recvBankAcctNum, recvBankBranchName, recvBankCity,
				recvBankName, recvBankProvince, tranAmt, description);
		// 组织加密明文
		String signValue=generateRechargePlain(goZhiFuVO);
		//生成map参数
		Map<String, String> params=generateWithDraw(goZhiFuVO,signValue);
		try {
			String url = UrlUtils.generateUrl(params, URL, charSet);
			System.out.println(url);
			WebClient.SendByUrl(respose, url,Constants.CHAR_UTF);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return outStr;

	}

	/**
	 * 生成充值Map
	 * @param goPayVO
	 * @param signValue
	 * @return
	 */
	private Map<String, String> generateRechargeMap(GoPayVO goPayVO,
			String signValue) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", goPayVO.getVersion());
		params.put("charset", GoPayVO.charset);
		params.put("language", GoPayVO.language);
		params.put("signType", GoPayVO.signType);
		params.put("tranCode", GoPayVO.tranCode);
		params.put("merchantID", goPayVO.getMerchantID());
		params.put("merOrderNum", goPayVO.getMerOrderNum());
		params.put("tranAmt", goPayVO.getTranAmt());
		params.put("feeAmt", goPayVO.getFeeAmt());
		params.put("currencyType", GoPayVO.currencyType);
		params.put("frontMerUrl", goPayVO.getFrontMerUrl());
		params.put("backgroundMerUrl", goPayVO.getBackgroundMerUrl());

		params.put("tranDateTime", goPayVO.getTranDateTime());
		params.put("virCardNoIn", goPayVO.getVirCardNoIn());

		params.put("tranIP", goPayVO.getTranIP());
		params.put("isRepeatSubmit", goPayVO.getIsRepeatSubmit());
		params.put("goodsName", goPayVO.getGoodsName());

		params.put("goodsDetail", goPayVO.getGoodsDetail());
		params.put("buyerName", goPayVO.getBuyerName());
		params.put("buyerContact", goPayVO.getBuyerContact());
		params.put("merRemark1", goPayVO.getMerRemark1());
		params.put("merRemark2", goPayVO.getMerRemark2());

		params.put("bankCode", goPayVO.getBankCode());
		params.put("userType", goPayVO.getUserType());
		params.put("signValue", signValue);
		params.put("gopayServerTime", goPayVO.getGopayServerTime());
		return params;
	}
	/**
	 * 生成提现加密数据
	 * @param goZhiFuVO
	 * @param signValue
	 * @return Map
	 */
	private Map<String, String> generateWithDraw(GoZhiFuVO goZhiFuVO,String signValue){
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", GoZhiFuVO.version);
		params.put("tranCode", GoZhiFuVO.tranCode);
		params.put("customerId", goZhiFuVO.getCustomerId());
		params.put("merOrderNum", goZhiFuVO.getMerOrderNum());
		params.put("tranAmt", goZhiFuVO.getTranAmt());
		params.put("merURL", goZhiFuVO.getMerURL());
		params.put("tranDateTime", goZhiFuVO.getTranDateTime());
		params.put("recvBankAcctName", goZhiFuVO.getRecvBankAcctName());
		params.put("recvBankProvince", goZhiFuVO.getRecvBankProvince());
		params.put("recvBankCity", goZhiFuVO.getRecvBankCity());
		params.put("recvBankName", goZhiFuVO.getRecvBankName());
		params.put("recvBankBranchName", goZhiFuVO.getRecvBankBranchName());
		params.put("recvBankAcctNum", goZhiFuVO.getRecvBankAcctNum());
		params.put("corpPersonFlag", GoZhiFuVO.corpPersonFlag2);
		params.put("description", goZhiFuVO.getDescription());
		params.put("merchantEncode", GoZhiFuVO.charset);
		params.put("signValue", signValue);
		return params;
		
	}

	/**
	 * 生成充值加密数据
	 * 
	 * @param goPayVO
	 * @return
	 */
	private String generateRechargePlain(GoPayVO goPayVO) {
		// 组织加密明文
		String plain = "version=[" + goPayVO.getVersion() + "]tranCode=["
				+ GoPayVO.tranCode + "]merchantID=[" + goPayVO.getMerchantID()
				+ "]merOrderNum=[" + goPayVO.getMerOrderNum() + "]tranAmt=["
				+ goPayVO.getTranAmt() + "]feeAmt=[" + goPayVO.getFeeAmt()
				+ "]tranDateTime=[" + goPayVO.getTranDateTime()
				+ "]frontMerUrl=[" + goPayVO.getFrontMerUrl()
				+ "]backgroundMerUrl=[" + goPayVO.getBackgroundMerUrl()
				+ "]orderId=[]gopayOutOrderId=[]tranIP=[" + goPayVO.getTranIP()
				+ "]respCode=[]gopayServerTime=["
				+ goPayVO.getGopayServerTime() + "]VerficationCode=["
				+ goPayVO.getVerficationCode() + "]";
		String signValue = GopayUtils.md5(plain);
		return signValue;
	}

	/**
	 * 生成提现加密数据
	 * 
	 * @param GoZhiFuVO
	 * @return
	 */
	private String generateRechargePlain(GoZhiFuVO goZhiFuVO) {
		String plain = "version=[" + GoZhiFuVO.version + "]tranCode=["
				+ GoZhiFuVO.tranCode + "]customerId=["
				+ goZhiFuVO.getCustomerId() + "]merOrderNum=["
				+ goZhiFuVO.getMerOrderNum() + "]tranAmt=["
				+ goZhiFuVO.getTranAmt() + "]feeAmt=[]totalAmount=[]merURL=["
				+ goZhiFuVO.getMerURL() + "]recvBankAcctNum=["
				+ goZhiFuVO.getRecvBankAcctNum() + "]tranDateTime=["
				+ goZhiFuVO.getTranDateTime()
				+ "]orderId=[]respCode=[]VerficationCode=["
				+ goZhiFuVO.getVerficationCode() + "]";
		String signValue = GopayUtils.md5(plain);
		return signValue;
	}

	/**
	 * 生成充值参数
	 * 
	 * @param goPayVO
	 * @param bankCode
	 * @param buyerContact
	 * @param buyerName
	 * @param feeAmt
	 * @param goodsName
	 * @param goodsDetail
	 * @param remark1
	 * @param remark2
	 * @param tranAmt
	 * @param ip
	 * @param userType
	 * @return
	 */
	private GoPayVO generateRechargeParamatsData(GoPayVO goPayVO,
			String bankCode, String buyerContact, String buyerName,
			String goodsName, String goodsDetail, String remark1,
			String remark2, String tranAmt, String ip, String userType) {
		goPayVO.setBankCode(bankCode);
		goPayVO.setBuyerContact(buyerContact);
		goPayVO.setBuyerName(buyerName);

		goPayVO.setGoodsName(goodsName);
		goPayVO.setGoodsDetail(goodsDetail);

		goPayVO.setMerRemark1(remark1);
		goPayVO.setMerRemark2(remark2);
		goPayVO.setTranAmt(tranAmt);

		goPayVO.setTranIP(ip);
		goPayVO.setUserType(userType); // 1 个人 2 企业支付

		return goPayVO;
	}

	/**
	 * 生成充值公共 数据
	 * 
	 * @param goPayVO
	 * @return
	 */
	private GoPayVO generateRechargePublicData(GoPayVO goPayVO) {

		// 获取系统配置信息
		// 商户id
		String mid = sysConfigService.findByKey("platformGoPay").getDataValue();
		// URL
		URL = sysConfigService.findByKey("goPayURL").getDataValue();
		// 商户识别码
		String password = sysConfigService.findByKey("goPayRecognition")
				.getDataValue();
		// 手续费
		String freeAmt = sysConfigService.findByKey("goPayPoundage")
				.getDataValue();
		// 版本号
		String goPayVer = sysConfigService.findByKey("goPayVer").getDataValue();
		// 国付宝账号
		String goPayNumber = sysConfigService.findByKey("goPayNumber")
				.getDataValue();

		goPayVO.setVerficationCode(password);
		goPayVO.setVersion(goPayVer);
		goPayVO.setVirCardNoIn(goPayNumber);
		goPayVO.setMerchantID(mid);
		goPayVO.setIsRepeatSubmit("1");
		goPayVO.setFeeAmt(freeAmt);
		goPayVO.setMerOrderNum(Common.getRandomNum(3)
				+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss")); // 订单号
		goPayVO.setGopayServerTime(GopayUtils.getGopayServerTime());
		goPayVO.setTranDateTime(DateUtil
				.dateToStr(new Date(), "yyyyMMddHHmmss"));
		goPayVO.setFrontMerUrl(AppUtil.getP2pUrl()+"/font/returnMsgPay.do");// (AppUtil.get+"pay/backPay.do");
		goPayVO.setBackgroundMerUrl(AppUtil.getSystemUrl()+"/back/returnMsgPay.do");// (this.getBasePath()+"pay/backPay.do");
		return goPayVO;
	}

	/**
	 * 生成国付宝 提现公共数据
	 * 
	 * @param goZhiFuVO
	 * @return
	 */
	private GoZhiFuVO generateWithdrawPublicData(GoZhiFuVO goZhiFuVO) {
		// 企业id
		String mid = sysConfigService.findByKey("goPayCustomerId")
				.getDataValue();
		// URL
		URL = sysConfigService.findByKey("goPayZhiFuURL").getDataValue();
		// 商户识别码
		String password = sysConfigService.findByKey("goPayRecognition")
				.getDataValue();
		goZhiFuVO.setCustomerId(mid);
		goZhiFuVO.setMerURL(AppUtil.getSystemUrl()+"/back/returnMsgPay.do");// 后台通知地址
		goZhiFuVO.setMerOrderNum(Common.getRandomNum(3)
				+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));// 订单号
		goZhiFuVO.setTranDateTime(DateUtil.dateToStr(new Date(),
				"yyyyMMddHHmmss"));
		goZhiFuVO.setVerficationCode(password);
		return goZhiFuVO;

	}

	/**
	 * 生成提现参数
	 * 
	 * @param goZhiFuVO
	 * @param recvBankAcctName
	 * @param recvBankAcctNum
	 * @param recvBankBranchName
	 * @param recvBankCity
	 * @param recvBankName
	 * @param recvBankProvince
	 * @param tranAmt
	 * @param description
	 * @return
	 */
	private GoZhiFuVO generateWithdrawParamatsData(GoZhiFuVO goZhiFuVO,
			String recvBankAcctName, String recvBankAcctNum,
			String recvBankBranchName, String recvBankCity,
			String recvBankName, String recvBankProvince, String tranAmt,
			String description) {

		goZhiFuVO.setRecvBankAcctName(recvBankAcctName);// 银行开户名称
		goZhiFuVO.setRecvBankAcctNum(recvBankAcctNum);// 银行账号
		goZhiFuVO.setRecvBankBranchName(recvBankBranchName);// 网点名称
		goZhiFuVO.setRecvBankCity(recvBankCity);// 收款方所在城市
		goZhiFuVO.setRecvBankName(recvBankName);// 银行名称
		goZhiFuVO.setRecvBankProvince(recvBankProvince);// 收款方银行所在省份
		goZhiFuVO.setTranAmt(tranAmt);// 付款金额
		goZhiFuVO.setDescription(description);

		return goZhiFuVO;
	}
}
