package com.zhiwei.credit.dao.creditFlow.ourmain.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/

import java.util.ArrayList;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.ourmain.SlCompanyMainDao;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;


@SuppressWarnings("unchecked")
public class SlCompanyMainDaoImpl extends BaseDaoImpl<SlCompanyMain> implements SlCompanyMainDao{

	public SlCompanyMainDaoImpl() {
		super(SlCompanyMain.class);
	}

	@Override
	public List<SlCompanyMain> findCompanyList(String corName) {
		String hql="from SlCompanyMain as slCompanyMain where slCompanyMain.corName=?";
		return findByHql(hql,new Object[]{corName});
	}

	/**
	 * 获取我方企业主体
	 * @param corName
	 * @param lawName
	 * @param organizeCode
	 * @param PagingBean
	 * @return
	 */
	public List<SlCompanyMain> findCompanyListByIsPledge(String corName,String lawName,String organizeCode,PagingBean pb,String companyId){
		
		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		//hql.append("from SlCompanyMain sc where sc.isPledge in (").append(isPledge).append(")");
		hql.append("from SlCompanyMain sc where sc.isPledge=0");
		if(null!=strs && !"".equals(strs)){
            hql.append(" and sc.companyId in ("+strs+")");
		}
		//第一次查询时候为null,把查询条件的值去掉后为""或者"null",多重并列判断。
		if(corName!=null&&!"".equals(corName)&&!"null".equals(corName)){
			hql.append(" and sc.corName like ? ");
			params.add("%" + corName + "%");
		}
		
		if(lawName!=null&&!"".equals(lawName)&&!"null".equals(lawName)){
			hql.append(" and sc.lawName like ? ");
			params.add("%" + lawName + "%");
		}
		
		if(organizeCode!=null&&!"".equals(organizeCode)&&!"null".equals(organizeCode)){
			hql.append(" and sc.organizeCode like ? ");
			params.add("%" + organizeCode + "%");
		}
		
		hql.append(" order by sc.companyMainId desc");
		
		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 获取我方企业主体-参照
	 * @param corName
	 * @param PagingBean
	 * @return
	 */
	public List<SlCompanyMain> findCompanyListReference(String corName,PagingBean pb){
		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		
		hql.append("from SlCompanyMain sc where 1=1");
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=strs && !"".equals(strs)){
			hql.append(" and sc.companyId in ("+strs+")");
		}
		hql.append(" and sc.isPledge in(0,1)");
		//第一次查询时候为null,把查询条件的值去掉后为""或者"null",多重并列判断。
		if(corName!=null&&!"".equals(corName)&&!"null".equals(corName)){
			hql.append(" and sc.corName like ? ");
			params.add("%" + corName + "%");
		}
		
		hql.append(" order by sc.companyMainId desc");
		
		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 获取我方企业主体-添加抵质押物索引匹配信息
	 * @param query
	 * @return
	 */
	public List<SlCompanyMain> queryListForCombo(String query){
		if(query==null){
			query="" ;
		}else{
			query.replaceAll(" ", "") ;
		}
		String hql = "from SlCompanyMain as slc where slc.corName like '%" + query + "%'" ;
		return findByHql(hql);
	}

}