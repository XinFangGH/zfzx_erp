package com.zhiwei.credit.dao.p2p.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.materials.PlWebShowMaterialsDao;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.p2p.materials.PlWebShowMaterials;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlWebShowMaterialsDaoImpl extends BaseDaoImpl<PlWebShowMaterials> implements PlWebShowMaterialsDao{

	public PlWebShowMaterialsDaoImpl() {
		super(PlWebShowMaterials.class);
	}

	@Override
	public List<PlWebShowMaterials> getByProjIdAndShow(String projId,
			String businessType) {
		String hql="from PlWebShowMaterials sm where sm.projId=? and sm.businessTypeKey=?";
		return getSession().createQuery(hql).setParameter(0, projId).setParameter(1, businessType).list();
	}

	@Override
	public List<PlWebShowMaterials> getByProMaterialsId(String projId,
			SlProcreditMaterials slProcreditMaterials, String businessType,
			String operationType) {
		// TODO Auto-generated method stub
		String hql="from PlWebShowMaterials sm where sm.projId=? and sm.proMaterialsId=? and sm.businessTypeKey=? and sm.operationTypeKey=?";
		return this.getSession().createQuery(hql).setParameter(0, projId).setParameter(1, slProcreditMaterials.getProMaterialsId()).setParameter(2, businessType).setParameter(3, operationType).list();
	}

	@Override
	public List<PlWebShowMaterials> listByMaterialsIdAndOperationTypeKey(
			String projId, String businessType, String operationType,
			Long parentId) {
		String hql = "from PlWebShowMaterials sm where sm.projId=? and businessTypeKey=? and sm.operationTypeKey=? and sm.parentId=?";
		return this.getSession().createQuery(hql).setParameter(0, projId).setParameter(1, businessType).setParameter(2, operationType).setParameter(3, Integer.valueOf(parentId.toString())).list();
		
	}

	@Override
	public List<PlWebShowMaterials> listByMaterialsIdGroupById(String projId,
			String businessType, String operationType) {
		String hql = "from PlWebShowMaterials sm where sm.projId=? and businessTypeKey=? and sm.operationTypeKey=? group by sm.parentId";
		
		return this.getSession().createQuery(hql).setParameter(0, projId).setParameter(1, businessType).setParameter(2, operationType).list();
	}

}