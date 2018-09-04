package com.zhiwei.credit.dao.creditFlow.contract;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;

public interface ProcreditContractDao extends BaseDao<ProcreditContract>{
	public ProcreditContract getById(Integer id);
	public List<ProcreditContract> listByTemplateId(String projId,Integer temptId);
	public List<ProcreditContract> getProcreditContractsList(String projectId);
	public List<ProcreditContract> getListByPIdAndBT(Long projectId, String businessType);
	public Integer getContractCountByNOLk(String contractNo);
	public List<ProcreditContract> listByBidPlanId(Long bidPlanId,String htType);
	
//	public	 List<ProcreditContract> listByBidPlanId(Integer id,String htType);
	public List<ProcreditContract> listById(Integer id);
	public Long countByBidId(Long bidPlanId);
	public List<ProcreditContract> getByPlanId(Long bidPlanId);
	public List<ProcreditContract> findListByBidPlanId(Long bidPlanId,String htType);
}