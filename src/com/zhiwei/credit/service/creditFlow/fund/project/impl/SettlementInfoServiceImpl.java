package com.zhiwei.credit.service.creditFlow.fund.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.fund.project.SettlementInfoDao;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementInfo;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementInfoService;

/**
 * 
 * @author 
 *
 */
public class SettlementInfoServiceImpl extends BaseServiceImpl<SettlementInfo> implements SettlementInfoService{
	@SuppressWarnings("unused")
	private SettlementInfoDao dao;
	
	public SettlementInfoServiceImpl(SettlementInfoDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<SettlementInfo> listByOrgId(HttpServletRequest request){
		return dao.listByOrgId(request);
	}
	@Override
	public List<SettlementInfo> listByOrgId(String orgId,String startDate,String endDate){
		return dao.listByOrgId(orgId, startDate, endDate);
	}
}