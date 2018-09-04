package com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;

/**
 * 
 * @author 
 *
 */
public interface GlAccountRecordService extends BaseService<GlAccountRecord>{
	public List<GlAccountRecord> getallbyglAccountBankId(Long glAccountBankId,int start,int limit);
	public List<GlAccountRecord> getallbycautionAccountId(Long cautionAccountId,int start,int limit);
	public int getallbyglAccountBankIdsize(Long glAccountBankId);
	public int getallbycautionAccountIdsize(Long cautionAccountId);	
	public List<GlAccountRecord> getallreleatebyglAccountBankId(Long glAccountBankId,int start,int limit,String flag);
	public List<GlAccountRecord> getallreleatebycautionAccountId(Long cautionAccountId,int start,int limit,String flag);
	public int getallreleatebyglAccountBankIdsize(Long glAccountBankId,String flag);
	public int getallreleatebycautionAccountIdsize(Long cautionAccountId,String flag);	
	public List<GlAccountRecord> getallreleate(int start,int limit,String flag);
	public int getallreleate(String flag);
	
	 public Map<String,BigDecimal> saveFinancingProjectfiance(Long projectId,String businessType);
	 
	 public List<GlAccountRecord> getbyprojectId(Long projectId);
	 public List<GlAccountRecord> getbyprojectIdcapitalType(Long projectId,int capitalType);
	 
	 public List<GlAccountRecord> getalllist();
	 
}


