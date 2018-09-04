package com.zhiwei.credit.service.creditFlow.smallLoan.finance.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlUrgeRecordDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlUrgeRecord;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlUrgeRecordService;

public class SlUrgeRecordServiceImpl extends BaseServiceImpl<SlUrgeRecord> implements
		SlUrgeRecordService {

	private SlUrgeRecordDao slUrgeRecordDao;
	public SlUrgeRecordServiceImpl(SlUrgeRecordDao slUrgeRecordDao) {
		super(slUrgeRecordDao);
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<SlUrgeRecord> getListByFundIntentId(Long fundIntentId) {
		String hql = "from SlUrgeRecord sr where sr.findIntentId = ?";
		return dao.findByHql(hql, new Object[]{fundIntentId});
	}
	
}