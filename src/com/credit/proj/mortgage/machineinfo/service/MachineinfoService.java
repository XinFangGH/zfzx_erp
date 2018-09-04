package com.credit.proj.mortgage.machineinfo.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageMachineinfo;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface MachineinfoService {

	//异步添加记录
	public void addMachineinfo(ProcreditMortgageMachineinfo procreditMortgageMachineinfo,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	public List seeMachineinfoForUpdate(int id) throws Exception;
	
	//查看详情从机器设备表
	@SuppressWarnings("unchecked")
	public List seeMachineinfo(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateMachineinfo(int mortgageid,ProcreditMortgageMachineinfo procreditMortgageMachineinfo,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List getMachineinfoByObjectType(int mortgageid,String objectType);
	
	public void addPawnMachineinfo(ProcreditMortgageMachineinfo procreditMortgageMachineinfo,PawnItemsList pawnItemsList);
	
	public List seeMachineinfoByObjectType(int mortgageid,String objectType);
}
