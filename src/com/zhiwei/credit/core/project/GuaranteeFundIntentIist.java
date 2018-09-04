package com.zhiwei.credit.core.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;



public class GuaranteeFundIntentIist {
	/**
	 * 保费
	 * */
	public static final String GuaranteeToCharge = "GuaranteeToCharge";
	/**
	 * 向客户收取的保证金
	 * */
	public static final String ToCustomGuarantMoney = "ToCustomGuarantMoney";
	
	/**
	 * 退还客户保证金
	 * */
	public static final String BackCustomGuarantMoney = "BackCustomGuarantMoney";
	
	
	public static List<SlFundIntent> getFundIntentDefaultList(String businessType,String date1, String date2,BigDecimal  earnestmoney,
			BigDecimal  customerEarnestmoneyScale,BigDecimal premiumRate,BigDecimal projectMoney
			){
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		SlFundIntent sf1 =new SlFundIntent();
		SlFundIntent sf2 =new SlFundIntent();
		SlFundIntent sf3 =new SlFundIntent();
		sf1.setFundType(GuaranteeToCharge);
		sf1.setIncomeMoney(projectMoney.multiply(premiumRate).divide(BigDecimal.valueOf(100)));
		sf1.setPayMoney(BigDecimal.valueOf(0));
		sf1.setIntentDate(new Date());
		sf1.setBusinessType(businessType);
		list.add(sf1);
		
		if(customerEarnestmoneyScale.compareTo(BigDecimal.valueOf(0))==1){
		sf2.setFundType(ToCustomGuarantMoney);
		sf2.setIncomeMoney(earnestmoney.multiply(customerEarnestmoneyScale).divide(BigDecimal.valueOf(100)));
		sf2.setPayMoney(BigDecimal.valueOf(0));
		sf2.setIntentDate(new Date());
		sf1.setBusinessType(businessType);
		
		sf3.setFundType(BackCustomGuarantMoney);
		sf3.setPayMoney(earnestmoney.multiply(customerEarnestmoneyScale).divide(BigDecimal.valueOf(100)));
		sf3.setIncomeMoney(BigDecimal.valueOf(0));
		sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
		sf3.setBusinessType(businessType);
		list.add(sf2);
		list.add(sf3);
		
		
		}
		
		return list;
		
		
		
		
	}

}
