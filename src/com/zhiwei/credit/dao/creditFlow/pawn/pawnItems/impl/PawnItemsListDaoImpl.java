package com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.VPawnItemsList;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PawnItemsListDaoImpl extends BaseDaoImpl<PawnItemsList> implements PawnItemsListDao{

	public PawnItemsListDaoImpl() {
		super(PawnItemsList.class);
	}

	@Override
	public List<PawnItemsList> getListByProjectId(Long projectId,
			String businessType) {
		String hql="from PawnItemsList as p where p.projectId=? and p.businessType=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public List<VPawnItemsList> listByProjectId(
			String pawnItemStatus, HttpServletRequest request, PagingBean pb) {
		String hql="from VPawnItemsList as p where p.pawnItemStatus=?";
		String str=ContextUtil.getBranchIdsStr();
		String companyId=request.getParameter("companyId");
		if(null!=companyId && !"".equals(companyId)){
			str=companyId;
		}
		if(null!=str && !"".equals(str)){
			hql=hql+" and p.projectId in (select w.projectId from PlPawnProject as w where w.companyId in ("+str+"))";
		}
		String projectNum=request.getParameter("projectNum");
		if(null!=projectNum && !"".equals(projectNum)){
			hql=hql+" and p.projectNumber like '%"+projectNum+"%'";
		}
		String projectName=request.getParameter("projectName");
		if(null!=projectName && !"".equals(projectName)){
			hql=hql+" and p.projectName like '%"+projectName+"%'";
		}
		if(null==pb){
			return getSession().createQuery(hql).setParameter(0, pawnItemStatus).list();
		}else{
			return getSession().createQuery(hql).setParameter(0, pawnItemStatus).setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).list();
		}
	}

}