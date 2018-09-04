package com.zhiwei.credit.util.financeUtil;

import java.util.Comparator;

import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;

public class FundIntentComparator implements Comparator<SlFundIntent> {

	@Override
	public int compare(SlFundIntent o1, SlFundIntent o2) {
		if(o1.getPayintentPeriod()==null)return -1;
		if(o2.getPayintentPeriod()==null)return 1;
		//比较糙，因为当涉及到展期的时候，展期期数是1但是也要排在之前的款项表前面，用的时候具体参考中宇慧通去啊，暂时用不着
		return o1.getPayintentPeriod().compareTo(o2.getPayintentPeriod());
	}

}
