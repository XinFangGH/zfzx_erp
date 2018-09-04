package com.credit.proj.mortgage.houseground.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageHouseground;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface HousegroundService {

	//异步添加记录
	public void addHouseground(ProcreditMortgageHouseground procreditMortgageHouseground,ProcreditMortgage procreditMortgage) throws Exception;
	
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeHousegroundForUpdate(int id) throws Exception;
	
	//查看详情从住宅用地表
	@SuppressWarnings("unchecked")
	public List seeHouseground(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateHouseground(int mortgageid,ProcreditMortgageHouseground procreditMortgageHouseground,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeHousegroundByObjectType(int mortgageid,String objectType);
	
	public void addPawnHouseground(ProcreditMortgageHouseground procreditMortgageHouseground,PawnItemsList pawnItemsList);
	
	public List getHousegroundByObjectType(int mortgageid,String objectType);
}
