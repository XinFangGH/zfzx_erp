package com.zhiwei.credit.service.thirdInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hurong.credit.model.user.BpCustMember;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;

public interface EasyPayService{
	/**
	 * 易生支付取现接口调用方法
	 * 参数：取现客户信息
	 * @return
	 */
	public String[] easyPayWithdraws(ObAccountDealInfo info,String basePath);
	
}