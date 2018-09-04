package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlMmOrderChildrenOrDaoImpl extends BaseDaoImpl<PlMmOrderChildrenOr> implements PlMmOrderChildrenOrDao{

	public PlMmOrderChildrenOrDaoImpl() {
		super(PlMmOrderChildrenOr.class);
	}

	
	@Override
	public List<PlMmOrderChildrenOr> listupdate(String searchDate) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where f.matchingState=0 ");
		if(null !=searchDate&&!searchDate.equals("")){
		hql.append(" and f.matchingEndDate <= '"+searchDate+"'");
		}
		return findByHql(hql.toString());
			
	}

	@Override
	public List<PlMmOrderChildrenOr> listbysearch(PagingBean pb,
			Map<String, String> map) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where 1=1");
		String matchingStartDate=map.get("Q_matchingStartDate_D_EQ");
		String matchingEndDate=map.get("Q_matchingEndDate_D_EQ");
		String keystr=map.get("keystr");
		String orderId=map.get("orderId");
		String investPersonId=map.get("investPersonName");
		
		
		if(null !=investPersonId&&!investPersonId.equals("")){
			hql.append(" and f.investPersonName like '%"+investPersonId+"%'");
			}
		/*if(null !=keystr&&!keystr.equals("")){
			hql.append(" and f.keystr ='"+keystr+"'");
			}*/
		if(null !=orderId&&!orderId.equals("")){
		hql.append(" and f.orderId ="+orderId);
		}
		if(null !=matchingStartDate&&!matchingStartDate.equals("")){
			hql.append(" and f.matchingEndDate >= '"+matchingStartDate+"'");
		}
		if(null !=matchingEndDate&&!matchingEndDate.equals("")){
			hql.append(" and f.matchingStartDate <= '"+matchingEndDate+"'");
		}
		hql.append(" order by f.matchingStartDate asc");
		if(null==pb){
			return findByHql(hql.toString());
			
		}else{
			return findByHql(hql.toString(),null,pb);
		}
	
	}

	@Override
	public List<PlMmOrderChildrenOr> listbyorderid(Long orderId,String enddate) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where f.matchingState=0 and f.orderId="+orderId+" and f.matchingEndDate > '" +enddate+"'");
		return findByHql(hql.toString());
	}

	@Override
	public List<PlMmOrderChildrenOr> listbychildrenorId(Long childrenorId) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where f.matchingState=0 and f.childrenorId="+childrenorId);
		return findByHql(hql.toString());
	}

	@Override
	public List<PlMmOrderChildrenOr> listbyIds(String ids) {
		if(ids!=null && !"".equals(ids)){
			StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where f.matchId in ( "+ids+")");
			return findByHql(hql.toString());
		}else{
			return null;
		}
		
	}

	@Override
	public List<PlMmOrderChildrenOr> listbysame(String matchStartDate,
			String endStartDate, Long orderId,Long childrenorId) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where f.matchingStartDate='"+matchStartDate
				+"' and f.matchingEndDate='"+endStartDate+"' and f.orderId="+orderId + " and f.childrenorId="+childrenorId);
		
		return findByHql(hql.toString());
	}

	@Override
	public List<PlMmOrderChildrenOr> listbyorderid(Long orderId) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where f.matchingState=0 and f.orderId="+orderId);
		return findByHql(hql.toString());
	}
	
	@Override
	public List<PlMmOrderChildrenOr> listbyChildrenorId(Long childrenorId) {
		StringBuffer hql = new StringBuffer("from PlMmOrderChildrenOr f where f.matchingState=0 and f.childrenorId="+childrenorId);
		return findByHql(hql.toString());
	}

}