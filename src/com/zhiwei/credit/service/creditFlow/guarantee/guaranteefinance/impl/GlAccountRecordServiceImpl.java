package com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountRecordDao;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountRecordService;

/**
 * 
 * @author 
 *
 */
public class GlAccountRecordServiceImpl extends BaseServiceImpl<GlAccountRecord> implements GlAccountRecordService{
	@SuppressWarnings("unused")
	private GlAccountRecordDao dao;
	
	public GlAccountRecordServiceImpl(GlAccountRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<GlAccountRecord> getallbycautionAccountId(
			Long cautionAccountId, int start, int limit) {
		return dao.getallbycautionAccountId(cautionAccountId, start, limit);
	}

	@Override
	public List<GlAccountRecord> getallbyglAccountBankId(
			Long glAccountBankId, int start, int limit) {
		return dao.getallbyglAccountBankId(glAccountBankId, start, limit);
	}

	@Override
	public int getallbycautionAccountIdsize(Long cautionAccountId) {
		// TODO Auto-generated method stub
		return dao.getallbycautionAccountIdsize(cautionAccountId);
	}

	@Override
	public int getallbyglAccountBankIdsize(Long glAccountBankId) {
		// TODO Auto-generated method stub
		return dao.getallbyglAccountBankIdsize(glAccountBankId);
	}

	@Override
	public List<GlAccountRecord> getallreleatebycautionAccountId(
			Long cautionAccountId, int start, int limit, String flag) {
		// TODO Auto-generated method stub
		return dao.getallreleatebycautionAccountId(cautionAccountId, start, limit, flag);
	}

	@Override
	public int getallreleatebycautionAccountIdsize(Long cautionAccountId,
			String flag) {
		// TODO Auto-generated method stub
		return dao.getallbycautionAccountIdsize(cautionAccountId);
	}

	@Override
	public List<GlAccountRecord> getallreleatebyglAccountBankId(
			Long glAccountBankId, int start, int limit, String flag) {
		// TODO Auto-generated method stub
		return dao.getallreleatebyglAccountBankId(glAccountBankId, start, limit, flag);
	}

	@Override
	public int getallreleatebyglAccountBankIdsize(Long glAccountBankId,
			String flag) {
		// TODO Auto-generated method stub
		return dao.getallreleatebyglAccountBankIdsize(glAccountBankId, flag);
	}

	@Override
	public List<GlAccountRecord> getallreleate(int start, int limit, String flag) {
		// TODO Auto-generated method stub
		return dao.getallreleate(start, limit, flag);
	}

	@Override
	public int getallreleate(String flag) {
		// TODO Auto-generated method stub
		return dao.getallreleate(flag);
	}

	@Override
	public Map<String, BigDecimal> saveFinancingProjectfiance(Long projectId,
			String businessType) {
		Map<String,BigDecimal> map=new HashMap<String,BigDecimal>();
		return map;
	}

	@Override
	public List<GlAccountRecord> getbyprojectId(Long projectId) {
		// TODO Auto-generated method stub
		return dao.getbyprojectId(projectId);
	}

	@Override
	public List<GlAccountRecord> getbyprojectIdcapitalType(Long projectId,
			int capitalType) {
		// TODO Auto-generated method stub
		return dao.getbyprojectIdcapitalType(projectId, capitalType);
	}

	@Override
	public List<GlAccountRecord> getalllist() {
		// TODO Auto-generated method stub
		return dao.getalllist();
	}

	

}