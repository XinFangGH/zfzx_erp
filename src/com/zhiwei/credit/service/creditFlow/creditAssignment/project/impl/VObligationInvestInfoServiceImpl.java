package com.zhiwei.credit.service.creditFlow.creditAssignment.project.impl;



import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.project.VObligationInvestInfoDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.VObligationInvestInfo;
import com.zhiwei.credit.service.creditFlow.creditAssignment.project.VObligationInvestInfoService;


public class VObligationInvestInfoServiceImpl extends BaseServiceImpl<VObligationInvestInfo> implements VObligationInvestInfoService {
	private VObligationInvestInfoDao dao;
	public VObligationInvestInfoServiceImpl(VObligationInvestInfoDao dao) {
		super(dao);
		this.dao=dao;
	}
	//查出所有债权信息，或者是不包含系统默认的金帆平台债权账户
	@Override
	public List<VObligationInvestInfo> getAllList(String obligationState,
			String obligationName, String investName, String payintentPeriod,
			String investEndDate, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return this.dao.getAllList(obligationState,obligationName,investName,payintentPeriod,investEndDate,start,limit);
	}
	//债权到期提醒查询方法
	@Override
	public List<VObligationInvestInfo> getInvestList(String obligationState,
			String obligationName, String investName, String investStartDate,
			String investEndDate, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return  this.dao.getInvestList(obligationState,obligationName,investName,investStartDate,investEndDate,start,limit);
	}
	//根据投资人的id查出来投资人的债权情况
	@Override
	public List<VObligationInvestInfo> getlistInvestPersonByPersonId(
			String investPersonId, String obligationState, Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.dao.getlistInvestPersonByPersonId(investPersonId,obligationState,start,limit);
	}
	@Override
	public List<VObligationInvestInfo> getlistInvestPersonObligation(
			String investPersonId, String investObligationStatus,
			String investStartDate, String investEndDate) {
		return this.dao.getlistInvestPersonObligation(investPersonId,investObligationStatus,investStartDate,investEndDate);
	}

}
