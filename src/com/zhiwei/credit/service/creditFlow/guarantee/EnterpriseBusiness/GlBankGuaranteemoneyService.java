package com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;

/**
 * 
 * @author 
 *
 */
public interface GlBankGuaranteemoneyService extends BaseService<GlBankGuaranteemoney>{
	public  List<GlBankGuaranteemoney> getbyprojId(Long projId);

	public Integer saveReturnMoneyFlow();

	public Integer saveAfterFlow(FlowRunInfo flowRunInfo);
	public Integer saveAfterFlow1(FlowRunInfo flowRunInfo);
	
	public Integer saveAfterFlowZm(FlowRunInfo flowRunInfo);
	
	
	
	public List<GlBankGuaranteemoney> getallbyglAccountBankId(Long glAccountBankId,int start,int limit);
	public List<GlBankGuaranteemoney> getallbycautionAccountId(Long cautionAccountId,int start,int limit);
	public int getallbyglAccountBankIdsize(Long glAccountBankId);
	public int getallbycautionAccountIdsize(Long cautionAccountId);	
	public void saveguaranteemoneyAccount(Long glAccountBankId,Long cautionAccountId);

}


