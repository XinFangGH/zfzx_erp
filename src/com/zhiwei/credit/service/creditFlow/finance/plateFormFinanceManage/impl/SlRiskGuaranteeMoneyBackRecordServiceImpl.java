package com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.SlRiskGuaranteeMoneyBackRecordDao;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskGuaranteeMoneyBackRecord;
import com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage.SlRiskGuaranteeMoneyBackRecordService;

/**
 * 
 * @author 
 *
 */
public class SlRiskGuaranteeMoneyBackRecordServiceImpl extends BaseServiceImpl<SlRiskGuaranteeMoneyBackRecord> implements SlRiskGuaranteeMoneyBackRecordService{
	@SuppressWarnings("unused")
	private SlRiskGuaranteeMoneyBackRecordDao dao;
	
	public SlRiskGuaranteeMoneyBackRecordServiceImpl(SlRiskGuaranteeMoneyBackRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlRiskGuaranteeMoneyBackRecord> getrepaymentRecord(
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return dao.getrepaymentRecord(request,start,limit);
	}

}