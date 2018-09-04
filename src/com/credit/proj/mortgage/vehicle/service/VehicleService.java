package com.credit.proj.mortgage.vehicle.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageCar;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

@SuppressWarnings("all")
public interface VehicleService {

	//添加记录
	public void addVehicle(ProcreditMortgageCar procreditMortgageCar,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	public List seeVehicleForUpdate(int id) throws Exception;
	
	//查看详情--从车辆表显示在更新页面
	public List seeVehicle(int mortgageid) throws Exception;
	
	
	//更新记录
	public void updateVehicle(int mortgageid,ProcreditMortgageCar procreditMortgageCar,ProcreditMortgage procreditMortgage) throws Exception;

	//制造商查询
	public void ajaxQueryCarFactoryForCombo(String query,int start,int limit) ;
	
	//根据制造商id查询相应信息返回json数据
//	public void ajaxGetProcessCarData(int manufacturer);
	
	//根据制造商id查询相应实体对象信息，供多方法调用
	//public VCarDic getProcessCarDataById(int manufacturer);
	
	public List getVehicleByObjectType(int mortgageid,String objectType);
	
	public void addPawnVehicle(ProcreditMortgageCar procreditMortgageCar,PawnItemsList pawnItemsList);
	
	public List seeVehicleByObjectType(int mortgageid,String objectType);
}
