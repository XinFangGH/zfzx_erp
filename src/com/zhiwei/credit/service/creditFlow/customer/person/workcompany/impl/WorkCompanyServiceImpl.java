package com.zhiwei.credit.service.creditFlow.customer.person.workcompany.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.workcompany.WorkCompanyDao;
import com.zhiwei.credit.model.creditFlow.customer.person.workcompany.WorkCompany;
import com.zhiwei.credit.service.creditFlow.customer.person.workcompany.WorkCompanyService;

/**
 * 
 * @author 
 *
 */
public class WorkCompanyServiceImpl extends BaseServiceImpl<WorkCompany> implements WorkCompanyService{
	private WorkCompanyDao dao;
	
	public WorkCompanyServiceImpl(WorkCompanyDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public WorkCompany getWorkCompanyByPersonId(int personId) {
		String hql = "from WorkCompany  as w where w.personId = ?";
		List<WorkCompany> list = new ArrayList<WorkCompany>();
		list = dao.findByHql(hql, new Object[]{personId});
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

}