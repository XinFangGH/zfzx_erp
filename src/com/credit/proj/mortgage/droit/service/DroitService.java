package com.credit.proj.mortgage.droit.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageDroit;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface DroitService {

	//异步添加记录
	public void addDroit(ProcreditMortgageDroit procreditMortgageDroit,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeDroitForUpdate(int id) throws Exception;
	
	//查看详情从无形权利表
	@SuppressWarnings("unchecked")
	public List seeDroit(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateDroit(int mortgageid,ProcreditMortgageDroit procreditMortgageDroit,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeDroitByObjectType(int mortgageid,String objectType);
	
	public void addPawnDroit(ProcreditMortgageDroit procreditMortgageDroit,PawnItemsList pawnItemsList);
	
	public List getDroitByObjectType(int mortgageid,String objectType);
}
