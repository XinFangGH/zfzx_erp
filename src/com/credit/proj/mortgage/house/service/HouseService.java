package com.credit.proj.mortgage.house.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageHouse;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface HouseService {

	//异步添加记录
	public void addHouse(ProcreditMortgageHouse procreditMortgageHouse,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeHouseForUpdate(int id) throws Exception;
	
	//查看详情从住宅表
	@SuppressWarnings("unchecked")
	public List seeHouse(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateHouse(int mortgageid,ProcreditMortgageHouse procreditMortgageHouse,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeHouseByObjectType(int mortgageid,String objectType);
	
	public void addPawnHouse(ProcreditMortgageHouse procreditMortgageHouse,PawnItemsList pawnItemsList);
	
	public List getHouseByObjectType(int mortgageid,String objectType);
}
