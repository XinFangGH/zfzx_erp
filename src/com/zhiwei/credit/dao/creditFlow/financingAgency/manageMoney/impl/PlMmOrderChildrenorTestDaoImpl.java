
package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTest;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlMmOrderChildrenorTestDaoImpl extends BaseDaoImpl<PlMmOrderChildrenorTest> implements PlMmOrderChildrenorTestDao{

	public PlMmOrderChildrenorTestDaoImpl() {
		super(PlMmOrderChildrenorTest.class);
	}

	@Override
	public List<PlMmOrderChildrenorTest> listupdate(String searchDate, Long orderId) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenorTest f where f.matchingState=0 and f.orderId="+orderId);
		if(null !=searchDate&&!searchDate.equals("")){
		hql.append(" and f.matchingEndDate <= '"+searchDate+"'");
		}
		return findByHql(hql.toString());
			
	}

}