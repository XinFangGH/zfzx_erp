package com.zhiwei.credit.dao.creditFlow.financingAgency.persion.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.PlPersionDirProKeepDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlPersionDirProKeepDaoImpl extends BaseDaoImpl<PlPersionDirProKeep> implements PlPersionDirProKeepDao{

	public PlPersionDirProKeepDaoImpl() {
		super(PlPersionDirProKeep.class);
	}

	@Override
	public PlPersionDirProKeep getByType(String type, Long id) {
		String hql="from PlPersionDirProKeep as k where 1=1";
		if(null!=type && type.equals("P_Dir")){
			hql=hql+" and k.bpPersionDirPro.pdirProId="+id;
		}
		if(null!=type && type.equals("P_Or")){
			hql=hql+" and k.bpPersionOrPro.porProId="+id;
		}
		return (PlPersionDirProKeep) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult();
	}

}