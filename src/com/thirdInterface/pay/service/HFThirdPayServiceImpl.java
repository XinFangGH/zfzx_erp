package com.thirdInterface.pay.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.p2p.BpCustMember;

/**
 * 汇付实现 接口
 * @author YUAN.zc
 *
 */
public class HFThirdPayServiceImpl implements ThirdPayEngine {
    
	@Override
	public String[] repayment(SlFundIntent fund,List<BpFundIntent> bpFundIntentList,BpCustMember outMem,BigDecimal fullMoney, String basePath, String postType,
			HttpServletResponse rep, HttpServletRequest req, String remk0,
			String remk1, String remk2) {
		
		return null;
	}

}
