package com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;

/**
 * 
 * @author 
 *
 */
public interface GlBankGuaranteemoneyDao extends BaseDao<GlBankGuaranteemoney>{
	public  List<GlBankGuaranteemoney> getbyprojId(Long projId);
	public List<GlBankGuaranteemoney> getallbyglAccountBankId(Long glAccountBankId,int start,int limit);
	public List<GlBankGuaranteemoney> getallbycautionAccountId(Long cautionAccountId,int start,int limit);
	public int getallbyglAccountBankIdsize(Long glAccountBankId);
	public int getallbycautionAccountIdsize(Long cautionAccountId);	
}