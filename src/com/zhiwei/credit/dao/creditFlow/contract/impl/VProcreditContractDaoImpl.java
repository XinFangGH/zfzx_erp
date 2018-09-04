package com.zhiwei.credit.dao.creditFlow.contract.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.contract.VProcreditContractDao;
import com.zhiwei.credit.model.admin.ContractElement;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;

@SuppressWarnings("unchecked")
public class VProcreditContractDaoImpl extends BaseDaoImpl<VProcreditContract> implements VProcreditContractDao{

	public VProcreditContractDaoImpl() {
		super(VProcreditContract.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<VProcreditContract> findList(String projectId,
			String businessType) {
		String hql="from VProcreditContract as v where v.projId=? and v.businessType=? and  (v.htType='simulateEnterpriseBook' or v.htType='assureCommitmentLetter' or v.htType='partnerMeetingResolution') ";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public List<VProcreditContract> getList(String projectId,
			String businessType, String htType) {
		if(htType==null){
			String hql="from VProcreditContract as v where v.projId=? and v.businessType=? and v.htType is null";
			return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
		}else{
			/*if(htType.equals("thirdContract")){
				String hql="from VProcreditContractCategory as v where v.projId=? and v.businessType=? and (v.htType='thirdContract' or v.htType='baozContract') order by v.mortgageId asc";
			    return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
			}else{*/
				String hql="from VProcreditContract as v where v.projId=? and v.businessType=? and v.htType in "+htType+" order by v.mortgageId asc";
			    return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
			//}
		}
		
	}

	@Override
	public List<VProcreditContract> getMortgageContract(
			Integer mortgageId) {
		String hql="from VProcreditContract as v where v.mortgageId=?";
		return getSession().createQuery(hql).setParameter(0, mortgageId).list();
	}

	@Override
	public List<VProcreditContract> getContractList(
			String businessType, String projectName, String projectNum,
			String contractNum,String searchRemark, String companyId, PagingBean pb) {
		StringBuffer hql=new StringBuffer("from VProcreditContract as v where v.businessType=?");
		String objectName="";
		if(businessType.equals("SmallLoan")){
			objectName="BpFundProject";
		}
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !companyId.equals("")){
			strs=companyId;
		}
		if(null!=strs && !strs.equals("")){
			hql.append(" and v.projId in (select s.id from "+objectName+" as s where s.companyId in("+strs+")");
		}else{
			hql.append(" and v.projId in (select s.id from "+objectName+" as s where 1=1");
		}
		if(null!=projectName && null!=projectNum){
			hql.append(" and s.projectName like '%"+projectName+"%' and s.projectNumber like '%"+projectNum+"%')");
		}
		if(null!=projectName && null==projectNum){
			hql.append(" and s.projectName like '%"+projectName+"%')");
		}
		if(null==projectName && null!=projectNum){
			hql.append(" and s.projectNumber like '%"+projectNum+"%')");
		}
		if(null==projectName && null==projectNum){
			hql.append(")");
		}
		if(null!=contractNum){
			hql.append(" and v.contractNumber like '%"+contractNum+"%'");
		}
		if(null!=searchRemark){
			hql.append(" and v.contractNumber like '%"+searchRemark+"%'");
		}
		return this.findByHql(hql.toString(), new Object[]{businessType}, pb);
	}

	@Override
	public List<VProcreditContract> findListdbhtbh(String projectId) {
		String hql="from VProcreditContract as v where v.projId=?  and  v.mortgageId !=0 ";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}
	@Override
	public List<ProcreditContract> getCategoryList(String projectId, String businessType, String htType,
			Long slSuperviseRecordId) {
		String hql="from ProcreditContract as p where p.projId=? and p.businessType=? and p.htType=? and p.isApply=1 and p.clauseId=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, htType).setParameter(3, slSuperviseRecordId).list();
	}

	@Override
	public List<ProcreditContract> getContractList(Integer categoryId) {
		String hql="from ProcreditContract as p where p.categoryId=?";
		return getSession().createQuery(hql).setParameter(0, categoryId).list();
	}

	@Override
	public List<VProcreditContract> getList(String projectId,
			String businessType, String htType, Integer categoryId) {
		String hql= "";
		if(htType==null){
		   hql="from VProcreditContract as v where v.projId=? and v.businessType=? and v.htType is null";
		   return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
		}else{
		   if(null !=categoryId){
			   hql="from VProcreditContract as v where v.projId=? and v.businessType=? and v.htType=? and v.id=? order by v.mortgageId asc";
			   return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, htType).setParameter(3, categoryId).list();
		   }else{
			   hql="from VProcreditContract as v where v.projId=? and v.businessType=? and v.htType=? order by v.mortgageId asc";
		       return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, htType).list();
		   }
		}
	}

