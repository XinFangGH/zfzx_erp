package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.finance.SlAccruedDao;
import com.zhiwei.credit.model.creditFlow.finance.SlAccrued;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlAccruedDaoImpl extends BaseDaoImpl<SlAccrued> implements SlAccruedDao{

	public SlAccruedDaoImpl() {
		super(SlAccrued.class);
	}

	@Override
	public List<SlAccrued> wslist(Long projectId, String businessType) {
		String hql="from SlAccrued q  where 1=1";
		String strs=ContextUtil.getLoginCompanyId().toString();
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		hql+=" and q.projectId="+projectId+" and q.businessType='"+businessType+"' order by q.accruedDate asc";
		return findByHql(hql.toString());
	}

}