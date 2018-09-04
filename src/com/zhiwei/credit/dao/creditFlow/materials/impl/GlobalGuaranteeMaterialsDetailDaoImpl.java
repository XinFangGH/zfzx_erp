package com.zhiwei.credit.dao.creditFlow.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.materials.GlobalGuaranteeMaterialsDetailDao;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsDetail;

/**
 * 担保材料
 * @author zhangyl
 *
 */
@SuppressWarnings("unchecked")
public class GlobalGuaranteeMaterialsDetailDaoImpl extends BaseDaoImpl<GlobalGuaranteeMaterialsDetail> implements GlobalGuaranteeMaterialsDetailDao{

	public GlobalGuaranteeMaterialsDetailDaoImpl() {
		super(GlobalGuaranteeMaterialsDetail.class);
	}

	@Override
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdAndShow(String projId,String businessType,boolean show)
	{
		String hql = "from GlobalGuaranteeMaterialsDetail sm where sm.projId=? and sm.isShow=?";
		Object[] objs={projId,show};
		return findByHql(hql, objs);
	}
	@Override
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdAndParentId(String projId,
			Integer parentId, String businessType, boolean show) {
		String hql = "from GlobalGuaranteeMaterialsDetail sm where sm.projId=? and sm.isShow=? and sm.businessTypeKey=? and sm.parentId=?";
		Object[] objs={projId,show,businessType,parentId};
		return findByHql(hql, objs);
	}
	@Override
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdBusinessTypeKey(String projId,String businessType){
		String hql = "from GlobalGuaranteeMaterialsDetail sm where sm.projId=? and businessTypeKey=?";
		Object[] objs={projId,businessType};
		return findByHql(hql, objs);
	}

	@Override
	public List<GlobalGuaranteeMaterialsDetail> getList(String projId,
			String businessType, String operationTypeKey) {
		String hql="from GlobalGuaranteeMaterialsDetail sm where sm.materialsId in(select p.materialsId from OurProcreditMaterials p where p.isPublic=1) and sm.projId=? and sm.businessTypeKey=? and sm.operationTypeKey=?";
		Object[] objs={projId,businessType,operationTypeKey};
		return findByHql(hql, objs);
	}

	@Override
	public GlobalGuaranteeMaterialsDetail getSLMaterials(String projId,
			String businessType, Long materialId) {
		String hql="from GlobalGuaranteeMaterialsDetail as sm where sm.materialsId=? and sm.projId=? and sm.businessTypeKey=?";
		GlobalGuaranteeMaterialsDetail s=null;
		List list=getSession().createQuery(hql).setParameter(0, materialId).setParameter(1, projId).setParameter(2, businessType).list();
		if(null!=list && list.size()>0){
			s=(GlobalGuaranteeMaterialsDetail)list.get(0);
		}
		return s;
	}

	@Override
	public List<GlobalGuaranteeMaterialsDetail> getByProjId(String projId,
			String businessType, boolean show) {
		String hql="from GlobalGuaranteeMaterialsDetail sm where sm.projId=? and sm.isShow=? and sm.businessTypeKey=?";
		return getSession().createQuery(hql).setParameter(0, projId).setParameter(1, show).setParameter(2, businessType).list();
	}
//add  by gaoqingrui	
	
	

}