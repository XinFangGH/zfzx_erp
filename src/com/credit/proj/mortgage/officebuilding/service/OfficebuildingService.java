package com.credit.proj.mortgage.officebuilding.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageOfficebuilding;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface OfficebuildingService {

	//异步添加记录
	public void addOfficebuilding(ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeOfficebuildingForUpdate(int id) throws Exception;
	
	//查看详情从商品写字楼表
	@SuppressWarnings("unchecked")
	public List seeOfficebuilding(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateOfficebuilding(int mortgageid,ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeOfficebuildingByObjectType(int mortgageid,String objectType);
	
	public void addPawnOfficebuilding(ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding,PawnItemsList pawnItemsList);
	
	public List getOfficebuildingByObjectType(int mortgageid,String objectType);
}