	@Override
	public List<VProcreditContract> listByHtType(String businessType,
			String projId, String htType) {
		String hql = "";
		List<VProcreditContract> list=null;
		if (htType == null || htType.equals("")) {
			hql = "from VProcreditContract a where a.projId=? and a.businessType=? and a.htType is null order by a.id ASC";
			Object[] object = { projId, businessType };
			
			list =this.findByHql(hql, object);
		} else {
			String htTypeStr = "";
			if (htType.equals("loanContract")) {
				htTypeStr = "('loanContract','baozContract','thirdContract')";
			} else if (htType.equals("extenstionContract")) {
				htTypeStr = "('extenstionContract','baozContract','thirdContract')";
			} else if (htType.equals("guaranteeContract")) {
				htTypeStr = "('guaranteeContract','clauseContract','baozContract','thirdContract')";
			} else if (htType.equals("financingContract")) {
				htTypeStr = "('financingContract','thirdRalationContract','baozContract','thirdContract')";
			} else if (htType.equals("pawnContract")) {
				htTypeStr = "('pawnContract','dwContract')";
			} else if (htType.equals("leaseFinanceContract") || htType.equals("leaseFinanceContractExt")) {
				htTypeStr = "('leaseFinanceContract','thirdContract','baozContract','leaseFinanceContractExt')";//
			} else if (htType.equals("leaseFinanceZQHT")) {
				htTypeStr = "('leaseFinanceZQHT','thirdContract','baozContract')";//
			}else if(htType.equals("investContract")){
				htTypeStr = "('investContract')";
			}
			hql = "from VProcreditContract a where a.projId=? and a.businessType=? and a.htType in "
					+ htTypeStr + " order by a.id ASC";
			Object[] object = { projId, businessType };
			list =this.findByHql(hql, object);
		}
		return list;
	}

	@Override
	public List<VProcreditContract> getThirdContractCategoryTree(
			String businessType, String projId, String htType, String mortgageId) {
		int mortId = Integer.parseInt(mortgageId);
		Object[] obj = { mortId, businessType, projId, htType };
		String hql = "from VProcreditContract where mortgageId =? and businessType =? and projId=? and htType =?";
		return this.findByHql(hql, obj);
	}

	@Override
	public List<VProcreditContract> getListByHtType(
			String businessType, String projId, String htType) {
		String hql="from VProcreditContract where businessType =? and projId=? and htType=?";
		return this.findByHql(hql, new Object[]{businessType,projId,htType});
	}

	@Override
	public List<VProcreditContract> getSlauseContractCategoryTree(
			String businessType, String projId, String htType, boolean isApply,
			boolean isUpdate, String clauseId) {
		String hql = "";
		List<VProcreditContract> list=null;
		if (isUpdate == true && clauseId != null && !"".equals(clauseId)) {// 展期记录的更新页面合同列表
			hql = "from VProcreditContract a where  a.clauseId =? and a.htType =? and a.projId=? and a.businessType=? order by a.id ASC";
			Object[] object = { Long.parseLong(clauseId), htType, projId,
					businessType };
			
			list = this.findByHql(hql, object);
		} else {
			hql = "from VProcreditContract a where a.projId=? and a.businessType=? and a.htType =? and a.isApply =? order by a.id ASC";
			Object[] object = { projId, businessType, htType, isApply };
			
			list = this.findByHql(hql, object);
		}
		return list;
	}

