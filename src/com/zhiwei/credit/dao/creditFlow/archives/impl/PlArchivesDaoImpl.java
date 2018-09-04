package com.zhiwei.credit.dao.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.archives.PlArchivesDao;
import com.zhiwei.credit.model.creditFlow.archives.PlArchives;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlArchivesDaoImpl extends BaseDaoImpl<PlArchives> implements PlArchivesDao{

	public PlArchivesDaoImpl() {
		super(PlArchives.class);
	}

	@Override
	public List<PlArchives> getallcabinet(int start,int limit) {
		String hql = "from PlArchives s where s.parentid = 0 and s.isdelete is null order by sortorder";
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getallcabinetsize() {
		String hql = "from PlArchives s where s.parentid = 0 and s.isdelete is null order by sortorder";
		return findByHql(hql).size();
	}

	@Override
	public List<PlArchives> getallchecker(int start,int limit) {
		String hql = "from PlArchives s where s.parentid != 0 and s.isdelete is null order by sortorder";
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getallcheckersize() {
		String hql = "from PlArchives s where s.parentid != 0 and s.isdelete is null order by sortorder";
		return findByHql(hql).size();
	}

	@Override
	public List<PlArchives> getcheckerbyparentid(Long parentId,int start,int limit) {
		String hql = "from PlArchives s where s.isdelete is null and  s.parentid = "+parentId;
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getcheckerbyparentidsize(Long parentId) {
		String hql = "from PlArchives s where s.isdelete is null and s.parentid = "+parentId;
		return findByHql(hql).size();
	}

	@Override
	public List<PlArchives> searchcabinet(String name,String companyId, int start, int limit) {
		String hql="from PlArchives s where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		
		if(companyId==null || companyId.equals("")){
			
			hql+=" and s.isdelete is null and s.parentid = 0 and s.name like '%"+name+"%' order by companyId,sortorder";
		}else{
			hql+=" and s.companyId="+companyId+" and s.isdelete is null and s.parentid = 0 and s.name like '%"+name+"%' order by sortorder";
			
		}
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int searchcabinetsize(String name,String companyId) {
		String hql="from PlArchives s where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		
		if(companyId==null || companyId.equals("")){
			
			hql+=" and s.isdelete is null and s.parentid = 0 and s.name like '%"+name+"%' order by companyId,sortorder";
		}else{
			hql+=" and s.companyId="+companyId+" and s.isdelete is null and s.parentid = 0 and s.name like '%"+name+"%' order by sortorder";
			
		}
		return findByHql(hql).size();
	}

	@Override
	public List<PlArchives> getbycompanyId(String companyId) {
		String hql="from PlArchives s where s.parentid = 0 and s.isdelete is null and s.companyId="+companyId+ " order by sortorder";
		return findByHql(hql);
	}

}