package com.zhiwei.credit.service.thirdPay.fuiou;

import com.zhiwei.credit.service.thirdPay.fuiou.model.req.incomefor.InComeForReqType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.payfor.PayForReqType;

public interface ThirdPayService {
	/**
	 * 付款接口  平台 -投资人   适合于 还不 付息
	 * @param merId 商户ID
	 * @param password 密码
	 * @param URL  url
	 * @param payForReqType 实体
	 * @param charSet  编码格式
	 * @param timeOut 超时
	 * @return
	 */
  public String  directPay(String merId ,String password,String URL,PayForReqType payForReqType,String charSet,String timeOut);
 /**
  * 放款接口   投资人 - 平台   适合 于 放款 
 * @param merId 商户ID
	 * @param password 密码
	 * @param URL  url
	 * @param payForReqType 实体
	 * @param charSet  编码格式
	 * @param timeOut 超时
	 * @return
	 */
  public String loan(String merId ,String password,String URL,InComeForReqType inComeForReqType,String charSet,String timeOut);
}