	@Override
	public List<ContractElement> getListByCategoryId(Integer categoryId) {
		String hql="from ContractElement where contractId=?";
		return getSession().createQuery(hql).setParameter(0, categoryId).list();
	}

	@Override
	public List<VProcreditContract> getByIsApply(String projId,
			String businessType, String htType, boolean isApply) {
		String hql="from VProcreditContract AS vp where vp.projId = ? and vp.businessType = ? and vp.htType = ? and vp.isApply =?";
		return this.findByHql(hql, new Object[]{projId,businessType,htType,isApply});
	}

	@Override
	public List<VProcreditContract> getVProcreditContractCategoryList(
			int mortgageId, String businessType, String contractType) {
		String hql="from VProcreditContract where mortgageId =? and businessType =? and htType = ?";
		return this.findByHql(hql, new Object[]{mortgageId,businessType,contractType});
	}

	@Override
	public VProcreditContract getById(Integer id) {
		String hql="from VProcreditContract as p where p.id=?";
		return (VProcreditContract) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<VProcreditContract> listByProjId(String projectId,
			String businessType) {
		String hql="from VProcreditContract AS vp where vp.projId = ? and vp.businessType = ?";
		return this.findByHql(hql, new Object[]{projectId,businessType});
	}

	@Override
	public List<VProcreditContract> listByTemplateId(String projId,
			Integer templateId) {
		String hql="from VProcreditContract p where p.projId = ? and p.templateId = ?";
		return this.getSession().createQuery(hql).setParameter(0, projId).setParameter(1, templateId).list();
	}
	public List<VProcreditContract> listByHtType(String businessType,
			String projId, String htType,String type) {
		String hql = "";
		List<VProcreditContract> list=null;
		if (htType == null || htType.equals("")) {
				hql = "from VProcreditContract a where a.projId=? and a.businessType=? and a.htType is null order by a.id ASC";
			if(null!=type&&type.equals("bidplan")){
				hql = "from VProcreditContract a where a.bidPlanId=? and a.businessType=? and a.htType is null order by a.id ASC";
			}
			Object[] object = { projId, businessType };
			
			list =this.findByHql(hql, object);
		} else {
			String htTypeStr = "";
			if (htType.equals("loanContract")) {
				htTypeStr = "('loanContract','baozContract','thirdContract')";
			} else if (htType.equals("extenstionContract")) {
				htTypeStr = "('extenstionContract','baozContract','thirdContract')";
			} else if (htType.equals("guaranteeContract")) {
				htTypeStr = "('guaranteeContract','clauseContract','baozContract','thirdContract')";
			} else if (htType.equals("financingContract")) {
				htTypeStr = "('financingContract','thirdRalationContract','baozContract','thirdContract')";
			} else if (htType.equals("pawnContract")) {
				htTypeStr = "('pawnContract','dwContract')";
			} else if (htType.equals("leaseFinanceContract") || htType.equals("leaseFinanceContractExt")) {
				htTypeStr = "('leaseFinanceContract','thirdContract','baozContract','leaseFinanceContractExt')";//
			} else if (htType.equals("leaseFinanceZQHT")) {
				htTypeStr = "('leaseFinanceZQHT','thirdContract','baozContract')";//
			}else if(htType.equals("investContract")){
				htTypeStr = "('investContract')";
			}else{
				htTypeStr = "('"+htType+"')";
			}
			hql = "from VProcreditContract a where a.projId=? and a.businessType=? and a.htType in "
					+ htTypeStr + " order by a.id ASC";
			if(null!=type&&type.equals("bidplan")){
				hql = "from VProcreditContract a where a.bidPlanId=? and a.businessType=? and a.htType in "
					+ htTypeStr + " order by a.id ASC";
			}
			Object[] object = { projId, businessType };
			list =this.findByHql(hql, object);
		}
		return list;
	}
}
