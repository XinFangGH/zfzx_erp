package com.credit.proj.mortgage.morservice.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryFinance;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;

@SuppressWarnings("all")
public interface MortgageService {
	
	

	//异步添加记录//
	public void addMortgage(ProcreditMortgage procreditMortgage) throws Exception ;

	//查询记录列表
	public void queryMortgage(String assureofname,int typeid,String projnum,int statusid,String enterprisename,double finalpriceOne,double finalpriceTow,int start ,int limit,String sort,String dir) throws Exception ;
	
	//查看详情-- 从视图(小额)显示在详情页面
	public VProcreditDictionary seeMortgage(int id) throws Exception;
	
	//查看详情-- 从视图(担保)显示在详情页面
	public VProcreditDictionaryGuarantee seeMortgageGuarantee(int id) throws Exception;
	
	//查看详情-- 从视图(融资)显示在详情页面
	public VProcreditDictionaryFinance seeMortgageFinance(int id) throws Exception;
	
	
	//获取融资租赁 抵质押物信息(VProcreditMortgageLeaseFinance)   add by gaoqingrui 
	public VProcreditMortgageLeaseFinance seeMortgageLeaseFinance(int id) throws Exception;
	
	//查看详情-- 从反担保主表,车辆表显示在更新页面
	public ProcreditMortgage seeMortgage(Class entityClass,Serializable id) throws Exception;
	
	//异步更新记录
	public void updateMortgage(int mortgageid,ProcreditMortgage procreditMortgage) throws IOException;
	//合同生成用到的更新反担保 add by chencc
	public void updateMortgageById(int mortgageid,ProcreditMortgage procreditMortgage) throws IOException;
	//更新记录
	public boolean updateMortgages(ArrayList entities) throws Exception;
	
	//删除记录
	//public boolean deleteMortgage(ProcreditMortgage procreditMortgage) throws Exception;
	//删除主表记录
	public boolean deleteMortgage(Class entityClass,int id) throws Exception;
	
	//根据主表id删除对应表的记录
	public boolean deleteObjectDatas(int typeid,int id,String objectType) throws Exception;
	
	//为删除主表,次表添加事务的删除方法
	public void deleteAllObjectDatas(Class entityClass,String mortgageIds) throws Exception;
	
	//节点26 修改页面显示数据的查询方法
	public void ajaxMortgageManageSeeNode26(int id);
	
	//节点26 修改的方法
	public void ajaxUpdateMortgageManageNode26(ProcreditMortgage procreditMortgage,int mortgageid,int beforeOrMiddleType);
	
	//反担保列表根据项目id查询主表及子表所有数据
	public List ajaxGetMortgageAllDataByProjectId(Long projectId,String businessType,String htType);
	
	//小额贷款归档确认节点修改抵质押物信息
	public ProcreditMortgage updateProcreditMortgage(int mortgageid,ProcreditMortgage procreditMortgage);
	
	//抵质押物归档确认
	public void archiveConfirm(int mortgageid,ProcreditMortgage procreditMortgage)throws IOException;
	
	//根据业务种类和项目id获取抵质押物主表信息
	public List getByBusinessTypeProjectId(String businessType,Long projectId);
	
	//融资项目添加抵质押物更新抵质押物表的isPledged状态。
	public void updateIsPledged(int mortgageId,short isPledged);
	
	//获取融资抵质押物信息-左侧菜单(我方抵质押物：是否已抵押的都显示isPledged=0,1)、融资项目信息节点(抵质押物参照：未抵押isPledged=0)
	public List getMortgageFinance(String isPledged,String mortgageType,String mortgageName,String finalpriceOne,String finalpriceTow,int start,int limit);
	
	
	//融资抵质押物假删除
	public void deleteMortgageFalse(String mortgageIds);
	//抵质押物管理
	public List getMortgageList(String businessType,String mortgagename,String projectName,String projectNum,String mortgageType,String danbaoType,String minMoney,String maxMoney,int start,int limit,int status,String mortgageStatus,String companyId);
	//根据所有权人类型和Id查询
	public List<ProcreditMortgage> getList(int personType,int assureofname);
	
	public List getMortgageOfDZ(Long projectId,String businessType);
	
	public List getMortgageOfBZ(Long projectId,String businessType);
	
	public List getMortgageFinanceList(String isPledged,String type,int start,int limit);
	
	public Long getMortgageFinanceCount(String isPledged,String type);
	
	public List<ProcreditMortgage> getTransactMortgage(Date date,Long companyId);
	
	public List<ProcreditMortgage> getUnchainMortgage(Date date,Long companyId);

	public void deleteByMortgageIds(Long valueOf);

	/**
	 * 根据项目id查询该项目下所有抵质押物信息
	 * @param projectId
	 * @return
	 * @HT_version3.0
	 */
	public List<ProcreditMortgage> getByProjectId(Long projectId);
}
