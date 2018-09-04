package com.zhiwei.credit.dao.creditFlow.contract;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.admin.ContractElement;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;

public interface VProcreditContractDao extends BaseDao<VProcreditContract>{
    public List<VProcreditContract> findList(String projectId,String businessType);
    public List<VProcreditContract> getList(String projectId,String businessType,String htType);
    public List<VProcreditContract> getMortgageContract(Integer mortgageId);
    public List<VProcreditContract> getContractList(String businessType,String projectName,String projectNum,String contractNum,String searchRemark,String companyId,PagingBean pb);
    public List<VProcreditContract> findListdbhtbh(String projectId);//担保合同编号
    public List<ProcreditContract> getCategoryList(String projectId,String businessType,String htType,Long slSuperviseRecordId);
    public List<ProcreditContract> getContractList(Integer categoryId);
	public List<VProcreditContract> getList(String projectId,
			String businessType, String htType, Integer categoryId);
	public List<VProcreditContract> listByHtType(String businessType, String projId,String htType);
	public List<VProcreditContract> getThirdContractCategoryTree(String businessType,String projId, String htType, String mortgageId);
	public List<VProcreditContract> getListByHtType(String businessType, String projId,String htType);
	public List<VProcreditContract> getSlauseContractCategoryTree(String businessType,
			String projId, String htType, boolean isApply, boolean isUpdate,
			String clauseId);
	public List<ContractElement> getListByCategoryId(Integer categoryId);
	public List<VProcreditContract> getByIsApply(String projId,
			String businessType, String htType, boolean isApply);
	public List<VProcreditContract> getVProcreditContractCategoryList(int mortgageId,
			String businessType, String contractType);
	public VProcreditContract getById(Integer id);
	public List<VProcreditContract> listByProjId(String projectId, String businessType);
	public List<VProcreditContract> listByTemplateId(String projId,Integer templateId);
	public List<VProcreditContract> listByHtType(String businessType,
			String projId, String htType,String type) ;
}
