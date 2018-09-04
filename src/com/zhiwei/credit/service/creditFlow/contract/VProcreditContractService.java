package com.zhiwei.credit.service.creditFlow.contract;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.admin.ContractElement;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;

public interface VProcreditContractService extends BaseService<VProcreditContract> {
    public List<VProcreditContract> findList(String projectId,String businessType);
    public List<VProcreditContract> getList(String projectId,String businessType,String htType);
    public List<VProcreditContract> getList(String projectId,String businessType,String htType,Integer categoryId);
    public List<VProcreditContract> getMortgageContract(Integer mortgageId);
    public List<VProcreditContract> getContractList(String businessType,String projectName,String projectNum,String contractNum,String searchRemark,String companyId,PagingBean pb);
    public List<ProcreditContract> getCategoryList(String projectId,String businessType,String htType,Long slSuperviseRecordId);
    public List<ProcreditContract> getContractList(Integer categoryId);
    public List<VProcreditContract> listByHtType(String businessType, String projId,String htType);
	public List<VProcreditContract> getThirdContractCategoryTree(String businessType,String projId, String htType, String mortgageId);
	public List<VProcreditContract> getListByHtType(String businessType, String projId,String htType);
	public List<VProcreditContract> getSlauseContractCategoryTree(String businessType,
			String projId, String htType, boolean isApply, boolean isUpdate,
			String clauseId);
	public void seeContract(String contractId);
	public void deleteContractRecordByMortgageId(String url, String id,
			String businessType, String contractType, String projId);
	public List<ContractElement> getListByCategoryId(Integer categoryId);
	public void deleteContractRecord(String url, String id);
	public void deleteContractRecordFor(String url, String projId,
			String businessType, String htType);
	public List<VProcreditContract> getByIsApply(String projId,
			String businessType, String htType, boolean isApply);
	public void deleteContractByProject(String projId,
			String businessType, String htType, boolean isApply);
	public void getAllThirdContractByProjectId(String projId,
			String businessType, String htType);
	public List<VProcreditContract> getVProcreditContractCategoryList(int mortgageId,
			String businessType, String contractType);
	public VProcreditContract getById(Integer id);
	public List<VProcreditContract> listByProjId(String projectId, String businessType);
	public Boolean deleteAllReportByProjectIdAndBusinessType(String url,
			Long projectId, String businessType);
	public Boolean deleteAllContractByProjectIdAndBusinessType(String url,
			Long projectId, String businessType);
	public List<VProcreditContract> listByTemplateId(String projId,Integer templateId);
	public List<VProcreditContract> listByHtType(String businessType,
			String projId, String htType,String type) ;
}