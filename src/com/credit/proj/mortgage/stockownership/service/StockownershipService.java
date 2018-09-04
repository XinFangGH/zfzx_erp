package com.credit.proj.mortgage.stockownership.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageStockownership;
import com.credit.proj.entity.VProjMortStockFinance;
import com.credit.proj.entity.VProjMortStockOwnerShip;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface StockownershipService {

	//异步添加记录
	public void addStockownership(ProcreditMortgageStockownership procreditMortgageStockownership,ProcreditMortgage procreditMortgage,Enterprise enterprise) throws Exception;
	
	//从视图查询数据显示在详情页面
	public List seeStockownershipForUpdate(int id) throws Exception;
	
	//查看详情从股权表
	@SuppressWarnings("unchecked")
	public List seeStockownership(int mortgageid) throws Exception;
	
	public VProjMortStockOwnerShip seeStockownershipFromView(int mortgageid);
	
	//异步更新记录
	public void updateStockownership(int mortgageid,ProcreditMortgageStockownership procreditMortgageStockownership,ProcreditMortgage procreditMortgage,Enterprise enterprise) throws Exception;
	
	public VProjMortStockFinance getStockownershipFinance(int mortgageid);
	
	public List getStockownershipByObjectType(int mortgageid,String objectType);
	
	public void addPawnStockownership(ProcreditMortgageStockownership procreditMortgageStockownership,PawnItemsList pawnItemsList,Enterprise enterprise);
	
	public List seeStockownershipByObjectType(int mortgageid,String objectType);
}
