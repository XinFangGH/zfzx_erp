package com.zhiwei.credit.dao.creditFlow.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.materials.GlobalGuaranteeMaterialsDetailDao;
import com.zhiwei.credit.dao.creditFlow.materials.GlobalGuaranteeMaterialsExtDao;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsExt;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsDetail;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlobalGuaranteeMaterialsExtDaoImpl extends BaseDaoImpl<GlobalGuaranteeMaterialsExt> implements GlobalGuaranteeMaterialsExtDao{

	public GlobalGuaranteeMaterialsExtDaoImpl() {
		super(GlobalGuaranteeMaterialsExt.class);
	}
    @Resource
    private GlobalGuaranteeMaterialsDetailDao globalGuaranteeMaterialsDetailDao;
	@Override
	public List<GlobalGuaranteeMaterialsExt> getListByParentId(
			Integer parentId) {
		String hql = "from GlobalGuaranteeMaterialsExt AS a where a.parentId =?";
		Object[] objs={parentId};
		return findByHql(hql, objs);
	}
	

	@Override
	public void initMaterials(String projectId, Integer businessType) {
		
		String hql = "from GlobalGuaranteeMaterialsExt AS a where a.parentId =?";
		
		Object[] objs={0};
		
		List<GlobalGuaranteeMaterialsExt> list=findByHql(hql, objs);
		
		for(GlobalGuaranteeMaterialsExt ome:list){
			   
			   Object[] objs1={ome.getMaterialsId().intValue()};
			   List<GlobalGuaranteeMaterialsExt> list1=findByHql(hql, objs1);
	      	   for(GlobalGuaranteeMaterialsExt ac:list1)
	      	   {
	      		       GlobalGuaranteeMaterialsDetail slProcreditMaterials = new GlobalGuaranteeMaterialsDetail();
		        	   slProcreditMaterials.setProjId(String.valueOf(projectId));
		        	   slProcreditMaterials.setMaterialsId(ac.getMaterialsId());
		        	   slProcreditMaterials.setIsShow(false);
		        	   slProcreditMaterials.setParentId(ome.getMaterialsId().intValue());
		        	   slProcreditMaterials.setBusinessTypeId(businessType.intValue());
		        	   slProcreditMaterials.setDatumNums(0);
		        	   slProcreditMaterials.setIsReceive(false);
		        	   slProcreditMaterials.setMaterialsName(ac.getMaterialsName());
		        	   globalGuaranteeMaterialsDetailDao.save(slProcreditMaterials);
	      	   }
		}
	}


	@Override
	public void initMaterials(String projectId,String businessTypeKey,String operationTypeKey) {
		
		String hql = "from GlobalGuaranteeMaterialsExt AS a where a.parentId =? and a.operationTypeKey like '%"+operationTypeKey+"%'";
		String hql1 = "from GlobalGuaranteeMaterialsExt AS a where a.parentId =?";
		Object[] objs={0};
		List<GlobalGuaranteeMaterialsExt> list=findByHql(hql, objs);
		for(GlobalGuaranteeMaterialsExt ome:list){
			   Object[] objs1={ome.getMaterialsId().intValue()};
			   List<GlobalGuaranteeMaterialsExt> list1=findByHql(hql1, objs1);
	      	   for(GlobalGuaranteeMaterialsExt ac:list1)
	      	   {
	      		       GlobalGuaranteeMaterialsDetail slProcreditMaterials = new GlobalGuaranteeMaterialsDetail();
		        	   slProcreditMaterials.setProjId(String.valueOf(projectId));
		        	   slProcreditMaterials.setMaterialsId(ac.getMaterialsId());
		        	   slProcreditMaterials.setIsShow(true);
		        	   slProcreditMaterials.setParentId(ome.getMaterialsId().intValue());
		        	   slProcreditMaterials.setBusinessTypeKey(businessTypeKey);
		        	   slProcreditMaterials.setOperationTypeKey(operationTypeKey);
		        	   slProcreditMaterials.setDatumNums(0);
		        	   slProcreditMaterials.setIsReceive(false);
		        	   slProcreditMaterials.setMaterialsName(ac.getMaterialsName());
		        	   slProcreditMaterials.setRuleExplain(ac.getRuleExplain());
		        	   globalGuaranteeMaterialsDetailDao.save(slProcreditMaterials);
	      	   }
		}
	}


	@Override
	public List<GlobalGuaranteeMaterialsExt> getListByParentIdAndType(
			Integer parentId, String operationTypeKey) {
		String hql = "from GlobalGuaranteeMaterialsExt AS a where a.parentId =? and a.operationTypeKey=?";
		Object[] objs={parentId,operationTypeKey};
		return findByHql(hql, objs);
	}
}