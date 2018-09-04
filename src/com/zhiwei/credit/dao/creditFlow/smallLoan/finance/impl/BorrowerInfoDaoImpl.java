package com.zhiwei.credit.dao.creditFlow.smallLoan.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.BorrowerInfoDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.BorrowerInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BorrowerInfoDaoImpl extends BaseDaoImpl<BorrowerInfo> implements BorrowerInfoDao{

	public BorrowerInfoDaoImpl() {
		super(BorrowerInfo.class);
	}

	@Override
	public List<BorrowerInfo> getBorrowerList(Long projectId) {
		String hql="from BorrowerInfo as b where b.projectId=?";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}
	@Override
	public List<BorrowerInfo> getBorrowerListDetail(Long projectId){
		List<BorrowerInfo> list= this.getBorrowerList(projectId);
		if(list ==null||list.size()==0)return list;
		EnterpriseDao ed = (EnterpriseDao)AppUtil.getBean("enterpriseDao");
		PersonDao pd = (PersonDao)AppUtil.getBean("personDao");
		for(BorrowerInfo bi : list){
			if(bi.getType()==0){
				Enterprise enter = ed.getById(bi.getCustomerId());
				bi.setCustomerName(enter.getEnterprisename());
			}else{
				Person person = pd.getById(bi.getCustomerId());
				bi.setCustomerName(person.getName());
			}
		}
		return list;
	}

}