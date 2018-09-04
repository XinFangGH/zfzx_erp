package com.zhiwei.credit.dao.creditFlow.financingAgency.persion.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.BpPersionDirProDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpPersionDirProDaoImpl extends BaseDaoImpl<BpPersionDirPro> implements BpPersionDirProDao{

	public BpPersionDirProDaoImpl() {
		super(BpPersionDirPro.class);
	}

	@Override
	public BpPersionDirPro getByBpFundProjectId(Long id) {
		String hql="from BpPersionDirPro as s where s.moneyPlanId=?";
		List<BpPersionDirPro> list=getSession().createQuery(hql).setParameter(0,id).list();
		BpPersionDirPro s=null;
		if(null!=list && list.size()>0){
			s=list.get(0);
		}
		return s;
	}

	@Override
	public BpPersionDirPro getByMoneyPlanId(Long id) {
		String hql="from BpPersionDirPro as s where s.moneyPlanId=?";
		List<BpPersionDirPro> list=getSession().createQuery(hql).setParameter(0,id).list();
		BpPersionDirPro s=null;
		if(null!=list && list.size()>0){
			s=list.get(0);
		}
		return s;
	}
	@Override
	public BpPersionDirPro getByProjectId1(Long projId,String businessType){
		String hql  = "from BpPersionDirPro bp where bp.proId = ? and bp.businessType=?";
		List list = new ArrayList();
		list = super.findByHql(hql, new Object[]{projId,businessType});
		if(list==null||list.size()==0){
			return null;
		}
		return (BpPersionDirPro) list.get(0);
	}

}