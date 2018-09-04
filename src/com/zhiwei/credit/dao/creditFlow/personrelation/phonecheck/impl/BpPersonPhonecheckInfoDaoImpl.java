package com.zhiwei.credit.dao.creditFlow.personrelation.phonecheck.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfoDao;
import com.zhiwei.credit.model.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpPersonPhonecheckInfoDaoImpl extends BaseDaoImpl<BpPersonPhonecheckInfo> implements BpPersonPhonecheckInfoDao{

	public BpPersonPhonecheckInfoDaoImpl() {
		super(BpPersonPhonecheckInfo.class);
	}

	@Override
	public BpPersonPhonecheckInfo getPhoneCheck(String projectId,int id) {
		String hql="from BpPersonPhonecheckInfo p where p.projectId="+Long.valueOf(projectId)+" and p.personRelationId="+id;
		List<BpPersonPhonecheckInfo> list=this.getSession().createQuery(hql).list();
		if(null!=list&&list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public BpPersonPhonecheckInfo getByPersonRelationId(Integer personRelationId) {
		String hql="from BpPersonPhonecheckInfo p where p.personRelationId="+personRelationId;
		return (BpPersonPhonecheckInfo) this.getSession().createQuery(hql).uniqueResult();
	}

	@Override
	public BpPersonPhonecheckInfo getByPersonRelationId(Integer id,
			Long projectId) {
		String hql="from BpPersonPhonecheckInfo p where p.personRelationId="+id+" and p.projectId="+projectId;
		return (BpPersonPhonecheckInfo) this.getSession().createQuery(hql).uniqueResult();
	}

}