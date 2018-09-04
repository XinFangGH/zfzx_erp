package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlDataListDao;
import com.zhiwei.credit.model.creditFlow.finance.SlDataList;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlDataListDaoImpl extends BaseDaoImpl<SlDataList> implements SlDataListDao{

	public SlDataListDaoImpl() {
		super(SlDataList.class);
	}

	@Override
	public List<SlDataList> getListByType(String type, String companyId,
			String startDate, String endDate, String sendPersonId,PagingBean pb) {
		String hql="from SlDataList as s where s.type=?";
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !companyId.equals("")){
			strs=companyId;
		}
		if(strs!=null && !strs.equals("")){
			hql=hql+" and s.companyId in ("+strs+")";
		}
		if(null!=startDate && !"".equals(startDate)&& null!=endDate && !"".equals(endDate)){
			hql=hql+" and s.sendTime between '"+startDate+"' and '"+endDate+"'";
		}
		if(null!=sendPersonId && !"".equals(sendPersonId)){
			hql=hql+" and s.sendPersonId="+Long.valueOf(sendPersonId);
		}
		hql+="order by s.dayDate desc";
		return this.findByHql(hql, new Object[]{type}, pb);
	}

	@Override
	public Date getMaxDate(String type) {
		String hql="select max(s.dayDate)from SlDataList as s where s.type=?";
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and s.companyId in ("+strs+")";
		}
		Date date=null;
		List list=getSession().createQuery(hql).setParameter(0, type).list();
		if(null!=list && list.size()>0){
			date=(Date)list.get(0);
		}
		return date;
	}

}