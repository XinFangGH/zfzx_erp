package com.zhiwei.credit.dao.creditFlow.contract.impl;

import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.contract.ProcreditContractDao;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;

@SuppressWarnings("unchecked")
public class ProcreditContractDaoImpl extends BaseDaoImpl<ProcreditContract> implements ProcreditContractDao{

	public ProcreditContractDaoImpl() {
		super(ProcreditContract.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcreditContract getById(Integer id) {
		String hql="from ProcreditContract as p where p.id=?";
		return (ProcreditContract) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<ProcreditContract> listByTemplateId(String projId,
			Integer temptId) {
		String hql="from ProcreditContract AS p where p.projId =? and p.templateId =?";
		return this.findByHql(hql, new Object[]{projId,temptId});
	}

	@Override
	public List<ProcreditContract> getProcreditContractsList(String projectId) {
		String hql="from ProcreditContract pc where pc.projId = ?";
		return this.findByHql(hql, new Object[]{projectId});
	}

	@Override
	public List<ProcreditContract>  getListByPIdAndBT(Long projectId, String businessType) {
		String hql="from ProcreditContract pc where pc.projId = ? and pc.businessType = ?";
		return this.findByHql(hql, new Object[]{projectId.toString(),businessType});
	}

	@Override
	public Integer getContractCountByNOLk(String contractNo) {
		String hql = "select count(*) from ProcreditContract pc where pc.contractNumber like '"+contractNo+"||%'";
		Query query = getSession().createQuery(hql);
		return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	public List<ProcreditContract> listByBidPlanId(Long bidPlanId, String htType) {
		String hql="from ProcreditContract as p where p.bidPlanId=? and p.htType in "+htType+" and p.investId is null";
		return this.findByHql(hql, new Object[]{bidPlanId,htType});
	}
	
	public	List<ProcreditContract> listById(Integer id){
		
		String hql="from ProcreditContract as p where p.id=?";
		return this.findByHql(hql, new Object[]{ id});
	}

	@Override
	public Long countByBidId(Long bidPlanId) {
		String hql="select count(*) from ProcreditContract as p where p.bidPlanId=?";
		List list=this.findByHql(hql,new Object[]{bidPlanId});
		Long count=0l;
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				count=(Long) list.get(0);
			}
		}
		return count;
	}

	@Override
	public List<ProcreditContract> getByPlanId(Long bidPlanId) {
		String hql="from ProcreditContract as p where p.bidPlanId=?";
		return this.findByHql(hql, new Object[]{bidPlanId});
	}

	@Override
	public List<ProcreditContract> findListByBidPlanId(Long bidPlanId,String htType) {
		String hql="from ProcreditContract as p where p.projId=? and p.htType=? ";
		return this.findByHql(hql, new Object[]{bidPlanId.toString(),htType});
	}

}