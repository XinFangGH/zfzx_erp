package com.credit.proj.mortgage.business.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageBusiness;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface BusinessServMort {

	//异步添加记录
	public void addBusiness(ProcreditMortgageBusiness procreditMortgageBusiness,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeBusinessForUpdate(int id) throws Exception;
	
	//查看详情--从商业表查询显示在更新页面
	@SuppressWarnings("unchecked")
	public List seeBusiness(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateBusiness(int mortgageid,ProcreditMortgageBusiness procreditMortgageBusiness,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeBusinessByObjectType(int mortgageid,String objectType);
	
	public void addPawnBusiness(ProcreditMortgageBusiness procreditMortgageBusiness,PawnItemsList pawnItemsList);
	public List getBusinessByObjectType(int mortgageid,String objectType);
}
