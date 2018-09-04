package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;

import java.util.Iterator;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseRelationPersonDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseRelationPerson;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseRelationPersonService;

public class EnterpriseRelationPersonServiceImpl extends BaseServiceImpl<EnterpriseRelationPerson> implements EnterpriseRelationPersonService{
	@SuppressWarnings("unused")
	private EnterpriseRelationPersonDao dao;
	public EnterpriseRelationPersonServiceImpl(EnterpriseRelationPersonDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<EnterpriseRelationPerson> getListByEnterpriseId(
			Integer enterpriseId) {
		
		return dao.getListByEnterpriseId(enterpriseId);
	}
	@Override
	public void add(EnterpriseRelationPerson relationPerson) {
		try{
			int eid = relationPerson.getEnterpriseid();
			EnterpriseRelationPerson relationPersonIter = null ;
			List<EnterpriseRelationPerson> list = this.getListByEnterpriseId(eid);
			if(null != list && !"".equals(list)){
				if(true == relationPerson.getMark()){
					for(Iterator<EnterpriseRelationPerson> iter = list.iterator() ; iter.hasNext();){
						relationPersonIter = iter.next() ;
						if(true == relationPersonIter.getMark()){
							relationPersonIter.setMark(false);
							dao.save(relationPersonIter);
						}
					}
				}
			}
			dao.save(relationPerson);
			JsonUtil.responseJsonString("{success : true , sign : true}");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void update(EnterpriseRelationPerson relationPerson) {
		try{

			int eid = relationPerson.getEnterpriseid();
			EnterpriseRelationPerson relationPersonIter = null ;
			List<EnterpriseRelationPerson> list = dao.getListByEnterpriseId(eid);
			if(null != list && !"".equals(list)){
				if(true == relationPerson.getMark()){
					for(Iterator<EnterpriseRelationPerson> iter = list.iterator() ; iter.hasNext();){
						relationPersonIter = iter.next() ;
						if(true == relationPersonIter.getMark() && relationPerson.getId()!=relationPersonIter.getId()){
							relationPersonIter.setMark(false);
							dao.merge(relationPersonIter);
						}
					}
				}
			}
			dao.merge(relationPerson);
			JsonUtil.responseJsonString("{success : true}");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public EnterpriseRelationPerson getById(int id) {
		
		return dao.getById(id);
	}
	@Override
	public List<EnterpriseRelationPerson> getList(Integer enterpriseId,PagingBean pb){
		return dao.getList(enterpriseId, pb);
	}
}