package com.zhiwei.credit.dao.creditFlow.materials.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.materials.OurProcreditMaterialsEnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.materials.SlProcreditMaterialsDao;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class OurProcreditMaterialsEnterpriseDaoImpl extends BaseDaoImpl<OurProcreditMaterialsEnterprise> implements OurProcreditMaterialsEnterpriseDao{

	public OurProcreditMaterialsEnterpriseDaoImpl() {
		super(OurProcreditMaterialsEnterprise.class);
	}
    @Resource
    private SlProcreditMaterialsDao slProcreditMaterialsDao;
    @Override
	public List<OurProcreditMaterialsEnterprise> getListByParentId(
			Integer parentId,PagingBean pb) {
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.parentId =? and a.projectId is  NULL and a.productId is  NULL ";
		if(pb!=null&&pb.getMap()!=null){
			Map<String, Object> map = pb.getMap();
			if(null!=map.get("operationTypeKey")&&!"".equals(map.get("operationTypeKey"))){
				hql += " and a .operationTypeKey = '" + map.get("operationTypeKey") +"' ";
			}
		}
		
		Object[] objs = { parentId };
		if(null==pb){
			return findByHql(hql, objs);
		}else{
			return findByHql(hql, objs,pb);
		}
	}
	

	@Override
	public void initMaterials(String projectId, Integer businessType) {
		
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.parentId =? and  a.projectId is  NULL and a.productId is  NULL ";
		
		Object[] objs={0};
		
		List<OurProcreditMaterialsEnterprise> list=findByHql(hql, objs);
		
		for(OurProcreditMaterialsEnterprise ome:list){
			   
			   Object[] objs1={ome.getMaterialsId().intValue()};
			   List<OurProcreditMaterialsEnterprise> list1=findByHql(hql, objs1);
	      	   for(OurProcreditMaterialsEnterprise ac:list1)
	      	   {
	      		       SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
		        	   slProcreditMaterials.setProjId(String.valueOf(projectId));
		        	   slProcreditMaterials.setMaterialsId(ac.getMaterialsId());
		        	   slProcreditMaterials.setIsShow(false);
		        	   slProcreditMaterials.setParentId(ome.getMaterialsId().intValue());
		        	   slProcreditMaterials.setBusinessTypeId(businessType.intValue());
		        	   slProcreditMaterials.setDatumNums(0);
		        	   slProcreditMaterials.setIsReceive(false);
		        	   slProcreditMaterials.setMaterialsName(ac.getMaterialsName());
		        	   slProcreditMaterialsDao.save(slProcreditMaterials);
	      	   }
		}
	}


	@Override
	public void initMaterials(String projectId,String businessTypeKey,String operationTypeKey) {
		
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.parentId =? and a.operationTypeKey like '%"+operationTypeKey+"%'";
		String hql1 = "from OurProcreditMaterialsEnterprise AS a where a.parentId =?";
		Object[] objs={0};
		List<OurProcreditMaterialsEnterprise> list=findByHql(hql, objs);
		for(OurProcreditMaterialsEnterprise ome:list){
			   Object[] objs1={ome.getMaterialsId().intValue()};
			   List<OurProcreditMaterialsEnterprise> list1=findByHql(hql1, objs1);
	      	   for(OurProcreditMaterialsEnterprise ac:list1)
	      	   {
	      		       SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
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
		        	   slProcreditMaterialsDao.save(slProcreditMaterials);
	      	   }
		}
	}
	
	
	/**
	 * 依据项目的productId初始化贷款材料（修改，增加，编辑）
	 * add by  linyan 2014-8-11
	 * @param projectId  项目id
	 * @param businessType 项目类型
	 * @param productId  产品编号
	 */
	@Override
	public void initMaterialsByProductId(Long projectId, String businessType,String operationTypeKey,Long productId) {
		// TODO Auto-generated method stub
		List<SlProcreditMaterials> listtemp1 = slProcreditMaterialsDao
				.getByProjId(projectId.toString(), businessType, true);
		if (productId != null) {
			List<OurProcreditMaterialsEnterprise> list = this
					.getByProductId(productId);
			if (list != null && list.size() != 0) {
				if (listtemp1 == null || listtemp1.size() == 0) {
					for (OurProcreditMaterialsEnterprise temp : list) {
						//if (temp.getProjectId() != null) {
							OurProcreditMaterialsEnterprise o = this.get(temp.getMaterialsId());
							SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
							slProcreditMaterials.setProjId(String
									.valueOf(projectId));
							slProcreditMaterials.setMaterialsId(temp
									.getMaterialsId());
							slProcreditMaterials.setIsShow(true);
							slProcreditMaterials.setParentId(o.getParentId());
							slProcreditMaterials
									.setBusinessTypeKey(businessType);
							slProcreditMaterials
									.setOperationTypeKey(operationTypeKey);
							slProcreditMaterials.setDatumNums(0);
							slProcreditMaterials.setIsReceive(false);
							slProcreditMaterials.setMaterialsName(o
									.getMaterialsName());
							slProcreditMaterials.setRuleExplain(o
									.getRuleExplain());
							slProcreditMaterials.setProductId(productId);
							slProcreditMaterialsDao.save(slProcreditMaterials);
						}

					//}
				} else {
					for (OurProcreditMaterialsEnterprise temp : list) {
						SlProcreditMaterials s = slProcreditMaterialsDao
								.getSLMaterials(projectId.toString(),
										businessType, temp.getMaterialsId());
						if (s == null) {
							//if (temp.getProjectId() != null) {
								OurProcreditMaterialsEnterprise o = this.get(temp.getMaterialsId());
								SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
								slProcreditMaterials.setProjId(String
										.valueOf(projectId));
								slProcreditMaterials.setMaterialsId(temp
										.getMaterialsId());
								slProcreditMaterials.setIsShow(true);
								slProcreditMaterials.setParentId(o
										.getParentId());
								slProcreditMaterials
										.setBusinessTypeKey(businessType);
								slProcreditMaterials
										.setOperationTypeKey(operationTypeKey);
								slProcreditMaterials.setDatumNums(0);
								slProcreditMaterials.setIsReceive(false);
								slProcreditMaterials.setMaterialsName(o
										.getMaterialsName());
								slProcreditMaterials.setRuleExplain(o
										.getRuleExplain());
								slProcreditMaterials.setProductId(productId);
								slProcreditMaterialsDao
										.save(slProcreditMaterials);
							/*} else if (temp.getProjectId() == null && temp.getParentId() != null) {
								SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
								slProcreditMaterials.setProjId(String
										.valueOf(projectId));
								slProcreditMaterials.setMaterialsId(temp
										.getMaterialsId());
								slProcreditMaterials.setIsShow(true);
								slProcreditMaterials.setParentId(temp
										.getParentId());
								slProcreditMaterials
										.setBusinessTypeKey(businessType);
								slProcreditMaterials
										.setOperationTypeKey(operationTypeKey);
								slProcreditMaterials.setDatumNums(0);
								slProcreditMaterials.setIsReceive(false);
								slProcreditMaterials.setMaterialsName(temp
										.getMaterialsName());
								slProcreditMaterials.setRuleExplain(temp
										.getRuleExplain());
								slProcreditMaterials.setProductId(productId);
								slProcreditMaterialsDao
										.save(slProcreditMaterials);
							}*/
						} else {
							s.setProductId(productId);
							slProcreditMaterialsDao.save(s);
						}
					}
					List<SlProcreditMaterials> listtemp11 = slProcreditMaterialsDao
							.getByProjId(projectId.toString(), businessType,
									true);
					if (listtemp11 != null && listtemp11.size() > 0) {
						for (SlProcreditMaterials tempp : listtemp11) {
							if (tempp.getProductId() != null
									&& (tempp.getProductId().compareTo(
											productId) == 0)) {
							} else {
								slProcreditMaterialsDao.remove(tempp
										.getProMaterialsId());
							}
						}
					}
				}
			} else {// 如果产品没有贷款材料，将已有的贷款材料全部删除
				if (listtemp1 != null) {
					for (SlProcreditMaterials temp : listtemp1) {
						slProcreditMaterialsDao
								.remove(temp.getProMaterialsId());
					}
				}
			}
		} else {// 如果没有产品id将所有已经初始化的贷款材料删除
			if (listtemp1 != null) {
				for (SlProcreditMaterials temp : listtemp1) {
					slProcreditMaterialsDao.remove(temp.getProMaterialsId());
				}
			}
		}

	}


	@Override
	public List<OurProcreditMaterialsEnterprise> getListByParentIdAndType(
			Integer parentId, String operationTypeKey) {
		//String hql = "from OurProcreditMaterialsEnterprise AS a where a.parentId =? and a.operationTypeKey=? and a.productId is null and a.projectId is null";
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.parentId =?  and a.productId is null and a.projectId is null";
		Object[] objs={parentId};
		return findByHql(hql, objs);
	}


	@Override
	public void deleteByProductId(String id) {
		String hql="delete from OurProcreditMaterialsEnterprise as o where o.productId=?";
		Query query = getSession().createQuery(hql).setLong(0, Long.valueOf(id));
		query.executeUpdate();
	}


	@Override
	public List<OurProcreditMaterialsEnterprise> getByProductId(Long productId) {
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.productId =? order by a.parentId";
		return getSession().createQuery(hql).setLong(0, Long.valueOf(productId)).list();
	}


	@Override
	public List<OurProcreditMaterialsEnterprise> getByProjectId(Long projectId) {
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.projectId =? ";
		return getSession().createQuery(hql).setLong(0, Long.valueOf(projectId)).list();
	}


	@Override
	public List<OurProcreditMaterialsEnterprise> getListByIdsNotNull(String node,String operationTypeKey) {
		String hql = "from OurProcreditMaterialsEnterprise AS a where a.projectId is  NULL and a.productId is  NULL ";
		if(null!=node && !"".equals(node)){
			hql+=" and a.parentId="+node;
		}
		
		if(null!=operationTypeKey && !"".equals(operationTypeKey)){
			hql+=" and a.operationTypeKey='"+operationTypeKey + "' ";
		}
		return getSession().createQuery(hql).list();
	}


	@Override
	public List<OurProcreditMaterialsEnterprise> getByProjIdAndOperationType(
			String productId, Long materialsId) {
		// TODO Auto-generated method stub
		String hql = " from OurProcreditMaterialsEnterprise sm where sm.productId=? and sm.projectId=?";
		return this.getSession().createQuery(hql).setParameter(0,
				Long.valueOf(productId))
				.setParameter(1, materialsId).list();
	}


	@Override
	public List<OurProcreditMaterialsEnterprise> listByMaterialsIdGroupById(
			String productId) {
		String hql = "from OurProcreditMaterialsEnterprise sm where sm.productId=? group by sm.parentId";

		return this.getSession().createQuery(hql).setParameter(0,
				Long.valueOf(productId)).list();
	}


	@Override
	public List<OurProcreditMaterialsEnterprise> listByProductIdAndParentId(
			Long productId, Integer parentId) {
		String hql="from OurProcreditMaterialsEnterprise as sm where sm.productId=? and sm.parentId=?";
		return this.findByHql(hql, new Object[]{productId,parentId});
	}
	
}