package com.zhiwei.credit.dao.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.archives.OurArchivesMaterialsDao;
import com.zhiwei.credit.dao.creditFlow.archives.PlArchivesMaterialsDao;
import com.zhiwei.credit.model.creditFlow.archives.OurArchivesMaterials;
import com.zhiwei.credit.model.creditFlow.archives.PlArchivesMaterials;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;



/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class OurArchivesMaterialsDaoImpl extends BaseDaoImpl<OurArchivesMaterials> implements OurArchivesMaterialsDao{

	public OurArchivesMaterialsDaoImpl() {
		super(OurArchivesMaterials.class);
	}
	 @Resource
	    private PlArchivesMaterialsDao plArchivesMaterialsDao;
	    
		@Override
		public void initMaterials(String projectId, Integer businessType) {
			   
			List<OurArchivesMaterials> list = this.getAll();
			for(OurArchivesMaterials dictionary : list){
				PlArchivesMaterials plArchivesMaterials = new PlArchivesMaterials();
				plArchivesMaterials.setProjId(Long.valueOf(projectId));
				plArchivesMaterials.setMaterialsId(dictionary.getMaterialsId());
				plArchivesMaterials.setMaterialsName(dictionary.getMaterialsName());
				plArchivesMaterials.setIsShow(false);
				plArchivesMaterials.setDatumNums(0);
				plArchivesMaterials.setParentId(null);
				plArchivesMaterials.setBusinessTypeId(businessType.intValue());
				plArchivesMaterialsDao.save(plArchivesMaterials);
			}
		}

		

		@Override
		public List<OurArchivesMaterials> getByBusinessType(String businessType,PagingBean pb) {
			Object[] objs = { businessType };
			String hql = "from OurArchivesMaterials sm where sm.businessTypeKey=? and sm.productId is null and sm.settingId is null";
			if(null==pb){
				return this.findByHql(hql, objs);
			}else{
				return this.findByHql(hql, objs,pb);
			}
		}
		@Override
		public List<OurArchivesMaterials> getbyoperationTypeKey(String operationTypeKey){
			String hql = "from OurArchivesMaterials sm where sm.operationTypeKey like '%"+operationTypeKey+"%' and sm.productId is null and sm.settingId is null";
			return this.findByHql(hql);
		}
		@Override
		public List<OurArchivesMaterials> checkIsExit(String productId,
				Long materialsId, String businessType) {
			String hql = "from OurArchivesMaterials sm where sm.businessTypeKey=?  and sm.productId=? and sm.settingId=?";
			return this.getSession().createQuery(hql).setParameter(0, businessType)
					.setParameter(1,
							Long.valueOf(productId)).setParameter(2, materialsId)
					.list();
		}

		@Override
		public List<OurArchivesMaterials> getByPProductIdAndOperationType(
				String productId, String businessType) {
			String hql = "from OurArchivesMaterials sm where sm.businessTypeKey=?  and sm.productId=? ";
			return this.getSession().createQuery(hql).setParameter(0, businessType)
					.setParameter(1,
							Long.valueOf(productId)).list();
		}

		@Override
		public List<OurArchivesMaterials> getByProductId(Long productId) {
			String hql = "from OurArchivesMaterials sm where sm.productId=? ";
			return this.getSession().createQuery(hql).setParameter(0, Long.valueOf(productId)).list();
		}

		@Override
		public List<OurArchivesMaterials> getListByType(String businessType,String operationType) {
			String hql = "from OurArchivesMaterials sm where sm.businessTypeKey=?  and sm.operationTypeKey=? and sm.productId is null and sm.settingId is null ";
			return this.getSession().createQuery(hql).setParameter(0, businessType).setParameter(1, operationType).list();
		}

		@Override
		public void initMateriais(String projectId,String businessTypeKey, String operationTypeKey) {
			
			String hql = "from OurArchivesMaterials sm  where sm.operationTypeKey like '%"+operationTypeKey+"%' and sm.isPublic=1 and sm.productId is null and sm.settingId is null";
			//Object[] objs={operationTypeKey};
			List<OurArchivesMaterials> list=this.findByHql(hql);
			for(OurArchivesMaterials ourArchivesMaterials : list){
				PlArchivesMaterials plArchivesMaterials = new PlArchivesMaterials();
				plArchivesMaterials.setProjId(Long.valueOf(projectId));
				plArchivesMaterials.setMaterialsId(ourArchivesMaterials.getMaterialsId());
				plArchivesMaterials.setMaterialsName(ourArchivesMaterials.getMaterialsName());
				plArchivesMaterials.setIsShow(false);
				plArchivesMaterials.setDatumNums(0);
				plArchivesMaterials.setXxnums(0);
				plArchivesMaterials.setBusinessType(businessTypeKey);
				plArchivesMaterials.setOperationType(operationTypeKey);
				plArchivesMaterials.setMaterialsType(Short.valueOf("1"));
				plArchivesMaterialsDao.save(plArchivesMaterials);
			}
			
		}
		 /**
		 * 依据项目的productId初始化归档材料（修改，增加，编辑）
		 * add by  linyan 2014-9-12
		 * @param projectId  项目id
		 * @param businessType 项目类型
		 * @param productId  产品编号
		 */
		@Override
		public void initMaterialsByProductId(Long projectId,String businessType, String operationTypeKey, Long productId) {
			// TODO Auto-generated method stub
			List<PlArchivesMaterials> listtemp1 = plArchivesMaterialsDao.listbyProjectId(projectId, businessType);
		    if(productId!=null){
		    	List<OurArchivesMaterials> list= this.getByProductId(productId);
		    	if(list!=null&&list.size()!=0){
		    		if(listtemp1==null || listtemp1.size()==0){
		    			for(OurArchivesMaterials  temp :list){
		    				if(temp.getIsPublic()!=null&&temp.getIsPublic().compareTo(Short.valueOf("1"))==0){
		    					PlArchivesMaterials plArchivesMaterials = new PlArchivesMaterials();
				    			plArchivesMaterials.setProjId(Long.valueOf(projectId));
				    			plArchivesMaterials.setMaterialsId(temp.getMaterialsId());
				    			plArchivesMaterials.setMaterialsName(temp.getMaterialsName());
				    			plArchivesMaterials.setIsShow(false);
				    			plArchivesMaterials.setDatumNums(0);
				    			plArchivesMaterials.setXxnums(0);
				    			plArchivesMaterials.setBusinessType(temp.getBusinessTypeKey());
				    			plArchivesMaterials.setOperationType(temp.getOperationTypeKey());
				    			plArchivesMaterials.setMaterialsType(Short.valueOf("1"));
				    			plArchivesMaterials.setProductId(productId);
				    			plArchivesMaterialsDao.save(plArchivesMaterials);
		    				}
			    		}
			    	}else{
			    		for(OurArchivesMaterials  temp :list){
			    			if(temp.getIsPublic()!=null&&temp.getIsPublic().compareTo(Short.valueOf("1"))==0){
				    			List<PlArchivesMaterials> s =plArchivesMaterialsDao.checkIsExit(projectId.toString(),businessType,temp.getMaterialsId());
				    			if(s==null){
					    				PlArchivesMaterials plArchivesMaterials = new PlArchivesMaterials();
						    			plArchivesMaterials.setProjId(Long.valueOf(projectId));
						    			plArchivesMaterials.setMaterialsId(temp.getMaterialsId());
						    			plArchivesMaterials.setMaterialsName(temp.getMaterialsName());
						    			plArchivesMaterials.setIsShow(false);
						    			plArchivesMaterials.setDatumNums(0);
						    			plArchivesMaterials.setXxnums(0);
						    			plArchivesMaterials.setBusinessType(temp.getBusinessTypeKey());
						    			plArchivesMaterials.setOperationType(temp.getOperationTypeKey());
						    			plArchivesMaterials.setMaterialsType(Short.valueOf("1"));
						    			plArchivesMaterials.setProductId(productId);
						    			plArchivesMaterialsDao.save(plArchivesMaterials);
				    			}else{
				    				s.get(0).setProductId(productId);
				    				plArchivesMaterialsDao.merge(s.get(0));
				    				
				    			}
			    		  }
			    		}
			    		List<PlArchivesMaterials> listtemp11 = plArchivesMaterialsDao.listbyProjectId(projectId, businessType);
			    		if(listtemp11!=null&&listtemp11.size()>0){
			    			for(PlArchivesMaterials  tempp:listtemp11){
			    				if(tempp.getProductId()!=null && (tempp.getProductId().compareTo(productId)==0)){
			    				}else{
			    					plArchivesMaterialsDao.remove(tempp.getProMaterialsId());
			    				}
			    			}
			    		}
			    	}
		    	}else{//如果产品没有归档材料，将已有的归档材料全部删除
		    		if(listtemp1!=null){
			    		for(PlArchivesMaterials temp:listtemp1){
			    			plArchivesMaterialsDao.remove(temp.getProMaterialsId());
			    		}
			    	}
		    	}
		    }else{//如果没有产品id将所有已经初始化的归档材料删除
		    	if(listtemp1!=null){
		    		for(PlArchivesMaterials temp:listtemp1){
		    			plArchivesMaterialsDao.remove(temp.getProMaterialsId());
		    		}
		    	}
		    }

		
			
		}
}