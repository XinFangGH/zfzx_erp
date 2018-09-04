package com.zhiwei.credit.service.creditFlow.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.materials.OurProcreditMaterialsEnterpriseDao;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.service.creditFlow.materials.OurProcreditMaterialsEnterpriseService;

/**
 * 
 * @author 
 *
 */
public class OurProcreditMaterialsEnterpriseServiceImpl extends BaseServiceImpl<OurProcreditMaterialsEnterprise> implements OurProcreditMaterialsEnterpriseService{
	private OurProcreditMaterialsEnterpriseDao dao;
	
	public OurProcreditMaterialsEnterpriseServiceImpl(OurProcreditMaterialsEnterpriseDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> getListByParentId(
			Integer parentId,PagingBean pb) {
		return this.dao.getListByParentId(parentId,pb);
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> getListByParentIdAndType(
			Integer parentId, String operationTypeKey) {
		return dao.getListByParentIdAndType(parentId, operationTypeKey);
	}

	@Override
	public void deleteByProductId(String id) {
		dao.deleteByProductId(id);
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> getByProjectId(Long projectId) {
		return dao.getByProjectId(projectId);
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> getByProductId(Long productId) {
		return dao.getByProductId(productId);
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> getListByIdsNotNull(String node,String operationTypeKey) {
		return dao.getListByIdsNotNull(node,operationTypeKey);
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> getByPProductIdAndOperationType(
			String productId, Long materialsId) {
		return dao.getByProjIdAndOperationType(productId, materialsId);
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> listByMaterialsIdGroupById(
			String productId) {
		// TODO Auto-generated method stub
		return dao.listByMaterialsIdGroupById(productId);
	}

	@Override
	public List<OurProcreditMaterialsEnterprise> listByProductIdAndParentId(
			Long productId, Integer parentId) {
		
		return dao.listByProductIdAndParentId(productId, parentId);
	}

	

	@Override
	public List<OurProcreditMaterialsEnterprise> listByProjectAndBusinessType(
			Long projectId, String businessType) {
		// and a.businessTypeKey = ?
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.projectId =? and a.operationTypeKey = ? ";
		return dao.findByHql(hql, new Object[] {projectId,businessType});
	
	}


	

}