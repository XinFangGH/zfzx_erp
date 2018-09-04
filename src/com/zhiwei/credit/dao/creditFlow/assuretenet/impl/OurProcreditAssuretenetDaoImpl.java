package com.zhiwei.credit.dao.creditFlow.assuretenet.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.assuretenet.OurProcreditAssuretenetDao;
import com.zhiwei.credit.dao.creditFlow.assuretenet.SlProcreditAssuretenetDao;
import com.zhiwei.credit.model.creditFlow.archives.OurArchivesMaterials;
import com.zhiwei.credit.model.creditFlow.archives.PlArchivesMaterials;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class OurProcreditAssuretenetDaoImpl extends BaseDaoImpl<OurProcreditAssuretenet> implements OurProcreditAssuretenetDao{

	public OurProcreditAssuretenetDaoImpl() {
		super(OurProcreditAssuretenet.class);
	}
    @Resource
    private SlProcreditAssuretenetDao procreditAssuretenetDao;
    
	@Override
	public boolean initAssuretenet(String projId, Integer businessType) {
		
		String hql = "from OurProcreditAssuretenet sm  where sm.businessTypeId=?";
		Object[] objs={businessType.intValue()};
		int i = 1;
		List<OurProcreditAssuretenet> list=this.findByHql(hql, objs);
		for(OurProcreditAssuretenet dictionary : list){
			SlProcreditAssuretenet assuretenet = new SlProcreditAssuretenet();
			assuretenet.setAssuretenet(dictionary.getAssuretenet());
			assuretenet.setProjid(projId);
			//assuretenet.setBusinessTypeId(businessType);
			assuretenet.setSortvalue(Integer.toString(i));
			i++;
			procreditAssuretenetDao.save(assuretenet);
		}
		return true;
		
	}


	
	@Override
	public List<OurProcreditAssuretenet> getListByBussinessType(
			String businessTypeKey,PagingBean pb) {
		String hql = "from OurProcreditAssuretenet sm  where sm.businessTypeKey=? and sm.productId is NULL and sm.projectId is null";
		Object[] objs = { businessTypeKey };
		if(null==pb){
			return this.findByHql(hql, objs);
		}else{
			return this.findByHql(hql, objs,pb);
		}
	}

	@Override
	public boolean initAssuretenet(String projectId, String businessTypeKey,String operationTypeKey,String customerType) {
		String hql = "from OurProcreditAssuretenet sm  where sm.businessTypeKey=? and sm.operationTypeKey like '%"+operationTypeKey+"%'and sm.productId is NULL and sm.projectId is null";
		if(customerType.equals("company_customer")){
			hql+=" and sm.customerType='company'";
		}else{
			hql+=" and sm.customerType='person'";
		}
		Object[] objs={businessTypeKey};
		int i = 1;
		List<OurProcreditAssuretenet> list=this.findByHql(hql, objs);
		for(OurProcreditAssuretenet dictionary : list){
			SlProcreditAssuretenet assuretenet = new SlProcreditAssuretenet();
			assuretenet.setSettingId(dictionary.getAssuretenetId());
			assuretenet.setAssuretenet(dictionary.getAssuretenet());
			assuretenet.setProjid(projectId);
			assuretenet.setBusinessTypeKey(businessTypeKey);
			assuretenet.setSortvalue(Integer.toString(i));
			i++;
			procreditAssuretenetDao.save(assuretenet);
		}
		return true;
	}
	/**
	 * 根据产品初始化贷款条件方法（即原有准入原则）
	 */
	@Override
	public boolean initAssuretenetProduct(String projectId,String businessTypeKey, String operationTypeKey, String customerType,Long productId) {
		// TODO Auto-generated method stub
		List<SlProcreditAssuretenet> listtemp1 = procreditAssuretenetDao.getByProjId(projectId, businessTypeKey);
	    if(productId!=null){
	    	List<OurProcreditAssuretenet> list= this.getByProductId(productId);
	    	if(list!=null&&list.size()!=0){
	    		if(listtemp1==null || listtemp1.size()==0){
	    			for(OurProcreditAssuretenet  temp :list){
	    				SlProcreditAssuretenet slProcreditAssuretenet = new SlProcreditAssuretenet();
	    				slProcreditAssuretenet.setProjid(projectId);
	    				slProcreditAssuretenet.setSettingId(temp.getAssuretenetId());
	    				slProcreditAssuretenet.setAssuretenet(temp.getAssuretenet());
	    				slProcreditAssuretenet.setProjid(projectId);
	    				slProcreditAssuretenet.setBusinessTypeKey(businessTypeKey);
	    				slProcreditAssuretenet.setProductId(productId);
	    				procreditAssuretenetDao.save(slProcreditAssuretenet);
		    		}
		    	}else{
		    		for(OurProcreditAssuretenet  temp :list){
		    			List<SlProcreditAssuretenet> s =procreditAssuretenetDao.checkIsExit(projectId.toString(),businessTypeKey,temp.getAssuretenetId());
		    			if(s==null){
		    				SlProcreditAssuretenet slProcreditAssuretenet = new SlProcreditAssuretenet();
		    				slProcreditAssuretenet.setProjid(projectId);
		    				slProcreditAssuretenet.setSettingId(temp.getAssuretenetId());
		    				slProcreditAssuretenet.setAssuretenet(temp.getAssuretenet());
		    				slProcreditAssuretenet.setProjid(projectId);
		    				slProcreditAssuretenet.setBusinessTypeKey(businessTypeKey);
		    				slProcreditAssuretenet.setProductId(productId);
		    				procreditAssuretenetDao.save(slProcreditAssuretenet);
		    			}else{
		    				s.get(0).setProductId(productId);
		    				procreditAssuretenetDao.save(s.get(0));
		    				
		    			}
		    		}
		    		List<SlProcreditAssuretenet> listtemp11 = procreditAssuretenetDao.getByProjId(projectId, businessTypeKey);;
		    		if(listtemp11!=null&&listtemp11.size()>0){
		    			for(SlProcreditAssuretenet  tempp:listtemp11){
		    				if(tempp.getProductId()!=null && (tempp.getProductId().compareTo(productId)==0)){
		    				}else{
		    					procreditAssuretenetDao.remove(tempp.getAssuretenetId());
		    				}
		    			}
		    		}
		    	}
	    	}else{//如果产品没有归档材料，将已有的归档材料全部删除
	    		if(listtemp1!=null){
		    		for(SlProcreditAssuretenet temp:listtemp1){
		    			procreditAssuretenetDao.remove(temp.getAssuretenetId());
		    		}
		    	}
	    	}
	    }else{//如果没有产品id将所有已经初始化的归档材料删除
	    	if(listtemp1!=null){
	    		for(SlProcreditAssuretenet temp:listtemp1){
	    			procreditAssuretenetDao.remove(temp.getAssuretenetId());
	    		}
	    	}
	    }
		return false;
	}
	
	@Override
	public void deleteByProductId(String id) {
		String hql="delete from OurProcreditAssuretenet as o where o.productId=?";
		Query query = getSession().createQuery(hql).setLong(0, Long.valueOf(id));
		query.executeUpdate();
	}

	@Override
	public List<OurProcreditAssuretenet> getByProductId(Long productId) {
		String hql = "from OurProcreditAssuretenet sm  where sm.productId="+productId;
		return this.findByHql(hql);
	}

	@Override
	public List<OurProcreditAssuretenet> getByProjectId(Long projectId) {
		String hql = "from OurProcreditAssuretenet sm  where sm.projectId="+projectId;
		return this.findByHql(hql);
	}

	@Override
	public List<OurProcreditAssuretenet> getAssuretenetTree(String customerType) {
		String hql = "from OurProcreditAssuretenet AS a where a.projectId is  NULL and a.productId is  NULL ";
		if(null!=customerType && !"".equals(customerType)){
			hql+=" and a.customerType='"+customerType+"'";
		}
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<OurProcreditAssuretenet> getByPProductIdAndOperationType(
			String productId, String businessType) {
		String hql = "from OurProcreditAssuretenet sm  where sm.businessTypeKey=? and sm.productId =?";
		return getSession().createQuery(hql).setParameter(0, businessType)
				.setParameter(1,
						Long.valueOf(productId)).list();
	}


	@Override
	public List<OurProcreditAssuretenet> getListByType(String businessType,
			String operationType) {
		String hql = "from OurProcreditAssuretenet sm  where sm.businessTypeKey=? and sm.operationTypeKey=? and sm.productId is NULL and sm.projectId is null";
		return getSession().createQuery(hql).setParameter(0, businessType).setParameter(1, operationType).list();
	}

	@Override
	public List<OurProcreditAssuretenet> checkIsExit(String productId,
			Long assuretenetId, String businessType) {
		String hql = "from OurProcreditAssuretenet sm  where sm.businessTypeKey=? and sm.productId=? and sm.projectId =?";
		return getSession().createQuery(hql).setParameter(0, businessType)
				.setParameter(1,
						Long.valueOf(productId)).setParameter(2, assuretenetId)
				.list();
	}
	
	

}