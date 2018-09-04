package com.zhiwei.credit.dao.creditFlow.financingAgency.business.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.PlBusinessDirProKeepDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlBusinessDirProKeepDaoImpl extends BaseDaoImpl<PlBusinessDirProKeep> implements PlBusinessDirProKeepDao{

	public PlBusinessDirProKeepDaoImpl() {
		super(PlBusinessDirProKeep.class);
	}

	@Override
	public PlBusinessDirProKeep getByType(String type, Long id) {
		String hql="from PlBusinessDirProKeep as k where 1=1";
		if(null!=type && type.equals("B_Dir")){
			hql=hql+" and k.bpBusinessDirPro.bdirProId="+id;
		}
		if(null!=type && type.equals("B_Or")){
			hql=hql+" and k.bpBusinessOrPro.borProId="+id;
		}
		return (PlBusinessDirProKeep) this.getSession().createQuery(hql).uniqueResult();
	}

}