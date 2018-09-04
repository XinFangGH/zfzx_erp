package com.zhiwei.credit.dao.creditFlow.financingAgency.business.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.BpBusinessDirProDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpBusinessDirProDaoImpl extends BaseDaoImpl<BpBusinessDirPro> implements BpBusinessDirProDao{

	public BpBusinessDirProDaoImpl() {
		super(BpBusinessDirPro.class);
	}

	@Override
	public BpBusinessDirPro getByBpFundProjectId(Long id) {
		String hql  = "from BpBusinessDirPro bp where bp.proId = ?";
		List list = new ArrayList();
		list = super.findByHql(hql, new Object[]{id});
		if(list==null||list.size()==0){
			return null;
		}
		return (BpBusinessDirPro) list.get(0);
	}

	@Override
	public BpBusinessDirPro getByMoneyPlanId(Long id) {
		String hql  = "from BpBusinessDirPro bp where bp.moneyPlanId = ?";
		List list = new ArrayList();
		list = super.findByHql(hql, new Object[]{id});
		if(list==null||list.size()==0){
			return null;
		}
		return (BpBusinessDirPro) list.get(0);
	}
	@Override
	public BpBusinessDirPro getByProjectId1(Long projId,String businessType){
		String hql  = "from BpBusinessDirPro bp where bp.proId = ? and bp.businessType=?";
		List list = new ArrayList();
		list = super.findByHql(hql, new Object[]{projId,businessType});
		if(list==null||list.size()==0){
			return null;
		}
		return (BpBusinessDirPro) list.get(0);
	}
}