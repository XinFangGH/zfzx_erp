package com.zhiwei.credit.dao.creditFlow.finance.impl;

import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.FundIntentDao;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;

public class FundIntentDaoImpl extends BaseDaoImpl<FundIntent> implements FundIntentDao {

	public FundIntentDaoImpl() {
		super(FundIntent.class);
	}

	@Override
	public List<FundIntent> getList(String pname, String[] names, Object[] values) {
		String hql = " from "+pname+ "  c where 1 =1 ";
		if(names.length!=0){
			for(int i=0; i<names.length;i++){
				hql += " and c."+names[i]+" = ? ";
			}
		}
		return super.findByHql(hql, values);
	}
	@Override
	public List<FundIntent> listNoEarlyId(Long bidPlanId,
			Long slEarlyRepaymentId) {
		String hql="from BpFundIntent as f where f.bidPlanId=? and (slEarlyRepaymentId is null or slEarlyRepaymentId!=?)";
		return this.getSession().createQuery(hql).setParameter(0, bidPlanId).setParameter(1, slEarlyRepaymentId).list();
	}
}
