package com.zhiwei.credit.service.creditFlow.contract;

import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;

public interface ProcreditContractService extends BaseService<ProcreditContract> {
	public ProcreditContract getById(Integer id);
	public void batchQSContract(String categoryIds);
	public int makeUpload(String contractId, String templateId,
			String contractType, String categoryId, String projId,
			Long thirdRalationId, String businessType, String rnum,
			String searchRemark, String contractNumber) throws Exception;
	public int makeUpload(String contractId, String templateId,
			String contractType, String categoryId, String projId,
			Long thirdRalationId, String businessType, String rnum,
			String searchRemark) throws Exception;
	public void updateContractByIdSql(int contractId);
	public List<ProcreditContract> listByTemplateId(String projId,Integer temptId);
	public List<ProcreditContract> getProcreditContractsList(String projectId);
	public Integer add(ProcreditContract procreditContract,String projId, String businessType);
	public List<ProcreditContract>  getListByPIdAndBT(Long valueOf, String businessType);
	public Integer getContractCountByNOLk(String contractNo);
	public List<ProcreditContract> listByBidPlanId(Long bidPlanId, String htType);
	
	
	public    List<ProcreditContract>  findById(Integer  id);
	public Long countByBidId(Long bidPlanId);
	public List<ProcreditContract> getByPlanId(Long bidPlanId);
	
	/**
	 * 向合同表中添加数据
	 * @param  map 参数集合
	 * @HT_version3.0
	 */
	public int newMakeUpload(Map<String, Object> mapDb);
	
	/**
	 * 修改合同的的名称
	 * @param  map 参数集合
	 * @param  pcontract 合同对象
	 * @HT_version3.0
	 */
	public void newMakeUpload(Map<String, Object> mapDb,ProcreditContract pcontract);
	/**
	 * 根据项目id和合同类别查询合同记录数
	 * @param bidPlanId
	 * @param htType
	 * @return
	 */
	public List<ProcreditContract> findListByBidPlanId(Long bidPlanId,String htType);
}