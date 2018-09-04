package com.credit.proj.mortgage.education.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageEducation;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface EducationService {

	//异步添加记录
	public void addEducation(ProcreditMortgageEducation procreditMortgageEducation,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeEducationForUpdate(int id) throws Exception;
	
	//查看详情从教育用地表
	@SuppressWarnings("unchecked")
	public List seeEducation(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateEducation(int mortgageid,ProcreditMortgageEducation procreditMortgageEducation,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List seeEducationByObjectType(int mortgageid,String objectType);
	
	public void addPawnEducation(ProcreditMortgageEducation procreditMortgageEducation,PawnItemsList pawnItemsList);
	
	public List getEducationByObjectType(int mortgageid,String objectType);
}
