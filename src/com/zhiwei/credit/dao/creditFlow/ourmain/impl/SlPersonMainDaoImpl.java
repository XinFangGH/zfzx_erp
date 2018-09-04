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
import com.zhiwei.credit.dao.creditFlow.ourmain.SlPersonMainDao;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;

@SuppressWarnings("unchecked")
public class SlPersonMainDaoImpl extends BaseDaoImpl<SlPersonMain> implements SlPersonMainDao{

	public SlPersonMainDaoImpl() {
		super(SlPersonMain.class);
	}

	@Override
	public List<SlPersonMain> selectCardNum(String cardNum) {
		String hql="from SlPersonMain slPersonMain where slPersonMain.cardnum=?";
		return findByHql(hql, new Object[]{cardNum});
	}
	
	/**
	 * 获取我方个人主体
	 * @param name
	 * @param cardnum
	 * @param PagingBean
	 * @return
	 */
	public List<SlPersonMain> findPersonListByIsPledge(String name,String cardnum,PagingBean pb,String companyId){

		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		
		//hql.append("from SlPersonMain sp where sp.isPledge in (").append(isPledge).append(")");
		hql.append("from SlPersonMain sp where sp.isPledge=0");
		
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		if(null!=strs && !"".equals(strs)){
			hql.append(" and sp.companyId in ("+strs+")");
		}
		//第一次查询时候为null,把查询条件的值去掉后为""或者"null",多重并列判断。
		if(name!=null&&!"".equals(name)&&!"null".equals(name)){
			hql.append(" and sp.name like ? ");
			params.add("%" + name + "%");
		}
		
		if(cardnum!=null&&!"".equals(cardnum)&&!"null".equals(cardnum)){
			hql.append(" and sp.cardnum like ? ");
			params.add("%" + cardnum + "%");
		}
		
		hql.append(" order by sp.personMainId desc");
		
		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 获取我方个人主体--参照
	 * @param name
	 * @param PagingBean
	 * @return
	 */
	public List<SlPersonMain> findPersonListReference(String name,PagingBean pb){
		
		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		
		hql.append("from SlPersonMain sp where 1=1");
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=strs && !"".equals(strs)){
			hql.append(" and sp.companyId in ("+strs+")");
		}
		hql.append(" and sp.isPledge in(0,1)");
		//第一次查询时候为null,把查询条件的值去掉后为""或者"null",多重并列判断。
		if(name!=null&&!"".equals(name)&&!"null".equals(name)){
			hql.append(" and sp.name like ? ");
			params.add("%" + name + "%");
		}
		
		hql.append(" order by sp.personMainId desc");
		
		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 获取我方个人主体-添加抵质押物索引匹配信息
	 * @param query
	 * @return
	 */
	public List<SlPersonMain> queryListForCombo(String query){
		if(query==null){
			query="" ;
		}else{
			query.replaceAll(" ", "") ;
		}
		String hql = "from SlPersonMain as slp where slp.name like '%" + query + "%'" ;
		return findByHql(hql);
	}

}