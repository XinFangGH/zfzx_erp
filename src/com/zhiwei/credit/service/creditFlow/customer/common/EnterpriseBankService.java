package com.zhiwei.credit.service.creditFlow.customer.common;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;

public interface EnterpriseBankService extends BaseService<EnterpriseBank> {
	public List<EnterpriseBank> getBankList(Integer customerId,Short isEnterprise,Short iscredit,Short isInvest);
	public EnterpriseBank getById(Integer id);
	public List<EnterpriseBank> getList(Integer customerId, Short isEnterprise,Short isInvest, Integer start, Integer limit);
	public void add(EnterpriseBank bank);
	public void update(EnterpriseBank bank);
	public EnterpriseBank queryIscredit(Integer customerId, Short isEnterprise,Short isInvest);
	public List<EnterpriseBank> queryAlreadyAccount(Integer id,String accountnum);
	
	public void querySomeList(PageBean<EnterpriseBank> pageBean);
}
