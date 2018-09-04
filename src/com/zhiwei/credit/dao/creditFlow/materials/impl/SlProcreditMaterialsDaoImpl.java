package com.zhiwei.credit.dao.creditFlow.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.materials.SlProcreditMaterialsDao;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;

/**
 * 担保材料
 * @author zhangyl
 *
 */
@SuppressWarnings("unchecked")
public class SlProcreditMaterialsDaoImpl extends BaseDaoImpl<SlProcreditMaterials> implements SlProcreditMaterialsDao{

	public SlProcreditMaterialsDaoImpl() {
		super(SlProcreditMaterials.class);
	}

	@Override
	public List<SlProcreditMaterials> getByProjIdAndShow(String projId,String businessType,boolean show)
	{
		String hql = "from SlProcreditMaterials sm where sm.projId=? and sm.isShow=?";
		Object[] objs={projId,show};
		return findByHql(hql, objs);
	}
	@Override
	public List<SlProcreditMaterials> getByProjIdAndParentId(String projId,
			Integer parentId, String businessType, boolean show) {
		String hql = "from SlProcreditMaterials sm where sm.projId=? and sm.isShow=? and sm.businessTypeKey=? and sm.parentId=?";
		Object[] objs={projId,show,businessType,parentId};
		return findByHql(hql, objs);
	}
	@Override
	public List<SlProcreditMaterials> getByProjIdBusinessTypeKey(String projId,String businessType){
		String hql = "from SlProcreditMaterials sm where sm.projId=? and businessTypeKey=?";
		Object[] objs={projId,businessType};
		return findByHql(hql, objs);
	}

	@Override
	public List<SlProcreditMaterials> getList(String projId,
			String businessType, String operationTypeKey) {
		String hql="from SlProcreditMaterials sm where sm.materialsId in(select p.materialsId from OurProcreditMaterials p where p.isPublic=1) and sm.projId=? and sm.businessTypeKey=? and sm.operationTypeKey=?";
		Object[] objs={projId,businessType,operationTypeKey};
		return findByHql(hql, objs);
	}

	@Override
	public SlProcreditMaterials getSLMaterials(String projId,
			String businessType, Long materialId) {
		String hql="from SlProcreditMaterials as sm where sm.materialsId=? and sm.projId=? and sm.businessTypeKey=?";
		SlProcreditMaterials s=null;
		List list=getSession().createQuery(hql).setParameter(0, materialId).setParameter(1, projId).setParameter(2, businessType).list();
		if(null!=list && list.size()>0){
			s=(SlProcreditMaterials)list.get(0);
		}
		return s;
	}

	@Override
	public List<SlProcreditMaterials> getByProjId(String projId,
			String businessType, boolean show) {
		String hql="from SlProcreditMaterials sm where sm.projId=? and sm.isShow=? and sm.businessTypeKey=?";
		return getSession().createQuery(hql).setParameter(0, projId).setParameter(1, show).setParameter(2, businessType).list();
	}
	
	@Override
	public List<SlProcreditMaterials> getByProjIdAndOperationType(
			String projId, Long materialsId, String businessType,
			String operationType) {
		// TODO Auto-generated method stub
		String hql=" from SlProcreditMaterials sm where sm.projId=? and sm.materialsId=? and sm.businessTypeKey=? and sm.operationTypeKey=?";
		return this.getSession().createQuery(hql).setParameter(0, projId).setParameter(1, materialsId).setParameter(2, businessType).setParameter(3, operationType).list();
	}

	@Override
	public List<SlProcreditMaterials> listByMaterialsIdGroupById(String projId,
			String businessType, String operationType) {
		// TODO Auto-generated method stub
		String hql = "from SlProcreditMaterials sm where sm.projId=? and businessTypeKey=?  group by sm.parentId";
		
		return this.getSession().createQuery(hql).setParameter(0, projId).setParameter(1, businessType).list();
	}

	@Override
	public List<SlProcreditMaterials> listByMaterialsIdAndOperationTypeKey(
			String projId, String businessType, String operationType,
			Long parentId) {
		String hql = "from SlProcreditMaterials sm where sm.projId=? and businessTypeKey=? and sm.parentId=?";
		return this.getSession().createQuery(hql).setParameter(0, projId).setParameter(1, businessType).setParameter(2, Integer.valueOf(parentId.toString())).list();
	}

	
	
}