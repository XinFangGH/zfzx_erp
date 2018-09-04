package com.credit.proj.mortgage.businessandlive.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageBusinessandlive;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface BusinessandliveService {

	//异步添加记录
	public void addBusinessandlive(ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeBusinessAndLiveForUpdate(int id) throws Exception;
	
	//查看详情从商住用地表
	@SuppressWarnings("unchecked")
	public List seeBusinessandlive(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateBusinessandlive(int mortgageid,ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeBusinessandliveByObjectType(int mortgageid,String objectType);
	
	public void addPawnBusinessandlive(ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive,PawnItemsList pawnItemsList);
	
	public List getBusinessandliveByObjectType(int mortgageid,String objectType);
}
