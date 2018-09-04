package com.credit.proj.mortgage.industry.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageIndustry;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
@SuppressWarnings("all")
public interface IndustryService {

	//异步添加记录
	public void addIndustry(ProcreditMortgageIndustry procreditMortgageIndustry,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	public List seeIndustryForUpdate(int id) throws Exception;
	
	//查看详情从工业用地表
	public List seeIndustry(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateIndustry(int mortgageid,ProcreditMortgageIndustry procreditMortgageIndustry,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeIndustryByObjectType(int mortgageid,String objectType);
	
	public void addPawnIndustry(ProcreditMortgageIndustry procreditMortgageIndustry,PawnItemsList pawnItemsList);
	
	public List getIndustryByObjectType(int mortgageid,String objectType);
}
