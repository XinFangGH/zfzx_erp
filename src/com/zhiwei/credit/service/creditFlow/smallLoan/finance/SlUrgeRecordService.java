package com.zhiwei.credit.service.creditFlow.smallLoan.finance;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlUrgeRecord;

public interface SlUrgeRecordService extends BaseService<SlUrgeRecord> {

	List<SlUrgeRecord> getListByFundIntentId(Long fundIntentId);
}